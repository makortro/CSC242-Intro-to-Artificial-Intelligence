import java.util.Set;

/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

public class Literal {
    private Integer symbol;
    private Boolean negated;

    /* CONSTRUCTORS */
    public Literal(Integer s, Boolean n) {
        this.symbol = s;
        this.negated = n;
    }

    /* ACCESSORS */
    public Integer getSymbol() {
        return this.symbol;
    }

    public Boolean isNegated() {
        return this.negated;
    }

    public Boolean isTrue(Set<Literal> m) {
        Boolean val = m.contains(this);
        if(this.isNegated()) {
            val = !val;
        }
        return val;
    }

    /* MUTATOR */

    /* MISC FUNCTS */
    @Override
    public String toString() {
        String s = String.valueOf(symbol);
        if(negated) {
            return "-" + s;
        } else {
            return s;
        }
    }
}