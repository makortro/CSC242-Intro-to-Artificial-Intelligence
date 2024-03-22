/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

import java.util.HashSet;
import java.util.Set;

public class Clause {
    private Set<Literal> literals;

    /* CONSTRUCTORS */
    public Clause() {
        literals = new HashSet<Literal>();
    }

    public Clause(Set<Literal> l) {
        this.literals = l;
    }

    /* ACCESSORS */
    public Set<Literal> getLiterals() {
        return literals;
    }

    /* MUTATORS */
    public void addLiteral(Literal l) {
        literals.add(l);
    }

    public int size(){
        return literals.size();
    }

    /* MISC FUNCTS */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Literal l : this.literals) {
            sb.append(l.toString());
            sb.append(", ");
        }
        if(!literals.isEmpty()) { // remove extra ", " at end
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}