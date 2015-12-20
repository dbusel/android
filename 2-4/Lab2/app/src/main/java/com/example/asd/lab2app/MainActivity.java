package com.example.asd.lab2app;

import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.brightdays.lab2app.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    private EditText textField;
    //private Button tryButton;
    private int systemNumber;

    private GestureLibrary gLib;
    private GestureOverlayView gestures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setView();
    }

    private void setView()
    {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textField = (EditText) findViewById(R.id.editText);
        textField.setInputType(InputType.TYPE_NULL);
        gestures = (GestureOverlayView) findViewById(R.id.gestureView);
        gestures.addOnGesturePerformedListener(this);

        setContentView(R.layout.activity_main);
    }

    private void setModel()
    {
        systemNumber = new Random().nextInt(100);
    }

    private void showAlert(String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showAlert(String title, String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    private void showAlertWithNumbers(int number)
    {
        String title = this.getString(R.string.you_lose);
        if (systemNumber == number)
            title = this.getString(R.string.you_win);
        String text = "";
        text += this.getString(R.string.your_number) + " : " + String.valueOf(number) + "\n";
        text += this.getString(R.string.system_integer) + " : " + systemNumber;
        showAlert(title, text);
    }

    public void onClick(View view)
    {
        this.setModel();
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        if (textField.getText().toString().length() == 0)
        {
            showAlert(this.getString(R.string.enter_number));
            return;
        }
        int number = Integer.parseInt(textField.getText().toString());
        if (number < 1 || number > 100)
        {
            showAlert(this.getString(R.string.incorrect_number));
            return;
        }

        showAlertWithNumbers(number);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture)
    {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);

        if (predictions.size() > 0)
        {
            Prediction prediction = predictions.get(0);
            if (prediction.score > 1.0)
            {
                for(int i = 0; i <= 9; i++)
                {
                    if (prediction.name.equals("pms" + String.valueOf(i)))
                    {
                        if (this.getCurrentFocus() == textField)
                        {
                            EditText eText = (EditText)this.getCurrentFocus();
                            eText.setText(eText.getText() + String.valueOf(i));
                        }
                    }
                }
                if (prediction.name.equals("pmsS"))
                {
                    onClick(null);
                }
                if (prediction.name.equals("pmsD"))
                {

                    if (this.getCurrentFocus() == textField)
                    {
                        EditText eText = (EditText)this.getCurrentFocus();
                        if (eText.getText().length() > 0)
                            eText.setText(eText.getText().subSequence(0, eText.getText().length() - 1));
                    }
                }
                if (prediction.name.equals("pmsC"))
                {
                    if (this.getCurrentFocus() == textField)
                    {
                        EditText eText = (EditText)this.getCurrentFocus();
                        eText.setText("");
                    }

                }

            }

        }

    }
}
