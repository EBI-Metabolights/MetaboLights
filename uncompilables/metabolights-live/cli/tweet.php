<?php

$code = $tmhOAuth->user_request(array(
  'method' => 'POST',
  'url' => $tmhOAuth->url('1.1/statuses/update'),
  'params' => array(
    'status' => 'MTBLS166-1H NMR and LC–MS/MS based urine metabolomic investigation of the subacute effects of HBCD in mice'
  )
));

$tmhOAuth->render_response();