//AST: Statements = Statement statement; Statements statements;
public class Statements extends Statement{
	Statement statement; 
	Statements statements;
	int line;
	
	Statements(Statement statement, Statements statements, int line){
		this.statement = statement;
		this.statements = statements;
		this.line = line;
	}
}
