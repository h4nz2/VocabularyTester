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

public class PhraseDetailActivity extends AppCompatActivity {
    public static final String PHRASE_TO_VIEW = "phrase_to_view";

    private Phrase mPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_detail);

        mPhrase = (Phrase) getIntent().getSerializableExtra(PHRASE_TO_VIEW);

        final EditText czechEdit = (EditText) findViewById(R.id.czechText);
        czechEdit.setText(mPhrase.getCzechPhrase());
        final EditText englishEdit = (EditText) findViewById(R.id.englishText);
        englishEdit.setText(mPhrase.getEnglishPhrase());

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhrase.setCzechPhrase(czechEdit.getText().toString());
                mPhrase.setEnglishPhrase(englishEdit.getText().toString());

                PhraseCRUD phraseCRUD = new PhraseCRUD(view.getContext());
                phraseCRUD.savePhrase(mPhrase);

                finish();
            }
        });
    }
}
