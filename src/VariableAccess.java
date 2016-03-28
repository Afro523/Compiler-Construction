//AST: VariableAccess = String identifier; Expression expression;
public class VariableAccess extends Expression{
	String identifier; 
	Expression expression;
	
	VariableAccess(String identifier, Expression expression, int line){
		this.identifier = identifier;
		this.expression = expression;
		this.line = line;
	}
}
