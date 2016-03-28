//AST: Unary = Operator op; Expression term
public class Unary extends Expression{
  Operator op; 
  Expression term;
	
  Unary(Operator op, Expression term, int line){
    this.op = op;
    this.term = term;
    this.line = line;
  }
}