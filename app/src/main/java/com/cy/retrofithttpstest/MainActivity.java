package com.cy.retrofithttpstest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cy.retrofithttpstest.retrofit.ToStringConverterFactory;
import com.zhy.http.okhttp.OkHttpClientManager;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

public class MainActivity extends Activity {
    Button mBtntest;
    public static final String API_URL = "https://192.168.1.124:5800";

    public interface ITest {

        @GET("/api/test")
        Call<String> getString();

        @FormUrlEncoded
        @POST("/api/test")
        Call<String> postString(@Field("field") String first);

        @DELETE("/api/test")
        Call<String> deleteString();

        @FormUrlEncoded
        @PUT("/api/test")
        Call<String> putString(@Field("field") String o1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtntest = (Button) findViewById(R.id.mBtnTest);
        OkHttpClientManager.getInstance()/*.enableGzip(true)*/
                .setCertificate(getApplicationContext(), "server.crt");

        initRetrofit();

        mBtntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofit();
            }
        });
    }

    ITest iTest;

    private void initRetrofit() {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new ToStringConverterFactory())
                .client(OkHttpClientManager.getInstance().getOkHttpClient())
                .build();

        // Create an instance of our GitHub API interface.
        iTest = retrofit.create(ITest.class);
    }

    void testRetrofit() {
        iTest.getString().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String result = response.body();
                System.out.println("getString() result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        iTest.postString("我们").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String result = response.body();
                System.out.println("postString() result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        iTest.putString("put参数").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String result = response.body();
                System.out.println("putString() result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        iTest.deleteString().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String result = response.body();
                System.out.println("deleteString() result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
