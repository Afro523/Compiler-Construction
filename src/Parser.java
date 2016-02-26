/* C-Subset EBNF
 
Program = VariableDefinitions {FunctionDefinition}  "int" "main" "(" ")" "{"VariableDefinitions Statements"}".
VariableDefinitions = {VariableGroup}.
VariableGroup = Type Variables ";".
Variables = Variable {"," Variable}.
Variable = Identifier ["["Literal"]"].
Type = = "int" | "char"
FunctionDefinition = "void" Identifier "(" [Parameters] ")" "{"VariableDefinitions Statements"}".
Parameters = Parameter {"," Parameter}
Parameter = Type Identifier ["["   "]"].
Statements = {Statement}.
Statement = Block | Assignment | FunctionCall | IfStatement | WhileStatement | ReadStatement | WriteStatement.
IfStatement = "if" "(" Expression")" Statement ["else" Statement].
WhileStatement = "while" "(" Expression")" Statement.
 Block = "{" Statements "}".
 Assignment = Identifier["["Expression"]"] "=" Expression ";" | Identifier "(" [Expression {"," Expression }] ")" ";".
ReadStatement = "read" "(" VariableAccess {"," VariableAccess} ")" ";".
VariableAccess = Identifier ["[" Expression "]"].
WriteStatement = "write" "(" Expression {"," Expression } ")" ";".
Expression = Conjunction {"||" Conjunction}.
Conjunction = Relation {"&&" Relation}.
Relation = Addition [ ("<" | "<=" | ">" | ">=" | "==" | "!=" ) Addition].
Addition = Term {("+" | "-") Term}.
Term = Negation {("*" | "/") Negation}.
Negation = ["!"]  Factor.
Factor = Literal |"'" Character "'" | VariableAccess | "(" Expression ")".

*/

public class Parser{
  private Token currentToken;
  private Lexer lexer;

  private void accept(int acceptedToken) {
    if (currentToken.kind == acceptedToken)
    	currentToken = lexer.nextToken();
    else
    	new Error("Syntax Error: " + currentToken.spelling + " is not accepted", currentToken.line);
    }

  public void parse() {
    lexer = new Lexer();
	currentToken = lexer.nextToken();
	parseProgram();
	if (currentToken.kind != Token.EOF)
	  new Error("Syntax error: Redundant characters at the end of program.", currentToken.line);
  }

  //Program = VariableDefinitions {FunctionDefinition}  "int" "main" "(" ")" "{"VariableDefinitions Statements"}".
  private void parseProgram(){
	  parseVariableDefinitions();
	  while (currentToken.kind == Token.VOID)
		  parseFunctionDefinition();
	  accept(Token.INT);
	  accept(Token.MAIN);
	  accept(Token.LPAREN);
	  accept(Token.RPAREN);
	  accept(Token.LBRACE);
	  parseVariableDefinitions();
	  parseStatements();
	  accept(Token.RBRACE);  
  }
  
  //VariableDefinitions = {VariableGroup}.
  private void parseVariableDefinitions(){
	  while(currentToken.kind == Token.INT || currentToken.kind == Token.CHAR)
		  parseVariableGroup();
  }

  //VariableGroup = Type Variables ";".
  private void parseVariableGroup(){
	  parseType();
	  parseVariables();
	  accept(Token.SEMICOLON);
  }
  
  //Variables = Variable {"," Variable}.
  private void  parseVariables(){
	  parseVariable();
	  while(currentToken.kind == Token.COMA){
		  accept(Token.COMA);
		  parseVariable();
	  }  
  }
  
  //Variable = Identifier ["["Literal"]"].
  private void parseVariable(){
	  accept(Token.IDENTIFIER);	  
	  if (currentToken.kind == Token.LBRACKET){
		  accept(Token.LBRACKET);		
		  accept(Token.LITERAL);
		  accept(Token.RBRACKET);
	  }
  }
  
  //Type = "int" | "char"
  private void parseType(){
	  if(currentToken.kind == Token.INT)
		  accept(Token.INT);
	  else if(currentToken.kind == Token.CHAR)
		  accept(Token.CHAR);
	  else new Error("Syntax Error: Missing Int or Char", currentToken.line);
  }
  
  //FunctionDefinition = "void" Identifier "(" [Parameters] ")" "{"VariableDefinitions Statements"}".
  private void parseFunctionDefinition(){
	  accept(Token.VOID);
	  accept(Token.IDENTIFIER);
	  accept(Token.LPAREN);
	  //Changed to Token.INT because first of parameters -> Parameter -> Type -> "int" | "char"
	  if(currentToken.kind == Token.INT || currentToken.kind == Token.CHAR)
		  parseParameters();
	  accept(Token.RPAREN);
	  accept(Token.LBRACE);
	  parseVariableDefinitions();
	  parseStatements();
	  accept(Token.RBRACE);
	  
  }
  
