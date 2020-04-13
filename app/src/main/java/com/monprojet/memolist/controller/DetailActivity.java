package com.monprojet.memolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.monprojet.memolist.R;
import com.monprojet.memolist.controller.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get data from MainActivity
        String MemoName = getIntent().getStringExtra("memoName");
        String MemoDescription = getIntent().getStringExtra("memoDescription");
        Log.i(TAG, "onCreate: ITEM = "+MemoName +" / "+MemoDescription);

        // Add DetailFragment into DetailActivity
        DetailFragment fragment = new DetailFragment();

        // Set data to DetailFragment
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        bundle.putString("memoName", MemoName);
        bundle.putString("memoDescription", MemoDescription);

        // FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment, "exemple1");
        fragmentTransaction.commit();
    }
}
