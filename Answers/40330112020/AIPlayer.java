import java.util.Random;

public class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public void takeTurn(Player opponent) {
        if (rockets > 0) {
            for (int i = 0; i < Grid.SIZE; i++) {
                for (int j = 0; j < Grid.SIZE; j++) {
                    char cell = trackingGrid.getCell(i, j);
                    if (cell == 'M') {
                        useRocket(opponent, i, j);
                        rockets--;
                        return;
                    }
                }
            }
            for (int i = 0; i < Grid.SIZE; i++) {
                for (int j = 0; j < Grid.SIZE; j++) {
                    char cell = trackingGrid.getCell(i, j);
                    if (cell == 'S') {
                        useRocket(opponent, i, j);
                        rockets--;
                        return;
                    }
                }
            }
        }

        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(Grid.SIZE);
            c = rand.nextInt(Grid.SIZE);
        } while (opponent.grid.isShotAt(r, c));

        char cell = opponent.grid.getCell(r, c);
        boolean hit = opponent.grid.shoot(r, c);
        trackingGrid.mark(r, c, hit);
        if (cell == Grid.RADAR) scanAndMarkRadar(opponent, r, c);
        if (cell == Grid.TREASURE) rockets++;

        System.out.println("AI fired at " + (char)(c + 'A') + r + (hit ? " and hit!" : " and missed."));
    }
}