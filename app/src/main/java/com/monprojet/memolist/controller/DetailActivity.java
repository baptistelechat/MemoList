package com.monprojet.memolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.monprojet.memolist.R;
import com.monprojet.memolist.controller.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // fragment :
        String MemoName = getIntent().getStringExtra("memo");
        Log.i(TAG, "onCreate: ITEM = "+MemoName);

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("memo", MemoName);
        fragment.setArguments(bundle);

        // fragment manager :
        FragmentManager fragmentManager = getSupportFragmentManager();
        // transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment, "exemple1");
        fragmentTransaction.commit();
    }
}
