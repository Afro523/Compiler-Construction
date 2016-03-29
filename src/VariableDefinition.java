//AST: VariableDefinition = Type type; String identifier;
public class VariableDefinition {
	Type type;
	String identifier; 
	int line;
	
	public VariableDefinition (Type type, String identifier, int line){
		this.type = type;
		this.identifier = identifier;
		this.line = line;
	}
}
