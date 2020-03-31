package andrew.DDC.front;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

import andrew.DDC.R;
import andrew.DDC.back.Drawable;
import andrew.DDC.back.GameThread;
import andrew.DDC.back.Vec2;

public class ArenaView extends View {

    Paint boundaryPaint = new Paint();
    Paint backPaint = new Paint();

    float scale = 1f;
    int arenaWidth = 10, arenaHeight = 10;
    Vec2 offset = new Vec2(0, 0);
    Matrix gent = new Matrix();
    HashMap<Integer, Bitmap> res = new HashMap<>();

    ArrayList<Drawable> stuff = new ArrayList<>();
    private GameThread game;


    public ArenaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(10, 10, null); //Just in case
    }

    public ArenaView(Context context) {
        super(context);
        setup(10, 10, null); //Just in case
    }

    public void setup(int arenaWidth, int arenaHeight, GameThread game) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.game = game;

        boundaryPaint.setAntiAlias(true);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setColor(Color.GRAY);

        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.STROKE);
        backPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_back));

        onSizeChanged(getWidth(), getHeight(), 0, 0); //Just in case
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.setSafeToDraw(false); //In case ui calls a redraw
        drawBounds(canvas);

        for (Drawable d : stuff) {
            gent.setScale(scale / 200f, scale / 200f); //Fixed image size value
            //Only generate 200*200 sized images for this code
            gent.postTranslate((d.getXoff() + 1) * scale, (d.getYoff() + 1) * scale);
            gent.postTranslate(offset.getX(), offset.getY());

            if (d.isBaseRequired()) {
                canvas.drawBitmap(drawableToBitmap(R.drawable.tt_base), gent, null);
                //Log.v("Mat","M: "+ gent);
            }
            gent.preRotate(d.getRotation(), 100, 100);
            canvas.drawBitmap(drawableToBitmap(d.getGId()), gent, null);
        }
        game.setSafeToDraw(true);
    }

    private void drawBounds(Canvas canvas) {
        float x = offset.getX() + scale / 2;
        float y = offset.getY() + scale / 2;
        canvas.drawRect(x, y, getWidth() - x, getHeight() - y, boundaryPaint);

        float ymid = (arenaHeight / 2f) * scale + offset.getY() + scale;
        canvas.drawRect(x, ymid - 1.5f * scale, getWidth() - x, ymid + 1.5f * scale, backPaint);
    }


    public Bitmap drawableToBitmap(int id) {
        if (!res.containsKey(id)) {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inScaled = false;
            res.put(id, BitmapFactory.decodeResource(getContext().getResources(), id, o));
        }
        return res.get(id);
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
        boundaryPaint.setStrokeWidth(scale);
        backPaint.setStrokeWidth(scale);
        //Log.v("Scale","S: "+ scale);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //TODO: Calc what tile was clicked.
            game.addClickEvent(new Point(0, 0));
        }
        return true;
    }

    public void update() {
        stuff = game.getDrawables();
        invalidate();
    }
}