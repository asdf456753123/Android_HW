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

package com.example.android.whowroteitloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The WhoWroteIt app queries the Book Search API for books based
 * on a user's search.  It uses an AsyncTask to run the search task in
 * the background.
 */
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    /**
     * onClick handler for the "Search Books" button.
     *
     * @param view The view (Button) that was clicked.
     */
    public void searchBooks(View view) {
        // Get the search string from the input field.
        String QSring = "";
        if (mSpinner.getSelectedItemPosition() == 0) {
            QSring = QSring+"http://";
        } else if (mSpinner.getSelectedItemPosition() == 1) {
            QSring = QSring+"https://";
        }
        QSring = QSring+mBookInput.getText().toString();
        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a BookLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()
                && QSring.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", QSring);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            mTitleText.setText(R.string.loading);
        }
        // Otherwise update the TextView to tell the user there is no
        // connection, or no search term.
        else {
            if (QSring.length() == 0) {
                mTitleText.setText(R.string.no_search_term);
            } else {
                mTitleText.setText(R.string.no_network);
            }
        }


    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            mTitleText.setText(data);
        } catch (Exception e) {
            mTitleText.setText(R.string.no_network);
            e.printStackTrace();
        }

    }
//        try {
//            // Convert the response into a JSON object.
//            JSONObject jsonObject = new JSONObject(data);
//            // Get the JSONArray of book items.
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            // Initialize iterator and results fields.
//            int i = 0;
//            String title = null;
//            String authors = null;
//
//            // Look for results in the items array, exiting when both the
//            // title and author are found or when all items have been checked.
//            while (i < itemsArray.length() &&
//                    (authors == null && title == null)) {
//                // Get the current item information.
//                JSONObject book = itemsArray.getJSONObject(i);
//                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
//
//                // Try to get the author and title from the current item,
//                // catch if either field is empty and move on.
//                try {
//                    title = volumeInfo.getString("title");
//                    authors = volumeInfo.getString("authors");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                // Move to the next item.
//                i++;
//            }
//
//            // If both are found, display the result.
//            if (title != null && authors != null) {
//                mTitleText.setText(title);
//                mAuthorText.setText(authors);
//                //mBookInput.setText("");
//            } else {
//                // If none are found, update the UI to show failed results.
//                mTitleText.setText(R.string.no_results);
//                mAuthorText.setText("");
//            }
//
//        } catch (Exception e) {
//            // If onPostExecute does not receive a proper JSON string,
//            // update the UI to show failed results.
//            mTitleText.setText(R.string.no_results);
//            mAuthorText.setText("");
//            e.printStackTrace();
//        }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Do nothing.  Required by interface.
    }
}