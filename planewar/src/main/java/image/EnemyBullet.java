package image;

import android.graphics.Bitmap;


import static utils.Constants.bullets_enemy;
import static utils.Constants.display_height;
import static utils.Constants.speed;

/**
 * 负责敌方子弹图片的处理
 */

public class EnemyBullet implements GameImage {

    private Bitmap bullet_enemy;
    private int x;
    private int y;

    public EnemyBullet(EnemyImage enemy, Bitmap bullet_enemy) {
        this.bullet_enemy = bullet_enemy;

        x = enemy.getX() + enemy.getWidth() / 2 - bullet_enemy.getWidth() / 2;
        y = enemy.getY() + enemy.getHeight();
    }

    @Override
    public Bitmap getBitmap() {
        y += speed + 9;
        if (y > display_height) {
            bullets_enemy.remove(this);
        }
        return bullet_enemy;
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
