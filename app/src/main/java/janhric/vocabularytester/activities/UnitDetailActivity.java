package janhric.vocabularytester.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.PhraseCRUD;
import janhric.vocabularytester.listAdapters.PhraseListAdapter;
import janhric.vocabularytester.utility.UnitCRUD;
import janhric.vocabularytester.utility.UnitImporter;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_unit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameText = (TextView) findViewById(R.id.nameEdit);
        nameText.setText(mUnit.getName());

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unit_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.importFromFile:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/*");
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new PhraseListAdapter(mPhraseList, this);
            mRecyclerView.setAdapter(mAdapter);
        } else
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            importPhrases(data.getData());
        }
    }

    private void importPhrases(Uri uri) {
        UnitImporter importer = new UnitImporter();
        try {
            int rowsImported = importer.importPhrases(uri, getApplicationContext(), mUnit);
            reloadList();
            updateUI();
        } catch (IOException e) {
            //shit happens
        }
    }
}
