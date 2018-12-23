package janhric.vocabularytester.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import janhric.vocabularytester.R;

public class PractiseResultsActivity extends AppCompatActivity {

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
    }
}
