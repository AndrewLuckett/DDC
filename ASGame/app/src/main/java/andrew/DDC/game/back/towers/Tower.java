package andrew.DDC.game.back.towers;

import java.io.Serializable;

import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.CreepTypes;

public class Tower implements GameObjectInterface {
    TowerTypes type;
    boolean expired = false;

    int hp; //Health
    Vec2 pos; //Location

    CreepTypes[] targets; //Things this tower can target
    float range;
    int dmg;
    int shotClDn; //ShotCooldown
    int NeShTi; //Next shot time

    float angle; //Facing direction
    float rot; //Rate of turn

    public Tower(TowerTypes type, Vec2 pos, int hp, CreepTypes[] targets, float range, int dmg, int shotClDn, float angle, float rot){
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

        //TODO stuff
        angle += rot * dtms;
        if(angle > 360){
            angle -= 360;
        }
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(type.getGId(), angle, pos, true);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

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
