package com.example.bolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.bolt.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedOutputStream;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    TextView mTotal;
    Button payBtn;
    double amount = 0.0;
    String name ="";
    String image_url = "";
    private Toolbar mToolbar;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Items> itemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amount = getIntent().getDoubleExtra("amount",0.0);
        name=getIntent().getStringExtra("name");
        image_url=getIntent().getStringExtra("image_url");
        itemsList = (ArrayList<Items>) getIntent().getSerializableExtra( "itemsList" );
        mStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mTotal = findViewById(R.id.sub_total);
        payBtn = findViewById(R.id.pay_btn);
        if(itemsList != null && itemsList.size()>0){
            amount = 0.0;
            for(Items item: itemsList){
                amount += item.getPrice();

            }
            mTotal.setText("$"+amount+"");
        }else{
            mTotal.setText("$"+amount+"");
        }
        Checkout.preload(getApplicationContext());
        mToolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }
    public void startPayment(){
        Checkout checkout = new Checkout();
        final Activity activity = PaymentActivity.this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Abhinav");
            options.put("description", "Reference No. #123456");
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png" );
            options.put("currency", "INR");
            //double total = Double.parseDouble(mAmountText.getText().toString());
            amount = amount * 100;

            options.put("amount", amount);
            JSONObject preFill = new JSONObject();

            preFill.put("email", "developer");
            preFill.put("contact", "88658879");
            preFill.put("prefill", preFill);
            checkout.open(activity, options);
        }
        catch (Exception e){
            Log.e("TAG","Error in starting Razorpay Checkout",e);

        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText( this, "Payment Succesfull", Toast.LENGTH_SHORT ).show();
        if (itemsList != null && itemsList.size() > 0) {
            for (Items items : itemsList) {
                Map<String, Object> mMap = new HashMap<>();
                mMap.put( "name", items.getName() );
                mMap.put( "image_url", items.getImage_url() );
                mMap.put( "payment_id", s );

                mStore.collection( "Users" ).document( mAuth.getCurrentUser().getUid() ).collection( "Orders" ).add( mMap ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Intent intent = new Intent( PaymentActivity.this, HomeActivity.class );
                        startActivity( intent );
                        finish();
                    }
                } );
            }
            mStore.collection( "User" ).document( mAuth.getCurrentUser().getUid() )
                    .collection( "Cart" ).document().delete();
        } else {
            Map<String, Object> mMap = new HashMap<>();
            mMap.put( "name", name );
            mMap.put( "image_url", image_url );
            mMap.put( "payment_id", s );

            mStore.collection( "Users" ).document( mAuth.getCurrentUser().getUid() ).collection( "Orders" ).add( mMap ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                    Intent intent = new Intent( PaymentActivity.this, HomeActivity.class );
                    startActivity( intent );
                    finish();
                }
            } );
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"" + s,Toast.LENGTH_SHORT).show();

    }
}