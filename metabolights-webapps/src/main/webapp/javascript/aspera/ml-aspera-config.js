/**
 * Created by venkata on 14/12/2015.
 */


jQuery.namespace( 'METABOLIGHTS' );

METABOLIGHTS.ConfigFileControl = {
    // remote_upload_host: 'hx-fasp-1.ebi.ac.uk',
    // remote_upload_user: 'UPLOAD_USERNAME',
    // remote_upload_password: 'UPLOAD_PASSWORD',
    remote_download_host: 'fasp.ebi.ac.uk',
    remote_download_user: 'fasp-ml',
    download_token: 'ATM2_ACsXKsKlNtYKyIqlkE3eZMfhrXaAP6Z_Lr4qxOVEMbX-9QAAETLf8GQtfZqCxxJareaMA5_2MTA',
    download_authentication: 'token',
    target_rate_kbps: 500000,
    rate_policy: 'fair',
    cipher: 'none',
    fasp_port: 33001,
    http_fallback: true,
    asperaDownloadConnectPath: 'http://downloads.asperasoft.com/download_connect/',
    // uploadDirectoryPrefix: 'upload'
};
