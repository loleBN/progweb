<?php

  $dbhost = '186.202.152.197';
    $dbuser = 'loislene';
    $dbpass = 'lois2011';
    $dbname = 'loislene';
  
  $mysqli = new mysqli($dbhost, $dbuser, $dbpass, $dbname);
    if ($mysqli->connect_error) {
      die("Connection failed: " . $mysqli->connect_error);
    }
  
  $result = $mysqli->query("SELECT tag_rfid, nome FROM Tags WHERE status=1");
  $quant = $result->num_rows;
  if($quant>0){
    echo '[';
    for($i = 1; $i <= $quant; $i++){
      $row = $result->fetch_assoc();
      echo '{"tag_rfid":' . $row['tag_rfid'] . ",";
      if($i == $quant){
        echo '"nome":"' . $row['nome'] .'"}';
      } else {
        echo '"nome":"' . $row['nome'] . '"},';
      }        
    }
    
    echo ']';
    }else{
      echo -1;
      }
    $mysqli->close();
?>
