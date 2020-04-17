package andrew.DDC.game.back.creeps;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.Vec2;

public class StrongCreep extends BasicCreep {
    public StrongCreep(ArenaInterface container, Vec2 pos, float multiplier) {
        super(container, pos, multiplier);
        hp *= 2;
    }

    @Override
    public Drawable getDrawable(){
        return new Drawable(R.drawable.cr_strong, 90, pos);
    }
}
