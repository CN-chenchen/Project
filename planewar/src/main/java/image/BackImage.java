package image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import static utils.Constants.display_height;
import static utils.Constants.display_width;

/**
 * 负责背景图片的处理
 */
public class BackImage implements GameImage {

    private Bitmap background;

    public BackImage(Bitmap background) {
        this.background = background;
        newBitmap = Bitmap.createBitmap(display_width, display_height, Bitmap.Config.ARGB_8888);
    }

    private Bitmap newBitmap = null;
    private int height = 0;

    public Bitmap getBitmap() {
        Paint paint = new Paint();
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(background, new Rect(0, 0, background.getWidth(), background.getHeight()), new Rect(0, height, display_width, display_height + height), paint);
        canvas.drawBitmap(background, new Rect(0, 0, background.getWidth(), background.getHeight()), new Rect(0, -display_height + height, display_width, height), paint);

        height += 3;
        if (height >= display_height) {
            height = 0;
        }
        return newBitmap;
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
