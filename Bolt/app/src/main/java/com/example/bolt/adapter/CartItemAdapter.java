package com.example.bolt.adapter;

import android.media.Image;
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
import com.example.bolt.R;
import com.example.bolt.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    List<Items> itemsList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ItemRemoved itemRemoved;
    public CartItemAdapter(List<Items> itemsList,ItemRemoved itemRemoved){
        this.itemsList = itemsList;
        firebaseFirestore= firebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        this.itemRemoved = itemRemoved;
    }
    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.single_cart_item,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartItemAdapter.ViewHolder holder, int position) {
        holder.cartName.setText(itemsList.get( position ).getName());
        holder.cartPrice.setText("$"+itemsList.get( position ).getPrice());
        Glide.with(holder.itemView.getContext()).load(itemsList.get(position).getImage_url()).into(holder.cartImage);
        holder.removeItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection( "User" ).document( firebaseAuth.getCurrentUser().getUid() )
                        .collection( "Cart" ).document(itemsList.get(position).getDocId()).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            itemsList.remove( itemsList.get(position));
                            notifyDataSetChanged();
                            itemRemoved.onItemRemoved( itemsList );
                            Toast.makeText(holder.itemView.getContext(),"Item Removed",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(holder.itemView.getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                } );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartName;
        TextView cartPrice;
        TextView removeItem;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super( itemView );
            cartImage=itemView.findViewById( R.id.cart_image );
            cartName=itemView.findViewById( R.id.cart_name );
            cartPrice=itemView.findViewById( R.id.cart_price );
            removeItem=itemView.findViewById( R.id.remove_item );
        }
    }
    public interface ItemRemoved{
        public void onItemRemoved(List<Items> itemsList);
    }
}
