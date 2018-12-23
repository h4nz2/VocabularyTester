package janhric.vocabularytester.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.UnitCRUD;
import janhric.vocabularytester.utility.UnitListAdapter;

import static java.security.AccessController.getContext;

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
