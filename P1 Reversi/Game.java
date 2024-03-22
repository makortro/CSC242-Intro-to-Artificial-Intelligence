import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Game {
    public State state;
    public Player human;
    public Player computer;
    public ArrayList<ourNode> legalMoves;
    public boolean gameOver;
    
    /* CONSTRUCTORS */
    public Game(int x) {
        this.state = new State(x);
        this.human = new Player('B');
        this.computer = new Player('W');
        this.legalMoves = new ArrayList<ourNode>();
        this.gameOver = false;
    }

    /* ACCESSORS */
    // NEED ADD: best moves (utlitity)

    /**
     * Loops through game board looking for current players' pieces. Once a piece is found
     * a node is created with the coordinates + proper ID, and added to arraylist to
     * return for further use.
     * @return
     */
    public ArrayList<ourNode> getMyPieces() {
        ArrayList<ourNode> myPieces = new ArrayList<ourNode>();
        char myID = this.state.getCurrentPlayerID();
        for(int i = 0; i < this.state.board.length; i++) {
            for(int j = 0; j < this.state.board.length; j++) {
                if(this.state.board[i][j] == myID) { //found matching piece
                    myPieces.add(new ourNode(i, j, myID));
                }
            }
        }
        return myPieces;
    }
    
    /**
     * Checks board for every legal move current player can make.
     * Searches the board in all 8 directions.
     * 
     * @param x - x coordinate
     * @param y - y coordinate
     * @param board - current game board 
     * @param opp - opponent's ID
     * @return array list of legal moves from current state
     */
    public ArrayList<ourNode> getLegalMoves(int x, int y, char[][] board, char opp) {
        char temp, tempNext;
        ourNode newNode;
        
        //check north -> (x-1,y)
        if(x != 0) {
            for (int i = (x-1); i >= 0; i--) {
                temp = board[i][y];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i-1) > 0) {
                        //check if next is null
                        tempNext = board[i-1][y];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            //newNode = new ourNode(i-1,y,tempNext);
                            //legalMoves.add(newNode);
                            legalMoves.add(new ourNode(i-1,y,tempNext));
                        }
                    }
                }
            }
        }
        
        //check northeast -> (x-1,y+1)
        if(x != 0 && y != (state.board.length - 1)) {
            for (int i = (x-1), j = (y+1); i >= 0 && j < 4; i--, j++) {
                temp = board[i][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i-1) > 0 && (j+1) < 4) {
                        //check if next is null
                        tempNext = board[i-1][j+1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(i-1,j+1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check east -> (x,y+1)
        if(y != (state.board.length - 1)) {
            for (int j = (y+1); j < 4; j++) {
                temp = board[x][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((j+1) < 4) {
                        //check if next is null
                        tempNext = board[x][j+1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(x,j+1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check southeast -> (x+1,y+1)
        if(x != (state.board.length - 1) && y != (state.board.length - 1)) {
            for (int i = (x+1), j = (y+1); i < 4 && j < 4; i++, j++) {
                temp = board[i][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i+1) < 4 && (j+1) < 4) {
                        //check if next is null
                        tempNext = board[i+1][j+1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(i+1,j+1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check south -> (x+1,y)
        if(x != (state.board.length - 1)) {
            for (int i = (x+1); i < 4; i++) {
                temp = board[i][y];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i+1) < 4) {
                        //check if next is null
                        tempNext = board[i+1][y];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(i+1,y,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check southwest -> (x+1,y-1)
        if(x != (state.board.length - 1) && y != 0) {
            for (int i = (x+1), j = (y-1); i < 4 && j >= 0; i++, j--) {
                temp = board[i][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i+1) < 4 && (j-1) > 0) {
                        //check if next is null
                        tempNext = board[i+1][j-1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(i+1,j-1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check west -> (x,y-1)
        if(y != 0) {
            for (int j = (y-1); j >= 0; j--) {
                temp = board[x][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((j-1) > 0) {
                        //check if next is null
                        tempNext = board[x][j-1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(x,j-1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        //check northwest -> (x-1,y-1)
        if(x != (state.board.length - 1) && y != (state.board.length - 1)) {
            for (int i = (x-1), j = (y-1); i >= 0 && j >= 0; i--, j--) {
                temp = board[i][j];
                if(temp == opp) { //opponent piece found
                    //check if next is in bounds
                    if((i-1) > 0 && (j-1) > 0) {
                        //check if next is null
                        tempNext = board[i-1][j-1];
                        if(tempNext == '\0') { //add legal move coordinates to list
                            newNode = new ourNode(i-1,j-1,tempNext);
                            legalMoves.add(newNode);
                        }
                    }
                }
            }
        }
        
        System.out.println("isLegalMoves empty? " + legalMoves.isEmpty());
        return legalMoves;
    }

    /* MUTATORS */

    /**
     * Makes copy of current board, then flips pieces according to current players'
     * move, in every direction, on new board. Keeps track of how many pieces were
     * flipped to update each player's score in the end. Returns the new board.
     * 
     * @param x - x coordinate of move
     * @param y - y coordinate of move
     * @param board - current game board
     * @param opp - opponent's ID
     * @return new game board
     */
    public char[][] needsFlipped(int x, int y, char board[][], char opp) {
        char newBoard[][] = state.copyBoard(board);
        int flipCount = 0;
        char currPlayer;
        if(state.turn % 2 == 0) { //comp is even int; human is odd int
            currPlayer = 'W';
        } else {
            currPlayer = 'B';
        }
        //check north -> (x-1,y)
        for (int i = (x-1); i >= 0; i--) {
            if(board[i][y] == opp) { //opponent piece found
                newBoard[i][y] = currPlayer;
                flipCount++;
            }
        }
        //check northeast -> (x-1,y+1)
        for (int i = (x-1), j = (y+1); i >= 0 && j < 4; i--, j++) {
            if(board[i][j] == opp) { //opponent piece found
                newBoard[i][j] = currPlayer;
                flipCount++;
            }
        }
        //check east -> (x,y+1)
        for (int j = (y+1); j < 4; j++) {
            if(board[x][j] == opp) { //opponent piece found
                newBoard[x][j] = currPlayer;
                flipCount++;
            }
        }
        //check southeast -> (x+1,y+1)
        for (int i = (x+1), j = (y+1); i < 4 && j < 4; i++, j++) {
            if(board[i][j] == opp) { //opponent piece found
                newBoard[i][j] = currPlayer;
                flipCount++;
            }
        }
        //check south -> (x+1,y)
        for (int i = (x+1); i < 4; i++) {
            if(board[i][y] == opp) { //opponent piece found
                newBoard[i][y] = currPlayer;
                flipCount++;
            }
        }
        //check southwest -> (x+1,y-1)
        for (int i = (x+1), j = (y-1); i < 4 && j >= 0; i++, j--) {
            if(board[i][j] == opp) { //opponent piece found
                newBoard[i][j] = currPlayer;
                flipCount++;
            }
        }
        //check west -> (x,y-1)
        for (int j = (y-1); j >= 0; j--) {
            if(board[x][j] == opp) { //opponent piece found
                newBoard[x][j] = currPlayer;
                flipCount++;
            }
        }
        //check northwest -> (x-1,y-1)
        for (int i = (x-1), j = (y-1); i >= 0 && j >= 0; i--, j--) {
            if(board[i][j] == opp) { //opponent piece found
                newBoard[i][j] = currPlayer;
                flipCount++;
            }
        }
        //update scores
        if(currPlayer == 'W') {
            computer.score += flipCount;
            human.score -= flipCount;
        } else {
            human.score += flipCount;
            computer.score -= flipCount;
        }
        return newBoard;
    }

    /* OTHER FUNCTTIONS */
    public void printBoard() {
        this.state.printBoard();
    }


    public ourNode minimax(State s) {
        
        ArrayList<Integer> values = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        TreeMap<ArrayList, Integer> tm = new TreeMap<ArrayList, Integer>();

        char[][] currentBoard = s.getBoard();
        int turn = s.turn;
        char player;

        // Even turn means computer is going, update char
        if(turn % 2 == 0) {
            player = 'W';

        } else {
            player = 'B';
        }

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(currentBoard[i][j] == player) {
                }
            }
        }
        
        

        ourNode node = new ourNode();
        return node;
    }
}