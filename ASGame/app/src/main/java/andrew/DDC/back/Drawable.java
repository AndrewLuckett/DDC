package andrew.DDC.back;

public class Drawable {
    private int GId;
    private float rotation;
    private float Xoff;
    private float Yoff;
    private boolean tower;

    public Drawable(int GId, float rotation, float Xoff, float Yoff){
        this.GId = GId;
        this.rotation = rotation;
        this.Xoff = Xoff;
        this.Yoff = Yoff;
        this.tower = false;
    }

    public Drawable(int GId, float rotation, float Xoff, float Yoff, boolean base){
        this(GId,rotation,Xoff,Yoff);
        this.tower = base;
    }

    public int getGId() {
        return GId;
    }

    public float getRotation() {
        return rotation;
    }

    public float getXoff() {
        return Xoff;
    }

    public float getYoff() {
        return Yoff;
    }

    public boolean isTower() {
        return tower;
    }
}
