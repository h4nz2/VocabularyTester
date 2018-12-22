package janhric.vocabularytester.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.UnitCRUD;

public class CreateUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        final EditText nameEdit = (EditText) findViewById(R.id.nameEdit);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unit unit = new Unit();
                unit.setName(nameEdit.getText().toString());

                UnitCRUD unitCRUD = new UnitCRUD(view.getContext());
                unitCRUD.saveUnit(unit);

                finish();
            }
        });
    }
}
