package com.example.planewar;

import static com.example.planewar.GameView.pool;

/**
 * Created by Hello on 2016/10/4.
 */

public class SoundPlay extends Thread {

    int i = 0;

    public SoundPlay(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        pool.play(i, 1, 1, 0, 0, 1);
    }

}

