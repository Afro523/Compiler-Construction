public class Show{
  private String s;
  
  public Show(Object p){
	  s = "";
	  display(p);
	  System.out.print(s);
  }

private void display(Object p){
	if(p == null) return;
    String s = p.getClass().getName();
    if(s.equals("ArrayType")){
    	ArrayType a = (ArrayType)p;
    	display(a.simpleType);
    	print("[");
    	if(a.intValue != null)
    		print("" + a.intValue.value);
    	print("]");
    } else if(s.equals("AssignmentStatement")){
    	AssignmentStatement a = (AssignmentStatement)p;
    	display(a.variableAccess);
    	print("=");
    	display(a.expression);
    } else if(s.equals("CharType"))
    	print("char");
    else if(s.equals("Binary")){
    	Binary b = (Binary)p;
    	display(b.term1);
    	display(b.op);
    	display(b.term2);
    }else if(s.equals("Block")){
    	print("{");
    	Block b = (Block)p;
    	display(b.statements);
    	print("}");
    }else if(s.equals("CharValue")){
    	CharValue cv = (CharValue)p;
    	print("'"+cv.c+"'");
    } else if(s.equals("Expressions")){
    	Expressions e = (Expressions)p;
    	display(e.expressions);
    	display(e.expression);
    }else if(s.equals("FunctionCall")){
    	FunctionCall f = (FunctionCall)p;
    	print(f.identifier);
    	print("(");
    	display(f.expressions);
    	print(")");
    }else if(s.equals("FunctionDefinitions")){
    	FunctionDefinitions fds = (FunctionDefinitions)p;
    	display(fds.functionDefinitions);
    	display(fds.functionDefinition);
    } else if(s.equals("FunctionDefinition")){
    	FunctionDefinition fd = (FunctionDefinition)p;
    	print("void "+fd.identifier);
    	print("(");
    	display(fd.parameters);
    	print(")");
    	display(fd.variableDefinitions);
    	display(fd.statements);
    } else if(s.equals("IfStatement")){
    	IfStatement f = (IfStatement)p;
    	print("if(");
    	display(f.expression);
    	print("){");
    	display(f.thenPart);
    	if(f.elsePart != null)
    		print("else{");
    	display(f.elsePart);
    	if(f.elsePart != null)
    		print("}");
    	print("}");
    } else if(s.equals("IntType"))
    	print("int");
    else if(s.equals("IntValue")){
    	IntValue iv = (IntValue)p;
    	print(""+iv.value);
    } else if(s.equals("Operator")){
    	Operator o = (Operator)p;
    	show(o.val);
    }else if(s.equals("Parameter")){
    	Parameter pa = (Parameter)p;
       	display(pa.type);
    	print(pa.identifier);
    }else if(s.equals("Parameters")){
    	Parameters pas = (Parameters)p;
    	display(pas.parameters);
    	display(pas.parameter);
    }else if(s.equals("Program")){
    	Program pr = (Program)p;
    	display(pr.globalVariableDefinitions);
    	display(pr.functionDefinitions);
    	display(pr.variableDefinitionsInMain);
    	display(pr.statements);
    }else if(s.equals("ReadStatement")){
    	ReadStatement r = (ReadStatement)p;
    	print("read(");
    	display(r.variablesAccess);
    	print(")");
    }else if(s.equals("Statements")){
    	Statements ss = (Statements)p;
    	display(ss.statements);
    	display(ss.statement);
    }else if(s.equals("Unary")){
    	Unary u = (Unary)p;
    	if(u.op != null){
    		print("not");
    	}
    	display(u.op);
    	display(u.term);
    } else if(s.equals("CharValue")){
    	CharValue u = (CharValue)p;
    	print(""+u.c);
    } else if(s.equals("VariableAccess")){
    	VariableAccess v = (VariableAccess)p;
    	print(v.identifier);
    	if(v.expression != null){
    		print("[");
    		display(v.expression);
    		print("]");
    	}
    }else if(s.equals("VariableDefinition")){
    	VariableDefinition v = (VariableDefinition)p;
    	display(v.type);
    	print(v.identifier);
    } else if(s.equals("VariableDefinitions")){
    	VariableDefinitions vs = (VariableDefinitions)p;
    	display(vs.variableDefinitions);
    	display(vs.variableDefinition);
    }else if(s.equals("VariablesAccess")){
    	VariablesAccess vsa = (VariablesAccess)p;
    	display(vsa.variablesAccess);
    	display(vsa.variableAccess);
    } else if(s.equals("WhileStatement")){
    	WhileStatement w = (WhileStatement)p;
    	print("while(");
    	display(w.expression);
    	print("){");
    	display(w.statement);
    	print("}");
    }else if(s.equals("WriteStatement")){
    	WriteStatement w = (WriteStatement)p;
    	print("write(");
    	display(w.expressions);
    	print(")");
    } else
    	new Error("You are not displaying an object", 0);
  } 
  
  private void print(String a){
	  s = s + a + " ";
	  if(s.length() > 100){
		  System.out.print(s + "\n");
		  s = "";
	  }
  }
  
  private void show(int op){
	  switch(op){
	  	case Token.AND: print("&&"); return;
	  	case Token.ASSIGN: print("="); return;
	  	case Token.DIVIDE: print("//"); return;
	  	case Token.EQ: print("=="); return;
	  	case Token.GEQ: print(">="); return;
	  	case Token.GREATER: print(">"); return;
	  	case Token.LEQ: print("<="); return;
	  	case Token.LESS: print("<"); return;
	  	case Token.MINUS: print("-"); return;
	  	case Token.NEQ: print("!="); return;
	  	case Token.NOT: print("not"); return;
	  	case Token.OR: print("||"); return;
	  	case Token.PLUS: print("+"); return;
	  	case Token.TIMES: print("*"); return;
	  	default: print("????????????????"); return;
  }
  }
}