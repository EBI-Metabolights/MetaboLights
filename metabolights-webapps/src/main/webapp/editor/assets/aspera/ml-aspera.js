// METABOLIGHTS-jquery.js
//
// Authors:
//
// Description:
// 		File controls for downloading METABOLIGHTS files
//
// Date:
//		20130722
//
// Copyright [2013-14] EMBL - European Bioinformatics Institute
// Licensed under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of
// the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied. See the License for the
// specific language governing permissions and limitations
// under the License.
//
// Version history
// 0.2, 2014/05/24: Added support for upload button. 
//                  General code clean-up and added documentation.

jQuery.namespace( 'METABOLIGHTS' );

// Global variables to keep track of Aspera plugin for pages with several jsTrees on them
var UploadStarted = false;

// Size limit of the possible file selection for a download from FTP
// 1.5 GB
var SizeLimitSelectedFTP = 1610612736;

var FtpSizeExceededMsg = "The maximum size of the data for the HTTP tarball download is: " + SizeLimitSelectedFTP + " B." +
    "\nPlease install and use Aspera plugin for a more convenient way of downloading or choose a smaller sample of data."

var conf = METABOLIGHTS.ConfigFileControl;


//A function literal that is executed immediately. It is used for postponing the execution of the function until the specified element is inserted into the DOM 
//@property jQuery plugin which runs handler function once specified element is inserted into the DOM
(function ($) {
    // @function
    // @param handler: a function to execute at the time when the element is inserted
    // @param shouldRunHandlerOnce Optional: if true, handler is unbound after its first invocation
    // $(selector).waitUntilExists(function);
    $.fn.waitUntilExists    = function (handler, shouldRunHandlerOnce, isChild) {
        var found       = 'found';
        var $this       = $(this.selector);
        var $elements   = $this.not(function () { return $(this).data(found); }).each(handler).data(found, true);

        if (!isChild)
        {
            (window.waitUntilExists_Intervals = window.waitUntilExists_Intervals || {})[this.selector] =
                window.setInterval(function () { $this.waitUntilExists(handler, shouldRunHandlerOnce, true); }, 500);
        }
        else if (shouldRunHandlerOnce && $elements.length)
        {
            window.clearInterval(window.waitUntilExists_Intervals[this.selector]);
        }
        return $this;
    }
}(jQuery));


function moveAsperaIframe() {
    var asperaIframe = document.getElementById('aspera-iframe-container');
    document.getElementsByTagName('body')[0].insertBefore(asperaIframe, document.getElementById('wrapper'));
}


var controls = {
    hide: function () {
        $('.asperaControlElement').hide();
        $('.noAsperaControlElement').show();
    },
    show: function () {
        $('.noAsperaControlElement').hide();
        $('.asperaControlElement').show();
    },
};


// Constructor for METABOLIGHTS.FileControl
// See init() for definition of params
METABOLIGHTS.FileControl = function (params) {
    this.init(params);
};


// Initialization function for METABOLIGHTS.FileControl called by the constructor
// @param params: object with configuration info
// @param params.sessionId: ID to give Aspera session
// @param params.transferContainer: DIV or other HTML element in which to show transfer progress
// @param params.messageContainer: DIV or other HTML element in which to show error/warning messages etc.
METABOLIGHTS.FileControl.prototype.init = function (params) {
    var self = this;
    self.transferDiv = null;
    self.transferInfoDiv = null;
    self.progressBar = null;
    self.progressBarTextDiv = null;
    self.transferPauseButton = null;
    self.transferResumeButton = null;
    self.transferAbortButton = null;
    self.requestId = null;
    self.transferId = null;
    self.transferStatus = null;

    var CONNECT_MIN_VERSION = "3.6.0";
    var CONNECT_AUTOINSTALL_LOCATION = "//d3gcli72yxqn2z.cloudfront.net/connect/v4";
    self.asperaWeb = new AW4.Connect({
        containerId: 'pluginContainer' + params.id,
        id: "aspera_connect_object_container" + params.id,
        minVersion: CONNECT_MIN_VERSION,
        sdkLocation: CONNECT_AUTOINSTALL_LOCATION
    });
    self.connectInstaller = new AW4.ConnectInstaller({sdkLocation : CONNECT_AUTOINSTALL_LOCATION});
    var statusEventListener = function (eventType, data) {
        if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.RUNNING) {
            self.connectInstaller.connected();
            // Enable controls that require Connect.
            $('.asperaControlElement').waitUntilExists(controls.show);
            if ($('.jstree').length > 0 && $('.jstree-checked').length < 1)
                $('.jstree').jstree("check_all");
        } else {
            if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.INITIALIZING) {
                self.connectInstaller.showLaunching();
            } else if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.FAILED) {
                self.connectInstaller.showDownload();
            } else if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.OUTDATED) {
                self.connectInstaller.showUpdate();
            }

            $('#aspera-iframe-container').waitUntilExists(moveAsperaIframe);
        }
    };
    self.asperaWeb.addEventListener(AW4.Connect.EVENT.STATUS, statusEventListener);

    self.sessionId = self.asperaWeb.initSession(params.sessionId);
    $(params.transferContainer).show();
    self.asperaWeb.addEventListener('transfer', function(event, obj) { self.handleTransferEvents(event, obj, params.transferContainer, params.entryName); });

    $(params.transferContainer).css('display', 'none');
};


