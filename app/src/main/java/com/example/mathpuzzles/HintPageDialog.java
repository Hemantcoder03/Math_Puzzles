package com.example.mathpuzzles;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

public class HintPageDialog {

    Context context;

    MaterialButton hintPageHint,closePageHint,hintPageAnswer,closeAnswerPage;

    public void hintDialog(){

        //show new hint dialog
        Dialog hintPageDialog = new Dialog(context);
        hintPageDialog.setContentView(R.layout.hint_page);
        hintPageDialog.setCancelable(false);
        hintPageDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        hintPageDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        hintPageDialog.show();

        hintPageHint = hintPageDialog.findViewById(R.id.hintPageHint);
        closePageHint = hintPageDialog.findViewById(R.id.closeHintPage);

        closePageHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintPageDialog.dismiss();
            }
        });
    }
}
