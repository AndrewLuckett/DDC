package andrew.DDC.game.back.towers;

import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.CreepTypes;

public class Radar extends Tower {

    public Radar(TowerTypes type, Vec2 pos, int hp, CreepTypes[] targets, float range, int dmg, int shotClDn, float angle, float rot) {
        super(type, pos, hp, targets, range, dmg, shotClDn, angle, rot);
    }

    public void update() {

    }
}
