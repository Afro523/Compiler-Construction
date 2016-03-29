//Expressions = Expression expression; Expressions expressions;
public class Expressions {
	Expression expression; 
	Expressions expressions;
	int line;

	Expressions(Expression expression, Expressions expressions, int line){
		this.expression = expression;
		this.expressions = expressions;
		this.line = line;
	}
}
