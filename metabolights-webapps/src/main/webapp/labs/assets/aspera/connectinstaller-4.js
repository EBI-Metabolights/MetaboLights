/*
 * Revision: 3.6.1.111494
 * Revision date: 09/14/2015
 *
 * http://www.asperasoft.com/
 *
 * Â© Copyright IBM Corp. 2008, 2015, Copyright (C) 1991, 1999 Free Software Foundation, Inc.
 */

"use strict";

if (typeof AW4 === "undefined") var AW4 = {};


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
      FASP_URL = "fasp://initialize",
  ////////////////////////////////////////////////////////////////////////////////////////
  // Browser helpers
  // https://github.com/ded/bowser
  // MIT License | (c) Dustin Diaz 2014
  ////////////////////////////////////////////////////////////////////////////////////////
      ua = typeof navigator !== 'undefined' ? navigator.userAgent : '',

      /**
       * AW4.Utils.BROWSER -> Object
       *
       * Contains the type of browser that we are currently on (based on user agent):
       *
       * 1. `AW4.Utils.BROWSER.OPERA` (`Boolean`)
       * 2. `AW4.Utils.BROWSER.IE` (`Boolean`)
       * 3. `AW4.Utils.BROWSER.CHROME` (`Boolean`)
       * 4. `AW4.Utils.BROWSER.FIREFOX` (`Boolean`)
       * 5. `AW4.Utils.BROWSER.SAFARI` (`Boolean`)
       **/
      BROWSER = {
        OPERA: /opera|opr/i.test(ua),
        IE: /msie|trident/i.test(ua),
        CHROME: /chrome|crios|crmo/i.test(ua) && !/opera|opr/i.test(ua),
        FIREFOX: /firefox|iceweasel/i.test(ua),
        SAFARI: /safari/i.test(ua)
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
   */
  var versionLessThan = function(a, b) {
    var versionToArray = function( version ) {
      var splits = version.split(".");
      var versionArray = new Array();
      for (var i = 0; i < splits.length; i++) {
        if (isNaN(parseInt(splits[i]))) {
          AW.utils.logger('Warning: Version contains non-numbers');
        }
        versionArray.push(parseInt(splits[i],10));
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

  /*
   * PT - I'm not sure if this is as good as a generator that has system
   * access, but the generated IDs should be unique enough.
   *
   * @returns ID string
   */
  var generateUuid = function() {
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

  /**
   * AW4.Utils.launchConnect(callback) -> null
   *  - callback (function):  It will be called once we have determined if
   *  connect is installed in the system.
   *
   * Attempt to launch connect. Returns true if Aspera Connect is installed.
   * Given the different implementation on each browser, we can only
   * deterministically decide if the application is installed, in which case
   * true is returned, if the result is false is because either we couldn't
   * detect that Aspera Connect is installed or because it is not installed.
   *
   * ##### Object returned to success callback as parameter
   *
   * 1. `true` : if Aspera Connect is installed
   * 2. `false` : if Aspera Connect is either not installed or we couldn't
   * detect it.
   **/
  var launchConnect = function(userCallback) {
    var callback = function(installed) {
      if (typeof userCallback === 'function') {
        userCallback(installed);
      }
    }
    var isRegistered = false;
    if (BROWSER.CHROME || BROWSER.OPERA) {
      document.body.focus();
      document.body.onblur = function() {
        isRegistered = true;
      };
      //will trigger onblur
      document.location = FASP_URL;
      //Note: timeout could vary as per the browser version, have a higher value
      setTimeout(function(){
        document.body.onblur = null;
        callback(isRegistered);
      }, 500);
    }
    //Other browsers use NPAPI/ActiveX to open connect
    // } else if (BROWSER.FIREFOX) {
    //   var iframe = document.createElement('iframe');
    //   iframe.style.display = 'none';
    //   document.body.appendChild(iframe);
    //   //Set iframe.src and handle exception
    //   try {
    //     iframe.contentWindow.location.href = FASP_URL;
    //     isRegistered = true;
    //   } catch(e) {}
    //   callback(isRegistered);
    // } else if (BROWSER.IE) {
    //   //IE10+ in Windows 8+
    //   if (navigator.msLaunchUri) {
    //     navigator.msLaunchUri(FASP_URL,
    //       function(){ callback(true);},
    //       function(){ callback(false);}
    //     );
    //   } else {
    //     var myWindow = window.open('','','width=0,height=0');
    //     myWindow.document.write("<iframe src='"+ FASP_URL + "></iframe>");
    //     setTimeout(function(){
    //       try{
    //         myWindow.location.href;
    //         isRegistered = true;
    //       }catch(e){
    //         //Handle Exception
    //       }
    //       myWindow.close();
    //       callback(isRegistered);
    //     });
    //   }
    // } else if (BROWSER.SAFARI) {

    // } else {
    //   //Default application launch, consider we couldn't launch connect
    //   //for compatibility reasons
    //   window.location.href = FASP_URL;
    //   callback(false);
    // }
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

  ////////////////////////////////////////////////////////////////////////////////////////
  // PUBLIC
  ////////////////////////////////////////////////////////////////////////////////////////

  // The symbols to export.
  return {
    //CONSTANTS
    FASP_URL: FASP_URL,
    BROWSER: BROWSER,
    //FUNCTIONS
    versionLessThan: versionLessThan,
    createError: createError,
    generateUuid: generateUuid,
    launchConnect: launchConnect,
    parseJson: parseJson,
    getFullURI: getFullURI
  };
})();
/**
 * == API ==
 **/

/** section: API
 * AW4
 *
 * The Aspera Web namespace.
 **/

/** section: API
 * class AW4.ConnectInstaller
 *
 * The [[AW4.ConnectInstaller]] class offers support for connect installation.
 **/

/**
 * new AW4.ConnectInstaller([options])
 * - options (Object): Configuration parameters for the plug-in.
 *
 * Creates a new [[AW4.ConnectInstaller]] object.
 *
 * ##### Options
 *
 * 1. `sdkLocation` (`String`):
 *     URL to the SDK location that has to be served in the same level of security
 *     as the web page (http/https). It has to be in the following format:
 *
 *     `//domain/path/to/connect/sdk`
 *
 *     Default:
 *
 *     `//d3gcli72yxqn2z.cloudfront.net/connect/v4`.
 *
 *     If the installer cannot reach the needed files (by checking for connectversions.js
 *     or connectversions.min.js) on the default server it will automatically fallback to
 *     locate them at the hosted SDKs location.
 *
 *     The client web application can choose to load connectinstaller-4.js (or connectinstaller-4.min.js)
 *     from a local deployment of the Connect SDK (` by specifying an `sdklocation`).
 *     The Connect installer tries to reach the default cloudfront.net location and, if reachable,
 *     delivers the Connect installer from the cloudfront.net.
 *     If cloudfront.net is not reachable, connectinstaller-4.js will deliver the Connect
 *     installer from the provided `sdkLocation`.
 *
 * 2. `stylesheetLocation` (`String`):
 *     URL to an stylesheet that has to be served in the same level of security
 *     as the web page (http/https). It has to be in the following format:
 *
 *     `//domain/path/to/css/file.css`
 *
 *     Default: ``
 * 3. `iframeId` (`String`):
 *     Id of the iframe that is going to be inserted in the DOM
 *     Default: `aspera-iframe-container`
 * 4. `iframeClass` (`String`):
 *     Class to be added to the iframe that is going to be inserted in the DOM,
 *     for easier use with the custom stylesheet
 *     Default: `aspera-iframe-container`
 *
 * ##### Example
 *
 * The following JavaScript creates an [[AW4.ConnectInstaller]] object to manage
 * the installation process.
 *
 *     var asperaInstaller = new AW4.ConnectInstaller();
 *
 **/
AW4.ConnectInstaller = function(options) {

  ////////////////////////////////////////////////////////////////////////////
  // Public constants
  ////////////////////////////////////////////////////////////////////////////

  /**
   * AW4.ConnectInstaller.EVENT -> Object
   *
   * Event types:
   *
   * 1. `AW4.ConnectInstaller.EVENT.DOWNLOAD_CONNECT` (`"downloadconnect"`)
   * 2. `AW4.ConnectInstaller.EVENT.REFRESH_PAGE` (`"refresh"`)
   * 3. `AW4.ConnectInstaller.EVENT.IFRAME_REMOVED` (`"removeiframe"`)
   * 4. `AW4.ConnectInstaller.EVENT.IFRAME_LOADED` (`"iframeloaded"`)
   **/
  AW4.ConnectInstaller.EVENT = {
    DOWNLOAD_CONNECT : "downloadconnect",
    REFRESH_PAGE : "refresh",
    IFRAME_REMOVED : "removeiframe",
    IFRAME_LOADED : "iframeloaded"
  };

  ////////////////////////////////////////////////////////////////////////////
  // Private constants
  ////////////////////////////////////////////////////////////////////////////

  var
      EVENT = AW4.ConnectInstaller.EVENT,
      DEFAULT_SDK_LOCATION = "//d3gcli72yxqn2z.cloudfront.net/connect/v4",
      CONNECT_VERSIONS_JS = "/connectversions.min.js";
  ////////////////////////////////////////////////////////////////////////////
  // Private variables
  ////////////////////////////////////////////////////////////////////////////
  var
      connectOptions = {},
      listeners = [],
      connectJSONreferences = null,
      showInstallTimerID = 0,
      iframeLoadedFlag = false,
      iframeLoadedTimerID = 0;

  if (isNullOrUndefinedOrEmpty(options)) {
    options = {};
  }

  connectOptions.iframeId = options.iframeId || 'aspera-iframe-container';
  connectOptions.sdkLocation = AW4.Utils.getFullURI(options.sdkLocation) || DEFAULT_SDK_LOCATION;
  connectOptions.stylesheetLocation = AW4.Utils.getFullURI(options.stylesheetLocation);

  ////////////////////////////////////////////////////////////////////////////
  // Helper Functions
  ////////////////////////////////////////////////////////////////////////////

  /*
   * loadFiles(files, type, callback) -> null
   * - files (Array): Set of files to load
   * - type (String): type of the files to load: `js` or `css`
   * - callback (function): to be called when all scripts provided have been loaded,
   *
   *     `true` : if files loaded correctly
   *
   *     `false` : if files failed to load
   *
   */
  var loadFiles = function(files, type, callback) {
    if (files === null || typeof files === 'undefined' || !(files instanceof Array)) {
      return null;
    } else if (type === null || typeof type !== 'string') {
      return null;
    }
    var
        numberOfFiles = 0,
        head = document.getElementsByTagName("head")[0] || document.documentElement;

    /* Loads the file given, and sets a callback, when the file is the last one and a callback is
     * provided, it will call it
     * Loading mechanism based on https://jquery.org (MIT license)
     */
    var loadFilesHelper = function (file) {
      //IE9+ supports both script.onload AND script.onreadystatechange thus the done check
      var
          done = false,
          fileref = null;

      if (type.toLowerCase() === "js") {
        fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src", file);
      } else if (type.toLowerCase() === "css") {
        fileref = document.createElement("link");
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", file);
      } else {
        return null;
      }
      if (typeof callback === 'function') {
        // Attach handlers for all browsers
        fileref.onload = fileref.onreadystatechange = function() {
          if (!done && (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") ) {
            done = false;
            // Handle memory leak in IE
            fileref.onload = fileref.onreadystatechange = null;
            if (head && fileref.parentNode) {
              head.removeChild(fileref);
            }
            if (--numberOfFiles <= 0 && typeof callback === 'function') {
              callback(true);
            }
          }
        };
        fileref.onerror = function() {
          callback(false);
        };
      }
      // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
      head.insertBefore(fileref, head.firstChild);
    }
    numberOfFiles = files.length;
    for (var i = 0; i < numberOfFiles; i++) {
      if (typeof files[i] === 'string') {
        loadFilesHelper(files[i]);
      }
    }
  };

  var osPlatform = function () {
    var os = "Not supported"
    if (/Win/.test(navigator.platform)) {
      if (navigator.userAgent.indexOf("WOW64") != -1 || navigator.userAgent.indexOf("Win64") != -1 ) {
        os = "Win64";
      } else {
        os = "Win32";
      }
    }
    else if (/Mac OS X 10[._]6/.test(navigator.userAgent)) {
      os = "MacIntel-10.6-legacy"
    }else if (/Mac/.test(navigator.platform)) {
      os = "MacIntel";
    } else if (/Linux x86_64/.test(navigator.platform)) {
      os = "Linux x86_64";
    } else if (/Linux/.test(navigator.platform)) {
      os = "Linux i686";
    }


    return os;
  };

  var osVersion = function () {
    var match = "";
    if (/Win/.test(navigator.platform)) {
      match = navigator.userAgent.match(/Windows NT (\d+)[._](\d+)/);
    } else if (/Mac/.test(navigator.platform)) {
      match = navigator.userAgent.match(/OS X (\d+)[._](\d+)/);
    }
    if (isNullOrUndefinedOrEmpty(match))
      return null;
    var os_version = {
      highWord:parseFloat(match[1]),
      loWord:parseFloat( match[2])
    }
    return os_version;
  };

  var platformVersion = function (arg0) {
    if (!isNullOrUndefinedOrEmpty(arg0)) {
      var match = arg0.match(/(\d+)[.](\d+)/);
      if (isNullOrUndefinedOrEmpty(match))
        return null;
      var platform_version = {
        highWord: parseFloat(match[1]),
        loWord: parseFloat(match[2])
      }
      return platform_version;
    }
    return arg0;
  }

  var notifyListeners = function(event) {
    for (var i = 0; i < listeners.length; i++) {
      listeners[i](event);
    }
  };

  var addStyleString = function(str) {
    var node = document.createElement('style');
    node.setAttribute('type', 'text/css');
    //fix for <= IE9
    if (node.styleSheet) {
      node.styleSheet.cssText = str;
    } else {
      node.innerHTML = str;
    }
    document.body.appendChild(node);
  };

  /*
   * x - variable we want to check
   * @returns {Boolean} - true if the value is null, empty or undefined
   */
  function isNullOrUndefinedOrEmpty(x) {
    return x === "" || x === null || typeof x === "undefined";
  };

  ////////////////////////////////////////////////////////////////////////////
  // API Functions
  ////////////////////////////////////////////////////////////////////////////

  /*
   * AW4.ConnectInstaller#addEventListener(listener) -> null
   * - listener (Function): function that will be called when the event is fired
   *
   * ##### Event types ([[AW4.ConnectInstaller.EVENT]])
   *
   **/
  var addEventListener = function(listener) {
    if (typeof listener !== 'function') {
      return null;
    }
    listeners.push(listener);
    return null;
  };

  /**
   * AW4.ConnectInstaller#installationJSON(callback) -> null
   * - callback (Function): function that will be called when the result is retrieved.
   *
   * Querys the SDK for the current system's information, returning the full spec of all the
   * documentation and binaries available for it.
   *
   * ##### Object returned to the callback function as parameter
   *
   *      {
   *        "title": "Aspera Connect for Windows",
   *        "platform": {
   *            "os": "win32"
   *       },
   *        "navigator": {
   *            "platform": "Win32"
   *        },
   *        "version": "3.6.0.105660",
   *        "id": "urn:uuid:589F9EE5-0489-4F73-9982-A612FAC70C4E",
   *        "updated": "2012-10-30T10:16:00+07:00",
   *        "links": [
   *            {
   *                "title": "Windows Installer",
   *                "type": "application/octet-stream",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/bin/AsperaConnect-ML-3.6.0.105660.msi",
   *                "hreflang": "en",
   *                "rel": "enclosure"
   *            },
   *            {
   *                "title": "Aspera Connect PDF Documentation for Windows",
   *                "type": "application/pdf",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/docs/user/win/zh-cn/pdf/Connect_3.6.0_Windows_User_Guide_SimplifiedChinese.pdf",
   *                "hreflang": "zh-cn",
   *                "rel": "documentation"
   *            },
   *            {
   *                "title": "Aspera Connect PDF Documentation for Windows",
   *                "type": "application/pdf",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/docs/user/win/ja-jp/pdf/Connect_3.6.0_Windows_User_Guide_Japanese.pdf",
   *                "hreflang": "ja-jp",
   *                "rel": "documentation"
   *            },
   *            {
   *                "title": "Aspera Connect PDF Documentation for Windows",
   *                "type": "application/pdf",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/docs/user/win/en/pdf/Connect_3.6.0_Windows_User_Guide_English.pdf",
   *                "hreflang": "en",
   *                "rel": "documentation"
   *            },
   *            {
   *                "title": "Aspera Connect PDF Documentation for Windows",
   *                "type": "application/pdf",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/docs/user/win/es/pdf/Connect_3.6.0_Windows_User_Guide_Spanish.pdf",
   *                "hreflang": "es",
   *                "rel": "documentation"
   *            },
   *            {
   *                "title": "Aspera Connect PDF Documentation for Windows",
   *                "type": "application/pdf",
   *                "href": "//d3gcli72yxqn2z.cloudfront.net/connect/v4/docs/user/win/fr/pdf/Connect_3.6.0_Windows_User_Guide_French.pdf",
   *                "hreflang": "fr",
   *                "rel": "documentation"
   *            },
   *            {
   *                "title": "Aspera Connect Release Notes for Windows",
   *                "type": "text/html",
   *                "href": "http://www.asperasoft.com/en/release_notes/default_1/release_notes_55",
   *                "hreflang": "en",
   *                "rel": "release-notes"
   *            }
   *          ]
   *      }
   *
   **/
  var installationJSON = function(callback) {
    if (typeof callback !== 'function') {
      return null;
    }
    if (connectJSONreferences !== null) {
      callback(connectJSONreferences);
      return null;
    }
    var updatesURL = DEFAULT_SDK_LOCATION;
    var replaceJSONWithFullHref = function (connectversionsSdkLocation, entryJSON) {
      for (var i = 0; i < entryJSON.links.length; i++) {
        var hrefLink = entryJSON.links[i].href;
        if (!/^https?:\/\//i.test(hrefLink) && !/^\/\//.test(hrefLink)) {
          entryJSON.links[i].hrefAbsolute = connectversionsSdkLocation + '/' + hrefLink;
        }
      }
    };
    //load references from file and parse to load in the iframe
    var parseIstallJSON = function (connectversionsSdkLocation) {
      var parsedInstallJSON = AW4.connectVersions;
      var installEntries = parsedInstallJSON.entries;
      var procesJSONentry = function(entryJSON) {
        replaceJSONWithFullHref(connectversionsSdkLocation, entryJSON);
        connectJSONreferences = entryJSON;
        callback(entryJSON);
      };
      var userOS = osPlatform();
      for (var i = 0; i < installEntries.length; i++) {
        var entry = installEntries[i];
        if (entry.navigator.platform === userOS) {
          var userOSVersion = osVersion();
          var currentPlatform = platformVersion(entry.platform.version);
          if (!isNullOrUndefinedOrEmpty(currentPlatform) && !isNullOrUndefinedOrEmpty(userOSVersion)) {
            if ((userOSVersion.highWord > currentPlatform.highWord) ||
                (userOSVersion.highWord >= currentPlatform.highWord &&
                userOSVersion.loWord >= currentPlatform.loWord)) {
              procesJSONentry(entry);
              return null;
            }
          } else {
            procesJSONentry(entry);
            return null;
          }
        }
      }
    };
    var scriptLoaded = function(success) {
      var fallbackURL = connectOptions.sdkLocation;
      if (success && AW4.connectVersions != undefined) {
        parseIstallJSON(updatesURL);
      } else if (updatesURL !== fallbackURL) {
        updatesURL = fallbackURL;
        loadFiles([updatesURL + CONNECT_VERSIONS_JS], 'js', scriptLoaded);
      }
    };
    loadFiles([updatesURL + CONNECT_VERSIONS_JS], 'js', scriptLoaded);
    return null;
  };

  /*
   * AW4.ConnectInstaller#show(eventType) -> null
   * - eventType (String): the event type
   *
   * ##### Event types
   *
   * 1. `connecting` (`String`).
   * 2. `unable-to-launch` (`String`).
   * 3. `refresh` (`String`).
   * 4. `outdated` (`String`).
   * 5. `running` (`String`).
   *
   **/
  var show = function(eventType) {
    //We always need to check if launching was going to be popped up, if so delete it
    if (showInstallTimerID !== 0) {
      clearTimeout(showInstallTimerID);
    }
    var iframe = document.getElementById(connectOptions.iframeId);
    //IE will complain that in strict mode functions cannot be nested inside a statement, so we have to define it here
    function handleMessage(event) {
      // iFrame installation: Handling of messages by the parent window.
      if (event.data === EVENT.DOWNLOAD_CONNECT) {
        notifyListeners(event.data);
        showInstall();
      } else if (event.data === EVENT.REFRESH_PAGE) {
        notifyListeners(event.data);
        //Fix for refreshing only window in which we are contained, if we are an iframe just refresh the iframe (Sharepoint bug)
        var inIframe = false;
        try {
          inIframe = window.self !== window.top;
        } catch (e) {
          inIframe = true;
        }
        var refreshWindow = inIframe ? contentWindow : window;
        refreshWindow.location.reload(true);
      } else if (event.data === EVENT.IFRAME_REMOVED) {
        notifyListeners(event.data);
        dismiss();
      }
    };
    //IE will complain that in strict mode functions cannot be nested inside a statement, so we have to define it here
    function iframeLoaded() {
      iframeLoadedFlag = true;
      notifyListeners(EVENT.IFRAME_LOADED);
      var iframe = document.getElementById(connectOptions.iframeId);
      //Set dialog type
      iframe.contentWindow.postMessage(eventType, "*");
      //populate the iframe with the information pulled from connectversions.js
      var populateIframe = function(referencesJSON) {
        for (var i = 0; i < referencesJSON.links.length; i++) {
          var link = referencesJSON.links[i];
          if (link.rel === 'enclosure') {
            if (typeof iframe !== 'undefined' && iframe !== null) {
              iframe.contentWindow.postMessage('downloadlink=' + link.hrefAbsolute, "*");
            }
          }
        }
      }
      installationJSON(populateIframe);
      //load an stylesheet if provided
      if (connectOptions.stylesheetLocation) {
        // Inserting a stylesheet into the DOM for more manageable styles.
        if (typeof iframe !== 'undefined' && iframe !== null) {
          iframe.contentWindow.postMessage('insertstylesheet=' + connectOptions.stylesheetLocation, "*");
        }
      }
      notifyListeners(EVENT.IFRAME_LOADED);
    };
    if (!iframe) {
      //Set iframe styling
      addStyleString('.' + connectOptions.iframeId + '{position: absolute;width: 100%;height: 80px;margin: 0px;padding: 0px;border: none;outline: none;overflow: hidden;top: 0px;left: 0px;z-index: 999999999}');
      // Build and insert the iframe.
      var iframe = document.createElement('iframe');
      iframe.id = connectOptions.iframeId;
      iframe.className = connectOptions.iframeId;
      iframe.frameBorder = '0';
      iframe.src = connectOptions.sdkLocation + '/install/auto-topbar/index.html';
      document.body.appendChild(iframe);
      //Check for tight security policies
      if (!iframe.contentWindow.postMessage) {
        return;
      }

      // Set listener for messages from the iframe installer.
      if (window.attachEvent) {
        window.attachEvent("onmessage", handleMessage);
      } else {
        window.addEventListener("message", handleMessage, false);
      }
    }
    if (iframeLoadedFlag) {
      iframe.contentWindow.postMessage(eventType, "*");
    } else {
      //Give time to the iFrame to be loaded #31040
      if (iframe.attachEvent)
        iframe.attachEvent('onload', iframeLoaded);
      else
        iframe.onload = iframeLoaded;
    }
  };

  /**
   * AW4.ConnectInstaller#showLaunching(timeout) -> null
   * - timeout (Number): (*optional*) Timeout to show the banner in milliseconds. If at any point
   * during this timeout [[AW4.ConnectInstaller#connected]] or [[AW4.ConnectInstaller#dismiss]]
   * are called, the banner will not show up. Default: `1500`.
   *
   * Displays a banner in the top of the screen explaining the user that Aspera Connect
   * is trying to be launched.
   *
   **/
  var showLaunching = function(timeout) {
    timeout = typeof timeout === 'number' ? timeout : 1500;
    if (showInstallTimerID !== 0) {
      clearTimeout(showInstallTimerID);
    }
    var showLaunchingHelperFunction = function() {
      show('launching');
    };
    showInstallTimerID = setTimeout(showLaunchingHelperFunction, timeout);
  };

  /**
   * AW4.ConnectInstaller#showDownload() -> null
   *
   * Displays a banner in the top of the screen urging the user to download Aspera Connect.
   *
   **/
  var showDownload = function() {
    show('download');
  };

  /**
   * AW4.ConnectInstaller#showInstall() -> null
   *
   * Displays a banner in the top of the screen explaining the user what he has to do once
   * Aspera Connect has been downloaded
   *
   **/
  var showInstall = function() {
    show('install');
  };

  /**
   * AW4.ConnectInstaller#showUpdate() -> null
   *
   * Displays a banner in the top of the screen urging the user to update Aspera Connect
   * to the latest version.
   *
   **/
  var showUpdate = function() {
    show('update');
  };

  /**
   * AW4.ConnectInstaller#connected(timeout) -> null
   * - timeout (Number): (*optional*) If specified, this will add a timeout to the
   * dismiss function. Default: `2000`.
   *
   * Displays a temporary message that connect has been found, and after *timeout* dismisses the
   * banner
   *
   **/
  var connected = function(timeout) {
    timeout = typeof timeout === 'number' ? timeout : 2000;
    clearTimeout(showInstallTimerID);
    var iframe = document.getElementById(connectOptions.iframeId);
    if (typeof iframe !== 'undefined' && iframe !== null) {
      show('running');
      setTimeout(dismiss, timeout);
    }
    return null;
  };

  /**
   * AW4.ConnectInstaller#dismiss() -> null
   *
   * Dismisses the banner
   *
   **/
  var dismiss = function() {
    if (showInstallTimerID !== 0) {
      clearTimeout(showInstallTimerID);
    }
    var iframe = document.getElementById(connectOptions.iframeId);
    if (typeof iframe !== 'undefined' && iframe !== null) {
      iframe.parentNode.removeChild(iframe);
    }
    return null;
  };

  // The symbols to export.
  return {
    //FUNCTIONS
    addEventListener: addEventListener,
    installationJSON: installationJSON,
    showLaunching: showLaunching,
    showDownload: showDownload,
    showInstall: showInstall,
    showUpdate: showUpdate,
    connected: connected,
    dismiss: dismiss
  };
};
