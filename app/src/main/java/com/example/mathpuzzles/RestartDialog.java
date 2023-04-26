package com.example.mathpuzzles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;

public class RestartDialog {

    MaterialButton restartYesBtn,restartNoBtn;
    DBHelper helper;

    public RestartDialog(Context context) {
        this.context = context;
    }

    Context context;


    public void restartDialog() {

        helper = new DBHelper(context);

        //It can create dialog for restart game
        Dialog restartDialog = new Dialog(context);
        restartDialog.setContentView(R.layout.restart_dialog);
        restartDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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

    public void restartGame() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Math Riddles", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        helper.deleteLevelProgress();
        editor.putInt("restart",1);
        editor.apply();
        context.startActivity(new Intent(context,StartActivity.class));


        Animatoo.INSTANCE.animateSwipeRight(context);
    }

}
