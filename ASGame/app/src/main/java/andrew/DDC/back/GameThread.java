package andrew.DDC.back;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import andrew.DDC.R;
import andrew.DDC.back.towers.TowerTypes;


public class GameThread extends Thread {
    private final int width; //Arena width in tiles
    private final int height; //Arena height in tiles
    private Handler mHandler; //Message bus

    private ArrayBlockingQueue<Point> inputQueue = new ArrayBlockingQueue<>(32);
    //Input presses, queue for thread safety

    private ArrayList<Drawable> db = new ArrayList<>();
    //Render array, warning shared. Not thread safe.

    private long lastFrame = System.nanoTime();
    //Time of start of last frame

    private boolean safeToDraw = false;
    //Is it safe to ask arenaView to draw again

    private float rot = 0;
    //Temp


    public GameThread(int width, int height, Handler mHandler) {
        this.width = width;
        this.height = height;
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        while (true) { //while theGame.running()
            //Handle timings
            long now = System.nanoTime();
            float dtms = (now - lastFrame) / 1000000f; //Whole last frame time
            //Log.v("Fps","dtms = "+ dtms);
            //Log.v("Fps","fps = "+ 1000f/dtms);

            lastFrame = now;


            //Handle input
            if (!inputQueue.isEmpty()) {
                Point p = null;
                try {
                    p = inputQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (p != null) {
                    Message msg = mHandler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putSerializable("mType", MessageTypes.Selection);
                    b.putSerializable("type", TowerTypes.Radar);
                    b.putInt("hp", 999);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                }
            }


            //Do stuff
            //theGame.update(dtms)
            rot += dtms / 16f;
            if (rot > 360) {
                rot -= 360;
            }

            //Draw
            if (safeToDraw) { //Other ways to do it
                //Most make more sense. But I figure why bother
                // calculating things if we cant even draw them.
                CreateDrawable();
            }

            //Sleep
            now = System.nanoTime();
            dtms = (now - lastFrame) / 1000000f; //Time for this frame
            try {
                Thread.sleep((long) Math.max(13 - dtms, 0)); //Keep us around 75 fps
                //By making this frame last around 13ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void CreateDrawable() {
        //rot += 0.75f;
        db = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                db.add(new Drawable(R.drawable.tt_gauss, rot, j, i, true));
            }
        }
        //Placeholder testing

        //db = theGame.getDrawables()


        //Ask arenaView to update
        //DO NOT MODIFY RENDER ARRAY UNTIL IT IS DONE
        safeToDraw = false;
        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("mType", MessageTypes.update);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }

    public void addClickEvent(Point point) {
        inputQueue.add(point);
    }

    public ArrayList<Drawable> getDrawables() {
        return db;
    }

    public void setSafeToDraw(boolean safe) {
        safeToDraw = safe;
    }
}
