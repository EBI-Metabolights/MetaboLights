/*
* Revision: 3.8
* Revision date: 02/13/2019
*
* http://www.asperasoft.com/
*
* Â© Copyright IBM Corp. 2008, 2019
*/

"use strict";

if (typeof AW4 === "undefined") var AW4 = {};
/* section: API
 * class AW4.Logger
 *
 * The [[AW4.Logger]] class contains logging wrapper functions for the developer.
 */
AW4.Logger = (function() {
    var LS_LOG_KEY = 'aspera-log-level';
    var LEVEL = {
        INFO : 0,
        DEBUG : 1,
        TRACE : 2
    };
    AW4.LogLevel = LEVEL.INFO;
    if (typeof(localStorage) != 'undefined' && localStorage.hasOwnProperty(LS_LOG_KEY)) {
        AW4.LogLevel = localStorage.getItem(LS_LOG_KEY);
    }

    function trace(message) {
        if(AW4.LogLevel >= LEVEL.TRACE && typeof window.console !== 'undefined') {
            console.log(message);
        }
    }

    function debug(message) {
        if(AW4.LogLevel >= LEVEL.DEBUG && typeof window.console !== 'undefined') {
            console.log(message);
        }
    }


    /*
     * AW4.Logger.log(message) -> No return value
     * -message (String): A check for if window.console is defined is performed,
     * and if window.console is defined, then message will be sent to
     * console.log.
     *
     * TODO: Support multiple arguments
     */
    function log(message) {
        if(typeof window.console !== 'undefined') {
            console.log(message);
        }
    }

    /*
     * AW4.Logger.warn(message) -> No return value
     * -message (String): A check for if window.console is defined is performed,
     * and if window.console is defined, then message will be sent to
     * console.warn.
     *
     * TODO: Support multiple arguments
     */
    function warn(message) {
        if(typeof window.console !== 'undefined') {
            console.warn(message);
        }
    }

    /*
     * AW4.Logger.error(message) -> No return value
     * -message (String): A check for if window.console is defined is performed,
     * and if window.console is defined, then message will be sent to
     * console.error.
     *
     * TODO: Support multiple arguments
     */
    function error(message) {
        if(typeof window.console !== 'undefined') {
            console.error(message);
        }
    }

    return {
        trace: trace,
        debug: debug,
        log: log,
        warn: warn,
        error: error
    };
})();
if (typeof AW4.Utils === "undefined") {

    /** section: API
     * class AW4.Utils
     *
     * The [[AW4.Utils]] class contains helper functions for the developer.
     **/
    AW4.Utils = (function() {

        var
            /**
             * AW4.Utils.FASP_URL -> String
             *
             * Returns the URL to initialize Aspera Connect
             **/
            DRIVE_API = "com.aspera.drive",
            FASP_API = "fasp",
            CURRENT_API = FASP_API,
            SESSION_ID = generateUuid(),
            SESSION_KEY = generateRandomStr(32),
            FASP_URL = "://initialize/?key=" + SESSION_KEY + "&id=" + SESSION_ID,
            SDK_LOCATION = null,
            // local storage keys
            LS_CONTINUED_KEY = "connect-version-continued",
            LS_CONNECT_APP_ID = "connect-app-id",
            ////////////////////////////////////////////////////////////////////////////
            // Browser helpers
            // https://github.com/ded/bowser
            // MIT License | (c) Dustin Diaz 2014
            ////////////////////////////////////////////////////////////////////////////
            ua = typeof navigator !== 'undefined' ? navigator.userAgent : '',
            check_safari = function(ua,minver) {
                var match = ua.match(/(?:Version)[\/](\d+(\.\d+)?)/i);
                var ver = parseInt((match && match.length > 1 && match[1] || "0"));
                return (ver >= minver);
            } ,
            /**
             * AW4.Utils.OS -> Object
             *
             * Contains the current OS (based on user agent):
             *
             * 1. `AW4.Utils.OS.MAC_LEGACY` (`Boolean`)
             * 2. `AW4.Utils.OS.LINUX` (`Boolean`)
             **/
            OS = {
                MAC_LEGACY: ua.indexOf("Intel Mac OS X 10_6") != -1,
                LINUX: ua.indexOf("X11") != -1 | ua.indexOf("Linux") != -1
            },

            /**
             * AW4.Utils.BROWSER -> Object
             *
             * Contains the type of browser that we are currently on (based on user agent):
             *
             * 1. `AW4.Utils.BROWSER.OPERA` (`Boolean`)
             * 2. `AW4.Utils.BROWSER.IE` (`Boolean`)
             * 3. `AW4.Utils.BROWSER.CHROME` (`Boolean`)
             * 4. `AW4.Utils.BROWSER.FIREFOX` (`Boolean`)
             * 5. `AW4.Utils.BROWSER.FIREFOX_32` (`Boolean`)
             * 6. `AW4.Utils.BROWSER.FIREFOX_64` (`Boolean`)
             * 7. `AW4.Utils.BROWSER.EDGE` (`Boolean`)
             * 8. `AW4.Utils.BROWSER.SAFARI` (`Boolean`)
             * 9. `AW4.Utils.BROWSER.SAFARI_NO_NPAPI` (`Boolean`)
             **/
            BROWSER = {
                OPERA: /opera|opr/i.test(ua) && !/edge/i.test(ua),
                IE: /msie|trident/i.test(ua) && !/edge/i.test(ua),
                CHROME: /chrome|crios|crmo/i.test(ua) && !/opera|opr/i.test(ua) && !/edge/i.test(ua),
                FIREFOX: /firefox|iceweasel/i.test(ua) && !/edge/i.test(ua),
                FIREFOX_32: /firefox|iceweasel/i.test(ua) && !/Win64/i.test(ua) && !/edge/i.test(ua),
                FIREFOX_64: /firefox|iceweasel/i.test(ua) && /Win64/i.test(ua) && !/edge/i.test(ua),
                EDGE: /edge/i.test(ua),
                SAFARI: /safari/i.test(ua) && !/chrome|crios|crmo/i.test(ua) && !/edge/i.test(ua),
                SAFARI_NO_NPAPI: /safari/i.test(ua) && !/chrome|crios|crmo/i.test(ua) && !/edge/i.test(ua) && check_safari(ua, 12)
            };

        function getInitUrl(){
            return this.CURRENT_API + "://initialize/?key=" + this.SESSION_KEY + "&id=" + this.SESSION_ID;
        };

        ////////////////////////////////////////////////////////////////////////////
        // Compatibility functions
        ////////////////////////////////////////////////////////////////////////////

        (function() {
            /*  Add TIMEOUT arguments support to IE < 9
             *  https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers.setTimeout
             */
            if(document.all&&!window.setTimeout.isPolyfill){var __nativeST__=window.setTimeout;window.setTimeout=function(e,t){var n=Array.prototype.slice.call(arguments,2);return __nativeST__(e instanceof Function?function(){e.apply(null,n)}:e,t)},window.setTimeout.isPolyfill=!0}if(document.all&&!window.setInterval.isPolyfill){var __nativeSI__=window.setInterval;window.setInterval=function(e,t){var n=Array.prototype.slice.call(arguments,2);return __nativeSI__(e instanceof Function?function(){e.apply(null,n)}:e,t)},window.setInterval.isPolyfill=!0}
        }());

        var createError = function(errorCode, message) {
            var internalMessage = "";
            if (errorCode == -1) {
                internalMessage = "Invalid request";
            }
            return {error: {code: errorCode, internal_message: internalMessage, user_message: message}};
        };

        /*
         * - str
         * @returns {Object}
         */
        var parseJson = function(str) {
            var obj;
            if ( typeof str === "string" && (str.length === 0 || str.replace(/\s/g, "") === "{}")) {
                return {};
            }
            try {
                obj = JSON.parse(str);
            } catch (e) {
                obj = createError(-1, e);
            }
            return obj;
        };

        ////////////////////////////////////////////////////////////////////////////
        // Helper Functions
        ////////////////////////////////////////////////////////////////////////////

        /*
         * AW4.Utils.versionLessThan(version1, version2) -> bool
         *  - version1 (Number):  a version Integer
         *  - version2 (Number):  a version Integer
         *
         * Compares two version strings.
         * Returns true if version string 'a' is less than version string 'b'
         *     "1.2.1" < "1.11.3"
         *     "1.1"   < "2.1"
         *     "1"     = "1"
         *     "1.2"   < "2"
         * Note the following behavior:
         *     "1"     = "1.2"
         *     "1.2"   = "1"
         *  This helps with upgrade checks.  If at least version "4" is required, and
         *   "4.4.2" is installed, versionLessThan("4.4.2","4") will return false.
         *
         * If the version number contains a character that is not a numeral it ignores
         * it
         */
        var versionLessThan = function(a, b) {
            var versionToArray = function( version ) {
                var splits = version.split(".");
                var versionArray = new Array();
                for (var i = 0; i < splits.length; i++) {
                    var versionPart = parseInt(splits[i], 10);
                    if (!isNaN(versionPart)) {
                        versionArray.push(versionPart);
                    }
                }
                return versionArray;
            };
            var a_arr = versionToArray(a);
            var b_arr = versionToArray(b);
            var i;
            for ( i = 0; i < Math.min(a_arr.length, b_arr.length); i++ ) {
                // if i=2, a=[0,0,1,0] and b=[0,0,2,0]
                if( a_arr[i] < b_arr[i] ) {
                    return true;
                }
                // if i=2, a=[0,0,2,0] and b=[0,0,1,0]
                if( a_arr[i] > b_arr[i] ) {
                    return false;
                }
                // a[i] and b[i] exist and are equal:
                // move on to the next version number
            }
            // all numbers equal (or all are equal and we reached the end of a or b)
            return false;
        };

        var checkVersionException = function() {
            if (typeof(localStorage) == 'undefined')
                return false;
            var prevContinuedSeconds = localStorage.getItem(AW4.Utils.LS_CONTINUED_KEY);
            if (prevContinuedSeconds !== typeof undefined && prevContinuedSeconds !== null) {
                var currentTimeSeconds = Math.round(new Date().getTime()/1000);
                if ((currentTimeSeconds - prevContinuedSeconds) < 60*24){
                    AW4.Logger.debug('User opted out of update');
                    return true;
                }
            }
            return false;
        };

        var addVersionException = function() {
            if (typeof(localStorage) == 'undefined')
                return;
            localStorage.setItem(AW4.Utils.LS_CONTINUED_KEY, Math.round(new Date().getTime() / 1000));
        };

        function generateUuid() {
            var date = new Date().getTime();
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = ((date + 16) * Math.random()).toFixed() % 16;
                if (c !== 'x') {
                    /*jslint bitwise: true */
                    r = r & 0x3 | 0x8;
                    /*jslint bitwise: false */
                }
                return r.toString(16);
            });
        };

        function generateRandomStr(size) {
            var text = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            for (var i = 0; i < size; i++)
                text += possible.charAt(Math.floor(Math.random() * possible.length));

            return text;
        };
        var aesencrypt = function(data) {
            var dataBytes = AW4.crypt.aesjs.utils.utf8.toBytes(data);
            var key = AW4.crypt.aesjs.utils.utf8.toBytes(this.SESSION_KEY);
            var iv = AW4.crypt.aesjs.utils.utf8.toBytes(generateRandomStr(16));
            // The counter is optional, and if omitted will begin at 1
            var aesOfb = new AW4.crypt.aesjs.ModeOfOperation.ofb(key, iv);
            var encryptedBytesString = aesOfb.encrypt(dataBytes);

            // To print or store the binary data, you may convert it to hex
            var encryptedHex = AW4.crypt.aesjs.utils.hex.fromBytes(iv)+'.'+AW4.crypt.aesjs.utils.hex.fromBytes(encryptedBytesString);
            return encryptedHex;
        };

        var aesdecrypt = function(data) {
            var arr = data.split('.');
            if(arr.length != 2){
                return data;
            }

            var iv = AW4.crypt.aesjs.utils.hex.toBytes(arr[0]);
            // When ready to decrypt the hex string, convert it back to bytes
            var encryptedBytes = AW4.crypt.aesjs.utils.hex.toBytes(arr[1]);

            var key = AW4.crypt.aesjs.utils.utf8.toBytes(this.SESSION_KEY);
            // The counter mode of operation maintains internal state, so to
            // decrypt a new instance must be instantiated.
            var aesOfb = new AW4.crypt.aesjs.ModeOfOperation.ofb(key, iv);
            var decryptedBytes = aesOfb.decrypt(encryptedBytes);

            // Convert our bytes back into text
            var decryptedText = AW4.crypt.aesjs.utils.utf8.fromBytes(decryptedBytes)
            return decryptedBytes;
        };

        /**
         * AW4.Utils.launchConnect(callback) -> null
         * - callback (function):  It will be called once we have determined if
         *  connect is installed in the system [CHROME/OPERA]
         *
         * Attempt to launch connect. It will handle different browser
         * implementations to not end in an error page or launch multiple
         * times.
         *
         * [CHROME/OPERA] will return true if Connect is installed
         *
         * ##### Object returned to success callback as parameter
         *
         * 1. `true` : if Aspera Connect is installed
         * 2. `false` : if Aspera Connect is either not installed or we couldn't
         * detect it.
         **/
        var launchConnect = function(userCallback) {
            var isRegistered = false;
            var callback = function(installed) {
                if (typeof userCallback === 'function') {
                    userCallback(installed);
                }
            }

            var launchUri = this.getInitUrl();
            AW4.Logger.log('Starting Connect session: ' + launchUri);

            if (BROWSER.CHROME || BROWSER.OPERA) {
                document.body.focus();
                document.body.onblur = function() {
                    isRegistered = true;
                };
                //will trigger onblur
                document.location = launchUri;
                //Note: timeout could vary as per the browser version, have a higher value
                setTimeout(function(){
                    document.body.onblur = null;
                    callback(isRegistered);
                }, 500);
            } else if (BROWSER.EDGE) {
                document.location = launchUri;
            } else if (BROWSER.FIREFOX || BROWSER.SAFARI_NO_NPAPI) {
                var dummyIframe = document.createElement("IFRAME");
                dummyIframe.src = launchUri;
                // Don't show the iframe and don't allow it to take up space
                dummyIframe.style.visibility = "hidden";
                dummyIframe.style.position = "absolute";
                document.body.appendChild(dummyIframe);
            }
            // ELSE is handled by the NPAPI plugin
            return null;
        };


        /**
         * AW4.Utils.getFullURI(relativeURL) -> String
         *  - relativeURL (String):  The relative URL that we want the full path to,
         *  it must be relative to the current page being rendered. If a full URL is
         *  provided, it will return the same.
         *
         *  @returns {String} - the full URL or null
         **/
        function getFullURI(relativeURL) {
            if (typeof relativeURL !== 'string') {
                return null;
            }
            var url = relativeURL;
            var a = document.createElement('a');
            a.href = url;
            var fullURL = a.href;
            if (fullURL.indexOf('/', fullURL.length - 1) !== -1) {
                fullURL = fullURL.slice(0,-1);
            }
            return fullURL;
        };

        /**
         * AW4.Utils.utoa(inputString) -> String
         * - inputString: The inputString can be utf8 or unicode. The output string is
         * a base64 string.
         **/
        function utoa(inputString) {
            if(window.btoa){
                return window.btoa(unescape(encodeURIComponent(inputString)));
            } else {
                return inputString;
            }
        }

        /**
         * AW4.Utils.atou(inputString) -> String
         * - inputString: The input string is a base64 string. The output is a unicode
         * string.
         **/
        function atou(inputString) {
            if (window.atob) {
                return decodeURIComponent(escape(window.atob(inputString)));
            } else {
                return inputString;
            }
        }

        function nextObjectId() {
            // Return an incrementing id even if file was reloaded
            if (typeof(AW4.nextObjId) == 'undefined')
                AW4.nextObjId = 0;
            return AW4.nextObjId++;
        }

        //////////////////////////////////////////////////////////////////////////////
        // PUBLIC
        //////////////////////////////////////////////////////////////////////////////

        // The symbols to export.
        return {
            //CONSTANTS
            LS_CONTINUED_KEY: LS_CONTINUED_KEY,
            LS_CONNECT_APP_ID: LS_CONNECT_APP_ID,
            SDK_LOCATION: SDK_LOCATION,
            DRIVE_API: DRIVE_API,
            FASP_API: FASP_API,
            CURRENT_API: CURRENT_API,
            SESSION_ID: SESSION_ID,
            SESSION_KEY: SESSION_KEY,
            OS: OS,
            BROWSER: BROWSER,
            //FUNCTIONS
            getInitUrl: getInitUrl,
            versionLessThan: versionLessThan,
            checkVersionException: checkVersionException,
            addVersionException: addVersionException,
            createError: createError,
            generateUuid: generateUuid,
            generateRandomStr: generateRandomStr,
            encrypt:aesencrypt,
            decrypt:aesdecrypt,
            launchConnect: launchConnect,
            parseJson: parseJson,
            getFullURI: getFullURI,
            utoa: utoa,
            atou: atou,
            nextObjectId: nextObjectId
        };
    })();

}

AW4.XMLhttpRequestImplementation = function() {
    //options
    //{sdkLocation: , callback:}
    var init = function(options) {
        //simple http doesn't need initialization or the connect SDK location
        if (typeof options.callback === 'function') {
            options.callback();
        }
    };

    var isSupportedByBrowser = function() {
        if (getXMLHttpRequest() === null) {
            return false;
        }
        return true
    };

    var getXMLHttpRequest = function() {
        if (typeof XMLHttpRequest === "undefined") {
            XMLHttpRequest = function() {
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP.6.0");
                } catch (e) {}
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP.3.0");
                } catch (e) {}
                try {
                    return new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
                // This browser does not support XMLHttpRequest
                return null;
            };
        }
        return new XMLHttpRequest();
    };

    var httpRequest = function(method, path, data, callback, requestId, sessionId) {
        //start request
        var request = getXMLHttpRequest();
        request.onreadystatechange = function(XMLHttpRequestProgressEvent) {
            if (request.readyState != 4) {
                return;
            }
            //when ready
            if (typeof callback != 'function') {
                return;
            }
            var respToProcess = request.responseText;
            if (request.status === 200) {
                var contentType = request.getResponseHeader("Content-Type");
                if (contentType == 'application/x-aspera-encrypted'
                    && sessionId !== undefined
                    && typeof(request.responseText) != 'undefined'
                    && request.responseText.length > 0
                    && request.responseText[0] != '{') {
                    respToProcess = AW4.Utils.decrypt(request.responseText);
                    respToProcess = AW4.crypt.aesjs.utils.utf8.fromBytes(respToProcess);
                }
            }
            AW4.Logger.trace('HttpRequest processed[' + path + '] postData[' + data +
                '] status[' + request.status + '] response[' + respToProcess + ']');
            callback(request.status, respToProcess, requestId);
        }
        request.open(method, path, true);
        if(sessionId !== undefined && sessionId !== null){
            request.setRequestHeader('x-aspera-session-id', sessionId);
        }
        if (method.toUpperCase() === "GET") {
            request.send();
        } else {
            var dataToSend = data;
            if(sessionId !== null && sessionId !== undefined){
                dataToSend = AW4.Utils.encrypt(data);
            }
            request.send(dataToSend);
        }
        return null;
    };

    ////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC
    ////////////////////////////////////////////////////////////////////////////////////////

    // The symbols to export.
    return {
        //FUNCTIONS
        isSupportedByBrowser: isSupportedByBrowser,
        init: init,
        httpRequest: httpRequest
    };
};

AW4.NPAPIrequestImplementation = function() {

    var npapiPlugin = null,
        pluginId = null,
        listenerId = null;

    /*
     * Return the mime type for NPAPI plugin.
     *
     * @return {string} The mime-type for the NPAPI plugin
     */
    function mimeType() {
        return 'application/x-aspera-web';
    }

    /*
     * Check if the browser supports aspera NPAPI plugin.
     *
     * @return {bool} True if the browser supports the aspera NPAPI plugin.
     */
    function isSupportedByBrowser() {
        if (AW4.Utils.BROWSER.IE || AW4.Utils.BROWSER.SAFARI && !AW4.Utils.BROWSER.SAFARI_NO_NPAPI) {
            return true;
        }
        return false;
    }

    /*
     * Create the NPAPI plugin <object> element as a child of the DOM element
     * given (if exists)
     *
     * @param {string} initializeTimeout [[AW4.Connect]] instantiation option
     */
    function createNPAPIPlugin(initializeTimeout) {
        var wrapperDiv = document.getElementById(listenerId);
        if (wrapperDiv == null) {
            wrapperDiv = document.createElement('div');
            wrapperDiv.setAttribute('id', listenerId);
            wrapperDiv.setAttribute('style', "display:inline-block;height:1px;width:1px;");
        } else {
            //Remove all elements of the wrapper
            while (wrapperDiv.firstChild) {
                wrapperDiv.removeChild(wrapperDiv.firstChild);
            }
        }

        npapiPlugin = document.createElement('object');
        npapiPlugin.setAttribute('name', pluginId);
        npapiPlugin.setAttribute('id', pluginId);
        npapiPlugin.setAttribute('type', mimeType());
        npapiPlugin.setAttribute('width', 1);
        npapiPlugin.setAttribute('height', 1);

        var timeoutParam = document.createElement('param');
        timeoutParam.setAttribute('name', 'connect-launch-wait-timeout-ms');
        timeoutParam.setAttribute('value', initializeTimeout);
        npapiPlugin.appendChild(timeoutParam);

        wrapperDiv.appendChild(npapiPlugin);
        document.body.appendChild(wrapperDiv);
    };

    /*
     * Called to initialize the plugin, it creates a new instance by appending an
     * <object> element to the DOM and runs the callback with the status
     */
    function initNPAPIPlugin(options) {
        var onLoadCallback = options.callback || function(){};
        try {
            if (!isSupportedByBrowser()) {
                //Browser does not support Netscape Plugin API
            } else if (npapiPlugin == null) {
                if ((AW4.Utils.BROWSER.IE && (new ActiveXObject("Aspera.AsperaWebCtrl.1"))) ||
                    navigator.mimeTypes[mimeType()] !== undefined)
                {
                    listenerId = options.containerId;
                    pluginId = options.pluginId;
                    createNPAPIPlugin(options.initializeTimeout);
                    /* Safari needs a timeout to finish loading the plugin
                     * Firefox if prompts user to allow plugin will take as much as
                     * the user takes to allow the plugin to initialize the object,
                     * so we just put an interval and keep trying until the object is
                     * initialized and has the expected call
                     */
                    var npapiWaitPluginLoadedID = setInterval(function() {
                        if (!npapiPlugin || !npapiPlugin.queryBuildVersion) {
                            return null;
                        }
                        clearInterval(npapiWaitPluginLoadedID);
                        //Check version is correct
                        if (AW4.Utils.versionLessThan(npapiPlugin.queryBuildVersion(), '3.6')) {
                            npapiPlugin = null;
                            onLoadCallback(AW4.Connect.STATUS.FAILED);
                        } else {
                            onLoadCallback(AW4.Connect.STATUS.RUNNING);
                        }
                    }, 500);
                } else {
                    //If plugin is still null, it means it is not installed
                    if (npapiPlugin == null) {
                        onLoadCallback(AW4.Connect.STATUS.FAILED);
                    }
                }
            }
        } catch (error) {
            //IE 10 ActiveXObject instantiation error recovery
            onLoadCallback(AW4.Connect.STATUS.FAILED);
        }
        return null;
    };

    /*
     * Place a request for Connect
     *
     * @param {string} method GET or POST
     * @param {string} path URL path
     * @param {string} data Payload to send with the request
     * @param {function} callback Function to be called when the request has finished
     * @param {int} requestId Identifier that needs to be returned when calling the given callback
     */
    function httpRequest(method, path, data, callback, requestId) {
        if (npapiPlugin == null)
            return;
        var requestCallback = function(data) {
            //Parse data to find out if an error ocurred
            var parsedData = AW4.Utils.parseJson(data);
            if (typeof parsedData.error !== 'undefined') {
                callback(parsedData.error.code, data, requestId);
            } else {
                callback(200, data, requestId);
            }
        };
        //NPAPI plugin doesn't accept null data even if it is a GET request
        if (data == null) {
            data = "";
        }
        npapiPlugin.httpRequestImplementation(method, path, data, requestCallback);
        return null;
    };

    // The symbols to export.
    return {
        //FUNCTIONS
        isSupportedByBrowser: isSupportedByBrowser,
        init: initNPAPIPlugin,
        httpRequest: httpRequest
    };
};

