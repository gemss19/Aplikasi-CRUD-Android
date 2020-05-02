<?php
    //import config db
    include 'config/con.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        //Mendapatkan nilai variable
        $name = $_POST['name'];
        $desg = $_POST['desg'];
        $sal = $_POST['salary'];

        //Query Sql
        $sql = "INSERT into tb_pegawai (nama,posis,gaji) values ('$name','$desg','$sal')";


        //Eksekusi Query
        if (mysqli_query($con, $sql)) {
            echo "Berhasil Menambah Pegawai";
        } else {
            echo "Gagal Menambah Pegawai";
        }

        mysqli_close($con);
    }
