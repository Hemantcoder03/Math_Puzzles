package com.example.mathpuzzles;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    MaterialButton mainPageBackBtn, clearBtn, hintDialogBtn, enterBtn, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    MaterialButton hintBtn, hintAnswerBtn, hintCloseBtn;
    MaterialTextView hintPageHint, closePageHint, hintPageAnswer, closeAnswerPage;  //hint dialog buttons
    MaterialTextView levelId, answer, wrongAnswerText;
    ImageFilterView image;
    int[] imageArray;
    int[] currentLevel;
    String[] data;
    MediaPlayer wrong_answer_sound, correct_answer_sound;

    //hintDialog
    Dialog hintPageDialog, answerPageDialog;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //used to manipulate data from sqlite database
        helper = new DBHelper(MainActivity.this);

        //set find view for header elements
        mainPageBackBtn = findViewById(R.id.main_page_back_btn);
        levelId = findViewById(R.id.level_id);

        //set find view for bottom elements
        clearBtn = findViewById(R.id.cross_btn);
        hintDialogBtn = findViewById(R.id.hint_btn);
        enterBtn = findViewById(R.id.enter_btn);
        answer = findViewById(R.id.answer);
        btn0 = findViewById(R.id.button_0);
        btn1 = findViewById(R.id.button_1);
        btn2 = findViewById(R.id.button_2);
        btn3 = findViewById(R.id.button_3);
        btn4 = findViewById(R.id.button_4);
        btn5 = findViewById(R.id.button_5);
        btn6 = findViewById(R.id.button_6);
        btn7 = findViewById(R.id.button_7);
        btn8 = findViewById(R.id.button_8);
        btn9 = findViewById(R.id.button_9);

        //image view
        image = findViewById(R.id.image);

        wrongAnswerText = findViewById(R.id.wrong_answer_text);

        //set the sound when answer is wrong or correct
        //set the sound answer is wrong
        wrong_answer_sound = new MediaPlayer();
        wrong_answer_sound = MediaPlayer.create(this, R.raw.wrong_answer_sound);

        //Set the sound when answer is right
        correct_answer_sound = new MediaPlayer();
        correct_answer_sound = MediaPlayer.create(this, R.raw.correct_answer_sound);

        //array for image data from drawable
        //It can start from index 0 and we start from 1 hence we can repeat the image1
        imageArray = new int[]{R.drawable.image1,R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,R.drawable.image5,
                R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9,R.drawable.image10,
                R.drawable.image11, R.drawable.image12, R.drawable.image13, R.drawable.image14,R.drawable.image15,
                R.drawable.image16, R.drawable.image17, R.drawable.image18, R.drawable.image19,R.drawable.image20,
                R.drawable.image21, R.drawable.image22, R.drawable.image23, R.drawable.image24,R.drawable.image25,
                R.drawable.image26, R.drawable.image27, R.drawable.image28, R.drawable.image29,R.drawable.image30,
                R.drawable.image31, R.drawable.image32, R.drawable.image33, R.drawable.image34,R.drawable.image35,
                R.drawable.image36, R.drawable.image37, R.drawable.image38, R.drawable.image39,R.drawable.image40,
                R.drawable.image41, R.drawable.image42, R.drawable.image43, R.drawable.image44};

        //insert the data in sqlite
        if (!sharedPreferences.getBoolean("insert", false)) {
            helper.insertData();

            editor.putBoolean("insert", true);
            editor.apply();
        }

        //It can set the next level content
        if (getIntent().getIntExtra("next_level", 0) == 1) {
            editor.putInt("next_level", 0);
            editor.apply();
            nextLevel();
        }

        //It can get the level id and it can set the level which can choice by user
        int level_id = getIntent().getIntExtra("level_id", 0);

        //when it came from play button than set max level was unlocked
        if (getIntent().getIntExtra("play_button", 0) == 1) {

            level_id = sharedPreferences.getInt("maxLevel", 0) + 1;

            //It can set the last level as default wen clicked on play button and when all levels are unlocked
            if (imageArray.length == sharedPreferences.getInt("maxLevel", 0) + 1) {
                level_id = sharedPreferences.getInt("maxLevel", 0);
            }
            editor.putInt("play_button", 0);
            editor.apply();

            //when app restart than set the id default 1
            if (sharedPreferences.getInt("restart", 0) == 1) {

                editor.putInt("restart", 0);
                editor.apply();
                level_id = 1;
                editor.putInt("maxLevel", 0);
                editor.apply();
            }
        }
        if (level_id != 0) {

            editor.putInt("levelNumber", level_id);
            editor.apply();
        }

        //get current level
        currentLevel = new int[]{sharedPreferences.getInt("levelNumber", 1)};

        //set the current level data
        if (imageArray.length > currentLevel[0]) {
            editor.putInt("levelNumber", currentLevel[0]);
            editor.apply();

            levelId.setText("Level " + currentLevel[0]);
            image.setImageResource(imageArray[currentLevel[0]]);
        }

        mainPageBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //It can redirect to the start page
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
                Animatoo.INSTANCE.animateFade(MainActivity.this);
            }
        });

        data = new String[]{""}; // which can store the whole entered data

        //set the selected number on the textview
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "0";
                saveData(data[0]);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "1";
                saveData(data[0]);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "2";
                saveData(data[0]);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "3";
                saveData(data[0]);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "4";
                saveData(data[0]);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "5";
                saveData(data[0]);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "6";
                saveData(data[0]);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "7";
                saveData(data[0]);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "8";
                saveData(data[0]);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] += "9";
                saveData(data[0]);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] = "";
                saveData(data[0]);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data[0] == "") {
                    if (sharedPreferences.getInt("volume", 1) == 1) {
                        wrong_answer_sound.start();
                    }
                    wrongAnswerText.setText("Enter something");
                    wrongAnswerText.setVisibility(View.VISIBLE);
                    wrongAnswerText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAnswerText.setVisibility(View.INVISIBLE);
                            wrongAnswerText.setText("Wrong answer, try again!!");
                        }
                    }, 2000);
                } else {

                    if (currentLevel[0] != imageArray.length - 1 && answer.getText() != "") {

                        //It is for regular move to the next level

                        if (Integer.parseInt(data[0]) == helper.getAnswer(currentLevel[0])) {

                            //set the level progress whether answer is correct or not
                            helper.updateProgress(currentLevel[0], true);

                            //It is set max level which unlocked
                            int maxLevel = sharedPreferences.getInt("maxLevel", 0);
                            if (currentLevel[0] > maxLevel) {
                                maxLevel = currentLevel[0];
                                editor.putInt("maxLevel", maxLevel);
                                editor.apply();
                            }

                            if (imageArray.length - 1 > currentLevel[0]) {
                                currentLevel[0]++;
                            }
                            editor.putInt("levelNumber", currentLevel[0]);
                            editor.apply();

                            //It redirect to correct answer activity
                            if (sharedPreferences.getInt("volume", 1) == 1) {
                                correct_answer_sound.start();
                            }
                            startActivity(new Intent(MainActivity.this, CorrectAnswerActivity.class));
                            finish();
                            Animatoo.INSTANCE.animateZoom(MainActivity.this);
                        } else {
                            //set the false where answer is wrong
                            if (sharedPreferences.getInt("volume", 1) == 1) {
                                wrong_answer_sound.start();
                            }
                            //set the answer box empty when answer is wrong
                            data[0] = "";
                            answer.setText(data[0]);
                            helper.updateProgress(currentLevel[0], false);

                            //If answer is wrong
                            wrongAnswerText.setVisibility(View.VISIBLE);
                            //It can hide after some time
                            wrongAnswerText.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    wrongAnswerText.setVisibility(View.INVISIBLE);
                                }
                            }, 5000);
                        }
                    } else if (currentLevel[0] != imageArray.length - 1 || answer.getText() != "") {

                        //It is for last level but for check there answer
                        if (Integer.parseInt(data[0]) == helper.getAnswer(currentLevel[0])) {

                            //It is set max level which unlocked
                            int maxLevel = sharedPreferences.getInt("maxLevel", 0);
                            if (currentLevel[0] > maxLevel) {
                                maxLevel = currentLevel[0];
                                editor.putInt("maxLevel", maxLevel);
                                editor.apply();
                            }

                            //set true where answer is correct
                            helper.updateProgress(currentLevel[0], true);

                            //It redirect to correct answer activity
                            if (sharedPreferences.getInt("volume", 1) == 1) {
                                correct_answer_sound.start();
                            }
                            startActivity(new Intent(MainActivity.this, LastCorrectAnswerActivity.class));
                            finish();
                            Animatoo.INSTANCE.animateZoom(MainActivity.this);

                            if (imageArray.length - 1 > currentLevel[0]) {
                                currentLevel[0]++;
                            }
                            editor.putInt("levelNumber", currentLevel[0]);
                            editor.apply();
                        } else {
                            helper.updateProgress(currentLevel[0], false);

                            //If answer is wrong
                            if (sharedPreferences.getInt("volume", 1) == 1) {
                                wrong_answer_sound.start();
                            }
                            //set the answer box empty when answer is wrong
                            data[0] = "";
                            answer.setText(data[0]);
                            wrongAnswerText.setVisibility(View.VISIBLE);
                            //It can hide after some time
                            wrongAnswerText.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    wrongAnswerText.setVisibility(View.INVISIBLE);
                                }
                            }, 5000);
                        }
                    }
                }
            }
        });

        hintDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show dialog of hint
                Dialog hintDialog = new Dialog(MainActivity.this);
                hintDialog.setContentView(R.layout.hint_dialog);
                hintDialog.setCancelable(false);
                hintDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                hintDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                hintDialog.getWindow().getAttributes().windowAnimations = R.style.exitDialogAnimation;

                hintBtn = hintDialog.findViewById(R.id.hintBtn);
                hintAnswerBtn = hintDialog.findViewById(R.id.hintAnswerBtn);
                hintCloseBtn = hintDialog.findViewById(R.id.hintCloseBtn);

                hintBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        hintDialog.dismiss();

                        //show new hint dialog
                        hintDialog();
                    }
                });

                hintAnswerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        hintDialog.dismiss();

                        //show answer dialog
                        answerDialog();
                    }
                });

                hintCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                });

                hintDialog.show();
            }
        });
    }

    public void saveData(String data) {
        if (data.length() > 10) {
            //set (...) when number count is greater than 10
            answer.setText(data.substring(0, 7) + "...");
        } else {
            answer.setText(data);
        }
    }

    private void nextLevel() {

        //set the data for next level data
        data[0] = "";
        answer.setText(data[0]);
        levelId.setText("Level " + currentLevel[0]);
        image.setImageResource(imageArray[currentLevel[0]]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, LevelsActivity.class));
        finish();
        //set animation when back button pressed
        Animatoo.INSTANCE.animateFade(MainActivity.this);
    }

    public void hintDialog() {

        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        hintPageDialog = new Dialog(MainActivity.this);
        hintPageDialog.setContentView(R.layout.hint_page);
        hintPageDialog.setCancelable(false);
        hintPageDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        hintPageDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hintPageDialog.show();

        hintPageHint = hintPageDialog.findViewById(R.id.hintPageHint);
        closePageHint = hintPageDialog.findViewById(R.id.closeHintPage);

        hintPageHint.setText(helper.getHint(sharedPreferences.getInt("levelNumber", 1)));
        closePageHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintPageDialog.dismiss();
            }
        });

    }

    public void answerDialog(){
        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //show new answer dialog
        answerPageDialog = new Dialog(MainActivity.this);
        answerPageDialog.setContentView(R.layout.answer_page);
        answerPageDialog.setCancelable(false);
        answerPageDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        answerPageDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        answerPageDialog.show();

        hintPageAnswer = answerPageDialog.findViewById(R.id.hintPageAnswer);
        closeAnswerPage = answerPageDialog.findViewById(R.id.closeAnswerPage);

        hintPageAnswer.setText(String.valueOf(helper.getAnswer(sharedPreferences.getInt("levelNumber", 1))));

        closeAnswerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPageDialog.dismiss();
            }
        });
    }

}