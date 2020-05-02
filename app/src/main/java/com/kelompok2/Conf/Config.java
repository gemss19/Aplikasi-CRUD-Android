package com.kelompok2.Conf;

/**
 *
 * Created By Gemss@Isekai
 *  29 - 04 - 2020
 *
 */

public class Config {

    //Pengalamatan Lokasi CRUD PHP
    public static final String URL_ADD = "http://192.168.1.6/mobile-app/tambahPgw.php";
    public static final String URL_GET_ALL = "http://192.168.1.6/mobile-app/viewAllPgw.php";
    public static final String URL_GET_PGW = "http://192.168.1.6/mobile-app/viewPgw.php?id=";
    public static final String URL_UPDATE_PGW = "http://192.168.1.6/mobile-app/updatePgw.php";
    public static final String URL_DELETE_PGW = "http://192.168.1.6/mobile-app/deletePgw.php?id=";

    //Key untuk Melakukan CRUD
    public static final String KEY_PGW_ID = "id";
    public static final String KEY_PGW_NAMA = "nama";
    public static final String KEY_PGW_POSISI = "posisi";
    public static final String KEY_PGW_GAJI = "gaji";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJI = "salary";

    //ID Karyawan
    public static final String PGW_ID = "pgw_id";




}