// Hide or show parts of the transfer panel, e.g., transferInfoDiv: 0/1 -> hide/show
// @param params: object containing switches to control visibility of different parts of the control panel
// @param params.transferInfoDiv: transfer text message; 0/1 -> hide/show
// @param params.progressBar: progress bar graphic; 0/1 -> hide/show
// @param params.progressBarTextDiv: progress as percentage; 0/1 -> hide/show
// @param params.transferPauseButton: 0/1 -> hide/show
// @param params.transferResumeButton: 0/1 -> hide/show
// @param params.transferAbortButton: 0/1 -> hide/show
METABOLIGHTS.FileControl.prototype.showHideTransferComponents = function (params) {
    if (params.transferInfoDiv) {
        $(this.transferDiv).css('display', 'block');
        $(this.transferInfoDiv).css('display', 'block');
    }
    else {
        $(this.transferInfoDiv).css('display', 'none');
    }
    if (params.progressBar) {
        $(this.transferDiv).css('display', 'block');
        $(this.progressBar).css('display', 'block');
    }
    else {
        $(this.progressBar).css('display', 'none');
    }
    if (params.progressBarTextDiv) {
        $(this.transferDiv).css('display', 'block');
        $(this.progressBarTextDiv).css('display', 'block');
    }
    else {
        $(this.progressBarTextDiv).css('display', 'none');
    }
    if (params.transferPauseButton) {
        $(this.transferDiv).css('display', 'block');
        $(this.transferPauseButton).css('display', 'inline-block');
    }
    else {
        $(this.transferPauseButton).css('display', 'none');
    }
    if (params.transferResumeButton) {
        $(this.transferDiv).css('display', 'block');
        $(this.transferResumeButton).css('display', 'inline-block');
    }
    else {
        $(this.transferResumeButton).css('display', "none");
    }
    if (params.transferAbortButton) {
        $(this.transferDiv).css('display', 'block');
        $(this.transferAbortButton).css('display', 'inline-block');
    }
    else {
        $(this.transferAbortButton).css('display', 'none');
    }
    // Do not show the transferDiv if none of the children are to be shown
    if (!params.transferInfoDiv && !params.progressBar && !params.progressBarTextDiv && !params.transferPauseButton && !params.transferResumeButton && !params.transferAbortButton) {
        $(this.transferDiv).css('display', 'none');
    }
}


