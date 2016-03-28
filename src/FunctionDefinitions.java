//AST: FunctionDefinitions = FunctionDefinition functionDefinition; FunctionDefinitions functionDefinitions;
public class FunctionDefinitions {
	FunctionDefinition functionDefinition;
	FunctionDefinitions functionDefinitions;
	int line;
	
	FunctionDefinitions (FunctionDefinition functionDefinition, FunctionDefinitions functionDefinitions, int line){
		this.functionDefinition = functionDefinition;
		this.functionDefinitions = functionDefinitions;
		this.line = line;
	}

}
