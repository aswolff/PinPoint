package com.example.pinpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FakeShop extends AppCompatActivity {

    TextView tv_header;
    private ExampleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<shoppingItem> mShoppingItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_shop);

        createItemList();
        buildRecyclerView();            //allows user to click items

        //Hardcoding some items into the shop
        insertItem("iPhone", 800.00);
        insertItem("MacBook", 1400.00);
        insertItem("test", 1400.00);

        tv_header = (TextView) findViewById(R.id.tv_fakeshopheader);
        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FakeShop.this, Welcome.class));
            }
        });
    }

    public void createItemList() {
        mShoppingItemList = new ArrayList<>();
    }

    public void insertItem(String name, double price) {
        mShoppingItemList.add(new shoppingItem(name, price));
        mAdapter.notifyDataSetChanged();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_items);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mShoppingItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("cart");

                String itemName = mShoppingItemList.get(position).getmName();
                String itemPrice = Double.toString(mShoppingItemList.get(position).getmPrice());

                //Adds item to database
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean itemExists = false;
                        int childrenCount = Math.toIntExact(snapshot.child("Item Name").getChildrenCount());
                        //Check if string is already in the watchlist
                        for (int i = 0; i < childrenCount; i++) {
                            String currentChild = (String) snapshot.child("Item Name").child(Integer.toString(i)).getValue();
                            if (currentChild != null) {
                                if (currentChild.equals(itemName)) {
                                    Toast.makeText(FakeShop.this, "Item already in cart" + Integer.toString(childrenCount), Toast.LENGTH_SHORT).show();
                                    itemExists = true;
                                }
                            }
                        }
                        if(!itemExists){
                            //Insert into database
                            reference.child("Item Name").child(Integer.toString(childrenCount)).setValue(itemName);
                            reference.child("Price").child(Integer.toString(childrenCount)).setValue(itemPrice);
                            Toast.makeText(FakeShop.this, "Item was added to the cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mAdapter.notifyItemChanged(position);
            }
        });
    }
}