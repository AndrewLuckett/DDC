package andrew.DDC.game.back.creeps;

import java.util.ArrayList;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.GameObjectInterface;
import andrew.DDC.game.back.Vec2;
import andrew.DDC.game.back.towers.Tower;

public class EaterCreep extends Creep {
    public EaterCreep(ArenaInterface container, Vec2 pos, float multiplier) {
        this.container = container;
        type = CreepTypes.Eater;
        hp = (int) (2 * multiplier);
        dmg = (int) (1 * multiplier);
        this.pos = pos;
        velo = 0.005f;
        bounty = 50;
        penalty = 10;
    }

    @Override
    public void update(float dtms) {
        float size = (float) container.getSize();
        Vec2 closest;
        float closestDist;
        GameObjectInterface closestTower = null;

        //Left wall
        closest = new Vec2(0, pos.getY());
        closestDist = pos.getX();


        if (pos.getY() < size / 2) { //Top wall
            if (pos.getY() < closestDist) {
                closest = new Vec2(pos.getX(), 0);
                closestDist = pos.getY();
            }
        } else { //Bottom wall
            if (size - pos.getY() < closestDist) {
                closest = new Vec2(pos.getX(), size);
                closestDist = size - pos.getY();
            }
        }

        if (pos.getX() < 0) {
            closest = new Vec2(pos.getX() - 1, pos.getY());
            closestDist = 1;
        }


        ArrayList<? extends GameObjectInterface> obj = container.getObstacles();
        for (GameObjectInterface c : obj) {
            Vec2 cpos = c.getPos();
            cpos = new Vec2(cpos.getX() + 0.5f, cpos.getY() + 0.5f);
            float dist = pos.distTo(cpos);
            if (dist < closestDist) {
                closestDist = dist;
                closest = cpos;
                closestTower = c;
            }
        }


        double b = pos.angleTo(closest);
        double dot = getDifference(angle, b); // Difference of two

        nextShotIn -= dtms;

        if (closestTower == null) {
            angle = (float) (b + Math.PI);
        } else if(closestDist > 0.8) {
            angle = (float) b;
        }else{
            angle = (float) b;
            if (nextShotIn <= 0) {
                nextShotIn = shotClDn;
                closestTower.shot(dmg);

            }
            return;
        }


        float newx = pos.getX();
        float newy = pos.getY();
        newx += Math.cos(angle) * velo * dtms;
        newy += Math.sin(angle) * velo * dtms;
        pos = new Vec2(newx, newy);
    }

    private static double getDifference(double b1, double b2) {
        double tau = Math.PI * 2;
        double r = (b2 - b1) % tau;
        if (r < -Math.PI)
            r += tau;
        if (r >= Math.PI)
            r -= tau;

        return r;
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(R.drawable.cr_eater, angle * TODEGREES + 90, pos);
    }

    @Override
    public float getAngle() {
        return 0;
    }
}
