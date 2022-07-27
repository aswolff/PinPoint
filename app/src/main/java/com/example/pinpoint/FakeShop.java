package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FakeShop extends AppCompatActivity {

    TextView tv_header;
    private ExampleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<shoppingItem> mShoppingItemList;
    private ArrayList<shoppingItem> shoppingCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_shop);

        createTickerList();

        //Building recyclerview
        mRecyclerView = findViewById(R.id.rv_items);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mShoppingItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Hardcoding some items into the shop
        insertItem("iPhone");

        tv_header = (TextView) findViewById(R.id.tv_fakeshopheader);
        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FakeShop.this, Welcome.class));
            }
        });
    }

    public void createTickerList(){
        mShoppingItemList = new ArrayList<>();
    }

    public void insertItem(String name){
        mShoppingItemList.add(new shoppingItem(name));
        mAdapter.notifyDataSetChanged();
    }

    public ArrayList<shoppingItem> getmShoppingItemList(){
        return shoppingCartList;
    }
}