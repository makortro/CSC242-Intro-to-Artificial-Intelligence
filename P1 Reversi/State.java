public class State {
    public char board[][];
    public int weightedBoard_4x4[][] = {{30,-7,-7,30},
                                    {-7,10,10,-7},
                                    {-7,10,10,-7},
                                    {30,-7,-7,30}};
    public int weightedBoard_8x8[][] = {{},{},{},{},{},{},{},{}};
    public int turn; //since human always goes first their turn will be odd num (starting at 1); comp will be even num

    /* CONSTRUCTORS */
    public State(int size) {
        this.turn = 1;
        // Initialize boards
            /* 4x4
            *    0 1 2 3 <y
            * 0  _ _ _ _
            * 1  _ B W _
            * 2  _ W B _
            * 3  _ _ _ _
            * ^x
            */
        if(size == 4) {
            this.board = new char[4][4];
            this.board[1][1] = 'B';
            this.board[1][2] = 'W';
            this.board[2][1] = 'W';
            this.board[2][2] = 'B';
        }
        /* 8x8
            *    0 1 2 3 4 5 6 7 <y
            * 0  _ _ _ _ _ _ _ _
            * 1  _ _ _ _ _ _ _ _
            * 2  _ _ _ _ _ _ _ _
            * 3  _ _ _ B W _ _ _
            * 4  _ _ _ W B _ _ _
            * 5  _ _ _ _ _ _ _ _
            * 6  _ _ _ _ _ _ _ _
            * 7  _ _ _ _ _ _ _ _
            * ^x
            */
        if(size == 8) {
            this.turn = 1;
            board = new char[8][8];
            this.board[3][3] = 'B';
            this.board[3][4] = 'W';
            this.board[4][3] = 'W';
            this.board[4][4] = 'B';
        }
    }

    /* ACCESSORS */
    public char[][] getBoard() {
        return this.board;
    }
    public char getCurrentPlayerID() {
        if(turn % 2 == 0) { //even count, comp's turn
            return 'W';
        } else {
            return 'B';
        }
    }
    public char getOpponentID() {
        if(getCurrentPlayerID() == 'B') {
            return 'W';
        } else {
            return 'B';
        }
    }
    
    /* MUTATORS */
    public void nextTurn() {
        this.turn++;
    }

    /* OTHER FUNCTIONS */
    public char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][board.length];
        for (int i = 0, j = 0; i < 4 && j < 4; i++, j++) {
            copy[i][j] = board[i][j];
        }
        return copy;
    }

    public void printBoard() {
        if(board.length == 4) {
            for (int i = 0; i < this.board.length; i++) {
                if(i == 0) {
                    System.out.println("  0 1 2 3");
                }
                System.out.print(i + " ");
                for (int j = 0; j < this.board.length; j++) {
                    System.out.print(this.board[i][j] == 0 ? '-' : this.board[i][j]);
                    System.out.print(' ');
                }
                System.out.println();
            }
        }
        if(board.length == 8) {
            for (int i = 0; i < this.board.length; i++) {
                if(i == 0) {
                    System.out.println("  0 1 2 3 4 5 6 7");
                }
                System.out.print(i + " ");
                for (int j = 0; j < this.board.length; j++) {
                    System.out.print(this.board[i][j] == 0 ? '-' : this.board[i][j]);
                    System.out.print(' ');
                }
                System.out.println();
            }
        }
    }
}