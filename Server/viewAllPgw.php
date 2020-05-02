<?php

    // Import config database
    include 'config/con.php';

    // Sql Query
    $sql = "SELECT * from tb_pegawai";

    //Hasil query
    $r = mysqli_query($con,$sql);

    //Membuat Array kosong
    $result = array();
    while($row = mysqli_fetch_array($r)){

        array_push($result, array(
            "id" => $row['id'],
            "name" => $row['nama']
        ));
    }    

    //Menampilkan array dalam format JSON
    echo json_encode(array('result' => $result));
    
    mysqli_close(($con));
