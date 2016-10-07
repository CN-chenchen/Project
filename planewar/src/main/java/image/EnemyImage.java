package image;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.planewar.GameView.pool;
import static utils.Constants.display_height;
import static utils.Constants.display_width;
import static utils.Constants.gameImages;
import static utils.Constants.score;
import static utils.Constants.speed;

/**
 * 负责敌方飞机图片的处理
 */

public class EnemyImage implements GameImage {

    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private List<Bitmap> booms = new ArrayList<Bitmap>();
    private int x;
    private int y;
    private int width;
    private int height;

    public EnemyImage(Bitmap enemy, Bitmap boom) {
//            bitmaps.add(Bitmap.createBitmap(enemy, 0, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 1, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 2, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 3, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 4, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 5, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 6, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 7, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 8, 0, enemy.getWidth() / 10, enemy.getHeight()));
//            bitmaps.add(Bitmap.createBitmap(enemy, (enemy.getWidth() / 10) * 9, 0, enemy.getWidth() / 10, enemy.getHeight()));
        bitmaps.add(enemy);

        booms.add(Bitmap.createBitmap(boom, 0, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 1, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 2, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 3, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 4, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 5, 0, boom.getWidth() / 6, boom.getHeight() / 2));

        width = enemy.getWidth();
        height = enemy.getHeight();

        y = -enemy.getHeight();
        Random random = new Random();
        x = random.nextInt(display_width - (enemy.getWidth()));
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
            //这里等于size-1出现死机
            if (index == bitmaps.size()) {
                index = 0;
            }
            num = 0;
        }
        y += speed;
        num++;
        if (y > display_height) {
            gameImages.remove(this);
        }
        return bitmap;
    }

    private boolean dead = false;

    //受到攻击
    public void attack(ArrayList<PlayerBullet> bullets_player, int sound_boom) {
        if (!dead) {
            for (GameImage image :
                    (List<GameImage>) bullets_player.clone()) {
                if (image.getX() > x && image.getY() > y && image.getX() < x + width && image.getY() < y + height
                        || image.getX() + image.getBitmap().getWidth() > x && image.getY() > y && image.getX() + image.getBitmap().getWidth() < x + width && image.getY() < y + height) {
                    System.out.println("--命中目标");
                    bullets_player.remove(image);
                    dead = true;
                    bitmaps = booms;
                    pool.play(sound_boom, 1, 1, 0, 0, 1);
                    score += 10;
                    break;
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
}
