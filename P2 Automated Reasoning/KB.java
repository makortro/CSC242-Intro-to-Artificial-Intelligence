/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

import java.util.HashSet;
import java.util.Set;

public class KB {
    private Set<Clause> clauses;
    
    /* CONSTRUCTORS */
    public KB() {
        clauses = new HashSet<Clause>();
    }

    public KB(Set<Clause> c) {
        this.clauses = c;
    }

    /* ACCESSORS */
    public Set<Clause> getClauses() {
        return clauses;
    }

    /* MUTATORS */
    public void addClause(Clause c) {
        clauses.add(c);
    }

    public int getSize(){
        return clauses.size();
    }

    // Returns the element of the set at a given index
    public Clause getIndexOf(int index){
        int n = clauses.size();
        Clause[] clause_array = new Clause[n];
        clause_array = clauses.toArray(clause_array);

        return clause_array[index];
    }
    
    /* MISC FUNCTS */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Clause c : this.clauses) {
            sb.append(c.toString());
            sb.append(", ");
        }
        if(!clauses.isEmpty()) { // remove extra ", " at end
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    public void printKB() {
        toString();
    }
}