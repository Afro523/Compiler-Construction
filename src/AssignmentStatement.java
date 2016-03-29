//AST: AssignmentStatement = VariableAccess variableAccess; Expression expression;
public class AssignmentStatement extends Statement{
	VariableAccess variableAccess; 
	Expression expression;
	int line;

	AssignmentStatement(VariableAccess variableAccess, Expression expression, int line){
		this.variableAccess = variableAccess;
		this.expression = expression;
		this.line = line;
	}
}
