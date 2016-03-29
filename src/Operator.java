//Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp
//BooleanOp = and | or
//RelationalOp = < |<= | > | >= | = | !=
//ArithmeticOp = + | - | * | div
//UnaryOp = !
public class Operator { 
	int val;
	
	Operator (int s){
		val = s;
	}
	
	boolean booleanOp(){
		return val == Token.AND || val == Token.OR;
	}
	
	boolean relationalOp(){
		return val == Token.LESS || val == Token.LEQ || val == Token.GREATER || val == Token.GEQ || val == Token.EQ || val == Token.NEQ; 
	}
	
	boolean arithmeticOp(){
		return val == Token.PLUS || val == Token.MINUS || val == Token.TIMES || val == Token.DIVIDE;  
	}
	
	boolean unaryOp(){
		return val == Token.NOT;
	}
}