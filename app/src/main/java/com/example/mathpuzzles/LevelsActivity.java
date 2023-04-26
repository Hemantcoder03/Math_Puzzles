package com.example.mathpuzzles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;

public class LevelsActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialButton backBtn,level1,level2,level3,level4,level5,level6,level7,level8,level9,level10,
    level11,level12,level13,level14,level15,level16,level17,level18,level19,level20,
    level21,level22,level23,level24,level25,level26,level27,level28,level29,level30,
    level31,level32,level33,level34,level35,level36,level37,level38,level39,level40,
    level41,level42,level43,level44;
    DBHelper helper;

    //create and initize the values in the levels which contains the level_id
    int[] levels = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44};
    int[] levelIdArray;
    MaterialButton[] levelsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        helper = new DBHelper(this);

        //back button to the startActivity
        backBtn = findViewById(R.id.level_page_back_btn);

        //levels button
        level1 = findViewById(R.id.level_1);
        level2 = findViewById(R.id.level_2);
        level3 = findViewById(R.id.level_3);
        level4 = findViewById(R.id.level_4);
        level5 = findViewById(R.id.level_5);
        level6 = findViewById(R.id.level_6);
        level7 = findViewById(R.id.level_7);
        level8 = findViewById(R.id.level_8);
        level9 = findViewById(R.id.level_9);
        level10 = findViewById(R.id.level_10);
        level11 = findViewById(R.id.level_11);
        level12 = findViewById(R.id.level_12);
        level13 = findViewById(R.id.level_13);
        level14 = findViewById(R.id.level_14);
        level15 = findViewById(R.id.level_15);
        level16 = findViewById(R.id.level_16);
        level17 = findViewById(R.id.level_17);
        level18 = findViewById(R.id.level_18);
        level19 = findViewById(R.id.level_19);
        level20= findViewById(R.id.level_20);
        level21 = findViewById(R.id.level_21);
        level22 = findViewById(R.id.level_22);
        level23= findViewById(R.id.level_23);
        level24 = findViewById(R.id.level_24);
        level25 = findViewById(R.id.level_25);
        level26 = findViewById(R.id.level_26);
        level27 = findViewById(R.id.level_27);
        level28 = findViewById(R.id.level_28);
        level29 = findViewById(R.id.level_29);
        level30 = findViewById(R.id.level_30);
        level31 = findViewById(R.id.level_31);
        level32 = findViewById(R.id.level_32);
        level33 = findViewById(R.id.level_33);
        level34 = findViewById(R.id.level_34);
        level35 = findViewById(R.id.level_35);
        level36 = findViewById(R.id.level_36);
        level37 = findViewById(R.id.level_37);
        level38 = findViewById(R.id.level_38);
        level39 = findViewById(R.id.level_39);
        level40 = findViewById(R.id.level_40);
        level41 = findViewById(R.id.level_41);
        level42 = findViewById(R.id.level_42);
        level43 = findViewById(R.id.level_43);
        level44 = findViewById(R.id.level_44);


        //It can store view of all levels
        levelsView = new MaterialButton[]{level1,level2,level3,level4,level5,level6,level7,level8,level9,level10,
                level11,level12,level13,level14,level15,level16,level17,level18,level19,level20,
                level21,level22,level23,level24,level25,level26,level27,level28,level29,level30,
                level31,level32,level33,level34,level35,level36,level37,level38,level39,level40,
                level41,level42,level43,level44};

        //It can set default level 1 as unlocked
        levelsView[0].setBackgroundResource(R.color.app_background);

        //set background color to all levels according to which the level is passed or not
        for(int i=0; i<levels.length; i++){
            int progress = helper.getLevelProgress(levels[i]);

            if(progress == 1){
                levelsView[i].setBackgroundResource(R.color.app_background);
                if(i+1 != levels.length){
                    levelsView[i+1].setBackgroundResource(R.color.app_background);
                }
            }
        }

        //set on click listeners on levels button
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
        level6.setOnClickListener(this);
        level7.setOnClickListener(this);
        level8.setOnClickListener(this);
        level9.setOnClickListener(this);
        level10.setOnClickListener(this);
        level11.setOnClickListener(this);
        level12.setOnClickListener(this);
        level13.setOnClickListener(this);
        level14.setOnClickListener(this);
        level15.setOnClickListener(this);
        level16.setOnClickListener(this);
        level17.setOnClickListener(this);
        level18.setOnClickListener(this);
        level19.setOnClickListener(this);
        level20.setOnClickListener(this);
        level21.setOnClickListener(this);
        level22.setOnClickListener(this);
        level23.setOnClickListener(this);
        level24.setOnClickListener(this);
        level25.setOnClickListener(this);
        level26.setOnClickListener(this);
        level27.setOnClickListener(this);
        level28.setOnClickListener(this);
        level29.setOnClickListener(this);
        level30.setOnClickListener(this);
        level31.setOnClickListener(this);
        level32.setOnClickListener(this);
        level33.setOnClickListener(this);
        level34.setOnClickListener(this);
        level35.setOnClickListener(this);
        level36.setOnClickListener(this);
        level37.setOnClickListener(this);
        level38.setOnClickListener(this);
        level39.setOnClickListener(this);
        level40.setOnClickListener(this);
        level41.setOnClickListener(this);
        level42.setOnClickListener(this);
        level43.setOnClickListener(this);
        level44.setOnClickListener(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelsActivity.this,StartActivity.class));
                finish();
                Animatoo.INSTANCE.animateFade(LevelsActivity.this);
            }
        });
    }


    @Override
    public void onClick(View view) {

        //Array of get level_id by R.id
        levelIdArray = new int[]{R.id.level_1,R.id.level_2,R.id.level_3,R.id.level_4,R.id.level_5,
                R.id.level_6,R.id.level_7,R.id.level_8,R.id.level_9,R.id.level_10,
                R.id.level_11,R.id.level_12,R.id.level_13,R.id.level_14,R.id.level_15,
                R.id.level_16,R.id.level_17,R.id.level_18,R.id.level_19,R.id.level_20,
                R.id.level_21,R.id.level_22,R.id.level_23,R.id.level_24,R.id.level_25,
                R.id.level_26,R.id.level_27,R.id.level_28,R.id.level_29,R.id.level_30,
                R.id.level_31,R.id.level_32,R.id.level_33,R.id.level_34,R.id.level_35,
                R.id.level_36,R.id.level_37,R.id.level_38,R.id.level_39,R.id.level_40,
                R.id.level_41,R.id.level_42,R.id.level_43,R.id.level_44
        };

        //check whether the level is unlock or not

        Intent intent = new Intent(LevelsActivity.this,MainActivity.class);

            //check the every id and if get then performed the given work
            for(int i=0; i<levelIdArray.length; i++){
                if(view.getId() == levelIdArray[i]){
                    //first it check whether level is lock or unlock
                    if(helper.getLevelProgress(i+1) == 1) {
                        //It is for unlock level
                        intent.putExtra("level_id", i + 1);
                        startActivity(intent);
                        finish();
                        Animatoo.INSTANCE.animateFade(LevelsActivity.this);
                    }
                    else if(helper.getLevelProgress(i) == 1){
                        //It check whether the next level is unlock
                        intent.putExtra("level_id", i + 1);
                        startActivity(intent);
                        finish();
                        Animatoo.INSTANCE.animateFade(LevelsActivity.this);
                    }
                    else{
                        if(view.getId() == levelIdArray[0]){
                            //It set default level 1 as unlock
                            intent.putExtra("level_id",1);
                            startActivity(intent);
                            finish();
                            Animatoo.INSTANCE.animateFade(LevelsActivity.this);
                        }
                    }
                }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //It called repeatly to refresh the page
        //set background color to all levels according to which the level is passed or not
        for(int i=0; i<levels.length; i++){
            int progress = helper.getLevelProgress(levels[i]);

            if(progress == 1){
                levelsView[i].setBackgroundResource(R.color.app_background);
                if(i+1 != levels.length){
                    levelsView[i+1].setBackgroundResource(R.color.app_background);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("exit",1);
        editor.apply();

        startActivity(new Intent(LevelsActivity.this,StartActivity.class));
        finish();

        //set animation when back button pressed
        Animatoo.INSTANCE.animateFade(LevelsActivity.this);
    }


}