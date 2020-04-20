package andrew.DDC.game.back.creeps;

import android.util.Log;

import java.util.ArrayList;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.GameObjectInterface;
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
        float size = (float) container.getSize();
        Vec2 closest;
        float closestDist;

        //Left wall
        closest = new Vec2(-0.5f, pos.getY());
        closestDist = pos.getX() + 0.5f;


        if (pos.getY() < size / 2) { //Top wall
            if (pos.getY() + 0.5f < closestDist) {
                closest = new Vec2(pos.getX(), -0.5f);
                closestDist = pos.getY() + 0.5f;
            }
        } else { //Bottom wall
            if (size - pos.getY() +0.5f < closestDist) {
                closest = new Vec2(pos.getX(), size + 0.5f);
                closestDist = size - pos.getY() + 0.5f;
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
            }
        }


        double b = pos.angleTo(closest);
        double dot = getDifference(angle, b); // Difference of two

        if (Math.abs(dot) < 3 && closestDist < 0.8) { // If infront and close
            angle = (float) (b + Math.PI/2f);
        }

        //Log.v("info",pos+" "+b+" "+dot+" " + angle+" "+closestDist);

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
        return new Drawable(R.drawable.cr_basic, angle * TODEGREES + 90, pos);
    }

    @Override
    public float getAngle() {
        return 0;
    }
}
