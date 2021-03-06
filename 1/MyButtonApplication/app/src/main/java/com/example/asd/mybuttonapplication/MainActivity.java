package com.example.asd.mybuttonapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Button switchToGreen;
    private Button switchToRed;
    private Button switchToBlue;
    private Button switchToImg;
    private LinearLayout screenLayout;
    private Toast informationToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init buttons
        switchToBlue = (Button) findViewById(R.id.switchBlue);
        switchToGreen = (Button) findViewById(R.id.switchGreen);
        switchToRed = (Button) findViewById(R.id.switchRed);
        switchToImg = (Button) findViewById(R.id.switchImg);
        screenLayout = (LinearLayout) findViewById(R.id.screenLayout);

        // setup listeners
        switchToBlue.setOnClickListener(this);
        switchToRed.setOnClickListener(this);
        switchToGreen.setOnClickListener(this);
        switchToImg.setOnClickListener(this);

        informationToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void onClick(View view) {
        if (switchToBlue.equals(view)) {
            screenLayout.setBackgroundColor(Color.BLUE);
            showToast("Hello blue");
        } else if (switchToRed.equals(view)) {
            screenLayout.setBackgroundColor(Color.RED);
            showToast("Hello red");
        } else if (switchToGreen.equals(view)) {
            screenLayout.setBackgroundColor(Color.GREEN);
            showToast("Hello green");
        } else if (switchToImg.equals(view)) {
            screenLayout.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
            showToast("Hello android");
        }

    }

    private void showToast(String text) {
        informationToast.cancel();
        informationToast.setText(text);
        informationToast.show();
    }
}