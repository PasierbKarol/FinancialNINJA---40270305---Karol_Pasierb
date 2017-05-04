<?php

//this file is placed on the server  and it is responsible for receiving data from the app when the registration requested is placed.
    //require("password.php");
    
    
	//this line connects to the database
	$connect = new mysqli("localhost", "40270305", "password", "40270305")
	or die(mysqli_error());
	
	//these variables receive the data from the app
    $name = $_POST["name"];
    $username = $_POST["username"];
	$email = $_POST["email"];
    $password = $_POST["password"];
	
	//this function performs registration
     function createUser() {
        global $connect, $name, $username, $email, $password;
        $passwordHash = password_hash($password, PASSWORD_DEFAULT);
		//this line inserts account details into the database
        $statement = mysqli_prepare($connect, "INSERT INTO ninjausers (name, username, email, password) VALUES (?, ?, ?, ?)")
		or die("Prepare failed");
        mysqli_stmt_bind_param($statement, "ssss", $name, $username , $email,  $passwordHash);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);     
    }
	
	//this function checks if the user is available before attempting regfistration
    function usernameAvailable() {
        global $connect, $username;
        $statement = mysqli_prepare($connect, "SELECT * FROM ninjausers WHERE username = ?")
		or die("Prepare failed"); 
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
    }
    $response = array();
    $response["success"] = false;  
    if (usernameAvailable()){
        createUser();
        $response["success"] = true;  
    }
    
    echo json_encode($response);
	
?>