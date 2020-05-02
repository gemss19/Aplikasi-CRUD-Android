<?php
    //import database
    include 'config/con.php';

    //mendapatkan id pegawai
    $id = $_GET['id'];

    //Sql Query
    $sql = "DELETE from tb_pegawai where id = $id";

    //Menhapus pegawai 
    if (mysqli_query($con, $sql)) {
        echo "Berhasil Menghapus Pegawai";
    } else {
        echo "Gagal Menghapus Pegawai";
    }

    mysqli_close($con);