  //Parameters = Parameter {"," Parameter}
  private void parseParameters(){
	  parseParameter();
	  while(currentToken.kind == Token.COMA)
        { 
		  accept(Token.COMA); 
		  parseParameter();  
        }
  }
  
  //Parameter = Type Identifier ["["   "]"].
  private void parseParameter(){
	  parseType();
	  accept(Token.IDENTIFIER);
	  if (currentToken.kind == Token.LBRACKET){
		  accept(Token.LBRACKET);
		  accept(Token.RBRACKET);
	  }
  }
  
  //Statements = {Statement}
  private void parseStatements(){
	  while (currentToken.kind == Token.IF || currentToken.kind == Token.WHILE || currentToken.kind == Token.READ
			  || currentToken.kind == Token.WRITE || currentToken.kind == Token.LBRACE || currentToken.kind == Token.IDENTIFIER)
		  parseStatement();
  }
  
  //Statement = Block | Assignment | FunctionCall | IfStatement | WhileStatement | ReadStatement | WriteStatement.
  //	Becomes Production Rule Below
  //Statement = Block | AssignmentOrFunctionCall | IfStatement | WhileStatement | ReadStatement | WriteStatement.   
  private void parseStatement(){
	  if(currentToken.kind == Token.LBRACE)
		  parseBlock();
	  else if(currentToken.kind == Token.IDENTIFIER)
		  parseAssignmentOrFunctionCall();		  
	  else if(currentToken.kind == Token.IF)
		  parseIfStatement();
	  else if(currentToken.kind == Token.WHILE)
		  parseWhileStatement();
	  else if(currentToken.kind == Token.READ)
		  parseReadStatement();
	  else if(currentToken.kind == Token.WRITE)
		  parseWriteStatement();
	  else new Error("Syntax Error: Invalid Statement line: ", currentToken.line);
  }
  
  //IfStatement = "if" "(" Expression")" Statement ["else" Statement].
  private void parseIfStatement(){
	  accept(Token.IF);
	  accept(Token.LPAREN);
	  parseExpression();
	  accept(Token.RPAREN);
	  parseStatement();
	  if(currentToken.kind == Token.ELSE){
		  accept(Token.ELSE);
		  parseStatement();
	  }
  }

  //WhileStatement = "while" "(" Expression")" Statement
  private void parseWhileStatement(){
	  accept(Token.WHILE);
	  accept(Token.LPAREN);
	  parseExpression();
	  accept(Token.RPAREN);
	  parseStatement();
  }
  
  // Block = "{" Statements "}"
  private void parseBlock(){
	  accept(Token.LBRACE);
	  parseStatements();
	  accept(Token.RBRACE);
  }
    
  //Note that I left factorized the name on the left side of the assignment and the name of the function call
  // AssignmentOrFunctionCall = Identifier["["Expression"]"] "=" Expression ";" | Identifier "(" [Expression {"," Expression }] ")" ";".  
	  private void parseAssignmentOrFunctionCall(){
		  accept(Token.IDENTIFIER);
		  if(currentToken.kind == Token.LBRACKET ||currentToken.kind == Token.ASSIGN){
		    if(currentToken.kind == Token.LBRACKET){
		      accept(Token.LBRACKET);
		      parseExpression();
		      accept(Token.RBRACKET);
		    }
		    accept(Token.ASSIGN);
		    parseExpression();
		    accept(Token.SEMICOLON);
		  } else if(currentToken.kind == Token.LPAREN){
		    accept(Token.LPAREN);
		    if(currentToken.kind == Token.NOT ||currentToken.kind == Token.LITERAL || currentToken.kind == Token.APOS ||
		        currentToken.kind == Token.IDENTIFIER ||  currentToken.kind == Token.LPAREN){
		          parseExpression();
		          while(currentToken.kind == Token.COMA){
		             accept(Token.COMA);
		             parseExpression();
		          }
		    }
		    accept(Token.RPAREN);
		    accept(Token.SEMICOLON);
		  } else new Error("Syntax Error, Missing [, (, =, in line: ", currentToken.line);
		}
  
