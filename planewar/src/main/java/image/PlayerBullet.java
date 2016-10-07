package image;

import android.graphics.Bitmap;

import static utils.Constants.bullets_player;

/**
 * 负责玩家子弹图片的处理
 */

public class PlayerBullet implements GameImage {

    private Bitmap bullet_player;
    private int x;
    private int y;

    public PlayerBullet(PlayerImage player, Bitmap bullet_player) {
        this.bullet_player = bullet_player;

        x = player.getX() + player.getWidth() / 2 - bullet_player.getWidth() / 2;
        y = player.getY() - bullet_player.getHeight();
    }

    @Override
    public Bitmap getBitmap() {
        y -= 6;
        if (y <= 0) {
            bullets_player.remove(this);
        }
        return bullet_player;
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
