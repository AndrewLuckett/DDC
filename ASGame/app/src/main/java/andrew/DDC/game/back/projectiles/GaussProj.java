package andrew.DDC.game.back.projectiles;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;

public class GaussProj implements GameObjectInterface {
    private GameObjectInterface parent;
    private long expiresIn = 1000;

    private float range = 4;
    private float fov = 30;

    public GaussProj(GameObjectInterface parent){
        this.parent = parent;
    }

    @Override
    public void update(float dtms) {
        expiresIn -= dtms;

        //Check if overlap
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(R.drawable.pr_gauss, parent.getAngle(), parent.getPos());
    }

    @Override
    public boolean isExpired() {
        return parent.isExpired() || expiresIn <= 0;
    }

    @Override
    public Vec2 getPos() {
        return parent.getPos();
    }

    @Override
    public float getAngle() {
        return parent.getAngle();
    }

    @Override
    public void shot(int dmg) {
        //Do Nothing
    }
}
