package com.example.bolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.example.bolt.adapter.ItemsRecyclerAdapter;
import com.example.bolt.domain.Items;
import com.example.bolt.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Fragment HomeFragment;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private EditText mSearchtext;
    private FirebaseFirestore mStore;
    private List<Items> mItemsList;
    private RecyclerView mitemRecyclerView;
    private ItemsRecyclerAdapter ItemsRecyclerAdapter;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment = new HomeFragment();
        loadFragment(HomeFragment);
        mAuth = FirebaseAuth.getInstance();
        mToolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        mStore= FirebaseFirestore.getInstance();



        // Button btn = findViewById(R.id.logout);
       // btn.setOnClickListener(new View.OnClickListener(){
       //     @Override
      //      public void onClick(View v){
      //          FirebaseAuth.getInstance().signOut();
      //          startActivity(new Intent(HomeActivity.this,MainActivity.class));
    //            finish();
  //          }
//        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,HomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.cart){
            Intent intent = new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout_btn){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}