<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['title']) && isset($_POST['statusname1']) && isset($_POST['begindate1']) && isset($_POST['enddate1'])&& isset($_POST['taskname1'])&& isset($_POST['userdetail1'])) {
    
    $title = $_POST['title'];
    $statusname = $_POST['statusname1'];
	$begindate = $_POST['begindate1'];
    $enddate = $_POST['enddate1'];
	$taskname = $_POST['taskname1'];
	$userdetail=$_POST['userdetail1'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
	$mysqli = new mysqli('72.167.233.102','sweng500','Ss500@500','sweng500');


    // mysql inserting a new row
    $result = $mysqli->query("INSERT INTO project_dmn(project_name, statusname, beginDate, endDate, taskName, user_detail) VALUES( '$title','$statusname','$begindate','$enddate', '$taskname','$userdetail')");

	
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