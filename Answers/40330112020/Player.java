import java.util.Scanner;

public class Player {
    protected final String name;
    protected final Grid grid = new Grid();
    protected final Grid trackingGrid = new Grid();
    protected int rockets = 0;

    public Player(String name) { this.name = name; }

    public void initializePlayer() {
        grid.initialize();
        trackingGrid.initialize();
        grid.placeShips();
        grid.placeSpecialItems();
    }

    public void takeTurn(Player opponent) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next().toUpperCase();

        if (input.length() == 3 && input.charAt(2) == 'R') {
            int r = Character.getNumericValue(input.charAt(1));
            int c = input.charAt(0) - 'A';
            if (rockets > 0) {
                useRocket(opponent, r, c);
                rockets--;
                return;
            } else {
                System.out.println("No rockets available.");
                return;
            }
        }

        if (input.length() != 2) {
            System.out.println("Invalid input.");
            return;
        }

        int row = Character.getNumericValue(input.charAt(1));
        int col = input.charAt(0) - 'A';

        if (opponent.grid.isShotAt(row, col)) {
            System.out.println("Already shot!");
            return;
        }

        char target = opponent.grid.getCell(row, col);

        switch (target) {
            case Grid.SHIP:
                System.out.println("Hit!");
                opponent.grid.shoot(row, col);
                trackingGrid.mark(row, col, true);
                break;
            case Grid.TREASURE:
                System.out.println("Treasure found! +1 Rocket");
                rockets++;
                opponent.grid.shoot(row, col);
                trackingGrid.mark(row, col, false);
                break;
            case Grid.RADAR:
                System.out.println("Radar hit! Scanning...");
                opponent.grid.shoot(row, col);
                trackingGrid.mark(row, col, false);
                scanAndMarkRadar(opponent, row, col);
                break;
            default:
                System.out.println("Miss!");
                opponent.grid.shoot(row, col);
                trackingGrid.mark(row, col, false);
        }
    }

    public void scanAndMarkRadar(Player opponent, int centerRow, int centerCol) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int r = centerRow + i;
                int c = centerCol + j;
                if (r >= 0 && r < Grid.SIZE && c >= 0 && c < Grid.SIZE) {
                    char cell = opponent.grid.getCell(r, c);
                    if (cell == Grid.SHIP) trackingGrid.markRadar(r, c, 'S');
                    if (cell == Grid.TREASURE) trackingGrid.markRadar(r, c, 'T');
                }
            }
        }
    }

    public void useRocket(Player opponent, int centerRow, int centerCol) {
        System.out.println("Launching rocket at " + (char)(centerCol + 'A') + centerRow);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = centerRow + i;
                int c = centerCol + j;
                if (r >= 0 && r < Grid.SIZE && c >= 0 && c < Grid.SIZE) {
                    if (!opponent.grid.isShotAt(r, c)) {
                        char cell = opponent.grid.getCell(r, c);
                        boolean hit = opponent.grid.shoot(r, c);
                        trackingGrid.mark(r, c, hit);
                        if (cell == Grid.RADAR) scanAndMarkRadar(opponent, r, c);
                        if (cell == Grid.TREASURE) {
                            System.out.println("Rocket found a treasure! +1 rocket");
                            rockets++;
                        }
                    }
                }
            }
        }
    }

    public String getName() { return name; }
    public Grid getGrid() { return grid; }
    public Grid getTrackingGrid() { return trackingGrid; }
    public int getRockets() { return rockets; }
}