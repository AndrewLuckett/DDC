package andrew.DDC.game.back;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import androidx.core.util.Consumer;

import java.util.ArrayList;

import andrew.DDC.core.Drawable;
import andrew.DDC.core.GameInterface;
import andrew.DDC.game.back.creeps.Creep;
import andrew.DDC.game.back.towers.Tower;
import andrew.DDC.game.back.towers.TowerBuilder;
import andrew.DDC.game.back.towers.TowerTypes;

public class TdGame implements GameInterface, ArenaInterface {
    private final int size;
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Creep> creeps = new ArrayList<>();
    private ArrayList<GameObjectInterface> proj = new ArrayList<>();

    private int score = 0;
    private int coins;
    private boolean running = true;

    private long nextWaveTime; //ms
    private long nextWavein; //ms

    private TowerTypes selected = TowerTypes.Base;


    public TdGame(int size, boolean hard) {
        this.size = size;
        nextWaveTime = hard ? 2000 : 3000;
        coins = hard ? 400 : 600;
        nextWavein = nextWaveTime * 2;
    }

    @Override
    public void update(float dtms) {
        if(coins < 0){ //Game over, man. Game over!
            running = false;
            return;
        }

        nextWavein -= dtms;
        if(nextWavein <= 0){
            nextWavein = nextWaveTime;

            //Add wave
        }

        for(Creep c:creeps){
            if(c.isExpired()){
                if(c.wasMurdered()){
                    score += c.getBounty();
                } else{
                    coins -= c.getPenalty();
                }
            }
        }

        updateAll(dtms, towers);
        updateAll(dtms, creeps);
        updateAll(dtms, proj);

    }

    private void updateAll(float dtms, ArrayList<? extends GameObjectInterface> goItem) {
        ArrayList<GameObjectInterface> delt = new ArrayList<>();
        for (GameObjectInterface i : goItem) {
            i.update(dtms);
            if(i.isExpired()) delt.add(i);
        }
        for (GameObjectInterface i : delt) {
            goItem.remove(i);
        }
    }

    @Override
    public Bundle handleInput(Point p) {
        Vec2 pos = new Vec2(p.x, p.y);
        for (Tower t : towers) {
            if (t.getPos().equals(pos)) {
                Bundle out = new Bundle();
                out.putSerializable("type", t.getType());
                out.putInt("hp", t.getHp());
                return out;
            }
        }

        //If no tower there, add one
        if(coins >= selected.getCost()) {
            coins -= selected.getCost();
            towers.add(new TowerBuilder(this, selected)
                    .atPos(pos)
                    .build()
            );
        }
        return null;
    }

    @Override
    public void forEachDrawable(Consumer<Drawable> d) {
        for (GameObjectInterface i : towers) {
            d.accept(i.getDrawable());
        }
        for (GameObjectInterface i : creeps) {
            d.accept(i.getDrawable());
        }
        for (GameObjectInterface i : proj) {
            d.accept(i.getDrawable());
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setSelected(TowerTypes tt) {
        selected = tt;
    }

    @Override
    public ArrayList<Creep> getTargets() {
        return creeps;
    }

    @Override
    public ArrayList<? extends GameObjectInterface> getObstacles() {
        return towers;
    }
}
