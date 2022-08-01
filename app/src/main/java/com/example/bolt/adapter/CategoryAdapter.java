package com.example.bolt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolt.ItemActivity;
import com.example.bolt.ItemsActivity;
import com.example.bolt.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.example.bolt.domain.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private  Context context;
    private List<Category> mCategoryList;
    public CategoryAdapter(Context context, List<Category> mCategoryList) {
        this.context=context;
        this.mCategoryList=mCategoryList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(mCategoryList.get(position).getImg_url()).into(holder.mTypeImg);
        holder.mTypeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsActivity.class);
                intent.putExtra("type",mCategoryList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mTypeImg;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mTypeImg = itemView.findViewById(R.id.category_img);
        }
    }
}
