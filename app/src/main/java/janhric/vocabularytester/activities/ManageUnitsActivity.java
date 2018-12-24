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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.UnitCRUD;
import janhric.vocabularytester.utility.UnitImporter;
import janhric.vocabularytester.listAdapters.UnitListAdapter;

public class ManageUnitsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UnitListAdapter mAdapter;
    private List<Unit> mUnitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_units);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateUnitActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.unitsList);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            importUnits(data.getData());
        }
    }

    private void importUnits(Uri uri) {
        UnitImporter importer = new UnitImporter();
        try {
            int rowsImported = importer.importAllUnits(uri, getApplicationContext());
            reloadList();
            updateUI();
        } catch (IOException e) {
            //shit happens
        }
    }

    private void updateUI() {
        if(mAdapter == null){
            mAdapter = new UnitListAdapter(mUnitList, this, Unit.MODE_MANAGE);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            mAdapter.notifyDataSetChanged();
    }

    private void reloadList() {
        if (mUnitList == null) {
            mUnitList = new ArrayList<Unit>();
        }
        UnitCRUD unitCRUD = new UnitCRUD(this);
        mUnitList.clear();
        mUnitList.addAll(unitCRUD.getAllUnits());
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
        updateUI();
    }
}
