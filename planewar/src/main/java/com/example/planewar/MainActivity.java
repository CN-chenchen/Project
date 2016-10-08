package com.example.planewar;

import android.content.DialogInterface;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
        initBGM();
    }

    private void initBGM() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gameView.stop();

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("是否退出？");
            alert.setNegativeButton("退出", this);
            alert.setPositiveButton("继续", this);
            alert.create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == -2) {
            Process.killProcess(Process.myPid());
        } else {
            gameView.start();
        }
        System.out.println("--" + which);
    }
}
