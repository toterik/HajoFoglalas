package com.example.hajofoglalas;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShipAdapter extends RecyclerView.Adapter<ShipAdapter.ViewHolder> {

    private ArrayList<Ship> mShipItemsData;
    private ArrayList<Ship> mShipItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    ShipAdapter(Context context, ArrayList<Ship> itemsData)
    {
        this.mShipItemsData = itemsData;
        this.mShipItemsDataAll = itemsData;
        this.mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.custom_ship,parent,false));
    }

    @Override
    public void onBindViewHolder(ShipAdapter.ViewHolder holder, int position) {
        Ship currentItem = mShipItemsData.get(position);

        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return mShipItemsDataAll.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mName;
        private TextView mPrice;
        private TextView mDescription;
        private ImageView mImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDescription = itemView.findViewById(R.id.description);
            mImage = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.name);
            mPrice = itemView.findViewById(R.id.price);

            itemView.findViewById(R.id.booking).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //TODO
                }
                
            });
        }

        public void bindTo(Ship currentItem)
        {
            mDescription.setText(currentItem.getDesccription());
            mName.setText(currentItem.getName());
            mPrice.setText(currentItem.getPrice());
            Glide.with(mContext).load(currentItem.getImageResource()).into(mImage);
        }
    };
};
