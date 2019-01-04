package al_2018.guessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.concurrent.ThreadLocalRandom;

public class GuessingActivity extends AppCompatActivity {

    private TextView mGuessPromptTextView;
    private TextView mGuessInputText;
    private EditText mGuessInputArea;

    private Button mGuessButton;
    private int mUserGuess;
    private boolean mUserGuessed;
    private int mCorrectAnswer;

    private ThreadLocalRandom mRandomNumberGenerator;
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 100;

    private static final String TAG = "GuessingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);

        if (savedInstanceState != null) {

        } else {
            mRandomNumberGenerator = ThreadLocalRandom.current();
            mCorrectAnswer = mRandomNumberGenerator.nextInt(LOWER_BOUND, UPPER_BOUND + 1);
        }
        Log.d(TAG, "value of correct answer: " + mCorrectAnswer);


        mGuessPromptTextView = findViewById(R.id.guess_prompt);
        mGuessInputText = findViewById(R.id.guess_input_text);

        mGuessInputArea = findViewById(R.id.guess_input);

        mGuessInputArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String userGuessStr = new String(s.toString());
                    Log.d(TAG, userGuessStr);
                    mUserGuess = Integer.parseInt(userGuessStr);
                    mUserGuessed = true;
                    mGuessButton.setEnabled(mUserGuessed);
                    mGuessButton.requestFocus();

                } catch (NumberFormatException nfe) {
                    // Catches exceptions when user deletes a previous number
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mGuessButton = findViewById(R.id.guess_button);
        mGuessButton.setEnabled(mUserGuessed);
        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGuessInputArea.getText().toString().equals("")) {
                    Toast.makeText(GuessingActivity.this, R.string.guess_invalid_text, Toast.LENGTH_SHORT).show();
                } else if (mUserGuess == mCorrectAnswer) {
                    Toast.makeText(GuessingActivity.this, R.string.win_text, Toast.LENGTH_LONG).show();
                } else if (mUserGuess < mCorrectAnswer) {
                    Toast.makeText(GuessingActivity.this, R.string.guess_too_low_text, Toast.LENGTH_SHORT).show();
                } else if (mUserGuess > mCorrectAnswer) {
                    Toast.makeText(GuessingActivity.this, R.string.guess_too_high_text, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
