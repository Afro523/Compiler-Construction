//AST: IfStatement = Expression expression; Statement thenPart; Statement elsePart;
public class IfStatement extends Statement{
	Expression expression; 
	Statement thenPart; 
	Statement elsePart;
	
	IfStatement(Expression expression, Statement thenPart, Statement elsePart, int line){
		this.expression = expression;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
		this.line = line;
	}
}
