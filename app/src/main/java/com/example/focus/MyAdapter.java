package com.example.focus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    Context myContext;
    List<MyLocation> myLocations;

    public MyAdapter(Context context) {
        myContext = context;
        myLocations = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(myContext).inflate(R.layout.list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyLocation currLoc = myLocations.get(position);
        holder.name.setText(currLoc.getName());
        holder.coords.setText(currLoc.formatLoc());
    }

    @Override
    public int getItemCount() {
        return myLocations.size();
    }

    public void updateData(List<MyLocation> newData) {
        myLocations = newData;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView name, coords;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.loc_name);
            coords = itemView.findViewById(R.id.coords);
        }
    }
}
