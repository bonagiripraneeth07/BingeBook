package com.example.bingebook;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpenTvShows extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opentvshows);
        ImageView backpathimg = findViewById(R.id.backpathimg);
        ImageView posterimg = findViewById(R.id.posterimg);
        TextView description = findViewById(R.id.description);
        ImageView rating_img5 = findViewById(R.id.rating_img5);
        ImageView rating_img4 = findViewById(R.id.rating_img4);
        ImageView rating_img3 = findViewById(R.id.rating_img3);
        ImageView rating_img2 = findViewById(R.id.rating_img2);
        ImageView rating_img1 = findViewById(R.id.rating_img1);

        TextView rating_int = findViewById(R.id.rating_int);
        Button watchlaterButton = findViewById(R.id.watchlaterButton);
        Button watchedButton = findViewById(R.id.watchedButton);
        TextView Title = findViewById(R.id.Title);
        TextView movieDetailsTextView;
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int userId = sharedPreferences.getInt("UserId", 0);
        System.out.println(userId + username + " from user name user id");
        // movieDetailsTextView = findViewById(R.id.movieDetailsTextView);

        // Get the passed movie ID
        // int movieId = Integer.parseInt(getIntent().getStringExtra("MOVIE_ID"));
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        // Display the movie ID or fetch more details based on it
        // movieDetailsTextView.setText("Movie: " + movieId);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/tv/" +movieId+ "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjI1MzkyNS40NDQsInN1YiI6IjY3NDAxOGU1ZGFlMmU2YTkzODI1NTQ1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WMwSTGfZQPhgkcuCPLHQV_kz4eIGNoiEo3P2DO384mk")
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
                        String ratingString = jsonObject.optString("vote_average");
                        double rating = Double.parseDouble(ratingString);
                        System.out.println(rating);
                        double num = 8.7;


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
                            if (rating >= 8) {
                                rating_img5.setVisibility(View.VISIBLE);
                            } else if (rating >= 7) {
                                rating_img4.setVisibility(View.VISIBLE);
                            } else if (rating >= 6) {
                                rating_img3.setVisibility(View.VISIBLE);
                            } else if (rating >= 5) {
                                rating_img2.setVisibility(View.VISIBLE);
                            } else {
                                rating_img1.setVisibility(View.VISIBLE);
                            }
                            rating_int.setText(ratingString);

                            watchlaterButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(OpenTvShows.this, "from watchlater", Toast.LENGTH_SHORT).show();
                                    ArrayList<Integer> watchlaterIds = new ArrayList<>();
                                    watchlaterIds.add(movieId);
                                    WatchLater watchLater = new WatchLater();
                                    watchLater.setId(userId);
                                    watchLater.setWatchlaterId(watchlaterIds);
                                   // watchLater(watchLater);


                                }
                            });
                            watchedButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(OpenTvShows.this, "from watched", Toast.LENGTH_SHORT).show();
                                    dialog(movieId, userId);

                                }
                            });
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }
        });


    }

    public void dialog(int movieid, int userid) {
        final Dialog c_dialog = new Dialog(this);
        c_dialog.setContentView(R.layout.watched_dialog);
//        ImageView call_dialog = c_dialog.findViewById(R.id.call_dialog);
//        ImageView message_dialog = c_dialog.findViewById(R.id.message_dialog);
//        ImageView booknow_dialog = c_dialog.findViewById(R.id.booknow_dialog);
//        LottieAnimationView bookedsuccess = c_dialog.findViewById(R.id.bookedsuccess);
        EditText addComment_Edittext = c_dialog.findViewById(R.id.addComment_editText);
        RatingBar ratingBar = c_dialog.findViewById(R.id.ratingbar);
        Button submitbtn = c_dialog.findViewById(R.id.submitButton);

        c_dialog.setCancelable(true);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comment = addComment_Edittext.getText().toString();
                int rating = (int) ratingBar.getRating();
                Toast.makeText(OpenTvShows.this, "rating : " + rating + "comment : " + Comment, Toast.LENGTH_SHORT).show();
                Watched watched = new Watched(userid, movieid, rating, Comment);
                System.out.println("watched class :" + watched.getComment());
                System.out.println(watched.getWatched());
                System.out.println(watched.getRating());
                System.out.println(watched.getUserid());
                watched(watched);

            }
        });
        c_dialog.show();
    }

    private void watched(Watched watched) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid",watched.getUserid());
            jsonObject.put("watched", watched.getWatched());
            jsonObject.put("rating",watched.getRating());
            jsonObject.put("comment",watched.getComment());
            System.out.println("from 204");
            System.out.println("userid : " +watched.getUserid());
            System.out.println("watched :" +watched.getWatched());
            System.out.println("rating : "+ watched.getRating());
            System.out.println("comment : " + watched.getComment());
            // Log.d("userid:","Response: watched.getUserid(),"watched: ",watched.getWatched(),"rating",watched.getRating(),"comment",watched.getComment());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("https://spring-boot-render-zs6y.onrender.com/addwatched")
                .addJSONObjectBody(jsonObject) // Attach the JSON payload
                .setTag("POST_REQUEST")
                .setPriority(Priority.MEDIUM)
                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response ) {
//                        // Handle successful response
//                        Log.d("API_RESPONSE", "Response: "  );
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        // Handle error
//                        Log.e("API_ERROR", "Error: " + anError.getMessage());
//                    }
//                });
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // Handle primitive response (e.g., "2")
                        Log.d("API_RESPONSE", "Response: " + response);
                        Toast.makeText(OpenTvShows.this, "successfully added to watch list", Toast.LENGTH_SHORT).show();
//                        try {
//
//                            if ( Integer.parseInt(response)!=404){
//                                UserId = Integer.parseInt(response);
//                                Toast.makeText(Login.this, "User found" + UserId, Toast.LENGTH_SHORT).show();
//                                reDirect(UserId,userName);
//                            }else{
//                                Toast.makeText(Login.this, "User not foud", Toast.LENGTH_SHORT).show();
//                            }
//                            // If you expect an integer
//                            Log.d("API_RESPONSE", "Parsed result: " + UserId);
//                        } catch (NumberFormatException e) {
//                            Log.e("API_ERROR", "Error parsing response to integer");
//                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("API_ERROR", "Error: " + anError.getMessage());
                        Toast.makeText(OpenTvShows.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();


                    }
                });


    }
    }

