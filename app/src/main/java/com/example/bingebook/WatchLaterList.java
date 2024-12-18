package com.example.bingebook;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WatchLaterList extends AppCompatActivity {
    private MyAdapter adapter;
RecyclerView recyclerView;
    List<Items_Views> items = new ArrayList<Items_Views>() ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchlaterlist);

         recyclerView =findViewById(R.id.recyclerview);
       // List<WatchedGet> WatchLater = new ArrayList<>();
        List<Integer> watchLaterIds = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials",MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int userId = sharedPreferences.getInt("UserId",0);
        String Url="https://spring-boot-render-zs6y.onrender.com/watchbucket/watchlater/"+userId;
        AndroidNetworking.get(Url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray watchLaterArray = response.getJSONArray("watchlater");
                            for (int i = 0; i < watchLaterArray.length(); i++) {
                                int watched = watchLaterArray.getInt(i);
                                watchLaterIds.add(watched);
                                System.out.println("Watched ID: " + watched);
                            }
                            System.out.println(watchLaterIds);

                            for(int l =0;l<watchLaterIds.size();l++){
                                System.out.println(watchLaterIds.get(l));
                                String apiKey = "f5a57063c06c5c806da4e1bb48c1411f"; // Replace with your TMDb API key
                                int sampleId = watchLaterIds.get(l);

                                OkHttpClient client = new OkHttpClient();

                                Request movieRequest = new Request.Builder()
                                        .url("https://api.themoviedb.org/3/movie/" + sampleId + "?api_key=" + apiKey)
                                        .get()
                                        .build();

                                client.newCall(movieRequest).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if (response.isSuccessful()) {
                                            String responseBody = response.body().string();

                                            // Now parse the response string into a JSONObject
                                            try {
                                                JSONObject jsonObject = new JSONObject(responseBody);
                                                String imgUrl = jsonObject.optString("poster_path");
                                                String image = "https://image.tmdb.org/t/p/w500" + imgUrl;
                                                System.out.println("movie poster path : "+ image);
                                                runOnUiThread(() -> {

                                                    addItem(image,sampleId);

                                                });

                                            }



                                            catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            System.err.println("Error fetching movie details. HTTP Status: " + response.code());
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error parsing JSON", e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("API_ERROR", anError.getMessage(), anError);
                    }
                });


        AndroidNetworking.initialize(getApplicationContext());

        //items.add(new Items_Views("https://image.tmdb.org/t/p/w500/bXQDZj79irceldVGLzL6RFdyC4Z.jpg",11));
//        items.add(new Items_Views(R.drawable.sample,2));
//        items.add(new Items_Views(R.drawable.sample,3));
//        items.add(new Items_Views(R.drawable.loadingimage,4));



    }



    private void addItem(String image,int id){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapter(this, items);
        items.add(new Items_Views(image,id));
        System.out.println( image +  "  " + id);


    }

}
