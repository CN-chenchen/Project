package image;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

import static com.example.planewar.GameView.pool;
import static utils.Constants.display_height;
import static utils.Constants.display_width;
import static utils.Constants.gameImages;
import static utils.Constants.isCatch;

/**
 * Created by Hello on 2016/10/5.
 */

public class SupplyImage implements GameImage {

    private Bitmap supply;
    private int x;
    private int y;
    private int width;
    private int height;

    public SupplyImage(Bitmap supply) {
        this.supply = supply;

        width = supply.getWidth();
        height = supply.getHeight();

        y = display_height / 3;
        Random random = new Random();
        x = random.nextInt(display_width - (supply.getWidth()));
    }

    int num = 0;

    @Override
    public Bitmap getBitmap() {

        if (isCatch) {
            gameImages.remove(this);
        }

        if (num == 100) {
            if (x > display_width) {
                x--;
            } else {
                x++;
            }
            if (y > display_height) {
                y--;
            } else {
                y++;
            }
            num = 0;
        }
        num++;
        return supply;
    }


    //获得弹药
    public void getBullet(int sound_get_bullet) {
        if (!isCatch) {
            for (GameImage image : (List<GameImage>) gameImages.clone()) {
                if (image instanceof PlayerImage) {
                    if (image.getX() > x && image.getY() > y && image.getX() < x + width && image.getY() < y + height
                            || image.getX() + ((PlayerImage) image).getWidth() > x && image.getY() > y && image.getX() + ((PlayerImage) image).getWidth() < x + width && image.getY() < y + height) {
                        isCatch = true;
                        pool.play(sound_get_bullet, 1, 1, 0, 0, 1);
                        System.out.println("--得到弹药!");
                        break;
                    }
                }
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
