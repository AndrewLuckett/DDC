package andrew.DDC.game.back.creeps;

import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;

public abstract class Creep implements GameObjectInterface {
    CreepTypes type;
    int hp;
    Vec2 pos;
    int dmg;
    float velo;
}
