package com.monprojet.memolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private List<MemoDTO> MemoList;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.MemoList);

//        //
//        if (savedInstanceState != null)
//        {
//            Log.i(TAG, "onSaveInstanceState: IF");
//        }
//        else
//        {
//            Log.i(TAG, "onSaveInstanceState: ELSE");
//        }

        // For best performances
        recyclerView.setHasFixedSize(true);

        // LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // MemoList
        MemoList = AppDatabaseHelper.getDatabase(this).MemoDAO().getListeMemo();
        Log.i(TAG, "onCreate: BDD : "+MemoList);

        // MemoAdapter
        memoAdapter = new MemoAdapter(MemoList, this);
        recyclerView.setAdapter(memoAdapter);

        // Create Database
        AppDatabaseHelper.getDatabase(this);

        // Get Database
        AppDatabaseHelper.getDatabase(this).MemoDAO().getListeMemo();

        // Get last Memo who was clicked and give a "Toast" on output
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String outName = preferences.getString("memoName", "TITRE");
        String outDescription = preferences.getString("memoDescription", "DESCRIPTION");
        Log.i(TAG, "onCreate: LastMemo :"+outName+" / "+outDescription);
        Toast.makeText(this, "Titre : "+outName, Toast.LENGTH_SHORT).show();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((new ItemTouchHelperCallback(memoAdapter)));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Add new Memo on click "+"
    public void clickButton(View view) {
        EditText name = findViewById(R.id.inputName);
        EditText description = findViewById(R.id.inputDescription);

       if (name.getText().toString().equals("") && description.getText().toString().equals("")) {
           Toast.makeText(view.getContext(), "Titre et desciption vide", Toast.LENGTH_SHORT).show();
       } else if (name.getText().toString().equals("")) {
            Toast.makeText(view.getContext(), "Titre vide", Toast.LENGTH_SHORT).show();

        } else if (description.getText().toString().equals("")) {
            Toast.makeText(view.getContext(), "Description vide", Toast.LENGTH_SHORT).show();

        } else {
            MemoDTO memoDTO = new MemoDTO(name.getText().toString(), description.getText().toString());
            memoAdapter.ajouterMemo(memoDTO);
            AppDatabaseHelper.getDatabase(this).MemoDAO().insert(memoDTO);
            Toast.makeText(this, "Memo ajouté !", Toast.LENGTH_SHORT).show();
            name.setText("");
            description.setText("");
        }

    }


    public void showHelp(View view) {
        Intent intent = new Intent(view.getContext(), HelpActivity.class);
        view.getContext().startActivity(intent);
    }

    public void deleteAll(View view) {
        int position = 0;
        int nbLoop = 0;
        while(position != memoAdapter.getItemCount()) {
            AppDatabaseHelper.getDatabase(view.getContext()).MemoDAO().delete(MemoList.get(position));
            Log.i(TAG, "deleteAll: POSITION"+MemoList.get(position));
            MemoList.remove(position);
            memoAdapter.notifyItemRemoved(position);
            nbLoop++;
            Log.i(TAG, "deleteAll: LOOP"+nbLoop);
        }

        if (nbLoop==1) {
            Toast.makeText(view.getContext(), "Mémo supprimé !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), nbLoop+" Mémo(s) supprimé(s) !", Toast.LENGTH_SHORT).show();

        }

    }
}
