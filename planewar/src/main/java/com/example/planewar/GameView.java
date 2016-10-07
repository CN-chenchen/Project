package com.example.planewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import image.BackImage;
import image.EnemyBullet;
import image.EnemyImage;
import image.GameImage;
import image.PlayerBullet;
import image.PlayerImage;
import image.SupplyImage;

import static utils.Constants.bullets_enemy;
import static utils.Constants.bullets_player;
import static utils.Constants.display_height;
import static utils.Constants.display_width;
import static utils.Constants.enemies;
import static utils.Constants.gameImages;
import static utils.Constants.isCatch;
import static utils.Constants.level;
import static utils.Constants.score;
import static utils.Constants.speed;
import static utils.Constants.target;
import static utils.Constants.update;

/**
 * Created by Hello on 2016/10/4.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {

    private Bitmap bacekground_1;
    private Bitmap bacekground_2;
    private Bitmap bacekground_3;
    private Bitmap bacekground_4;
    private Bitmap bacekground_5;
    private ArrayList<Bitmap> bacekgrounds = new ArrayList<Bitmap>();
    private Bitmap enemy;
    private Bitmap player;
    private Bitmap supply;
    private Bitmap bullet_player;
    private Bitmap bullet_enemy;
    private Bitmap bullet_award;
    private Bitmap boom;
    private Bitmap cacheBitmap;//二级缓存
    private PlayerImage selectPlayer;
    private EnemyImage enemyImage;
    private boolean state = false;
    private SurfaceHolder holder;
    private Thread thread = null;

    private boolean stopState = false;

    //声音池
    public static SoundPool pool;
    private int sound_bullet = 0;
    private int sound_boom = 0;
    private int sound_over = 0;
    private int sound_get_bullet = 0;
    private BackImage backImage;
    private PlayerImage playerImage;
    private SupplyImage supplyImage;
    private int supply_num = new Random().nextInt(5);

    public GameView(Context context) {
        super(context);
        //注册事件
        getHolder().addCallback(this);
        this.setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //得到屏幕的宽高
        display_width = width;
        display_height = height;
        init();
        this.holder = holder;

        state = true;
        thread = new Thread(this);
        thread.start();
    }

    private void init() {
        //加载照片
        bacekground_1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg_1);
        bacekground_2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg_2);
        bacekground_3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg_3);
        bacekground_4 = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg_4);
        bacekground_5 = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg_5);
        bacekgrounds.add(bacekground_1);
        bacekgrounds.add(bacekground_2);
        bacekgrounds.add(bacekground_3);
        bacekgrounds.add(bacekground_4);
        bacekgrounds.add(bacekground_5);

        enemy = BitmapFactory.decodeResource(getResources(), R.drawable.img_enemy);
        player = BitmapFactory.decodeResource(getResources(), R.drawable.img_player);
        supply = BitmapFactory.decodeResource(getResources(), R.drawable.img_supply);
        bullet_player = BitmapFactory.decodeResource(getResources(), R.drawable.img_bullet_plane);
        bullet_enemy = BitmapFactory.decodeResource(getResources(), R.drawable.img_bullet_enemy);
        bullet_award = BitmapFactory.decodeResource(getResources(), R.drawable.img_bullet_award);
        boom = BitmapFactory.decodeResource(getResources(), R.drawable.img_boom);

        //生产二级缓存照片
        cacheBitmap = Bitmap.createBitmap(display_width, display_height, Bitmap.Config.ARGB_8888);
        backImage = new BackImage(bacekgrounds.get(level - 1));
        playerImage = new PlayerImage(player, boom);
        supplyImage = new SupplyImage(supply);
        gameImages.add(backImage);//先加入背景照片
        gameImages.add(playerImage);

        //加载声音
        pool = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
        sound_bullet = pool.load(getContext(), R.raw.bullet, 1);
        sound_boom = pool.load(getContext(), R.raw.boom, 1);
        sound_over = pool.load(getContext(), R.raw.game_over, 1);
        sound_get_bullet = pool.load(getContext(), R.raw.get_bullet, 1);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        state = false;
    }

    public void stop() {
        stopState = true;
    }

    public void start() {
        stopState = false;
        thread.interrupt();//唤醒
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        Paint score_paint = new Paint();
        score_paint.setColor(0xffcc0000);
        score_paint.setTextSize(54);
        score_paint.setDither(true);
        score_paint.setAntiAlias(true);
        int enemy_num = 0;
        int bullet_player_num = 0;
        int bullet_enemy_num = 0;
        try {
            while (state) {
                while (stopState) {
                    try {
                        Thread.sleep(100000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (selectPlayer != null) {
                    if (bullet_player_num == 10) {
                        new SoundPlay(sound_bullet).start();
                        if (!isCatch) {
                            bullets_player.add(new PlayerBullet(selectPlayer, bullet_player));
                        } else {
                            bullets_player.add(new PlayerBullet(selectPlayer, bullet_award));
                        }
                        bullet_player_num = 0;
                    }
                    bullet_player_num++;
                }

                Canvas newCanvas = new Canvas(cacheBitmap);

                for (GameImage image :
                        (List<GameImage>) gameImages.clone()) {
                    newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
                    if (image instanceof EnemyImage) {
                        //敌方飞机受到攻击
                        ((EnemyImage) image).attack(bullets_player, sound_boom);
                    }
                    if (image instanceof PlayerImage) {
                        //玩家飞机受到攻击
                        //((PlayerImage) image).attack(bullets_enemy, sound_over);
                    }
                    if (image instanceof SupplyImage) {
                        ((SupplyImage) image).getBullet(sound_get_bullet);
                    }
                }
                for (GameImage image :
                        (List<GameImage>) bullets_player.clone()) {
                    newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
                }
                for (GameImage image : (List<GameImage>) bullets_enemy.clone()) {
                    newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
                }

                //分数
                newCanvas.drawText("score:" + score, 12, 60, score_paint);
                newCanvas.drawText("level:" + level, 12, 114, score_paint);
                newCanvas.drawText("target:" + target, 12, 168, score_paint);

                if (update[level - 1][1] <= score) {
                    target = update[level][1];
                    enemies = update[level][2];
                    speed = update[level][3];
                    score = update[level - 1][1] - score;
                    level = update[level][0];
                    gameImages.add(new BackImage(bacekgrounds.get(level - 1)));//先加入背景照片
                    gameImages.add(playerImage);
                }

                if (enemy_num == enemies) {
                    enemyImage = new EnemyImage(enemy, boom);
                    gameImages.add(enemyImage);
                    enemy_num = 0;
                }
                enemy_num++;
                if (enemyImage != null) {
                    if (bullet_enemy_num == 16) {
                        bullets_enemy.add(new EnemyBullet(enemyImage, bullet_enemy));
                        bullet_enemy_num = 0;
                    }
                    bullet_enemy_num++;
                }
                if (level == supply_num + 1 && score == 50) {
                    if (!isCatch) {
                        gameImages.add(supplyImage);
                    }
                }
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(cacheBitmap, 0, 0, paint);
                holder.unlockCanvasAndPost(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (GameImage game :
                    gameImages) {
                if (game instanceof PlayerImage) {
                    PlayerImage player = (PlayerImage) game;
                    if (game.getX() < event.getX() && game.getY() < event.getY() && game.getX() + player.getWidth() > event.getX() && game.getY() + player.getHeight() > event.getY()) {
                        System.out.println("--选中飞机");
                        selectPlayer = player;
                    } else {
                        System.out.println("--没有选中飞机");
                        selectPlayer = null;
                    }
                    break;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selectPlayer != null) {
                selectPlayer.setX((int) event.getX() - selectPlayer.getWidth() / 2);
                selectPlayer.setY((int) event.getY() - selectPlayer.getHeight() / 2);
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            selectPlayer = null;
        }
        return true;
    }

}
