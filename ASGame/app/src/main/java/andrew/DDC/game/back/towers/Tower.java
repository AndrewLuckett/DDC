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
    private int NeShTi; //Next shot time

    private float angle; //Facing direction
    private float rot; //Rate of turn

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

        //Temp
        if(closest != null && closestDist < range) {
            closest.shot(dmg);
        }

        //TODO aim at closest and shoot if available
        angle += rot * dtms;
        if(angle > 360){
            angle -= 360;
        }
    }

    @Override
    public void shot(int dmg){
        hp -= dmg;
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(type.getGId(), angle, pos, true);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public Vec2 getPos(){
        return pos;
    }

    public TowerTypes getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }
}
