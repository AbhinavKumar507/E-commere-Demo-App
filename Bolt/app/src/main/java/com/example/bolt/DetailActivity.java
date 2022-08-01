package com.example.bolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bolt.domain.BestSell;
import com.example.bolt.domain.Feature;
import com.example.bolt.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class DetailActivity extends AppCompatActivity {
    private ImageView mImage;
    private TextView mItemName;
    private TextView mPrice;
    private Button mItemRating;
    private TextView mItemRatDes;
    private TextView mItemDes;
    private Button mAddToCart;
    private Button mBuyBtn;
    Feature feature = null;
    BestSell bestSell = null;
    private Toolbar mToolbar;
    Items items = null;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        mImage = findViewById( R.id.item_img );
        mItemName = findViewById( R.id.item_name );
        mPrice = findViewById( R.id.item_price );
        mItemRating = findViewById( R.id.item_rating );
        mItemRatDes = findViewById( R.id.item_rat_des );
        mItemDes = findViewById( R.id.item_des );
        mAddToCart = findViewById( R.id.item_add_cart );
        mBuyBtn = findViewById( R.id.cart_buy_now );
        mToolbar = findViewById( R.id.detail_toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mAuth = FirebaseAuth.getInstance();
        mStore =FirebaseFirestore.getInstance();
        Object obj = getIntent().getSerializableExtra( "detail" );
        if (obj instanceof Feature) {
            feature = (Feature) obj;
        } else {
            bestSell = (BestSell) obj;
        }
        if (feature != null) {
            Glide.with( getApplicationContext() ).load( feature.getImage_url() ).into( mImage );
            mItemName.setText( feature.getName() );
            mPrice.setText( feature.getPrice() + "$" );
            mItemRating.setText( feature.getRating() + "" );
            if (feature.getRating() > 3) {
                mItemRatDes.setText( "Very Good" );
            } else {
                mItemRatDes.setText( "Good" );
            }
            mItemDes.setText( feature.getDescription() );
        }
        if (bestSell != null) {
            Glide.with( getApplicationContext() ).load( bestSell.getImage_url() ).into( mImage );
            mItemName.setText( bestSell.getName() );
            mPrice.setText( bestSell.getPrice() + "$" );
            mItemRating.setText( bestSell.getRating() + "" );
            if (bestSell.getRating() > 3) {
                mItemRatDes.setText( "Very Good" );
            } else {
                mItemRatDes.setText( "Good" );
            }
            mItemDes.setText( bestSell.getDescription() );

            //mBuyBtn.setOnClickListener( view -> {
            //   Intent intent = new Intent( DetailActivity.this, AddressActivity.class );
            //   startActivity(intent);
            //} );
        }
    }

    public void goToAddress(View view) {
        Intent intent = new Intent( DetailActivity.this, AddressActivity.class );
        if(feature!=null){
            intent.putExtra("item",feature);
        }
        if(bestSell !=null){
            intent.putExtra("item",bestSell);
        }
        startActivity(intent);
    }

    public void goToCart(View view) {
        if (feature != null) {
            mStore.collection( "User" ).document( mAuth.getCurrentUser().getUid() )
                    .collection( "Cart" ).add( feature ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                    Toast.makeText( DetailActivity.this, "Item Added To Cart", Toast.LENGTH_SHORT ).show();
                }
            } );
        }
        if (bestSell != null) {
            mStore.collection( "User" ).document( mAuth.getCurrentUser().getUid() )
                    .collection( "Cart" ).add( bestSell ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                    Toast.makeText( DetailActivity.this, "Item Added To Cart", Toast.LENGTH_SHORT ).show();
                }
            } );

        }
    }
}