
public class ArrayType extends Type{
	SimpleType simpleType;
	IntValue intValue;		//null means it is the type of a parameter. Example: int[]
	
	ArrayType(SimpleType simpleType, IntValue intValue, int line){
		this.simpleType = simpleType;
		this.intValue = intValue;
		this.line = line;
	}
}
