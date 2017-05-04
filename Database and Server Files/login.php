<?php
	ini_set('display_errors', 'On');
	error_reporting(E_ALL);


    //require("password.php");
    $connection = mysqli_connect("shareddb1a.hosting.stackcp.net", "ninjausers-34a36a", "cycki616717","ninjausers-34a36a");

//$connection = mysqli_connect("localhost", "ninjausers-34a36a", "cycki616717","karol666");


if ( !$connection ) {
		die( 'connect error: '.mysqli_connect_error() );
		}
		else {
			echo "connection works" . " <br/>";
		}

    $username = $_POST['username'];
    $passwordApp = $_POST['password'];
    
	//if( isset($_POST['username']) && isset($_POST['password'])) {
	
		$query = "SELECT * FROM ninjausers WHERE username = ? AND `password` = ?";
		$statement = mysqli_prepare($connection, $query);
		if ( !$statement ) {
			echo "stamement died";
			die("mysqli error: ".mysqli_error($connection));
			} else {
				echo "statement works" . " <br/>";
			}
			
		mysqli_stmt_bind_param($statement, "ss", $username, $passwordApp);
		mysqli_stmt_execute($statement);
		if ( !mysqli_stmt_execute($statement) ) {
			echo "stamement didn't execute";
			die( 'stmt error: '. mysqli_stmt_error($statement) );
			}
			else {
				echo "statement executed" . " <br/>";
			}
		
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $user_id, $name, $username, $email, $password);
		
		$response = array();
		$response["success"] = false;  
		
		while(mysqli_stmt_fetch($statement)){
	
			//if (password_verify($passwordApp, $password)) {
				$response["success"] = true;  
				$response["name"] = $name;
				$response["username"] = $username;
				$response["user_id"] = $user_id;
						//}
		}
	//}	
	echo "hash is: " . $password ."<br /> " ;
	echo "password is : " . $passwordApp . "<br/>";
	echo json_encode($response);
?>