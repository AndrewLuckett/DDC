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
    int shotClDn = 500;
    float velo;
    int bounty;
    int penalty;
    float angle = 0;
    int nextShotIn = 0;

    public final float TODEGREES = (float) (180 / Math.PI);

    public void shot(int dmg) {
        hp -= dmg;
    }

    public boolean isExpired() {
        if (hp <= 0) {
            container.addCoins(bounty);
            container.addScore(bounty);
            return true;
        }
        if (pos.getX() > container.getSize() + 1 || pos.getY() > container.getSize() + 1) {
            container.addCoins(-(penalty + dmg));
            return true;
        }
        return false;
    }

    public Vec2 getPos() {
        return pos;
    }

    public CreepTypes getType() {
        return type;
    }
}