  //ReadStatement = "read" "(" VariableAccess {"," VariableAccess} ")" ";".
  private void parseReadStatement(){
	  accept(Token.READ);
	  accept(Token.LPAREN);
	  parseVariableAccess();
	  if(currentToken.kind == Token.COMA){
		  accept(Token.COMA);
		  parseVariableAccess();
	  }
	  accept(Token.RPAREN);
	  accept(Token.SEMICOLON);
  }
  
  //VariableAccess = Identifier ["[" Expression "]"].
  public void parseVariableAccess(){
	  accept(Token.IDENTIFIER);
	  if(currentToken.kind == Token.LBRACKET){
		  accept(Token.LBRACKET);
		  parseExpression();
		  accept(Token.RBRACKET);
	  }
	  
  }
  
  //WriteStatement = "write" "(" Expression {"," Expression } ")" ";".
  private void parseWriteStatement(){
	  accept(Token.WRITE);
	  accept(Token.LPAREN);
	  parseExpression();
	  while(currentToken.kind == Token.COMA){
		  accept(Token.COMA);
		  parseExpression();
	  }
	  accept(Token.RPAREN);
	  accept(Token.SEMICOLON);
	  
  }
  
  //Expression = Conjunction {"||" Conjunction}
  private void parseExpression() {
	  parseConjunction();
	  while(currentToken.kind == Token.OR){
		  accept(Token.OR);
		  parseConjunction();
	  }
  }
  
  //Conjunction = Relation {"&&" Relation}
  private void parseConjunction() {
	  parseRelation();
	  while(currentToken.kind == Token.AND){
		  accept(Token.AND);
		  parseRelation();
	  }
  }

  //Relation = Addition [ ("<" | "<=" | ">" | ">=" | "==" | "!=" ) Addition]
  private void parseRelation() {
	  parseAddition();
	  while(currentToken.kind == Token.LESS || currentToken.kind == Token.LEQ || currentToken.kind == Token.GREATER || 
			currentToken.kind == Token.GEQ || currentToken.kind == Token.EQ || currentToken.kind == Token.NEQ){
		  if(currentToken.kind == Token.LESS)
			  accept(Token.LESS);
		  else if(currentToken.kind == Token.LEQ)
			  accept(Token.LEQ);
		  else if(currentToken.kind == Token.GREATER)
			  accept(Token.GREATER);
		  else if(currentToken.kind == Token.GEQ)
			  accept(Token.GEQ);
		  else if(currentToken.kind == Token.EQ)
			  accept(Token.EQ);
		  else if(currentToken.kind == Token.NEQ)
			  accept(Token.NEQ);
		  else new Error("Syntax Error, Missing Relation line: ", currentToken.line);
		  parseAddition();
	  }
  }

  //Addition = Term {("+" | "-") Term}
  private void parseAddition() {
	  parseTerm();
	  while(currentToken.kind == Token.PLUS || currentToken.kind == Token.MINUS){
		  if(currentToken.kind == Token.PLUS)
			  accept(Token.PLUS);
		  else if(currentToken.kind == Token.MINUS)
			  accept(Token.MINUS);
		  else new Error("Syntax Error Missing + or - line: ", currentToken.line);
		  parseTerm();
	  }
  }

  //Term = Negation {("*" | "/") Negation}
  private void parseTerm() {
	  parseNegation();
	  while( currentToken.kind == Token.TIMES || currentToken.kind == Token.DIVIDE){
	  if( currentToken.kind == Token.TIMES)
		  accept(Token.TIMES); 
	  else if (currentToken.kind == Token.DIVIDE)
		  accept(Token.DIVIDE);
	  else new Error ("Syntax Error, * or / is expected at line: ", currentToken.line);
	  parseNegation();
	  }
  }	  

  //Negation = ["!"]  Factor
  private void parseNegation() {
	  if (currentToken.kind == Token.NOT ){
		  accept(Token.NOT);
	  }
	  parseFactor();
  }

  //Factor = Literal |"'" Character "'" | VariableAccess | "(" Expression ")"
  private void parseFactor() {
	  if(currentToken.kind == Token.LITERAL)
		  accept(Token.LITERAL);
	  else if(currentToken.kind == Token.APOS){
		  accept(Token.APOS);
		  accept(Token.CHARACTER); //Josh 
		  accept(Token.APOS);
	  } else if(currentToken.kind == Token.IDENTIFIER)
		  parseVariableAccess();
	  else if(currentToken.kind == Token.LPAREN){
		  accept(Token.LPAREN);
		  parseExpression();
		  accept(Token.RPAREN);
	  } else new Error("Syntax Error, Missing Factor", currentToken.line);
  }
}
