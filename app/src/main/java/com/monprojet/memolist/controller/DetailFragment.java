package com.monprojet.memolist.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monprojet.memolist.R;

public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";
    TextView Name;
    TextView Description;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get MemoName from MainActivity
        String argumentName = getArguments().getString("memoName", "DEFAULTNM");
        String argumentDescription = getArguments().getString("memoDescription", "DEFAULTDS");
        Log.i(TAG, "onActivityCreated: ARGUMENT :"+argumentName+" / "+argumentDescription);

        // Inflate - Convert XML to View Object
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);

        // Set Textview of MemoName
        Name = root.findViewById(R.id.MemoName);
        Name.setText(argumentName);
        Description = root.findViewById(R.id.MemoDescription);
        Description.setText(argumentDescription);

        // Inflate the layout for this fragment
        return root;
    }
}
