package com.example.bingebook;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class openWatchedMovies extends AppCompatActivity {
    TextView idshow;
    String comment =" ";
    int rating=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openwatchedmovies);
        ImageView backpathimg = findViewById(R.id.backpathimg);
        ImageView posterimg = findViewById(R.id.posterimg);
        TextView description = findViewById(R.id.description);
        ImageView rating_img5 = findViewById(R.id.rating_img5);
        ImageView rating_img4 = findViewById(R.id.rating_img4);
        ImageView rating_img3 = findViewById(R.id.rating_img3);
        ImageView rating_img2 = findViewById(R.id.rating_img2);
        ImageView rating_img1 = findViewById(R.id.rating_img1);
        TextView commentview = findViewById(R.id.comment);
        TextView rating_int = findViewById(R.id.rating_int);
        TextView textView5 = findViewById(R.id.textView5);
        TextView Title = findViewById(R.id.Title);
        TextView movieDetailsTextView;
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int userId = sharedPreferences.getInt("UserId", 0);
        System.out.println(userId + username + " from user name user id");
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        //userdata fetch
        Map<Integer, String> map = new HashMap<>();

        String Url="https://spring-boot-render-zs6y.onrender.com/watchbucket/watched/"+userId;
        AndroidNetworking.get(Url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);
                                int userId = user.getInt("userid");

                                 int mathcedMovieId  = user.getInt("watched");

                                 if (mathcedMovieId ==movieId){
                                     rating = user.getInt("rating");
                                       comment = user.getString("comment");
                                       if(comment!=null){
                                           textView5.setVisibility(View.VISIBLE);
                                       }
                                     commentview.setText(comment);
                                     rating_int.setText(String.valueOf(rating));
                                     Toast.makeText(openWatchedMovies.this, "comment " + comment + "rating : " + rating, Toast.LENGTH_SHORT).show();
                                     if (rating == 5) {
                                         rating_img5.setVisibility(View.VISIBLE);
                                     } else if (rating == 4) {
                                         rating_img4.setVisibility(View.VISIBLE);
                                     } else if (rating == 3) {
                                         rating_img3.setVisibility(View.VISIBLE);
                                     } else if (rating == 2) {
                                         rating_img2.setVisibility(View.VISIBLE);
                                     } else {
                                         rating_img1.setVisibility(View.VISIBLE);
                                     }

                                 }


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


        //end her e

        //from here
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieId + "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjM0NDUzMy4zMzY4MTkyLCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.GDSQi2Facm4wX99opN5ACFXrI-KaD34ZpdrgxZ0ghyI")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        // Parse the JSON response
                        JSONObject jsonObject = new JSONObject(responseData);
                        //JSONArray resultsArray = jsonObject.getJSONArray("results");

                        // Convert to Java List or Log each item
                        System.out.println(responseData);

                        // Example: Extract data from each object
                        String title = jsonObject.optString("title");
                        String imgUrl = jsonObject.optString("poster_path");
                        String backpathurl = jsonObject.optString("backdrop_path");
                        String description_overview = jsonObject.optString("overview");
                        Log.d("Movie Title", title + " img path :" + imgUrl);
//                        String ratingString = jsonObject.optString("vote_average");
//                        double rating = Double.parseDouble(ratingString);
                       // System.out.println(rating);
                       // double num = 8.7;


                        // url2[0] = "https://image.tmdb.org/t/p/original"+imgUrl;
                        // url1="https://image.tmdb.org/t/p/original"+imgUrl;
                        //  imgUrl[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        String imagePath = "https://image.tmdb.org/t/p/original" + imgUrl;
                        String backpathimgurl = "https://image.tmdb.org/t/p/original" + backpathurl;
                        runOnUiThread(() -> {
                            description.setText(description_overview);
                            Title.setText(title);
                            new ImageLoader().loadImageFromUrl(backpathimgurl, backpathimg);
                            new ImageLoader().loadImageFromUrl(imagePath, posterimg);

                            Toast.makeText(openWatchedMovies.this, "rating" +rating , Toast.LENGTH_SHORT).show();

                           // rating_int.setText(rating);
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }
        });

        //tohere
    }
}
