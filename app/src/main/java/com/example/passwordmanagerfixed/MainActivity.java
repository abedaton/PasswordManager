package com.example.passwordmanagerfixed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonGen;
    private TextView password, lenPassword;
    private SeekBar seekBar;
    private CheckBox checkSpecial, checkNum;
    private int lenPass = 1;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGen = findViewById(R.id.idButtonGenerate);
        password = findViewById(R.id.idPassword);
        lenPassword = findViewById(R.id.idLenPassword);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(99);
        checkSpecial = findViewById(R.id.idSpecial);
        checkNum = findViewById(R.id.idNum);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                if (progress < min) {
                    seekBar.setProgress(min);
                }
                lenPassword.setText("Length of password: " + seekBar.getProgress());
                lenPass = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText(generateString(lenPass));
            }
        });

        password.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String text;
                text = password.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(MainActivity.this, "Copied to clipboard.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    private String generateString(int length) {
        char[] chars = "AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn".toCharArray();
        char[] charsNum = "AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn0123456789".toCharArray();
        char[] specials = "AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn0123456789&é'(§è!çà)-µù,;:=+/.?<>'".toCharArray();
        char[] specialNoNum = "AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn&é'(§è!çà)-µù,;:=+/.?<>'".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i< length; i++){
            if(checkSpecial.isChecked() && checkNum.isChecked()){
                char c = specials[random.nextInt(specials.length)];
                stringBuilder.append(c);
            }else if(checkSpecial.isChecked()){
                char c = specialNoNum[random.nextInt(specialNoNum.length)];
                stringBuilder.append(c);
            } else if(checkNum.isChecked()){
                char c = charsNum[random.nextInt(charsNum.length)];
                stringBuilder.append(c);
            } else {
                char c = chars[random.nextInt(chars.length)];
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }
}