AW4.PPAPIrequestImplementation = function() {

    var idCallbackHash = {},
        nextId = 0,
        pluginId = null,
        naclModule = null,
        onLoadCallback = null;

    /*
     * Return the mime type for NaCl plugin.
     *
     * @return {string} The mime-type for the pnacl plugin
     */
    function mimeType() {
        return 'application/x-pnacl';
    }

    /*
     * Check if the browser supports NaCl plugins.
     *
     * @return {bool} True if the browser supports the pnacl plugin
     */
    function isSupportedByBrowser() {
        return navigator.mimeTypes[mimeType()] !== undefined;
    }

    /*
     * Add the default "load" and "message" event listeners to the element with
     * id "listener".
     *
     * The "load" event is sent when the module is successfully loaded. The
     * "message" event is sent when the naclModule posts a message using
     * PPB_Messaging.PostMessage() (in C) or pp::Instance().PostMessage() (in
     * C++).
     */
    function createListenerDiv(listenerId) {
        var listenerDiv = document.getElementById(listenerId);
        if (listenerDiv == null) {
            listenerDiv = document.createElement('div');
            listenerDiv.setAttribute('id', listenerId);
            listenerDiv.setAttribute('style', "display:inline-block;height:1px;width:1px;");
        } else {
            //Remove all elements of the wrapper
            while (listenerDiv.firstChild) {
                listenerDiv.removeChild(listenerDiv.firstChild);
            }
        }
        return listenerDiv;
    }

    /*
     * Add the default "load" and "message" event listeners to the element with
     * id "listener".
     *
     * The "load" event is sent when the module is successfully loaded. The
     * "message" event is sent when the naclModule posts a message using
     * PPB_Messaging.PostMessage() (in C) or pp::Instance().PostMessage() (in
     * C++).
     */
    function attachDefaultListeners(listenerDiv) {
        listenerDiv.addEventListener('load', moduleDidLoad, true);
        listenerDiv.addEventListener('message', handleMessage, true);
        listenerDiv.addEventListener('error', handleError, true);
        listenerDiv.addEventListener('crash', handleCrash, true);
    }

    /*
     * Create the Native Client <embed> element as a child of the DOM element
     * given (if exists)
     *
     * @param {string} URL To the SDK location
     */
    function createNaClModule(URL) {
        var moduleEl = document.createElement('embed');
        moduleEl.setAttribute('name', pluginId);
        moduleEl.setAttribute('id', pluginId);
        moduleEl.setAttribute('path', 'plugin/chrome');
        moduleEl.setAttribute('src', URL.replace(/\/$/, '') + '/plugin/chrome/url_loader.nmf');
        moduleEl.setAttribute('type', mimeType());
        moduleEl.setAttribute('style', "display:inline-block;height:1px;width:1px;");
        return moduleEl;
    }

    /*
     * Called when the NaCl module fails to load.
     *
     * This event listener is registered in createNaClModule above.
     */
    function handleError(event) {
        // We can't use common.naclModule yet because the module has not been
        // loaded.
    }

    /*
     * Called when the Browser can not communicate with the Module
     *
     * This event listener is registered in attachDefaultListeners above.
     */
    function handleCrash(event) {
    }

    /*
     * Called when the NaCl module is loaded.
     *
     * This event listener is registered in attachDefaultListeners above.
     */
    function moduleDidLoad() {
        naclModule = document.getElementById(pluginId);
        onLoadCallback();
    }

    /*
     * Called when the NaCl module sends a message to JavaScript (via
     * PPB_Messaging.PostMessage())
     *
     * This event listener is registered in createNaClModule above.
     *
     * @param {Event} message_event A message event. message_event.data contains
     *     the data sent from the NaCl module.
     */
    function handleMessage(message_event) {
        if (typeof message_event.data === 'object') {
            //Call back with the response retrieved from the server
            var callbackId = message_event.data.id;
            if (typeof idCallbackHash[callbackId] === 'undefined') {
                return;
            }
            var callback = idCallbackHash[callbackId].callback;
            var requestId = idCallbackHash[callbackId].id;
            if (!callback) {
                return;
            }
            var status = message_event.data.status;
            var response = message_event.data.response;
            callback(status, response, requestId);
            //remove the callback since we don't need anymore
            delete idCallbackHash[callbackId];
            return;
        }
        //The PPAPI plugin gave an invalid response
    }

    /*
     * Called to initialize the plugin, it creates a new instance by appending an
     * <embed> element to the DOM and runs the callback with the status
     */
    function initPPAPIPlugin(options) {
        if (!isSupportedByBrowser()) {
            //Browser does not support NaCl (pnacl), or NaCl is disabled
        } else if (naclModule == null) {
            //Register onLoadCallback
            onLoadCallback = options.callback || function(){};
            pluginId = options.pluginId;
            var parent = createListenerDiv(options.containerId);
            attachDefaultListeners(parent);
            var NaClModule = createNaClModule(options.sdkLocation);
            // The <EMBED> element is wrapped inside a <DIV>, which has both a 'load'
            // and a 'message' event listener attached.  This wrapping method is used
            // instead of attaching the event listeners directly to the <EMBED> element
            // to ensure that the listeners are active before the NaCl module 'load'
            // event fires.
            parent.appendChild(NaClModule);
            document.body.appendChild(parent);
        }
    }

    /*
     * Place a request for Connect
     *
     * @param {string} method GET or POST
     * @param {string} path URL path
     * @param {string} data Payload to send with the request
     * @param {function} callback Function to be called when the request has finished
     * @param {int} requestId Identifier that needs to be returned when calling the given callback
     */
    function httpRequest(method, path, data, callback, requestId) {
        if (naclModule == null)
            return;
        var callbackId = nextId++;
        var callbackInfo = {id: requestId, callback: callback};
        idCallbackHash[callbackId] = callbackInfo;
        naclModule.postMessage({id: callbackId, method: method, url: path, data: data});
    }

    // The symbols to export.
    return {
        //FUNCTIONS
        isSupportedByBrowser: isSupportedByBrowser,
        init: initPPAPIPlugin,
        httpRequest: httpRequest
    };

};

