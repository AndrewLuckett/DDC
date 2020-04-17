package andrew.DDC.game.back.creeps;

import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;

public abstract class Creep implements GameObjectInterface {
    ArenaInterface container;
    CreepTypes type;
    int hp;
    Vec2 pos;
    int dmg;
    float velo;
    int bounty;
    int penalty;

    public void shot(int dmg){
        hp -= dmg;
    }

    public boolean isExpired() {
        return pos.getX() > container.getSize() + 1 || pos.getY() > container.getSize() + 1 || hp <= 0;
    }

    public Vec2 getPos() {
        return pos;
    }

    public boolean wasMurdered(){
        return hp <= 0;
    }

    public int getBounty(){
        return bounty;
    }

    public int getPenalty(){
        return penalty;
    }

    public CreepTypes getType(){
        return type;
    }
}
