//AST: WhileStatement = Expression expression; Statement statement;
public class WhileStatement extends Statement{
	Expression expression; 
	Statement statement;

	WhileStatement(Expression expression, Statement statement, int line){
		this.expression = expression;
		this.statement = statement;
		this.line = line;
	}
}
