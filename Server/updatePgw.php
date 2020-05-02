<?php
    //import database
    include 'config/con.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        ////mendapatkan nilai dari variable
        $id = $_POST['id'];
        $name = $_POST['name'];
        $desg = $_POST['desg'];
        $sal = $_POST['salary'];

        //Sql Query
        $sql = "UPDATE tb_pegawai set nama = '$name', posisi = '$desg', gaji = '$sal' where id = $id;";

        //Update Database
        if(mysqli_query($con,$sal)){
            echo "Berhasil Update Pegawai";
        }else{
            echo "Gagal Update Pegawai";
        }

        mysqli_close($con);

    }
