package janhric.vocabularytester.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.R;
import janhric.vocabularytester.listAdapters.AnswersListAdapter;
import janhric.vocabularytester.models.Phrase;

public class PractiseResultsActivity extends AppCompatActivity {
    public static final String WRONG_ANSWERS = "wrong_answers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_results);

        Button restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PractiseStartActivity.class);
                intent.putExtra(PractiseStartActivity.UNIT_TO_PRACTISE, getIntent().getSerializableExtra(PractiseStartActivity.UNIT_TO_PRACTISE));
                intent.putExtra(PractiseStartActivity.DIRECTION, getIntent().getSerializableExtra(PractiseStartActivity.DIRECTION));
                startActivity(intent);
            }
        });

        Button selectUnitButton = (Button) findViewById(R.id.selectUnitButton);
        selectUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PractiseSelectUnitActivity.class);
                startActivity(intent);
            }
        });

        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });

        List<Phrase> wrongAnswers = (ArrayList<Phrase>) getIntent().getSerializableExtra(WRONG_ANSWERS);
        for (Phrase phrase :
                wrongAnswers) {
            Log.i("wrong phrase", "\'" + phrase.getCzechPhrase() + "\'");
        }

        RecyclerView answersListView = (RecyclerView) findViewById(R.id.wrongAnswersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        answersListView.setLayoutManager(mLayoutManager);
        AnswersListAdapter answersListAdapter = new AnswersListAdapter(wrongAnswers, this);
        answersListView.setAdapter(answersListAdapter);
    }
}
