/**
 * Created by venkata on 14/12/2015.
 */

// Log only if a window.console object is present
var isLogging = (function() {
    var console = window.console || '';
    if (console.log || !window.attachEvent) {
        return true;
    } else {
        return  false;
    };
}());

var logger = function(msg) {
    if ( this.isLogging ) {
        console.log(msg);
    } else {
        //alert(msg);
    }
};

/* ////////////////////////////////////

 Installation functionality

 */


var parseSdkLocation = function (path) {
    var sdkLocation,
        prefixIndex;
    prefixIndex = window.location.href.lastIndexOf(path);
    sdkLocation = window.location.href.substr(0, prefixIndex);
    return sdkLocation;
};
// Pass the path to this example's parent folder
//var installerPath = "http://demo.asperasoft.com/connect";
var installerPath = "file:///C:/Users/ardan.EBI/eclipse_workspace/test_aspera";

var showInstallPluginButton = function () {
    var pluginButton;
    pluginButton = document.createElement('a');
    pluginButton.href = 'javascript:(void(0))';
    pluginButton.id = 'install_plugin_button';
    pluginButton.className = 'install_button';
    pluginButton.innerHTML = 'Install Plug-in';
    pluginButton.onclick = installPlugin;

    document.getElementById('button_container').appendChild(pluginButton);
    logger('Inserting "Install Plug-in" button ...');
};

var installPlugin = function() {
    var downloadMessage,
        progressContainer;
    // Precondition: plugin is not installed
    if (!awInstaller.installerUpdateAvailable())
        return;

    logger('Insert progress bar...');
    downloadMessage = connectInstaller.installPackage ? '<br><br><span class="alt_download">If nothing happens, download and run the <a href="' +
    (connectInstaller.manualInstallPackage || connectInstaller.installPackage) + '">Aspera Connect installer</a>.<span>' : '';
    progressContainer = document.createElement('div');
    progressContainer.className = 'progress_container';
    progressContainer.id = 'progress_container';
    progressContainer.innerHTML = 'Installing plugin...' + downloadMessage;

    var container = document.getElementById('button_container');
    container.appendChild(progressContainer);

    logger('Hide the install button');
    var buttonEl = document.getElementById("install_plugin_button");
    if (buttonEl != null)
        buttonEl.style.display = 'none';

    // Start installer
    awInstaller.setInstallSource(installerPath);
    awInstaller.setCallback(function(state, desc, percentage) {
        logger("installer prep callback - " + state + ", " + desc + ", " + percentage);

        if (state == "COMPLETE") {
            navigator.plugins.refresh();
            window.location.reload();
        }
    });
    awInstaller.installInstaller();
};

var installConnect = function (elementId) {
    var el
        , container
        , progressContainer
        , progressDescription
        , barContainer
        , progressBar
        , header;

    logger('Create and assemble progress bar components...');
    progressContainer = document.createElement('div');
    progressContainer.className = 'progress_container';
    progressContainer.id = 'progress_container';
    progressDescription = document.createElement('p');
    progressDescription.className = 'progress_description';
    progressDescription.id = 'progress_description';
    progressDescription.innerHTML = '&nbsp;';
    barContainer = document.createElement('div');
    barContainer.className = 'bar_container';
    barContainer.id = 'bar_container';
    progressBar = document.createElement('div');
    progressBar.className = 'progress_bar';
    progressBar.id = 'progress_bar';
    progressBar.innerHTML = '&nbsp;'
    header = document.createElement('div');
    header.className = 'header';
    header.innerHTML = 'Installing Aspera Connect v' + connectInstaller.getLatestConnectVersion();
    progressContainer.appendChild(header);
    barContainer.appendChild(progressBar);
    progressContainer.appendChild(progressDescription);
    progressContainer.appendChild(barContainer);

    logger('Insert progress bar...')
    container = document.getElementById('button_container');
    container.appendChild(progressContainer);

    logger('Calling installLatest()');
    //connectInstaller.installLatest();
    connectInstaller.internalInstallLatest(handleConnectInstallation);

    logger('Hide the install button');
    buttonEl = document.getElementById("install_button");
    buttonEl.style.display = 'none';
};

