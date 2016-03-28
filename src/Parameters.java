//AST:Parameters = Parameter parameter; Parameters parameters;
public class Parameters {
	Parameter parameter; 
	Parameters parameters;
	int line;
	
	Parameters(Parameter parameter, Parameters parameters, int line){
		this.parameter = parameter;
		this.parameters = parameters;
		this.line = line;
	}
}
