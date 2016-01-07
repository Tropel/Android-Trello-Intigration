package com.vaiuu.androidtrello.util;


import android.app.Dialog;
import android.content.Context;

import com.vaiuu.androidtrello.R;


public class CustomLoaderDialog {
    Context c;
    Dialog dialog;
    public CustomLoaderDialog(Context context) {
        this.c = context;
        dialog = new Dialog(c, R.style.DialogTheme);
    }
    /**
     * This method use for show progress dailog
     *
     * @param isCancelable set true if you set calcel progressDialog by user event
     */
    public void show(Boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.progress_layout);
        dialog.show();
    }

    public Boolean isShowing() {
        return dialog.isShowing();
    }

    public void hide() {
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
        }
    }
}
