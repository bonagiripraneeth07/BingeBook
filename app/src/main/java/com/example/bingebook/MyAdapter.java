package com.example.bingebook;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Items_Views> items;
    public MyAdapter(Context context, List<com.example.bingebook.Items_Views> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.items_view, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//

        String imageUrl = items.get(position).getImage();


        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .into(holder.imageView);


        holder.imageView.setTag(items.get(position).getId());


        holder.imageView.setOnClickListener(v ->  {

            int clickedPosition = (int) v.getTag();
            Intent intent = new Intent( context, openWatchedMovies.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("MOVIE_ID", clickedPosition);
            context.startActivity(intent);

            Toast.makeText(context, "clicked : " + clickedPosition , Toast.LENGTH_SHORT).show();
        } );

    }
    @Override
    public int getItemCount() {
        return items.size();
    }


}
