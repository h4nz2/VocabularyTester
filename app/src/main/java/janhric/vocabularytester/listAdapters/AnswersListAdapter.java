package janhric.vocabularytester.listAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Phrase;

/**
 * Created by Honza on 12/24/2018.
 */

public class AnswersListAdapter extends RecyclerView.Adapter<AnswersListAdapter.ViewHolder> {
    private final List<Phrase> mPhraseList;
    private Context mContext;

    public AnswersListAdapter(List<Phrase> newList, Context context){
        mPhraseList = newList;
        mContext = context;
    }

    @Override
    public AnswersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_phrase_layout, parent, false);
        return new AnswersListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnswersListAdapter.ViewHolder holder, int position) {
        holder.populateRow(mPhraseList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhraseList.size();
    }

    public void updateList(List<Phrase> newlist) {
        // Set new updated list
        mPhraseList.clear();
        mPhraseList.addAll(newlist);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView czechText;
        private TextView englishText;

        public ViewHolder(View view) {
            super(view);
            czechText = (TextView) view.findViewById(R.id.czechText);
            englishText = (TextView) view.findViewById(R.id.englishText);
        }

        public void populateRow(Phrase phrase){
            czechText.setText(phrase.getCzechPhrase());
            englishText.setText(phrase.getEnglishPhrase());
        }

    }
}
