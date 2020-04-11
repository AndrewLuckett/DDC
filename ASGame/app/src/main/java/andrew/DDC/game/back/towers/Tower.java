package andrew.DDC.game.back.towers;

import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.creeps.CreepTypes;

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
