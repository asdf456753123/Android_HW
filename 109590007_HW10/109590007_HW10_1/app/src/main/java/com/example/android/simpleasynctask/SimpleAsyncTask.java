/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.simpleasynctask;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * The SimpleAsyncTask class extends AsyncTask to perform a very simple
 * background task -- in this case, it just sleeps for a random amount of time.
 */

public class SimpleAsyncTask extends AsyncTask<Void,Void, String> {

    // The TextView where we will show results
    private WeakReference<TextView> mTextView;
    private int i;
    private int s;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDlg;
    // Constructor that provides a reference to the TextView from the MainActivity
    SimpleAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
    }

    /**
     * Runs on the background thread.
     *
     * @param voids No parameters in this use case.
     * @return Returns the string including the amount of time that
     * the background thread slept.
     */
    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(Void... voids) {

        // Generate a random number between 0 and 10.
        Random r = new Random();
        int n = r.nextInt(11);
        s= 200 * n;

        // Sleep for the random amount of time.
        try {
            int time = s/100;
            for(int i=0;i<time;i++){
                progressBar.setProgress(i*100/time);
                Thread.sleep(s/time);
            }
            progressBar.setProgress(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Make the task take long enough that we have
        // time to rotate the phone while it is running.
        // Return a String result.

        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    /**
     * Does something with the result on the UI thread; in this case
     * updates the TextView.
     */
    protected void onPostExecute(String result) {
       mTextView.get().setText(result);


    }


    public void setProgressBar(ProgressBar mProBar) {
        progressBar=mProBar;
    }
}
