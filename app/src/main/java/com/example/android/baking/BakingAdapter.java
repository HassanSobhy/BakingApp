package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.utilities.RecipeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassa on 4/14/2018.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.MyViewHolder>  {

    Context mContext;
    List<RecipeModel> mRecipeData;
    final private BakingItemClickListener mClickHandler ;


    public BakingAdapter(Context context , BakingItemClickListener bakingItemClickListener) {
        mContext = context;
        mClickHandler = bakingItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(mRecipeData.get(position).getName());
        if (!mRecipeData.get(position).getImage().isEmpty()) {
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mRecipeData.get(position).getImage()).into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.recipe_name);
            image =  itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onListClick(getAdapterPosition());
        }
    }
    public interface BakingItemClickListener {
        void onListClick(int index);
    }

    public void setRecipeData(List<RecipeModel> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }
}
