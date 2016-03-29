public class Main{
  public static void main(String[] args){
    Parser parser = new Parser();
    Program p = parser.parse();
    new Show(p);
    System.out.println();
    ContextualAnalyzes t = new ContextualAnalyzes();
    if(t.validate(p))
    	System.out.println("\nStatic Semantics is correct.");
    else
    	System.out.println("\nStatic Semantics is NOT correct.");
  }  
}