package andrew.DDC.game.back.towers;

import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.CreepTypes;

public class TowerBuilder {
    TowerTypes type;
    int hp = 10; //Health
    Vec2 pos = new Vec2(); //Location

    CreepTypes[] targets = {CreepTypes.Basic}; //Things this tower can target
    float range = 1;
    int dmg = 1;
    int shotClDn = 500; //ShotCooldown

    float angle = 0; //Facing direction
    float rot = 0.36f; //Rate of turn, 1 rps

    public TowerBuilder(TowerTypes type){
        this.type = type;
    }

    public Tower build(){
        return new Tower(type, pos, hp, targets, range, dmg, shotClDn, angle, rot);
    }

    public TowerBuilder withHp(int hp){
        this.hp = hp;
        return this;
    }

    public TowerBuilder atPos(Vec2 pos){
        this.pos = pos;
        return this;
    }

    public TowerBuilder withTargets(CreepTypes[] targets){
        this.targets = targets;
        return this;
    }

    public TowerBuilder withRange(float range){
        this.range = range;
        return this;
    }

    public TowerBuilder withDmg(int dmg){
        this.dmg = dmg;
        return this;
    }

    public TowerBuilder withShotClDn(int shotClDn){
        this.shotClDn = shotClDn;
        return this;
    }

    public TowerBuilder withAngle(float angle){
        this.angle = angle;
        return this;
    }

    public TowerBuilder withRot(float rot){
        this.rot = rot;
        return this;
    }
}
