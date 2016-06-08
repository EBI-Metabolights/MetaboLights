<?php 
	header('Content-Type: application/json');
	
	$data = file_get_contents($_POST['data']);
	echo $data;
	// $data = json_decode($data, true);

?>