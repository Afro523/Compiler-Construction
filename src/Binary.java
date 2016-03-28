//Binary = Expression term1; Operator op; Expression term2
public class Binary extends Expression{
	Expression term1;
	Operator op; 
	Expression term2;
	
	Binary(Expression term1, Operator op, Expression term2, int line){
		this.term1 = term1;
		this.op = op;
		this.term2 = term2;
		this.line = line;
	}
}