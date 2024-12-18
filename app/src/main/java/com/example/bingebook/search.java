package com.example.bingebook;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
 * Use the {@link search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static search newInstance(String param1, String param2) {
        search fragment = new search();
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

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        TextView search_text = v.findViewById(R.id.search_text);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView searchicon = v.findViewById(R.id.searchicon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView imageView = v.findViewById(R.id.imageView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Switch tvSwitch = v.findViewById(R.id.tvSwitch);
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_KeyWord;
                search_KeyWord = search_text.getText().toString();
                System.out.println(search_KeyWord);


                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://api.themoviedb.org/3/search/multi?query=" + search_KeyWord + "&include_adult=false&language=en-US&page=1")
                            .get()
                            .addHeader("accept", "application/json")
                            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWE1NzA2M2MwNmM1YzgwNmRhNGUxYmI0OGMxNDExZiIsIm5iZiI6MTczMjU5Mjk1NC4xNjE5MDI0LCJzdWIiOiI2NzQwMThlNWRhZTJlNmE5MzgyNTU0NTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.Baw_7Ls8doNXv9PF79P4NY6M7-7Uq2sg1HVejznKI9E")
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
                                    JSONArray resultsArray = jsonObject.optJSONArray("results");
                                    String title;
                                    String img;
                                    JSONObject firstResult = resultsArray.getJSONObject(0);
                                    int Id = 0;
                                    try {
                                        Id = firstResult.getInt("id");
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    // Get title and poster_path of the first result
                                    title = firstResult.optString("name", "No Title");
                                    img = firstResult.optString("poster_path", "No Image");
                                    String imageUrl = "https://image.tmdb.org/t/p/original" + img;
                                    // Log or update the UI with the extracted details
                                    Log.d("FirstResult", "Title: " + title + ", Image URL: " + imageUrl);
                                    System.out.println(Id);
                                    //JSONArray resultsArray = jsonObject.getJSONArray("results");
                                    // Convert to Java List or Log each item

                                    int finalId = Id;
                                    requireActivity().runOnUiThread(() -> {
                                        imageView.setVisibility(View.VISIBLE);
                                        new ImageLoader().loadImageFromUrl(imageUrl, imageView);
                                        imageView.setTag(finalId);
                                        imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int movieId = (int) v.getTag();
                                                if (tvSwitch.isChecked()) {
                                                    Toast.makeText(getActivity(), "series", Toast.LENGTH_SHORT).show();
                                                    openTvDetails(movieId);

                                                } else {
                                                    openMovieDetails(movieId);
                                                    System.out.println(movieId);
                                                }
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

        });

        return v;
    }
    private void openMovieDetails(int movieId) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        startActivity(intent);
    }
    private void openTvDetails(int tvId){
        Intent intent = new Intent(getContext(), OpenTvShows.class);
        intent.putExtra("MOVIE_ID", tvId);
        startActivity(intent);
    }

}