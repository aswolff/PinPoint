package com.example.pinpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCart extends AppCompatActivity{
    TextView tv_price;
    Button btn_checkout;
    TextView tv_header;
    ExampleAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<shoppingItem> mCartItems;
    int count = 0;
    double cost = 0;
    double finalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        tv_price = (TextView) findViewById(R.id.tv_cartPrice);
        btn_checkout = (Button) findViewById(R.id.btn_CalculatePrice);

        mCartItems = new ArrayList<>();
        buildRecyclerView();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("cart");
                int counter = 0;

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cost = 0;
                        for (int i = 0; i < snapshot.child("Price").getChildrenCount(); i++){
                            cost += Double.parseDouble(snapshot.child("Price").child(Integer.toString(i)).getValue().toString());
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                tv_price.setText("Checkout Cost: $" + cost);
            }
        });

        tv_header = findViewById(R.id.tv_cartheader);
        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewCart.this, Welcome.class));
            }
        });
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_CartItems);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mCartItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("cart");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("Item Name").getChildren()){
                    mCartItems.add(new shoppingItem(snapshot.child("Item Name").child(Integer.toString(count)).getValue().toString(), Double.parseDouble(snapshot.child("Price").child(Integer.toString(count)).getValue().toString())));
                    count++;
                }
                count = 0;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}