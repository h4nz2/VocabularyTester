package janhric.vocabularytester.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import janhric.vocabularytester.R;

public class PhraseDetailActivity extends AppCompatActivity {
    public static final String PHRASE_TO_VIEW = "phrase_to_view";
    public static final String MODE = "mode";
    public static final int EDIT_MODE = 1000;
    public static final int CREATE_MODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_detail);
    }
}
