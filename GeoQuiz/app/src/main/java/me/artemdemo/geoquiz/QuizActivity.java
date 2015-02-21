package me.artemdemo.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    
    private static final String TAG = "QuizActivity";

    /*
     * Array of questions, that will be show to the user
     */
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true)
    };

    /*
     * Index of the current shown question
     */
    private int mCurrentIndex = 0;
    
    /*
     * Enum variable to describe which question should be shown
     */
    private enum mQuestonDirection { NEXT, PREV }

    /*
     * Update question inside of the view
     */
    private void updateQuestion() {
        // Pay attention that I'm getting question ID (int)
        // It will work case setText() can get resource string id (int) and fetch it from strings.xml
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }
    
    /*
     * Check if given answer is correct for current index
     */
    private void checkAnswer( boolean userPressedTrue ) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;
        
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    
    /*
     * Show next question
     */
    private void changeQuestion( mQuestonDirection mQstDir ) {
        switch (mQstDir) {
            case NEXT:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                break;
            case PREV:
                if ( mCurrentIndex == 0 ) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                break;
        }
        updateQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mPrevButton = (Button)findViewById(R.id.prev_button);

        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeQuestion( mQuestonDirection.NEXT );
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeQuestion( mQuestonDirection.NEXT );
            }
        });
        
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeQuestion( mQuestonDirection.PREV );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
