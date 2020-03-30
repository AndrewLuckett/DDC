package andrew.DDC.back;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import andrew.DDC.R;


public class GameThread extends Thread {
    private final int width;
    private final int height;
    private Handler mHandler;
    ArrayBlockingQueue<Point> queue = new ArrayBlockingQueue<>(32);
    ArrayList<Drawable> d = new ArrayList<>();

    static final Integer sync = 1;
    long lastFrame = System.nanoTime();

    private float rot = 0;

    public GameThread(int width, int height, Handler mHandler) {
        this.width = width;
        this.height = height;
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        while (true) {
            //Handle timings
            long now = System.nanoTime();
            float dtms = (now-lastFrame)/1000000f;
            Log.v("Fps","dtms = "+ dtms);
            Log.v("Fps","fps = "+ 1000f/dtms);

            lastFrame = now;


            //Handle input
            if(!queue.isEmpty()){
                Point p = null;
                try {
                    p = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(p != null){
                    Message msg = mHandler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putSerializable("mType",MessageTypes.Selection);
                    b.putSerializable("type",TowerTypes.Radar);
                    b.putInt("hp", 999);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                }
            }


            //Do stuff
            //rot += 0.75f;
            rot += dtms/16f;
            if(rot > 360){
                rot -= 360;
            }

            //Draw
            dummydp();

            //Sleep
            now = System.nanoTime();
            dtms = (now-lastFrame)/1000000f; //Time for this frame
            try {
                Thread.sleep((long) Math.max(13-dtms,0)); //Keep us around 75 fps
                //Don't have to wait on front side this way
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void dummydp(){
        //rot += 0.75f;
        d = new ArrayList<>();
        for(int i =0; i<height;i++){
            for(int j =0; j<width;j++){
                d.add(new Drawable(R.drawable.tt_gauss,rot,j,i,true));
            }
        }



        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("mType", MessageTypes.update);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }

    public void addClickEvent(Point point) {
        queue.add(point);
    }

    public ArrayList<Drawable> getDrawables() {
        return d;
    }
}
