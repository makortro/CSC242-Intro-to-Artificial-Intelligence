import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */


 // Class using the WalkSAT algorithm
public class SatisfiabilityTesting {

      /* Returns true if the clause is true under the given variable assignment */
      public static boolean evaluateClause(Clause clause, boolean[] assignment) {
            for (Literal literal : clause.getLiterals()) {
                int variable = Math.abs(literal.getSymbol());
                boolean value = literal.isNegated();
                if (assignment[variable] == value) {
                    return true;
                }
            }
            return false;
        }

        /* Returns true if the Knowledge Base is true under the given variable assignment */
        public static boolean evaluateKB(KB kb, boolean[] assignment) {
            for (Clause clause : kb.getClauses()) {
                if (!evaluateClause(clause, assignment)) {
                    return false;
                }
            }
            return true;
        }


      /* GSAT ALGORITHM IMPLEMENTATION */
      public static boolean GSAT(KB kb, int max_flips, int max_tries) {

            // Get the number of variables
            int numVariables = 0;
            for (Clause clause : kb.getClauses()) {
                  for (Literal literal : clause.getLiterals()) {
                        int variable = Math.abs(literal.getSymbol());
                        if (variable > numVariables) {
                              numVariables = variable;
                        }
                  }
             }

             boolean[] assignment = new boolean[numVariables + 1];
             Random random = new Random();

             for (int i = 0; i < max_flips; i++) {
                  if (evaluateKB(kb, assignment)) {
                      return true;
                  }
      
                  int numTrueClauses = 0;
                  for (Clause clause : kb.getClauses()) {
                      if (evaluateClause(clause, assignment)) {
                          numTrueClauses++;
                      }
                  }
      
                  if (numTrueClauses == 0) {
                      // Random restart w/ Max Tries
                      for (int j = 1; j <= max_tries; j++) {
                          assignment[j] = random.nextBoolean();
                      }
                  } else {
                      Set<Integer> unsatisfiedClauses = new HashSet<>();
                      for (int j = 0; j < kb.getSize(); j++) {
                          if (!evaluateClause(kb.getIndexOf(j), assignment)) {
                              unsatisfiedClauses.add(j);
                          }
                      }
      
                      int clauseIndex = random.nextInt(unsatisfiedClauses.size());
                      Iterator<Integer> it = unsatisfiedClauses.iterator();
                      for (int j = 0; j < clauseIndex; j++) {
                          it.next();
                      }
                      int chosenClause = it.next();
      
                      int numVariablesInClause = kb.getIndexOf(chosenClause).size();
                      int[] variablesInClause = new int[numVariablesInClause];
                      int k = 0;
                      for (Literal literal : kb.getIndexOf(chosenClause).getLiterals()) {
                          variablesInClause[k] = Math.abs(literal.getSymbol());
                          k++;
                      }
      
                      int numFlips = 10;
                      int bestVariable = variablesInClause[0];
                      int bestNumSatisfied = numTrueClauses;
                      for (int j = 0; j < numFlips; j++) {
                          int variable = variablesInClause[random.nextInt(numVariablesInClause)];
                          assignment[variable] = !assignment[variable];
                          int numSatisfied = 0;
                          for (Clause clause : kb.getClauses()) {
                              if (evaluateClause(clause, assignment)) {
                                  numSatisfied++;
                              }
                          }
                          if (numSatisfied > bestNumSatisfied) {
                              bestVariable = variable;
                              bestNumSatisfied = numSatisfied;
                          } else {
                              assignment[variable] = !assignment[variable];
                          }
                      }
                      assignment[bestVariable] = !assignment[bestVariable];
                  }
              }
              return false;
      }

      // Populates the clause
      public static Clause setKB(String data){
            char[] chars = data.toCharArray();
            Clause c = new Clause();
            int length = chars.length;
            for(int i = 0; i < length; i++) {

                  // Skip over comments and definition lines
                  if(chars[0] == 'C' || chars[0] == 'p') {
                        break;
                  }

                  // Adding negative numbers
                  if(chars[i] == '-' && Character.isDigit(chars[i + 1])) {

                        int num = Character.getNumericValue(chars[i + 1]);
                        int negation = num - (num * 2);
                        c.addLiteral(new Literal(negation, true));
                        // Skip ahead to the next number
                        i += 2;
                  }

                  // Adding positive numbers
                  if(Character.isDigit(chars[i]) && chars[i] != '0') {
                        int num = Character.getNumericValue(chars[i]);
                        c.addLiteral(new Literal(num, false));
                  }
            }

            return c;
      }

      public static void main(String[] args) {
            // Problem 1: (x1 ∨ x3 ∨ ¬x4) ∧ (x4) ∧ (x2 ∨ ¬x3)

            // Creating the first clause
            System.out.println("--------------------------------------");
        System.out.println("--------------- PART 3 ---------------");
        System.out.println("------- Satisfiability Testing -------");
        System.out.println("--------------------------------------");
            Clause c1 = new Clause();
            c1.addLiteral(new Literal(1, false));
            c1.addLiteral(new Literal(3, false));
            c1.addLiteral(new Literal(4, true));

            Clause c2 = new Clause();
            c2.addLiteral(new Literal(4, false));

            Clause c3 = new Clause();
            c3.addLiteral(new Literal(2, false));
            c3.addLiteral(new Literal(3, true));

            KB kb = new KB();
            kb.addClause(c1);
            kb.addClause(c2);
            kb.addClause(c3);

            System.out.println("Knowledge base contains: " + kb.toString());
            System.out.println("Now checking satisfiability...");

            if(GSAT(kb, 100, 100)) {
                  System.out.println("Sentence is satisfiable.");

            } else {
                  System.out.println("Sentence is not satisfiable.");
            }
            System.out.println("--------------------------------------");
        System.out.println("------------- END PART 3 -------------");
        System.out.println("--------------------------------------\n\n");
      }
}

/* GSAT ALGORITHM:

      Input: a set of clauses α, MAX-FLIPS, and MAX-TRIES
      Output: a satisfying truth assignment of α, if found
      begin
            for i := 1 to MAX-TRIES
                  T := a randomly generated truth assignment
                  for j := 1 to MAX-FLIPS
                        if T satisfies α then return T;
                        p := a propositional variable such that a change
                              in its truth assignment gives the largest
                              increase in the total number of clauses
                              of α that are satisfied by T;
                        T := T with the truth assignment of p reversed  
                  end for
            end for
            return no satisfying assignment found
      end */
