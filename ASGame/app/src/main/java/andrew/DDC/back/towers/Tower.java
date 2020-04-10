package andrew.DDC.back.towers;

import andrew.DDC.mid.Drawable;
import andrew.DDC.back.GameObjectInterface;
import andrew.DDC.back.Vec2;
import andrew.DDC.back.creeps.CreepTypes;

public class Tower implements GameObjectInterface {
    TowerTypes type;

    int hp; //Health
    Vec2 pos; //Location

    CreepTypes[] targets; //Things this tower can target
    float range;
    int dmg;
    int shotClDn; //ShotCooldown
    int NeShTi; //Next shot time

    float angle; //Facing direction
    float rot; //Rate of turn

    @Override
    public void update(float dtms) {

    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(type.getGId(), angle, pos, true);
    }
}
