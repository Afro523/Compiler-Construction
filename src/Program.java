//AST: Program = VariableDefinitions globalVariableDefinitions; FunctionDefinitions functionDefinitions; VariableDefinitions variableDefinitionsInMain; Statements statements;
public class Program{
	VariableDefinitions globalVariableDefinitions;
	FunctionDefinitions functionDefinitions;
	VariableDefinitions variableDefinitionsInMain;
	Statements statements;
	int line;
	
	Program(VariableDefinitions globalVariableDefinitions, FunctionDefinitions functionDefinitions, VariableDefinitions variableDefinitionsInMain, Statements statements, int line){
		this.globalVariableDefinitions = globalVariableDefinitions;
		this.functionDefinitions = functionDefinitions;
		this.variableDefinitionsInMain = variableDefinitionsInMain;
		this.statements = statements;
		this.line = line;
	}
}