// Event handler for Aspera transfer events 
// @param event: event triggering handler - not currently used
// @param obj: object with information about the transfer
// @param divId: DIV ID for panel used to display transfer info
//METABOLIGHTS.FileControl.prototype.handleTransferEvents = function (event, obj, divId, entryName) {
METABOLIGHTS.FileControl.prototype.handleTransferEvents = function (event, allTransfers, divId, entryName) {
    var self = this,
        transferDiv,
        transferPauseId,
        transferResumeId,
        progressPercent,
        infoText = '';
    var obj = allTransfers.transfers[0];
    if (obj) {
        if (obj.error_code === -1) {
            // Handle case when unable to contact Connect
            alert(obj.error_desc);
        }

        self.transferId = obj.uuid;
        //self.requestId = obj.aspera_connect_settings.request_id;
        self.transferStatus = obj.status;

        // Get transfer div if null
        if(!this.transferDiv) {
            this.transferDiv = $(divId);
        }

        // Create transfer info div if it does not exist
        if(!this.transferInfoDiv) {
            this.transferInfoDiv = document.createElement('div');
            $(this.transferDiv).append(this.transferInfoDiv);
        }

        // If filename exists and/or status - add to info text
        if(obj.current_file) {
            infoText += obj.current_file + ', ';
        }
        if(obj.status) {
            infoText += obj.status;
        }
        $(this.transferInfoDiv).html(infoText);

        // Create progress bar if it does not exist
        if(!this.progressBar) {
            this.progressBar = document.createElement('div');

            $(this.progressBar).progressbar({
                value: 0
            });
            $(this.progressBar).height("10px");
            $(this.progressBar).width("200px");

            $(this.transferDiv).append(this.progressBar);
        }

        // Create progress bar text div if it does not exist
        if(!this.progressBarTextDiv) {
            this.progressBarTextDiv = document.createElement('div');
            $(this.transferDiv).append(this.progressBarTextDiv);
        }

        if (obj.percentage) {
            progressPercent = obj.percentage * 100;
            $(this.progressBar).progressbar('value', progressPercent);
            $(this.progressBarTextDiv).html(progressPercent.toFixed(1));
        }

        // Create pause button if it does not exist
        transferPauseId = divId + '_pause'
        if(!this.transferPauseButton) {
            this.transferPauseButton = $('<button/>',
                {
                    id: transferPauseId,
                    label: 'Pause',
                    text: 'Pause',
                });
            $(this.transferDiv).append(this.transferPauseButton);

            var transferPauseButtonClick = function (e, fcObj) {
                if (fcObj.transferId !== null && (fcObj.transferStatus === "initiating" || fcObj.transferStatus === "running")) {
                    fcObj.requestId = fcObj.asperaWeb.stopTransfer(fcObj.transferId);
                }
                e.preventDefault();
            };

            this.transferPauseButton.on("click", function(e) { transferPauseButtonClick(e, self); });
        }

        // Create resume button if it does not exist
        transferResumeId = divId + '_resume'

        if(! this.transferResumeButton ) {
            this.transferResumeButton = $('<button/>',
                {
                    id: transferResumeId,
                    label: 'Resume',
                    text: 'Resume',
                });
            $(this.transferDiv).append(this.transferResumeButton);

            var transferResumeButtonClick = function (e, fcObj) {
                if (fcObj.transferId !== null && (fcObj.transferStatus !== "initiating" && fcObj.transferStatus !== "running")) {
                    fcObj.requestId = fcObj.asperaWeb.resumeTransfer(fcObj.transferId);
                }
                e.preventDefault();
            };

            this.transferResumeButton.on("click", function(e) { transferResumeButtonClick(e, self); });
        }

        // Create abort button if it does not exist
        transferAbortId = divId + '_abort'
        if(!this.transferAbortButton) {
            this.transferAbortButton = $('<button/>',
                {
                    id: transferAbortId,
                    label: 'Abort',
                    text: 'Abort',
                });
            $(this.transferDiv).append(this.transferAbortButton);

            var transferAbortButtonClick = function (e, fcObj) {
                if (fcObj.transferId !== null) {
                    fcObj.requestId = fcObj.asperaWeb.removeTransfer(fcObj.transferId);
                    fcObj.transferId = null;
                }
                e.preventDefault();
            };

            this.transferAbortButton.on("click", function(e) { transferAbortButtonClick(e, self); });
        }

        // Hide and show components depending on current status
        if (['running', 'queued', 'willretry'].indexOf(self.transferStatus) >= 0) {
            this.showHideTransferComponents({ transferInfoDiv: 1, progressBar: 1, progressBarTextDiv: 1, transferPauseButton: 1, transferResumeButton: 0, transferAbortButton: 1});
        }
        else if (self.transferStatus === "initiating") {
            this.showHideTransferComponents({ transferInfoDiv: 1, progressBar: 0, progressBarTextDiv: 0, transferPauseButton: 1, transferResumeButton: 0, transferAbortButton: 1});
        }
        else if (self.transferStatus === "cancelled") {
            this.showHideTransferComponents({ transferInfoDiv: 1, progressBar: 1, progressBarTextDiv: 1, transferPauseButton: 0, transferResumeButton: 1, transferAbortButton: 1});
        }
        else if (['completed', 'removed'].indexOf(self.transferStatus) >= 0) {
            this.showHideTransferComponents({ transferInfoDiv: 1, progressBar: 1, progressBarTextDiv: 1, transferPauseButton: 0, transferResumeButton: 0, transferAbortButton: 0});
            setTimeout(function () {
                $(self.transferDiv).css( 'display', 'none');
                if (UploadStarted) {
                    UploadStarted = false;
                    if (entryName !== undefined)
                        window.open("edit_imagesets/", "_self");
                }
            }, 12000);
        }
        else if (['failed'].indexOf(self.transferStatus) >= 0) {
            this.showHideTransferComponents({ transferInfoDiv: 1, progressBar: 1, progressBarTextDiv: 1, transferPauseButton: 0, transferResumeButton: 0, transferAbortButton: 0});
        }
        else {
            this.showHideTransferComponents({ transferInfoDiv: 0, progressBar: 0, progressBarTextDiv: 0, transferPauseButton: 0, transferResumeButton: 0, transferAbortButton: 0});
        }
    }
};


