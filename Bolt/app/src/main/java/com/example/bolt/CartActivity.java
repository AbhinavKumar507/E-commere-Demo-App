package com.example.bolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolt.adapter.CartItemAdapter;
import com.example.bolt.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.ItemRemoved{
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Items> itemsList;
    RecyclerView cartRecyclerView;
    CartItemAdapter cartItemAdapter;
    Button buyItembtn;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart );
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        itemsList = new ArrayList<>();
        cartRecyclerView = findViewById( R.id.cart_item_container );
        buyItembtn = findViewById( R.id.cart_buy_now );
        totalAmount = findViewById( R.id.total_amount );
        cartRecyclerView.setLayoutManager(new LinearLayoutManager( this) );
        cartRecyclerView.setHasFixedSize( true );

        cartItemAdapter = new CartItemAdapter(itemsList,this);
        cartRecyclerView.setAdapter( cartItemAdapter );
        mStore.collection( "User" ).document( mAuth.getCurrentUser().getUid() )
                .collection( "Cart" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(DocumentChange doc:task.getResult().getDocumentChanges()){
                            String documentId = doc.getDocument().getId();
                            Items item =doc.getDocument().toObject(Items.class);
                            item.setDocId(documentId);
                            itemsList.add(item);
                        }
                        calculateAmount(itemsList);
                        cartItemAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText( CartActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    private void calculateAmount(List<Items> itemsList) {
        double totalAmountInDouble = 0.0;
        for(Items item:itemsList){
            totalAmountInDouble += item.getPrice();
        }
        totalAmount.setText( "Total Amount : "+totalAmountInDouble );
    }

    public void cartbuybtn(View view) {
        Intent intent = new Intent( CartActivity.this,AddressActivity.class);
        intent.putExtra( "itemsList", (Serializable) itemsList );
        startActivity( intent );
    }

    @Override
    public void onItemRemoved(List<Items> itemsList) {
        calculateAmount( itemsList );
    }
}