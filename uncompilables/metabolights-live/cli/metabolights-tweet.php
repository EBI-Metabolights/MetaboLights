<?php
error_reporting(E_ERROR | E_PARSE);

require __DIR__.DIRECTORY_SEPARATOR.'..'.DIRECTORY_SEPARATOR.'tmhOAuthExample.php';
$tmhOAuth = new tmhOAuthExample();

function CallAPI($method, $url, $data = false)
{
    $curl = curl_init();

    $headers = array();
    $headers[] = 'user_token: abcdefg';

    switch ($method)
    {
        case "POST":
            curl_setopt($curl, CURLOPT_POST, 1);

            if ($data)
                curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
            break;
        case "PUT":
            curl_setopt($curl, CURLOPT_PUT, 1);
            break;
        default:
            if ($data)
                $url = sprintf("%s?%s", $url, http_build_query($data));
    }

    // Optional Authentication:
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

    $result = curl_exec($curl);

    curl_close($curl);

    return $result;
}

$StudyList = json_decode(CallAPI('GET', 'http://www.ebi.ac.uk/metabolights/webservice/study/list'),true);


foreach ( $StudyList['content'] as $study )
{   
    $StudyDetails = json_decode(CallAPI('GET', 'http://www.ebi.ac.uk/metabolights/webservice/study/'. $study ),true);
    $releaseDate = $StudyDetails['content']['studyPublicReleaseDate'];
    $current = strtotime(date("Y-m-d"));
    $date    = strtotime($releaseDate);

     $datediff = $date - $current;
     $difference = floor($datediff/(60*60*24));
     if($difference==0)
     {
        var_dump('Today-'. $study);
        $code = $tmhOAuth->user_request(array(
          'method' => 'POST',
          'url' => $tmhOAuth->url('1.1/statuses/update'),
          'params' => array(
            'status' =>  $study . ': ' . $StudyDetails['content']['title'] . ' http://www.ebi.ac.uk/metabolights/'.$study
          )
        ));
        $tmhOAuth->render_response();
     }else if($difference > 1)
     {
        var_dump('Future-'. $study);
     }
     else
     {
        var_dump('Published-'. $study);
     }
}

?>