// Add Aspera download button page and setup event handlers. Used on the main METABOLIGHTS page for 
// adding to the main table buttons that allow downloading METABOLIGHTS entries as a whole
// @param params: object with settings on how the button should be setup
// @param params.buttonId: HTML ID to give button element
// @param params.buttonLabel: Label to show on button
// @param params.source: Name of file or directory to download, e.g. archive/junk/
// @param params.downloadContainer: DIV or HTML element in which to put the download button
METABOLIGHTS.FileControl.prototype.addDownloadButton = function (params) {

    var self = this;
    var downloadButton = $('<button/>',
        {
            id: params.buttonId,
            label: params.buttonLabel,
            text: params.buttonLabel,
        });
    downloadButton.addClass("METABOLIGHTSDownloadButton asperaControlElement");
    $(params.downloadContainer).append(downloadButton);
    var downloadButtonClick = function (e) {
        self.asperaWeb.showSelectFolderDialog( { success: function(dataTransferObj) { if (dataTransferObj.dataTransfer.files[0]) self.download(params.source, dataTransferObj.dataTransfer.files[0].name); } });
    };
    downloadButton.on("click", downloadButtonClick);

    var uploadButtonNoAspera = $('<button/>',
        {
            id: params.buttonId + "NoAspera",
            label: params.buttonLabel + "NoAspera",
            text: params.buttonLabel,
        });
    uploadButtonNoAspera.addClass("METABOLIGHTSDownloadButton noAsperaControlElement");
    $(params.downloadContainer).append(uploadButtonNoAspera);

    // If Aspera plugin is not installed, then show a message that prompts users to install it
    var uploadButtonNoAsperaClick = function (e) {
        alert("In order to download files please install Aspera Connect.");
    };
    uploadButtonNoAspera.unbind('click').bind('click', uploadButtonNoAsperaClick);
};


// File download callback handler triggered from METABOLIGHTS.FileControl.prototype.addDownloadButton
// @param source: directory path on Aspera server that is to be downloaded
// @param path: directory path on client where download is to take place
// @param fcObj: METABOLIGHTS.FileControl object
METABOLIGHTS.FileControl.prototype.download = function (source, path) {
    var self=this,
        params,
        transferSettings = { 	allow_dialogs: false,
            use_absolute_destination_path: true},
        requestId;
    if (path != null && path.length > 0) {
        var pathsArray = new Array();
        if (jQuery.type(source) === 'array') {
            for (i=0; i<source.length; ++i) {
                pathsArray[i] = {"source" : source[i]};
            }
        } else {
            pathsArray[0] = {"source" : source};
        }

        var transferSpecs = [];
        transferSpecs.push({
            "aspera_connect_settings": {
                // allow_dialogs is true by default.
                // Added for clarity.
                "allow_dialogs": false,
                "back_link": location.href,
                "use_absolute_destination_path" : true
            },
            "transfer_spec": {
                "authentication": conf.download_authentication,
                "cipher": conf.cipher,
                "create_dir": true,
                "token": conf.download_token,
                "destination_root": path,
                "direction": "receive",
                "http_fallback": true,
                "paths": pathsArray,
                "rate_policy": conf.rate_policy,
                "remote_host": conf.remote_download_host,
                "remote_user": conf.remote_download_user,
                "resume": 'sparse_checksum', // "none","attributes","sparse_checksum","full_checksum"
                "ssh_port": 22,
                "target_rate_kbps": conf.target_rate_kbps
            }
        });

        requestId = self.asperaWeb.startTransfers({'transfer_specs': transferSpecs}, {"error": self.handleStartResponse});
    }
};


