package com.example.mirodone.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";
    private final OnDownLoadComplete mCallBack;
    //track our download status
    private DownloadStatus mDownloadStatus;


     GetRawData(OnDownLoadComplete callBack) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        mCallBack = callBack;
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread Starts");

        onPostExecute(doInBackground(s));

        Log.d(TAG, "runInSameThread Ends");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter " + s);
        if (mCallBack != null) {
            mCallBack.onDownloadComplete(s, mDownloadStatus);
        }
        // super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        //check to see if we have an URL
        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try {
//set the status to processing using the constant enum,
// then attempt to create a URL from the string parameter
// and we throw an exception if the URL is not valid (MalformedURLException).
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            //we use a GET request type
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //when using readLine() the newline characters are stripped off at the end of each line the we get
            //and we have to append them back to the string we are building with the second append

/*            String line;
              while (null != (line = reader.readLine())){
                result.append(line).append("\n");
            }*/

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: Exception reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Need Permision ? " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
                }
            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

    interface OnDownLoadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }


}
