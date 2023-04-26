package com.example.mathpuzzles;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;

public class LastCorrectAnswerActivity extends AppCompatActivity {

    MaterialButton restartBtn, testFriendsBtn, restartYesBtn, restartNoBtn;
    ImageView testFriendsImage;
    MaterialCardView imageCardView;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_correct_answer);

        //database manipulation
        helper = new DBHelper(LastCorrectAnswerActivity.this);

        restartBtn = findViewById(R.id.restartBtn);
        testFriendsBtn = findViewById(R.id.lastLevelTestFriendsBtn);
        testFriendsImage = findViewById(R.id.testFriendsImage);
        imageCardView = findViewById(R.id.image_cardView);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //It can restart the game

                //It can create dialog for restart game
                Dialog restartDialog = new Dialog(LastCorrectAnswerActivity.this);
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

        testFriendsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                testFriendsImage.setImageResource(R.drawable.image44);   //set the image of last level
                share(imageCardView);   //It can send image and set the share image as per the layout
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(LastCorrectAnswerActivity.this, LevelsActivity.class));
        finish();
        //set animation when back button pressed
        Animatoo.INSTANCE.animateFade(LastCorrectAnswerActivity.this);
    }

    public void restartGame() {
        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        helper.deleteLevelProgress();
        editor.putInt("restart", 1);
        editor.apply();
        startActivity(new Intent(LastCorrectAnswerActivity.this, StartActivity.class));
        finish();

        Animatoo.INSTANCE.animateSwipeRight(LastCorrectAnswerActivity.this);
    }

    public void share(View view){

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap=view.getDrawingCache();

        try{

            File file=new File(getApplicationContext().getExternalCacheDir(),File.separator+"image.jpg");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true,false);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoUri= FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",file);
            intent.putExtra(Intent.EXTRA_STREAM,photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");
            startActivity(Intent.createChooser(intent,"share"));

        }catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}