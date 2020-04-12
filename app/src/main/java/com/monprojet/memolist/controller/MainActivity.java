package com.monprojet.memolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monprojet.memolist.R;
import com.monprojet.memolist.model.AppDatabaseHelper;
import com.monprojet.memolist.model.MemoDTO;
import com.monprojet.memolist.view.MemoAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MemoAdapter memoAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.MemoList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<MemoDTO> MemoList = AppDatabaseHelper.getDatabase(this).MemoDAO().getListeMemo();

        memoAdapter = new MemoAdapter(MemoList);
        recyclerView.setAdapter(memoAdapter);

        AppDatabaseHelper.getDatabase(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String out = preferences.getString("memo", "Bienvenue !");
        Log.i(TAG, "onCreate: memo :"+out);
        Toast.makeText(this, out, Toast.LENGTH_SHORT).show();
    }

    public void clickButton(View view) {
        EditText input = findViewById(R.id.input);
        MemoDTO memoDTO = new MemoDTO(input.getText().toString());
        memoAdapter.ajouterMemo(memoDTO);
    }




}
