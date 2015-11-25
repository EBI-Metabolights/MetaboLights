/*
 * EBI MetaboLights Project - 2012.
 *
 * File: detectIE.js
 *
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 26/01/2012
 * Time: 09:10
 * To change this template use File | Settings | File Templates.
 */

function getInternetExplorerVersion()
// Returns the version of Internet Explorer or a -1
// (indicating the use of another browser).
{
    var rv = -1; // Return value assumes failure.
    if (navigator.appName == 'Microsoft Internet Explorer')
    {
        var ua = navigator.userAgent;
        var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat( RegExp.$1 );
    }
    return rv;
}
function checkVersion()
{
    var msg = "You're not using Internet Explorer.";
    var ver = getInternetExplorerVersion();

    if ( ver > -1 )
    {
        if ( ver >= 8.0 )
            msg = "You're using a recent copy of Internet Explorer."
        else
            msg = "We recommend you upgrade your copy of Internet Explorer.";
    }
    alert( msg );
}