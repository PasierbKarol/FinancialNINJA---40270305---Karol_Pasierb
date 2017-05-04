
	<?php
	//header('Content-Type: application/json');
    $con = mysqli_connect("localhost", "id817592_finninja", "finninja666", "id817592_financialninja");
	$name = $_POST['name'];
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
	
    if( isset($_POST['name']) && isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password'])) {
    
	
    $statement = mysqli_prepare($con, "INSERT INTO ninjausers (name, username, email, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name, $username, $email, $password);

    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
	echo json_encode($response);
	
    }

	
?>