package com.example.android.baking;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by hassa on 4/15/2018.
 */

public class WidgetUpdateService extends IntentService {
    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction().equals("UPDATE_LIST"))
        {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
            //Trigger data update to handle the GridView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
            BakingWidgetProvider a = new BakingWidgetProvider();
            a.onUpdate(this,appWidgetManager,appWidgetIds);

        }
    }
}
