package com.example.david.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public final static String CLIENT_ID = "b92352e5063c46c2a304392b7c4420a1";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        //Send out API request to Popular Photos
        photos = new ArrayList<InstagramPhoto>();
        // 1. Create the adapter linking it to the data source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        //2. Find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //3. Set the adapter binding it to the listview
        lvPhotos.setAdapter(aPhotos);
        // fetch the popular photos
        fetchPopularPhotos();
    }

    //Trigger API Request

    public void fetchPopularPhotos(){
        /*
        client id: b92352e5063c46c2a304392b7c4420a1
        Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
- Response

         */

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        // Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
           //onSuccess (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /*Expecting a JSON object

                - URL: {"data" => [x] => "images" => "standard_resolution" =>URL
                }
               */
                //Iterate each of the photo items and decode the items into a JAva ojbect
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data"); //array of posts
                   //iterate array of posts
                    for (int i = 0;i < photosJSON.length(); i++){
                        //get JSON object at that position
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //decode the attributes of the json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        //Author name: {"data" => [x] =>  "user" => "username"}
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        //Caption: {"data" => [x] => "caption" => "text"}
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        //-Type: {"data" => [x] => "type"} ("image" or "video")
                        //photo.type = photoJSON.getJSONObject("type").getString("text");
                       // //URL: {"data" => [x] => "images" => "standard_resolution" =>URL
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.timePosted = DateUtils.getRelativeTimeSpanString(photoJSON.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
                        //how does it know that "count" results in an int?
                        photos.add(photo);


                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

                //callback : WHAT IS THIS?
                aPhotos.notifyDataSetChanged();
            }


            //onFailure (fail)

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
