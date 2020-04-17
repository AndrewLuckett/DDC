package andrew.DDC.game.back.creeps;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.Vec2;

public class BasicCreep extends Creep {

    public BasicCreep(ArenaInterface container, Vec2 pos, float multiplier) {
        this.container = container;
        type = CreepTypes.Basic;
        hp = (int) (2 * multiplier);
        dmg = (int) (1 * multiplier);
        this.pos = pos;
        velo = 0.005f;
        bounty = 50;
        penalty = 10;
    }

    @Override
    public void update(float dtms) {
        pos = new Vec2(pos.getX() + velo * dtms, pos.getY());
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(R.drawable.cr_basic, 90, pos);
    }
}
