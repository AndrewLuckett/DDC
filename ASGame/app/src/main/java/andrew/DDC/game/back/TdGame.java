package andrew.DDC.game.back;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import andrew.DDC.core.Drawable;
import andrew.DDC.core.GameInterface;
import andrew.DDC.game.back.creeps.Creep;
import andrew.DDC.game.back.creeps.CreepFactory;
import andrew.DDC.game.back.creeps.CreepTypes;
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


    public TdGame(int size, boolean hard, boolean random) {
        this.size = size;
        nextWaveTime = hard ? 3000 : 4000;
        coins = hard ? 400 : 600;
        nextWavein = nextWaveTime * 2;
        setup(random);
    }

    private void setup(boolean random) {
        TowerBuilder tb = new TowerBuilder(this, TowerTypes.Base);
        tb.withHp(TowerTypes.Base.getHp() * 2);

        if (random) {
            Random r = new Random();
            for (int x = 0; x < size - 1; x++) {
                for (int y = 0; y < size; y++) {
                    float weight = 2 * x / (float) size;
                    weight = (float) Math.min(0.3, weight);
                    if (r.nextFloat() < weight) {
                        tb.atPos(new Vec2(x, y));
                        towers.add(tb.build());
                    }
                }
            }
        } else {
            for (int x = 0; x < size - 1; x++) {
                if (x % 4 == 1) {
                    for (int y = 0; y < size - 4; y++) {
                        tb.atPos(new Vec2(x, y));
                        towers.add(tb.build());
                    }
                } else if (x % 2 == 1) {
                    for (int y = 4; y < size; y++) {
                        tb.atPos(new Vec2(x, y));
                        towers.add(tb.build());
                    }
                }
            }
        }
    }

    @Override
    public void update(float dtms) {
        if (coins < 0) { //Game over, man. Game over!
            running = false;
            return;
        }

        nextWavein -= dtms;
        if (nextWavein <= 0) {
            nextWavein = nextWaveTime;

            creeps.add(CreepFactory.getCreep(this, CreepTypes.Basic, new Vec2(-1, size / 2f), 1f));
            //TODO Add wave
        }

        updateAll(dtms, creeps);
        updateAll(dtms, towers);
        updateAll(dtms, proj);

    }

    private void updateAll(float dtms, ArrayList<? extends GameObjectInterface> goItem) {
        ArrayList<GameObjectInterface> delt = new ArrayList<>();
        for (GameObjectInterface i : goItem) {
            if (i.isExpired()) delt.add(i);
            i.update(dtms);
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

        if (p.x >= size || p.y >= size) return null;
        if (p.x < 0 || p.y < 0) return null;

        //If no tower there, add one
        if (coins >= selected.getCost()) {
            coins -= selected.getCost();

            HashSet<CreepTypes> targets = new HashSet<CreepTypes>();
            TowerBuilder tb = new TowerBuilder(this, selected);
            switch (selected) {
                case Base:
                case Radar:
                    break;
                case Basic:
                case Gauss:
                    targets.add(CreepTypes.Basic);
                    targets.add(CreepTypes.Eater);
                    targets.add(CreepTypes.Fast);
                    targets.add(CreepTypes.Strong);
                    break;
                case Aa:
                    targets.add(CreepTypes.Flying);
                    break;
            }

            switch (selected) { //TODO Range dmg shtcldn rot
                case Base:
                    break;
                case Basic:
                    tb.withRange(2);
                    break;
                case Gauss:
                    tb.withRange(2);
                    tb.withDmg(2);
                    break;
                case Radar:

                    break;
                case Aa:
                    break;
            }

            tb.atPos(pos);
            tb.withTargets(targets);
            towers.add(tb.build());
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

    @Override
    public void setSelected(TowerTypes tt) {
        selected = tt;
    }

    @Override
    public void addProjectile(GameObjectInterface proj) {
        this.proj.add(proj);
    }

    @Override
    public void addCoins(int delta) {
        coins += delta;
    }

    @Override
    public void addScore(int delta) {
        score += delta;
    }

    @Override
    public int getSize() {
        return size;
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