AW4.RequestHandler = function() {
    ////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////

    var
        //The two possible http requests
        HTTP_METHOD = {
            GET : "GET",
            POST : "POST"
        },
        //ALL the status in which connect can be
        STATUS = {
            INITIALIZING : 0,
            RETRYING : 1,
            RUNNING : 2,
            FAILED : 3,
            STOPPED : 4,
            WAITING : 5,
            OUTDATED : 6
        },
        //The port in which we are going to look for connect first
        DEFAULT_PORT = AW4.Utils.CURRENT_API === AW4.Utils.FASP_API?43003:44003,
        //Localhost value for the requests
        LOCALHOST = "https://local.connectme.us:",
        //Controls how many ports we want to search from the DEFAULT_PORT
        MAX_PORT_SEARCH = 10,
        MAX_POLLING_ERRORS = 3,
        DEFAULT_VERSION_PREFIX = "/v6",
        // Help initialize Connect quicker by using sessionStorage
        SESSION_LASTKNOWN_ID = 'aspera-last-known-session-id',
        SESSION_LASTKNOWN_KEY = 'aspera-last-known-session-key',
        SESSION_LASTKNOWN_PORT = 'aspera-last-known-port';

    //Current internal connect status
    AW4.RequestHandler.connectStatusInternal = null;

    ////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE VARS
    ////////////////////////////////////////////////////////////////////////////////////////

    var
        //Technology we are going to use to make the http requests
        requestImplementation = null,
        //Current connect status
        connectStatus = null,
        //Port in which connect is listening
        connectPort = DEFAULT_PORT,
        //Position in which we are going to store the callbacks for a requests
        nextId = 0,
        //Hash in which we are going to store the callbacks for the requests
        idCallbackHash = {},
        //Array in which we are going to store all the requests that cannot be processed at this time
        requestQueue = [],
        //Listeners for connect status
        statusListener = null,
        //Controls the time between ports rescan
        scanRetryTimeValues = [0,1],
        //Helper to keep track of multiple instances
        objectId = AW4.Utils.nextObjectId(),
        apiVersionPrefix = DEFAULT_VERSION_PREFIX,
        minVersion = '',
        waitReadyTimer = null,
        //Track number of polling errors for debounce
        pollingRequestErrors = 0,
        //Avoid XHR errors when page is being unloaded (navigating to new location)
        windowUnloading = false;

    ////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////////////////////////////////

    var processQueue = function() {
        //process all pending requests
        while (requestQueue.length > 0) {
            var requestInfo = requestQueue.pop();
            // NOTE: a request could be queued before the correct port is identified, use the current port instead
            var fullURL = LOCALHOST + connectPort + apiVersionPrefix + requestInfo.path;
            if (apiVersionPrefix == '/v5') { // session id not supported
                requestImplementation.httpRequest(requestInfo.method, fullURL, requestInfo.data, handleResponse, requestInfo.id);
            } else {
                requestImplementation.httpRequest(requestInfo.method, fullURL, requestInfo.data, handleResponse, requestInfo.id, AW4.Utils.SESSION_ID);
            }
        }
        return null;
    };

    var requestStatusString = function(status) {
        if (status == STATUS.INITIALIZING)
            return "initializing";
        if (status == STATUS.RETRYING)
            return "retrying";
        if (status == STATUS.RUNNING)
            return "running";
        if (status == STATUS.FAILED)
            return "failed";
        if (status == STATUS.STOPPED)
            return "stopped";
        if (status == STATUS.WAITING)
            return "waiting";
        if (status == STATUS.OUTDATED)
            return "outdated";
        return "unknown";
    };

    /*
     * ##### Format for `listener`
     *
     *      function(eventType, data) { ... }
     */
    var changeConnectStatus = function(newConnectStatus) {
        AW4.Logger.debug('[' + objectId + '] Request handler status changing from[' + requestStatusString(connectStatus)
            + '] to[' + requestStatusString(newConnectStatus) + ']');
        connectStatus = newConnectStatus;
        if (connectStatus == STATUS.RUNNING)
            processQueue();
        if (statusListener !== null)
            statusListener(connectStatus);
        return null;
    };

    var stopWaitReady = function() {
        if (waitReadyTimer != null) {
            clearInterval(waitReadyTimer);
            waitReadyTimer = null;
        }
    };

    var waitReadyCallback = function(httpCode, response, requestId) {
        // NOTE: This state only applies to /v6 api
        delete idCallbackHash[requestId];
        if (connectStatus != STATUS.WAITING) {
            stopWaitReady();
            return null;
        }
        if (httpCode == 200) {
            stopWaitReady();
            if (typeof sessionStorage != 'undefined') {
                sessionStorage.setItem(SESSION_LASTKNOWN_ID, AW4.Utils.SESSION_ID);
                sessionStorage.setItem(SESSION_LASTKNOWN_KEY, AW4.Utils.SESSION_KEY);
                sessionStorage.setItem(SESSION_LASTKNOWN_PORT, connectPort);
            }
            changeConnectStatus(STATUS.RUNNING);
        }
        return null;
    };

    var enterWaitReady = function() {
        if (apiVersionPrefix == '/v5') {
            AW4.Logger.error('waitReady not supported for v5');
            return;
        }

        var waitReady = function() {
            if (connectStatus != STATUS.WAITING) {
                stopWaitReady();
                return;
            }
            var requestId = nextId++;
            var method = HTTP_METHOD.GET;
            var path = "/connect/info/ready";
            var fullURL = LOCALHOST + connectPort + apiVersionPrefix + path;
            var requestInfo = {id: requestId, method: method, port: connectPort, path: path, data: null, callbacks: null};
            idCallbackHash[requestId] = requestInfo;

            requestImplementation.httpRequest(method, fullURL, null, waitReadyCallback, requestId, AW4.Utils.SESSION_ID);
        };

        stopWaitReady();
        changeConnectStatus(STATUS.WAITING);
        if (waitReadyTimer == null) {
            waitReadyTimer = setInterval(waitReady, 1000);
        }
        waitReady();
    };

    var checkVersionCallback = function(httpCode, response, requestId) {
        delete idCallbackHash[requestId];
        if (httpCode == 200) {
            var parsedResponse = AW4.Utils.parseJson(response);
            var hasError = typeof parsedResponse.error != 'undefined';
            if (hasError) {
                AW4.Logger.error('Failed to parse version response: ' + response);
                return;
            }
            AW4.connectVersion = parsedResponse.version;
        }

        if (connectStatus == STATUS.OUTDATED && !AW4.Utils.checkVersionException()) {
            return;
        }

        // Adjust API compatibility. v6 API was introduced in 3.8
        if (AW4.Utils.versionLessThan(AW4.connectVersion, '3.8.0')) {
            if (apiVersionPrefix != '/v5') {
                AW4.Logger.debug('Falling back to v5 API');
                apiVersionPrefix = '/v5';
            }
        }

        if (!AW4.Utils.checkVersionException()) {
            if (minVersion == '' || AW4.Utils.versionLessThan(minVersion, AW4.MIN_SECURE_VERSION)) {
                minVersion = AW4.MIN_SECURE_VERSION;
            }
            if (AW4.Utils.versionLessThan(AW4.connectVersion, minVersion)) {
                changeConnectStatus(STATUS.OUTDATED);
                if (apiVersionPrefix != '/v5') { // 3.7.4 cannot update
                    // Trigger update interface in Connect
                    var requestId = nextId++;
                    var method = HTTP_METHOD.POST;
                    var path = "/connect/update/require";
                    var fullURL = LOCALHOST + connectPort + apiVersionPrefix + path;
                    var postData = { min_version: minVersion, sdk_location: AW4.Utils.SDK_LOCATION};
                    var requestInfo = {id: requestId, method: method, port: connectPort, path: path, data: null, callbacks: null};
                    idCallbackHash[requestId] = requestInfo;
                    requestImplementation.httpRequest(method, fullURL, JSON.stringify(postData), null, requestId, AW4.Utils.SESSION_ID);
                }
                return;
            }
        }

        // If we got here, we passed all version requirements/exceptions
        if (apiVersionPrefix == '/v5') {
            changeConnectStatus(STATUS.RUNNING);
            return;
        }

        // v6 API requires session id to be ready
        // Note: Do not set the state to running early because it starts processing queued requests
        if (connectStatus != STATUS.RUNNING) {
            enterWaitReady();
        }
    };

    var checkVersion = function() {
        var requestId = nextId++;
        var method = HTTP_METHOD.GET;
        var path = "/connect/info/version";
        var apiPrefix =  '/v5'; // Check version with minimum supported api level
        var fullURL = LOCALHOST + connectPort + apiPrefix + path;
        var requestInfo = {id: requestId, method: method, port: connectPort, path: path, data: null, callbacks: null};
        idCallbackHash[requestId] = requestInfo;

        requestImplementation.httpRequest(HTTP_METHOD.GET, fullURL, null, checkVersionCallback, requestId, null);
    };

    var iteratePortsCallback = function(httpCode, response, requestId) {
        //retrieve wanted value (copy because primitive) and remove request
        var checkedPort = idCallbackHash[requestId].port;
        delete idCallbackHash[requestId];

        //check always if we have found connect, if so stop searching
        if (connectStatus == STATUS.RUNNING || connectStatus == STATUS.STOPPED) {
            return null;
        }

        if (httpCode == 200) {
            connectPort = checkedPort;
            checkVersion();
            return null;
        }
        if (checkedPort === DEFAULT_PORT) {
            //Check the rest of the ports
            for (var port = DEFAULT_PORT + 1; port < (DEFAULT_PORT + MAX_PORT_SEARCH); port++) {
                var currentRequestId = nextId++;
                var method = HTTP_METHOD.GET;
                var path = "/connect/info/ping";
                var apiPrefix = '/v5'; // Check ping using minimum supported api level
                var fullURL = LOCALHOST + port + apiPrefix + path;
                var requestInfo = {id: currentRequestId, method: method, port: port, path: path, data: null, callbacks: null};
                idCallbackHash[currentRequestId] = requestInfo;

                requestImplementation.httpRequest(method, fullURL, null, iteratePortsCallback, currentRequestId, null);
            }
        }
        return null;
    };

    var iteratePorts = function(firstRun) {
        //check always if we have found connect or stopped the requests, if so stop searching
        if (connectStatus == STATUS.RUNNING || connectStatus == STATUS.STOPPED) {
            return null;
        } else if (connectStatus == STATUS.INITIALIZING && firstRun) {
            AW4.Utils.launchConnect();
            changeConnectStatus(STATUS.RETRYING);
        }

        //Set next ping request, we scale back using an exponential function (Fibonacci sequence)
        var retryTimeS = scanRetryTimeValues[0] + scanRetryTimeValues[1];
        setTimeout(iteratePorts, retryTimeS * 1000);
        scanRetryTimeValues[0] = scanRetryTimeValues[1];
        scanRetryTimeValues[1] = retryTimeS;

        var requestId = nextId++;
        var method = HTTP_METHOD.GET;
        var path = "/connect/info/ping";
        var apiPrefix =  '/v5'; // Check ping using minimum supported api level
        var fullURL = LOCALHOST + DEFAULT_PORT + apiPrefix + path;
        var requestInfo = {id: requestId, method: method, port: DEFAULT_PORT, path: path, data: null, callbacks: null};
        idCallbackHash[requestId] = requestInfo;

        requestImplementation.httpRequest(method, fullURL, null, iteratePortsCallback, requestId, null);
        return null;
    };

    var handleResponse = function(httpCode, response, requestId) {
        if (connectStatus == STATUS.STOPPED || windowUnloading) {
            AW4.Logger.debug('Connect stopped or page unloading. Skipping xhr processing.');
            return null;
        }

        var requestInfo = idCallbackHash[requestId];
        if (!requestInfo) {
            //We shouldn't reach this point
            //Received response from server for which there is no callback
            return;
        }
        //connection error (either wrong port or connect not running)
        if (httpCode == 0) {
            //Avoid excessive re-launch related to polling failures
            //Safari causes CORS failures when new page navigation starts
            if (connectStatus == STATUS.RUNNING && pollingRequestErrors < MAX_POLLING_ERRORS
                && requestInfo.path == '/connect/transfers/activity') {
                pollingRequestErrors++;
                requestInfo.callbacks.error({});
                return;
            }
            //Connect must be running, please start it and reload the page
            if (connectStatus == STATUS.INITIALIZING || connectStatus == STATUS.RUNNING) {
                //change status, relaunch connect, search for connect
                changeConnectStatus(STATUS.RETRYING);
                AW4.Utils.launchConnect();
                iteratePorts();
            }
            //This was a client request, so queue it until we restablish connection with the server
            requestQueue.push(requestInfo);
            return null;
        } else {
            if (connectStatus != STATUS.RUNNING) {
                changeConnectStatus(STATUS.RUNNING);
            }
            var parsedResponse = AW4.Utils.parseJson(response);
            if (httpCode == 200 && typeof parsedResponse.error === 'undefined') {
                pollingRequestErrors = 0;
                //Call back with the response retrieved from connect if parsing is ok
                var callback = requestInfo.callbacks.success;
                callback(parsedResponse);
            } else {
                //Call back with error
                var callback = requestInfo.callbacks.error;
                callback(parsedResponse);
            }
            //remove object
            delete idCallbackHash[requestId];
            return null;
        }
        return null;
    };

    // 2018-06-30 Only Safari 12 has trouble with failing XHR requests when page navigation begins
    if (AW4.Utils.BROWSER.SAFARI_NO_NPAPI) {
        window.addEventListener('beforeunload', function() {
            // TODO: Look into transitioning to STATUS.STOPPED
            AW4.Logger.trace('[' + objectId + '] Page being unloaded.');
            windowUnloading = true;
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC
    ////////////////////////////////////////////////////////////////////////////////////////

    var addStatusListener = function(callback) {
        statusListener = callback;
    };

    var processClientRequest = function(method, path, data, sessionId, callbacks) {
        if (connectStatus == STATUS.STOPPED) {
            return null;
        }
        if (callbacks == ""|| callbacks == null || typeof callbacks ==='undefined') {
            callbacks = {};
        }
        //Prepare callbacks
        if (typeof callbacks.success !== 'function') {
            callbacks.success = function(){};
        }
        if (typeof callbacks.error !== 'function') {
            callbacks.error = function(){};
        }

        var requestId = nextId++;
        var requestInfo = {id: requestId, method: method, port: connectPort, path: path, data: data, callbacks: callbacks};
        idCallbackHash[requestId] = requestInfo;

        //if connect is not ready, queue the request
        if (connectStatus != STATUS.RUNNING) {
            requestQueue.push(requestInfo);
            return null;
        }

        //construct final URL
        var fullURL = LOCALHOST + connectPort + apiVersionPrefix + path;
        if (apiVersionPrefix == '/v5') { // session id not supported
            requestImplementation.httpRequest(method, fullURL, data, handleResponse, requestId, null);
        } else {
            requestImplementation.httpRequest(method, fullURL, data, handleResponse, requestId, sessionId);
        }
        return null;
    };

    ////////////////////////////////////////////////////////////////////////////////////////
    // INITIALIZE PLUGINS
    ////////////////////////////////////////////////////////////////////////////////////////

    var fastInitCallback = function(httpCode, response, requestId) {
        delete idCallbackHash[requestId];
        if (httpCode == 200) {
            AW4.Logger.debug('Connect fast init success');
            changeConnectStatus(STATUS.RUNNING);
            checkVersion();
        } else {
            iteratePorts(true);
        }
    };

    var fastInit = function() {
        // Help reduce the number of protocol handler calls
        // Check if we can restablish connection quickly
        if (typeof sessionStorage == 'undefined') { // Min support IE 8
            return false;
        }
        var isInvalid = function(x) {
            return x === "" || x === null || typeof x === "undefined";
        };

        var id = sessionStorage.getItem(SESSION_LASTKNOWN_ID);
        if (isInvalid(id)) {
            id = AW4.Utils.SESSION_ID;
            sessionStorage.setItem(SESSION_LASTKNOWN_ID, AW4.Utils.SESSION_ID);
        } else {
            AW4.Utils.SESSION_ID = id;
        }

        var key = sessionStorage.getItem(SESSION_LASTKNOWN_KEY);
        if (isInvalid(key)) {
            key = AW4.Utils.SESSION_KEY;
            sessionStorage.setItem(SESSION_LASTKNOWN_KEY, AW4.Utils.SESSION_KEY);
        } else {
            AW4.Utils.SESSION_KEY = key;
        }

        var port = sessionStorage.getItem(SESSION_LASTKNOWN_PORT);
        if (isInvalid(port)) {
            return false;
        }
        connectPort = port;

        AW4.Logger.debug('Connect fast init');
        var requestId = nextId++;
        var method = HTTP_METHOD.GET;
        var path = "/connect/info/ready";
        var fullURL = LOCALHOST + port + DEFAULT_VERSION_PREFIX + path;
        var requestInfo = {id: requestId, method: method, port: connectPort, path: path, data: null, callbacks: null};
        idCallbackHash[requestId] = requestInfo;
        requestImplementation.httpRequest(method, fullURL, null, fastInitCallback, requestId, AW4.Utils.SESSION_ID);
        return true;
    };

    var init = function(options) {
        minVersion = options.minVersion;
        //Change connect status to initailizing
        changeConnectStatus(STATUS.INITIALIZING);
        //Find the request implementation that is optimal for this environment
        requestImplementation = (function () {
            // Use NPAPI if available
            var requestImpl = new AW4.NPAPIrequestImplementation();
            if (requestImpl.isSupportedByBrowser()) {
                return requestImpl;
            }
            // Next check deprecated (10.6) and use PPAPI
            if (AW4.Utils.OS.MAC_LEGACY && AW4.Utils.BROWSER.CHROME) {
                DEFAULT_PORT = AW4.Utils.CURRENT_API === AW4.Utils.FASP_API?33003:34003;
                LOCALHOST = "http://127.0.0.1:",
                    requestImpl = new AW4.PPAPIrequestImplementation();
                if (requestImpl.isSupportedByBrowser()) {
                    return requestImpl;
                }
            }
            // Use default browser native HTTP requests
            requestImpl = new AW4.XMLhttpRequestImplementation();
            if (requestImpl.isSupportedByBrowser()) {
                return requestImpl;
            }
            return "This browser is not supported";
        }());
        if (typeof requestImplementation === 'string') {
            return AW4.Utils.createError(-1, requestImplementation);
        }

        //Initialize the request implementations
        var initializedCallback = function() {
            //Set timeout to decide that the launch of connect has failed and that further actions
            //are required
            var initializeTimeoutCallback = function() {
                if (connectStatus != STATUS.RUNNING && connectStatus != STATUS.OUTDATED) {
                    changeConnectStatus(STATUS.FAILED);
                }
            };
            setTimeout(initializeTimeoutCallback, options.initializeTimeout);

            // INITIALIZE CONNECT
            if (!fastInit()) {
                iteratePorts(true);
            }
        };

        var initializationOptions = {
            pluginId: options.pluginId,
            containerId: options.containerId,
            initializeTimeout: options.initializeTimeout,
            sdkLocation: options.sdkLocation,
            callback: initializedCallback
        };
        requestImplementation.init(initializationOptions);
        return null;
    };

    var stopRequests = function() {
        connectStatus = STATUS.STOPPED;
        return true;
    };

    return {
        //CONSTANTS
        HTTP_METHOD: HTTP_METHOD,
        STATUS: STATUS,
        //FUNCTIONS
        init: init,
        start: processClientRequest,
        addStatusListener: addStatusListener,
        stopRequests: stopRequests
    }
};

AW4.MIN_SECURE_VERSION = "3.8.0";

/**
 * == API ==
 **/

/** section: API
 * AW4
 *
 * The Aspera Web namespace.
 **/

/** section: API
 * class AW4.Connect
 *
 * The [[AW4.Connect]] class contains all the Connect API methods.
 **/

/**
 * new AW4.Connect([options])
 * - options (Object): Configuration parameters for the plug-in.
 *
 * Creates a new [[AW4.Connect]] object.
 *
 * ##### Options
 *
 * 1. `connectLaunchWaitTimeoutMs` (`Number`):
 *     How long to wait in milliseconds for Aspera Connect to launch, if we reach
 *     this timeout without a successful request to connect, we will go into FAILED
 *     status.
 *     `5000`.
 * 2. `id` (`String`):
 *     The DOM `id` of the plug-in object to be inserted. Default:
 *     `"aspera-web"`.
 * 3. `containerId` (`String`):
 *     The DOM `id` of an existing element to insert the plug-in element into
 *     (replacing its contents). If not specified, the plug-in is appended to
 *     the document body. Note that the plug-in must not be hidden in order to
 *     be loaded.
 * 4. `sdkLocation` (`String`):
 *     Optional. Specifies custom SDK location to check for Connect installers.
 *     It has to be in the following format:`//domain/path/to/connect/sdk`.
 *     The default location, if not specified is the unchanging Aspera location
 *     for the current SDK: `//d3gcli72yxqn2z.cloudfront.net/connect/v4`. If you
 *     are hosting your own SDK, and not using the Aspera one, then you must
 *     provide the location to your copy of the SDK. This points to the /v4/ folder
 *     of the provided SDK. This folder contains a number of items including JavaScript
 *     API and installer code, installers for all platforms, and documentation.
 *     The URL provided needs to be in the same level of security as the web page
 *     (HTTP/HTTPS), HTTPS preferred.
 * 5. `pollingTime` (`Number`):
 *     How often in milliseconds we want to get updates of the transfer's status
 *     Default: `2000`.
 * 6. `minVersion` (`String`):
 *     Minimum version of connect required by the web application in order to work.\
 *     Format:\
 *     `3.6.0`
 * 7. `dragDropEnabled` (`Boolean`):
 *     Enable drag and drop of files/folders into the browser
 *     Default: \
 *     `false`.
 *
 * ##### Example
 *
 * The following JavaScript creates an [[AW4.Connect]] object to interface with
 * Aspera Connect on the client computer. This code should be executed on
 * document ready.
 *
 *     var asperaConnect = new AW4.Connect();
 *
 **/
AW4.Connect = function(options) {

    if (isNullOrUndefinedOrEmpty(options)) {
        options = {};
    }

    ////////////////////////////////////////////////////////////////////////////
    // Public constants
    ////////////////////////////////////////////////////////////////////////////

    /**
     * AW4.Connect.HTTP_METHOD -> Object
     *
     * Http method types:
     *
     * 1. `AW4.Connect.HTTP_METHOD.GET` (`"GET"`)
     * 2. `AW4.Connect.HTTP_METHOD.POST` (`"POST"`)
     **/
    AW4.Connect.HTTP_METHOD = {
        GET : "GET",
        POST : "POST",
        DELETE: "DELETE",
        REVERT: "REVERT"

    };

    /**
     * AW4.Connect.STATUS -> Object
     *
     * Event types:
     *
     * 1. `AW4.Connect.STATUS.INITIALIZING` (`INITIALIZING`)
     * 2. `AW4.Connect.STATUS.RETRYING` (`RETRYING`)
     * 3. `AW4.Connect.STATUS.RUNNING` (`RUNNING`)
     * 4. `AW4.Connect.STATUS.OUTDATED` (`OUTDATED`)
     * 5. `AW4.Connect.STATUS.FAILED` (`FAILED`)
     **/
    AW4.Connect.STATUS = {
        INITIALIZING : "INITIALIZING",
        RETRYING : "RETRYING",
        RUNNING : "RUNNING",
        OUTDATED : "OUTDATED",
        FAILED : "FAILED"
    };

    /**
     * AW4.Connect.EVENT -> Object
     *
     * Event types:
     *
     * 1. `AW4.Connect.EVENT.ALL` (`"all"`)
     * 2. `AW4.Connect.EVENT.TRANSFER` (`"transfer"`)
     * 2. `AW4.Connect.EVENT.DROP` (`"drop"`)
     * 3. `AW4.Connect.EVENT.STATUS` (`"status"`)
     **/
    AW4.Connect.EVENT = {
        ALL : "all",
        TRANSFER : "transfer",
        STATUS : "status"
    };

    /**
     * AW4.Connect.TRANSFER_STATUS -> Object
     *
     * The possible states of a transfer, reported in the `status` field:
     *
     * 1. `AW4.Connect.TRANSFER_STATUS.CANCELLED`: The user stopped the transfer.
     * 2. `AW4.Connect.TRANSFER_STATUS.COMPLETED`: The transfer finished
     * successfully.
     * 3. `AW4.Connect.TRANSFER_STATUS.FAILED`: The transfer had an error.
     * 4. `AW4.Connect.TRANSFER_STATUS.INITIATING`: The transfer request was
     *  accepted; starting transfer.
     * 5. `AW4.Connect.TRANSFER_STATUS.QUEUED`: The transfer is waiting for other
     * transfers to finish. The queue is configurable in Connect.
     * 6. `AW4.Connect.TRANSFER_STATUS.REMOVED`: The user deleted the transfer.
     * 7. `AW4.Connect.TRANSFER_STATUS.RUNNING`: Transfer in progress.
     * 8. `AW4.Connect.TRANSFER_STATUS.WILLRETRY`: Transfer waiting to retry after
     * a recoverable error.
     **/
    AW4.Connect.TRANSFER_STATUS = {
        CANCELLED : "cancelled",
        COMPLETED : "completed",
        FAILED : "failed",
        INITIATING : "initiating",
        QUEUED : "queued",
        REMOVED : "removed",
        RUNNING : "running",
        WILLRETRY : "willretry"
    };

    ////////////////////////////////////////////////////////////////////////////
    // Private constants
    ////////////////////////////////////////////////////////////////////////////

    var INITIALIZE_TIMEOUT = options.connectLaunchWaitTimeoutMs || 5000,
        PLUGIN_ID = options.id || "aspera-web",
        PLUGIN_CONTAINER_ID = options.containerId || "aspera-web-container",
        SDK_LOCATION = AW4.Utils.getFullURI(options.sdkLocation) || "//d3gcli72yxqn2z.cloudfront.net/connect/v4",
        APPLICATION_ID = "",
        AUTHORIZATION_KEY = options.authorizationKey || "",
        POLLING_TIME = options.pollingTime || 2000,
        MINIMUM_VERSION = options.minVersion || "",
        DRAGDROP_ENABLED = options.dragDropEnabled || false,
        MAX_ACTIVITY_OUTSTANDING = options.maxActivityOutstanding || 2;

    AW4.Utils.CURRENT_API = AW4.Utils.FASP_API;
    AW4.Utils.SDK_LOCATION = SDK_LOCATION;

    if (typeof(AW4.connectVersion) === "undefined") {
        AW4.connectVersion = "";
    }

    // Expose the requested version to the install banner
    if (options.minVersion) {
        AW4.MIN_REQUESTED_VERSION = options.minVersion;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private variables
    ////////////////////////////////////////////////////////////////////////////

    var transferListeners = [],
        transferEventIntervalId = 0,
        transferEventIterationToken = 0,
        requestHandler = null,
        statusListeners = [],
        connectStatus = AW4.Connect.STATUS.INITIALIZING,
        objectId = AW4.Utils.nextObjectId(),
        outstandingActivityReqs = 0; // Keep track of polling requests to avoid overfilling the queue

    ////////////////////////////////////////////////////////////////////////////
    // Helper Functions
    ////////////////////////////////////////////////////////////////////////////

    /*
     * x - variable we want to check
     * @returns {Boolean} - true if the value is null, empty or undefined
     */
    function isNullOrUndefinedOrEmpty(x) {
        return x === "" || x === null || typeof x === "undefined";
    };

    /*
     * data - obj
     * @returns {Boolean} - true if the value is null, empty or undefined
     */
    function addStandardConnectSettings(data) {
        if (AUTHORIZATION_KEY.length !== 0) {
            data.authorization_key = AUTHORIZATION_KEY;
        }
        if (isNullOrUndefinedOrEmpty(data.aspera_connect_settings)) {
            data.aspera_connect_settings = {};
        }
        data.aspera_connect_settings.app_id = APPLICATION_ID;
        return data;
    };

    function connectHttpRequest(method, path, data, sessionId, callbacks) {
        if (requestHandler == null) {
            return null;
        }
        //Use our own local variable to avoid mutating user's object
        var localData = {};
        if (!isNullOrUndefinedOrEmpty(data)) {
            //5-10 times faster than JSON.parse(JSON.stringify(data))
            for (var property in data) {
                if (data.hasOwnProperty(property)) {
                    localData[property] = data[property];
                }
            }
        }
        // prepare data
        var dataStr = JSON.stringify(addStandardConnectSettings(localData));
        // start request
        requestHandler.start(method, path, dataStr, sessionId, callbacks);
        return null;
    };

    function driveHttpRequest(method, path, data, sessionId, callbacks) {
        if (requestHandler == null) {
            return null;
        }
        // prepare data
        var dataStr = JSON.stringify(data);
        // start request
        requestHandler.start(method, path, dataStr, sessionId, callbacks);
        return null;
    };

    function getAllTransfersHelper(iterationToken, callbacks) {
        //This is never supposed to happen
        if (isNullOrUndefinedOrEmpty(iterationToken)) {
            return null;
        }
        var data = {iteration_token: iterationToken};
        return connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/activity", data, AW4.Utils.SESSION_ID, callbacks);
    };

    function notifyTransferListeners(response) {
        //First update the iterate token for future requests
        transferEventIterationToken = response.iteration_token;
        //Notify the listeners
        for (var i = 0; i < transferListeners.length; i++) {
            transferListeners[i](AW4.Connect.EVENT.TRANSFER, response);
        }
    };

    function pollTranfersHelperFunction() {
        if (outstandingActivityReqs >= MAX_ACTIVITY_OUTSTANDING) {
            AW4.Logger.debug("Skipping activity request. Reached maximum number of outstanding polling requests. Configure via Connect.maxActivityOutstanding.");
            return;
        }
        outstandingActivityReqs++;
        getAllTransfersHelper(transferEventIterationToken, {
            success: function(response) {
                outstandingActivityReqs--;
                notifyTransferListeners(response);
            },
            error: function() {
                outstandingActivityReqs--;
            }
        });
    };

    function removeEventListenerHelper(listener, listenerArray) {
        var listenerFound = false;
        var index = listenerArray.indexOf(listener);
        while (index > -1) {
            listenerArray.splice(index, 1);
            listenerFound = true;
            index = listenerArray.indexOf(listener);
        }
        return listenerFound;
    };

    function isAppIdEntropyOk(appId) {
        var entropy = 0;
        var len = appId.length;
        var charFreq = Object.create(null);
        appId.split("").forEach(function (s) {
            if (charFreq[s]) {
                charFreq[s] += 1;
            } else {
                charFreq[s] = 1;
            }
        });
        for (var s in charFreq) {
            var percent = charFreq[s] / len;
            entropy -= percent * (Math.log(percent) / Math.log(2));
        };
        return entropy > 3.80;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Manage Connect Status and high level logic
    ////////////////////////////////////////////////////////////////////////////

    function notifyStatusListeners(notifyStatus) {
        for (var i = 0; i < statusListeners.length; i++) {
            var listener = statusListeners[i](AW4.Connect.EVENT.STATUS, notifyStatus);
        };
    }

    function setConnectStatus(newStatus) {
        AW4.Logger.debug('[' + objectId + '] Connect status changing from[' + connectStatus + '] to[' + newStatus + ']');
        connectStatus = newStatus;
    }

    function manageConnectStatus(newStatus) {

        //Initialize options before calling RUNNING
        if (newStatus == requestHandler.STATUS.RUNNING && DRAGDROP_ENABLED) {
            connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/file/initialize-drag-drop", null, AW4.Utils.SESSION_ID, null);
        }
        if (newStatus == requestHandler.STATUS.INITIALIZING) {
            setConnectStatus(AW4.Connect.STATUS.INITIALIZING);
        } else if (newStatus == requestHandler.STATUS.RETRYING) {
            setConnectStatus(AW4.Connect.STATUS.RETRYING);
        } else if (newStatus == requestHandler.STATUS.FAILED) {
            setConnectStatus(AW4.Connect.STATUS.FAILED);
        } else if (newStatus == requestHandler.STATUS.WAITING) {
            // No change
        } else if (newStatus == requestHandler.STATUS.OUTDATED) {
            if (connectStatus != AW4.Connect.STATUS.OUTDATED) {
                setConnectStatus(AW4.Connect.STATUS.OUTDATED);
            }
        } else {
            setConnectStatus(AW4.Connect.STATUS.RUNNING);
        }
        notifyStatusListeners(connectStatus);
    };

    this.connectHttpRequest = connectHttpRequest;
    this.driveHttpRequest = driveHttpRequest;
    this.isNullOrUndefinedOrEmpty = isNullOrUndefinedOrEmpty;

    ////////////////////////////////////////////////////////////////////////////
    // API Functions
    ////////////////////////////////////////////////////////////////////////////

    /**
     * AW4.Connect#addEventListener(type, listener) -> null | Error
     * - type (AW4.Connect.EVENT): The type of event to receive events for. See
     * below for the format.
     * - listener (Function): The function that will be called when the event
     * occurs.
     *
     * Subscribe for Aspera Web events. The first time the listener is called
     * it will receive an event for each of the transfers already displayed in
     * Connect, such that the listener will know the complete state of all transfers.
     *
     * ##### Format for `listener`
     *
     *      function(eventType, data) { ... }
     *
     * Event types ([[AW4.Connect.EVENT]]) and their associated `data`:
     *
     * 1. `TRANSFER` - [[AllTransfersInfo]]
     * 2. `STATUS` - [[AW4.Connect.STATUS]]
     *
     **/
    this.addEventListener = function(type, listener) {
        //Check the parameters
        if (typeof type !== typeof AW4.Connect.EVENT.ALL) {
            return AW4.Utils.createError(-1, "Invalid EVENT parameter");
        } else if (typeof listener !== 'function') {
            return AW4.Utils.createError(-1, "Invalid Listener parameter");
        }
        //Add the listener
        if (type == AW4.Connect.EVENT.TRANSFER || type == AW4.Connect.EVENT.ALL) {
            if (transferEventIntervalId === 0) {
                transferEventIntervalId = setInterval(pollTranfersHelperFunction, POLLING_TIME);
            }
            //Already set a function for polling the status, just add to the queue
            transferListeners.push(listener);
        }
        if (type == AW4.Connect.EVENT.STATUS || type == AW4.Connect.EVENT.ALL) {
            statusListeners.push(listener);
        }
        return null;
    };

    /**
     * AW4.Connect#authenticate(authSpec, callbacks) -> null | Error
     * - authSpec (Object): Authentication credentials
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Test authentication credentials against a transfer server.
     *
     * ##### Options for `authSpec`
     *
     * These are a subset of [[TransferSpec]].
     *
     * 1. `remote_host`
     * 2. `ssh_port`
     * 3. `remote_user`
     * 4. `remote_password`
     * 5. `token`
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.authenticate = function(authSpec, callbacks) {
        if (isNullOrUndefinedOrEmpty(authSpec)) {
            return AW4.Utils.createError(-1, "Invalid authSpec parameter");
        }
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/info/authenticate", authSpec, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#getAllTransfers(callbacks[, iterationToken]) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     * - iterationToken (String): (*optional*) If specified, return only
     * transfers that have had activity since the last call.
     *
     * *This method is asynchronous.*
     *
     * Get statistics for all transfers.
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[AllTransfersInfo]]
     *
     **/
    this.getAllTransfers = function(callbacks, iterationToken) {
        if (isNullOrUndefinedOrEmpty(iterationToken)) {
            iterationToken = 0;
        }
        getAllTransfersHelper(iterationToken, callbacks);
        return null;
    };

    /**
     * AW4.Connect#getStatus() -> AW4.Connect.STATUS
     *
     * Get current status of Connect
     **/
    this.getStatus = function() {
        return connectStatus;
    };

    /**
     * AW4.Connect#initSession([applicationId]) -> Object | Error
     *  - applicationId (String): (*optional*) An ID to represent this session.
     * Transfers initiated during this session will be associated with the ID.
     * To continue a previous session, use the same ID as before. Use a unique ID
     * in order to keep transfer information private from other websites. An ID
     * is automatically generated for you if not specified (default).
     *
     * Call this method after creating the [[AW4.Connect]] object. It is mandatory to call
     * this function before making use of any other function of the API. If called more than
     * once on the same instance, it will return an error
     *
     * ##### Return format
     *
     *      {
     *        "app_id" : "APPLICATION_ID"
     *      }
     **/
    this.initSession = function(applicationId) {
        if (isNullOrUndefinedOrEmpty(APPLICATION_ID)) {
            if (isNullOrUndefinedOrEmpty(applicationId)) {
                APPLICATION_ID = localStorage.getItem(AW4.Utils.LS_CONNECT_APP_ID);
                if(isNullOrUndefinedOrEmpty(APPLICATION_ID)) {
                    APPLICATION_ID = AW4.Utils.utoa(AW4.Utils.generateUuid());
                    localStorage.setItem(AW4.Utils.LS_CONNECT_APP_ID, APPLICATION_ID);
                }
            } else {
                APPLICATION_ID = applicationId;
            }
        } else {
            return AW4.Utils.createError(-1, "Session was already initialized");
        }
        if (!isAppIdEntropyOk(APPLICATION_ID)){
            AW4.Logger.warn("WARNING: app_id field entropy might be too low.");
        }
        //Initialize requests
        var error = this.start();
        if (error == null) {
            return {"app_id" : APPLICATION_ID};
        }
        return error;
    };

    /**
     * AW4.Connect#modifyTransfer(transferId, options, callbacks) -> null
     * - transferId (String): The ID of the transfer to modify.
     * - options (Object): A subset of [[TransferSpec]]
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Change the speed of a running transfer.
     *
     * ##### `options`:
     *
     * See [[TransferSpec]] for definitions.
     *
     * 1. `rate_policy`
     * 2. `target_rate_kbps`
     * 3. `min_rate_kbps`
     * 4. `target_rate_cap_kbps`
     * 5. `lock_rate_policy`
     * 6. `lock_target_rate`
     * 7. `lock_min_rate`
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[TransferSpec]]
     **/
    this.modifyTransfer = function(transferId, options, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/modify/" + transferId, options, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#readAsArrayBuffer(options, callbacks) -> null | Error
     * - options (Object): Object with the options needed for reading the file as 64-bit encoded data.
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * ##### Options
     * 1. 'path' ('String'):
     *     Absolute path to the file we want to read the chunk from.
     *
     * ##### Object returned to success callback as parameter
     *
     *      {
     *        "type" : "image/pjpeg",
     *        "data" : "/9j/4AAQSkZ..."
     *      }
     **/
    this.readAsArrayBuffer = function(options, callbacks) {
        console.warn('AW4.Connect#readAsArrayBuffer will be deprecated in the future.');
        var params = {};
        if (!options) {
            return AW4.Utils.createError(-1, "Invalid options parameter");
        }
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/file/read-as-array-buffer/", options, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#readChunkAsArrayBuffer(options, callbacks) -> null | Error
     * - options (Object): Object with the options needed for reading a chunk
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * ##### Options
     * 1. 'path' ('String'):
     *     Absolute path to the file we want to read the chunk from.
     * 2. 'offset' ('Number'):
     *     Offset (in bytes) that we want to start reading the file.
     * 3. 'chunkSize' ('Number'):
     *     The size (in bytes) of the chunk we want.
     *
     * ##### Object returned to success callback as parameter
     *
     *      {
     *        "type" : "image/pjpeg",
     *        "data" : "/9j/4AAQSkZ..."
     *      }
     *
     **/
    this.readChunkAsArrayBuffer = function(options, callbacks) {
        console.warn('AW4.Connect#readChunkAsArrayBuffer will be deprecated in the future.');
        if (!options.path || typeof options.offset === 'undefined' || typeof options.chunkSize === 'undefined') {
            return AW4.Utils.createError(-1, "Invalid parameters");
        }
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/file/read-chunk-as-array-buffer/", options, AW4.Utils.SESSION_ID, callbacks);
        return null;
    }

    /**
     * AW4.Connect#removeEventListener([type][, listener]) -> Boolean
     * - type (AW4.Connect.EVENT): (*optional*) The type of event to stop receiving events for.
     * - listener (Function): (*optional*) The function used to subscribe in
     * [[AW4.Connect#addEventListener]].
     *
     * Unsubscribe from Aspera Web events. If `type` is not specified,
     * all versions of the `listener` with different types will be removed.
     * If `listener` is not specified, all listeners for the `type` will be
     * removed. If neither `type` nor `listener` are specified, all listeners
     * will be removed.
     *
     * ##### Return values
     *
     * 1. `true` : if we could find a listener for the parameters provided
     * 2. `false` : if we could not find a listener for the parameters provided
     **/
    this.removeEventListener = function(type, listener) {
        var listenerFound = false;

        if (typeof type === 'undefined') {
            if (transferListeners.length > 0) {
                transferListeners = [];
                listenerFound = true;
            }
            if (statusListeners.length > 0) {
                statusListeners = [];
                listenerFound = true;
            }
        } else if (typeof type !== typeof AW4.Connect.EVENT.ALL) {
            //The parameter type is actually the listener
            listenerFound = listenerFound || removeEventListenerHelper(type, transferListeners);
            listenerFound = listenerFound || removeEventListenerHelper(type, statusListeners);
        } else if (typeof listener !== 'function') {
            //The user only provided the type
            //First the TRANSFER events
            if (type === AW4.Connect.EVENT.TRANSFER || type === AW4.Connect.EVENT.ALL) {
                if (transferListeners.length > 0) {
                    transferListeners = [];
                    listenerFound = true;
                }
            }
            //Then the STATUS events
            if (type === AW4.Connect.EVENT.STATUS || type === AW4.Connect.EVENT.ALL) {
                if (statusListeners.length > 0) {
                    statusListeners = [];
                    listenerFound = true;
                }
            }
        } else {
            //The user provided both arguments
            //First the TRANSFER events
            if (type === AW4.Connect.EVENT.TRANSFER || type === AW4.Connect.EVENT.ALL) {
                listenerFound = listenerFound || removeEventListenerHelper(listener, transferListeners);
            }
            //Then the STATUS events
            if (type === AW4.Connect.EVENT.STATUS || type === AW4.Connect.EVENT.ALL) {
                listenerFound = listenerFound || removeEventListenerHelper(listener, statusListeners);
            }
        }
        if (transferListeners.length === 0) {
            clearInterval(transferEventIntervalId);
            transferEventIntervalId = 0;
        }
        return listenerFound;
    };

    /**
     * AW4.Connect#removeTransfer(transferId, callbacks) -> null
     * - transferId (String): The ID (`uuid`) of the transfer to delete.
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Remove the transfer - terminating it if necessary - from Connect.
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[TransferSpec]]
     **/
    this.removeTransfer = function(transferId, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/remove/" + transferId, null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#resumeTransfer(transferId, options, callbacks) -> null
     * - transferId (String): The ID (`uuid`) of the transfer to resume.
     * - options (Object): A subset of [[TransferSpec]]
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Resume a transfer that was stopped.
     *
     * ##### `options`:
     *
     * See [[TransferSpec]] for definitions.
     *
     * 1. `token`
     * 2. `cookie`
     * 3. `authentication`
     * 4. `remote_user`
     * 5. `remote_password`
     * 6. `content_protection_passphrase`
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[TransferSpec]]
     **/
    this.resumeTransfer = function(transferId, options, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/resume/" + transferId, options, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#setDragDropTargets(cssSelector, options, listener) -> null | Error
     * - cssSelector (String): CSS selector for drop targets
     * - options (Object): (*optional*) Drag and drop options for these targets
     * - listener (Function): Function to be called when each of the event occurs
     *
     * *This method is asynchronous.*
     *
     * Sets drag and drop options for the element given in the cssSelector. Please note that
     * dragDropEnabled option must have been set to `true` when instantiating Aspera Connect
     * object.
     *
     * ##### `options`:
     *
     * 1. `dragEnter` (`Boolean`): `true` if drag enter event should trigger the listener. Default: `false`.
     * 2. `dragOver` (`Boolean`): `true` if drag over event should trigger the listener. Default: `false`.
     * 3. `dragLeave` (`Boolean`): `true` if drag leave event should trigger the listener. Default: `false`.
     * 4. `drop` (`Boolean`): `true` if drop event should trigger the listener. Default: `true`.
     *
     *
     * ##### Fields of the object returned to the listener
     *
     * 1. `event` (`Object`): DOM Event object as implemented by the browser.
     * 2. `files` (`Object`): See [[dataTransfer]]. This is only valid on `drop` events.
     *
     **/
    this.setDragDropTargets = function(cssSelector, options, listener) {
        if (!DRAGDROP_ENABLED) {
            return AW4.Utils.createError(-1, "Drop is not enabled in the initialization " +
                "options, please instantiate Connect again with the dragDropEnabled option set to true.");
        }
        if (typeof listener !== 'function') {
            return AW4.Utils.createError(-1, "You must provide a valid listener");
        }
        if (isNullOrUndefinedOrEmpty(options)) {
            return AW4.Utils.createError(-1, "You must provide a valid options object");
        }
        var elements = document.querySelectorAll(cssSelector);
        if (elements.length == 0) {
            return AW4.Utils.createError(-1, "No valid elements for the selector given");
        }
        var dragListener = function (evt) {
            evt.stopPropagation();
            evt.preventDefault();
            listener({event: evt});
        };
        //Needed for the Drop event to be called
        var dragOverListener = function (evt) {
            evt.stopPropagation();
            evt.preventDefault();
            if (options.dragOver == true) {
                listener({event: evt});
            }
        };
        var dropListener = function (evt) {
            evt.stopPropagation();
            evt.preventDefault();
            //Prepare request and create a valid JSON object to be serialized
            var filesDropped = evt.dataTransfer.files;
            var data = {};
            data.dataTransfer = {};
            data.dataTransfer.files = [];
            for (var i = 0; i < filesDropped.length; i++) {
                var fileObject  = {
                    'lastModifiedDate' : filesDropped[i].lastModifiedDate,
                    'name'             : filesDropped[i].name,
                    'size'             : filesDropped[i].size,
                    'type'             : filesDropped[i].type
                };
                data.dataTransfer.files.push(fileObject);
            }
            //Drop helper
            var dropHelper = function (response) {
                listener({event: evt, files: response});
            };
            connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/file/dropped-files", data, AW4.Utils.SESSION_ID, {success: dropHelper});
        };
        for (var i = 0; i < elements.length; i++) {
            //Independent from our implementation
            if (options.dragEnter == true) {
                elements[i].addEventListener('dragenter', dragListener);
            }
            if (options.dragLeave == true) {
                elements[i].addEventListener('dragleave', dragListener);
            }
            if (options.dragOver == true || options.drop !== false) {
                elements[i].addEventListener('dragover', dragOverListener);
            }
            if (options.drop !== false) {
                elements[i].addEventListener('drop', dropListener);
            }
        }
        return null;
    };

    /**
     * AW4.Connect#showAbout(callbacks) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Displays the Aspera Connect "About" window.
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.showAbout = function(callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/windows/about", null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showDirectory(transferId, callbacks) -> null
     * - transferId (String): The ID (`uuid`) of the transfer to show files for.
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Open the destination directory of the transfer, using the system file
     * browser.
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.showDirectory = function(transferId, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/windows/finder/" + transferId, null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showPreferences(callbacks) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Displays the Aspera Connect "Preferences" window.
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.showPreferences = function(callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/windows/preferences", null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showSaveFileDialog(callbacks[, options]) -> null
     * - callbacks (Callbacks): On success, returns the selected file path.
     * Returns `null` if the user cancels the dialog.
     * - options (Object): (*optional*) File chooser options
     *
     * *This method is asynchronous.*
     *
     * Displays a file chooser dialog for the user to pick a "save-to" path.
     *
     * ##### `options`:
     *
     * 1. `allowedFileTypes` ([[FileFilters]]): Filter the files displayed by file
     * extension.
     * 2. `suggestedName` (`String`): The file name to pre-fill the dialog with.
     * 3. `title` (`String`): The name of the dialog window.
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[dataTransfer]]. If user canceled the dialog, it will return an empty object
     **/
    this.showSaveFileDialog = function(callbacks, options) {
        //Prepare the options object, use our own local variable to avoid mutating user's object
        var localOptions = {};
        if (isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || "";
        localOptions.suggestedName = options.suggestedName || "";
        localOptions.allowedFileTypes = options.allowedFileTypes || "";
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/windows/select-save-file-dialog/", localOptions, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showSelectFileDialog(callbacks[, options]) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     * - options (Object): (*optional*) File chooser options
     *
     * *This method is asynchronous.*
     *
     * Displays a file browser dialog for the user to select files. The select file
     * dialog call(s) may be separated in time from the later startTransfer(s) call,
     * but they must occur in the same Connect session.
     *
     * ##### `options`:
     *
     * 1. `allowedFileTypes` ([[FileFilters]]): Filter the files displayed by file
     * extension.
     * 2. `allowMultipleSelection` (`Boolean`): Allow the selection of multiple
     * files. Default: `true`.
     * 3. `title` (`String`): The name of the dialog window.
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[dataTransfer]]. If user canceled the dialog, it will return an empty object
     **/
    this.showSelectFileDialog = function(callbacks, options) {
        //Prepare the options object, use our own local variable to avoid mutating user's object
        var localOptions = {};
        if (isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || "";
        localOptions.suggestedName = options.suggestedName || "";
        localOptions.allowMultipleSelection = isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        localOptions.allowedFileTypes = options.allowedFileTypes || "";
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/windows/select-open-file-dialog/", localOptions, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showSelectFolderDialog(callbacks[, options]) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     * - options (Object): (*optional*) File chooser options
     *
     * *This method is asynchronous.*
     *
     * Displays a file browser dialog for the user to select directories. The select
     * folder dialog call(s) may be separated in time from the later startTransfer(s)
     * call, but they must occur in the same Connect session.
     *
     * ##### `options`:
     *
     * 1. `allowMultipleSelection` (`Boolean`): Allow the selection of multiple
     * folders. Default: `true`.
     * 2. `title` (`String`): The name of the dialog window.
     *
     * ##### Object returned to success callback as parameter
     *
     * See [[dataTransfer]]. If user canceled the dialog, it will return an empty object
     **/
    this.showSelectFolderDialog = function(callbacks, options) {
        //Prepare the options object, use our own local variable to avoid mutating user's object
        var localOptions = {};
        if (isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || "";
        localOptions.allowMultipleSelection = isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/windows/select-open-folder-dialog/", localOptions, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showTransferManager(callbacks) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Displays the Aspera Connect "Transfer Manager" window.
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.showTransferManager = function(callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/windows/transfer-manager", null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#showTransferMonitor(transferId, callbacks) -> null
     * - transferId (String): The ID (`uuid`) of the corresponding transfer.
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Displays the Aspera Connect "Transfer Monitor" window for the transfer.
     *
     * ##### Object returned to success callback as parameter
     *
     *     {}
     **/
    this.showTransferMonitor = function(transferId, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/windows/transfer-monitor/" + transferId, null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /**
     * AW4.Connect#start() -> null | error
     *
     * It will start looking for Connect. Please note that this is called when calling AW4.Connect#initSession
     * and it should only be used after a call to AW4.Connect#stop
     **/
    this.start = function() {
        if (APPLICATION_ID == "") {
            return AW4.Utils.createError(-1, "Please call initSession first");
        }
        requestHandler = new AW4.RequestHandler();
        //Add status listener to connect
        requestHandler.addStatusListener(manageConnectStatus);
        //Initialize request
        var options = {
            pluginId: PLUGIN_ID,
            containerId: PLUGIN_CONTAINER_ID,
            initializeTimeout: INITIALIZE_TIMEOUT,
            sdkLocation: SDK_LOCATION,
            minVersion: MINIMUM_VERSION
        };
        return requestHandler.init(options);
    };

    /**
     * AW4.Connect#startTransfer(transferSpec, connectSpecs, callbacks) -> Object | Error
     * - transferSpec (TransferSpec): Transfer parameters
     * - asperaConnectSettings (ConnectSpec): Aspera Connect options
     * - callbacks (Callbacks): `success` and `error` functions to
     * receive results. This call is successful if Connect is able to start the
     * transfer. Note that an error could still occur after the transfer starts,
     * e.g. if authentication fails. Use [[AW4.Connect#addEventListener]] to
     * receive notification about errors that occur during a transfer session.
     * This call fails if validation fails or the user rejects the transfer.
     *
     * *This method is asynchronous.*
     *
     * Initiates a single transfer. Call [[AW4.Connect#getAllTransfers]] to get transfer
     * statistics, or register an event listener through [[AW4.Connect#addEventListener]].
     *
     * ##### Return format
     *
     * The `request_id`, which is returned immediately, may be for matching
     * this transfer with its events.
     *
     *      {
     *        "request_id" : "bb1b2e2f-3002-4913-a7b3-f7aef4e79132"
     *      }
     **/
    this.startTransfer = function(transfer_spec, aspera_connect_settings, callbacks) {
        if (isNullOrUndefinedOrEmpty(transfer_spec)) {
            return AW4.Utils.createError(-1, "Invalid transferSpec parameter");
        }

        aspera_connect_settings = aspera_connect_settings || {};

        var transferSpecs = {
            transfer_specs : [{
                transfer_spec : transfer_spec,
                aspera_connect_settings : aspera_connect_settings
            }]
        };

        return this.startTransfers(transferSpecs, callbacks);
    };

    /**
     * AW4.Connect#startTransfers(transferSpecs, callbacks) -> Object | Error
     * - transferSpecs (Object): See below
     * - callbacks (Callbacks): `success` and `error` functions to
     * receive results. This call is successful if Connect is able to start the
     * transfer. Note that an error could still occur after the transfer starts,
     * e.g. if authentication fails. Use [[AW4.Connect#addEventListener]] to
     * receive notification about errors that occur during a transfer session.
     * This call fails if validation fails or the user rejects the transfer.
     *
     * *This method is asynchronous.*
     *
     * Initiates one or more transfers (_currently only the first `transfer_spec`
     * is used_). Call [[AW4.Connect#getAllTransfers]] to get transfer
     * statistics, or register an event listener through [[AW4.Connect#addEventListener]].
     *
     * Use this method when generating transfer specs using Aspera Node.
     *
     * ##### Return format
     *
     * The `request_id`, which is returned immediately, may be for matching
     * this start request with transfer events.
     *
     *      {
     *        "request_id" : "bb1b2e2f-3002-4913-a7b3-f7aef4e79132"
     *      }
     *
     * ##### Format for `transferSpecs`
     *
     * See [[TransferSpec]] and [[ConnectSpec]] for definitions.
     *
     *      {
     *        transfer_specs : [
     *          {
     *            transfer_spec : TransferSpec,
     *            aspera_connect_settings : ConnectSpec
     *          },
     *          {
     *            transfer_spec : TransferSpec,
     *            aspera_connect_settings : ConnectSpec
     *          },
     *          ...
     *        ]
     *      }
     **/
    this.startTransfers = function(transfer_specs, callbacks) {
        if (isNullOrUndefinedOrEmpty(transfer_specs)) {
            return AW4.Utils.createError(-1, "Invalid transferSpecs parameter");
        }
        var i, requestId, result, transferSpec;

        requestId = AW4.Utils.generateUuid();

        for ( i = 0; i < transfer_specs.transfer_specs.length; i++) {
            transferSpec = transfer_specs.transfer_specs[i];
            addStandardConnectSettings(transferSpec);
            transferSpec.aspera_connect_settings.request_id = requestId;
            if (isNullOrUndefinedOrEmpty(transferSpec.aspera_connect_settings.back_link)) {
                transferSpec.aspera_connect_settings.back_link = window.location.href;
            }
        }
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/start", transfer_specs, AW4.Utils.SESSION_ID, callbacks);
        return {request_id : requestId};
    };

    /**
     * AW4.Connect#stop() -> null
     *
     * Stop all requests from AW4.Connect to restart activity, please
     * create a new AW4.Connect object or call AW4.Connect#start
     **/
    this.stop = function() {
        return requestHandler.stopRequests();
    };

    /**
     * AW4.Connect#stopTransfer(transferId, callbacks) -> null
     * - transferId (String): The ID (`uuid`) of the transfer to stop.
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * *This method is asynchronous.*
     *
     * Terminate the transfer. Use [[AW4.Connect#resumeTransfer]] to resume.
     **/
    this.stopTransfer = function(transferId, callbacks) {
        connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/connect/transfers/stop/" + transferId, null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };


    /**
     * AW4.Connect#version(callbacks) -> null
     * - callbacks (Callbacks): `success` and `error` functions to receive
     * results.
     *
     * Get the Aspera Connect version.
     *
     * *This method is asynchronous.*
     *
     * ##### Object returned to success callback as parameter
     *
     *     {
     *       version : "3.6.0.8456"
     *     }
     **/
    this.version = function (callbacks) {
        if (isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/connect/info/version", null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };
};
// AW4.Connect

/**
 * == Objects ==
 *
 * Specifications for common objects used as arguments or result data.
 **/

/** section: Objects
 * class Callbacks
 *
 * This object can be passed to an asynchronous API call to get the
 * results of the call.
 *
 * ##### Format
 *
 *     {
 *       success: function(Object) { ... },
 *       error: function(Error) { ... }
 *     }
 *
 * The argument passed to the `success` function depends on the original method
 * invoked. The argument to the `error` function is an [[Error]] object.
 *
 * If an Error is thrown during a callback, it is logged to window.console
 * (if supported by the browser).
 **/

/** section: Objects
 * class Error
 *
 * This object is returned if an error occurs. It contains an error code
 * and a message.
 *
 * *Note that this is not related to the JavaScript `Error`
 * object, but is used only to document the format of errors returned by this
 * API.*
 *
 * ##### Format
 *
 *     {
 *       "error": {
 *         "code": Number,
 *         "internal_message": String,
 *         "user_message": String
 *       }
 *     }
 **/

/** section: Objects
 * class AllTransfersInfo
 *
 * The data format for statistics for all the existing transfers.
 *
 * See [[TransferInfo]].
 *
 * ##### Example
 *
 *     {
 *       "iteration_token": 28,
 *       "result_count": 3,
 *       "transfers": [
 *         TransferInfo,
 *         TransferInfo,
 *         TransferInfo
 *       ]
 *     }
 **/

/**
 * AllTransfersInfo.iteration_token -> Number
 *
 * A marker that represents the moment in time
 * that the transfer status was retrieved. If it is passed as an argument to a
 * getAllTransfers call, the result set will only contain transfers that
 * have had activity since the previous call. Note that this token
 * persists, such that it is still valid if the user restarts Connect.
 *
 * Default: `0`
 **/

/**
 * AllTransfersInfo.result_count -> Number
 *
 * The number of [[TransferInfo]] objects that [[AllTransfersInfo.transfers]] array contains.
 *
 * Default: `0`
 **/

/**
 * AllTransfersInfo.transfers -> Array
 *
 * An array that contains [[TransferInfo]] objects.
 *
 * Default: `[]`
 **/

/** section: Objects
 * class AsperaConnectSettings
 *
 * The data format for the connect web app parameters
 *
 * ##### Example
 *
 *     {
 *       "app_id": "TUyMGQyNDYtM2M1NS00YWRkLTg0MTMtOWQ2OTkxMjk5NGM4",
 *       "back_link": "http://demo.asperasoft.com",
 *       "request_id": "36d3c2a4-1856-47cf-9865-f8e3a8b47822"
 *     }
 **/

/**
 * AsperaConnectSettings.app_id -> String
 *
 * A secure, random identifier for all transfers associated with this webapp.
 * Do not hardcode this id. Do not use the same id for different users.
 * Do not including the host name, product name in the id.
 * Do not use monotonically increasing ids.
 * If you do not provide one, a random id will be generated for you and persisted in localStorage.
 **/

/**
 * AsperaConnectSettings.back_link -> String
 *
 * Link to the webapp.
 **/

/**
 * AsperaConnectSettings.request_id -> String
 *
 * Universally Unique IDentifier for the webapp.
 **/

/** section: Objects
 * class TransferInfo
 *
 * The data format for statistics for one transfer session.
 *
 * See [[TransferSpec]] and [[AsperaConnectSettings]] for definitions.
 *
 * ##### Example
 *
 *     {
 *       "add_time": "2012-10-05T17:53:16",
 *       "aspera_connect_settings": AsperaConnectSettings,
 *       "bytes_expected": 102400,
 *       "bytes_written": 11616,
 *       "calculated_rate_kbps": 34,
 *       "current_file": "/temp/tinyfile0001",
 *       "elapsed_usec": 3000000,
 *       "end_time": "",
 *       "modify_time": "2012-10-05T17:53:18",
 *       "percentage": 0.113438,
 *       "previous_status": "initiating",
 *       "remaining_usec": 21000000,
 *       "start_time": "2012-10-05T17:53:16",
 *       "status": "running",
 *       "title": "tinyfile0001",
 *       "transfer_iteration_token": 18,
 *       "transfer_spec": TransferSpec,
 *       "transport": "fasp",
 *       "uuid": "add433a8-c99b-4e3a-8fc0-4c7a24284ada",
 *       "files": [
 *          {
 *            "bytes_expected": 10485760,
 *            "bytes_written": 1523456,
 *            "fasp_file_id": "3c40b511-5b2dfebb-a2e63483-9b58cb45-9cd9abff",
 *            "file": "/Users/aspera/Downloads/connect_downloads/10MB.3"
 *          }, {
 *            "bytes_expected": 10485760,
 *            "bytes_written": 10485760,
 *            "fasp_file_id": "d5b7deea-2d5878f4-222661f6-170ce0f2-68880a6c",
 *            "file": "/Users/aspera/Downloads/connect_downloads/10MB.2"
 *          }
 *       ]
 *     }
 **/

/**
 * TransferInfo.add_time -> String
 *
 * The time when the transfer was added (according to the system's clock).
 **/

/**
 * TransferInfo.aspera_connect_settings -> AsperaConnectSettings
 **/

/**
 * TransferInfo.bytes_expected -> Number
 *
 * The number of bytes that are still remaining to be written.
 **/

/**
 * TransferInfo.bytes_written -> Number
 *
 * The number of bytes that have already been written to disk.
 **/

/**
 * TransferInfo.calculated_rate_kbps -> Number
 *
 * The current rate of the transfer.
 **/

/**
 * TransferInfo.current_file -> String
 *
 * The full path of the current file.
 **/

/**
 * TransferInfo.elapsed_usec -> Number
 *
 * The duration of the transfer since it started transferring in microseconds.
 *
 * Default: `0`
 **/

/**
 * TransferInfo.end_time -> String
 *
 * The time when the transfer was completed.
 *
 * Default: `""`
 **/

/**
 * TransferInfo.modify_time -> String
 *
 * The last time the transfer was modified.
 *
 * Default: `""`
 **/

/**
 * TransferInfo.percentage -> Number
 *
 * The progress of the transfer over 1.
 *
 * Default: `0`
 **/

/**
 * TransferInfo.previous_status -> String
 *
 * The previous status of the transfer. See [[TransferInfo.status]]
 **/

/**
 * TransferInfo.remaining_usec -> Number
 *
 * The ETA of the transfer in microseconds.
 *
 * Default: `0`
 **/

/**
 * TransferInfo.start_time -> String
 *
 * The time when the transfer moved to initiating status.
 **/

/**
 * TransferInfo.status -> String
 *
 * The status of the transfer.
 *
 * See [[AW4.Connect.TRANSFER_STATUS]]
 *
 **/

/**
 * TransferInfo.title -> String
 *
 * The name of the file.
 **/

/**
 * TransferInfo.transfer_iteration_token -> Number
 *
 * A marker that represents the moment in time that the transfer status was
 * checked.
 **/

/**
 * TransferInfo.transfer_spec -> TransferSpec
 **/

/**
 * TransferInfo.transport -> String
 *
 * Values:
 *
 * 1. `"fasp"` (default)
 * 2. `"http"` - Set when a fasp transfer could not be performed and http fallback was used
 **/

/**
 * TransferInfo.uuid -> String
 *
 * The Universally Unique IDentifier for the transfer, so that it can be
 * differenced from any other.
 **/

/**
 * TransferInfo.files -> Array
 *
 * A list of the files that have been active on this transfer session, with
 * information about their ID, full path, and size and transferred info. Please
 * note that files that haven't been active yet on this session, won't be
 * reported (and you can assume bytes_written is 0)
 *
 * ##### Files format
 *
 *     [
 *       {
 *         "bytes_expected": 10485760,
 *         "bytes_written": 1523456,
 *         "fasp_file_id": "3c40b511-5b2dfebb-a2e63483-9b58cb45-9cd9abff",
 *         "file": "/Users/aspera/Downloads/connect_downloads/10MB.3"
 *       }, {
 *         "bytes_expected": 10485760,
 *         "bytes_written": 10485760,
 *         "fasp_file_id": "d5b7deea-2d5878f4-222661f6-170ce0f2-68880a6c",
 *         "file": "/Users/aspera/Downloads/connect_downloads/10MB.2"
 *       }
 *     ]
 **/

/** section: Objects
 * class TransferSpec
 *
 * The parameters for starting a transfer.
 *
 * ##### Minimal Example
 *
 *     {
 *       "paths": [
 *         {
 *           "source": "/foo/1"
 *         }
 *       ],
 *       "remote_host": "10.0.203.80",
 *       "remote_user": "aspera",
 *       "direction": "send"
 *     }
 *
 * ##### Download Example
 *
 *     {
 *       "paths": [
 *         {
 *           "source": "tinyfile0001"
 *         }, {
 *           "source": "tinyfile0002"
 *         }
 *       ],
 *       "remote_host": "demo.asperasoft.com",
 *       "remote_user": "asperaweb",
 *       "authentication": "password",
 *       "remote_password": "**********",
 *       "fasp_port": 33001,
 *       "ssh_port": 33001,
 *       "http_fallback": true,
 *       "http_fallback_port": 443,
 *       "direction": "receive",
 *       "create_dir": false,
 *       "source_root": "aspera-test-dir-tiny",
 *       "destination_root": "/temp",
 *       "rate_policy": "high",
 *       "target_rate_kbps": 1000,
 *       "min_rate_kbps": 100,
 *       "lock_rate_policy": false,
 *       "target_rate_cap_kbps": 2000,
 *       "lock_target_rate": false,
 *       "lock_min_rate": false,
 *       "resume": "sparse_checksum",
 *       "cipher": "aes-128",
 *       "cookie": "foobarbazqux",
 *       "dgram_size": 1492
 *     }
 **/

/** section: Objects
 * class dataTransfer
 *
 * This object holds the data of the files that have been selected by the user. It
 * may hold one or more data items
 *
 * ##### Format  *
 *     {
 *       "dataTransfer" : {
 *         "files": [
 *           {
 *             "lastModifiedDate": "Wed Sep 24 12:22:02 2014",
 *             "name": "/Users/aspera/Desktop/foo.txt",
 *             "size": 386,
 *             "type: "text/plain"
 *           },
 *           {
 *             "lastModifiedDate": "Mon Sep 22 18:01:02 2014",
 *             "name": "/Users/aspera/Desktop/foo.rb",
 *             "size": 609,
 *             "type: "text/x-ruby-script"
 *           }
 *         ]
 *       }
 *     }
 *
 **/

/**
 * TransferSpec.authentication -> String
 *
 * *optional*
 *
 * The type of authentication to use.
 *
 * Values:
 *
 * 1. `"password"` (default)
 * 2. `"token"`
 **/

/**
 * TransferSpec.cipher -> String
 *
 * *optional*
 *
 * The algorithm used to encrypt data sent during a transfer. Use this option
 * when transmitting sensitive data. Increases CPU utilization.
 *
 * Values:
 *
 * 1. `"none"`
 * 2. `"aes-128"` (default)
 **/

/**
 * TransferSpec.content_protection -> String
 *
 * *optional*
 *
 * Enable content protection (encryption-at-rest), which keeps files encrypted
 * on the server. Encrypted files have the extension ".aspera-env".
 *
 * Values:
 *
 * 1. `"encrypt"`: Encrypt uploaded files. If `content_protection_passphrase`
 * is not specified, Connect will prompt for the passphrase.
 * 2. `"decrypt"`: Decrypt downloaded files. If `content_protection_passphrase`
 * is not specified, Connect will prompt for the passphrase.
 *
 * Default: disabled
 **/

/**
 * TransferSpec.content_protection_passphrase -> String
 *
 * *optional*
 *
 * A passphrase to use to encrypt or decrypt files when using
 * `content_protection`.
 *
 * Default: none
 **/

/**
 * TransferSpec.cookie -> String
 *
 * *optional*
 *
 * Data to associate with the transfer. The cookie is reported to both client-
 * and server-side applications monitoring faspâ„¢ transfers. It is often used
 * by applications to identify associated transfers.
 *
 * Default: none
 **/

/**
 * TransferSpec.create_dir -> Boolean
 *
 * *optional*
 *
 * Creates the destination directory if it does not already exist. When
 * enabling this option, the destination path is assumed to be a directory
 * path.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/**
 * TransferSpec.destination_root -> String
 *
 * *optional*
 *
 * The transfer destination file path. If destinations are specified in
 * `paths`, this value is prepended to each destination.
 *
 * Note that download destination paths are relative to the user's Connect
 * download directory setting unless `ConnectSpec.use_absolute_destination_path`
 * is enabled.
 *
 * Default: `"/"`
 **/

/**
 * TransferSpec.dgram_size -> Number
 *
 * *optional*
 *
 * The IP datagram size for faspâ„¢ to use. If not specified, faspâ„¢ will
 * automatically detect and use the path MTU as the datagram size.
 * Use this option only to satisfy networks with strict MTU requirements.
 *
 * Default: auto-detect
 **/

/**
 * TransferSpec.direction -> String
 *
 * *required*
 *
 * Whether to perform an upload or a download.
 *
 * Values:
 *
 * 1. `"send"` (upload)
 * 2. `"receive"` (download)
 **/

/**
 * TransferSpec.fasp_port -> Number
 *
 * *optional*
 *
 * The UDP port for faspâ„¢ to use. The default value is satisfactory for most
 * situations. However, it can be changed to satisfy firewall requirements.
 *
 * Default: `33001`
 **/

/**
 * TransferSpec.http_fallback -> Boolean
 *
 * *optional*
 *
 * Attempts to perform an HTTP transfer if a faspâ„¢ transfer cannot be
 * performed.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/**
 * TransferSpec.http_fallback_port -> Number
 *
 * *optional*
 *
 * The port where the Aspera HTTP server is servicing HTTP transfers.
 * Defaults to port 443 if a `cipher` is enabled, or port 80 otherwise.
 *
 * Default: `80` or `443` (HTTPS)
 **/

/**
 * TransferSpec.lock_min_rate -> Boolean
 *
 * *optional*
 *
 * Prevents the user from changing the minimum rate during a transfer.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/**
 * TransferSpec.lock_rate_policy -> Boolean
 *
 * *optional*
 *
 * Prevents the user from changing the rate policy during a transfer.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/**
 * TransferSpec.lock_target_rate -> Boolean
 *
 * *optional*
 *
 * Prevents the user from changing the target rate during a transfer.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/**
 * TransferSpec.min_rate_kbps -> Number
 *
 * *optional*
 *
 * The minimum speed of the transfer. faspâ„¢ will only share bandwidth exceeding
 * this value.
 *
 * Note: This value has no effect if `rate_policy` is `"fixed"`.
 *
 * Default: Server-side minimum rate default setting (aspera.conf). Will
 * respect both local- and server-side minimum rate caps if set.
 **/

/**
 * TransferSpec.paths -> Array
 *
 * *required*
 *
 * A list of the file and directory paths to transfer. Use `destination_root`
 * to specify the destination directory.
 *
 * ##### Source list format
 *
 *     [
 *       {
 *         "source": "/foo"
 *       }, {
 *         "source": "/bar/baz"
 *       },
 *       ...
 *     ]
 *
 * Optionally specify a destination path - including the file name - for each
 * file. This format is useful for renaming files or sending to different
 * destinations. Note that for this format all paths must be file paths (not
 * directory paths).
 *
 * ##### Source-Destination pair format
 *
 *     [
 *       {
 *         "source": "/foo",
 *         "destination": "/qux/foofoo"
 *       }, {
 *         "source": "/bar/baz",
 *         "destination": "/qux/bazbaz"
 *       },
 *       ...
 *     ]
 **/

/**
 * TransferSpec.rate_policy -> String
 *
 * *optional*
 *
 * The congestion control behavior to use when sharing bandwidth.
 *
 * Values:
 *
 * 1. `"fixed"`: Transfer at the target rate, regardless of the actual network
 * capacity. Do not share bandwidth.
 * 2. `"high"`: When sharing bandwidth, transfer at twice the rate of a
 * transfer using a "fair" policy.
 * 3. `"fair"` (default): Share bandwidth equally with other traffic.
 * 4. `"low"`: Use only unutilized bandwidth.
 **/

/**
 * TransferSpec.remote_host -> String
 *
 * *required*
 *
 * The fully qualified domain name or IP address of the transfer server.
 **/

/**
 * TransferSpec.remote_password -> String
 *
 * *optional*
 *
 * The password to use when `authentication` is set to `"password"`. If this
 * value is not specified, Connect will prompt the user.
 **/

/**
 * TransferSpec.remote_user -> String
 *
 * *optional*
 *
 * The username to use for authentication. For password authentication, if
 * this value is not specified, Connect will prompt the user.
 **/

/**
 * TransferSpec.resume -> String
 *
 * *optional*
 *
 * The policy to use when resuming partially transferred (incomplete) files.
 *
 * Values:
 *
 * 1. `"none"`: Transfer the entire file again.
 * 2. `"attributes"`: Resume if the files' attributes match.
 * 3. `"sparse_checksum"` (default): Resume if the files' attributes and sparse
 * (fast) checksums match.
 * 4. `"full_checksum"`: Resume if the files' attributes and full checksums
 * match. Note that computing full checksums of large files takes time, and
 * heavily utilizes the CPU.
 **/

/**
 * TransferSpec.ssh_port -> Number
 *
 * *optional*
 *
 * The server's TCP port that is listening for SSH connections. faspâ„¢ initiates
 * transfers through SSH.
 *
 * Default: `33001`
 **/

/**
 * TransferSpec.source_root -> String
 *
 * *optional*
 *
 * A path to prepend to the source paths specified in `paths`. If this is not
 * specified, then `paths` should contain absolute paths.
 *
 * Default: `"/"`
 **/

/**
 * TransferSpec.target_rate_cap_kbps -> Number
 *
 * *optional*
 *
 * Limit the transfer rate that the user can adjust the target and minimum
 * rates to.
 *
 * Default: no limit
 **/

/**
 * TransferSpec.target_rate_kbps -> Number
 *
 * *optional*
 *
 * The desired speed of the transfer. If there is competing network traffic,
 * faspâ„¢ may share this bandwidth, depending on the `rate_policy`.
 *
 * Default: Server-side target rate default setting (aspera.conf). Will
 * respect both local- and server-side target rate caps if set.
 **/

/**
 * TransferSpec.token -> String
 *
 * *optional*
 *
 * Used for token-based authorization, which involves the server-side
 * application generating a token that gives the client rights to transfer
 * a predetermined set of files.
 *
 * Default: none
 **/

/** section: Objects
 * class ConnectSpec
 *
 * Connect-specific parameters when starting a transfer.
 *
 * ##### Example
 *
 *     {
 *       "allow_dialogs" : false,
 *       "back_link" : "www.foo.com",
 *       "return_paths" : false,
 *       "return_files" : false,
 *       "use_absolute_destination_path" : true
 *     }
 **/

/**
 * ConnectSpec.allow_dialogs -> Boolean
 *
 * *optional*
 *
 * If this value is `false`, Connect will no longer prompt or display windows
 * automatically, except to ask the user to authorize transfers if the server
 * is not on the list of trusted hosts.
 *
 * Values:
 *
 * 1. `true` (default)
 * 2. `false`
 **/

/**
 * ConnectSpec.back_link -> String
 *
 * *optional*
 *
 * A URL to associate with the transfer. Connect will display this link
 * in the context menu of the transfer.
 *
 * Default: The URL of the current page
 **/

/**
 * ConnectSpec.return_files -> Boolean
 *
 * *optional*
 *
 * If this value is `false`, [[TransferInfo]] will not contain
 * [[TransferInfo.files]]. Use this option to prevent performance deterioration
 * when transferring large number of files.
 *
 * Values:
 *
 * 1. `true` (default)
 * 2. `false`
 **/

/**
 * ConnectSpec.return_paths -> Boolean
 *
 * *optional*
 *
 * If this value is `false`, [[TransferInfo]] will not contain
 * [[TransferSpec.paths]]. Use this option to prevent performance deterioration
 * when specifying a large number of source paths.
 *
 * Values:
 *
 * 1. `true` (default)
 * 2. `false`
 **/

/**
 * ConnectSpec.use_absolute_destination_path -> Boolean
 *
 * *optional*
 *
 * By default, the destination of a download is relative to the user's Connect
 * download directory setting. Setting this value to `true` overrides this
 * behavior, using absolute paths instead.
 *
 * Values:
 *
 * 1. `false` (default)
 * 2. `true`
 **/

/** section: Objects
 * class FileFilters
 *
 * A set of file extension filters.
 *
 * ##### Example
 *
 *     [
 *       {
 *         filter_name : "Text file",
 *         extensions : ["txt"]
 *       },
 *       {
 *         filter_name : "Image file",
 *         extensions : ["jpg", "png"]
 *       },
 *       {
 *         filter_name : "All types",
 *         extensions : ["*"]
 *       }
 *     ]
 **/
/* ====================== Drive API =======================*/
/* section: API
 * class AW4.Drive
 *
 * The [[AW4.Drive]] class contains all the Connect API methods.
 */

/*
 * new AW4.Drive([options])
 * - options (Object): Configuration parameters for the plug-in.
 *
 * Creates a new [[AW4.Connect]] object.
 *
 * ##### Options
 *
 * 1. `connectLaunchWaitTimeoutMs` (`Number`):
 *     How long to wait in milliseconds for Aspera Drive to launch, if we reach
 *     this timeout without a successful request to connect, we will go into FAILED
 *     status.
 *     `5000`.
 * 2. `id` (`String`):
 *     The DOM `id` of the plug-in object to be inserted. Default:
 *     `"aspera-web"`.
 * 3. `containerId` (`String`):
 *     The DOM `id` of an existing element to insert the plug-in element into
 *     (replacing its contents). If not specified, the plug-in is appended to
 *     the document body. Note that the plug-in must not be hidden in order to
 *     be loaded.
 * 4. `sdkLocation` (`String`):
 *     URL to the SDK location that has to be served in the same level of security
 *     as the web page (http/https). It has to be in the following format:\
 *     `//domain/path/to/connect/sdk`\
 *     Default: \
 *     `//d3gcli72yxqn2z.cloudfront.net/connect/v4`.
 * 5. `pollingTime` (`Number`):
 *     How often in milliseconds we want to get updates of the transfer's status
 *     Default: `2000`.
 * 6. `minVersion` (`String`):
 *     Minimum version of connect required by the web application in order to work.\
 *     Format:\
 *     `3.6.0`
 * 7. `dragDropEnabled` (`Boolean`):
 *     Enable drag and drop of files/folders into the browser
 *     Default: \
 *     `false`.
 *
 * ##### Example
 *
 * The following JavaScript creates an [[AW4.Drive]] object to interface with
 * Aspera Drive on the client computer. This code should be executed on
 * document ready.
 *
 *     var asperaDrive = new AW4.Drive();
 *
 */
AW4.Drive = function(options) {

    AW4.Connect.call(this, options);
    AW4.Utils.CURRENT_API = AW4.Utils.DRIVE_API;

    /*
   * AW4.Drive#accounts(callbacks) -> null
   * - callbacks (Callbacks): `success` and `error` functions to receive
   * results.
   *
   * Get the Aspera Drive Accounts.
   *
   * *This method is asynchronous.*
   *
   * ##### Object returned to success callback as parameter
   *
   *    TODO: add object sample
   *
   */
    this.accounts = function (callbacks) {
        if (this.isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        this.driveHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/account", null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /*
   * AW4.Drive#accountById(accountId, callbacks) -> null | Error
   * - accountId (String): Drive's account Id
   * - callbacks (Callbacks): `success` and `error` functions to receive
   * results.
   *
   * *This method is asynchronous.*
   *
   *
   * ##### Object returned to success callback as parameter
   *
   *
   *    TODO: Add Account object sample
   *
   */
    this.accountById = function (accountId, callbacks) {
        if (this.isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        this.driveHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/account/" + accountId, null, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /*
    * AW4.Drive#checkoutFilesByIdPath(idPaths, callbacks) -> null | Error
    * - idPaths (Array of Objects): Files Ids path:
    *    [{id_path:'drive>53a89300-9abd-4ff1-b3ff-de78ec049e6c>638>218135>741259'}],
    *    where 53a89300-9abd-4ff1-b3ff-de78ec049e6c is Drive's Account Id
    * - callbacks (Callbacks): `success` and `error` functions to receive
    * results.
    *
    * *This method is asynchronous.*
    *
    *
    * ##### Object returned to success callback as parameter
    *
    *
    * [
    *  {
    *      "checkout_id": "9f081b65-1b08-4a2e-865e-ba59c0c13372",
    *      "account_id": "53a89300-9abd-4ff1-b3ff-de78ec049e6c",
    *      "filelock": {
    *          "file_id": "741259",
    *          "created_at": "2018-01-02T20:43:23Z",
    *          "created_by_id": "1249",
    *          "created_by_type": "bearer",
    *          "tags": {
    *              "account_id": "53a89300-9abd-4ff1-b3ff-de78ec049e6c",
    *              "checkout_id": "9f081b65-1b08-4a2e-865e-ba59c0c13372",
    *              "local_path": "C:/Users/oleh/.aspera/localcache/files_qa/Dev_workspace/Files/AsperaDrive-2.2.0.141065.dmg",
    *              "name": "AsperaDrive-2.2.0.141065.dmg",
    *              "node_id": "95",
    *              "owner_name": "Me",
    *              "parent_file_id": "218135",
    *              "remote_path": "/files_qa/Dev_workspace/Files/AsperaDrive-2.2.0.141065.dmg"
    *          }
    *      },
    *      "name": "AsperaDrive-2.2.0.141065.dmg",
    *      "path": "/files_qa/Dev_workspace/Files/AsperaDrive-2.2.0.141065.dmg"
    *  }
    * ]
    *
    */
    this.checkoutFilesByIdPath = function (idPaths, callbacks) {
        if (this.isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        this.driveHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/checkouts", idPaths, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /*
    * AW4.Drive#checkinCheckouts(checkoutIds, callbacks) -> null | Error
    * - idPaths (Array of Objects): Checkout Ids (checkout_id) from AW4.Drive#checkoutFilesByIdPath response:
    *    ['9f081b65-1b08-4a2e-865e-ba59c0c13372']
    * - callbacks (Callbacks): `success` and `error` functions to receive
    * results.
    *
    * *This method is asynchronous.*
    *
    *
    *
    */
    this.checkinCheckouts = function (checkoutIds, callbacks) {
        if (this.isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        this.driveHttpRequest(AW4.Connect.HTTP_METHOD.DELETE, "/checkouts", checkoutIds, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };

    /*
    * AW4.Drive#revertCheckouts(checkoutIds, callbacks) -> null | Error
    * - idPaths (Array of Objects): Checkout Ids (checkout_id) from AW4.Drive#checkoutFilesByIdPath response:
    *    ['9f081b65-1b08-4a2e-865e-ba59c0c13372']
    * - callbacks (Callbacks): `success` and `error` functions to receive
    * results.
    *
    * *This method is asynchronous.*
    *
    *
    *
    */
    this.revertCheckouts = function (checkoutIds, callbacks) {
        if (this.isNullOrUndefinedOrEmpty(callbacks)) {
            return null;
        }
        this.driveHttpRequest(AW4.Connect.HTTP_METHOD.REVERT, "/checkouts", checkoutIds, AW4.Utils.SESSION_ID, callbacks);
        return null;
    };
};
"use strict";
if (typeof AW4 === "undefined") var AW4 = {};
AW4.crypt = (function(root) {

    function checkInt(value) {
        return (parseInt(value) === value);
    }

    function checkInts(arrayish) {
        if (!checkInt(arrayish.length)) { return false; }

        for (var i = 0; i < arrayish.length; i++) {
            if (!checkInt(arrayish[i]) || arrayish[i] < 0 || arrayish[i] > 255) {
                return false;
            }
        }

        return true;
    }

    function coerceArray(arg, copy) {

        if (arg.buffer && ArrayBuffer.isView(arg) && arg.name === 'Uint8Array') {

            if (copy) {
                if (arg.slice) {
                    arg = arg.slice();
                } else {
                    arg = Array.prototype.slice.call(arg);
                }
            }

            return arg;
        }

        if (Array.isArray(arg)) {
            if (!checkInts(arg)) {
                throw new Error('Array contains invalid value: ' + arg);
            }

            return new Uint8Array(arg);
        }

        if (checkInt(arg.length) && checkInts(arg)) {
            return new Uint8Array(arg);
        }

        throw new Error('unsupported array-like object');
    }

    function createArray(length) {
        return new Uint8Array(length);
    }

    function copyArray(sourceArray, targetArray, targetStart, sourceStart, sourceEnd) {
        if (sourceStart != null || sourceEnd != null) {
            if (sourceArray.slice) {
                sourceArray = sourceArray.slice(sourceStart, sourceEnd);
            } else {
                sourceArray = Array.prototype.slice.call(sourceArray, sourceStart, sourceEnd);
            }
        }
        targetArray.set(sourceArray, targetStart);
    }



    var convertUtf8 = (function() {
        function toBytes(text) {
            var result = [], i = 0;
            text = encodeURI(text);
            while (i < text.length) {
                var c = text.charCodeAt(i++);

                if (c === 37) {
                    result.push(parseInt(text.substr(i, 2), 16))
                    i += 2;

                } else {
                    result.push(c)
                }
            }

            return coerceArray(result);
        }

        function fromBytes(bytes) {
            var result = [], i = 0;

            while (i < bytes.length) {
                var c = bytes[i];

                if (c < 128) {
                    result.push(String.fromCharCode(c));
                    i++;
                } else if (c > 191 && c < 224) {
                    result.push(String.fromCharCode(((c & 0x1f) << 6) | (bytes[i + 1] & 0x3f)));
                    i += 2;
                } else {
                    result.push(String.fromCharCode(((c & 0x0f) << 12) | ((bytes[i + 1] & 0x3f) << 6) | (bytes[i + 2] & 0x3f)));
                    i += 3;
                }
            }

            return result.join('');
        }

        return {
            toBytes: toBytes,
            fromBytes: fromBytes,
        }
    })();

    var convertHex = (function() {
        function toBytes(text) {
            var result = [];
            for (var i = 0; i < text.length; i += 2) {
                result.push(parseInt(text.substr(i, 2), 16));
            }

            return result;
        }

        var Hex = '0123456789abcdef';

        function fromBytes(bytes) {
            var result = [];
            for (var i = 0; i < bytes.length; i++) {
                var v = bytes[i];
                result.push(Hex[(v & 0xf0) >> 4] + Hex[v & 0x0f]);
            }
            return result.join('');
        }

        return {
            toBytes: toBytes,
            fromBytes: fromBytes,
        }
    })();


    var numberOfRounds = {16: 10, 24: 12, 32: 14}

    var rcon = [0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91];

    var S = [0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76, 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0, 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15, 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75, 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84, 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf, 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8, 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2, 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73, 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb, 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79, 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08, 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a, 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e, 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf, 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16];
    var Si =[0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b, 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d];

    var T1 = [0xc66363a5, 0xf87c7c84, 0xee777799, 0xf67b7b8d, 0xfff2f20d, 0xd66b6bbd, 0xde6f6fb1, 0x91c5c554, 0x60303050, 0x02010103, 0xce6767a9, 0x562b2b7d, 0xe7fefe19, 0xb5d7d762, 0x4dababe6, 0xec76769a, 0x8fcaca45, 0x1f82829d, 0x89c9c940, 0xfa7d7d87, 0xeffafa15, 0xb25959eb, 0x8e4747c9, 0xfbf0f00b, 0x41adadec, 0xb3d4d467, 0x5fa2a2fd, 0x45afafea, 0x239c9cbf, 0x53a4a4f7, 0xe4727296, 0x9bc0c05b, 0x75b7b7c2, 0xe1fdfd1c, 0x3d9393ae, 0x4c26266a, 0x6c36365a, 0x7e3f3f41, 0xf5f7f702, 0x83cccc4f, 0x6834345c, 0x51a5a5f4, 0xd1e5e534, 0xf9f1f108, 0xe2717193, 0xabd8d873, 0x62313153, 0x2a15153f, 0x0804040c, 0x95c7c752, 0x46232365, 0x9dc3c35e, 0x30181828, 0x379696a1, 0x0a05050f, 0x2f9a9ab5, 0x0e070709, 0x24121236, 0x1b80809b, 0xdfe2e23d, 0xcdebeb26, 0x4e272769, 0x7fb2b2cd, 0xea75759f, 0x1209091b, 0x1d83839e, 0x582c2c74, 0x341a1a2e, 0x361b1b2d, 0xdc6e6eb2, 0xb45a5aee, 0x5ba0a0fb, 0xa45252f6, 0x763b3b4d, 0xb7d6d661, 0x7db3b3ce, 0x5229297b, 0xdde3e33e, 0x5e2f2f71, 0x13848497, 0xa65353f5, 0xb9d1d168, 0x00000000, 0xc1eded2c, 0x40202060, 0xe3fcfc1f, 0x79b1b1c8, 0xb65b5bed, 0xd46a6abe, 0x8dcbcb46, 0x67bebed9, 0x7239394b, 0x944a4ade, 0x984c4cd4, 0xb05858e8, 0x85cfcf4a, 0xbbd0d06b, 0xc5efef2a, 0x4faaaae5, 0xedfbfb16, 0x864343c5, 0x9a4d4dd7, 0x66333355, 0x11858594, 0x8a4545cf, 0xe9f9f910, 0x04020206, 0xfe7f7f81, 0xa05050f0, 0x783c3c44, 0x259f9fba, 0x4ba8a8e3, 0xa25151f3, 0x5da3a3fe, 0x804040c0, 0x058f8f8a, 0x3f9292ad, 0x219d9dbc, 0x70383848, 0xf1f5f504, 0x63bcbcdf, 0x77b6b6c1, 0xafdada75, 0x42212163, 0x20101030, 0xe5ffff1a, 0xfdf3f30e, 0xbfd2d26d, 0x81cdcd4c, 0x180c0c14, 0x26131335, 0xc3ecec2f, 0xbe5f5fe1, 0x359797a2, 0x884444cc, 0x2e171739, 0x93c4c457, 0x55a7a7f2, 0xfc7e7e82, 0x7a3d3d47, 0xc86464ac, 0xba5d5de7, 0x3219192b, 0xe6737395, 0xc06060a0, 0x19818198, 0x9e4f4fd1, 0xa3dcdc7f, 0x44222266, 0x542a2a7e, 0x3b9090ab, 0x0b888883, 0x8c4646ca, 0xc7eeee29, 0x6bb8b8d3, 0x2814143c, 0xa7dede79, 0xbc5e5ee2, 0x160b0b1d, 0xaddbdb76, 0xdbe0e03b, 0x64323256, 0x743a3a4e, 0x140a0a1e, 0x924949db, 0x0c06060a, 0x4824246c, 0xb85c5ce4, 0x9fc2c25d, 0xbdd3d36e, 0x43acacef, 0xc46262a6, 0x399191a8, 0x319595a4, 0xd3e4e437, 0xf279798b, 0xd5e7e732, 0x8bc8c843, 0x6e373759, 0xda6d6db7, 0x018d8d8c, 0xb1d5d564, 0x9c4e4ed2, 0x49a9a9e0, 0xd86c6cb4, 0xac5656fa, 0xf3f4f407, 0xcfeaea25, 0xca6565af, 0xf47a7a8e, 0x47aeaee9, 0x10080818, 0x6fbabad5, 0xf0787888, 0x4a25256f, 0x5c2e2e72, 0x381c1c24, 0x57a6a6f1, 0x73b4b4c7, 0x97c6c651, 0xcbe8e823, 0xa1dddd7c, 0xe874749c, 0x3e1f1f21, 0x964b4bdd, 0x61bdbddc, 0x0d8b8b86, 0x0f8a8a85, 0xe0707090, 0x7c3e3e42, 0x71b5b5c4, 0xcc6666aa, 0x904848d8, 0x06030305, 0xf7f6f601, 0x1c0e0e12, 0xc26161a3, 0x6a35355f, 0xae5757f9, 0x69b9b9d0, 0x17868691, 0x99c1c158, 0x3a1d1d27, 0x279e9eb9, 0xd9e1e138, 0xebf8f813, 0x2b9898b3, 0x22111133, 0xd26969bb, 0xa9d9d970, 0x078e8e89, 0x339494a7, 0x2d9b9bb6, 0x3c1e1e22, 0x15878792, 0xc9e9e920, 0x87cece49, 0xaa5555ff, 0x50282878, 0xa5dfdf7a, 0x038c8c8f, 0x59a1a1f8, 0x09898980, 0x1a0d0d17, 0x65bfbfda, 0xd7e6e631, 0x844242c6, 0xd06868b8, 0x824141c3, 0x299999b0, 0x5a2d2d77, 0x1e0f0f11, 0x7bb0b0cb, 0xa85454fc, 0x6dbbbbd6, 0x2c16163a];
    var T2 = [0xa5c66363, 0x84f87c7c, 0x99ee7777, 0x8df67b7b, 0x0dfff2f2, 0xbdd66b6b, 0xb1de6f6f, 0x5491c5c5, 0x50603030, 0x03020101, 0xa9ce6767, 0x7d562b2b, 0x19e7fefe, 0x62b5d7d7, 0xe64dabab, 0x9aec7676, 0x458fcaca, 0x9d1f8282, 0x4089c9c9, 0x87fa7d7d, 0x15effafa, 0xebb25959, 0xc98e4747, 0x0bfbf0f0, 0xec41adad, 0x67b3d4d4, 0xfd5fa2a2, 0xea45afaf, 0xbf239c9c, 0xf753a4a4, 0x96e47272, 0x5b9bc0c0, 0xc275b7b7, 0x1ce1fdfd, 0xae3d9393, 0x6a4c2626, 0x5a6c3636, 0x417e3f3f, 0x02f5f7f7, 0x4f83cccc, 0x5c683434, 0xf451a5a5, 0x34d1e5e5, 0x08f9f1f1, 0x93e27171, 0x73abd8d8, 0x53623131, 0x3f2a1515, 0x0c080404, 0x5295c7c7, 0x65462323, 0x5e9dc3c3, 0x28301818, 0xa1379696, 0x0f0a0505, 0xb52f9a9a, 0x090e0707, 0x36241212, 0x9b1b8080, 0x3ddfe2e2, 0x26cdebeb, 0x694e2727, 0xcd7fb2b2, 0x9fea7575, 0x1b120909, 0x9e1d8383, 0x74582c2c, 0x2e341a1a, 0x2d361b1b, 0xb2dc6e6e, 0xeeb45a5a, 0xfb5ba0a0, 0xf6a45252, 0x4d763b3b, 0x61b7d6d6, 0xce7db3b3, 0x7b522929, 0x3edde3e3, 0x715e2f2f, 0x97138484, 0xf5a65353, 0x68b9d1d1, 0x00000000, 0x2cc1eded, 0x60402020, 0x1fe3fcfc, 0xc879b1b1, 0xedb65b5b, 0xbed46a6a, 0x468dcbcb, 0xd967bebe, 0x4b723939, 0xde944a4a, 0xd4984c4c, 0xe8b05858, 0x4a85cfcf, 0x6bbbd0d0, 0x2ac5efef, 0xe54faaaa, 0x16edfbfb, 0xc5864343, 0xd79a4d4d, 0x55663333, 0x94118585, 0xcf8a4545, 0x10e9f9f9, 0x06040202, 0x81fe7f7f, 0xf0a05050, 0x44783c3c, 0xba259f9f, 0xe34ba8a8, 0xf3a25151, 0xfe5da3a3, 0xc0804040, 0x8a058f8f, 0xad3f9292, 0xbc219d9d, 0x48703838, 0x04f1f5f5, 0xdf63bcbc, 0xc177b6b6, 0x75afdada, 0x63422121, 0x30201010, 0x1ae5ffff, 0x0efdf3f3, 0x6dbfd2d2, 0x4c81cdcd, 0x14180c0c, 0x35261313, 0x2fc3ecec, 0xe1be5f5f, 0xa2359797, 0xcc884444, 0x392e1717, 0x5793c4c4, 0xf255a7a7, 0x82fc7e7e, 0x477a3d3d, 0xacc86464, 0xe7ba5d5d, 0x2b321919, 0x95e67373, 0xa0c06060, 0x98198181, 0xd19e4f4f, 0x7fa3dcdc, 0x66442222, 0x7e542a2a, 0xab3b9090, 0x830b8888, 0xca8c4646, 0x29c7eeee, 0xd36bb8b8, 0x3c281414, 0x79a7dede, 0xe2bc5e5e, 0x1d160b0b, 0x76addbdb, 0x3bdbe0e0, 0x56643232, 0x4e743a3a, 0x1e140a0a, 0xdb924949, 0x0a0c0606, 0x6c482424, 0xe4b85c5c, 0x5d9fc2c2, 0x6ebdd3d3, 0xef43acac, 0xa6c46262, 0xa8399191, 0xa4319595, 0x37d3e4e4, 0x8bf27979, 0x32d5e7e7, 0x438bc8c8, 0x596e3737, 0xb7da6d6d, 0x8c018d8d, 0x64b1d5d5, 0xd29c4e4e, 0xe049a9a9, 0xb4d86c6c, 0xfaac5656, 0x07f3f4f4, 0x25cfeaea, 0xafca6565, 0x8ef47a7a, 0xe947aeae, 0x18100808, 0xd56fbaba, 0x88f07878, 0x6f4a2525, 0x725c2e2e, 0x24381c1c, 0xf157a6a6, 0xc773b4b4, 0x5197c6c6, 0x23cbe8e8, 0x7ca1dddd, 0x9ce87474, 0x213e1f1f, 0xdd964b4b, 0xdc61bdbd, 0x860d8b8b, 0x850f8a8a, 0x90e07070, 0x427c3e3e, 0xc471b5b5, 0xaacc6666, 0xd8904848, 0x05060303, 0x01f7f6f6, 0x121c0e0e, 0xa3c26161, 0x5f6a3535, 0xf9ae5757, 0xd069b9b9, 0x91178686, 0x5899c1c1, 0x273a1d1d, 0xb9279e9e, 0x38d9e1e1, 0x13ebf8f8, 0xb32b9898, 0x33221111, 0xbbd26969, 0x70a9d9d9, 0x89078e8e, 0xa7339494, 0xb62d9b9b, 0x223c1e1e, 0x92158787, 0x20c9e9e9, 0x4987cece, 0xffaa5555, 0x78502828, 0x7aa5dfdf, 0x8f038c8c, 0xf859a1a1, 0x80098989, 0x171a0d0d, 0xda65bfbf, 0x31d7e6e6, 0xc6844242, 0xb8d06868, 0xc3824141, 0xb0299999, 0x775a2d2d, 0x111e0f0f, 0xcb7bb0b0, 0xfca85454, 0xd66dbbbb, 0x3a2c1616];
    var T3 = [0x63a5c663, 0x7c84f87c, 0x7799ee77, 0x7b8df67b, 0xf20dfff2, 0x6bbdd66b, 0x6fb1de6f, 0xc55491c5, 0x30506030, 0x01030201, 0x67a9ce67, 0x2b7d562b, 0xfe19e7fe, 0xd762b5d7, 0xabe64dab, 0x769aec76, 0xca458fca, 0x829d1f82, 0xc94089c9, 0x7d87fa7d, 0xfa15effa, 0x59ebb259, 0x47c98e47, 0xf00bfbf0, 0xadec41ad, 0xd467b3d4, 0xa2fd5fa2, 0xafea45af, 0x9cbf239c, 0xa4f753a4, 0x7296e472, 0xc05b9bc0, 0xb7c275b7, 0xfd1ce1fd, 0x93ae3d93, 0x266a4c26, 0x365a6c36, 0x3f417e3f, 0xf702f5f7, 0xcc4f83cc, 0x345c6834, 0xa5f451a5, 0xe534d1e5, 0xf108f9f1, 0x7193e271, 0xd873abd8, 0x31536231, 0x153f2a15, 0x040c0804, 0xc75295c7, 0x23654623, 0xc35e9dc3, 0x18283018, 0x96a13796, 0x050f0a05, 0x9ab52f9a, 0x07090e07, 0x12362412, 0x809b1b80, 0xe23ddfe2, 0xeb26cdeb, 0x27694e27, 0xb2cd7fb2, 0x759fea75, 0x091b1209, 0x839e1d83, 0x2c74582c, 0x1a2e341a, 0x1b2d361b, 0x6eb2dc6e, 0x5aeeb45a, 0xa0fb5ba0, 0x52f6a452, 0x3b4d763b, 0xd661b7d6, 0xb3ce7db3, 0x297b5229, 0xe33edde3, 0x2f715e2f, 0x84971384, 0x53f5a653, 0xd168b9d1, 0x00000000, 0xed2cc1ed, 0x20604020, 0xfc1fe3fc, 0xb1c879b1, 0x5bedb65b, 0x6abed46a, 0xcb468dcb, 0xbed967be, 0x394b7239, 0x4ade944a, 0x4cd4984c, 0x58e8b058, 0xcf4a85cf, 0xd06bbbd0, 0xef2ac5ef, 0xaae54faa, 0xfb16edfb, 0x43c58643, 0x4dd79a4d, 0x33556633, 0x85941185, 0x45cf8a45, 0xf910e9f9, 0x02060402, 0x7f81fe7f, 0x50f0a050, 0x3c44783c, 0x9fba259f, 0xa8e34ba8, 0x51f3a251, 0xa3fe5da3, 0x40c08040, 0x8f8a058f, 0x92ad3f92, 0x9dbc219d, 0x38487038, 0xf504f1f5, 0xbcdf63bc, 0xb6c177b6, 0xda75afda, 0x21634221, 0x10302010, 0xff1ae5ff, 0xf30efdf3, 0xd26dbfd2, 0xcd4c81cd, 0x0c14180c, 0x13352613, 0xec2fc3ec, 0x5fe1be5f, 0x97a23597, 0x44cc8844, 0x17392e17, 0xc45793c4, 0xa7f255a7, 0x7e82fc7e, 0x3d477a3d, 0x64acc864, 0x5de7ba5d, 0x192b3219, 0x7395e673, 0x60a0c060, 0x81981981, 0x4fd19e4f, 0xdc7fa3dc, 0x22664422, 0x2a7e542a, 0x90ab3b90, 0x88830b88, 0x46ca8c46, 0xee29c7ee, 0xb8d36bb8, 0x143c2814, 0xde79a7de, 0x5ee2bc5e, 0x0b1d160b, 0xdb76addb, 0xe03bdbe0, 0x32566432, 0x3a4e743a, 0x0a1e140a, 0x49db9249, 0x060a0c06, 0x246c4824, 0x5ce4b85c, 0xc25d9fc2, 0xd36ebdd3, 0xacef43ac, 0x62a6c462, 0x91a83991, 0x95a43195, 0xe437d3e4, 0x798bf279, 0xe732d5e7, 0xc8438bc8, 0x37596e37, 0x6db7da6d, 0x8d8c018d, 0xd564b1d5, 0x4ed29c4e, 0xa9e049a9, 0x6cb4d86c, 0x56faac56, 0xf407f3f4, 0xea25cfea, 0x65afca65, 0x7a8ef47a, 0xaee947ae, 0x08181008, 0xbad56fba, 0x7888f078, 0x256f4a25, 0x2e725c2e, 0x1c24381c, 0xa6f157a6, 0xb4c773b4, 0xc65197c6, 0xe823cbe8, 0xdd7ca1dd, 0x749ce874, 0x1f213e1f, 0x4bdd964b, 0xbddc61bd, 0x8b860d8b, 0x8a850f8a, 0x7090e070, 0x3e427c3e, 0xb5c471b5, 0x66aacc66, 0x48d89048, 0x03050603, 0xf601f7f6, 0x0e121c0e, 0x61a3c261, 0x355f6a35, 0x57f9ae57, 0xb9d069b9, 0x86911786, 0xc15899c1, 0x1d273a1d, 0x9eb9279e, 0xe138d9e1, 0xf813ebf8, 0x98b32b98, 0x11332211, 0x69bbd269, 0xd970a9d9, 0x8e89078e, 0x94a73394, 0x9bb62d9b, 0x1e223c1e, 0x87921587, 0xe920c9e9, 0xce4987ce, 0x55ffaa55, 0x28785028, 0xdf7aa5df, 0x8c8f038c, 0xa1f859a1, 0x89800989, 0x0d171a0d, 0xbfda65bf, 0xe631d7e6, 0x42c68442, 0x68b8d068, 0x41c38241, 0x99b02999, 0x2d775a2d, 0x0f111e0f, 0xb0cb7bb0, 0x54fca854, 0xbbd66dbb, 0x163a2c16];
    var T4 = [0x6363a5c6, 0x7c7c84f8, 0x777799ee, 0x7b7b8df6, 0xf2f20dff, 0x6b6bbdd6, 0x6f6fb1de, 0xc5c55491, 0x30305060, 0x01010302, 0x6767a9ce, 0x2b2b7d56, 0xfefe19e7, 0xd7d762b5, 0xababe64d, 0x76769aec, 0xcaca458f, 0x82829d1f, 0xc9c94089, 0x7d7d87fa, 0xfafa15ef, 0x5959ebb2, 0x4747c98e, 0xf0f00bfb, 0xadadec41, 0xd4d467b3, 0xa2a2fd5f, 0xafafea45, 0x9c9cbf23, 0xa4a4f753, 0x727296e4, 0xc0c05b9b, 0xb7b7c275, 0xfdfd1ce1, 0x9393ae3d, 0x26266a4c, 0x36365a6c, 0x3f3f417e, 0xf7f702f5, 0xcccc4f83, 0x34345c68, 0xa5a5f451, 0xe5e534d1, 0xf1f108f9, 0x717193e2, 0xd8d873ab, 0x31315362, 0x15153f2a, 0x04040c08, 0xc7c75295, 0x23236546, 0xc3c35e9d, 0x18182830, 0x9696a137, 0x05050f0a, 0x9a9ab52f, 0x0707090e, 0x12123624, 0x80809b1b, 0xe2e23ddf, 0xebeb26cd, 0x2727694e, 0xb2b2cd7f, 0x75759fea, 0x09091b12, 0x83839e1d, 0x2c2c7458, 0x1a1a2e34, 0x1b1b2d36, 0x6e6eb2dc, 0x5a5aeeb4, 0xa0a0fb5b, 0x5252f6a4, 0x3b3b4d76, 0xd6d661b7, 0xb3b3ce7d, 0x29297b52, 0xe3e33edd, 0x2f2f715e, 0x84849713, 0x5353f5a6, 0xd1d168b9, 0x00000000, 0xeded2cc1, 0x20206040, 0xfcfc1fe3, 0xb1b1c879, 0x5b5bedb6, 0x6a6abed4, 0xcbcb468d, 0xbebed967, 0x39394b72, 0x4a4ade94, 0x4c4cd498, 0x5858e8b0, 0xcfcf4a85, 0xd0d06bbb, 0xefef2ac5, 0xaaaae54f, 0xfbfb16ed, 0x4343c586, 0x4d4dd79a, 0x33335566, 0x85859411, 0x4545cf8a, 0xf9f910e9, 0x02020604, 0x7f7f81fe, 0x5050f0a0, 0x3c3c4478, 0x9f9fba25, 0xa8a8e34b, 0x5151f3a2, 0xa3a3fe5d, 0x4040c080, 0x8f8f8a05, 0x9292ad3f, 0x9d9dbc21, 0x38384870, 0xf5f504f1, 0xbcbcdf63, 0xb6b6c177, 0xdada75af, 0x21216342, 0x10103020, 0xffff1ae5, 0xf3f30efd, 0xd2d26dbf, 0xcdcd4c81, 0x0c0c1418, 0x13133526, 0xecec2fc3, 0x5f5fe1be, 0x9797a235, 0x4444cc88, 0x1717392e, 0xc4c45793, 0xa7a7f255, 0x7e7e82fc, 0x3d3d477a, 0x6464acc8, 0x5d5de7ba, 0x19192b32, 0x737395e6, 0x6060a0c0, 0x81819819, 0x4f4fd19e, 0xdcdc7fa3, 0x22226644, 0x2a2a7e54, 0x9090ab3b, 0x8888830b, 0x4646ca8c, 0xeeee29c7, 0xb8b8d36b, 0x14143c28, 0xdede79a7, 0x5e5ee2bc, 0x0b0b1d16, 0xdbdb76ad, 0xe0e03bdb, 0x32325664, 0x3a3a4e74, 0x0a0a1e14, 0x4949db92, 0x06060a0c, 0x24246c48, 0x5c5ce4b8, 0xc2c25d9f, 0xd3d36ebd, 0xacacef43, 0x6262a6c4, 0x9191a839, 0x9595a431, 0xe4e437d3, 0x79798bf2, 0xe7e732d5, 0xc8c8438b, 0x3737596e, 0x6d6db7da, 0x8d8d8c01, 0xd5d564b1, 0x4e4ed29c, 0xa9a9e049, 0x6c6cb4d8, 0x5656faac, 0xf4f407f3, 0xeaea25cf, 0x6565afca, 0x7a7a8ef4, 0xaeaee947, 0x08081810, 0xbabad56f, 0x787888f0, 0x25256f4a, 0x2e2e725c, 0x1c1c2438, 0xa6a6f157, 0xb4b4c773, 0xc6c65197, 0xe8e823cb, 0xdddd7ca1, 0x74749ce8, 0x1f1f213e, 0x4b4bdd96, 0xbdbddc61, 0x8b8b860d, 0x8a8a850f, 0x707090e0, 0x3e3e427c, 0xb5b5c471, 0x6666aacc, 0x4848d890, 0x03030506, 0xf6f601f7, 0x0e0e121c, 0x6161a3c2, 0x35355f6a, 0x5757f9ae, 0xb9b9d069, 0x86869117, 0xc1c15899, 0x1d1d273a, 0x9e9eb927, 0xe1e138d9, 0xf8f813eb, 0x9898b32b, 0x11113322, 0x6969bbd2, 0xd9d970a9, 0x8e8e8907, 0x9494a733, 0x9b9bb62d, 0x1e1e223c, 0x87879215, 0xe9e920c9, 0xcece4987, 0x5555ffaa, 0x28287850, 0xdfdf7aa5, 0x8c8c8f03, 0xa1a1f859, 0x89898009, 0x0d0d171a, 0xbfbfda65, 0xe6e631d7, 0x4242c684, 0x6868b8d0, 0x4141c382, 0x9999b029, 0x2d2d775a, 0x0f0f111e, 0xb0b0cb7b, 0x5454fca8, 0xbbbbd66d, 0x16163a2c];

    var T5 = [0x51f4a750, 0x7e416553, 0x1a17a4c3, 0x3a275e96, 0x3bab6bcb, 0x1f9d45f1, 0xacfa58ab, 0x4be30393, 0x2030fa55, 0xad766df6, 0x88cc7691, 0xf5024c25, 0x4fe5d7fc, 0xc52acbd7, 0x26354480, 0xb562a38f, 0xdeb15a49, 0x25ba1b67, 0x45ea0e98, 0x5dfec0e1, 0xc32f7502, 0x814cf012, 0x8d4697a3, 0x6bd3f9c6, 0x038f5fe7, 0x15929c95, 0xbf6d7aeb, 0x955259da, 0xd4be832d, 0x587421d3, 0x49e06929, 0x8ec9c844, 0x75c2896a, 0xf48e7978, 0x99583e6b, 0x27b971dd, 0xbee14fb6, 0xf088ad17, 0xc920ac66, 0x7dce3ab4, 0x63df4a18, 0xe51a3182, 0x97513360, 0x62537f45, 0xb16477e0, 0xbb6bae84, 0xfe81a01c, 0xf9082b94, 0x70486858, 0x8f45fd19, 0x94de6c87, 0x527bf8b7, 0xab73d323, 0x724b02e2, 0xe31f8f57, 0x6655ab2a, 0xb2eb2807, 0x2fb5c203, 0x86c57b9a, 0xd33708a5, 0x302887f2, 0x23bfa5b2, 0x02036aba, 0xed16825c, 0x8acf1c2b, 0xa779b492, 0xf307f2f0, 0x4e69e2a1, 0x65daf4cd, 0x0605bed5, 0xd134621f, 0xc4a6fe8a, 0x342e539d, 0xa2f355a0, 0x058ae132, 0xa4f6eb75, 0x0b83ec39, 0x4060efaa, 0x5e719f06, 0xbd6e1051, 0x3e218af9, 0x96dd063d, 0xdd3e05ae, 0x4de6bd46, 0x91548db5, 0x71c45d05, 0x0406d46f, 0x605015ff, 0x1998fb24, 0xd6bde997, 0x894043cc, 0x67d99e77, 0xb0e842bd, 0x07898b88, 0xe7195b38, 0x79c8eedb, 0xa17c0a47, 0x7c420fe9, 0xf8841ec9, 0x00000000, 0x09808683, 0x322bed48, 0x1e1170ac, 0x6c5a724e, 0xfd0efffb, 0x0f853856, 0x3daed51e, 0x362d3927, 0x0a0fd964, 0x685ca621, 0x9b5b54d1, 0x24362e3a, 0x0c0a67b1, 0x9357e70f, 0xb4ee96d2, 0x1b9b919e, 0x80c0c54f, 0x61dc20a2, 0x5a774b69, 0x1c121a16, 0xe293ba0a, 0xc0a02ae5, 0x3c22e043, 0x121b171d, 0x0e090d0b, 0xf28bc7ad, 0x2db6a8b9, 0x141ea9c8, 0x57f11985, 0xaf75074c, 0xee99ddbb, 0xa37f60fd, 0xf701269f, 0x5c72f5bc, 0x44663bc5, 0x5bfb7e34, 0x8b432976, 0xcb23c6dc, 0xb6edfc68, 0xb8e4f163, 0xd731dcca, 0x42638510, 0x13972240, 0x84c61120, 0x854a247d, 0xd2bb3df8, 0xaef93211, 0xc729a16d, 0x1d9e2f4b, 0xdcb230f3, 0x0d8652ec, 0x77c1e3d0, 0x2bb3166c, 0xa970b999, 0x119448fa, 0x47e96422, 0xa8fc8cc4, 0xa0f03f1a, 0x567d2cd8, 0x223390ef, 0x87494ec7, 0xd938d1c1, 0x8ccaa2fe, 0x98d40b36, 0xa6f581cf, 0xa57ade28, 0xdab78e26, 0x3fadbfa4, 0x2c3a9de4, 0x5078920d, 0x6a5fcc9b, 0x547e4662, 0xf68d13c2, 0x90d8b8e8, 0x2e39f75e, 0x82c3aff5, 0x9f5d80be, 0x69d0937c, 0x6fd52da9, 0xcf2512b3, 0xc8ac993b, 0x10187da7, 0xe89c636e, 0xdb3bbb7b, 0xcd267809, 0x6e5918f4, 0xec9ab701, 0x834f9aa8, 0xe6956e65, 0xaaffe67e, 0x21bccf08, 0xef15e8e6, 0xbae79bd9, 0x4a6f36ce, 0xea9f09d4, 0x29b07cd6, 0x31a4b2af, 0x2a3f2331, 0xc6a59430, 0x35a266c0, 0x744ebc37, 0xfc82caa6, 0xe090d0b0, 0x33a7d815, 0xf104984a, 0x41ecdaf7, 0x7fcd500e, 0x1791f62f, 0x764dd68d, 0x43efb04d, 0xccaa4d54, 0xe49604df, 0x9ed1b5e3, 0x4c6a881b, 0xc12c1fb8, 0x4665517f, 0x9d5eea04, 0x018c355d, 0xfa877473, 0xfb0b412e, 0xb3671d5a, 0x92dbd252, 0xe9105633, 0x6dd64713, 0x9ad7618c, 0x37a10c7a, 0x59f8148e, 0xeb133c89, 0xcea927ee, 0xb761c935, 0xe11ce5ed, 0x7a47b13c, 0x9cd2df59, 0x55f2733f, 0x1814ce79, 0x73c737bf, 0x53f7cdea, 0x5ffdaa5b, 0xdf3d6f14, 0x7844db86, 0xcaaff381, 0xb968c43e, 0x3824342c, 0xc2a3405f, 0x161dc372, 0xbce2250c, 0x283c498b, 0xff0d9541, 0x39a80171, 0x080cb3de, 0xd8b4e49c, 0x6456c190, 0x7bcb8461, 0xd532b670, 0x486c5c74, 0xd0b85742];
    var T6 = [0x5051f4a7, 0x537e4165, 0xc31a17a4, 0x963a275e, 0xcb3bab6b, 0xf11f9d45, 0xabacfa58, 0x934be303, 0x552030fa, 0xf6ad766d, 0x9188cc76, 0x25f5024c, 0xfc4fe5d7, 0xd7c52acb, 0x80263544, 0x8fb562a3, 0x49deb15a, 0x6725ba1b, 0x9845ea0e, 0xe15dfec0, 0x02c32f75, 0x12814cf0, 0xa38d4697, 0xc66bd3f9, 0xe7038f5f, 0x9515929c, 0xebbf6d7a, 0xda955259, 0x2dd4be83, 0xd3587421, 0x2949e069, 0x448ec9c8, 0x6a75c289, 0x78f48e79, 0x6b99583e, 0xdd27b971, 0xb6bee14f, 0x17f088ad, 0x66c920ac, 0xb47dce3a, 0x1863df4a, 0x82e51a31, 0x60975133, 0x4562537f, 0xe0b16477, 0x84bb6bae, 0x1cfe81a0, 0x94f9082b, 0x58704868, 0x198f45fd, 0x8794de6c, 0xb7527bf8, 0x23ab73d3, 0xe2724b02, 0x57e31f8f, 0x2a6655ab, 0x07b2eb28, 0x032fb5c2, 0x9a86c57b, 0xa5d33708, 0xf2302887, 0xb223bfa5, 0xba02036a, 0x5ced1682, 0x2b8acf1c, 0x92a779b4, 0xf0f307f2, 0xa14e69e2, 0xcd65daf4, 0xd50605be, 0x1fd13462, 0x8ac4a6fe, 0x9d342e53, 0xa0a2f355, 0x32058ae1, 0x75a4f6eb, 0x390b83ec, 0xaa4060ef, 0x065e719f, 0x51bd6e10, 0xf93e218a, 0x3d96dd06, 0xaedd3e05, 0x464de6bd, 0xb591548d, 0x0571c45d, 0x6f0406d4, 0xff605015, 0x241998fb, 0x97d6bde9, 0xcc894043, 0x7767d99e, 0xbdb0e842, 0x8807898b, 0x38e7195b, 0xdb79c8ee, 0x47a17c0a, 0xe97c420f, 0xc9f8841e, 0x00000000, 0x83098086, 0x48322bed, 0xac1e1170, 0x4e6c5a72, 0xfbfd0eff, 0x560f8538, 0x1e3daed5, 0x27362d39, 0x640a0fd9, 0x21685ca6, 0xd19b5b54, 0x3a24362e, 0xb10c0a67, 0x0f9357e7, 0xd2b4ee96, 0x9e1b9b91, 0x4f80c0c5, 0xa261dc20, 0x695a774b, 0x161c121a, 0x0ae293ba, 0xe5c0a02a, 0x433c22e0, 0x1d121b17, 0x0b0e090d, 0xadf28bc7, 0xb92db6a8, 0xc8141ea9, 0x8557f119, 0x4caf7507, 0xbbee99dd, 0xfda37f60, 0x9ff70126, 0xbc5c72f5, 0xc544663b, 0x345bfb7e, 0x768b4329, 0xdccb23c6, 0x68b6edfc, 0x63b8e4f1, 0xcad731dc, 0x10426385, 0x40139722, 0x2084c611, 0x7d854a24, 0xf8d2bb3d, 0x11aef932, 0x6dc729a1, 0x4b1d9e2f, 0xf3dcb230, 0xec0d8652, 0xd077c1e3, 0x6c2bb316, 0x99a970b9, 0xfa119448, 0x2247e964, 0xc4a8fc8c, 0x1aa0f03f, 0xd8567d2c, 0xef223390, 0xc787494e, 0xc1d938d1, 0xfe8ccaa2, 0x3698d40b, 0xcfa6f581, 0x28a57ade, 0x26dab78e, 0xa43fadbf, 0xe42c3a9d, 0x0d507892, 0x9b6a5fcc, 0x62547e46, 0xc2f68d13, 0xe890d8b8, 0x5e2e39f7, 0xf582c3af, 0xbe9f5d80, 0x7c69d093, 0xa96fd52d, 0xb3cf2512, 0x3bc8ac99, 0xa710187d, 0x6ee89c63, 0x7bdb3bbb, 0x09cd2678, 0xf46e5918, 0x01ec9ab7, 0xa8834f9a, 0x65e6956e, 0x7eaaffe6, 0x0821bccf, 0xe6ef15e8, 0xd9bae79b, 0xce4a6f36, 0xd4ea9f09, 0xd629b07c, 0xaf31a4b2, 0x312a3f23, 0x30c6a594, 0xc035a266, 0x37744ebc, 0xa6fc82ca, 0xb0e090d0, 0x1533a7d8, 0x4af10498, 0xf741ecda, 0x0e7fcd50, 0x2f1791f6, 0x8d764dd6, 0x4d43efb0, 0x54ccaa4d, 0xdfe49604, 0xe39ed1b5, 0x1b4c6a88, 0xb8c12c1f, 0x7f466551, 0x049d5eea, 0x5d018c35, 0x73fa8774, 0x2efb0b41, 0x5ab3671d, 0x5292dbd2, 0x33e91056, 0x136dd647, 0x8c9ad761, 0x7a37a10c, 0x8e59f814, 0x89eb133c, 0xeecea927, 0x35b761c9, 0xede11ce5, 0x3c7a47b1, 0x599cd2df, 0x3f55f273, 0x791814ce, 0xbf73c737, 0xea53f7cd, 0x5b5ffdaa, 0x14df3d6f, 0x867844db, 0x81caaff3, 0x3eb968c4, 0x2c382434, 0x5fc2a340, 0x72161dc3, 0x0cbce225, 0x8b283c49, 0x41ff0d95, 0x7139a801, 0xde080cb3, 0x9cd8b4e4, 0x906456c1, 0x617bcb84, 0x70d532b6, 0x74486c5c, 0x42d0b857];
    var T7 = [0xa75051f4, 0x65537e41, 0xa4c31a17, 0x5e963a27, 0x6bcb3bab, 0x45f11f9d, 0x58abacfa, 0x03934be3, 0xfa552030, 0x6df6ad76, 0x769188cc, 0x4c25f502, 0xd7fc4fe5, 0xcbd7c52a, 0x44802635, 0xa38fb562, 0x5a49deb1, 0x1b6725ba, 0x0e9845ea, 0xc0e15dfe, 0x7502c32f, 0xf012814c, 0x97a38d46, 0xf9c66bd3, 0x5fe7038f, 0x9c951592, 0x7aebbf6d, 0x59da9552, 0x832dd4be, 0x21d35874, 0x692949e0, 0xc8448ec9, 0x896a75c2, 0x7978f48e, 0x3e6b9958, 0x71dd27b9, 0x4fb6bee1, 0xad17f088, 0xac66c920, 0x3ab47dce, 0x4a1863df, 0x3182e51a, 0x33609751, 0x7f456253, 0x77e0b164, 0xae84bb6b, 0xa01cfe81, 0x2b94f908, 0x68587048, 0xfd198f45, 0x6c8794de, 0xf8b7527b, 0xd323ab73, 0x02e2724b, 0x8f57e31f, 0xab2a6655, 0x2807b2eb, 0xc2032fb5, 0x7b9a86c5, 0x08a5d337, 0x87f23028, 0xa5b223bf, 0x6aba0203, 0x825ced16, 0x1c2b8acf, 0xb492a779, 0xf2f0f307, 0xe2a14e69, 0xf4cd65da, 0xbed50605, 0x621fd134, 0xfe8ac4a6, 0x539d342e, 0x55a0a2f3, 0xe132058a, 0xeb75a4f6, 0xec390b83, 0xefaa4060, 0x9f065e71, 0x1051bd6e, 0x8af93e21, 0x063d96dd, 0x05aedd3e, 0xbd464de6, 0x8db59154, 0x5d0571c4, 0xd46f0406, 0x15ff6050, 0xfb241998, 0xe997d6bd, 0x43cc8940, 0x9e7767d9, 0x42bdb0e8, 0x8b880789, 0x5b38e719, 0xeedb79c8, 0x0a47a17c, 0x0fe97c42, 0x1ec9f884, 0x00000000, 0x86830980, 0xed48322b, 0x70ac1e11, 0x724e6c5a, 0xfffbfd0e, 0x38560f85, 0xd51e3dae, 0x3927362d, 0xd9640a0f, 0xa621685c, 0x54d19b5b, 0x2e3a2436, 0x67b10c0a, 0xe70f9357, 0x96d2b4ee, 0x919e1b9b, 0xc54f80c0, 0x20a261dc, 0x4b695a77, 0x1a161c12, 0xba0ae293, 0x2ae5c0a0, 0xe0433c22, 0x171d121b, 0x0d0b0e09, 0xc7adf28b, 0xa8b92db6, 0xa9c8141e, 0x198557f1, 0x074caf75, 0xddbbee99, 0x60fda37f, 0x269ff701, 0xf5bc5c72, 0x3bc54466, 0x7e345bfb, 0x29768b43, 0xc6dccb23, 0xfc68b6ed, 0xf163b8e4, 0xdccad731, 0x85104263, 0x22401397, 0x112084c6, 0x247d854a, 0x3df8d2bb, 0x3211aef9, 0xa16dc729, 0x2f4b1d9e, 0x30f3dcb2, 0x52ec0d86, 0xe3d077c1, 0x166c2bb3, 0xb999a970, 0x48fa1194, 0x642247e9, 0x8cc4a8fc, 0x3f1aa0f0, 0x2cd8567d, 0x90ef2233, 0x4ec78749, 0xd1c1d938, 0xa2fe8cca, 0x0b3698d4, 0x81cfa6f5, 0xde28a57a, 0x8e26dab7, 0xbfa43fad, 0x9de42c3a, 0x920d5078, 0xcc9b6a5f, 0x4662547e, 0x13c2f68d, 0xb8e890d8, 0xf75e2e39, 0xaff582c3, 0x80be9f5d, 0x937c69d0, 0x2da96fd5, 0x12b3cf25, 0x993bc8ac, 0x7da71018, 0x636ee89c, 0xbb7bdb3b, 0x7809cd26, 0x18f46e59, 0xb701ec9a, 0x9aa8834f, 0x6e65e695, 0xe67eaaff, 0xcf0821bc, 0xe8e6ef15, 0x9bd9bae7, 0x36ce4a6f, 0x09d4ea9f, 0x7cd629b0, 0xb2af31a4, 0x23312a3f, 0x9430c6a5, 0x66c035a2, 0xbc37744e, 0xcaa6fc82, 0xd0b0e090, 0xd81533a7, 0x984af104, 0xdaf741ec, 0x500e7fcd, 0xf62f1791, 0xd68d764d, 0xb04d43ef, 0x4d54ccaa, 0x04dfe496, 0xb5e39ed1, 0x881b4c6a, 0x1fb8c12c, 0x517f4665, 0xea049d5e, 0x355d018c, 0x7473fa87, 0x412efb0b, 0x1d5ab367, 0xd25292db, 0x5633e910, 0x47136dd6, 0x618c9ad7, 0x0c7a37a1, 0x148e59f8, 0x3c89eb13, 0x27eecea9, 0xc935b761, 0xe5ede11c, 0xb13c7a47, 0xdf599cd2, 0x733f55f2, 0xce791814, 0x37bf73c7, 0xcdea53f7, 0xaa5b5ffd, 0x6f14df3d, 0xdb867844, 0xf381caaf, 0xc43eb968, 0x342c3824, 0x405fc2a3, 0xc372161d, 0x250cbce2, 0x498b283c, 0x9541ff0d, 0x017139a8, 0xb3de080c, 0xe49cd8b4, 0xc1906456, 0x84617bcb, 0xb670d532, 0x5c74486c, 0x5742d0b8];
    var T8 = [0xf4a75051, 0x4165537e, 0x17a4c31a, 0x275e963a, 0xab6bcb3b, 0x9d45f11f, 0xfa58abac, 0xe303934b, 0x30fa5520, 0x766df6ad, 0xcc769188, 0x024c25f5, 0xe5d7fc4f, 0x2acbd7c5, 0x35448026, 0x62a38fb5, 0xb15a49de, 0xba1b6725, 0xea0e9845, 0xfec0e15d, 0x2f7502c3, 0x4cf01281, 0x4697a38d, 0xd3f9c66b, 0x8f5fe703, 0x929c9515, 0x6d7aebbf, 0x5259da95, 0xbe832dd4, 0x7421d358, 0xe0692949, 0xc9c8448e, 0xc2896a75, 0x8e7978f4, 0x583e6b99, 0xb971dd27, 0xe14fb6be, 0x88ad17f0, 0x20ac66c9, 0xce3ab47d, 0xdf4a1863, 0x1a3182e5, 0x51336097, 0x537f4562, 0x6477e0b1, 0x6bae84bb, 0x81a01cfe, 0x082b94f9, 0x48685870, 0x45fd198f, 0xde6c8794, 0x7bf8b752, 0x73d323ab, 0x4b02e272, 0x1f8f57e3, 0x55ab2a66, 0xeb2807b2, 0xb5c2032f, 0xc57b9a86, 0x3708a5d3, 0x2887f230, 0xbfa5b223, 0x036aba02, 0x16825ced, 0xcf1c2b8a, 0x79b492a7, 0x07f2f0f3, 0x69e2a14e, 0xdaf4cd65, 0x05bed506, 0x34621fd1, 0xa6fe8ac4, 0x2e539d34, 0xf355a0a2, 0x8ae13205, 0xf6eb75a4, 0x83ec390b, 0x60efaa40, 0x719f065e, 0x6e1051bd, 0x218af93e, 0xdd063d96, 0x3e05aedd, 0xe6bd464d, 0x548db591, 0xc45d0571, 0x06d46f04, 0x5015ff60, 0x98fb2419, 0xbde997d6, 0x4043cc89, 0xd99e7767, 0xe842bdb0, 0x898b8807, 0x195b38e7, 0xc8eedb79, 0x7c0a47a1, 0x420fe97c, 0x841ec9f8, 0x00000000, 0x80868309, 0x2bed4832, 0x1170ac1e, 0x5a724e6c, 0x0efffbfd, 0x8538560f, 0xaed51e3d, 0x2d392736, 0x0fd9640a, 0x5ca62168, 0x5b54d19b, 0x362e3a24, 0x0a67b10c, 0x57e70f93, 0xee96d2b4, 0x9b919e1b, 0xc0c54f80, 0xdc20a261, 0x774b695a, 0x121a161c, 0x93ba0ae2, 0xa02ae5c0, 0x22e0433c, 0x1b171d12, 0x090d0b0e, 0x8bc7adf2, 0xb6a8b92d, 0x1ea9c814, 0xf1198557, 0x75074caf, 0x99ddbbee, 0x7f60fda3, 0x01269ff7, 0x72f5bc5c, 0x663bc544, 0xfb7e345b, 0x4329768b, 0x23c6dccb, 0xedfc68b6, 0xe4f163b8, 0x31dccad7, 0x63851042, 0x97224013, 0xc6112084, 0x4a247d85, 0xbb3df8d2, 0xf93211ae, 0x29a16dc7, 0x9e2f4b1d, 0xb230f3dc, 0x8652ec0d, 0xc1e3d077, 0xb3166c2b, 0x70b999a9, 0x9448fa11, 0xe9642247, 0xfc8cc4a8, 0xf03f1aa0, 0x7d2cd856, 0x3390ef22, 0x494ec787, 0x38d1c1d9, 0xcaa2fe8c, 0xd40b3698, 0xf581cfa6, 0x7ade28a5, 0xb78e26da, 0xadbfa43f, 0x3a9de42c, 0x78920d50, 0x5fcc9b6a, 0x7e466254, 0x8d13c2f6, 0xd8b8e890, 0x39f75e2e, 0xc3aff582, 0x5d80be9f, 0xd0937c69, 0xd52da96f, 0x2512b3cf, 0xac993bc8, 0x187da710, 0x9c636ee8, 0x3bbb7bdb, 0x267809cd, 0x5918f46e, 0x9ab701ec, 0x4f9aa883, 0x956e65e6, 0xffe67eaa, 0xbccf0821, 0x15e8e6ef, 0xe79bd9ba, 0x6f36ce4a, 0x9f09d4ea, 0xb07cd629, 0xa4b2af31, 0x3f23312a, 0xa59430c6, 0xa266c035, 0x4ebc3774, 0x82caa6fc, 0x90d0b0e0, 0xa7d81533, 0x04984af1, 0xecdaf741, 0xcd500e7f, 0x91f62f17, 0x4dd68d76, 0xefb04d43, 0xaa4d54cc, 0x9604dfe4, 0xd1b5e39e, 0x6a881b4c, 0x2c1fb8c1, 0x65517f46, 0x5eea049d, 0x8c355d01, 0x877473fa, 0x0b412efb, 0x671d5ab3, 0xdbd25292, 0x105633e9, 0xd647136d, 0xd7618c9a, 0xa10c7a37, 0xf8148e59, 0x133c89eb, 0xa927eece, 0x61c935b7, 0x1ce5ede1, 0x47b13c7a, 0xd2df599c, 0xf2733f55, 0x14ce7918, 0xc737bf73, 0xf7cdea53, 0xfdaa5b5f, 0x3d6f14df, 0x44db8678, 0xaff381ca, 0x68c43eb9, 0x24342c38, 0xa3405fc2, 0x1dc37216, 0xe2250cbc, 0x3c498b28, 0x0d9541ff, 0xa8017139, 0x0cb3de08, 0xb4e49cd8, 0x56c19064, 0xcb84617b, 0x32b670d5, 0x6c5c7448, 0xb85742d0];

    var U1 = [0x00000000, 0x0e090d0b, 0x1c121a16, 0x121b171d, 0x3824342c, 0x362d3927, 0x24362e3a, 0x2a3f2331, 0x70486858, 0x7e416553, 0x6c5a724e, 0x62537f45, 0x486c5c74, 0x4665517f, 0x547e4662, 0x5a774b69, 0xe090d0b0, 0xee99ddbb, 0xfc82caa6, 0xf28bc7ad, 0xd8b4e49c, 0xd6bde997, 0xc4a6fe8a, 0xcaaff381, 0x90d8b8e8, 0x9ed1b5e3, 0x8ccaa2fe, 0x82c3aff5, 0xa8fc8cc4, 0xa6f581cf, 0xb4ee96d2, 0xbae79bd9, 0xdb3bbb7b, 0xd532b670, 0xc729a16d, 0xc920ac66, 0xe31f8f57, 0xed16825c, 0xff0d9541, 0xf104984a, 0xab73d323, 0xa57ade28, 0xb761c935, 0xb968c43e, 0x9357e70f, 0x9d5eea04, 0x8f45fd19, 0x814cf012, 0x3bab6bcb, 0x35a266c0, 0x27b971dd, 0x29b07cd6, 0x038f5fe7, 0x0d8652ec, 0x1f9d45f1, 0x119448fa, 0x4be30393, 0x45ea0e98, 0x57f11985, 0x59f8148e, 0x73c737bf, 0x7dce3ab4, 0x6fd52da9, 0x61dc20a2, 0xad766df6, 0xa37f60fd, 0xb16477e0, 0xbf6d7aeb, 0x955259da, 0x9b5b54d1, 0x894043cc, 0x87494ec7, 0xdd3e05ae, 0xd33708a5, 0xc12c1fb8, 0xcf2512b3, 0xe51a3182, 0xeb133c89, 0xf9082b94, 0xf701269f, 0x4de6bd46, 0x43efb04d, 0x51f4a750, 0x5ffdaa5b, 0x75c2896a, 0x7bcb8461, 0x69d0937c, 0x67d99e77, 0x3daed51e, 0x33a7d815, 0x21bccf08, 0x2fb5c203, 0x058ae132, 0x0b83ec39, 0x1998fb24, 0x1791f62f, 0x764dd68d, 0x7844db86, 0x6a5fcc9b, 0x6456c190, 0x4e69e2a1, 0x4060efaa, 0x527bf8b7, 0x5c72f5bc, 0x0605bed5, 0x080cb3de, 0x1a17a4c3, 0x141ea9c8, 0x3e218af9, 0x302887f2, 0x223390ef, 0x2c3a9de4, 0x96dd063d, 0x98d40b36, 0x8acf1c2b, 0x84c61120, 0xaef93211, 0xa0f03f1a, 0xb2eb2807, 0xbce2250c, 0xe6956e65, 0xe89c636e, 0xfa877473, 0xf48e7978, 0xdeb15a49, 0xd0b85742, 0xc2a3405f, 0xccaa4d54, 0x41ecdaf7, 0x4fe5d7fc, 0x5dfec0e1, 0x53f7cdea, 0x79c8eedb, 0x77c1e3d0, 0x65daf4cd, 0x6bd3f9c6, 0x31a4b2af, 0x3fadbfa4, 0x2db6a8b9, 0x23bfa5b2, 0x09808683, 0x07898b88, 0x15929c95, 0x1b9b919e, 0xa17c0a47, 0xaf75074c, 0xbd6e1051, 0xb3671d5a, 0x99583e6b, 0x97513360, 0x854a247d, 0x8b432976, 0xd134621f, 0xdf3d6f14, 0xcd267809, 0xc32f7502, 0xe9105633, 0xe7195b38, 0xf5024c25, 0xfb0b412e, 0x9ad7618c, 0x94de6c87, 0x86c57b9a, 0x88cc7691, 0xa2f355a0, 0xacfa58ab, 0xbee14fb6, 0xb0e842bd, 0xea9f09d4, 0xe49604df, 0xf68d13c2, 0xf8841ec9, 0xd2bb3df8, 0xdcb230f3, 0xcea927ee, 0xc0a02ae5, 0x7a47b13c, 0x744ebc37, 0x6655ab2a, 0x685ca621, 0x42638510, 0x4c6a881b, 0x5e719f06, 0x5078920d, 0x0a0fd964, 0x0406d46f, 0x161dc372, 0x1814ce79, 0x322bed48, 0x3c22e043, 0x2e39f75e, 0x2030fa55, 0xec9ab701, 0xe293ba0a, 0xf088ad17, 0xfe81a01c, 0xd4be832d, 0xdab78e26, 0xc8ac993b, 0xc6a59430, 0x9cd2df59, 0x92dbd252, 0x80c0c54f, 0x8ec9c844, 0xa4f6eb75, 0xaaffe67e, 0xb8e4f163, 0xb6edfc68, 0x0c0a67b1, 0x02036aba, 0x10187da7, 0x1e1170ac, 0x342e539d, 0x3a275e96, 0x283c498b, 0x26354480, 0x7c420fe9, 0x724b02e2, 0x605015ff, 0x6e5918f4, 0x44663bc5, 0x4a6f36ce, 0x587421d3, 0x567d2cd8, 0x37a10c7a, 0x39a80171, 0x2bb3166c, 0x25ba1b67, 0x0f853856, 0x018c355d, 0x13972240, 0x1d9e2f4b, 0x47e96422, 0x49e06929, 0x5bfb7e34, 0x55f2733f, 0x7fcd500e, 0x71c45d05, 0x63df4a18, 0x6dd64713, 0xd731dcca, 0xd938d1c1, 0xcb23c6dc, 0xc52acbd7, 0xef15e8e6, 0xe11ce5ed, 0xf307f2f0, 0xfd0efffb, 0xa779b492, 0xa970b999, 0xbb6bae84, 0xb562a38f, 0x9f5d80be, 0x91548db5, 0x834f9aa8, 0x8d4697a3];
    var U2 = [0x00000000, 0x0b0e090d, 0x161c121a, 0x1d121b17, 0x2c382434, 0x27362d39, 0x3a24362e, 0x312a3f23, 0x58704868, 0x537e4165, 0x4e6c5a72, 0x4562537f, 0x74486c5c, 0x7f466551, 0x62547e46, 0x695a774b, 0xb0e090d0, 0xbbee99dd, 0xa6fc82ca, 0xadf28bc7, 0x9cd8b4e4, 0x97d6bde9, 0x8ac4a6fe, 0x81caaff3, 0xe890d8b8, 0xe39ed1b5, 0xfe8ccaa2, 0xf582c3af, 0xc4a8fc8c, 0xcfa6f581, 0xd2b4ee96, 0xd9bae79b, 0x7bdb3bbb, 0x70d532b6, 0x6dc729a1, 0x66c920ac, 0x57e31f8f, 0x5ced1682, 0x41ff0d95, 0x4af10498, 0x23ab73d3, 0x28a57ade, 0x35b761c9, 0x3eb968c4, 0x0f9357e7, 0x049d5eea, 0x198f45fd, 0x12814cf0, 0xcb3bab6b, 0xc035a266, 0xdd27b971, 0xd629b07c, 0xe7038f5f, 0xec0d8652, 0xf11f9d45, 0xfa119448, 0x934be303, 0x9845ea0e, 0x8557f119, 0x8e59f814, 0xbf73c737, 0xb47dce3a, 0xa96fd52d, 0xa261dc20, 0xf6ad766d, 0xfda37f60, 0xe0b16477, 0xebbf6d7a, 0xda955259, 0xd19b5b54, 0xcc894043, 0xc787494e, 0xaedd3e05, 0xa5d33708, 0xb8c12c1f, 0xb3cf2512, 0x82e51a31, 0x89eb133c, 0x94f9082b, 0x9ff70126, 0x464de6bd, 0x4d43efb0, 0x5051f4a7, 0x5b5ffdaa, 0x6a75c289, 0x617bcb84, 0x7c69d093, 0x7767d99e, 0x1e3daed5, 0x1533a7d8, 0x0821bccf, 0x032fb5c2, 0x32058ae1, 0x390b83ec, 0x241998fb, 0x2f1791f6, 0x8d764dd6, 0x867844db, 0x9b6a5fcc, 0x906456c1, 0xa14e69e2, 0xaa4060ef, 0xb7527bf8, 0xbc5c72f5, 0xd50605be, 0xde080cb3, 0xc31a17a4, 0xc8141ea9, 0xf93e218a, 0xf2302887, 0xef223390, 0xe42c3a9d, 0x3d96dd06, 0x3698d40b, 0x2b8acf1c, 0x2084c611, 0x11aef932, 0x1aa0f03f, 0x07b2eb28, 0x0cbce225, 0x65e6956e, 0x6ee89c63, 0x73fa8774, 0x78f48e79, 0x49deb15a, 0x42d0b857, 0x5fc2a340, 0x54ccaa4d, 0xf741ecda, 0xfc4fe5d7, 0xe15dfec0, 0xea53f7cd, 0xdb79c8ee, 0xd077c1e3, 0xcd65daf4, 0xc66bd3f9, 0xaf31a4b2, 0xa43fadbf, 0xb92db6a8, 0xb223bfa5, 0x83098086, 0x8807898b, 0x9515929c, 0x9e1b9b91, 0x47a17c0a, 0x4caf7507, 0x51bd6e10, 0x5ab3671d, 0x6b99583e, 0x60975133, 0x7d854a24, 0x768b4329, 0x1fd13462, 0x14df3d6f, 0x09cd2678, 0x02c32f75, 0x33e91056, 0x38e7195b, 0x25f5024c, 0x2efb0b41, 0x8c9ad761, 0x8794de6c, 0x9a86c57b, 0x9188cc76, 0xa0a2f355, 0xabacfa58, 0xb6bee14f, 0xbdb0e842, 0xd4ea9f09, 0xdfe49604, 0xc2f68d13, 0xc9f8841e, 0xf8d2bb3d, 0xf3dcb230, 0xeecea927, 0xe5c0a02a, 0x3c7a47b1, 0x37744ebc, 0x2a6655ab, 0x21685ca6, 0x10426385, 0x1b4c6a88, 0x065e719f, 0x0d507892, 0x640a0fd9, 0x6f0406d4, 0x72161dc3, 0x791814ce, 0x48322bed, 0x433c22e0, 0x5e2e39f7, 0x552030fa, 0x01ec9ab7, 0x0ae293ba, 0x17f088ad, 0x1cfe81a0, 0x2dd4be83, 0x26dab78e, 0x3bc8ac99, 0x30c6a594, 0x599cd2df, 0x5292dbd2, 0x4f80c0c5, 0x448ec9c8, 0x75a4f6eb, 0x7eaaffe6, 0x63b8e4f1, 0x68b6edfc, 0xb10c0a67, 0xba02036a, 0xa710187d, 0xac1e1170, 0x9d342e53, 0x963a275e, 0x8b283c49, 0x80263544, 0xe97c420f, 0xe2724b02, 0xff605015, 0xf46e5918, 0xc544663b, 0xce4a6f36, 0xd3587421, 0xd8567d2c, 0x7a37a10c, 0x7139a801, 0x6c2bb316, 0x6725ba1b, 0x560f8538, 0x5d018c35, 0x40139722, 0x4b1d9e2f, 0x2247e964, 0x2949e069, 0x345bfb7e, 0x3f55f273, 0x0e7fcd50, 0x0571c45d, 0x1863df4a, 0x136dd647, 0xcad731dc, 0xc1d938d1, 0xdccb23c6, 0xd7c52acb, 0xe6ef15e8, 0xede11ce5, 0xf0f307f2, 0xfbfd0eff, 0x92a779b4, 0x99a970b9, 0x84bb6bae, 0x8fb562a3, 0xbe9f5d80, 0xb591548d, 0xa8834f9a, 0xa38d4697];
    var U3 = [0x00000000, 0x0d0b0e09, 0x1a161c12, 0x171d121b, 0x342c3824, 0x3927362d, 0x2e3a2436, 0x23312a3f, 0x68587048, 0x65537e41, 0x724e6c5a, 0x7f456253, 0x5c74486c, 0x517f4665, 0x4662547e, 0x4b695a77, 0xd0b0e090, 0xddbbee99, 0xcaa6fc82, 0xc7adf28b, 0xe49cd8b4, 0xe997d6bd, 0xfe8ac4a6, 0xf381caaf, 0xb8e890d8, 0xb5e39ed1, 0xa2fe8cca, 0xaff582c3, 0x8cc4a8fc, 0x81cfa6f5, 0x96d2b4ee, 0x9bd9bae7, 0xbb7bdb3b, 0xb670d532, 0xa16dc729, 0xac66c920, 0x8f57e31f, 0x825ced16, 0x9541ff0d, 0x984af104, 0xd323ab73, 0xde28a57a, 0xc935b761, 0xc43eb968, 0xe70f9357, 0xea049d5e, 0xfd198f45, 0xf012814c, 0x6bcb3bab, 0x66c035a2, 0x71dd27b9, 0x7cd629b0, 0x5fe7038f, 0x52ec0d86, 0x45f11f9d, 0x48fa1194, 0x03934be3, 0x0e9845ea, 0x198557f1, 0x148e59f8, 0x37bf73c7, 0x3ab47dce, 0x2da96fd5, 0x20a261dc, 0x6df6ad76, 0x60fda37f, 0x77e0b164, 0x7aebbf6d, 0x59da9552, 0x54d19b5b, 0x43cc8940, 0x4ec78749, 0x05aedd3e, 0x08a5d337, 0x1fb8c12c, 0x12b3cf25, 0x3182e51a, 0x3c89eb13, 0x2b94f908, 0x269ff701, 0xbd464de6, 0xb04d43ef, 0xa75051f4, 0xaa5b5ffd, 0x896a75c2, 0x84617bcb, 0x937c69d0, 0x9e7767d9, 0xd51e3dae, 0xd81533a7, 0xcf0821bc, 0xc2032fb5, 0xe132058a, 0xec390b83, 0xfb241998, 0xf62f1791, 0xd68d764d, 0xdb867844, 0xcc9b6a5f, 0xc1906456, 0xe2a14e69, 0xefaa4060, 0xf8b7527b, 0xf5bc5c72, 0xbed50605, 0xb3de080c, 0xa4c31a17, 0xa9c8141e, 0x8af93e21, 0x87f23028, 0x90ef2233, 0x9de42c3a, 0x063d96dd, 0x0b3698d4, 0x1c2b8acf, 0x112084c6, 0x3211aef9, 0x3f1aa0f0, 0x2807b2eb, 0x250cbce2, 0x6e65e695, 0x636ee89c, 0x7473fa87, 0x7978f48e, 0x5a49deb1, 0x5742d0b8, 0x405fc2a3, 0x4d54ccaa, 0xdaf741ec, 0xd7fc4fe5, 0xc0e15dfe, 0xcdea53f7, 0xeedb79c8, 0xe3d077c1, 0xf4cd65da, 0xf9c66bd3, 0xb2af31a4, 0xbfa43fad, 0xa8b92db6, 0xa5b223bf, 0x86830980, 0x8b880789, 0x9c951592, 0x919e1b9b, 0x0a47a17c, 0x074caf75, 0x1051bd6e, 0x1d5ab367, 0x3e6b9958, 0x33609751, 0x247d854a, 0x29768b43, 0x621fd134, 0x6f14df3d, 0x7809cd26, 0x7502c32f, 0x5633e910, 0x5b38e719, 0x4c25f502, 0x412efb0b, 0x618c9ad7, 0x6c8794de, 0x7b9a86c5, 0x769188cc, 0x55a0a2f3, 0x58abacfa, 0x4fb6bee1, 0x42bdb0e8, 0x09d4ea9f, 0x04dfe496, 0x13c2f68d, 0x1ec9f884, 0x3df8d2bb, 0x30f3dcb2, 0x27eecea9, 0x2ae5c0a0, 0xb13c7a47, 0xbc37744e, 0xab2a6655, 0xa621685c, 0x85104263, 0x881b4c6a, 0x9f065e71, 0x920d5078, 0xd9640a0f, 0xd46f0406, 0xc372161d, 0xce791814, 0xed48322b, 0xe0433c22, 0xf75e2e39, 0xfa552030, 0xb701ec9a, 0xba0ae293, 0xad17f088, 0xa01cfe81, 0x832dd4be, 0x8e26dab7, 0x993bc8ac, 0x9430c6a5, 0xdf599cd2, 0xd25292db, 0xc54f80c0, 0xc8448ec9, 0xeb75a4f6, 0xe67eaaff, 0xf163b8e4, 0xfc68b6ed, 0x67b10c0a, 0x6aba0203, 0x7da71018, 0x70ac1e11, 0x539d342e, 0x5e963a27, 0x498b283c, 0x44802635, 0x0fe97c42, 0x02e2724b, 0x15ff6050, 0x18f46e59, 0x3bc54466, 0x36ce4a6f, 0x21d35874, 0x2cd8567d, 0x0c7a37a1, 0x017139a8, 0x166c2bb3, 0x1b6725ba, 0x38560f85, 0x355d018c, 0x22401397, 0x2f4b1d9e, 0x642247e9, 0x692949e0, 0x7e345bfb, 0x733f55f2, 0x500e7fcd, 0x5d0571c4, 0x4a1863df, 0x47136dd6, 0xdccad731, 0xd1c1d938, 0xc6dccb23, 0xcbd7c52a, 0xe8e6ef15, 0xe5ede11c, 0xf2f0f307, 0xfffbfd0e, 0xb492a779, 0xb999a970, 0xae84bb6b, 0xa38fb562, 0x80be9f5d, 0x8db59154, 0x9aa8834f, 0x97a38d46];
    var U4 = [0x00000000, 0x090d0b0e, 0x121a161c, 0x1b171d12, 0x24342c38, 0x2d392736, 0x362e3a24, 0x3f23312a, 0x48685870, 0x4165537e, 0x5a724e6c, 0x537f4562, 0x6c5c7448, 0x65517f46, 0x7e466254, 0x774b695a, 0x90d0b0e0, 0x99ddbbee, 0x82caa6fc, 0x8bc7adf2, 0xb4e49cd8, 0xbde997d6, 0xa6fe8ac4, 0xaff381ca, 0xd8b8e890, 0xd1b5e39e, 0xcaa2fe8c, 0xc3aff582, 0xfc8cc4a8, 0xf581cfa6, 0xee96d2b4, 0xe79bd9ba, 0x3bbb7bdb, 0x32b670d5, 0x29a16dc7, 0x20ac66c9, 0x1f8f57e3, 0x16825ced, 0x0d9541ff, 0x04984af1, 0x73d323ab, 0x7ade28a5, 0x61c935b7, 0x68c43eb9, 0x57e70f93, 0x5eea049d, 0x45fd198f, 0x4cf01281, 0xab6bcb3b, 0xa266c035, 0xb971dd27, 0xb07cd629, 0x8f5fe703, 0x8652ec0d, 0x9d45f11f, 0x9448fa11, 0xe303934b, 0xea0e9845, 0xf1198557, 0xf8148e59, 0xc737bf73, 0xce3ab47d, 0xd52da96f, 0xdc20a261, 0x766df6ad, 0x7f60fda3, 0x6477e0b1, 0x6d7aebbf, 0x5259da95, 0x5b54d19b, 0x4043cc89, 0x494ec787, 0x3e05aedd, 0x3708a5d3, 0x2c1fb8c1, 0x2512b3cf, 0x1a3182e5, 0x133c89eb, 0x082b94f9, 0x01269ff7, 0xe6bd464d, 0xefb04d43, 0xf4a75051, 0xfdaa5b5f, 0xc2896a75, 0xcb84617b, 0xd0937c69, 0xd99e7767, 0xaed51e3d, 0xa7d81533, 0xbccf0821, 0xb5c2032f, 0x8ae13205, 0x83ec390b, 0x98fb2419, 0x91f62f17, 0x4dd68d76, 0x44db8678, 0x5fcc9b6a, 0x56c19064, 0x69e2a14e, 0x60efaa40, 0x7bf8b752, 0x72f5bc5c, 0x05bed506, 0x0cb3de08, 0x17a4c31a, 0x1ea9c814, 0x218af93e, 0x2887f230, 0x3390ef22, 0x3a9de42c, 0xdd063d96, 0xd40b3698, 0xcf1c2b8a, 0xc6112084, 0xf93211ae, 0xf03f1aa0, 0xeb2807b2, 0xe2250cbc, 0x956e65e6, 0x9c636ee8, 0x877473fa, 0x8e7978f4, 0xb15a49de, 0xb85742d0, 0xa3405fc2, 0xaa4d54cc, 0xecdaf741, 0xe5d7fc4f, 0xfec0e15d, 0xf7cdea53, 0xc8eedb79, 0xc1e3d077, 0xdaf4cd65, 0xd3f9c66b, 0xa4b2af31, 0xadbfa43f, 0xb6a8b92d, 0xbfa5b223, 0x80868309, 0x898b8807, 0x929c9515, 0x9b919e1b, 0x7c0a47a1, 0x75074caf, 0x6e1051bd, 0x671d5ab3, 0x583e6b99, 0x51336097, 0x4a247d85, 0x4329768b, 0x34621fd1, 0x3d6f14df, 0x267809cd, 0x2f7502c3, 0x105633e9, 0x195b38e7, 0x024c25f5, 0x0b412efb, 0xd7618c9a, 0xde6c8794, 0xc57b9a86, 0xcc769188, 0xf355a0a2, 0xfa58abac, 0xe14fb6be, 0xe842bdb0, 0x9f09d4ea, 0x9604dfe4, 0x8d13c2f6, 0x841ec9f8, 0xbb3df8d2, 0xb230f3dc, 0xa927eece, 0xa02ae5c0, 0x47b13c7a, 0x4ebc3774, 0x55ab2a66, 0x5ca62168, 0x63851042, 0x6a881b4c, 0x719f065e, 0x78920d50, 0x0fd9640a, 0x06d46f04, 0x1dc37216, 0x14ce7918, 0x2bed4832, 0x22e0433c, 0x39f75e2e, 0x30fa5520, 0x9ab701ec, 0x93ba0ae2, 0x88ad17f0, 0x81a01cfe, 0xbe832dd4, 0xb78e26da, 0xac993bc8, 0xa59430c6, 0xd2df599c, 0xdbd25292, 0xc0c54f80, 0xc9c8448e, 0xf6eb75a4, 0xffe67eaa, 0xe4f163b8, 0xedfc68b6, 0x0a67b10c, 0x036aba02, 0x187da710, 0x1170ac1e, 0x2e539d34, 0x275e963a, 0x3c498b28, 0x35448026, 0x420fe97c, 0x4b02e272, 0x5015ff60, 0x5918f46e, 0x663bc544, 0x6f36ce4a, 0x7421d358, 0x7d2cd856, 0xa10c7a37, 0xa8017139, 0xb3166c2b, 0xba1b6725, 0x8538560f, 0x8c355d01, 0x97224013, 0x9e2f4b1d, 0xe9642247, 0xe0692949, 0xfb7e345b, 0xf2733f55, 0xcd500e7f, 0xc45d0571, 0xdf4a1863, 0xd647136d, 0x31dccad7, 0x38d1c1d9, 0x23c6dccb, 0x2acbd7c5, 0x15e8e6ef, 0x1ce5ede1, 0x07f2f0f3, 0x0efffbfd, 0x79b492a7, 0x70b999a9, 0x6bae84bb, 0x62a38fb5, 0x5d80be9f, 0x548db591, 0x4f9aa883, 0x4697a38d];

    function convertToInt32(bytes) {
        var result = [];
        for (var i = 0; i < bytes.length; i += 4) {
            result.push(
                (bytes[i    ] << 24) |
                (bytes[i + 1] << 16) |
                (bytes[i + 2] <<  8) |
                bytes[i + 3]
            );
        }
        return result;
    }

    var AES = function(key) {
        if (!(this instanceof AES)) {
            throw Error('AES must be instanitated with `new`');
        }

        Object.defineProperty(this, 'key', {
            value: coerceArray(key, true)
        });

        this._prepare();
    }


    AES.prototype._prepare = function() {

        var rounds = numberOfRounds[this.key.length];
        if (rounds == null) {
            throw new Error('invalid key size (must be 16, 24 or 32 bytes)');
        }

        this._Ke = [];

        this._Kd = [];

        for (var i = 0; i <= rounds; i++) {
            this._Ke.push([0, 0, 0, 0]);
            this._Kd.push([0, 0, 0, 0]);
        }

        var roundKeyCount = (rounds + 1) * 4;
        var KC = this.key.length / 4;

        var tk = convertToInt32(this.key);

        var index;
        for (var i = 0; i < KC; i++) {
            index = i >> 2;
            this._Ke[index][i % 4] = tk[i];
            this._Kd[rounds - index][i % 4] = tk[i];
        }

        var rconpointer = 0;
        var t = KC, tt;
        while (t < roundKeyCount) {
            tt = tk[KC - 1];
            tk[0] ^= ((S[(tt >> 16) & 0xFF] << 24) ^
                (S[(tt >>  8) & 0xFF] << 16) ^
                (S[ tt        & 0xFF] <<  8) ^
                S[(tt >> 24) & 0xFF]        ^
                (rcon[rconpointer] << 24));
            rconpointer += 1;

            if (KC != 8) {
                for (var i = 1; i < KC; i++) {
                    tk[i] ^= tk[i - 1];
                }

            } else {
                for (var i = 1; i < (KC / 2); i++) {
                    tk[i] ^= tk[i - 1];
                }
                tt = tk[(KC / 2) - 1];

                tk[KC / 2] ^= (S[ tt        & 0xFF]        ^
                    (S[(tt >>  8) & 0xFF] <<  8) ^
                    (S[(tt >> 16) & 0xFF] << 16) ^
                    (S[(tt >> 24) & 0xFF] << 24));

                for (var i = (KC / 2) + 1; i < KC; i++) {
                    tk[i] ^= tk[i - 1];
                }
            }

            var i = 0, r, c;
            while (i < KC && t < roundKeyCount) {
                r = t >> 2;
                c = t % 4;
                this._Ke[r][c] = tk[i];
                this._Kd[rounds - r][c] = tk[i++];
                t++;
            }
        }

        for (var r = 1; r < rounds; r++) {
            for (var c = 0; c < 4; c++) {
                tt = this._Kd[r][c];
                this._Kd[r][c] = (U1[(tt >> 24) & 0xFF] ^
                    U2[(tt >> 16) & 0xFF] ^
                    U3[(tt >>  8) & 0xFF] ^
                    U4[ tt        & 0xFF]);
            }
        }
    }

    AES.prototype.encrypt = function(plaintext) {
        if (plaintext.length != 16) {
            throw new Error('invalid plaintext size (must be 16 bytes)');
        }

        var rounds = this._Ke.length - 1;
        var a = [0, 0, 0, 0];

        var t = convertToInt32(plaintext);
        for (var i = 0; i < 4; i++) {
            t[i] ^= this._Ke[0][i];
        }

        for (var r = 1; r < rounds; r++) {
            for (var i = 0; i < 4; i++) {
                a[i] = (T1[(t[ i         ] >> 24) & 0xff] ^
                    T2[(t[(i + 1) % 4] >> 16) & 0xff] ^
                    T3[(t[(i + 2) % 4] >>  8) & 0xff] ^
                    T4[ t[(i + 3) % 4]        & 0xff] ^
                    this._Ke[r][i]);
            }
            t = a.slice();
        }

        var result = createArray(16), tt;
        for (var i = 0; i < 4; i++) {
            tt = this._Ke[rounds][i];
            result[4 * i    ] = (S[(t[ i         ] >> 24) & 0xff] ^ (tt >> 24)) & 0xff;
            result[4 * i + 1] = (S[(t[(i + 1) % 4] >> 16) & 0xff] ^ (tt >> 16)) & 0xff;
            result[4 * i + 2] = (S[(t[(i + 2) % 4] >>  8) & 0xff] ^ (tt >>  8)) & 0xff;
            result[4 * i + 3] = (S[ t[(i + 3) % 4]        & 0xff] ^  tt       ) & 0xff;
        }

        return result;
    }

    AES.prototype.decrypt = function(ciphertext) {
        if (ciphertext.length != 16) {
            throw new Error('invalid ciphertext size (must be 16 bytes)');
        }

        var rounds = this._Kd.length - 1;
        var a = [0, 0, 0, 0];

        var t = convertToInt32(ciphertext);
        for (var i = 0; i < 4; i++) {
            t[i] ^= this._Kd[0][i];
        }

        for (var r = 1; r < rounds; r++) {
            for (var i = 0; i < 4; i++) {
                a[i] = (T5[(t[ i          ] >> 24) & 0xff] ^
                    T6[(t[(i + 3) % 4] >> 16) & 0xff] ^
                    T7[(t[(i + 2) % 4] >>  8) & 0xff] ^
                    T8[ t[(i + 1) % 4]        & 0xff] ^
                    this._Kd[r][i]);
            }
            t = a.slice();
        }

        var result = createArray(16), tt;
        for (var i = 0; i < 4; i++) {
            tt = this._Kd[rounds][i];
            result[4 * i    ] = (Si[(t[ i         ] >> 24) & 0xff] ^ (tt >> 24)) & 0xff;
            result[4 * i + 1] = (Si[(t[(i + 3) % 4] >> 16) & 0xff] ^ (tt >> 16)) & 0xff;
            result[4 * i + 2] = (Si[(t[(i + 2) % 4] >>  8) & 0xff] ^ (tt >>  8)) & 0xff;
            result[4 * i + 3] = (Si[ t[(i + 1) % 4]        & 0xff] ^  tt       ) & 0xff;
        }

        return result;
    }


    var ModeOfOperationECB = function(key) {
        if (!(this instanceof ModeOfOperationECB)) {
            throw Error('AES must be instanitated with `new`');
        }

        this.description = "Electronic Code Block";
        this.name = "ecb";

        this._aes = new AES(key);
    }

    ModeOfOperationECB.prototype.encrypt = function(plaintext) {
        plaintext = coerceArray(plaintext);

        if ((plaintext.length % 16) !== 0) {
            throw new Error('invalid plaintext size (must be multiple of 16 bytes)');
        }

        var ciphertext = createArray(plaintext.length);
        var block = createArray(16);

        for (var i = 0; i < plaintext.length; i += 16) {
            copyArray(plaintext, block, 0, i, i + 16);
            block = this._aes.encrypt(block);
            copyArray(block, ciphertext, i);
        }

        return ciphertext;
    }

    ModeOfOperationECB.prototype.decrypt = function(ciphertext) {
        ciphertext = coerceArray(ciphertext);

        if ((ciphertext.length % 16) !== 0) {
            throw new Error('invalid ciphertext size (must be multiple of 16 bytes)');
        }

        var plaintext = createArray(ciphertext.length);
        var block = createArray(16);

        for (var i = 0; i < ciphertext.length; i += 16) {
            copyArray(ciphertext, block, 0, i, i + 16);
            block = this._aes.decrypt(block);
            copyArray(block, plaintext, i);
        }

        return plaintext;
    }



    var ModeOfOperationCBC = function(key, iv) {
        if (!(this instanceof ModeOfOperationCBC)) {
            throw Error('AES must be instanitated with `new`');
        }

        this.description = "Cipher Block Chaining";
        this.name = "cbc";

        if (!iv) {
            iv = createArray(16);

        } else if (iv.length != 16) {
            throw new Error('invalid initialation vector size (must be 16 bytes)');
        }

        this._lastCipherblock = coerceArray(iv, true);

        this._aes = new AES(key);
    }

    ModeOfOperationCBC.prototype.encrypt = function(plaintext) {
        plaintext = coerceArray(plaintext);

        if ((plaintext.length % 16) !== 0) {
            throw new Error('invalid plaintext size (must be multiple of 16 bytes)');
        }

        var ciphertext = createArray(plaintext.length);
        var block = createArray(16);

        for (var i = 0; i < plaintext.length; i += 16) {
            copyArray(plaintext, block, 0, i, i + 16);

            for (var j = 0; j < 16; j++) {
                block[j] ^= this._lastCipherblock[j];
            }

            this._lastCipherblock = this._aes.encrypt(block);
            copyArray(this._lastCipherblock, ciphertext, i);
        }

        return ciphertext;
    }

    ModeOfOperationCBC.prototype.decrypt = function(ciphertext) {
        ciphertext = coerceArray(ciphertext);

        if ((ciphertext.length % 16) !== 0) {
            throw new Error('invalid ciphertext size (must be multiple of 16 bytes)');
        }

        var plaintext = createArray(ciphertext.length);
        var block = createArray(16);

        for (var i = 0; i < ciphertext.length; i += 16) {
            copyArray(ciphertext, block, 0, i, i + 16);
            block = this._aes.decrypt(block);

            for (var j = 0; j < 16; j++) {
                plaintext[i + j] = block[j] ^ this._lastCipherblock[j];
            }

            copyArray(ciphertext, this._lastCipherblock, 0, i, i + 16);
        }

        return plaintext;
    }

    var ModeOfOperationCFB = function(key, iv, segmentSize) {
        if (!(this instanceof ModeOfOperationCFB)) {
            throw Error('AES must be instanitated with `new`');
        }

        this.description = "Cipher Feedback";
        this.name = "cfb";

        if (!iv) {
            iv = createArray(16);

        } else if (iv.length != 16) {
            throw new Error('invalid initialation vector size (must be 16 size)');
        }

        if (!segmentSize) { segmentSize = 1; }

        this.segmentSize = segmentSize;

        this._shiftRegister = coerceArray(iv, true);

        this._aes = new AES(key);
    }

    ModeOfOperationCFB.prototype.encrypt = function(plaintext) {
        if ((plaintext.length % this.segmentSize) != 0) {
            throw new Error('invalid plaintext size (must be segmentSize bytes)');
        }

        var encrypted = coerceArray(plaintext, true);

        var xorSegment;
        for (var i = 0; i < encrypted.length; i += this.segmentSize) {
            xorSegment = this._aes.encrypt(this._shiftRegister);
            for (var j = 0; j < this.segmentSize; j++) {
                encrypted[i + j] ^= xorSegment[j];
            }

            copyArray(this._shiftRegister, this._shiftRegister, 0, this.segmentSize);
            copyArray(encrypted, this._shiftRegister, 16 - this.segmentSize, i, i + this.segmentSize);
        }

        return encrypted;
    }

    ModeOfOperationCFB.prototype.decrypt = function(ciphertext) {
        if ((ciphertext.length % this.segmentSize) != 0) {
            throw new Error('invalid ciphertext size (must be segmentSize bytes)');
        }

        var plaintext = coerceArray(ciphertext, true);

        var xorSegment;
        for (var i = 0; i < plaintext.length; i += this.segmentSize) {
            xorSegment = this._aes.encrypt(this._shiftRegister);

            for (var j = 0; j < this.segmentSize; j++) {
                plaintext[i + j] ^= xorSegment[j];
            }

            copyArray(this._shiftRegister, this._shiftRegister, 0, this.segmentSize);
            copyArray(ciphertext, this._shiftRegister, 16 - this.segmentSize, i, i + this.segmentSize);
        }

        return plaintext;
    }


    var ModeOfOperationOFB = function(key, iv) {
        if (!(this instanceof ModeOfOperationOFB)) {
            throw Error('AES must be instanitated with `new`');
        }

        this.description = "Output Feedback";
        this.name = "ofb";

        if (!iv) {
            iv = createArray(16);

        } else if (iv.length != 16) {
            throw new Error('invalid initialation vector size (must be 16 bytes)');
        }

        this._lastPrecipher = coerceArray(iv, true);
        this._lastPrecipherIndex = 16;

        this._aes = new AES(key);
    }

    ModeOfOperationOFB.prototype.encrypt = function(plaintext) {
        var encrypted = coerceArray(plaintext, true);

        for (var i = 0; i < encrypted.length; i++) {
            if (this._lastPrecipherIndex === 16) {
                this._lastPrecipher = this._aes.encrypt(this._lastPrecipher);
                this._lastPrecipherIndex = 0;
            }
            encrypted[i] ^= this._lastPrecipher[this._lastPrecipherIndex++];
        }

        return encrypted;
    }

    ModeOfOperationOFB.prototype.decrypt = ModeOfOperationOFB.prototype.encrypt;



    var Counter = function(initialValue) {
        if (!(this instanceof Counter)) {
            throw Error('Counter must be instanitated with `new`');
        }

        if (initialValue !== 0 && !initialValue) { initialValue = 1; }

        if (typeof(initialValue) === 'number') {
            this._counter = createArray(16);
            this.setValue(initialValue);

        } else {
            this.setBytes(initialValue);
        }
    }

    Counter.prototype.setValue = function(value) {
        if (typeof(value) !== 'number' || parseInt(value) != value) {
            throw new Error('invalid counter value (must be an integer)');
        }

        for (var index = 15; index >= 0; --index) {
            this._counter[index] = value % 256;
            value = value >> 8;
        }
    }

    Counter.prototype.setBytes = function(bytes) {
        bytes = coerceArray(bytes, true);

        if (bytes.length != 16) {
            throw new Error('invalid counter bytes size (must be 16 bytes)');
        }

        this._counter = bytes;
    };

    Counter.prototype.increment = function() {
        for (var i = 15; i >= 0; i--) {
            if (this._counter[i] === 255) {
                this._counter[i] = 0;
            } else {
                this._counter[i]++;
                break;
            }
        }
    }



    var ModeOfOperationCTR = function(key, counter) {
        if (!(this instanceof ModeOfOperationCTR)) {
            throw Error('AES must be instanitated with `new`');
        }

        this.description = "Counter";
        this.name = "ctr";

        if (!(counter instanceof Counter)) {
            counter = new Counter(counter)
        }

        this._counter = counter;

        this._remainingCounter = null;
        this._remainingCounterIndex = 16;

        this._aes = new AES(key);
    }

    ModeOfOperationCTR.prototype.encrypt = function(plaintext) {
        var encrypted = coerceArray(plaintext, true);

        for (var i = 0; i < encrypted.length; i++) {
            if (this._remainingCounterIndex === 16) {
                this._remainingCounter = this._aes.encrypt(this._counter._counter);
                this._remainingCounterIndex = 0;
                this._counter.increment();
            }
            encrypted[i] ^= this._remainingCounter[this._remainingCounterIndex++];
        }

        return encrypted;
    }

    ModeOfOperationCTR.prototype.decrypt = ModeOfOperationCTR.prototype.encrypt;


    function pkcs7pad(data) {
        data = coerceArray(data, true);
        var padder = 16 - (data.length % 16);
        var result = createArray(data.length + padder);
        copyArray(data, result);
        for (var i = data.length; i < result.length; i++) {
            result[i] = padder;
        }
        return result;
    }

    function pkcs7strip(data) {
        data = coerceArray(data, true);
        if (data.length < 16) { throw new Error('PKCS#7 invalid length'); }

        var padder = data[data.length - 1];
        if (padder > 16) { throw new Error('PKCS#7 padding byte out of range'); }

        var length = data.length - padder;
        for (var i = 0; i < padder; i++) {
            if (data[length + i] !== padder) {
                throw new Error('PKCS#7 invalid padding byte');
            }
        }

        var result = createArray(length);
        copyArray(data, result, 0, 0, length);
        return result;
    }


    var aesjs = {
        AES: AES,
        Counter: Counter,

        ModeOfOperation: {
            ecb: ModeOfOperationECB,
            cbc: ModeOfOperationCBC,
            cfb: ModeOfOperationCFB,
            ofb: ModeOfOperationOFB,
            ctr: ModeOfOperationCTR
        },

        utils: {
            hex: convertHex,
            utf8: convertUtf8
        },

        padding: {
            pkcs7: {
                pad: pkcs7pad,
                strip: pkcs7strip
            }
        },

        _arrayTest: {
            coerceArray: coerceArray,
            createArray: createArray,
            copyArray: copyArray,
        }
    };

    return {aesjs:aesjs};
})(this);
