package janhric.vocabularytester.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
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
    private List<Phrase> mCorrectAnswers;
    private List<Phrase> mWrongAnswers;
    private Iterator<Phrase> mCurrentList;
    private Phrase mCurrentPhrase;
    private boolean mAnswerChecked;

    private TextView fromLabel;
    private TextView toLabel;
    private TextView fromContentText;
    private TextView correctAnswerText;
    private EditText answerEdit;
    private Button checkAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_start);

        // get configuration
        mDirection = (Direction) getIntent().getSerializableExtra(DIRECTION);
        mUnit = (Unit) getIntent().getSerializableExtra(UNIT_TO_PRACTISE);
        PhraseCRUD phraseCRUD = new PhraseCRUD(this);
        mPhrases = phraseCRUD.getUnitPhrases(mUnit);

        // initialize views
        fromLabel = (TextView) findViewById(R.id.fromLabelText);
        toLabel = (TextView) findViewById(R.id.toLabelText);
        fromContentText = (TextView) findViewById(R.id.fromContentText);
        correctAnswerText = (TextView) findViewById(R.id.correctAnswerText);
        answerEdit = (EditText) findViewById(R.id.answerEdit);
        checkAnswerButton = (Button) findViewById(R.id.checkButton);

        // set labels to match direction
        if (mDirection.equals(Direction.CZECH_TO_ENGLISH)) {
            fromLabel.setText(R.string.czech);
            toLabel.setText(R.string.english);
        } else {
            fromLabel.setText(R.string.english);
            toLabel.setText(R.string.czech);
        }

        startPractice();
    }

    private void startPractice() {
        // reset answers
        mCorrectAnswers = new ArrayList<Phrase>();
        mWrongAnswers = new ArrayList<Phrase>();

        // reset list of phrases
        mCurrentList = mPhrases.iterator();

        showNextPhrase();

        // activate button
        checkAnswerButton.setOnClickListener(new CheckAnswerListener());
    }

    private void showNextPhrase() {
        mAnswerChecked = false;
        if (mCurrentList.hasNext()) {
            // get next phrase
            mCurrentPhrase = mCurrentList.next();

            // set correct texts to views
            fromContentText.setText(mCurrentPhrase.getFromPhrase(mDirection));
            answerEdit.setText("");
            correctAnswerText.setText("");
            checkAnswerButton.setText(R.string.checkAnswer);
        } else {
            finishPractice();
        }
    }

    private void finishPractice() {
        Intent intent = new Intent(this.getApplicationContext(), PractiseResultsActivity.class);
        intent.putExtra(PractiseStartActivity.UNIT_TO_PRACTISE, mUnit);
        intent.putExtra(PractiseStartActivity.DIRECTION, mDirection);
        startActivity(intent);
        // TODO send results
    }

    private class CheckAnswerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mAnswerChecked) {
                showNextPhrase();
            } else {
                // check answer
                String answer = answerEdit.getText().toString();
                String correctAnswer = mCurrentPhrase.getToPhrase(mDirection);
                // TODO evaluate answer
                correctAnswerText.setText(getString(R.string.correctAnswer, correctAnswer));

                // enable next phrase
                checkAnswerButton.setText(R.string.next);
                mAnswerChecked = true;
            }
        }
    }

}
