<?php
  
  $servername = '186.202.152.197';
  $username = 'loislene';
  $password = 'lois2011';
  $dbname = 'loislene';

  // Create connection
  $conn = new mysqli($servername, $username, $password, $dbname);
  // Check connection
  if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }
  $ver =$conn->query("SELECT status FROM Tags WHERE tag_rfid=".$_GET["tag_rfid"]);
   echo "Ver = " . $ver->num_rows;
  if ($ver->num_rows>0){
    $status=$ver->fetch_assoc();
    if ($status['status'] == 0){
      $result = $conn->query("INSERT INTO Registros (tag_rfid, date_time, status) VALUES ('" . $_GET['tag_rfid'] . "','" . $_GET['date_time'] . "',1)");  
      $troca_sts = $conn->query("UPDATE Tags SET status=1 WHERE tag_rfid=".$_GET['tag_rfid']);
    }
    elseif ($status['status'] == 1){
      $result = $conn->query("INSERT INTO Registros (tag_rfid, date_time, status) VALUES ('" . $_GET['tag_rfid'] . "','" . $_GET['date_time'] . "',0)");  
      $troca_sts = $conn->query("UPDATE Tags SET status=0 WHERE tag_rfid=".$_GET['tag_rfid']);  
    }else{
      echo "Error";
    }
    echo "Done";
  }else{
        echo "tag nao registrada";
  }
   
?>
