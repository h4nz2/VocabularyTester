package janhric.vocabularytester.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import janhric.vocabularytester.R;
import janhric.vocabularytester.utility.PractiseConfig.Direction;

public class PractiseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_details);


        final RadioGroup directionRadioGroup = (RadioGroup) findViewById(R.id.directionRadioGroup);
        Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Direction direction;
                Intent intent = new Intent(view.getContext(), PractiseStartActivity.class);
                intent.putExtra(PractiseStartActivity.UNIT_TO_PRACTISE, getIntent().getSerializableExtra(PractiseStartActivity.UNIT_TO_PRACTISE));
                switch(directionRadioGroup.getCheckedRadioButtonId()){
                    case R.id.czech_to_english:
                        direction = Direction.CZECH_TO_ENGLISH;
                        break;
                    case R.id.english_to_czech:
                        direction = Direction.ENGLISH_TO_CZECH;
                        break;
                    default:
                        direction = Direction.CZECH_TO_ENGLISH;
                }
                intent.putExtra(PractiseStartActivity.DIRECTION, direction.ordinal());
                startActivity(intent);
            }
        });
    }
}
