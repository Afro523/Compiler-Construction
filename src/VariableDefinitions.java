//AST: VariableDefinitions = VariableDefinition variableDefinition; VariableDefinitions variableDefinitions;
public class VariableDefinitions {
	VariableDefinition variableDefinition;
	VariableDefinitions variableDefinitions;
	int line;
	
	VariableDefinitions(VariableDefinition variableDefinition, VariableDefinitions variableDefinitions, int line){
		this.variableDefinition = variableDefinition;
		this.variableDefinitions = variableDefinitions;
		this.line = line;
	}
}
