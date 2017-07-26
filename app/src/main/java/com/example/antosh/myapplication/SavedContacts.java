package com.example.antosh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SavedContacts extends AppCompatActivity {

    String name;
    String surname;
    String city;
    String street;
    String image;
    ListView lv;
    ArrayAdapter<String> adapter;
    List  values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_contacts);


    }
}
