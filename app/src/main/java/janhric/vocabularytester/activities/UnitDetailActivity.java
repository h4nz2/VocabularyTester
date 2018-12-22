package janhric.vocabularytester.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.PhraseCRUD;
import janhric.vocabularytester.utility.PhraseListAdapter;
import janhric.vocabularytester.utility.UnitCRUD;
import janhric.vocabularytester.utility.UnitListAdapter;

public class UnitDetailActivity extends AppCompatActivity {
    public static final String UNIT_TO_VIEW = "unit_to_view";

    private Unit mUnit;
    private TextView nameText;
    private RecyclerView mRecyclerView;
    private PhraseListAdapter mAdapter;
    private List<Phrase> mPhraseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_detail);

        mUnit = (Unit) getIntent().getSerializableExtra(UNIT_TO_VIEW);

        nameText = (TextView) findViewById(R.id.nameEdit);
        nameText.setText(mUnit.getName());

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (nameText.getText().length() > 0) {
                    mUnit.setName(nameText.getText().toString());

                    UnitCRUD unitCRUD = new UnitCRUD(nameText.getContext());
                    unitCRUD.saveUnit(mUnit);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreatePhraseActivity.class);
                intent.putExtra(CreatePhraseActivity.PARENT_UNIT, mUnit);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.phrasesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        reloadList();
        updateUI();
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new PhraseListAdapter(mPhraseList, this);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            mAdapter.notifyDataSetChanged();
    }

    private void reloadList() {
        if (mPhraseList == null) {
            mPhraseList = new ArrayList<Phrase>();
        }
        PhraseCRUD phraseCRUD = new PhraseCRUD(this);
        mPhraseList.clear();
        mPhraseList.addAll(phraseCRUD.getUnitPhrases(mUnit));
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
        updateUI();
    }
}
