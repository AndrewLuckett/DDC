package andrew.DDC.front;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import andrew.DDC.R;

public class Game extends AppCompatActivity {
    private TextView countdown;
    private TextView scorebox;

    int answer;

    Count timer = new Count(20000,25);
    private EditText textbox;
    int score = 0;
    private int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");


        Button gobutton = findViewById(R.id.button);
        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterpress();
            }
        });

        textbox = findViewById(R.id.answerbox);
        textbox.setOnEditorActionListener(new Loginkeyboardhandler());


        countdown = findViewById(R.id.countdown);
        scorebox = findViewById(R.id.score);
        scorebox.setText(getString(R.string.Sc) + score);

        newquestion();
    }

    public void newquestion(){
        int maxnum = 7;
        if (score > 1600){
            maxnum = 127;
        }else if(score > 800){
            maxnum = 63;
        }else if (score > 400) {
            maxnum = 15;
        }

        if(id == 0){
            genquestionconvert(maxnum);
        }else if(id == 1){
            genquestionadd(maxnum);
        }else{
            //if id is null or invalid
            finish();
        }

        timer.start();
    }

    public void genquestionconvert(int maxnum){
        int num = randomden(maxnum);

        int questionvalue = decimalToBinary(num);

        TextView question1 = findViewById(R.id.question);
        TextView question2 = findViewById(R.id.question2);

        int T = randomden(2);
        if (T == 1) {
            question1.setText(String.format(getString(R.string.Conv),getString(R.string.den),getString(R.string.bin)));
            answer = questionvalue;
            questionvalue = num;

        } else {
            question1.setText(String.format(getString(R.string.Conv),getString(R.string.bin),getString(R.string.den)));
            answer = num;
        }
        question2.setText(String.valueOf(questionvalue));
    }

    public void genquestionadd(int maxnum){
        int num1 = randomden(maxnum);
        int num2 = randomden(maxnum);

        answer = decimalToBinary(num1 + num2);

        num1 = decimalToBinary(num1);
        num2 = decimalToBinary(num2);

        TextView question2 = findViewById(R.id.question2);
        question2.setText("" + num1 + "\n + " + num2 + "");
    }

    class Loginkeyboardhandler implements TextView.OnEditorActionListener {

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                enterpress();
                handled = true;
            }

            return handled;
        }
    }


    public void enterpress(){
        String value = textbox.getText().toString();

        if(value.equals(String.valueOf(answer)) ){
            timer.cancel();
            timer = new Count(8000 + timer.timeTillDone(),25);
            //timer = timer + 8 seconds
            score += 100;
            newquestion();
        }else{
            score -= 10;
        }
        textbox.setText("");
        scorebox.setText(getString(R.string.Sc) + score);
    }

    public String gamemode(){
        if( id == 0){
            return "highscore_convert";
        }else if(id == 1){
            return "highscore_add";
        }else{
            finish();
            return "";
        }
    }

    public void failed(){
        String message = "The answer was "+answer+"."+"\n"+"Your score was "+score+".";

        //getting preferences
        SharedPreferences prefs = this.getSharedPreferences("scoredata", Context.MODE_PRIVATE);
        int highscore = prefs.getInt(gamemode(), 0); //0 is the default value
        if(score > highscore){
            //setting preferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(gamemode(), score);
            editor.apply();

            message = "The answer was "+answer+"."+"\n"+"Your score was "+score+"."+"\n"+"That's a new High Score!";

        }
        if (score <= 0){
            message = "The answer was "+answer+"."+"\n"+"Really? "+score+ " points? Were you even trying?";
        }


        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Game Over");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Game.this, MainActivity.class));
                        finish();
                    }
                });
        dlgAlert.setCancelable(false);

        dlgAlert.create().show();
    }

    public int decimalToBinary(int decimal){
        String binary = "";

        while (decimal != 0){

            binary = (decimal % 2) + binary;

            decimal /= 2;

        }
        return Integer.parseInt(binary);
        /*--
        }catch(Exception e){
            //I think I've fixed the error
            //the random sometimes produce a 0
            //that would throw an error as it you try to turn "" into an int
            Log.w("Error",e);
            Log.w("Error", String.valueOf(decimal));
            return -1;

        }
        --*/
    }

    public int randomden(int max){
        Random rand = new Random();

        return rand.nextInt(max)+1;
    }

    // Rather not mess with it now that its working
    public class Count extends CountDownTimer {
        long muf = -1;

        Count(long millisInFuture, long countDownInterval) {
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            muf = millisUntilFinished;
            String hms = String.format("%02d:%02d", muf / 1000, muf % 1000 / 10);
            countdown.setText(hms);

        }

        long timeTillDone(){
            return muf;
        }

        @Override
        public void onFinish(){
            String hms = String.format("%02d:%02d", 0, 0);
            countdown.setText(hms);
            failed();
        }
    }
}
