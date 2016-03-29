//AST: Parameter = Type type; String identifier;
public class Parameter {
	Type type; 
	String identifier; 
	int line;
	
	Parameter(Type type, String identifier, int line){
		this.type = type;
		this.identifier = identifier;
		this.line = line;
	}
}
