package com.example.android.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.utilities.Ingredient;
import com.example.android.baking.utilities.RecipeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassa on 4/14/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    Context mContext;
    ArrayList<Ingredient> mIngredient;
    public IngredientAdapter(Context context)
    {
        mContext= context;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientViewHolder(view);

    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.mIngredientTextView.setText(mIngredient.get(position).getIngredient());
        holder.mQuantityTextView.setText(String.valueOf(mIngredient.get(position).getQuantity()));
        holder.mMeasureTextView.setText(mIngredient.get(position).getMeasure());

    }

    @Override
    public int getItemCount() {
        return mIngredient.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView mIngredientTextView, mQuantityTextView, mMeasureTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_text_view);
            mQuantityTextView = (TextView) itemView.findViewById(R.id.quantity_text_view);
            mMeasureTextView = (TextView) itemView.findViewById(R.id.measure_text_view);
        }
    }

    public void setIngredientData(ArrayList<Ingredient> ingredients) {
        mIngredient = ingredients;
        notifyDataSetChanged();
    }
}