// Add upload button to page and setup event handlers
// @param params: object with settings on how the button should be setup
// @param params.buttonId: HTML ID to give button element
// @param params.buttonLabel: Button label
// @param params.buttonText: Text element of button
// @param params.uploadTokenCodeInputId: Name of input box where the upload token code is specified. This is the name of the destination directory.
// @param params.uploadContainer: DIV or HTML element in which to put the upload button
METABOLIGHTS.FileControl.prototype.addUploadButton = function (params) {
    var self = this;

    $('#'+params.uploadContainer).append('<div id="' + params.buttonId + "Aspera" + 'Div"></div>');

    var uploadButton = $('<button/>',
        {
            id: params.buttonId + "Aspera",
            label: params.buttonLabel + "Aspera",
            text: params.buttonText + ' files',
        });
    uploadButton.addClass("METABOLIGHTSDownloadButton asperaControlElement icon icon-functional");
    uploadButton.attr("data-icon", "_");
    $('#'+params.buttonId+"Aspera"+'Div').append(uploadButton);
    var uploadButtonClick = function (e) {
        self.asperaWeb.showSelectFileDialog({
            success: function(dataTransferObj) {
                if (dataTransferObj.dataTransfer.files[0]) {
                    self.upload(dataTransferObj.dataTransfer.files, params.destination, self);
                }
            }
        });
    };
    uploadButton.on("click", uploadButtonClick);

    var uploadButtonDir = $('<button/>',
        {
            id: params.buttonId + "DirAspera",
            label: params.buttonLabel + "DirAspera",
            text: params.buttonText + ' directories',
        });
    uploadButtonDir.addClass("METABOLIGHTSDownloadButton asperaControlElement icon icon-functional");
    uploadButtonDir.attr("data-icon", "_");
    $('#'+params.buttonId+"Aspera"+'Div').append(uploadButtonDir);
    var uploadButtonDirClick = function (e) {
        self.asperaWeb.showSelectFolderDialog({
            success: function(dataTransferObj) {
                if (dataTransferObj.dataTransfer.files[0]) {
                    self.upload(dataTransferObj.dataTransfer.files, params.destination, self);
                }
            }
        });
    };
    uploadButtonDir.on("click", uploadButtonDirClick);


    var uploadButtonNoAspera = $('<button/>',
        {
            id: params.buttonId + "NoAspera",
            label: params.buttonLabel + "NoAspera",
            text: params.buttonText + ' files',
        });
    uploadButtonNoAspera.addClass("METABOLIGHTSDownloadButton noAsperaControlElement icon icon-functional");
    uploadButtonNoAspera.attr("data-icon", "_");
    $('#'+params.buttonId+"Aspera"+'Div').append(uploadButtonNoAspera);

    // If Aspera plugin is not installed, then show a message that prompts users to install it
    var uploadButtonNoAsperaClick = function (e) {
        alert("In order to upload files please install Aspera Connect.");
    };
    uploadButtonNoAspera.unbind('click').bind('click', uploadButtonNoAsperaClick);

    var uploadButtonDirNoAspera = $('<button/>',
        {
            id: params.buttonId + "DirNoAspera",
            label: params.buttonLabel + "DirNoAspera",
            text: params.buttonText + ' directories',
        });
    uploadButtonDirNoAspera.addClass("METABOLIGHTSDownloadButton noAsperaControlElement icon icon-functional");
    uploadButtonDirNoAspera.attr("data-icon", "_");
    $('#'+params.buttonId+"Aspera"+'Div').append(uploadButtonDirNoAspera);

    // If Aspera plugin is not installed, then show a message that prompts users to install it
    var uploadButtonDirNoAsperaClick = function (e) {
        alert("In order to upload directories please install Aspera Connect.");
    };
    uploadButtonDirNoAspera.unbind('click').bind('click', uploadButtonDirNoAsperaClick);
};


// File upload callback handler triggered from METABOLIGHTS.FileControl.prototype.addUploadButton
// @param path: array of source file paths
// @param destination: upload token code that forms the last part of the destination directory path
// @param fcObj: METABOLIGHTS.FileControl object
METABOLIGHTS.FileControl.prototype.upload = function (paths, destination, fcObj) {
    var params,
        transferSettings = { 	allow_dialogs: false,
            use_absolute_destination_path: false},
        requestId,
        pathsArray = [],
        nPaths,
        i;

    if (paths != null && paths.length > 0) {
        nPaths = paths.length;
        for(i=0; i<nPaths; i++) {
            pathsArray.push({source:paths[i].name, destination:""});
        }

        var transferSpecs = [];
        transferSpecs.push({
            "aspera_connect_settings": {
                // allow_dialogs is true by default.
                // Added for clarity.
                "allow_dialogs": false,
                "back_link": location.href,
                "use_absolute_destination_path" : true
            },
            "transfer_spec": {
                "cipher": conf.cipher,
                "create_dir": false,
                "destination_root": destination,
                "direction": "send",
                "http_fallback": true,
                "paths": pathsArray,
                "rate_policy": conf.rate_policy,
                "remote_host": conf.remote_upload_host,
                "remote_user": conf.remote_upload_user,
                "remote_password": conf.remote_upload_password,
                "resume": 'sparse_checksum', // "none","attributes","sparse_checksum","full_checksum"
                "ssh_port": 22,
                "target_rate_kbps": conf.target_rate_kbps
            }
        });

        requestId = fcObj.asperaWeb.startTransfers({'transfer_specs': transferSpecs}, {"error": fcObj.handleStartResponse});
    }
};


