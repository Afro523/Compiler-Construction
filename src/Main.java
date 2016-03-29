public class Main{
  public static void main(String[] args){
    Parser parser = new Parser();
    Program p = parser.parse();
    new Show(p);
    System.out.println();
  }  
}