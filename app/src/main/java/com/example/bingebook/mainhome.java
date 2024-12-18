package com.example.bingebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainhome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainhome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainhome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainhome.
     */
    // TODO: Rename and change types and number of parameters
    public static mainhome newInstance(String param1, String param2) {
        mainhome fragment = new mainhome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=   inflater.inflate(R.layout.fragment_mainhome, container, false);
        ImageView img1 = view.findViewById(R.id.img1);
       // img2=view.findViewById(R.id.img1);
       ImageView  img2 = view.findViewById(R.id.img2);
        ImageView  img3 = view.findViewById(R.id.img3);
        ImageView  img4 = view.findViewById(R.id.img4);
        ImageView  img5= view.findViewById(R.id.img5);
        ImageView  img6 = view.findViewById(R.id.img6);
        ImageView  img7 = view.findViewById(R.id.img7);
        ImageView trending_img1 = view.findViewById(R.id.trending_img1);
        ImageView trending_img2 = view.findViewById(R.id.trending_img2);
        ImageView trending_img3 = view.findViewById(R.id.trending_img3);
        ImageView trending_img4 = view.findViewById(R.id.trending_img4);
        ImageView trending_img5 = view.findViewById(R.id.trending_img5);
        ImageView trending_img6 = view.findViewById(R.id.trending_img6);

        ImageView nowplaying_img1 = view.findViewById(R.id.nowplaying_img1);
        ImageView nowplaying_img2 = view.findViewById(R.id.nowplaying_img2);
        ImageView nowplaying_img3 = view.findViewById(R.id.nowplaying_img3);
        ImageView nowplaying_img4 = view.findViewById(R.id.nowplaying_img4);
        ImageView nowplaying_img5 = view.findViewById(R.id.nowplaying_img5);
        ImageView nowplaying_img6 = view.findViewById(R.id.nowplaying_img6);

        ImageView popular_img1 =view.findViewById(R.id.popular_img1);
        ImageView popular_img2 =view.findViewById(R.id.popular_img2);
        ImageView popular_img3 =view.findViewById(R.id.popular_img3);
        ImageView popular_img4 =view.findViewById(R.id.popular_img4);
        ImageView popular_img5 =view.findViewById(R.id.popular_img5);
        ImageView popular_img6 =view.findViewById(R.id.popular_img6);

        String[] arr2=new String[7];
        int moviesId[]= new int[7];
        String[] trendingArray = new String[6];
        int trendingIdArray[]= new int[6];

        String[] nowplayingArray = new String[6];
        int nowplayingmoviesIdArray[] = new int[6];
        String[] popularMoviesArray = new String[6];
        int popularMoviesIdArray[]= new int[6];
//getting TMDB data through API


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjI1NTA5NS4xMTg0NDIzLCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.NXybpAfK4GhUfdpC8Dk6APxlWiJ1I7CT1e_VA7qddTU")
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
                        JSONArray resultsArray = jsonObject.getJSONArray("results");

                        // Convert to Java List or Log each item
                        for (int i = 0; i < 7; i++) {
                            JSONObject item = resultsArray.getJSONObject(i);

                            // Example: Extract data from each object
                            String title = item.optString("title");
                            String imgUrl = item.optString("backdrop_path");
                            int Id = item.getInt("id");
                            Log.d("Movie Title", title + " img path :" + imgUrl);
                           // url2[0] = "https://image.tmdb.org/t/p/original"+imgUrl;
                           // url1="https://image.tmdb.org/t/p/original"+imgUrl;
                          //  imgUrl[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                            arr2[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                            moviesId[i]=Id;
                        }
                        for(int j=0;j<moviesId.length;j++){
                            System.out.println(moviesId[j]);
                        }
                        getActivity().runOnUiThread(() -> {
                            new ImageLoader().loadImageFromUrl(arr2[0], img1);
                            img1.setTag(moviesId[0]);

                            new ImageLoader().loadImageFromUrl(arr2[1], img2);
                            img2.setTag(moviesId[1]);
                            new ImageLoader().loadImageFromUrl(arr2[2], img3);
                            img3.setTag(moviesId[2]);
                            new ImageLoader().loadImageFromUrl(arr2[3], img4);
                            img4.setTag(moviesId[3]);
                            new ImageLoader().loadImageFromUrl(arr2[4], img5);
                            img5.setTag(moviesId[4]);
                            new ImageLoader().loadImageFromUrl(arr2[5], img6);
                            img6.setTag(moviesId[5]);
                            new ImageLoader().loadImageFromUrl(arr2[6], img7);
                            img7.setTag(moviesId[6]);
                        });
                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                               // Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                               // Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                               // Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });
                        img7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                // Example: Show a Toast
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                openMovieDetails(movieId);
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }
        });
