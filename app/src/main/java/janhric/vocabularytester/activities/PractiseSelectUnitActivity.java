package janhric.vocabularytester.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.UnitCRUD;
import janhric.vocabularytester.listAdapters.UnitListAdapter;

public class PractiseSelectUnitActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UnitListAdapter mAdapter;
    private List<Unit> mUnitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit);

        mRecyclerView = (RecyclerView)

                findViewById(R.id.unitsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        reloadList();
        updateUI();
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new UnitListAdapter(mUnitList, this, Unit.MODE_PRACTISE);
            mRecyclerView.setAdapter(mAdapter);
        } else
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