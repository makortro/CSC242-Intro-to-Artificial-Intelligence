public class Player {
    public char ID;
    public int score;

    /* CONSTRUCTORS */
    public Player() {
        this.ID = '\0';
        this.score = 0;
    }
    public Player(char c) {
        this.ID = c;
        this.score = 0;
    }

    /* ACCESSORS */
    public char getID() {
        return ID;
    }
    public int getScore() {
        return score;
    }

    /* MUTATORS */
    public void setID(char c) {
        this.ID = c;
    }
    public void setScore(int s) {
        this.score = s;
    }
}