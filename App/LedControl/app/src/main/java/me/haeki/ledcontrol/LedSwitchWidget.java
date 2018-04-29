package me.haeki.ledcontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.webkit.WebView;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class LedSwitchWidget extends AppWidgetProvider {

    private static final String OnOff_Button = "automaticWidgetSyncButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.led_switch_widget);
        watchWidget = new ComponentName(context, LedSwitchWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.ledOnOffButton, getPendingSelfIntent(context, OnOff_Button));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (OnOff_Button.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.led_switch_widget);
            watchWidget = new ComponentName(context, LedSwitchWidget.class);


            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            boolean widgetLedState = sharedPref.getBoolean("widgetLedState",false);
            String state = "off";

            if(widgetLedState) {
                remoteViews.setInt(R.id.ledOnOffButton, "setBackgroundResource", R.drawable.ic_led_bulb_off);
                widgetLedState = false;
                state = "off";
                System.out.println("Turned off");
            } else {
                remoteViews.setInt(R.id.ledOnOffButton, "setBackgroundResource", R.drawable.ic_led_bulb_on);
                widgetLedState = true;
                state = "on";
                System.out.println("Turned on");
            }

            String urlIP = sharedPref.getString("pref_ip", "192.168.158.39");
            try {
                WebView testWebView = new WebView(context);
                testWebView.loadUrl("http://" + urlIP + "/execs/switchState.php?q=" + state);
                //URL url = new URL("http://" + urlIP + "/execs/switchState.php?q=" + state);
                //System.out.println("http://" + urlIP + "/execs/switchState.php?q=" + state);

                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.disconnect();
            //} catch (MalformedURLException e) {
                //e.printStackTrace();
            //} catch (IOException e) {
                //e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putBoolean("widgetLedState", widgetLedState);
            prefEditor.apply();


            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}


