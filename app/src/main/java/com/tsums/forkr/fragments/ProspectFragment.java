package com.tsums.forkr.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.squareup.picasso.Picasso;
import com.tsums.forkr.R;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by trevor on 1/16/16.
 */
public class ProspectFragment extends Fragment {

    @Bind(R.id.prospect_card_container) CardContainer cc;

    @Inject Picasso picasso;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_prospect, container, false);

        ButterKnife.bind(this, parentView);

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground (Void... params) {
                try {
                    return picasso.load("http://ba6.us/content/BobbyTablesNametag.png").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (Bitmap b) {

                CardModel card = new CardModel("Bobby Tables", "I drop it like it's hot.", b);
                SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getContext());
                adapter.add(card);
                cc.setAdapter(adapter);
            }
        }.execute();



        return parentView;
    }
}