// Error callback function for asperaWeb.startTransfer
// @param responseData: object contains information about the error condition
// @param responseData.error.code: error code
// @param responseData.error.user_message: error message
METABOLIGHTS.FileControl.prototype.handleStartResponse = function(responseData) {
    var code,
        userMessage;

    code = responseData.error.code;
    userMessage = responseData.error.user_message;
    switch(code) {
        case 401:
            break;
        case 900:
            // Content protection not accepted by the destination
            break;
        default:
    }
};


// Make jsTree that represents a directory on a local machine with checkboxes and a Download button.
// @param ftpServer: FTP server, where JSON files that represent the directory structures are stored
// @param dirStruct: location on the FTP server, where JSON files that represent the directory structures are stored
// @param treeDiv: DIV element of the page that will represent jstree
// @param buttonId: ID of the download button for this particular tree
// @param sourcePath: ID of the source path for this particular tree
// @param treeFileController: FileControl element that connects with a Download button. Submit -1 to make a jsTree for image set association, without a download button
// @param entryUrl: the address to the METABOLIGHTS entry that determines whether the jsTree json should be obtained for the deposition, for the release process or for the METABOLIGHTS entry page
// @param imageSetId: ID of the Image set to be processed. Defaults to -1 for the main directory tree
// @example: @example: browserTree ('10014', '#jstree_json', '#mybuttonid', 'archive/', trfc, 1)
// @example: browserTree ('ftp.ebi.ac.uk', '/pub/databases/emtest/METABOLIGHTS/directoryStructures', '#jstree_json', 'mybuttonid', 'archive/', trfc, 'www.ebi.ac.uk/pdbe/emdb/METABOLIGHTS/entry/10002');
browserTree = function (ftpServer, dirStruct, treeDiv, buttonId, sourcePath, treeFileController, entryUrl, imageSetId) {
    $(function () {
        var jsTreeCore = {
            'data' : {
                "url" : function (node) {
                    if (typeof imageSetId == 'undefined')
                        imageSetId = '-1';
                    return node.id === '#' ? entryUrl + '/ftpServer=' + ftpServer + '&dirStruct=' + dirStruct + '&list=' + 1 + '/' + imageSetId : entryUrl + '/ftpServer=' + ftpServer + '&dirStruct=' + dirStruct + '&list=' + (parseInt(node.id) + parseInt(1)).toString() + '/' + imageSetId ;
                },
                "data" : function (node) {
                    return { "id" : node.id };
                },
                "error": function (jqXHR, textStatus, errorThrown) { $(treeDiv).html("There was an error while loading data for this tree")}
            },
        }
        if (treeFileController == -1) {
            jsTreeCore['multiple'] = false;
            jsTreeCheckbox = {
                "three_state" : false
            };
        }
        else
            jsTreeCheckbox = {
                "whole_node" : false,
                "tie_selection" : false,
            };

        $(treeDiv).jstree({
            'core': jsTreeCore,
            "checkbox" : jsTreeCheckbox,
            "types" : {
                "default" : {
                    "valid_children" : ["default","file"]
                },
                "file" : {
                    "icon" : "jstree-file",
                    "valid_children" : []
                }
            },
            "plugins" : [ "checkbox", "json_data", "types" ],
        });
    });

    if (treeFileController != -1) {
        // A general download button that shows Aspera and FTP download buttons in a shadowbox
        var downloadButtonAspFtp = $('<button/>',
            {
                id: buttonId + 'AspFtp',
                label: 'Download',
                text: 'Download',
            });
        downloadButtonAspFtp.addClass("METABOLIGHTSDownloadButton icon icon-functional");
        downloadButtonAspFtp.attr("data-icon", "=");
        $('#'+buttonId).append('<div id="' + buttonId + 'AspFtpDiv"></div>');
        $('#'+buttonId+'AspFtpDiv').append(downloadButtonAspFtp);
        downloadButtonAspFtp.on("click",  function(){
            var arr = new Array();
            arr = $(treeDiv).jstree('get_top_checked');
            if (arr.length < 1) {
                alert("Please select files and/or folders which you would like to download.");
                return;
            }

            treeFileController.addDownloadButtonTree( { buttonId: buttonId,
                buttonLabel: 'Download',
                downloadContainer: 'METABOLIGHTSChooseDlInner',
                source: sourcePath,
                treeId: treeDiv } );

            treeFileController.addFtpDownloadButtonTree( { buttonId: buttonId,
                buttonLabel: 'Download',
                downloadContainer: 'METABOLIGHTSChooseDlInner',
                source: sourcePath,
                treeId: treeDiv } );
            $('#METABOLIGHTSChooseDl').show();
        });
    }
};


