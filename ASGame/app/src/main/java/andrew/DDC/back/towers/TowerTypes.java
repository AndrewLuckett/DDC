package andrew.DDC.back.towers;

public enum TowerTypes {
    Base(100, 1),
    Basic(70, 2),
    Gauss(120, 3),
    Radar(80, 4),
    Aa(80, 5);

    public final int cost;
    public final int hp;

    TowerTypes(int cost, int hp) {
        this.cost = cost;
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getCost() {
        return cost;
    }
}
