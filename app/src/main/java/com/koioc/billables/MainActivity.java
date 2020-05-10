package com.koioc.billables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    double billIncrement = .1;
    int secondsOne = 0;
    int secondsTwo = 0;
    int secondsThree = 0;
    double hoursBilledOne = 0;
    double hoursBilledTwo = 0;
    double hoursBilledThree = 0;
    double decimalOneAdjust = 0;
    double decimalTwoAdjust = 0;
    double decimalThreeAdjust = 0;
    boolean runningOne;
    boolean runningTwo;
    boolean runningThree;
    boolean wasRunningOne;
    boolean wasRunningTwo;
    boolean wasRunningThree;
    String stringMatterOne = "Matter One";
    String stringMatterTwo = "Matter Two";
    String stringMatterThree = "Matter Three";
    double testHours = 0;

   /* popup information
    LayoutInflater inflaterOne = (LayoutInflater)
            getSystemService(LAYOUT_INFLATER_SERVICE);
    View popupViewOne = inflaterOne.inflate(R.layout.adjustonelayout, null);

    //create popup window
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    boolean focusable = true; // lets taps outside the popup dismiss it
    final PopupWindow popupWindowOne = new PopupWindow(popupViewOne, width, height, focusable);
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {

            // Get the previous state of the app
            secondsOne = savedInstanceState.getInt("secondsOne");
            runningOne = savedInstanceState.getBoolean("runningOne");
            wasRunningOne = savedInstanceState.getBoolean("wasRunningOne");
            secondsTwo = savedInstanceState.getInt("secondsTwo");
            runningTwo = savedInstanceState.getBoolean("runningTwo");
            wasRunningTwo = savedInstanceState.getBoolean("wasRunningTwo");
            secondsThree = savedInstanceState.getInt("secondsTwo");
            runningThree = savedInstanceState.getBoolean("runningThree");
            wasRunningThree = savedInstanceState.getBoolean("wasRunningThree");
        }
        runTimer();
    }

    // Save the state of the timer
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("secondsOne", secondsOne);
        savedInstanceState.putBoolean("runningOne", runningOne);
        savedInstanceState.putBoolean("wasRunningOne", wasRunningOne);
        savedInstanceState.putInt("secondsTwo", secondsTwo);
        savedInstanceState.putBoolean("runningTwo", runningTwo);
        savedInstanceState.putBoolean("wasRunningTwo", wasRunningTwo);
        savedInstanceState.putInt("secondsThree", secondsThree);
        savedInstanceState.putBoolean("runningThree", runningThree);
        savedInstanceState.putBoolean("wasRunningThree", runningThree);
    }

    // if the activity is paused, stop the stopwatch
    // THIS MAY NEED TO BE ELIMIANTED IF THE STOPWATCH STOPS WHEN YOU SWITCH APPS

    /*
    @Override
    protected void onPause() {
        super.onPause();
        wasRunningOne = runningOne;
        runningOne = false;
        wasRunningTwo = runningTwo;
        runningTwo = false;
        wasRunningThree = runningThree;
        runningThree = false;
    }


     */
    //if the activity is resumed, start the stopwatch if it was running previously
    //THIS MAY NEED TO BE ELIMINATED IF THE STOPWATCH RUNS WHEN YOU SWITCH APPS
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunningOne) {
            runningOne = true;
        }
        if (wasRunningTwo) {
            runningTwo = true;
        }
        if (wasRunningThree) {
            runningThree = true;
        }
    }


    private void runTimer() {
        //Get the text view
        final TextView timeViewOne = (TextView) findViewById(R.id.textTimeOne);
        final TextView decimalOne = (TextView) findViewById(R.id.textHourDecOne);
        final TextView timeViewTwo = (TextView) findViewById(R.id.textTimeTwo);
        final TextView decimalTwo = (TextView) findViewById(R.id.textHourDecTwo);
        final TextView timeViewThree = (TextView) findViewById(R.id.textTimeThree);
        final TextView decimalThree = (TextView) findViewById(R.id.textHourDecThree);



        // creates a new handler
        final Handler handlerOne = new Handler();

        //call the post method passing in a new Runnable
        //This needs to include each one, not duplicated
        handlerOne.post(new Runnable() {
            @Override
            public void run() {
                double hoursOne = (double) secondsOne / 3600;
                int minutesOne = (secondsOne % 3600) / 60;
                int secsOne = secondsOne % 60;
                double hoursTwo = (double) secondsTwo / 3600;
                int minutesTwo = (secondsTwo % 3600) / 60;
                int secsTwo = secondsTwo % 60;
                double hoursThree = (double) secondsThree / 3600;
                int minutesThree = (secondsThree %3600) / 60;
                int secsThree = secondsThree % 60;

                // run the timer
                if (runningOne) {
                    secondsOne++;

                }
                if(secondsOne > 0){
                    decimalOneAdjust = billIncrement + hoursOne;
                    testHours = hoursOne;
                } else {
                    decimalOneAdjust = hoursOne;
                }

                if(runningTwo){
                    secondsTwo++;
                }

                if(secondsTwo > 0){
                    decimalTwoAdjust = billIncrement + hoursTwo;
                } else {
                    decimalTwoAdjust = hoursTwo;
                }

                if(runningThree){
                    secondsThree++;
                }

                if (secondsThree > 0) {
                    decimalThreeAdjust = billIncrement + hoursThree;
                } else {
                    decimalThreeAdjust = hoursThree;
                }

                hoursBilledOne = decimalOneAdjust;
                hoursBilledTwo = decimalTwoAdjust;
                hoursBilledThree = decimalThreeAdjust;

                // format the seconds into hours minutes and seconds
                String timeOne = String.format(Locale.getDefault(), "%.00f:%02d:%02d", hoursOne, minutesOne, secsOne);
                String decimalOneNumber = String.format(Locale.getDefault(), "%.2f", decimalOneAdjust);

                String timeTwo = String.format(Locale.getDefault(), "%.00f:%02d:%02d", hoursTwo, minutesTwo, secsTwo);
                String decimalTwoNumber = String.format(Locale.getDefault(), "%.2f", decimalTwoAdjust);

                String timeThree = String.format(Locale.getDefault(), "%.00f:%02d:%02d", hoursThree, minutesThree, secsThree);
                String decimalThreeNumber = String.format(Locale.getDefault(), "%.2f", decimalThreeAdjust);

                // Set the text view text
                timeViewOne.setText(timeOne);
                decimalOne.setText(decimalOneNumber);
                timeViewTwo.setText(timeTwo);
                decimalTwo.setText(decimalTwoNumber);
                timeViewThree.setText(timeThree);
                decimalThree.setText(decimalThreeNumber);

                // post the code again every second
                handlerOne.postDelayed(this, 1000);
            }
        });

    }

    //Expand Adjustment Window

    //Start With Matter One

    public void startOne(View view) {
        runningOne = true;
        runningTwo = false;
        runningThree = false;
    }

    public void startTwo(View view){
        runningTwo = true;
        runningOne = false;
        runningThree = false;

    }

    public void startThree(View view){
        runningThree = true;
        runningOne = false;
        runningTwo = false;
    }

    public void stopOne(View view) {
        runningOne = false;


        // create toast for testing
        /*
        Context context = getApplicationContext();
        String text = String.valueOf(testHours);
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
*/
    }

    public void stopTwo(View view){
        runningTwo = false;
    }

    public void stopThree (View view){
        runningThree = false;
    }

    public void resetOne(View view) {
        runningOne = false;
        secondsOne = 0;
    }

    public void resetTwo(View view){
        runningTwo = false;
        secondsTwo = 0;
    }

    public void resetThree(View view){
        runningThree = false;
        secondsThree = 0;
    }

    // Get the text view

    public void adjustOne(View view){

        // int inputAdjustOne = 0;

        EditText enterAdjustOne = findViewById(R.id.enterAdjustOne);
        String strAdjustOne = enterAdjustOne.getText().toString();
        int inputAdjustOne=Integer.parseInt(strAdjustOne);
        int inputAdjustOneSecs = inputAdjustOne * 60;

        secondsOne = secondsOne + inputAdjustOneSecs;

        enterAdjustOne.setText("");
    }

    public void adjustTwo(View view){

        EditText enterAdjustTwo = findViewById(R.id.enterAdjustTwo);
        String strAdjustTwo = enterAdjustTwo.getText().toString();
        int inputAdjustTwo = Integer.parseInt(strAdjustTwo);
        int inputAdjustTwoSecs = inputAdjustTwo * 60;

        secondsTwo = secondsTwo + inputAdjustTwoSecs;

        enterAdjustTwo.setText("");
    }

    public void adjustThree(View view){

        EditText enterAdjustThree = findViewById(R.id.enterAdjustTwo2);
        String strAdjustThree = enterAdjustThree.getText().toString();
        int inputAdjustThree = Integer.parseInt(strAdjustThree);
        int inputAdjustThreeSecs = inputAdjustThree * 60;

        secondsThree = secondsThree + inputAdjustThreeSecs;

        enterAdjustThree.setText("");

    }

    public void nameMatterOne(View view){

        //Change the name of Matter One
        TextView getMatterOne = findViewById(R.id.textMatterOne);
        String getMatterOneText = getMatterOne.getText().toString();
        stringMatterOne = getMatterOneText;

        //Change the text in the Button
        Button buttonStartOne = (Button)findViewById(R.id.buttonStartOne);
        buttonStartOne.setText("Bill " + stringMatterOne);

    }

    public void nameMatterTwo(View view){

        TextView getMatterTwo = findViewById(R.id.textMatterTwo);
        String getMatterTwoText = getMatterTwo.getText().toString();
        stringMatterTwo = getMatterTwoText;

        Button buttonStartTwo = (Button)findViewById(R.id.buttonStartTwo);
        buttonStartTwo.setText("Bill " + stringMatterTwo);

    }

    public void nameMatterThree(View view){

        TextView getMatterThree = findViewById(R.id.textMatterThree);
        String getMatterThreeText = getMatterThree.getText().toString();
        stringMatterThree = getMatterThreeText;

        Button buttonStartThree = (Button)findViewById(R.id.buttonStartThree);
        buttonStartThree.setText("Bill " + stringMatterThree);
    }

    public void emailHours(View view){
        Log.i("Send Email", "");
        String[] TO = {""};
        String[] CC = {""};
        String textHoursOne = Double.toString(hoursBilledOne);
        String textHoursTwo = Double.toString(hoursBilledTwo);
        String textHoursThree = Double.toString(hoursBilledThree);
        String emailBody =
                "My billables for today were: \n" +
                stringMatterOne + ": " + textHoursOne + " hours \n" +
                stringMatterTwo + ": " + textHoursTwo + " hours \n" +
                stringMatterThree + ": " + textHoursThree +" hours \n"
                ;
        Intent emailIntent = new Intent (Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Billables for Today");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail to:"));
            finish();
            Log.i("Finished sending email", "");
            } catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(MainActivity.this, "there is no email client installed", Toast.LENGTH_LONG).show();
        }

    }



    }




