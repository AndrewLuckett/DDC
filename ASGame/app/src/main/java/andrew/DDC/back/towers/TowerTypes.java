package andrew.DDC.back.towers;

import andrew.DDC.R;

public enum TowerTypes {
    Base(100, 1, R.drawable.tt_nothing),
    Basic(70, 2, R.drawable.tt_basic),
    Gauss(120, 3, R.drawable.tt_gauss),
    Radar(80, 4, R.drawable.tt_radar),
    Aa(80, 5, R.drawable.tt_aa);

    public final int cost;
    public final int hp;
    public final int GId;

    TowerTypes(int cost, int hp, int GId) {
        this.cost = cost;
        this.hp = hp;
        this.GId = GId;
    }

    public int getHp() {
        return hp;
    }

    public int getCost() {
        return cost;
    }

    public int getGId(){
        return GId;
    }
}
