package andrew.DDC.game.back.towers;

import java.util.HashSet;

import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.CreepTypes;

public class TowerBuilder {
    private ArenaInterface container;
    private TowerTypes type;
    private int hp = 10; //Health
    private Vec2 pos = new Vec2(); //Location

    private HashSet<CreepTypes> targets = new HashSet<>(); //Things this tower can target
    private float range = 1.5f;
    private int dmg = 1;

    private int shotClDn = 1000; //ShotCooldown

    private float angle = 0; //Facing direction
    private float rot = 3.141f; //Rate of turn, 0.25 rps

    public TowerBuilder(ArenaInterface container, TowerTypes type){
        this.container = container;
        this.type = type;
        this.hp = type.getHp();
    }

    public Tower build(){
        return new Tower(container, type, pos, hp, targets, range, dmg, shotClDn, angle, rot);
    }

    public TowerBuilder withHp(int hp){
        this.hp = hp;
        return this;
    }

    public TowerBuilder atPos(Vec2 pos){
        this.pos = pos;
        return this;
    }

    public TowerBuilder withTargets(HashSet<CreepTypes> targets){
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
