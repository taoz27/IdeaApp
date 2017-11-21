package com.taoz27.ideaapp.net;

import android.text.TextUtils;
import android.util.Log;

import com.taoz27.ideaapp.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class MyHttp {
    private static final int TYPE_GET=0;
    private static final int TYPE_POST=1;

    private MyHttpRequestListener listener;
    private int requestType;
    private String url;
    private String jsonParams;
    private boolean isLogin=false;

    public void execute(MyHttpRequestListener listener){
        Log.e(this.toString(),"url:"+url+"   params:"+jsonParams);
        this.listener=listener;
        Thread thread;
        if (requestType==TYPE_GET){
            thread=new Thread(){
                @Override
                public void run() {
                    getRequest();
                }
            };
        }else {
            thread=new Thread(){
                @Override
                public void run() {
                    postRequest();
                }
            };
        }
        thread.start();
    }

    public MyHttp params(String key,Object value){
        if (jsonParams.equals("")){
            jsonParams+="{"+ convertToJson(key)+":"+ convertToJson(value)+"}";
        }else {
            jsonParams= jsonParams.substring(0,jsonParams.length()-1);
            jsonParams+=","+ convertToJson(key)+":"+ convertToJson(value)+"}";
        }
        return this;
    }

    public static MyHttp Get(String url){
        MyHttp http=new MyHttp();
        http.url=url;
        http.requestType=TYPE_GET;
        http.jsonParams="";
        return http;
    }

    public static MyHttp Post(String url){
        MyHttp http=new MyHttp();
        http.url=url;
        http.requestType=TYPE_POST;
        http.jsonParams="";
        if (url.equals(Urls.UserLogin))
            http.isLogin=true;
        return http;
    }

    private String convertToJson(Object key){
        String json;
        if (key instanceof String){
            json="\""+key+"\"";
        }else {
            json=key.toString();
        }
        return json;
    }

    private void postRequest() {
        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Cookie",MyApplication.cookie);
//            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
//            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (jsonParams != null && !TextUtils.isEmpty(jsonParams)) {
                byte[] writebytes = jsonParams.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(jsonParams.getBytes());
                outwritestream.flush();
                outwritestream.close();
                if (isLogin)
                    MyApplication.cookie=conn.getHeaderField("Set-Cookie");
                Log.e("hlhupload", "doJsonPost: conn"+conn.getResponseCode()+"   "+MyApplication.cookie);
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
            }
            Log.e(this.toString(),result);
            if (listener!=null)
                listener.onSuccess(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getRequest() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestProperty("Cookie",MyApplication.cookie);
            Log.e(this.toString(),"cookie:"+MyApplication.cookie);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            if (listener!=null)
                listener.onSuccess(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
