public class Game {
    private final Player p1, p2;
    private boolean turn = true;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void start() {
        p1.initializePlayer();
        p2.initializePlayer();
        while (!p1.getGrid().allShipsSunk() && !p2.getGrid().allShipsSunk()) {
            Player current = turn ? p1 : p2;
            Player opponent = turn ? p2 : p1;
            System.out.println("==========================");
            System.out.println(current.getName() + "'s Turn | Rockets: " + current.getRockets());
            current.getTrackingGrid().printStats(opponent.getGrid());
            current.getTrackingGrid().print();
            current.takeTurn(opponent);
            turn = !turn;
        }
        System.out.println("Game Over! Winner: " + (!turn ? p1.getName() : p2.getName()));
    }
}