//




        //for trending section
        OkHttpClient client1 = new OkHttpClient();

        Request request1 = new Request.Builder()
                .url("https://api.themoviedb.org/3/trending/all/day?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjM0NDUzMy4zMzY4MTkyLCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.GDSQi2Facm4wX99opN5ACFXrI-KaD34ZpdrgxZ0ghyI")
                .build();
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONArray resultsArray = null;
                    try {
                        resultsArray = jsonObject.getJSONArray("results");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    for (int i = 0; i < 6; i++) {
                        JSONObject item = null;
                        try {
                            item = resultsArray.getJSONObject(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        String title = item.optString("title");
                        String imgUrl = item.optString("poster_path");
                        int Id = 0;
                        try {
                            Id = item.getInt("id");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("Movie Title", title + " img path :" + imgUrl);
                        // url2[0] = "https://image.tmdb.org/t/p/original"+imgUrl;
                        // url1="https://image.tmdb.org/t/p/original"+imgUrl;
                        //  imgUrl[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        trendingArray[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        trendingIdArray[i]=Id;
                    }
                    for(int j=0;j<trendingArray.length;j++){
                        System.out.println(trendingArray[j]);
                    }
                    for(int k =0;k<trendingIdArray.length;k++){
                        System.out.println("id : " + trendingIdArray[k] );
                    }
                    getActivity().runOnUiThread(() -> {
                        new ImageLoader().loadImageFromUrl(trendingArray[0], trending_img1);
                        trending_img1.setTag(trendingIdArray[0]);
                        trending_img1.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            openMovieDetails(movieId);
                           // Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                        });
                        new ImageLoader().loadImageFromUrl(trendingArray[1], trending_img2);
                        trending_img2.setTag(trendingIdArray[1]);
                        trending_img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int MovieId = (int) v.getTag();
                                openMovieDetails(MovieId);
                                //Toast.makeText(getActivity(), "clicked"+MovieId, Toast.LENGTH_SHORT).show();
                            }
                        });
                        new ImageLoader().loadImageFromUrl(trendingArray[2], trending_img3);
                        trending_img3.setTag(trendingIdArray[2]);
                        trending_img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag

                                openMovieDetails(movieId);
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();

                            }
                        });
                        new ImageLoader().loadImageFromUrl(trendingArray[3], trending_img4);
                        trending_img4.setTag(trendingIdArray[3]);
                        trending_img4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag

                                openMovieDetails(movieId);
                               // Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();

                            }
                        });
                        new ImageLoader().loadImageFromUrl(trendingArray[4], trending_img5);
                        trending_img5.setTag(trendingIdArray[4]);
                        trending_img5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                openMovieDetails(movieId);
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();

                            }
                        });
                        new ImageLoader().loadImageFromUrl(trendingArray[5], trending_img6);
                        trending_img6.setTag(trendingIdArray[5]);
                        trending_img6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int movieId = (int) v.getTag(); // Get the tag
                                openMovieDetails(movieId);
                                //Toast.makeText(getActivity(), "clicked: "+ movieId, Toast.LENGTH_SHORT).show();
                                // Example: Open another page
                                // openMovieDetails(movieId);
                            }
                        });

                    });
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }
        });



