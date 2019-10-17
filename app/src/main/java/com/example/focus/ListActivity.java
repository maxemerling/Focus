package com.example.focus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<String> locations = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;
    Context context = getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location_string");
        locations.add(location);

        recyclerView = (RecyclerView) findViewById(R.id.focus_location_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(locations, context);

    }


}
