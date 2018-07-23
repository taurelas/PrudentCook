package com.leadinsource.prudentcook.net;

import android.net.Uri;
import android.os.AsyncTask;

import com.leadinsource.prudentcook.model.Ingredient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import timber.log.Timber;

public class EdamamWebService implements WebService {

    final static String API_BASE_URL = "https://api.edamam.com/search";
    final static String PARAM_QUERY = "q";
    final static String PARAM_APP_ID = "app_id";
    final static String PARAM_APP_KEY = "app_key";
    final static String APP_ID = "9959fa87";
    final static String APP_KEY = "044b6c7f9a7ec374c3b6105dbbdefddd";

    Callback callback;

    @Override
    public void searchAPI(List<Ingredient> ingredients, Callback callback) {
        this.callback = callback;

        StringBuilder ingredientSearchQuery = new StringBuilder();

        for(int i=0;i<ingredients.size();i++) {
            if(i>0) {
                ingredientSearchQuery.append(",");
            }
            ingredientSearchQuery.append(ingredients.get(i).getName());
        }

        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, ingredientSearchQuery.toString())
                .appendQueryParameter(PARAM_APP_ID, APP_ID)
                .appendQueryParameter(PARAM_APP_KEY, APP_KEY)
                .build();

        URL url = covertUriToURL(builtUri);

        new EdamamQueryTask().execute(new Param(url, callback ));
    }

    private URL covertUriToURL(Uri uri) {
        URL resultUrl = null;

        try {
            resultUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return resultUrl;
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static class EdamamQueryTask extends AsyncTask<Param, Void, String> {

        private Callback callback;

        @Override
        protected String doInBackground(Param... params) {
            Timber.d("Starting background action with %s and %s", params[0].url, params[0].callback);
            String result = null;
            callback = params[0].callback;

            try {
                result =  getResponseFromHttpUrl(params[0].url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            callback.onResult(result);
        }
    }

    private class Param {
        public URL url;
        public Callback callback;

        public Param(URL url, Callback callback) {
            this.url = url;
            this.callback = callback;
        }
    }
}
