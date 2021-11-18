package com.example.svghangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    ImageView infoBtn;

    private static final String LIGHT = "#b17f4a";
    private static final String LSHADOW = "#936037";
    private static final String MSHADOW = "#7d4e24";
    private static final String DSHADOW = "#683c11";
    private static final String ROPEANDHUMAN = "#432918";

    TextView txtViewWordToBeGuessed; //Showing display
    private String wordDisplayedString;
    private char[] wordDisplayedCharArray;

    TextView txtViewLettersTried;
    private List<Character> lettersTried;

    private String wordToBeGuessed; //String stored
    private String guessedChar;

    EditText edtViewCharGuess;

    private Integer tries;
    private Integer triesLeft;

    TextView txtViewTriesLeft;

    private ArrayList<String> myListOfWords;

    private final String MESSAGE_WITH_LETTERS_TRIED = "No letters tried yet";
    private final String WINNING_MESSAGE = "You won!";
    private final String LOSING_MESSAGE = "You lost!";

    Button guessBtn;

    VectorMasterView hangmanVector;
    PathModel step1LegL1;
    PathModel step1LegS1;
    PathModel step1LegL2;
    PathModel step1LegS2;
    PathModel step1LegL3;
    PathModel step1LegS3;
    PathModel step1LegL4;
    PathModel step1LegS4;
    PathModel step2BeamL;
    PathModel step2BeamS;
    PathModel step3HoldL;
    PathModel step3HoldS;
    PathModel step4TopBeamL;
    PathModel step4TopBeamS;
    PathModel step4TopBeamC;
    PathModel step5HeadRope;
    PathModel step6Body;
    PathModel step7RightArm;
    PathModel step8LeftArm;
    PathModel step9RightLeg;
    PathModel step10LeftLeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Toolbar(Actionbar)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Using toolbar as ActionBar
        setSupportActionBar(toolbar);
        //Display application icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_small_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Hide title in the toolbar
        getSupportActionBar().setTitle("");
        //Customize backArrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        backArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        //InfoBtn in actionbar
        infoBtn = (ImageView) findViewById(R.id.info);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInformation();
            }
        });
        //Refresh game with new word in actionbar
        infoBtn = (ImageView) findViewById(R.id.newWordRefresh);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGameWithNewWord();
            }
        });
        //End Toolbar(Actionbar)


        //Hangman vector with accessible paths
        hangmanVector = (VectorMasterView) findViewById(R.id.hangman_Vector);
        step1LegL1 = hangmanVector.getPathModelByName("s1_bot_l_leg1");
        step1LegS1 = hangmanVector.getPathModelByName("s1_bot_s_leg1");
        step1LegL2 = hangmanVector.getPathModelByName("s1_bot_l_leg2");
        step1LegS2 = hangmanVector.getPathModelByName("s1_bot_s_leg2");
        step1LegL3 = hangmanVector.getPathModelByName("s1_bot_l_leg3");
        step1LegS3 = hangmanVector.getPathModelByName("s1_bot_s_leg3");
        step1LegL4 = hangmanVector.getPathModelByName("s1_bot_l_leg4");
        step1LegS4 = hangmanVector.getPathModelByName("s1_bot_s_leg4");
        step2BeamL = hangmanVector.getPathModelByName("s2_mid_l_beam");
        step2BeamS = hangmanVector.getPathModelByName("s2_mid_s_beam");
        step3HoldL = hangmanVector.getPathModelByName("s3_mid_l_hold");
        step3HoldS = hangmanVector.getPathModelByName("s3_mid_s_hold");
        step4TopBeamL = hangmanVector.getPathModelByName("s4_top_l_topbeam");
        step4TopBeamS = hangmanVector.getPathModelByName("s4_top_s_topbeam");
        step4TopBeamC = hangmanVector.getPathModelByName("s4_top_s_topbeam_corner");
        step5HeadRope = hangmanVector.getPathModelByName("s5_top_head_rope");
        step6Body = hangmanVector.getPathModelByName("s6_body");
        step7RightArm = hangmanVector.getPathModelByName("s7_right_arm");
        step8LeftArm = hangmanVector.getPathModelByName("s8_left_arm");
        step9RightLeg = hangmanVector.getPathModelByName("s9_right_leg");
        step10LeftLeg = hangmanVector.getPathModelByName("s10_left_leg");

        //TextViews
        txtViewWordToBeGuessed = (TextView) findViewById(R.id.txtViewWordToBeGuessed);
        txtViewLettersTried = (TextView) findViewById(R.id.txtViewLettersTried);
        txtViewTriesLeft = (TextView) findViewById(R.id.txtViewTriesLeft);

        //ButtonViews
        guessBtn = (Button) findViewById(R.id.guessBtn);
        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessWordClick();
            }
        });


        //EditTextViews
        edtViewCharGuess = findViewById(R.id.edtViewCharGuess);
        edtViewCharGuess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                    Toast.makeText(GameActivity.this, "You can only enter one letter at a time", Toast.LENGTH_SHORT).show();
                    guessBtn.setEnabled(false);
                } else if (charSequence.length() == 0) {
                    //If EditText is empty
                    guessBtn.setEnabled(false);
                } else {
                    //If its only one letter in the EditText
                    guessedChar = charSequence.toString();
                    guessBtn.setEnabled(true);
                }


                if (guessBtn.isEnabled()) {
                    //Show guessBtn
                    guessBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                } else if (!guessBtn.isEnabled()) {
                    //Hide guessBtn
                    guessBtn.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.transparent));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });//End TextWatcher

        //Create wordlist from database_file.txt
        myListOfWords = new ArrayList<>();
        myListOfWords = Helpers.getWords(GameActivity.this, "database_file.txt");

        initializeGame();

    }//End onCreate


    public void initializeGame() {
        //If wordlist is empty, get all words again
        if (myListOfWords.size() == 0 ) {
            myListOfWords = Helpers.getWords(GameActivity.this, "database_file.txt");
        }
        //Shuffle wordlist
        Collections.shuffle(myListOfWords);
        //Get first word in shuffled list
        wordToBeGuessed = myListOfWords.get(0);
        //Remove word from list, if you choose to get a new word to not get it again
        myListOfWords.remove(0);
        //Make charArray from word
        wordDisplayedCharArray = wordToBeGuessed.toCharArray();
        //Replace chars with underscores, letters and underscores presented to the user
        Arrays.fill(wordDisplayedCharArray, '_');
        //Initialize string from word-char-array (for search purposes)
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
        //Clear guessed char-text for safety's sake
        edtViewCharGuess.setText("");
        //Initialize string for letters tried
        lettersTried = new ArrayList<>();
        //Set initial string message in lettersTriedView
        txtViewLettersTried.setText(MESSAGE_WITH_LETTERS_TRIED);
        //Set amount of tries
        tries = 10;
        //Set initial value to triesLeft
        triesLeft = tries;
        //Show tries left
        txtViewTriesLeft.setText(triesLeft + "/" + tries);

        displayWordOnScreen();

    }


    private void displayWordOnScreen() {
        StringBuilder formattedString = new StringBuilder();
        //Create space between chars/letters when displayed on screen
        for (char character : wordDisplayedCharArray) {
            formattedString.append(character).append(" ");
        }
        txtViewWordToBeGuessed.setText(formattedString.toString());
    }


    private void guessWordClick() {
        if (String.valueOf(lettersTried).contains(guessedChar)) {
            edtViewCharGuess.setText("");
            Toast.makeText(GameActivity.this, "You have already used the letter " + guessedChar, Toast.LENGTH_SHORT).show();
        } else {
            checkIfLetterIsInWord(guessedChar.charAt(0));
            edtViewCharGuess.setText("");
        }
    }


    private void checkIfLetterIsInWord(char letter) {
        //If the letter was found inside the word to be guessed
        if(wordToBeGuessed.indexOf(letter) >= 0) {
            //Display letters on screen
            if(wordDisplayedString.indexOf(letter) <= -1 ){
                revealLetterInWord(letter);
                displayWordOnScreen();

                //Check if the game is won
                if(!wordDisplayedString.contains("_")) {
                    txtViewTriesLeft.setText(WINNING_MESSAGE);
                    goToResult(WINNING_MESSAGE, wordToBeGuessed, triesLeft.toString());
                }
            }

        //Otherwise if the letter was not found inside the word to be guessed
            if(!lettersTried.contains(letter)) {
                lettersTried.add(letter);
                String lettersPresented = lettersTried.toString();
                txtViewLettersTried.setText((lettersPresented.substring(1, lettersPresented.length() - 1)));
            }
        } else {
            decreaseAndDisplayTriesLeft();

            //Check if the game is lost
            if(triesLeft <= 0) {
                goToResult(LOSING_MESSAGE, wordToBeGuessed, triesLeft.toString());
            }
            if(!lettersTried.contains(letter)) {
                lettersTried.add(letter);
                String lettersPresented = lettersTried.toString();
                txtViewLettersTried.setText((lettersPresented.substring(1, lettersPresented.length() - 1)));
            }
        }

        //Display tried letters
    }//End checkIfLetterIsInWord


    private void revealLetterInWord(char letter) {
        //Replace underscores with revealed letters
        int indexOfLetter = wordToBeGuessed.indexOf(letter);
        //While index is positive or 0
        while(indexOfLetter >= 0 ) {
            wordDisplayedCharArray[indexOfLetter] = wordToBeGuessed.charAt(indexOfLetter);
            //Find possible more equal letters,while until indexOfLetter is -1
            indexOfLetter = wordToBeGuessed.indexOf(letter, indexOfLetter + 1);
        }
        //Update the string as well
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
    }


    private void decreaseAndDisplayTriesLeft() {
        if(triesLeft >= 0) {
            //Update hangman-svg
            drawHangman(triesLeft);
            /* Decrease tries */
            triesLeft -= 1;
            //Show tries tries left on screen
            txtViewTriesLeft.setText(triesLeft + "/" + tries);
        }
    }


    private void goToResult(String resultWinOrLose, String resultWord, String resultTries) {
        //Declare and put values into Hashmap to send multiple values in intent(putExtra)
        HashMap<String, String> results = new HashMap<>();
        results.put("winOrLose", resultWinOrLose);
        results.put("word", resultWord);
        results.put("tries", resultTries);

        Intent goToResultActivityIntent = new Intent(this, ResultActivity.class);
        goToResultActivityIntent.putExtra("results", results);
        startActivity(goToResultActivityIntent);
    }


    private void resetGameWithNewWord() {
        initializeGame();
    }

    //Actionbar-method
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Actionbar-method
    public void goToInformation (){
        Intent goToInformationIntent = new Intent(this, InformationActivity.class); //Create intent
        startActivity(goToInformationIntent); //Start next activity(LoginActivity)
    }

    //Animations on hangman svg(image)-paths
    private void drawHangman(int tries) {
        switch (tries) {
            case 10:
                animations(hangmanVector, step1LegL1, LSHADOW);
                animations(hangmanVector, step1LegS1, MSHADOW);
                animations(hangmanVector, step1LegL2, LSHADOW);
                animations(hangmanVector, step1LegS2, MSHADOW);
                animations(hangmanVector, step1LegL3, DSHADOW);
                animations(hangmanVector, step1LegS3, LIGHT);
                animations(hangmanVector, step1LegL4, MSHADOW);
                animations(hangmanVector, step1LegS4, DSHADOW);
                break;
            case 9:
                animations(hangmanVector, step2BeamL, LIGHT);
                animations(hangmanVector, step2BeamS, LSHADOW);
                break;
            case 8:
                animations(hangmanVector, step3HoldL, LIGHT);
                animations(hangmanVector, step3HoldS, MSHADOW);
                break;
            case 7:
                animations(hangmanVector, step4TopBeamL, LIGHT);
                animations(hangmanVector, step4TopBeamS, DSHADOW);
                animations(hangmanVector, step4TopBeamC, MSHADOW);
                break;
            case 6:
                animations(hangmanVector, step5HeadRope, ROPEANDHUMAN);
                break;
            case 5:
                animations(hangmanVector, step6Body, ROPEANDHUMAN);
                break;
            case 4:
                animations(hangmanVector, step7RightArm, ROPEANDHUMAN);
                break;
            case 3:
                animations(hangmanVector, step8LeftArm, ROPEANDHUMAN);
                break;
            case 2:
                animations(hangmanVector, step9RightLeg, ROPEANDHUMAN);
                break;
            case 1:
                animations(hangmanVector, step10LeftLeg, ROPEANDHUMAN);
                break;
        }
    }


    private void animations(VectorMasterView hangmanVector, PathModel area, String color) {
        //Initialise valueAnimator
        ValueAnimator valueAnimatorExpand = ValueAnimator.ofFloat(0.0f, 1.0f);

        valueAnimatorExpand.setDuration(1000);

        ValueAnimator valueAnimatorFade = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.parseColor(color));

        if (area.getName().equals("s4_top_s_topbeam_corner") || area.getName().equals("s1_bot_s_leg3")) {
            valueAnimatorFade.setStartDelay(700);
            valueAnimatorFade.setDuration(500);
        } else {
            valueAnimatorFade.setDuration(1000);
        }

        valueAnimatorExpand.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                area.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                hangmanVector.update();
            }
        });

        valueAnimatorFade.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                area.setFillColor((Integer) valueAnimator.getAnimatedValue());
                hangmanVector.update();
            }
        });

        //Prevent expand-animation on specific paths
        if (!area.getName().equals("s4_top_s_topbeam_corner")
                && !area.getName().equals("s3_mid_s_hold")
                && !area.getName().equals("s3_mid_l_hold")
                && !area.getName().equals("s1_bot_s_leg3")
                && !area.getName().equals("s5_top_head_rope")
                && !area.getName().equals("s6_body")
                && !area.getName().equals("s7_right_arm")
                && !area.getName().equals("s8_left_arm")
                && !area.getName().equals("s9_right_leg")
                && !area.getName().equals("s10_left_leg")
        ) {
            valueAnimatorExpand.start();
        }

        //All paths executes the fade-animation
        valueAnimatorFade.start();

    }

}