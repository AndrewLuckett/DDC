package andrew.DDC.game.back.creeps;

import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.Vec2;

public class CreepFactory {
    private CreepFactory() {

    }

    public static Creep getCreep(ArenaInterface container, CreepTypes type, Vec2 pos, float multiplier) {
        switch (type) {
            case Basic:
                return new BasicCreep(container, pos, multiplier);
            case Eater:
                return new EaterCreep(container, pos, multiplier);
            case Fast:
                return new FastCreep(container, pos, multiplier);
            case Flying:
                return new FlyingCreep(container, pos, multiplier);
            case Strong:
                break;
        }
        return null;
    }
}
