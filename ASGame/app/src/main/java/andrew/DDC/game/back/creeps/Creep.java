package andrew.DDC.game.back.creeps;

import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;

public abstract class Creep implements GameObjectInterface {
    CreepTypes type;
    int hp;
    Vec2 pos;
    int dmg;
    float velo;
    int bounty;
    int penalty;

    public boolean wasMurdered(){
        return hp <= 0;
    }

    public int getBounty(){
        return bounty;
    }

    public int getPenalty(){
        return penalty;
    }
}
