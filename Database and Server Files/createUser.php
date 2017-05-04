<?php

//require("password.php");

    //$connection = mysqli_connect("localhost", "ninjausers-34a36a", "cycki616717","ninjausers-34a36a");

$connection = mysqli_connect("shareddb1a.hosting.stackcp.net", "karol666", "cycki616717","ninjausers-34a36a");
	
		if ( !$connection ) {
  die( 'connect error: '.mysqli_connect_error() );
}

	$name = $_POST['name'];
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
	$user_taken;
	$user_id;
	
	function registerUser() {
		echo "Registering user ";
		global $connection, $name, $username, $email, $password;
		//$passwordHash = password_hash($password, PASSWORD_DEFAULT);
		 
		if( isset($_POST['name']) && isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password'])) {
			
			$query = "INSERT INTO ninjausers (`name`, username, email, `password`) VALUES (?, ?, ?, ?)";
			$statement = mysqli_prepare($connection, $query);
			mysqli_stmt_bind_param($statement, "ssss", $name, $username, $email,$password);//$passwordHash);
			mysqli_stmt_execute($statement);
			mysqli_stmt_close($statement);  
			echo "user  registered";
			$response["success"] = true;  
			
		} 
	}
	
	 function usernameAvailable() {
		 global $connection, $username, $user_taken, $user_id, $email, $password;
		 
		 $statement = mysqli_prepare($connection, "SELECT * FROM ninjausers WHERE username = ?"); 
        mysqli_stmt_bind_param($statement, "s",$username);
        mysqli_stmt_execute($statement);
       mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $user_id, $name, $username, $email, $password);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
			$user_taken = "User is available";
			echo $user_taken;
            return true; 
        } else {
			$user_taken = "Username is taken";
			echo $user_taken;
            return false; 
        }
	 }
	
		function findUserID() {
			 global $connection, $username, $user_taken, $user_id, $email, $password;
			 
			 echo "I'M INSIDE                   "; 
			$query = "SELECT * FROM ninjausers WHERE username = ?";
			$statement = mysqli_prepare($connection, $query);
			if ( !$statement ) {
				echo "stamement died";
				die("mysqli error: ".mysqli_error($connection));
			} else {
				echo "statement works" . " <br/>";
			}
			
			mysqli_stmt_bind_param($statement, "s", $username);
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
			
			while(mysqli_stmt_fetch($statement)){
				echo "I'M INSIDE  WHILE                 "; 
					$response["name"] = $name;
					$response["username"] = $username;
					$response["user_id"] = $user_id;
					echo "USER ID                  ".$user_id."</br>"; 
			}
		}
	 
	 
	 
	$response = array();
	$response["success"] = false;  
	
	if (usernameAvailable()){
		echo "user is available, we can register";
        registerUser();
		
		findUserID();
  
        $response["success"] = true;  
		$response["user_id"] = $user_id;
		$response["name"] = $name;
		$response["user_taken"] = $user_taken;
		echo "user id was passed on ".$user_id;
    }
	else 
	{
		$response["user_taken"] = $user_taken;
	}
	
	echo json_encode($response);
?>