//AST: Block = Statements statements;
public class Block extends Statement{
	Statements statements;
	int line;
	
	Block(Statements statements, int line){
		this.statements = statements;
		this.line = line;
	}
}
