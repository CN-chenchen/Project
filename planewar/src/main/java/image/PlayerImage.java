package image;

import android.graphics.Bitmap;

import com.example.planewar.SoundPlay;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.bullets_enemy;
import static utils.Constants.display_height;
import static utils.Constants.display_width;
import static utils.Constants.gameImages;

/**
 * 负责玩家飞机图片的处理
 */

public class PlayerImage implements GameImage {

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> booms = new ArrayList<Bitmap>();
    private int x;
    private int y;
    private int width;
    private int height;

    public PlayerImage(ArrayList<Bitmap> player, Bitmap boom) {
        this.bitmaps = player;

        booms.add(Bitmap.createBitmap(boom, 0, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 1, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 2, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 3, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 4, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 5, 0, boom.getWidth() / 6, boom.getHeight() / 2));

        //得到飞机的宽高
        width = player.get(0).getWidth();
        height = player.get(0).getHeight();

        //飞机的初始坐标
        x = (display_width - player.get(0).getWidth()) / 2;
        y = display_height - player.get(0).getHeight() - 10;
    }

    private int index = 0;
    private int num = 0;

    @Override
    public Bitmap getBitmap() {
        Bitmap bitmap = bitmaps.get(index);
        if (num == 5) {
            index++;
            if (index == booms.size() && dead) {
                gameImages.remove(this);
            }
            //index++先运算
            if (index == bitmaps.size()) {
                index = 0;
            }
            num = 0;
        }
        num++;
        return bitmap;
    }

    private boolean dead = false;

    //受到攻击
    public void attack(int sound_over) {
        if (!dead) {
            for (GameImage image : (List<GameImage>) bullets_enemy.clone()) {
                //命中目标移除子弹，并设置爆炸
                if (image.getX() > x && image.getY() > y && image.getX() < x + width && image.getY() < y + height
                        || image.getX() + image.getBitmap().getWidth() > x && image.getY() > y && image.getX() + image.getBitmap().getWidth() < x + width && image.getY() < y + height) {
                    bullets_enemy.remove(image);
                    dead = true;
                    bitmaps = booms;
                    new SoundPlay(sound_over).start();
                    System.out.println("--Game Over!");
                    break;
                }
            }
            for (GameImage image : (List<GameImage>) gameImages.clone()) {
                if (image instanceof EnemyImage) {
                    if (image.getX() > x && image.getY() > y && image.getX() < x + width && image.getY() < y + height
                            || image.getX() + ((EnemyImage) image).getWidth() > x && image.getY() > y && image.getX() + ((EnemyImage) image).getWidth() < x + width && image.getY() < y + height) {
                        dead = true;
                        bitmaps = booms;
                        new SoundPlay(sound_over).start();
                        System.out.println("--Game Over!");
                        break;
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
