package com.kelompok2.Conf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    //Metode untuk mengirim httpPostRequest
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams){

        //Membuat URL
        URL url;

        //Objek untuk menyimpan pesan diambil dari server
        StringBuilder sbuilder = new StringBuilder();
        try {
            //Inisiasi URL
            url = new URL(requestURL);

            //Membuat Koneksi HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Konfigurasi koneksi
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            //Membuat keluaran Stream
            OutputStream ostream = con.getOutputStream();

            //Parameter untuk Permintaan CRUD
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(ostream, "UTF-8") );
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            ostream.close();
            int responseCode = con.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader breader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                sbuilder = new StringBuilder();
                String response;

                //Reading server Response
                while ((response = breader.readLine()) != null) {
                    sbuilder.append(response);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return sbuilder.toString();
    }

    public String sendGetRequest(String requestURL) {

        StringBuilder sbuilder = new StringBuilder();
        try{
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null ){
                sbuilder.append(s + "\n");
            }
        } catch (Exception e) {

        }
        return  sbuilder.toString();

    }

    public String sendGetRequestParam(String requestURL, String id){
        StringBuilder sbuilder = new StringBuilder();
        try {
            //fix update & delete data :D
            URL url = new URL(requestURL+id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sbuilder.append(s + "\n");
            }
        } catch (Exception e) {

        }
        return sbuilder.toString();

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first =false;
            }else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
