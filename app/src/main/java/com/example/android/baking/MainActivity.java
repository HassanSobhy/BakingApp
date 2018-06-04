package com.example.android.baking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.widget.Toast;

import com.example.android.baking.utilities.Ingredient;
import com.example.android.baking.utilities.RecipeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements BakingAdapter.BakingItemClickListener {

    public static final String DATA_EXTRA = "Steps";
    private RecyclerView recyclerView;
    private LayoutManager layoutManager;
    BakingAdapter mAdapter;
    private List<RecipeModel> mRecipeData;
    static ArrayList<Ingredient> arrayList;
    private BakingTestingIdlingResource bakingTestingIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();
        recyclerView = findViewById(R.id.recycler_view);
        if (findViewById(R.id.tablet_layout)!=null)
        {
            int numberOfColumns = 3;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,numberOfColumns);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
        } else
        {
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        }

        mAdapter = new BakingAdapter(this,this);

        // Get the data from server.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (bakingTestingIdlingResource != null) {
            bakingTestingIdlingResource.setIdleState(false);
        }
        BakingApi bakingApi = retrofit.create(BakingApi.class);
        Call<List<RecipeModel>> connection = bakingApi.getRecipes();
        connection.enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {

                    mRecipeData = response.body();
                    mAdapter.setRecipeData(mRecipeData);
                    //Toast.makeText(MainActivity.this,mRecipeData.get(0).getName(),Toast.LENGTH_LONG).show();
                    recyclerView.setAdapter(mAdapter);
                if (bakingTestingIdlingResource != null) {
                    bakingTestingIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onListClick(int index) {
        arrayList = new ArrayList<>(mRecipeData.get(index).getIngredients());
        Intent intentSvc = new Intent(this,WidgetUpdateService.class);
        intentSvc.setAction("UPDATE_LIST");
        startService(intentSvc);
        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(DATA_EXTRA,mRecipeData.get(index));
        //Toast.makeText(this,String.valueOf(mRecipeData.get(index).getIngredients().size()),Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public interface BakingApi {
        @GET("baking.json")
        Call<List<RecipeModel>> getRecipes();
    }
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (bakingTestingIdlingResource == null) {
            bakingTestingIdlingResource = new BakingTestingIdlingResource();
        }
        return bakingTestingIdlingResource;
    }
}
