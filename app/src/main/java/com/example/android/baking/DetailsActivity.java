package com.example.android.baking;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.utilities.Ingredient;
import com.example.android.baking.utilities.RecipeModel;
import com.example.android.baking.utilities.Steps;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.baking.MainActivity.DATA_EXTRA;

public class DetailsActivity extends AppCompatActivity implements DetailsAdapter.DetailsItemClickListener {


    static boolean mTwoPane ;
    private RecipeModel mRecipeData;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredient> ingredients;


    static DetailsAdapter.DetailsItemClickListener itemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mRecipeData = getIntent().getParcelableExtra(DATA_EXTRA);
        setTitle(mRecipeData.getName());
        steps = new ArrayList<Steps>(mRecipeData.getSteps());
        ingredients = new ArrayList<Ingredient>(mRecipeData.getIngredients());
        itemClickListener = this;
        DetailsAdapter mAdapter = new DetailsAdapter(this,itemClickListener);

        if (findViewById(R.id.details_fragment_container)!=null)
            mTwoPane = true;
        else
            mTwoPane = false;

        if (savedInstanceState == null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            final Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Steps",steps);
            detailsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.details_activity, detailsFragment).commit();
        }
    }

    @Override
    public void onListClick(int index) {
        if (index==0)
        {
            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList("Ingredient",ingredients);
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(bundle1);
            if (mTwoPane)
            {
                DetailsActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.details_fragment_container,ingredientFragment).commit();
            } else
            {
                DetailsActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.details_activity,ingredientFragment).commit();
            }
        }
        else {
            StepsFragment stepsFragment = new StepsFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList("Steps",steps);
            bundle2.putInt("Position",index-1);
            stepsFragment.setArguments(bundle2);
            if (mTwoPane)
            {
                getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, stepsFragment).commit();

            } else
            {
                getFragmentManager().beginTransaction().replace(R.id.details_activity, stepsFragment).commit();

            }

        }

    }
}
