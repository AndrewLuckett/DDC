package andrew.DDC.mid;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

    private volatile ArrayList<Drawable> db = new ArrayList<>();
    //Render array, warning shared. Not thread safe.

    private long lastFrame = 0;
    //Time of start of last frame

    private long lastDraw = 0;

    private volatile boolean safeToDraw = false;
    //Is it safe to ask arenaView to draw again

    private float rot = 0;
    private float fps;
    //Temp


    public GameThread(int width, int height, Handler mHandler) {
        this.width = width;
        this.height = height;
        this.mHandler = mHandler;

        //theGame = new ArenaController(width, height);
    }

    @Override
    public void run() {
        lastFrame = System.nanoTime();

        while (true) { //while theGame.running()
            //Handle timings
            long now = System.nanoTime();
            float dtms = (now - lastFrame) / 1000000f; //Whole last frame time
            lastFrame = now;


            //Handle inputs
            while (!inputQueue.isEmpty()) {
                Point p = null;
                try {
                    p = inputQueue.take();
                } catch (InterruptedException e) {
                    Log.v("Warning", "InterruptException on inputQueue");
                }
                if (p != null) {
                    handleInput(p);
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
                createDrawable();
                //Most make more sense. But I figure why bother
                // calculating things if we cant even draw them.

                float dtfd = (now - lastDraw) / 1000000f; //Whole last frame time
                lastDraw = now;

                fps = 1000f / dtfd;

                //Log.v("Fps", "fps = " + 1000f / dtfd);
                //Log.v("Fps", "tps = " + 1000f / dtms);
            }


            //Temporary forced slowdown
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void handleInput(Point p) {
        Bundle b = new Bundle();
        b.putSerializable("mType", MessageTypes.Selection);
        b.putSerializable("type", TowerTypes.Radar);
        b.putInt("hp", 999);
        // Above is placeholder

        // Bundle b = theGame.handleInput(Point p)
        // if(b == null){
        // return;
        // }

        Message msg = mHandler.obtainMessage();
        msg.setData(b);
        mHandler.sendMessage(msg);
    }

    private void createDrawable() {
        //rot += 0.75f;
        db = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                db.add(new Drawable(R.drawable.tt_radar, rot, j, i, true)); //Tower
                db.add(new Drawable(R.drawable.tt_aa, rot, j+0.5f, i+0.5f, false)); //Bug
                db.add(new Drawable(R.drawable.tt_gauss, rot, j+0.5f, i+0.5f, false)); //Proj1
                db.add(new Drawable(R.drawable.tt_basic, 180 + rot, j+0.5f, i+0.5f, false)); //Proj2
                db.add(new Drawable(R.drawable.tt_basic, 360 - rot, j+0.5f, i+0.5f, false)); //Proj3
                //Literal worst case
            }
        }
        //db.add(new Drawable(R.drawable.tt_gauss,rot,1,1,true));
        //db.add(new Drawable(R.drawable.pr_gauss,rot,1.5f,1.5f,false));
        //Remember towers use tl corner other ents use centre!
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

    public int getScore() {
        return (int) fps;
    }

    public int getCoins() {
        return db.size();
    }
}
