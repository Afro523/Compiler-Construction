/*
I left all the error messages your program should issue. You need to change the test program many times to see these errors on the screen.
I left a couple of complete methods to help you. Therefore if you do not see the comment:
This method is complete
You need to complete it.

Once this class is complete most (if not all) of the "return false" and "return null" should disappear.

*/
public class ContextualAnalyzes {

	private Declarations[] decpart = new Declarations[2];
	private int level;
	
	ContextualAnalyzes(){
		level = 0;
		decpart[level] = new Declarations(); //For global identifiers (variables & function names)
	}
	
	public boolean validate(Program p){
		//This method is complete
		return validate(p.globalVariableDefinitions) && validate(p.functionDefinitions) && validate(p.variableDefinitionsInMain) && 
		validate(p.statements);
	}
	
	private boolean validate(VariableDefinitions variableDefinitions){
		return false;
	}
	
	private boolean validate(VariableDefinition variableDefinition){
		new Error("Contextual Analyzes Error: " + variableDefinition.identifier + " is already defined.", variableDefinition.line); 
		return false;
	}

	private boolean isDeclared(String identifier){
		//This method is complete
		if(decpart[level].size() == 0) return false;
		for(int i = 0; i < decpart[level].size(); i++)
			if(identifier.equals(decpart[level].elementAt(i).identifier))
				return true;
		return false;
	}
		
	private void add(Object obj, String identifier){
		//This method is complete
		Identifier id = new Identifier(identifier, obj);
		decpart[level].addElement(id);
	}
	
	private boolean validate(FunctionDefinitions functionDefinitions){
		new Error("Contextual Analyzes Error: " + functionDefinitions.functionDefinition.identifier + " is alreay defined.", functionDefinitions.functionDefinition.line);
		return false;
	}
	
	private boolean validate(FunctionDefinition functionDefinition){
		level++;
		// Write code here
		level--;
		return false;
	}
	
	private boolean validate(Parameters parameters){
		return false;
	}
	
	private boolean validate(Parameter parameter){
			new Error("Contextual Analyzes Error:Parameter " + parameter.identifier + " is alreay defined.", parameter.line);
			return false;
	}

	private boolean validate(Statement statement){
		if(statement == null)
			 return true;
		if(statement instanceof AssignmentStatement)
			return validate((AssignmentStatement)statement);
		//This method is not complete
		return false;
	}

	private boolean validate(AssignmentStatement assignmentStatement){
		new Error("Type of the left and right sides of = must be the same.", assignmentStatement.line);
		return false; 
	}
	
	private Type validate(VariableAccess variableAccess){
			new Error(variableAccess.identifier + " must either be a variable or a parameter", variableAccess.line);
		
			new Error("The type of the subscript of " + variableAccess.identifier + " must be of integer", variableAccess.line);
			new Error("The type of the subscript of " + variableAccess.identifier + " must be of integer", variableAccess.line);
			return null;
	}
	
	Identifier find(String id, int line){
		//This method is complete.
		for(int l = level; l >= 0; l--)
			for(int i = 0; i < decpart[l].size(); i++){
				Identifier item = decpart[l].elementAt(i);
				if(item.identifier.equals(id))
					return item;
			}
		new Error(id + " is not defined.", line);
		return null;
	}
	
	private boolean validate(Statements statements){
		return false;
	}
	
	private boolean validate(IfStatement ifStatement){
			new Error("Expression of a conditional statement must be of type boolean.", ifStatement.line);
		return false;
	}

	private boolean validate(FunctionCall functionCall){
			new Error(functionCall.identifier + " is not declared as a function", functionCall.line);
		return false;
	}

	private boolean checkArgumentsAgainstParameters(Expressions expressions, Parameters parameters){
			new Error("More arguments than parameters", expressions.line);
			new Error("Less arguments than parameters", parameters.line);
		return false;
	}

	private boolean checkSingleArgumentAgainstSingleParameter(Expression expression, Parameter parameter){
			new Error("The type of the argument and the parameter must be the same.", expression.line);
			return false;
	}
	
	private boolean isTheSameTypes(Type type1, Type type2){
		return false;
	}	

	private boolean validate(ReadStatement readStatement){
		return false;
	}
	
	private boolean validate(VariablesAccess variablesAccess){
		return false;
	}
	
	private boolean checkReadArgument(VariableAccess variableAccess, int line){
				new Error(variableAccess.identifier + " cannot be of type boolean", line);//This never happen
				new Error(variableAccess.identifier + " cannot be of type boolean", line);//This never happen
			new Error(variableAccess.identifier + " is not declared as a variable or parameter.", variableAccess.line);
		return false;
	}

	private boolean validate(WhileStatement whileStatement){
			new Error("Only boolean expression is allowed.", whileStatement.line);
		return false;
	}
	
	private boolean validate(WriteStatement writeStatement){
		return false;
	}

	private boolean validate(Expressions expressions){
		return false;
	}
	
	private Type validate(Expression expression){
		return null;
	}
		
	private Type validate(Binary binary){
				new Error("Operands must be of type integer", binary.line);
				new Error("Both operadns must be of type integer or character", binary.line);
				new Error("Both operands must be of type boolean", binary.line);
		return null;
	}
	
	private Type validate(Unary unary){
		new Error("operand must be of type boolean", unary.line);//Never happen
		return null;
	}
	
	private Type validate(Value value){
		return null;
	}	
}
