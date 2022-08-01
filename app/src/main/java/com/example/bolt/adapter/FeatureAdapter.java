package com.example.bolt.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolt.DetailActivity;
import com.example.bolt.R;
import com.example.bolt.domain.Feature;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    Context context;
    List<Feature> mFeatureList;
    public FeatureAdapter(Context context, List<Feature> mFeatureList) {
        this.context=context;
        this.mFeatureList=mFeatureList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.single_feature_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeatureAdapter.ViewHolder holder, int position) {
        holder.mFetCost.setText(mFeatureList.get(position).getPrice()+"$");
        holder.mFetName.setText(mFeatureList.get(position).getName());
        Glide.with(context).load(mFeatureList.get(position).getImage_url()).into(holder.mFetImage);
        holder.mFetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",mFeatureList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mFeatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mFetImage;
        TextView mFetCost;
        TextView mFetName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mFetImage = itemView.findViewById(R.id.f_img);
            mFetCost = itemView.findViewById(R.id.f_cost);
            mFetName = itemView.findViewById(R.id.f_name);
        }
    }
}
