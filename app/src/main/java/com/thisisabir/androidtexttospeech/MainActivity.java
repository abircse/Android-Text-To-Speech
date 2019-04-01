package com.thisisabir.androidtexttospeech;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private EditText speechBox;
    private Button convertText;
    private TextToSpeech speech;
    private Spinner spinner;
    private ArrayAdapter<String> CountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechBox = findViewById(R.id.speechTextBox);
        convertText = findViewById(R.id.firedbutton);
        spinner = findViewById(R.id.spineer);
        speech = new TextToSpeech(this, this);

        String[] country = getResources().getStringArray(R.array.country);
        CountryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinerdesign, country);
        CountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(CountryAdapter);

        convertText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                speakOut();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        int result = speech.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                        } else {
                            convertText.setEnabled(true);
                            speakOut();
                        }
                    }
                    if (position == 1) {
                        int result = speech.setLanguage(Locale.CHINA);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                        } else {
                            convertText.setEnabled(true);
                            speakOut();
                        }
                    }

                    if (position == 2) {
                        int result = speech.setLanguage(new Locale("bn_IN"));
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                        } else {
                            convertText.setEnabled(true);
                            speakOut();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Error in Text to Speech", Toast.LENGTH_SHORT).show();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut() {

        String data = speechBox.getText().toString();
        if (data.matches("")) {
            Toast.makeText(getApplicationContext(), "Please Enter A Text First", Toast.LENGTH_SHORT).show();
        } else {
            CharSequence text = speechBox.getText();
            speech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "null");

        }

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (speech != null) {
            speech.stop();
            speech.shutdown();
        }
        super.onDestroy();
    }
}
