package com.koioc.billables;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

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
    boolean anyTimerRunning = false;
    String stringMatterOne = "Matter One";
    String stringMatterTwo = "Matter Two";
    String stringMatterThree = "Matter Three";
    double testHours = 0;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Ad Code
        MobileAds.initialize(this, new OnInitializationCompleteListener(){
        @Override
            public void onInitializationComplete(InitializationStatus initializationStatus){   }
        });


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (runningOne || runningTwo || runningThree) {
            anyTimerRunning = true;
        } else {
            anyTimerRunning = false;
        }

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
        super.onSaveInstanceState(savedInstanceState);
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
                double floorHoursOne = Math.floor(hoursOne);
                double roundHoursOne = (Math.floor(hoursOne * 10)) / 10;
                int minutesOne = (secondsOne % 3600) / 60;
                int secsOne = secondsOne % 60;
                double hoursTwo = (double) secondsTwo / 3600;
                double floorHoursTwo = Math.floor(hoursTwo);
                double roundHoursTwo = (Math.floor(hoursTwo * 10)) / 10;
                int minutesTwo = (secondsTwo % 3600) / 60;
                int secsTwo = secondsTwo % 60;
                double hoursThree = (double) secondsThree / 3600;
                double floorHoursThree = Math.floor(hoursThree);
                double roundHoursThree = (Math.floor(hoursThree * 10)) / 10;
                int minutesThree = (secondsThree % 3600) / 60;
                int secsThree = secondsThree % 60;

                // run the timer
                if (runningOne) {
                    secondsOne++;

                }
                if (secondsOne > 0) {
                    decimalOneAdjust = billIncrement + roundHoursOne;
                    testHours = roundHoursOne;
                } else {
                    decimalOneAdjust = hoursOne;
                }

                if (runningTwo) {
                    secondsTwo++;
                }

                if (secondsTwo > 0) {
                    decimalTwoAdjust = billIncrement + roundHoursTwo;
                } else {
                    decimalTwoAdjust = roundHoursTwo;
                }

                if (runningThree) {
                    secondsThree++;
                }

                if (secondsThree > 0) {
                    decimalThreeAdjust = billIncrement + roundHoursThree;
                } else {
                    decimalThreeAdjust = roundHoursThree;
                }

                hoursBilledOne = decimalOneAdjust;
                hoursBilledTwo = decimalTwoAdjust;
                hoursBilledThree = decimalThreeAdjust;

                // format the seconds into hours minutes and seconds
                String timeOne = String.format(Locale.getDefault(), "%.00f:%02d:%02d", floorHoursOne, minutesOne, secsOne);
                String decimalOneNumber = String.format(Locale.getDefault(), "%.2f", decimalOneAdjust);

                String timeTwo = String.format(Locale.getDefault(), "%.00f:%02d:%02d", floorHoursTwo, minutesTwo, secsTwo);
                String decimalTwoNumber = String.format(Locale.getDefault(), "%.2f", decimalTwoAdjust);

                String timeThree = String.format(Locale.getDefault(), "%.00f:%02d:%02d", floorHoursThree, minutesThree, secsThree);
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

    //Start With Matter One

    public void startOne(View view) {
        runningOne = true;
        runningTwo = false;
        runningThree = false;
    }

    public void startTwo(View view) {
        runningTwo = true;
        runningOne = false;
        runningThree = false;

    }

    public void startThree(View view) {
        runningThree = true;
        runningOne = false;
        runningTwo = false;
    }

    public void stopOne(View view) {
        runningOne = false;

    }

    public void stopTwo(View view) {
        runningTwo = false;
    }

    public void stopThree(View view) {
        runningThree = false;
    }

    public void resetOne(View view) {
        runningOne = false;
        secondsOne = 0;
    }

    public void resetTwo(View view) {
        runningTwo = false;
        secondsTwo = 0;
    }

    public void resetThree(View view) {
        runningThree = false;
        secondsThree = 0;
    }

    // Get the text view

    public void adjustOne(View view) {

        EditText enterAdjustOne = findViewById(R.id.enterAdjustOne);
        String strAdjustOne = enterAdjustOne.getText().toString();
        int inputAdjustOne = Integer.parseInt(strAdjustOne);
        int inputAdjustOneSecs = inputAdjustOne * 60;

        if((secondsOne + inputAdjustOneSecs) < 0) {
            secondsOne = 0;
        } else {
            secondsOne = secondsOne + inputAdjustOneSecs;
        }
        enterAdjustOne.setText("");
    }

    public void adjustTwo(View view) {

        EditText enterAdjustTwo = findViewById(R.id.enterAdjustTwo);
        String strAdjustTwo = enterAdjustTwo.getText().toString();
        int inputAdjustTwo = Integer.parseInt(strAdjustTwo);
        int inputAdjustTwoSecs = inputAdjustTwo * 60;
        if((secondsTwo + inputAdjustTwoSecs) < 0){
            secondsTwo = 0;
        } else {
            secondsTwo = secondsTwo + inputAdjustTwoSecs;
        }

        enterAdjustTwo.setText("");
    }

    public void adjustThree(View view) {

        EditText enterAdjustThree = findViewById(R.id.enterAdjustTwo2);
        String strAdjustThree = enterAdjustThree.getText().toString();
        int inputAdjustThree = Integer.parseInt(strAdjustThree);
        int inputAdjustThreeSecs = inputAdjustThree * 60;
        if((secondsThree + inputAdjustThreeSecs) < 0){
            secondsThree = 0;
        } else {
            secondsThree = secondsThree + inputAdjustThreeSecs;
        }

        enterAdjustThree.setText("");

    }

    public void nameMatterOne(View view) {

        //Change the name of Matter One
        TextView getMatterOne = findViewById(R.id.textMatterOne);
        String getMatterOneText = getMatterOne.getText().toString();
        stringMatterOne = getMatterOneText;

        //Change the text in the Button
        Button buttonStartOne = (Button) findViewById(R.id.buttonStartOne);
        buttonStartOne.setText("Bill " + stringMatterOne);

    }

    public void nameMatterTwo(View view) {

        TextView getMatterTwo = findViewById(R.id.textMatterTwo);
        String getMatterTwoText = getMatterTwo.getText().toString();
        stringMatterTwo = getMatterTwoText;

        Button buttonStartTwo = (Button) findViewById(R.id.buttonStartTwo);
        buttonStartTwo.setText("Bill " + stringMatterTwo);

    }

    public void nameMatterThree(View view) {

        TextView getMatterThree = findViewById(R.id.textMatterThree);
        String getMatterThreeText = getMatterThree.getText().toString();
        stringMatterThree = getMatterThreeText;

        Button buttonStartThree = (Button) findViewById(R.id.buttonStartThree);
        buttonStartThree.setText("Bill " + stringMatterThree);
    }

    public void emailHours(View view) {
        runningOne = false;
        runningTwo = false;
        runningThree = false;
        Log.i("Send Email", "");
        String[] TO = {""};
        String[] CC = {""};
        String textHoursOne = String.format(Locale.getDefault(), "%.02f", hoursBilledOne);
        String textHoursTwo = String.format(Locale.getDefault(), "%.02f", hoursBilledTwo);
        String textHoursThree = String.format(Locale.getDefault(), "%.02f", hoursBilledThree);
        String emailBody =
                "Today I billed: \n" +
                        stringMatterOne + ": " + textHoursOne + " hours \n" +
                        stringMatterTwo + ": " + textHoursTwo + " hours \n" +
                        stringMatterThree + ": " + textHoursThree + " hours \n" +
                        "Using Koioc's Billable Me App \n" +
                        "www.koioc.com";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

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
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "there is no email client installed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        endTime = System.currentTimeMillis();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("secondsOne", secondsOne);
        editor.putBoolean("timerOneRunning", runningOne);
        editor.putInt("secondsTwo", secondsTwo);
        editor.putBoolean("timerTwoRunning", runningTwo);
        editor.putInt("secondsThree", secondsThree);
        editor.putBoolean("timerThreeRunning", runningThree);
        editor.putLong("endTime", endTime);

        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        secondsOne = prefs.getInt("secondsOne", 0);
        runningOne = prefs.getBoolean("timerOneRunning", false);
        secondsTwo = prefs.getInt("secondsTwo", 0);
        runningTwo = prefs.getBoolean("timerTwoRunning", false);
        secondsThree = prefs.getInt("secondsThree", 0);
        runningThree = prefs.getBoolean("timerThreeRunning", false);
        endTime = prefs.getLong("endTime", 0);
        long lTimeDiff = (System.currentTimeMillis() - endTime) / 1000;
        int iTimeDiff = (int)lTimeDiff;


        if (runningOne) {
            secondsOne = secondsOne + iTimeDiff;
        }
        if (runningTwo) {
            secondsTwo = secondsTwo + iTimeDiff;
        }
        if (runningThree) {
            secondsThree = secondsThree + iTimeDiff;
        }


    }
}