//for NowPlaying API
        OkHttpClient client2 = new OkHttpClient();



        Request request2 = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjM0NDUzMy4zMzY4MTkyLCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.GDSQi2Facm4wX99opN5ACFXrI-KaD34ZpdrgxZ0ghyI")
                .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONArray resultsArray = null;
                    try {
                        resultsArray = jsonObject.getJSONArray("results");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    for (int i = 0; i < 6; i++) {
                        JSONObject item = null;
                        try {
                            item = resultsArray.getJSONObject(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        String title = item.optString("title");
                        String imgUrl = item.optString("poster_path");
                        int Id = 0;
                        try {
                            Id = item.getInt("id");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("Movie Title", title + " img path :" + imgUrl);
                        // url2[0] = "https://image.tmdb.org/t/p/original"+imgUrl;
                        // url1="https://image.tmdb.org/t/p/original"+imgUrl;
                        //  imgUrl[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        nowplayingArray[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        nowplayingmoviesIdArray[i]=Id;
                    }
                    for(int j=0;j<nowplayingArray.length;j++){
                        System.out.println(nowplayingArray[j]);
                    }
                    for(int k =0;k<nowplayingmoviesIdArray.length;k++){
                        System.out.println("id : " + nowplayingmoviesIdArray[k] );
                    }
                    getActivity().runOnUiThread(() -> {
                        new ImageLoader().loadImageFromUrl(nowplayingArray[0], nowplaying_img1);
                        nowplaying_img1.setTag(nowplayingmoviesIdArray[0]);
                        nowplaying_img1.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(nowplayingArray[1], nowplaying_img2);
                        nowplaying_img2.setTag(nowplayingmoviesIdArray[1]);
                        nowplaying_img2.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(nowplayingArray[2], nowplaying_img3);
                        nowplaying_img3.setTag(nowplayingmoviesIdArray[2]);
                        nowplaying_img3.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                           // Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(nowplayingArray[3], nowplaying_img4);
                        nowplaying_img4.setTag(nowplayingmoviesIdArray[3]);
                        nowplaying_img4.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(nowplayingArray[4], nowplaying_img5);
                        nowplaying_img5.setTag(nowplayingmoviesIdArray[4]);
                        nowplaying_img5.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(nowplayingArray[5], nowplaying_img6);
                        nowplaying_img6.setTag(nowplayingmoviesIdArray[5]);
                        nowplaying_img6.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            openMovieDetails(movieId);
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                        });
                    });
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }
        });

        //for popular movies API
        OkHttpClient client3 = new OkHttpClient();

        Request request3 = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjM0NDUzMy4zMzY4MTkyLCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.GDSQi2Facm4wX99opN5ACFXrI-KaD34ZpdrgxZ0ghyI")
                .build();
        client3.newCall(request3).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONArray resultsArray = null;
                    try {
                        resultsArray = jsonObject.getJSONArray("results");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    for (int i = 0; i < 6; i++) {
                        JSONObject item = null;
                        try {
                            item = resultsArray.getJSONObject(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        String title = item.optString("title");
                        String imgUrl = item.optString("poster_path");
                        int Id = 0;
                        try {
                            Id = item.getInt("id");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("Movie Title", title + " img path :" + imgUrl);
                        // url2[0] = "https://image.tmdb.org/t/p/original"+imgUrl;
                        // url1="https://image.tmdb.org/t/p/original"+imgUrl;
                        //  imgUrl[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        popularMoviesArray[i]="https://image.tmdb.org/t/p/original"+imgUrl;
                        popularMoviesIdArray[i]=Id;
                    }
                    for(int j=0;j<trendingArray.length;j++){
                        System.out.println(trendingArray[j]);
                    }
                    for(int k =0;k<trendingIdArray.length;k++){
                        System.out.println("id : " + trendingIdArray[k] );
                    }
                    getActivity().runOnUiThread(() -> {
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[0], popular_img1);
                        popular_img1.setTag(popularMoviesIdArray[0]);
                        popular_img1.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                           // Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[1], popular_img2);
                        popular_img2.setTag(popularMoviesIdArray[1]);
                        popular_img2.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            openMovieDetails(movieId);
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                        });
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[2], popular_img3);
                        popular_img3.setTag(popularMoviesIdArray[2]);
                        popular_img3.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[3], popular_img4);
                        popular_img4.setTag(popularMoviesIdArray[3]);
                        popular_img4.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                           // Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[4], popular_img5);
                        popular_img5.setTag(popularMoviesIdArray[4]);
                        popular_img5.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                            //Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });
                        new ImageLoader().loadImageFromUrl(popularMoviesArray[5], popular_img6);
                        popular_img6.setTag(popularMoviesIdArray[5]);
                        popular_img6.setOnClickListener(v -> {
                            int movieId = (int) v.getTag();
                           // Toast.makeText(getActivity(), "Clicked: " + movieId, Toast.LENGTH_SHORT).show();
                            openMovieDetails(movieId);
                        });

                    });
                } else {
                    Log.e("Error", "Request failed with code: " + response.code());
                }
            }


        });







        return view;
    }
    private void openMovieDetails(int movieId) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ID", movieId); // Pass the movie ID to the new activity
        startActivity(intent); // Start the MovieDetailsActivity
    }

}
