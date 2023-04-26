package com.example.mathpuzzles;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {

    MaterialButton playBtn, levelsBtn, settingsBtn, exitBtn, exitPageYesBtn, exitPageNoBtn, settingsRestartBtn, settingsVolumeBtn, settingsCloseBtn,
            restartYesBtn, restartNoBtn;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //set findviewbyid for all button
        playBtn = findViewById(R.id.play_btn);
        levelsBtn = findViewById(R.id.levels_btn);
        settingsBtn = findViewById(R.id.settings_btn);
        exitBtn = findViewById(R.id.exit_btn);

        //now set onclick listener
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //It can redirect to the mainactivity
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("play_button", 1);
                startActivity(intent);
                finish();
                Animatoo.INSTANCE.animateFade(StartActivity.this);
            }
        });

        levelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //It can redirect to the levelsActivity to display the total levels
                startActivity(new Intent(StartActivity.this, LevelsActivity.class));
                finish();
                Animatoo.INSTANCE.animateFade(StartActivity.this);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //settings dialog
                Dialog settingsDialog = new Dialog(StartActivity.this);
                settingsDialog.setContentView(R.layout.settings_dialog);
                settingsDialog.setCancelable(false);
                settingsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                settingsDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                settingsDialog.show();

                settingsRestartBtn = settingsDialog.findViewById(R.id.settingsRestartBtn);
                settingsVolumeBtn = settingsDialog.findViewById(R.id.settingsVolumeBtn);
                settingsCloseBtn = settingsDialog.findViewById(R.id.settingsCloseBtn);

                //set the previous settings done by the user or set default option
                if (sharedPreferences.getInt("volume", 1) == 1) {
                    settingsVolumeBtn.setText("Sound ON");
                    settingsVolumeBtn.setIconResource(R.drawable.sound_on);
                } else {
                    settingsVolumeBtn.setText("Sound OFF");
                    settingsVolumeBtn.setIconResource(R.drawable.sound_off);
                }

                settingsRestartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //It can restart the game
                        helper = new DBHelper(StartActivity.this);

                        //It can create dialog for restart game
                        Dialog restartDialog = new Dialog(StartActivity.this);
                        restartDialog.setContentView(R.layout.restart_dialog);
                        restartDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        restartDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        restartDialog.getWindow().getAttributes().windowAnimations = R.style.exitDialogAnimation;   //set animation to the dialog box
                        restartDialog.setCancelable(false);
                        restartDialog.show();

                        restartYesBtn = restartDialog.findViewById(R.id.restartYesBtn);
                        restartNoBtn = restartDialog.findViewById(R.id.restartNoBtn);

                        restartYesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                restartGame();
                            }
                        });

                        restartNoBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                restartDialog.dismiss();
                            }
                        });
                    }

                });

                settingsVolumeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //first get the default settings of sound
                        //if sound on then set sound as off or vice versa
                        if (sharedPreferences.getInt("volume", 1) == 1) {
                            settingsVolumeBtn.setText("Sound OFF");
                            settingsVolumeBtn.setIconResource(R.drawable.sound_off);
                            editor.putInt("volume", 0);
                            editor.apply();
                        } else {
                            settingsVolumeBtn.setText("Sound ON");
                            settingsVolumeBtn.setIconResource(R.drawable.sound_on);
                            editor.putInt("volume", 1);
                            editor.apply();
                        }
                    }
                });

                settingsCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingsDialog.dismiss();
                    }
                });
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //exit dialog
                Dialog exitDialog = new Dialog(StartActivity.this);
                exitDialog.setContentView(R.layout.exit_dialog);
                exitDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);  //set height and width to the dialog box
                exitDialog.getWindow().getAttributes().windowAnimations = R.style.exitDialogAnimation;   //set animation to the dialog box
                exitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                exitDialog.setCancelable(false);
                exitDialog.show();

                //exit dialog id
                exitPageYesBtn = exitDialog.findViewById(R.id.exitPageYesBtn);
                exitPageNoBtn = exitDialog.findViewById(R.id.exitPageNoBtn);

                exitPageYesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //exit from app

                        finish();
                        StartActivity.super.onBackPressed(); // it is also used to exit

                    }
                });

                exitPageNoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //not exit from app
                        exitDialog.dismiss();
                    }
                });
            }
        });

    }

    public void restartGame() {
        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        helper.deleteLevelProgress();
        editor.putInt("restart", 1);
        editor.apply();
        startActivity(new Intent(StartActivity.this, StartActivity.class));
        finish();

        Animatoo.INSTANCE.animateSwipeRight(StartActivity.this);
    }
}