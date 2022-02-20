package andrew.DDC.game.front;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;

import andrew.DDC.R;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.core.Drawable;
import andrew.DDC.core.GameThread;
import andrew.DDC.core.GameViewInterface;

public class ArenaView extends View implements GameViewInterface {

    private Paint boundaryPaint = new Paint();  //Boundary
    private Paint backPaint = new Paint(); //Unpaint boundary
    private Paint tp = new Paint(); //Text paint
    private Paint tpr = new Paint(); //Text paint (right weighted)

    float scale = 1f;
    int arenaSize = 10;
    Vec2 offset = new Vec2(0, 0);
    Matrix canMat = new Matrix();
    Matrix dMat = new Matrix();
    HashMap<Integer, Bitmap> res = new HashMap<>();

    private ArrayList<Drawable> stuff = new ArrayList<>();
    private int score;
    private int coins;

    private GameThread game;


    public ArenaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(10, null); //Just in case
    }


    public void setup(int arenaSize, GameThread game) {
        this.arenaSize = arenaSize;
        this.game = game;

        boundaryPaint.setAntiAlias(true);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setColor(Color.GRAY);

        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.STROKE);
        backPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_back));

        Typeface tf = ResourcesCompat.getFont(getContext(), R.font.lavi);
        tp.setAntiAlias(true);
        tp.setTypeface(tf);

        tpr = new Paint(tp);
        tpr.setTextAlign(Paint.Align.RIGHT);

        onSizeChanged(getWidth(), getHeight(), 0, 0); //Just in case
    }


    public void update() {
        stuff = game.getDrawables();
        score = (int) game.getValue("score");
        coins = (int) game.getValue("coins");
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.setSafeToDraw(false); //In case ui calls a redraw
        drawBounds(canvas);

        Bitmap tow = drawableToBitmap(R.drawable.tt_base); //Preload

        canvas.setMatrix(canMat);

        for (Drawable d : stuff) {
            dMat.setTranslate(d.getXoff() * 200, d.getYoff() * 200); //Locate image

            Bitmap out = drawableToBitmap(d.getGId());
            float outoff = out.getWidth() / 2f; //All images must be square

            if (d.isTower()) { //If tower draw base
                canvas.drawBitmap(tow, dMat, null); //Draw base
            } else { //If not offset image by half to centre on point
                dMat.postTranslate(-outoff, -outoff);
            }
            dMat.preRotate(d.getRotation(), outoff, outoff); //Rotate about centre
            canvas.drawBitmap(out, dMat, null);

        }

        game.setSafeToDraw(true);
    }

    private void drawBounds(Canvas canvas) {
        float x = offset.getX() + scale / 2;
        float y = offset.getY() + scale / 2;
        canvas.drawRect(x, y, getWidth() - x, getHeight() - y, boundaryPaint);

        y = (arenaSize / 2f) * scale + offset.getY() + scale;
        canvas.drawRect(x, y - 1.5f * scale, getWidth() - x, y + 1.5f * scale, backPaint);

        y = offset.getY() + scale / 2f - tp.ascent() / 2f;
        canvas.drawText("Score: " + score, x, y, tp);
        canvas.drawText("Coins: " + coins, getWidth() - x, y, tpr);
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
        } else {
            offset = new Vec2(0, (ma - mi) / 2);
        }
        scale = mi / (arenaSize + 2);

        boundaryPaint.setStrokeWidth(scale);
        backPaint.setStrokeWidth(scale);
        tp.setTextSize(scale);
        tpr.setTextSize(scale);
        //Log.v("Scale","S: "+ scale);

        canMat.setScale(scale / 200, scale / 200); //Scale image
        canMat.postTranslate(offset.getX() + scale, offset.getY() + scale); //Add main offset
    }


    private int dr = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dr = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                dr++;
                break;
            case MotionEvent.ACTION_UP:
                Log.v("Click", "Moved for " + dr + " events");
                if (dr < 5) {
                    handleClick(ev.getX(), ev.getY());
                }
                break;
        }
        return true;
    }


    public void handleClick(float x, float y) {
        x -= offset.getX() + scale;
        y -= offset.getY() + scale;
        Point loc = new Point( (int) Math.floor(x/scale), (int) Math.floor(y/scale));
        Log.v("info","Tile: "+ loc.toString());
        game.addClickEvent(loc);
    }
}