package com.codetek.railwayandroid.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.codetek.railwayandroid.R;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomUtils {
    private String url;
    private static Context context;
    private String baseUrl="http://10.0.2.2:8000/api/";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;
    public static String authKey;
    public static User userData;

    private SharedPreferences sharedPref;

    public CustomUtils(Context context, String url){
//        sharedPref=this.context.getSharedPreferences(this.context.getString(R.string.memory_key),Context.MODE_PRIVATE);
        client = new OkHttpClient();
        this.context=context;
        this.url=baseUrl+url;
        System.out.println(this.url);
    }

    public String getSharedString(String key){
        return sharedPref.getString (key,null);
    }

    public void setSharedString(String key,String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public CustomResponse doPost(JSONObject json,boolean addAuth) throws IOException {
        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request.Builder builder= new Request.Builder();

        if(addAuth==true){
            builder.addHeader("Authorization","Bearer "+authKey);
        }

        Request request = builder
                .url(this.url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new CustomResponse(response.code(),response.body().string());
        }
    }
}
