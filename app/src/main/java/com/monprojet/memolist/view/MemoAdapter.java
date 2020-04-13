package com.monprojet.memolist.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.monprojet.memolist.R;
import com.monprojet.memolist.controller.DetailActivity;
import com.monprojet.memolist.controller.DetailFragment;
import com.monprojet.memolist.model.MemoDTO;
import com.monprojet.memolist.model.json.Form;

import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private static final String TAG = "MemoAdapter";

    private List<MemoDTO> MemoList;
    public String result;

    public MemoAdapter(List<MemoDTO> MemoList) {
        this.MemoList = MemoList;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewMemo = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo_list, parent, false);
        return new MemoViewHolder(viewMemo);
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position)
    {
        holder.tvLabelMemo.setText(MemoList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return MemoList.size();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder
    {
        // TextView
        public TextView tvLabelMemo;

        // Constructeur :
        public MemoViewHolder(View itemView)
        {
            super(itemView);
            final int orientation = itemView.getResources().getConfiguration().orientation;
            tvLabelMemo = itemView.findViewById(R.id.LabelMemo);
            tvLabelMemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final MemoDTO memo = MemoList.get(getAdapterPosition());
                    Log.i(TAG, "onClick: Position : " + getAdapterPosition());
                    Toast.makeText(view.getContext(), "Titre : "+memo.name, Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("memoName", memo.name);
                    editor.putString("memoDescription", memo.description);
                    editor.apply();

                    // Load Fragment if ORIENTATION_PORTRAIT else ORIENTATION_LANDSCAPE
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("memoName", memo.name);
                        intent.putExtra("memoDescription", memo.description);
                        view.getContext().startActivity(intent);
                    }

//                    ----------------
//                     CRASH ON RUN
//                    ----------------
//                    } else {
//                        DetailFragment fragment = new DetailFragment();
//                        // fragment manager :
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        // transaction :
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragmentLandscape, fragment, "exemple1");
//                        fragmentTransaction.commit();
//                    }

                    // client HTTP :
                    AsyncHttpClient client = new AsyncHttpClient();
                    // Params :
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("memo", memo.name);
                    // CRUD - Post :
                    client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response)
                        {
                            // WS return :
                            String retour = new String(response);
                            Log.i(TAG, retour);

                            // Convert Json to Java :
                            Gson gson = new Gson();
                            Form frm = gson.fromJson(retour, Form.class);

                            result = frm.form.memo;
                            Log.i(TAG, result);
                        }


                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
                        {
                            Log.e(TAG, e.toString());
                        }
                    });
                }
            });
        }
    }

    public void ajouterMemo(MemoDTO memoDTO) {
        MemoList.add(memoDTO);
        notifyItemInserted(MemoList.size());
    }


//    ----------------
//     Move elements + delete elements
//    ----------------
    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(MemoList, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }
    // Appelé une fois à la suppression.
    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            MemoList.remove(position);
            notifyItemRemoved(position);
        }
    }

}
