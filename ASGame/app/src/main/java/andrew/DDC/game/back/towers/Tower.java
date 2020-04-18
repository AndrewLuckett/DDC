package andrew.DDC.game.back.towers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.Creep;
import andrew.DDC.game.back.creeps.CreepTypes;

public class Tower implements GameObjectInterface {
    ArenaInterface container;
    private TowerTypes type;
    private boolean expired = false;

    private int hp; //Health
    private Vec2 pos; //Location

    private HashSet<CreepTypes> targets; //Things this tower can target
    private float range;
    private int dmg;
    private int shotClDn; //ShotCooldown
    private int NeShTi = 0; //Next shot time

    private float angle; //Facing direction, rads
    private float rot; //Rate of turn, rads per second

    private final float TODEGREES = (float) (180/Math.PI);

    public Tower(ArenaInterface container, TowerTypes type, Vec2 pos, int hp, HashSet<CreepTypes> targets, float range, int dmg, int shotClDn, float angle, float rot){
        this.container = container;
        this.type = type;
        this.pos = pos;
        this.hp = hp;
        this.targets = targets;
        this.range = range;
        this.dmg = dmg;
        this.shotClDn = shotClDn;
        this.angle = angle;
        this.rot = rot;
    }

    @Override
    public void update(float dtms) {
        if(hp <= 0){
            expired = true;
            return;
        }

        if(targets.isEmpty()){
            turn(true, dtms);
            return;
        }

        Vec2 centre = new Vec2(pos.getX() + 0.5f, pos.getY() + 0.5f);
        ArrayList<Creep> creeps = container.getTargets();
        float closestDist = Float.POSITIVE_INFINITY;
        Creep closest = null;
        for(Creep c: creeps){
            float dist = centre.distTo(c.getPos());
            if(dist < closestDist && targets.contains(c.getType())){
                closestDist = dist;
                closest = c;
            }
        }

        NeShTi -= dtms;
        if(closest != null){
            //TODO aim at closest and shoot if available

            if(closestDist < range && NeShTi <= 0) { // && within fov
                NeShTi = shotClDn;
                closest.shot(dmg);
            }
        }
    }

    private void turn(boolean direction, float dtms) {
        int dir = direction ? 1 : -1;
        angle += dir * rot * dtms/1000f;

        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
    }

    @Override
    public void shot(int dmg){
        hp -= dmg;
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(type.getGId(), angle * TODEGREES, pos, true);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public Vec2 getPos(){
        return pos;
    }

    @Override
    public float getAngle() {
        return angle;
    }

    public TowerTypes getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }
}
