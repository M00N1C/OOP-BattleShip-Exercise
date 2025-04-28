import java.util.Arrays;
import java.util.Random;

public class Grid {
    public static final int SIZE = 10;
    public static final char SHIP = 's';
    public static final char RADAR = 'R';
    public static final char TREASURE = 'T';
    public static final char HIT = 'x';
    public static final char MISS = '0';

    private final char[][] grid = new char[SIZE][SIZE];

    public void initialize() {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(grid[i], '~');
    }

    public void placeShips() {
        Random rand = new Random();
        for (int s = 5; s >= 2; s--) {
            boolean placed = false;
            while (!placed) {
                int r = rand.nextInt(SIZE);
                int c = rand.nextInt(SIZE);
                boolean h = rand.nextBoolean();
                if (canPlace(r, c, s, h)) {
                    for (int i = 0; i < s; i++)
                        grid[r + (h ? 0 : i)][c + (h ? i : 0)] = SHIP;
                    placed = true;
                }
            }
        }
    }

    public void placeSpecialItems() {
        Random rand = new Random();
        while (true) {
            int r = rand.nextInt(SIZE), c = rand.nextInt(SIZE);
            if (grid[r][c] == '~') {
                grid[r][c] = RADAR;
                break;
            }
        }
        int placed = 0;
        while (placed < 3) {
            int r = rand.nextInt(SIZE), c = rand.nextInt(SIZE);
            if (grid[r][c] == '~') {
                grid[r][c] = TREASURE;
                placed++;
            }
        }
    }

    public boolean canPlace(int r, int c, int size, boolean h) {
        if ((h && c + size > SIZE) || (!h && r + size > SIZE)) return false;
        for (int i = 0; i < size; i++)
            if (grid[r + (h ? 0 : i)][c + (h ? i : 0)] != '~') return false;
        return true;
    }

    public boolean shoot(int r, int c) {
        if (grid[r][c] == SHIP || grid[r][c] == RADAR || grid[r][c] == TREASURE) {
            grid[r][c] = HIT;
            return true;
        } else {
            grid[r][c] = MISS;
            return false;
        }
    }

    public boolean isShotAt(int r, int c) {
        return grid[r][c] == HIT || grid[r][c] == MISS;
    }

    public char getCell(int r, int c) { return grid[r][c]; }

    public void mark(int r, int c, boolean hit) {
        grid[r][c] = hit ? HIT : MISS;
    }

    public void markRadar(int r, int c, char mark) {
        if (grid[r][c] == '~') grid[r][c] = mark;
    }

    public boolean allShipsSunk() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (grid[i][j] == SHIP) return false;
        return true;
    }

    public void print() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) System.out.print((char)(i + 'A') + " ");
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }

    public void printStats(Grid real) {
        int h = 0, m = 0, r = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (real.grid[i][j] == SHIP) r++;
                if (real.grid[i][j] == HIT) h++;
                if (real.grid[i][j] == MISS) m++;
            }
        System.out.println("Hits: " + h + " | Misses: " + m + " | Remaining: " + r);
    }
}