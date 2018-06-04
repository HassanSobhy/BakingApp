package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.utilities.Ingredient;

import java.util.ArrayList;

import static com.example.android.baking.MainActivity.arrayList;

/**
 * Created by hassa on 4/15/2018.
 */

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(this.getApplicationContext(), intent);
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    ArrayList<Ingredient> record;

    public AppWidgetListView(Context applicationContext, Intent intent) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {
        record = arrayList;
    }

    @Override
    public void onDataSetChanged() {
        record = arrayList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(record==null)return 0;
        return record.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.widget_ingredient, record.get(position).getIngredient() +"\n"
                + record.get(position).getMeasure() +"\n" + record.get(position).getQuantity());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

