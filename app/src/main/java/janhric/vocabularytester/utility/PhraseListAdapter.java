package janhric.vocabularytester.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.activities.PhraseDetailActivity;
import janhric.vocabularytester.models.Phrase;

/**
 * Created by Honza on 12/22/2018.
 */

public class PhraseListAdapter extends RecyclerView.Adapter<PhraseListAdapter.ViewHolder> {
    private final List<Phrase> mPhraseList;
    private Context mContext;

    public PhraseListAdapter(List<Phrase> newList, Context context){
        mPhraseList = newList;
        mContext = context;
    }

    @Override
    public PhraseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_phrase_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhraseListAdapter.ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView czechText;
        private TextView englishText;

        public ViewHolder(View view) {
            super(view);
            czechText = (TextView) view.findViewById(R.id.czechText);
            englishText = (TextView) view.findViewById(R.id.englishText);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void populateRow(Phrase phrase){
            czechText.setText(phrase.getCzechPhrase());
            englishText.setText(phrase.getEnglishPhrase());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, PhraseDetailActivity.class);
            intent.putExtra(PhraseDetailActivity.PHRASE_TO_VIEW, mPhraseList.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            final Phrase phrase = mPhraseList.get(getAdapterPosition());
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle("Delete \"" + phrase.getEnglishPhrase() + "\" ?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PhraseCRUD phraseCRUD = new PhraseCRUD(mContext);
                    phraseCRUD.deletePhrase(phrase);
                    updateList(phraseCRUD.getAllPhrases());
                    notifyDataSetChanged();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialogBuilder.show();
            return true;
        }
    }
}