var showConnectInstallButton = function() {
    var installButton;
    installButton = document.createElement('a');
    installButton.id = 'install_button';
    installButton.className = 'install_button';
    installButton.href = 'javascript:void(0)';
    installButton.innerHTML = connectInstaller.isUpgrade() ? 'Upgrade Aspera Connect' : 'Install Aspera Connect';
    installButton.onclick = function() {
        installConnect();
    };

    document.getElementById('button_container').appendChild(installButton);
    installButton = document.getElementById('install_button');
};

var removeEl = function(elId) {
    var el;
    el = document.getElementById(elId);
    el.parentNode.removeChild(el);
};

var handleConnectInstallation = function (evt) {
    var state = evt.state, desc = evt.description, percentage = evt.percent;
    var progressParent
        , progressContainer
        , progressBar
        , description
        , descriptionText
        , error;

    progressContainer = document.getElementById('progress_container');
    if (progressContainer) {
        progressBar = document.getElementById('progress_bar');
        description = document.getElementById('progress_description');
        descriptionText = description.getAttribute('data-text') || '';
        logger('state: ' + state + '  description: ' + desc + '  percentage: ' + percentage);

        switch(state.toLowerCase()) {
            case 'start':
                descriptionText = 'Starting' + '\u2026';
                break;
            case 'download':
                descriptionText = 'Downloading' + '\u2026';
                break;
            case 'error':
                descriptionText = 'Error: ' + AW.utils.localize(desc);
                percentage = 0; // -1 is not a valid el.style.width argument in IE.
                progressBar.setAttribute('data-error', 'true');
                break;
            case 'error_admin_rights':
                descriptionText = AW.utils.localize("ErrAdminRights") + ' ' + '<a href="' + (connectInstaller.manualInstallPackage || connectInstaller.installPackage) + '">' + AW.utils.localize("Download") + '</a>';
                percentage = 0;
                progressBar.setAttribute('data-error', 'true');
                break;
            case 'install':
                descriptionText = 'Installing' + '\u2026';
                break;
            case 'restart_required':
                // Occurs on IE if Connect was previously installed.
                insertRestartButton();
                break;
            case 'complete':
                // This state will not be fired if an errors occurs.
                logger('Connect Install Complete');
                if ( progressBar.getAttribute('data-error') !== 'true') {
                    setTimeout( function() {
                        if (connectInstaller.shouldRestart()) {
                            // remove the progress markup.
                            //progressParent = progressContainer.parentNode;
                            //progressParent.removeChild(progressContainer);
                            descriptionText = 'Please quit and restart your browser.';
                            description.innerHTML = descriptionText;
                        } else {
                            if (AW.utils.browser.is.ie || AW.utils.browser.is.ff) {
                                window.location.reload();
                            }
                            // re-initialize the page
                            initAsperaConnect();
                        }
                    }, 4000);
                }
                break;
        }
        progressBar.style.width = percentage + '%';
        description.setAttribute('data-text', descriptionText);
        description.innerHTML = descriptionText;
    }
};
var insertRestartButton = function () {
    var restartButton, container;
    container = document.getElementById('button_container');
    restartButton = document.createElement('a');
    restartButton.href = 'javascript:void(0)';
    restartButton.id = 'install_plugin_button';
    restartButton.className = 'button';
    restartButton.innerHTML = 'Restart Browser and Complete Installation';
    restartButton.onclick = function(){connectInstaller.restartBrowser();};
    container.innerHTML = '';
    container.appendChild(restartButton);
};
// Show the appropriate install button
var connectInstaller = null;
var awInstaller = null;
var initAsperaConnect = function () {
    // Initialize installer
    if (connectInstaller == null)
        connectInstaller = new AW.ConnectInstaller(installerPath);
    awInstaller = connectInstaller.getAsperaInstaller();

    //Clear out the container
    document.getElementById('button_container').innerHTML = '';

    // If the plugin is installed, do we have the latest Connect App
    if (connectInstaller.asperaWebInstalled() && !connectInstaller.updateAvailable()) {
        // Aspera Web plugin and Aspera Connect App are installed
        fileControls.setup();
    } else if (connectInstaller.asperaWebInstalled()) {
        // Deliver the Aspera Connect App
        showConnectInstallButton();
    } else if (!awInstaller.installerUpdateAvailable()) {
        // The Aspera Installer Plugin is Available
        showConnectInstallButton();
    } else {
        // Deliver the Aspera Installer Plugin
        showInstallPluginButton();
    }
};

