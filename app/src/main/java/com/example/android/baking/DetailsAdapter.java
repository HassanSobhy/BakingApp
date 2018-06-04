package com.example.android.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.baking.utilities.Ingredient;
import com.example.android.baking.utilities.RecipeModel;
import com.example.android.baking.utilities.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassa on 4/14/2018.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyStepViewHolder>{

    ArrayList<Steps> mSteps;
    Context mContext;
    final private DetailsItemClickListener mClickHandler ;
    DetailsAdapter(Context context , DetailsItemClickListener detailsItemClickListener){
        mContext = context;
        mClickHandler = detailsItemClickListener;
    }



    @Override
    public MyStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);
        return new MyStepViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyStepViewHolder holder, int position) {
        if (position==0)
            holder.mStepNameTextView.setText("Ingredients");
        else
        {
            holder.mStepNameTextView.setText(mSteps.get(position-1).getShortDescription());
            if (mSteps.get(position-1).getVideoURL().isEmpty())
            {

                holder.mVideoImageView.setVisibility(View.INVISIBLE);
            } else
                holder.mVideoImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size()+1;
    }

    public class MyStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mStepNameTextView;
        ImageView mVideoImageView;

        public MyStepViewHolder(View itemView) {
            super(itemView);
            mStepNameTextView = itemView.findViewById(R.id.step_name);
            mVideoImageView = itemView.findViewById(R.id.video_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onListClick(getAdapterPosition());
        }
    }
    public interface DetailsItemClickListener {
        void onListClick(int index);
    }
    public void setStepData(ArrayList<Steps> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}
