//AST: FunctionCall = String identifier; Expressions expression;
public class FunctionCall extends Statement{
	String identifier; 
	Expressions expressions;
	
	FunctionCall(String identifier, Expressions expressions, int line){
		this.identifier = identifier;
		this.expressions = expressions;
		this.line = line;
	}
}
