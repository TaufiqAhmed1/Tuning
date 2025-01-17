package com.example.tuning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<MusicFiles> albumFiles;
    View view;
    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.music_items,parent,false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AlbumDetailsAdapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.album_name.setText(albumFiles.get(position).getTitle());
        byte[] image = getAlbumArt(albumFiles.get(position).getPath());
        if(image!=null){
            Glide.with(mContext).asBitmap()
                    .load(image).into(holder.album_image);
        }
        else{
            Glide.with(mContext).load(R.drawable.bewedoc)
                    .into(holder.album_image);
        }


    }


    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder  extends RecyclerView.ViewHolder  {
        ImageView album_image;
        TextView album_name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            album_image = itemView.findViewById(R.id.music_img);
            album_name = itemView.findViewById(R.id.music_file_name);
        }
    }
    private  byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever= new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return art;
    }
}
