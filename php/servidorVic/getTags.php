<?php
  
  $dbhost = '186.202.152.197';
  $dbuser = 'loislene';
  $dbpass = 'lois2011';
  $dbname = 'loislene';

  $mysqli = new mysqli($dbhost, $dbuser, $dbpass, $dbname);
  if ($mysqli->connect_error) {
      die("Connection failed: " . $mysqli->connect_error);
  }

  $result = $mysqli->query("SELECT * FROM Tags");
  $quant = $result->num_rows;
  
  echo '[';
  for($i = 1; $i <= $quant; $i++){
    $row = $result->fetch_assoc();
    echo '{"tag_rfid":"' . $row['tag_rfid'] . '",';
    echo '"nome":"' . $row['nome'] . '",';
           if($i == $quant){
      echo '"status":' . $row['status'] .'}';
    } else {
      echo '"status":' . $row['status'] . '},';
    }        
  }
  
  echo ']';
  $mysqli->close();
?>
