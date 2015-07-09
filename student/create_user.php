<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['finame']) && isset($_POST['laname']) && isset($_POST['email']) && isset($_POST['psname'])) {
    
    $firstname = $_POST['finame'];
    $lastname = $_POST['laname'];
	 $emailname = $_POST['email'];
    $password = $_POST['psname'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
	$mysqli = new mysqli('72.167.233.102','sweng500','Ss500@500','sweng500');


    // mysql inserting a new row
    $result = $mysqli->query("INSERT INTO user_dmn(user_lastName, user_firstName, email, Password) VALUES( '$lastname', '$firstname', '$emailname', '$password')");

    // check if row inserted or not

	
    if ($result) {
        // successfully inserted into database

        $response["success"] = 1;
        $response["message"] = "Product successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>