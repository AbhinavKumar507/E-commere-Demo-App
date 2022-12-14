package com.example.bolt.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolt.DetailActivity;
import com.example.bolt.R;
import com.example.bolt.domain.BestSell;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BestSellAdapter extends RecyclerView.Adapter<BestSellAdapter.ViewHolder> {
    Context context;
    List<BestSell> mBestSellList;
    public BestSellAdapter(Context context,List<BestSell> mBestSellList){
        this.context=context;
        this.mBestSellList=mBestSellList;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestsell_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull BestSellAdapter.ViewHolder holder, int position) {
        holder.mName.setText(mBestSellList.get(position).getName());
        holder.mPrice.setText(mBestSellList.get(position).getPrice()+"$");
        Glide.with(context).load(mBestSellList.get(position).getImage_url()).into(holder.mImage);

        holder.mImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",mBestSellList.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mBestSellList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        TextView mName;
        TextView mPrice;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.bs_img);
            mName = itemView.findViewById(R.id.bs_name);
            mPrice = itemView.findViewById(R.id.bs_cost);
        }
    }

}
