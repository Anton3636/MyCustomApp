package com.example.antosh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity{


    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;
    String gender ;
    String large_image;



    private static final int MENU_ADD = Menu.FIRST;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ADD, Menu.NONE, "Go to Database")
                .setIcon(null).
                setIntent(new Intent(this,SavedContacts.class));
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapter(this, list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);


      if (isNetworkAvailable()) {
            new GetDataTask().execute();
        } else {
           Toast.makeText(this, "Internet Connection Is Not Available", Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ImageShowActivity.class);
                intent.putExtra("img",large_image);
                startActivity(intent);
            }
        });

    }






    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, String, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Wait Please...");
            dialog.setMessage("Getting Data");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_RESULT);

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for(int jIndex = 0; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                MyDataModel model = new MyDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                gender = innerObject.getString(Keys.KEY_GENDER.toString());


                                /**
                                 * Getting Object from Object "phone"
                                 */
                                JSONObject nameObject = innerObject.getJSONObject(Keys.KEY_NAME);
                                String first_name = nameObject.getString(Keys.KEY_FIRST_NAME);
                                String last_name = nameObject.getString(Keys.KEY_LAST_NAME);

                                model.setName(first_name);
                                model.setSurname(last_name);


                                JSONObject locationObject = innerObject.getJSONObject(Keys.KEY_LOCATION);
                                String city = locationObject.getString(Keys.KEY_CITY);
                                String street = locationObject.getString(Keys.KEY_STREET);


                                model.setCity(city);
                                model.setStreet(street);

                                publishProgress(gender);

                                JSONObject pictureObject = innerObject.getJSONObject(Keys.KEY_IMAGE);
                                large_image = pictureObject.getString(Keys.KEY_LARGE_IMAGE);
                                String medium_image = pictureObject.getString(Keys.KEY_MID_IMAGE);
                                String smallImage = pictureObject.getString(Keys.KEY_SMALL_IMAGE);

                                model.setImage(smallImage);



                                /**
                                 * Adding image,city,street,name and surname to List...
                                 */
                                list.add(model);
                            }
                        }
                    }
                }

            } catch (JSONException je) {
                Toast.makeText(MainActivity.this, "Error to load data ,please check connection", Toast.LENGTH_SHORT).show();
                Log.i(JSONParser.Tag, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            switch (values[0])
            {
                case "male":  listView.setBackgroundColor(Color.GRAY);
                    break;
                case "female":  listView.setBackgroundColor(Color.MAGENTA);
                    break;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {

            }
        }
    }

    boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
