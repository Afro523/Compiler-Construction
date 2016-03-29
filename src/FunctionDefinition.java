//AST: FunctionDefinition = String identifier; Parameters parameters; VariableDefinitions variableDefinitions; Statements statements;
public class FunctionDefinition {
	String identifier; 
	Parameters parameters; 
	VariableDefinitions variableDefinitions; 
	Statements statements;
	int line;
	
	FunctionDefinition(String identifier, Parameters parameters, VariableDefinitions variableDefinitions, Statements statements, int line){
		this.identifier = identifier;
		this.parameters = parameters;
		this.variableDefinitions = variableDefinitions;
		this.statements = statements;
		this.line = line;
	}
}
