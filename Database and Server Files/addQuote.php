<?php

$connection = mysqli_connect("shareddb1a.hosting.stackcp.net", "ninjausers-34a36a", "cycki616717","ninjausers-34a36a");

    $quote = $_POST['quote'];
    $author = $_POST['author'];
	$user_id = $_POST['user_id'];
    
	
		$query = "INSERT INTO ninjaquotes (quote, author, user_id) VALUES (?, ?, ?)";
		$statement = mysqli_prepare($connection, $query);

			
		mysqli_stmt_bind_param($statement, "sss", $quote, $author, $user_id);

		if ( !mysqli_stmt_execute($statement) ) {
			echo "stamement didn't execute";
			die( 'stmt error: '. mysqli_stmt_error($statement) );
			}
			else {
				echo "statement executed";
			}
		
			echo "quote submitted";
			$response = array();
			$response["success"] = true;  
		
	echo json_encode($response);
?>