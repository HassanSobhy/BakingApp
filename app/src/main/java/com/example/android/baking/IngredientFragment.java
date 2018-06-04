package com.example.android.baking;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.baking.utilities.Ingredient;

import java.util.ArrayList;

/**
 * Created by hassa on 4/14/2018.
 */

public class IngredientFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Ingredient> mIngredients;
    IngredientAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_fragment, container, false);
        mIngredients = getArguments().getParcelableArrayList("Ingredient");
        recyclerView = view.findViewById(R.id.ingredient_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new IngredientAdapter(getActivity());
        mAdapter.setIngredientData(mIngredients);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
