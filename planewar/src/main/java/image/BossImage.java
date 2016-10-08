package image;

import android.graphics.Bitmap;

import java.util.ArrayList;

import static utils.Constants.level;

/**
 * Created by Hello on 2016/10/8.
 */

public class BossImage implements GameImage {

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> booms = new ArrayList<Bitmap>();
    private int width;
    private int height;
    private int x;
    private int y;

    public BossImage(ArrayList<Bitmap> boss, Bitmap boom) {
        this.bitmaps = boss;

        booms.add(Bitmap.createBitmap(boom, 0, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 1, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 2, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 3, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 4, 0, boom.getWidth() / 6, boom.getHeight() / 2));
        booms.add(Bitmap.createBitmap(boom, (boom.getWidth() / 6) * 5, 0, boom.getWidth() / 6, boom.getHeight() / 2));

        width = boss.get(level - 1).getWidth();
        height = boss.get(level - 1).getHeight();

        //boss的初始坐标
        x = -width / 2;
        y = -height / 2;
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
