package alexis.breuse.domain.player;

public class Player {
    private final String name;
    private final PlayerTag tag;

    public Player(String name, PlayerTag tag) {
        this.name = name;
        this.tag = tag;
    }

    public boolean isPlayerOne() {
        return tag == PlayerTag.PLAYER_ONE;
    }

    public boolean isPlayerTwo() {
        return tag == PlayerTag.PLAYER_TWO;
    }

    public String getName() {
        return name;
    }
}
