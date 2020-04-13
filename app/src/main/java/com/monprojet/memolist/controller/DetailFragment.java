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
    TextView txtFragment;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get MemoName from MainActivity
        String argument = getArguments().getString("memo", "DEFAULT");
        Log.i(TAG, "onActivityCreated: ARGUMENT :"+argument);

        // Inflate - Convert XML to View Object
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);

        // Set Textview by MemoName
        txtFragment = root.findViewById(R.id.txtFragment);
        txtFragment.setText(argument);

        // Inflate the layout for this fragment
        return root;
    }
}
