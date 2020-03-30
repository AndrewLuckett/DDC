package andrew.DDC.front;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import andrew.DDC.R;
import andrew.DDC.back.Drawable;
import andrew.DDC.back.Vec2;

public class ArenaView extends View {

    Paint mPaint = new Paint();
    float scale = 1;
    int arenaWidth = 10, arenaHeight = 10;
    Vec2 offset = new Vec2(0, 0);
    Matrix gent = new Matrix();

    ArrayList<Drawable> stuff = new ArrayList<Drawable>();
    private RectF outline;


    public ArenaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(10, 10); //Just in case
    }

    public ArenaView(Context context) {
        super(context);
        setup(10, 10); //Just in case
    }

    public void setup(int arenaWidth, int arenaHeight) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;

        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);


        stuff.add(new Drawable(R.drawable.tt_aa, 60, 0, 0, true));
        stuff.add(new Drawable(R.drawable.tt_basic, 12, 1, 1, true));
        stuff.add(new Drawable(R.drawable.tt_gauss, 99, 8, 8, true));
        stuff.add(new Drawable(R.drawable.tt_radar, 18, 9, 9, true));

        onSizeChanged(getWidth(), getHeight(), 0, 0); //Just in case
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = offset.getX() + scale / 2;
        float y = offset.getY() + scale / 2;
        canvas.drawRect(x, y, getWidth() - x, getHeight() - y, mPaint);

        for (Drawable d : stuff) {
            gent.setScale(scale / 500f, scale / 500f); //why div 500?! when image is 400 square
            //Stuff like that reminds me why I hate ui stuff
            gent.postTranslate((d.getXoff() + 1) * scale, (d.getYoff() + 1) * scale);
            gent.postTranslate(offset.getX(), offset.getY());

            if (d.isBaseRequired()) {
                canvas.drawBitmap(drawableToBitmap(R.drawable.tt_base), gent, null);
            }
            gent.preRotate(d.getRotation(),250,250);
            canvas.drawBitmap(drawableToBitmap(d.getGId()), gent, null);
        }
    }


    public Bitmap drawableToBitmap(int id) {
        return BitmapFactory.decodeResource(getContext().getResources(), id);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float mi = Math.min(w, h);
        float ma = Math.max(w, h);

        if (w > h) {
            offset = new Vec2((ma - mi) / 2, 0);
            scale = mi / (arenaHeight + 2);
        } else {
            offset = new Vec2(0, (ma - mi) / 2);
            scale = mi / (arenaWidth + 2);
        }
        mPaint.setStrokeWidth(scale);
    }
}