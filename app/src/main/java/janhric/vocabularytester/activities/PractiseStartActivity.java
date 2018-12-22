package janhric.vocabularytester.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;
import janhric.vocabularytester.utility.PhraseCRUD;
import janhric.vocabularytester.utility.PractiseConfig.Direction;

public class PractiseStartActivity extends AppCompatActivity {
    public static final String UNIT_TO_PRACTISE = "unit_to_practise";
    public static final String DIRECTION = "direction";

    private Direction mDirection;
    private Unit mUnit;
    private List<Phrase> mPhrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_unit);

        mDirection = Direction.values()[getIntent().getIntExtra(DIRECTION, Direction.CZECH_TO_ENGLISH.ordinal())];
        mUnit = (Unit) getIntent().getSerializableExtra(UNIT_TO_PRACTISE);
        PhraseCRUD phraseCRUD = new PhraseCRUD(this);
        mPhrases = phraseCRUD.getUnitPhrases(mUnit);
    }
}
