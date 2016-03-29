import java.util.Vector;

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

  private void acceptIt() {
	  currentToken = lexer.nextToken();
  }

  public Program parse() {
    lexer = new Lexer();
    currentToken = lexer.nextToken();
    Program p = parseProgram();
    // Please uncomment the following.
    if (currentToken.kind != Token.EOF)
      new Error("Syntax error: Redundant characters at the end of program.", currentToken.line);
    return p;
  }

  //AST: Program = VariableDefinitions globalVariableDefinitions; FunctionDefinitions functionDefinitions; VariableDefinitions variableDefinitionsInMain; Statements statements;
  //EBNF: Program = VariableDefinitions {FunctionDefinition}  "int" "main" "(" ")" "{"VariableDefinitions Statements"}".
  private Program parseProgram(){
	  FunctionDefinitions fds = null;
	  VariableDefinitions gvds = parseVariableDefinitions();
	  while (currentToken.kind == Token.VOID){
		 fds = parseFunctionDefinition(fds);
	  }
	  accept(Token.INT);
	  accept(Token.MAIN);
	  accept(Token.LPAREN);
	  accept(Token.RPAREN);
	  accept(Token.LBRACE);
	  VariableDefinitions vdim = parseVariableDefinitions();
	  Statements sts = parseStatements();
	  accept(Token.RBRACE);
 	return new Program(gvds, fds, vdim, sts, currentToken.line);
 }

  //AST: VariableDefinitions = VariableDefinition variableDefinition; VariableDefinitions variableDefinitions;
  // EBNF: VariableDefinitions = {VariableGroup}.
  private VariableDefinitions parseVariableDefinitions(){
	  
	  VariableDefinition vd = null;
	  VariableDefinitions fds = null;
	  while(currentToken.kind == Token.INT || currentToken.kind == Token.CHAR){
		  fds = parseVariableGroup(fds);
		  fds = new VariableDefinitions(vd, fds, currentToken.line);
	  }
	  
	  return fds;
  }

  //AST: None
  //VariableGroup = Type Variables ";".
  private VariableDefinitions parseVariableGroup(VariableDefinitions fds){
	  SimpleType st = parseType();
	  fds = parseVariables(st, fds);
	  accept(Token.SEMICOLON);
	  return fds;
  }
  
  //AST: None
  //Variables = Variable {"," Variable}.
  private VariableDefinitions parseVariables(SimpleType simpleType, VariableDefinitions fds){
	  VariableDefinition vd = parseVariable(simpleType);
	  fds = new VariableDefinitions(vd, fds, currentToken.line);
	  while(currentToken.kind == Token.COMA){
		  acceptIt();
		  vd = parseVariable(simpleType);
		  fds = new VariableDefinitions(vd, fds, currentToken.line);
	  }
	  return fds;
  }
	  
  //AST: //AST: VariableDefinition = Type type; String identifier;
  //EBNF: Variable = Identifier ["["Literal"]"].
  private VariableDefinition parseVariable(SimpleType simpleType){
	  
	  String id = null;
	  Type type = simpleType;
	  int i;
	  IntValue iv = null;
	  
	  
	  id = currentToken.spelling;
	  accept(Token.IDENTIFIER);
	  if (currentToken.kind == Token.LBRACKET){
		  accept(Token.LBRACKET);
		  i = Integer.parseInt(currentToken.spelling);
		  iv = new IntValue(i, currentToken.line);
		  accept(Token.LITERAL);
		  accept(Token.RBRACKET);
		  type = new ArrayType(simpleType, iv, currentToken.line);
	  }
	  
	  VariableDefinition vd = new VariableDefinition(type, id, currentToken.line);
	  
	  return vd; 
  }
  
  //AST: Type.  It is an empty class.
  //AST: Type = ArrayType|SimpleType
  //ArrayType inherits Type.
  //AST: SimpleType inherits Type. It is an empty class.
  //AST: CharType, BooleanType, and CharTypes inherit SimpleType.
  //EBNF:   Type = = "int" | "char"
  private SimpleType parseType(){
	  
	  if(currentToken.kind == Token.INT){
		  IntType i = new IntType(currentToken.line);
		  accept(Token.INT);
		  return i;
	  }
	  else if(currentToken.kind == Token.CHAR){
		  CharType c = new CharType(currentToken.line);
		  accept(Token.CHAR);
		  return c;
	  }
	  else new Error("Syntax Error: Missing Int or Char", currentToken.line);
	  return null;
  }
  
  //AST: FunctionDefinitions = FunctionDefinition functionDefinition; FunctionDefinitions functionDefinitions;
  //AST: FunctionDefinition = String identifier; Parameters parameters; VariableDefinitions variableDefinitions; Statements statements;
  //EBNF: FunctionDefinition = "void" Identifier "(" [Parameters] ")" "{"VariableDefinitions Statements"}".
  private FunctionDefinitions parseFunctionDefinition(FunctionDefinitions fds){	  
	  Parameters ps = null;
	  accept(Token.VOID);
	  String id = currentToken.spelling;
	  accept(Token.IDENTIFIER);
	  accept(Token.LPAREN);
	  if(currentToken.kind == Token.INT || currentToken.kind == Token.CHAR)
		  ps = parseParameters();
	  accept(Token.RPAREN);
	  accept(Token.LBRACE);
	  VariableDefinitions vds = parseVariableDefinitions();
	  Statements sts = parseStatements();
	  accept(Token.RBRACE);
	  FunctionDefinition fd = new FunctionDefinition(id, ps, vds, sts, currentToken.line); 
	  fds = new FunctionDefinitions(fd, fds, currentToken.line);
	  
	  return fds;
  }
   
  //AST:Parameters = Parameter parameter; Parameters parameters;
  //EBNF: Parameters = Parameter {"," Parameter}
  private Parameters parseParameters(){
	  Parameter p = null;
	  Parameters ps = null;
	  
	  p = parseParameter();
	  ps = new Parameters(p, ps, currentToken.line);
	  while(currentToken.kind == Token.COMA)
        { 
		  accept(Token.COMA); 
		  p = parseParameter();
		  ps = new Parameters(p, ps, currentToken.line);
		  
        }
	  return ps;
  }
  
  //AST: Parameter = Type type; String identifier;
  //EBNF: Parameter = Type Identifier ["["   "]"].
  private Parameter parseParameter(){
	 SimpleType st = parseType();
	 String s = currentToken.spelling;
	  accept(Token.IDENTIFIER);
	  if (currentToken.kind == Token.LBRACKET){
		  acceptIt();
		  accept(Token.RBRACKET);
		  ArrayType arrayType = new ArrayType(st, null, currentToken.line);
		  return new Parameter(arrayType, s, currentToken.line);
	  }
	  return new Parameter(st, s, currentToken.line);
  }
   
  //AST: Statements = Statement statement; Statements statements;
  //EBNF: Statements = {Statement}.
  private Statements parseStatements(){
	  Statement s = null;
	  Statements sts = null;
	  
	  while (currentToken.kind == Token.IF || currentToken.kind == Token.WHILE || currentToken.kind == Token.READ
			  || currentToken.kind == Token.WRITE || currentToken.kind == Token.LBRACE || currentToken.kind == Token.IDENTIFIER){
		  s = parseStatement();
		  sts = new Statements(s, sts, currentToken.line);
	  }
	  
	  return sts;
  }
  
  //AST: Statement. All kinds of the statements inherit this class
  //EBNF: Statement = Block | Assignment | FunctionCall | IfStatement | WhileStatement | ReadStatement | WriteStatement.
  private Statement parseStatement(){
	  Statement s = null;
	  if(currentToken.kind == Token.LBRACE)
		  s = parseBlock();
	  else if(currentToken.kind == Token.IDENTIFIER)
		  s = parseAssignmentOrFunctionCall();		  
	  else if(currentToken.kind == Token.IF)
		  s = parseIfStatement();
	  else if(currentToken.kind == Token.WHILE)
		  s = parseWhileStatement();
	  else if(currentToken.kind == Token.READ)
		  s = parseReadStatement();
	  else if(currentToken.kind == Token.WRITE)
		  s = parseWriteStatement();
	  else new Error("Syntax Error: Invalid Statement line: ", currentToken.line);
	  
	  return s;
  }

  //AST: IfStatement = Expression expression; Statement thenPart; Statement elsePart;
  //EBNF:IfStatement = "if" "(" Expression")" Statement ["else" Statement].
  private Statement parseIfStatement(){
	  Expression e = null;
	  Statement sthen = null;
	  Statement selse = null;
	  
	  accept(Token.IF);
	  accept(Token.LPAREN);
	  e = parseExpression();
	  accept(Token.RPAREN);
	  sthen = parseStatement();
	  if(currentToken.kind == Token.ELSE){
		  accept(Token.ELSE);
		  selse = parseStatement();
		  return new IfStatement(e, sthen, selse, currentToken.line);
	  }	  	  
	  return new IfStatement(e, sthen, selse, currentToken.line);
  }

  //AST: WhileStatement = Expression expression; Statement statement;
  //EBNF: WhileStatement = "while" "(" Expression")" Statement.
  private Statement parseWhileStatement(){
	  Expression e = null;
	  Statement s = null;
	  
	  accept(Token.WHILE);
	  accept(Token.LPAREN);
	  e = parseExpression();
	  accept(Token.RPAREN);
	  s = parseStatement();
	  
	  return new WhileStatement(e, s, currentToken.line);
  }
  
  //AST: Block = Statements statements;
  //EBNF: Block = "{" Statements "}"
  private Statement parseBlock(){
	  Statements sts = null;
	  accept(Token.LBRACE);
	  sts = parseStatements();
	  accept(Token.RBRACE);
	  
	  return new Block(sts, currentToken.line);
  }

  //AST: AssignmentStatement = VariableAccess variableAccess; Expression expression;
  //AST: AST: FunctionCall = String identifier; Expressions expression;
  //EBNF: Assignment = Identifier["["Expression"]"] "=" Expression ";" | Identifier "(" [Expression {"," Expression }] ")" ";".  
  private Statement parseAssignmentOrFunctionCall(){
	  VariableAccess va = null;
	  Expression e = null;
	  String s = null;
	  Expressions es = null;
	  
	  s = currentToken.spelling;
	  accept(Token.IDENTIFIER);
	  
	  if(currentToken.kind == Token.LBRACKET ||currentToken.kind == Token.ASSIGN){
	    if(currentToken.kind == Token.LBRACKET){
	      accept(Token.LBRACKET);
	      e = parseExpression();
	      accept(Token.RBRACKET);
	      //va = new VariableAccess(s, e, currentToken.line);//New Line
	    }
	    accept(Token.ASSIGN);
	    va = new VariableAccess(s, e, currentToken.line);//New Line
	    e = parseExpression();
	    
	    accept(Token.SEMICOLON);
	    return new AssignmentStatement(va, e, currentToken.line);
	  } else if(currentToken.kind == Token.LPAREN){
	    accept(Token.LPAREN);
	    if(currentToken.kind == Token.NOT ||currentToken.kind == Token.LITERAL || currentToken.kind == Token.APOS ||
	        currentToken.kind == Token.IDENTIFIER ||  currentToken.kind == Token.LPAREN){
	          e = parseExpression();
	          es = new Expressions(e, es, currentToken.line);
	          while(currentToken.kind == Token.COMA){
	             accept(Token.COMA);
	             e = parseExpression();
	             es = new Expressions(e, es, currentToken.line);
	          }	          
	    }
	    accept(Token.RPAREN);
	    accept(Token.SEMICOLON);
	    return new FunctionCall(s, es, currentToken.line);
	  } else new Error("Syntax Error, Missing [, (, =, in line: ", currentToken.line);
	  return null;
  }

  //AST:  ReadStatement = VariableAccess variableAccess; ReadStatement readStatement;
  //EBNF: Read = Variables variables; I know this was different then the EBNF from the old parser, just wasnt sure what to do
  private Statement parseReadStatement(){	  
	  VariablesAccess vas = null;
	  
	  accept(Token.READ);
	  accept(Token.LPAREN);
	  VariableAccess va = parseVariableAccess();
	  vas = new VariablesAccess(va, vas);
	  while(currentToken.kind == Token.COMA){
		  accept(Token.COMA);
		  va = parseVariableAccess();
		  vas = new VariablesAccess(va, vas);
	  }
	  accept(Token.RPAREN);
	  accept(Token.SEMICOLON);
	  
	  
	  return new ReadStatement(vas);
  }
  
  //AST: VariableAccess = String identifier; Expression expression;
  //VariableAccess = Identifier ["[" Expression "]"].
  public VariableAccess parseVariableAccess(){
	  String s = null;
	  Expression e = null;
	  
	  s = currentToken.spelling;
	  accept(Token.IDENTIFIER);
	  if(currentToken.kind == Token.LBRACKET){
		  accept(Token.LBRACKET);
		  e = parseExpression();
		  accept(Token.RBRACKET);
	  }
	  
	  return new VariableAccess(s, e, currentToken.line);
  }
  
  //AST: WriteStatement = Expressions expressions;
  //WriteStatement = "write" "(" Expression {"," Expression } ")" ";".
  private Statement parseWriteStatement(){
	  Expressions es = null;
	  Expression e = null;
	  
	  accept(Token.WRITE);
	  accept(Token.LPAREN);
	  e = parseExpression();
	  es = new Expressions(e, es, currentToken.line);
	  while(currentToken.kind == Token.COMA){
		  accept(Token.COMA);
		  e = parseExpression();
		  es = new Expressions(e, es, currentToken.line);
	  }
	  accept(Token.RPAREN);
	  accept(Token.SEMICOLON);
	  
	  return new WriteStatement(es);
  }

  //AST: Expression The classes VariableAccess,  Value,  Binary, and Unary inherit this class.
  //AST: Binary = Expression term1; Operator op; Expression term2
  //EBNF Expression = Conjunction {"||" Conjunction}
  private Expression parseExpression() {
      Expression e1 = parseConjunction();
      while(currentToken.kind == Token.OR){
          accept(Token.OR);
          Expression e2 = parseConjunction();
          e1 = new Binary(e1, new Operator(Token.OR), e2, currentToken.line);
      }      
      return e1;
  }

  //AST: Binary = Expression term1; Operator op; Expression term2
  //EBNF: Conjunction = Relation {"&&" Relation}
  private Expression parseConjunction() {
	  Expression e1 = parseRelation();
	  while(currentToken.kind == Token.AND){
		  accept(Token.AND);
		  Expression e2 = parseRelation();
		  e1 = new Binary(e1, new Operator(Token.AND), e2, currentToken.line);
	  }
	  return e1;
  }

  //AST: Binary = Expression term1; Operator op; Expression term2;
  // There is a class: Operator. Use it for all kinds of operators.
  //EBNF: Relation = Addition [ ("<" | "<=" | ">" | ">=" | "==" | "!=" ) Addition]. 
  private Expression parseRelation() {
	  Operator op = null;
	  Expression e1 = parseAddition();
	  //Josh changed statement below because it is plain brackets and not curly brackets
	  if(currentToken.kind == Token.LESS || currentToken.kind == Token.LEQ || currentToken.kind == Token.GREATER || 
			currentToken.kind == Token.GEQ || currentToken.kind == Token.EQ || currentToken.kind == Token.NEQ){ 
		  
		  if(currentToken.kind == Token.LESS){
			  accept(Token.LESS);
			  op = new Operator(Token.LESS);
		  }
		  else if(currentToken.kind == Token.LEQ){
			  accept(Token.LEQ);
			  op = new Operator(Token.LEQ);
		  }
		  else if(currentToken.kind == Token.GREATER){
			  accept(Token.GREATER);
			  op = new Operator(Token.GREATER);
		  }
		  else if(currentToken.kind == Token.GEQ){
			  accept(Token.GEQ);
			  op = new Operator(Token.GEQ);
		  }
		  else if(currentToken.kind == Token.EQ){
			  accept(Token.EQ);
			  op = new Operator(Token.EQ);
		  }
		  else if(currentToken.kind == Token.NEQ){
			  accept(Token.NEQ);
			  op = new Operator(Token.NEQ);
		  }
		  else new Error("Syntax Error, Missing Relation line: ", currentToken.line);
		  
		  Expression e2 = parseAddition();
		  e1 = new Binary(e1, op, e2, currentToken.line);
	  }
	  return e1;
  }

  //AST: Binary = Expression term1; Operator op; Expression term2;
  //EBNF: Addition = Term {("+" | "-") Term}
  private Expression parseAddition() {
	  Operator op = null;
	  Expression e1 = parseTerm();
	  while(currentToken.kind == Token.PLUS || currentToken.kind == Token.MINUS){
		  if(currentToken.kind == Token.PLUS){
			  accept(Token.PLUS);
			  op = new Operator(Token.PLUS);
		  }
		  else if(currentToken.kind == Token.MINUS){
			  accept(Token.MINUS);
			  op = new Operator(Token.MINUS);
		  }
		  else new Error("Syntax Error Missing + or - line: ", currentToken.line);
		  Expression e2 = parseTerm();
		  e1 = new Binary(e1, op, e2, currentToken.line);
	  }
	  return e1;
  }

  //AST: Binary = Expression term1; Operator op; Expression term2;
  //EBNF: Term = Negation {("*" | "/") Negation}
  private Expression parseTerm() {
	  Operator op = null;
	  Expression e1 = parseNegation();
	  while( currentToken.kind == Token.TIMES || currentToken.kind == Token.DIVIDE){
		  if( currentToken.kind == Token.TIMES){
			  accept(Token.TIMES);
			  op = new Operator(Token.TIMES);
		  }
		  else if (currentToken.kind == Token.DIVIDE){
			  accept(Token.DIVIDE);
			  op = new Operator(Token.DIVIDE);
		  }
		  else new Error ("Syntax Error, * or / is expected at line: ", currentToken.line);
		  Expression e2 = parseNegation();
		  e1 = new Binary(e1, op, e2, currentToken.line);
	  }
	  return e1;
  }

  //AST: Unary = Operator op; Expression term
  //EBNF: Negation = ["!"]  Factor
  private Expression parseNegation() {
	  Operator op = null;
	  if (currentToken.kind == Token.NOT ){
		  accept(Token.NOT);
		  op = new Operator(Token.NOT);
	  }
	  Expression e = parseFactor();
	  Unary u = new Unary(op, e, currentToken.line);  
	  return u;
  }

  //AST: We use the classes: Value, VariableAccess, Unary. Each of them are a subclass of: Expression.
  //Factor = Literal | VariableName | "(" Expression ")" | "not" Factor | "true" | "false" | character. 
  //Note that we do not have a class called: Factor.
  // Character is: 'x' where x is any printable symbol.
  //Factor = Literal |"'" Character "'" | VariableAccess | "(" Expression ")"
  //AST: Factor = Literal |"'" Character "'" | VariableAccess | "(" Expression ")"
  private Expression parseFactor() {
    if(currentToken.kind == Token.LITERAL){
        int num = Integer.parseInt(currentToken.spelling); //Must be before accept. Why? Because acceptIt moves to the next token, and we want the spelling of the current token.
        acceptIt();
        return new IntValue(num, currentToken.line);
    }
    if(currentToken.kind == Token.CHARACTER){
       //A character is a string of 'X'. Means ' and X, and ' made a string of three characters.
        char c = currentToken.spelling.charAt(1);
        acceptIt();
        return new CharValue(c, currentToken.line);
    }
   if(currentToken.kind == Token.IDENTIFIER)
        return parseVariableAccess();  
    if(currentToken.kind == Token.LPAREN){
        acceptIt();
        Expression e = parseExpression();
        accept(Token.RPAREN);
        return e;
    }
    new Error("Syntax Error: " + currentToken.spelling + " Must be an identifier, a number, or a left parenthesis.", currentToken.line);
    return null;
  }
}
