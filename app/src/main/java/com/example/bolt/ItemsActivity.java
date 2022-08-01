package com.example.bolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import com.example.bolt.adapter.ItemsRecyclerAdapter;
import com.example.bolt.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    private FirebaseFirestore mStore;
    List<Items> mItemsList;
    private RecyclerView itemRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_items );
        String type = getIntent().getStringExtra( "type" );
        mStore = FirebaseFirestore.getInstance();
        mItemsList = new ArrayList<>();
        mToolbar = findViewById( R.id.item_toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setTitle( "Items" );
        itemRecyclerView = findViewById( R.id.items_recycler );
        itemRecyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
        itemsRecyclerAdapter = new ItemsRecyclerAdapter( this, mItemsList );
        itemRecyclerView.setAdapter( itemsRecyclerAdapter );

        if (type == null || type.isEmpty()) {
            mStore.collection( "All" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NotNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            Log.i( "TAG", "On Complete" + doc.toString() );
                            Items items = doc.toObject( Items.class );
                            mItemsList.add( items );
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type !=null && type.equalsIgnoreCase("man")){
            mStore.collection( "All" ).whereEqualTo("type","man").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NotNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            Log.i( "TAG", "On Complete" + doc.toString() );
                            Items items = doc.toObject( Items.class );
                            mItemsList.add( items );
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type !=null && type.equalsIgnoreCase("woman")){
            mStore.collection( "All" ).whereEqualTo("type","woman").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NotNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            Log.i( "TAG", "On Complete" + doc.toString() );
                            Items items = doc.toObject( Items.class );
                            mItemsList.add( items );
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type !=null && type.equalsIgnoreCase("kid")){
            mStore.collection( "All" ).whereEqualTo("type","kid").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NotNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            Log.i( "TAG", "On Complete" + doc.toString() );
                            Items items = doc.toObject( Items.class );
                            mItemsList.add( items );
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}