package andrew.DDC.back.creeps;

import andrew.DDC.back.GameObjectInterface;
import andrew.DDC.back.Vec2;

public abstract class Creep implements GameObjectInterface {
    CreepTypes type;
    int hp;
    Vec2 pos;
    int dmg;
    float velo;
}
