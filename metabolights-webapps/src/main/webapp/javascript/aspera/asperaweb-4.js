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

  var httpRequest = function(method, path, data, callback, requestId) {
    //start request
    var request = getXMLHttpRequest();
    request.onreadystatechange = function(XMLHttpRequestProgressEvent) {
      if (request.readyState != 4) {
        return;
      }
      //when ready
      callback(request.status, request.responseText, requestId);
    }
    request.open(method, path, true);
    if (method.toUpperCase() === "GET") {
      request.send();
    } else {
      request.send(data);
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

  var
      npapiPlugin = null,
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
    if (AW4.Utils.BROWSER.IE || AW4.Utils.BROWSER.FIREFOX || AW4.Utils.BROWSER.SAFARI) {
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

  var
      idCallbackHash = {},
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
        STOPPED : 4
      },
  //The port in which we are going to look for connect first
      DEFAULT_PORT = 33003,
  //Localhost value for the requests
      LOCALHOST = "http://127.0.0.1:",
  //Current version of the Connect Client API
      URI_VERSION_PREFIX = "/v5/connect",
  //Controls how many ports we want to search from the DEFAULT_PORT
      MAX_PORT_SEARCH = 10;

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
      scanRetryTimeValues = [0,1];

  ////////////////////////////////////////////////////////////////////////////////////////
  // PRIVATE METHODS
  ////////////////////////////////////////////////////////////////////////////////////////

  var processQueue = function() {
    //process all pending requests
    while (requestQueue.length > 0) {
      var requestInfo = requestQueue.pop();
      var fullURL = LOCALHOST + requestInfo.port + URI_VERSION_PREFIX + requestInfo.path;
      requestImplementation.httpRequest(requestInfo.method, fullURL, requestInfo.data, handleResponse, requestInfo.id);
    }
    return null;
  };

  /*
   * ##### Format for `listener`
   *
   *      function(eventType, data) { ... }
   */
  var changeConnectStatus = function(newConnectStatus) {

    connectStatus = newConnectStatus;
    if (connectStatus == STATUS.RUNNING) {
      processQueue();
    }
    if (statusListener !== null) {
      statusListener(connectStatus);
    }
    return null;
  };

  var iteratePortsCallback = function(httpCode, response, requestId) {

    //retrieve wanted value (copy beacuse primitive) and remove request
    var checkedPort = idCallbackHash[requestId].port;
    delete idCallbackHash[requestId];

    //check always if we have found connect, if so stop searching
    if (connectStatus == STATUS.RUNNING || connectStatus == STATUS.STOPPED) {
      return null;
    }

    if (httpCode == 200) {
      //console.log('Found connect in port: ' + checkedPort);
      connectPort = checkedPort;
      changeConnectStatus(STATUS.RUNNING);
    } else if (checkedPort === DEFAULT_PORT) {
      //Check the rest of the ports
      for (var port = DEFAULT_PORT + 1; port < (DEFAULT_PORT + MAX_PORT_SEARCH); port++) {
        var currentRequestId = nextId++;
        var method = HTTP_METHOD.GET;
        var path = "/info/ping";
        var fullURL = LOCALHOST + port + URI_VERSION_PREFIX + path;

        var requestInfo = {id: currentRequestId, method: method, port: port, path: path, data: null, callbacks: null};
        idCallbackHash[currentRequestId] = requestInfo;

        requestImplementation.httpRequest(method, fullURL, null, iteratePortsCallback, currentRequestId);
      }
    }
    return null;
  };

  var iteratePorts = function(firstRun) {
    //check always if we have found connect or stopped the requests, if so stop searching
    if (connectStatus == STATUS.RUNNING || connectStatus == STATUS.STOPPED) {
      return null;
    } else if (connectStatus == STATUS.INITIALIZING && !firstRun) {
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
    var path = "/info/ping";
    var fullURL = LOCALHOST + DEFAULT_PORT + URI_VERSION_PREFIX + path;

    var requestInfo = {id: requestId, method: method, port: DEFAULT_PORT, path: path, data: null, callbacks: null};
    idCallbackHash[requestId] = requestInfo;

    requestImplementation.httpRequest(method, fullURL, null, iteratePortsCallback, requestId);

    return null;
  };

  var handleResponse = function(httpCode, response, requestId) {
    if (connectStatus == STATUS.STOPPED) {
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

  ////////////////////////////////////////////////////////////////////////////////////////
  // PUBLIC
  ////////////////////////////////////////////////////////////////////////////////////////

  var addStatusListener = function(callback) {
    statusListener = callback;
  };

  var processClientRequest = function(method, path, data, callbacks) {
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
    var fullURL = LOCALHOST + connectPort + URI_VERSION_PREFIX + path;
    requestImplementation.httpRequest(method, fullURL, data, handleResponse, requestId);
    return null;
  };

  ////////////////////////////////////////////////////////////////////////////////////////
  // INITIALIZE PLUGINS
  ////////////////////////////////////////////////////////////////////////////////////////

  var init = function(options) {
    //Change connect status to initailizing
    changeConnectStatus(STATUS.INITIALIZING);
    //Find the request implementation that is optimal for this environment
    requestImplementation = (function () {
      var requestImpl = new AW4.PPAPIrequestImplementation();
      if (requestImpl.isSupportedByBrowser()) {
        return requestImpl;
      }
      var requestImpl = new AW4.NPAPIrequestImplementation();
      if (requestImpl.isSupportedByBrowser()) {
        return requestImpl;
      }
      var requestImpl = new AW4.XMLhttpRequestImplementation();
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
        if (connectStatus != STATUS.RUNNING) {
          changeConnectStatus(STATUS.FAILED);
        }
      };
      setTimeout(initializeTimeoutCallback, options.initializeTimeout);
      // INITIALIZE CONNECT
      iteratePorts(true);
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
    POST : "POST"
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

  var
      INITIALIZE_TIMEOUT = options.connectLaunchWaitTimeoutMs || 5000,
      PLUGIN_ID = options.id || "aspera-web",
      PLUGIN_CONTAINER_ID = options.containerId || "aspera-web-container",
      SDK_LOCATION = AW4.Utils.getFullURI(options.sdkLocation) || "//d3gcli72yxqn2z.cloudfront.net/connect/v4",
      APPLICATION_ID = "",
      AUTHORIZATION_KEY = options.authorizationKey || "",
      POLLING_TIME = options.pollingTime || 2000,
      MINIMUM_VERSION = options.minVersion || "",
      DRAGDROP_ENABLED = options.dragDropEnabled || false;

  ////////////////////////////////////////////////////////////////////////////
  // Private variables
  ////////////////////////////////////////////////////////////////////////////

  var
      transferListeners = [],
      transferEventIntervalId = 0,
      transferEventIterationToken = 0,
      requestHandler = null,
      statusListeners = [],
      connectVersion = "",
      connectStatus = AW4.Connect.STATUS.INITIALIZING;

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

  function connectHttpRequest(method, path, data, callbacks) {
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
    requestHandler.start(method, path, dataStr, callbacks);
    return null;
  };

  function getAllTransfersHelper(iterationToken, callbacks) {
    //This is never supposed to happen
    if (isNullOrUndefinedOrEmpty(iterationToken)) {
      return null;
    }
    var data = {iteration_token: iterationToken};
    return connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/activity", data, callbacks);
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
    getAllTransfersHelper(transferEventIterationToken, {success: notifyTransferListeners});
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

  ////////////////////////////////////////////////////////////////////////////
  // Manage Connect Status and high level logic
  ////////////////////////////////////////////////////////////////////////////

  function notifyStatusListeners(notifyStatus) {
    for (var i = 0; i < statusListeners.length; i++) {
      var listener = statusListeners[i](AW4.Connect.EVENT.STATUS, notifyStatus);
    };
  }

  function manageConnectStatus(newStatus) {
    //Initialize options before calling RUNNING
    if (newStatus == requestHandler.STATUS.RUNNING && DRAGDROP_ENABLED) {
      connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/file/initialize-drag-drop", null, null);
    }
    if (connectStatus == AW4.Connect.STATUS.OUTDATED) {
      return null;
    }
    if (newStatus == requestHandler.STATUS.INITIALIZING) {
      connectStatus = AW4.Connect.STATUS.INITIALIZING;
    } else if (newStatus == requestHandler.STATUS.RETRYING) {
      connectStatus = AW4.Connect.STATUS.RETRYING;
    } else if (newStatus == requestHandler.STATUS.FAILED) {
      connectStatus = AW4.Connect.STATUS.FAILED;
    } else if (MINIMUM_VERSION != "" && connectVersion == "") {
      var greaterVersion = function(response) {
        connectVersion = response.version;
        connectStatus = AW4.Connect.STATUS.RUNNING;
        notifyStatusListeners(connectStatus);
      };
      var lowerVersion = function(response) {
        connectStatus = AW4.Connect.STATUS.OUTDATED;
        notifyStatusListeners(connectStatus);
      };
      var versionCallbacks = {success: greaterVersion, error: lowerVersion};
      connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/update/require", {min_version: MINIMUM_VERSION, sdk_location: SDK_LOCATION}, versionCallbacks);
      return null;
    } else {
      //RUNNING and no version required or version already retrieved
      connectStatus = AW4.Connect.STATUS.RUNNING;
    }
    notifyStatusListeners(connectStatus);
  };

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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/info/authenticate", authSpec, callbacks);
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
   *        "app_id" : "^mydomain"
   *      }
   **/
  this.initSession = function(applicationId) {
    if (isNullOrUndefinedOrEmpty(APPLICATION_ID)) {
      if (isNullOrUndefinedOrEmpty(applicationId)) {
        // Default appId. Caret prefix differentiates it from a user-specified id.
        APPLICATION_ID = "^" + isNullOrUndefinedOrEmpty(window.location.hostname) ? "localhost" : window.location.hostname;
      } else {
        APPLICATION_ID = applicationId;
      }
    } else {
      return AW4.Utils.createError(-1, "Session was already initialized");
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/modify/" + transferId, options, callbacks);
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
    var params = {};
    if (!options) {
      return AW4.Utils.createError(-1, "Invalid options parameter");
    }
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/file/read-as-array-buffer/", options, callbacks);
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
    if (!options.path || !options.offset || !options.chunkSize) {
      return AW4.Utils.createError(-1, "Invalid parameters");
    }
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/file/read-chunk-as-array-buffer/", options, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/remove/" + transferId, null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/resume/" + transferId, options, callbacks);
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
      connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/file/dropped-files", data, {success: dropHelper});
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/windows/about", null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/windows/finder/" + transferId, null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/windows/preferences", null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/windows/select-save-file-dialog/", localOptions, callbacks);
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
   * Displays a file browser dialog for the user to select files.
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/windows/select-open-file-dialog/", localOptions, callbacks);
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
   * Displays a file browser dialog for the user to select directories.
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/windows/select-open-folder-dialog/", localOptions, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/windows/transfer-manager", null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/windows/transfer-monitor/" + transferId, null, callbacks);
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
      sdkLocation: SDK_LOCATION
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/start", transfer_specs, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.POST, "/transfers/stop/" + transferId, null, callbacks);
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
    connectHttpRequest(AW4.Connect.HTTP_METHOD.GET, "/info/version", null, callbacks);
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
 *       "app_id": "^localhost",
 *       "back_link": "http://http://demo.asperasoft.com",
 *       "request_id": "36d3c2a4-1856-47cf-9865-f8e3a8b47822"
 *     }
 **/

/**
 * AsperaConnectSettings.app_id -> String
 *
 * An identifier for the current webapp, so that all transfers can be
 * associated with the same app.
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
 * The data format for statistics for one transfer.
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
 *       "uuid": "add433a8-c99b-4e3a-8fc0-4c7a24284ada"
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
 * TransferInfo.uuid -> String
 *
 * The Universally Unique IDentifier for the transfer, so that it can be
 * differenced from any other.
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
 * 1. `"none"` (default)
 * 2. `"aes-128"`
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

