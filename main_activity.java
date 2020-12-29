  package com.example.alarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.LANG_MISSING_DATA;

  public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView textView;
    TextToSpeech TTS;
    Button button1;
    private String text;
      String message = "hi";
      String voicedata = "hello sir";

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        button1 =(Button)findViewById(R.id.button1) ;
        TTS = new TextToSpeech(this,this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stt();
                speak();
            }
        });
    }

    public void getsppechinput(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager())!=null){
        startActivityForResult(intent,10);
    } else {
            Toast.makeText(this,"your device not supported",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if (resultCode==RESULT_OK && data!=null){

                    ArrayList<String>result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text = result.get(0);
                    textView.setText(text);


                }
        }
    }

    @Override
    public void onInit(int i) {
        if(i==TextToSpeech.SUCCESS){
            int textresult = TTS.setLanguage(Locale.getDefault()) ;
            TTS.setSpeechRate(0);
            TTS.setPitch(1);
            if (textresult == TextToSpeech.LANG_MISSING_DATA || textresult == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language not supported");
            }

            if (text == message){
                button1.setEnabled(true);
                speak();


            }
            else {
                button1.setEnabled(true);
                stt();
            }
        }
        else {
            Log.e("TextToSpeech","Initialzation failed");
        }
    }
    public void speak(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }

    }
    public void stt(){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                TTS.speak(voicedata, TextToSpeech.QUEUE_FLUSH, null, null);


    }}
}
