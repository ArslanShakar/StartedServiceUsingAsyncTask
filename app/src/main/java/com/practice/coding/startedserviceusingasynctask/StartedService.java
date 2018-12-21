package com.practice.coding.startedserviceusingasynctask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class StartedService extends Service {

    private static final String TAG = "TAG";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Callled : ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand Called : ");

        String songName = intent.getStringExtra(Constants.MESSAGE_KEY);

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(songName);

        /*
        Every time when the song is received one async task object created and that object completed that task.
        like here in the onStartCommand we received 3 songs name via intent ..for every song one async object is created.
        */

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //omBind does not call when we use started service
        Log.d(TAG, "onBind Called.");
        return null; //started service return nothing so here we require to implements this method so pass null
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy Called.");
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        //Only doInBackground mehtod runs on Background thread..all other methods of AsyncTask are run on Main Thread.
        @Override
        protected String doInBackground(String... songsPlayList) {
            for (String songName : songsPlayList) {
                try {
                    publishProgress("\nDownloading Start. . .");
                    Thread.sleep(5000);
                    publishProgress(songName + " : Download Completed!\n");
                } catch (InterruptedException e) {
                }
            }
            return "All Downloads Completed!!";
        }

        /*
        onProgressUpdate(..) method received the value from  publishProgress(..) method every time the value that is updated in
        publishProgress() that is received in onProgressUpdate() and we use that value for updating the views...
        */

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate : " + values[0]);
        }

        /*
        When doInBackground finishes its execution and the value that doInBackground return after execution .that value reveived
        by onPostExecute Method in it parameter.
        */
        @Override
        protected void onPostExecute(String valueReturnedByDoInBackground) {
            Log.d(TAG, "onPostExecute : Received value that Returned By doInBackground : " + valueReturnedByDoInBackground);
        }
    }


}
