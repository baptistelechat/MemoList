package com.monprojet.memolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.monprojet.memolist.view.ItemTouchHelperCallback;
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

        // For best performances
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // MemoList
        List<MemoDTO> MemoList = AppDatabaseHelper.getDatabase(this).MemoDAO().getListeMemo();

        // MemoAdapter
        memoAdapter = new MemoAdapter(MemoList);
        recyclerView.setAdapter(memoAdapter);

        // Create Database
        AppDatabaseHelper.getDatabase(this);

        // Get last Memo who was clicked and give a "Toast" on output
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String out = preferences.getString("memo", "Bienvenue !");
        Log.i(TAG, "onCreate: memo :"+out);
        Toast.makeText(this, out, Toast.LENGTH_SHORT).show();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((new ItemTouchHelperCallback(memoAdapter)));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Add new Memo on click "+"
    public void clickButton(View view) {
        EditText input = findViewById(R.id.input);

        if (input.getText().toString().equals("")) {
            Toast.makeText(view.getContext(), "Champ de saisie vide", Toast.LENGTH_SHORT).show();

        } else {
            MemoDTO memoDTO = new MemoDTO(input.getText().toString());
            memoAdapter.ajouterMemo(memoDTO);
        }

    }

}
