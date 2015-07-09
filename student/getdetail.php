<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
//require_once __DIR__ . '/db_connect.php';

// connecting to db
$mysqli = new mysqli('72.167.233.102','sweng500','Ss500@500','sweng500');
//$db = new DB_CONNECT();
$user_detail = $_POST['user_detail'];
//$passwordname = $_POST['uspass'];
//$emailname = "kevinyeb@aol.com";//$_POST['emailname'];
//$passwordname = "Welcome";//$_POST['luspass'];
// get all products from products table
$result = $mysqli->query("SELECT * FROM project_dmn where user_detail='$user_detail'");

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["project_name"] = $row["project_name"];
        $product["status_name"] = $row["statusname"];
        $product["beginDate"] = $row["beginDate"];
        $product["endDate"] = $row["endDate"];
		$product["taskName"] = $row["taskName"];
		$product["user_detail"] = $row["user_detail"];
       


        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
?>