// Add Aspera download button for jsTree to page and setup event handlers. Used on METABOLIGHTS entry pages for 
// adding buttons that allow downloading separate files of METABOLIGHTS entries as selected in jsTree directory
// representation
// @param params: object with settings on how the button should be setup
// @param params.buttonId: HTML ID to give button element
// @param params.buttonLabel: Label to show on button
// @param params.downloadContainer: DIV or HTML element in which to put the download button
// @param params.source: Array of names of files or directories to download, e.g. ['archive/10014/DeltProTM', 'archive/10014/10014.xml']
// @param params.treeId: ID of the jsTree that is being associated with the downlaod button
METABOLIGHTS.FileControl.prototype.addDownloadButtonTree = function (params) {
    var self = this;
    var downloadButton = $('<button/>',
        {
            id: params.buttonId + "Aspera",
            label: params.buttonLabel + "Aspera",
            text: "Aspera (recommended)",
        });
    downloadButton.addClass("METABOLIGHTSDownloadButton asperaControlElement icon icon-functional");
    downloadButton.attr("data-icon", "=");
    $('#'+params.downloadContainer).append('<div id="' + params.buttonId + "Aspera" + 'Div"></div>');
    $('#'+params.buttonId+"Aspera"+'Div').append(downloadButton);

    // If Aspera plugin is installed, then get an array of checked tree nodes and provide it to Aspera
    var downloadButtonClick = function (e) {
        // Get the checked jsTree nodes
        var arr = new Array();
        arr = $(params.treeId).jstree('get_top_checked');
        if (arr.length > 0) {
            var arrayLength = arr.length;

            // Make an array of system paths to the checked jsTree nodes
            var parents = new Array();
            for (var i = 0; i < arrayLength; ++i) {
                parents.push("archive/" + $(params.treeId).jstree("get_path", arr[i], '/'));
            };

            // Feed the array of paths to Aspera plugin for download initiation
            self.asperaWeb.showSelectFolderDialog( { success: function(dataTransferObj) { if (dataTransferObj.dataTransfer.files[0]) self.download(parents, dataTransferObj.dataTransfer.files[0].name); } });
        }
        else
            alert("Please select files and/or folders which you would like to download.");
    };
    downloadButton.unbind('click').bind('click', downloadButtonClick);


    var downloadButtonNoAspera = $('<button/>',
        {
            id: params.buttonId + "NoAspera",
            label: params.buttonLabel + "NoAspera",
            text: "Aspera (recommended)",
        });
    downloadButtonNoAspera.addClass("METABOLIGHTSDownloadButton noAsperaControlElement icon icon-functional");
    downloadButtonNoAspera.attr("data-icon", "=");
    $('#'+params.buttonId+"Aspera"+'Div').append(downloadButtonNoAspera);

    // If Aspera plugin is not installed, then show a message that prompts users to install it
    var downloadButtonNoAsperaClick = function (e) {
        alert("In order to download files please install Aspera Connect.");
    };

    downloadButtonNoAspera.unbind('click').bind('click', downloadButtonNoAsperaClick);
};


//Display the loading wheel depending on the browser
showLoader = function(){
    var divBrowserVersion = document.createElement("div");
    divBrowserVersion.innerHTML = "<!--[if lt IE 10]><i></i><![endif]-->";
    var isIeLessThan10 = (divBrowserVersion.getElementsByTagName("i").length == 1);

    // If the browser is IE<10, then show the GIF loading wheel
    if (isIeLessThan10) {
        $('#METABOLIGHTSLoaderGif').show();
    }
    // Else show the CSS loading wheel
    else {
        $('#METABOLIGHTSLoader').show();
    }
}


//Hide the loading wheel depending on the browser
hideLoader = function(){
    var divBrowserVersion = document.createElement("div");
    divBrowserVersion.innerHTML = "<!--[if lt IE 10]><i></i><![endif]-->";
    var isIeLessThan10 = (divBrowserVersion.getElementsByTagName("i").length == 1);

    // If the browser is IE<10, then hide the GIF loading wheel
    if (isIeLessThan10) {
        $('#METABOLIGHTSLoaderGif').hide();
    }
    // Else hide the CSS loading wheel
    else {
        $('#METABOLIGHTSLoader').hide();
    }
}


