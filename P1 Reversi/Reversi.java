import java.util.ArrayList;
import java.util.Scanner;

public class Reversi {
    public static void main(String[] args) {
        // Human will always go first in our game, so human is always B, comp is always W
        Game game;
        char currentPlayer;
        int num, row, col;
        ourNode humanMove; 
        ArrayList<ourNode> possActions = new ArrayList<ourNode>();
        ArrayList<ourNode> myPieces = new ArrayList<ourNode>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Get size of board
            System.out.println("Choose which game you'd lke to play: ");
            System.out.println("1. 4x4");
            System.out.println("2. 8x8");
            System.out.print("Pick 1 or 2: ");
            num = scanner.nextInt();
            if(num == 1) {
                game = new Game(4);
            } else if (num == 2) {
                game = new Game(8);
            } else {
                System.out.println("Invalid input, please try again.");
                continue;
            }

            // Print board, get next move
            currentPlayer = game.state.getCurrentPlayerID();
            game.printBoard();
            System.out.println("Player " + currentPlayer + ", enter your move (row column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            humanMove = new ourNode(row, col, currentPlayer);

            // Get all possible actions
            myPieces.addAll(game.getMyPieces()); //get player's pieces --WORKING
            System.out.println("my pieces size " + myPieces.size());
            for(int i = 0; i < myPieces.size(); i++) { //get legal moves from each piece on board
                ourNode temp = myPieces.get(i);
                System.err.print("temp.x:" + temp.getX() + " ");
                System.err.print("temp.y:" + temp.getY() + " ");
                System.err.print("temp.val:" + temp.getValue() + " ");
                System.err.println("oppID:" + game.state.getOpponentID());
                possActions.addAll(game.getLegalMoves(temp.getX(), temp.getY(), game.state.board, game.state.getOpponentID()));
                System.out.print("possActions x:" + possActions.get(0).x + "y:" + possActions.get(0).y + "val:" +possActions.get(0).value + "; ");
                System.out.println("possActions x:" + possActions.get(1).x + "y:" + possActions.get(1).y + "val:" +possActions.get(1).value);
            }
            // Update pieces, score and board if legal move chosen
            if(possActions.contains(humanMove)) {
                // Flip pieces, update score, new board
                game.state.board = game.needsFlipped(row, col, game.state.getBoard(), game.state.getOpponentID());
            } else {
                System.out.println("Illegal move, please try again.");
                continue;
            }
            game.state.nextTurn();
            if(game.gameOver == false) {
                break;
            }
        }
        scanner.close();
    } 
}