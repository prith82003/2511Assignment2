package dungeonmania.entities.buildables;

public class Sceptre extends Buildable {
    private int durability;
    private int mindControlDuration;

    public Sceptre(int durability, int mindControlDuration) {
        super(null);
        this.durability = durability;
        this.mindControlDuration = mindControlDuration;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }
}
