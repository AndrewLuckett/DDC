package andrew.DDC.core;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class GameThread extends Thread {
    private Handler mHandler; //Message bus

    private ArrayBlockingQueue<Point> inputQueue = new ArrayBlockingQueue<>(32);
    //Input presses, queue for thread safety

    private volatile ArrayList<Drawable> db = new ArrayList<>();
    //Render array, warning shared. Not thread safe.

    private volatile boolean safeToDraw = false;
    //Is it safe to ask arenaView to draw again
    //If not respected flickering will occur at best
    //Concurrent modification errors at worst

    private float fps;
    //Temp

    private GameInterface theGame;


    public GameThread(GameInterface theGame, Handler mHandler) {
        this.theGame = theGame;
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        long lastDraw = 0;

        while (theGame.isRunning()){
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
            theGame.update(dtms);

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
        sendDoneMessage();
    }

    private void sendDoneMessage() {
        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("mType", MessageTypes.finished);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }

    private void handleInput(Point p) {
        Bundle b = theGame.handleInput(p);
        if(b == null){
            return;
        }
        b.putSerializable("mType", MessageTypes.selection);

        Message msg = mHandler.obtainMessage();
        msg.setData(b);
        mHandler.sendMessage(msg);
    }

    private void createDrawable() {
        //rot += 0.75f;
        db = new ArrayList<>();
        theGame.forEachDrawable(new Consumer<Drawable>() {
            @Override
            public void accept(Drawable d) {
                db.add(d);
            }
        });
        //Remember towers use tl corner other ents use centre!

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
        //return theGame.getScore();
        return (int) fps;
    }

    public int getCoins() {
        //return theGame.getCoins();
        return db.size();
    }
}
