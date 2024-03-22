public class ourNode {
    public int x;
    public int y;
    public char value; //either 'W' or 'B'
    public int weight; //weight of position in weightedBoard[][]

    /* CONSTRUCTORS */
    public ourNode() {
        this.x = 0;
        this.y = 0;
        this.value = '\0';
        this.weight = 0;
    }
    public ourNode(int x, int y, char val) {
        this.x = y;
        this.y = y;
        this.value = val;   
        this.weight = 0;   
    }
    public ourNode(int x, int y, char val, int w) {
        this.x = y;
        this.y = y;
        this.value = val;   
        this.weight = w;   
    }

    /* ACCESSORS */
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public char getValue() {
        return this.value;
    }
    public int getWeight() {
        return this.weight;
    }
}