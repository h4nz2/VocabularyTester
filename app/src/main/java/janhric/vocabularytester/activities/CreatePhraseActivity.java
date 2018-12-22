package janhric.vocabularytester.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.PhraseCRUD;

public class CreatePhraseActivity extends AppCompatActivity {
    public static final String PARENT_UNIT = "parent_unit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phrase);

        final EditText czechEdit = (EditText) findViewById(R.id.czechText);
        final EditText englishEdit = (EditText) findViewById(R.id.englishText);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phrase phrase = new Phrase(
                        czechEdit.getText().toString(),
                        englishEdit.getText().toString(),
                        (Unit) getIntent().getSerializableExtra(PARENT_UNIT)
                );

                PhraseCRUD phraseCRUD = new PhraseCRUD(view.getContext());
                phraseCRUD.savePhrase(phrase);

                finish();
            }
        });
    }
}
