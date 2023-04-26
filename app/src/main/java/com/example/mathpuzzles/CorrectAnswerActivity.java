package com.example.mathpuzzles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;


public class CorrectAnswerActivity extends AppCompatActivity {

    MaterialButton nextLevelBtn,testFriendsBtn;
    MaterialCardView imageCardView;
    ImageView testFriendsImage;
    int[] imageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_answer);

        nextLevelBtn = findViewById(R.id.nextLevelBtn);
        testFriendsBtn = findViewById(R.id.testFriendsBtn);
        imageCardView = findViewById(R.id.image_cardView);
        testFriendsImage = findViewById(R.id.testFriendsImage);

        SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //array for image data from drawable
        //zero index is default
        imageArray = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,R.drawable.image5,
                R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9,R.drawable.image10,
                R.drawable.image11, R.drawable.image12, R.drawable.image13, R.drawable.image14,R.drawable.image15,
                R.drawable.image16, R.drawable.image17, R.drawable.image18, R.drawable.image19,R.drawable.image20,
                R.drawable.image21, R.drawable.image22, R.drawable.image23, R.drawable.image24,R.drawable.image25,
                R.drawable.image26, R.drawable.image27, R.drawable.image28, R.drawable.image29,R.drawable.image30,
                R.drawable.image31, R.drawable.image32, R.drawable.image33, R.drawable.image34,R.drawable.image35,
                R.drawable.image36, R.drawable.image37, R.drawable.image38, R.drawable.image39,R.drawable.image40,
                R.drawable.image41, R.drawable.image42, R.drawable.image43, R.drawable.image44};

        nextLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //It can redirect to the next level
                SharedPreferences sharedPreferences = getSharedPreferences("Math Riddles", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("next_level",1);
                editor.apply();
                startActivity(new Intent(CorrectAnswerActivity.this, MainActivity.class));
                finish();
                Animatoo.INSTANCE.animateSlideDown(CorrectAnswerActivity.this);
            }
        });

        testFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int level_id = sharedPreferences.getInt("levelNumber",1)-1;  // This -1 because we referring now previous activity not next
                testFriendsImage.setImageResource(imageArray[level_id - 1]);   //set the image from array
                share(imageCardView);   //It can send image and set the share image as per the layout
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(CorrectAnswerActivity.this,LevelsActivity.class));
        finish();
        //set animation when back button pressed
        Animatoo.INSTANCE.animateFade(CorrectAnswerActivity.this);
    }

    public void share(View view){

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = view.getDrawingCache();

        try{

            File file = new File(getApplicationContext().getExternalCacheDir(),File.separator+"image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true,false);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()+".provider",file);
            intent.putExtra(Intent.EXTRA_STREAM,photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");
            startActivity(Intent.createChooser(intent,"share"));

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}