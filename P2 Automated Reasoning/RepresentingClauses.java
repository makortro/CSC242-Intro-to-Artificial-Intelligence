/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RepresentingClauses {
    public KB readCNF(KB kb, String file) {
        try {
            File text = new File(file);
            Scanner scan = new Scanner(text);

            // Finds and prints clauses
            while(scan.hasNextLine()) {
                  String data = scan.nextLine();
                  Clause c = new Clause();

                  //c1.setClause(data);

                  char[] chars = data.toCharArray();
                  //int length = chars.length;
                  for(int i = 0; i < chars.length; i++) {

                        // Skip over comments and definition lines
                        if(chars[0] == 'c' || chars[0] == 'p') {
                              break;
                        }

                        // Adding negative numbers
                        if(chars[i] == '-' && Character.isDigit(chars[i + 1])) {

                              //int num = Character.getNumericValue(chars[i + 1]);
                              //int negation = num - (num * 2);

                              //c.clauses.add(negation);
                              Literal l = new Literal(Character.getNumericValue(chars[i+1]), true);
                              c.addLiteral(l);

                              // Skip ahead to the next number
                              i += 2;
                        }

                        // Adding positive numbers
                        if(Character.isDigit(chars[i]) && chars[i] != '0') {
                              //int num = Character.getNumericValue(chars[i]);
                              Literal l = new Literal(Character.getNumericValue(chars[i]), false);
                              c.addLiteral(l);
                        }
                  }
                  if(!c.getLiterals().isEmpty()) {
                        //System.out.println("Clause found: " + c.toString());
                        kb.addClause(c);
                        //System.out.println("KB now contains: " + kb.toString());
                  }
            }
            scan.close();
      } catch (FileNotFoundException e) { // File not found exception
            System.out.println("An error occured.");
            e.printStackTrace();
      }
      return kb;
    }
}