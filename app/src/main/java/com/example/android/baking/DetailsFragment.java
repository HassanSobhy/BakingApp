package com.example.android.baking;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.baking.utilities.Ingredient;
import com.example.android.baking.utilities.Steps;

import java.util.ArrayList;

import static com.example.android.baking.DetailsActivity.itemClickListener;

/**
 * Created by hassa on 4/14/2018.
 */

public class DetailsFragment extends Fragment  {
    private RecyclerView recyclerView;
    private ArrayList<Steps> steps;
    LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        recyclerView = view.findViewById(R.id.details_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        steps= getArguments().getParcelableArrayList("Steps");
        DetailsAdapter mAdapter = new DetailsAdapter(getActivity(),itemClickListener);
        mAdapter.setStepData(steps);
        recyclerView.setAdapter(mAdapter);
        return  view;
    }


}
