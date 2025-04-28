import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Normal Match");
        System.out.println("2. Easy AI Match");
        int choice = scanner.nextInt();

        Player player1 = new Player("Player");
        Player player2 = (choice == 2) ? new AIPlayer("Easy AI") : new Player("Opponent");

        Game game = new Game(player1, player2);
        game.start();
    }
}

// @Pv_McM