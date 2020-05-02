<?php
    //import database
    include 'config/con.php';

    //mendapatkan id pegawai yang di inginnkan
    $id = $_GET['id'];

    //Sql Query sesuai id
    $sql = "SELECT * from tb_pegawai WHERE id=$id";

    //Hasil Query
    $r = mysqli_query($con, $sql);

    //Memasukkan hasil ke array
    $result = array();
    $row = mysqli_fetch_array($r);
    array_push($result, array(
        "id" => $row['id'],
        "Nama" => $row['nama'],
        "Posisi" => $row['posisi'],
        "Gaji" => $row['gaji'],
    ));

    //menampilkan array ke JSON
    echo json_encode(array('result' => $result));

    mysqli_close($con);
