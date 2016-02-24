public class Token{
  public int kind;
  public String spelling;
  public int line;

  private final static String[] keywords = {
      "<Place holder>", "<Place holder>", "char", "else", "if", "int", "main", "void", "while", "read", "write"
    
  };


  public Token(int kind, String spelling, int line){
    this.kind = kind;
    this.spelling = spelling;
    this.line = line;
    if(kind==IDENTIFIER)
    	for(int k = 0; k < keywords.length; k++)
    		if(spelling.equals(keywords[k])){
    			this.kind = k;
    			return;
    		}
  }

  public final static int
    IDENTIFIER 	=  0,		//Identifier
    LITERAL		=  1,		//Literal
    CHAR		=  2,		//char
    ELSE		=  3,		//else
    IF			=  4,		//if
    INT			=  5,		//int
    MAIN		=  6,		//main
    VOID		=  7,		//void
    WHILE		=  8,		//while
    READ		=  9,		//read this is in place of scanf in C
    WRITE		= 10,		//write this is in place of prontf in C
    LPAREN		= 11,		//(
    RPAREN		= 12,		//)
    LBRACKET	= 13,		//[
    RBRACKET	= 14,		//]
    LBRACE		= 15,		//{
    RBRACE		= 16,		//}
    SEMICOLON	= 17,		//;
    COMA		= 18,		//,
    ASSIGN		= 19,		//=
    PLUS		= 20,		//+
    MINUS		= 21,		//-
    TIMES		= 22,		//*
    DIVIDE		= 23,		///
    LESS		= 24,		//<
    LEQ			= 25,		//<=
    GREATER		= 26,		//>
    GEQ			= 27,		//>=
    EQ			= 28,		//==
    NEQ			= 29,		//!=
    AND			= 30,		//&&
    OR			= 31,		//||
    NOT			= 32,		//!
    NOTHING    	= 33,       //Never happen but we need to return some thing in
    EOF        	= 34,
    UNDEFINED 	= 35,		//Note: I added this for the next phases of the compiler.
    CHARACTER	= 36,		//Any single character between ' and '
    APOS		= 37,		//' changed by josh
    BOOLEAN 	= 38;		//We do not define a variable of type boolean. But we need this to give a boolean type to a boolean expression
    
}
