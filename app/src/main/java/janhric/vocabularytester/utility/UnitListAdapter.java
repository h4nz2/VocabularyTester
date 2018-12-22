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
import janhric.vocabularytester.activities.UnitDetailActivity;
import janhric.vocabularytester.models.Unit;

/**
 * Created by Honza on 12/22/2018.
 */

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.ViewHolder> {
    private final List<Unit> mUnitList;
    private Context mContext;

    public UnitListAdapter(List<Unit> newList, Context context){
        mUnitList = newList;
        mContext = context;
    }

    @Override
    public UnitListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unit_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UnitListAdapter.ViewHolder holder, int position) {
        holder.populateRow(mUnitList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUnitList.size();
    }

    public void updateList(List<Unit> newlist) {
        // Set new updated list
        mUnitList.clear();
        mUnitList.addAll(newlist);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView nameView;

        public ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.unitName);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void populateRow(Unit unit){
            nameView.setText(unit.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, UnitDetailActivity.class);
            intent.putExtra(UnitDetailActivity.UNIT_TO_VIEW, mUnitList.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }


        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            final Unit unit = mUnitList.get(getAdapterPosition());
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Delete " + unit.getName() + " ?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UnitCRUD unitCRUD = new UnitCRUD(mContext);
                    unitCRUD.deleteUnit(unit);
                    updateList(unitCRUD.getAllUnits());
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