// Start the preparation of the tarball of selected folders/files. Check if the tarball is ready, download it on success
// or recursively execute the check again. The check is performed every three seconds, timing is set in the Django view.
// This check is necessary to keep the connection between the server and the client alive, otherwise after thirty seconds
// a redirect to an error page occurs
// @param params: object with parts of the link that is used in the AJAX call
// @param params.name: the name under which the tarball will be stored by the user
// @param params.parents: list of files/folders that are to be downloaded from FTP to the server's memory, and archived 
// into a tarball that would be sent to the user
// @param params.sizeChecked: the size of the selected files/folders that will be used to check on the available server's
// memory for storing the tarball before sending it to the user
// @param params.unique_id: unique ID that is used in Django view to check whether the finished tarball is the one that
// @example: getTarFile("archive.tar", ["/archive/10030/10030.xml"], 3742, "false")
getTarFile = function(params) {
    $.ajax({
        type: "GET",
        cache: false,
        url: "storeas=" + params.name + "&getfile=" + params.parents + "&ss=" + params.sizeChecked + "&uid=" + params.unique_id,
        success: function(dataPy){
            // If the tarball is ready for download, then close the shadowbox and open the link
            // that gets the tarball from the server
            if (dataPy == "Ready" || dataPy == "error_ftp") {
                hideLoader();
                // If the file is ready, open the file download dialogue
                if (dataPy == "Ready")
                    window.location.assign("gettar=" + params.name);
                // If an error has occurred, redirect to the ftp error page
                else if (dataPy == "error_ftp")
                    window.location.assign("ftp_error");
            }
            // If the tarball is not ready yet, then get the unique ID of the download and check again whether the tarball 
            // is ready
            else {
                params.unique_id = dataPy.split("uid=")[1]
                getTarFile(params);
            }
        },
        error: function(){
            alert("Could not connect to the server. Please try again later");
        },
    });
}


// Add FTP download button for jsTree to page and setup event handlers
// @param params: object with settings on how the button should be setup
// @param params.buttonId: HTML ID to give button element
// @param params.buttonLabel: Label to show on button
// @param params.source: Array of names of files or directories to download, e.g. ['archive/10014/DeltProTM', 'archive/10014/10014.xml']
// @param params.downloadContainer: DIV or HTML element in which to put the download button
// @param params.treeId: ID of the jsTree that is being associated with the downlaod button
METABOLIGHTS.FileControl.prototype.addFtpDownloadButtonTree = function (params) {
    var self = this;

    var ftpDownloadButton = $('<button/>',
        {
            id: params.buttonId + "Ftp",
            label: params.buttonLabel + "Ftp",
            text: "HTTP tar: suitable only for small data sets (< " + SizeLimitSelectedFTP/1073741824 + " GB)",
        });
    ftpDownloadButton.addClass("METABOLIGHTSDownloadButton icon icon-functional");
    ftpDownloadButton.attr("data-icon", "=");
    $('#'+params.downloadContainer).append('<div id="' + params.buttonId + "Ftp" + 'Div"></div>');
    $('#'+params.buttonId+"Ftp"+'Div').append(ftpDownloadButton);

    // Get an array of checked tree nodes and download them via FTP
    var ftpDownloadButtonClick = function (e) {

        // Get the checked jsTree nodes
        var arr = new Array();
        arr = $(params.treeId).jstree('get_top_checked');
        if (arr.length > 0) {
            var sizeChecked = 0;
            var arrayLength = arr.length;

            // Make an array of system paths to the checked jsTree nodes
            var parents = new Array();
            for (var i = 0; i < arrayLength; ++i) {

                // Calculate the size of the checked nodes
                sizeChecked += parseInt($(params.treeId).jstree(true).get_node($(params.treeId).jstree('get_top_checked')[i]).original.size);
                parents.push("/archive/" + $(params.treeId).jstree("get_path", arr[i], '/'));
            };

            // If the size of checked nodes exceeds the limit, then show an error message.
            // Otherwise, proceed with the preparation of a tarball of the selected files
            // and its consequent downloading
            if (sizeChecked > SizeLimitSelectedFTP) {
                alert(FtpSizeExceededMsg);
            }
            else if ( parents.join("|").indexOf('The file with the list of directories does not exist') >= 0 )
                alert('Please select a valid file.');
            else {
                var name=prompt("Please enter the filename under which you would like to save the tarball:","archive.tar");
                if (name!=null){
                    showLoader();
                    // Start the download of checked files into the server's memory, their archiving into a tar store in the server's
                    // memory, checking whether the tar is ready or not and getting the resulting tar file when its preparation has
                    // finished
                    getTarFile({
                        name: name,
                        parents: parents,
                        sizeChecked: sizeChecked,
                        unique_id: "start"
                    });
                }
            }
        }
        else
            alert("Please select files and/or folders which you would like to download.");
    };

    ftpDownloadButton.on("click", ftpDownloadButtonClick);
};
