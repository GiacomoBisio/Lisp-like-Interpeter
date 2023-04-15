import java.util.ArrayList;

public class Parser{
	
	private Program program;
	private Context context;
	public int i;
	public Parser() {
		this.i = 0;
		this.program = new Block();
		this.context = new Context();
	}
	
	public Program doParse(ArrayList<Token> ArrTok){
    		doRecursiveParse(ArrTok, (Block)program);
		return program;
	}
	
	private Statement doRecursiveParse(ArrayList<Token> ArrTok, Block block) throws SyntaxError {
		
		if(ArrTok.size() == 0) {
			throw new SyntaxError("(ERROR in parser: Premature end of input)\r\n"
					+ "");
		}
		
		if(ArrTok.get(i).o.toString() == "(") {
			i++;
			String possibility = ArrTok.get(i).o.toString();
			switch(possibility){
			
				case "BLOCK":
					if(ArrTok.get(i+1).o.toString() != "(") {
						throw new SyntaxError("(ERROR in parser: Empty BLOCK statement)\r\n"
								+ "");
					}
					boolean k = false;

					while (i < ArrTok.size())
			    	{
						i++;

						if(i == ArrTok.size() || ArrTok.get(i).o.toString() != "(") {

							break;
						}
			    		Statement statement = doRecursiveParse(ArrTok, block);
			    		if(k)
			    			block.list.add(statement);
			    		k = true;
			    	}

					if(i == ArrTok.size() || ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR in parser: Premature end of input");
					}
					else break;
					
					
					
					
				case "SET":
					i++;
					if(ArrTok.get(i).t.toString() != "VAR" ) {
						throw new SyntaxError("ERROR in parser: Expected a variable, got: "+ ArrTok.get(i).o.toString());
					}
					Variable variable = new Variable(ArrTok.get(i).o.toString());
					i++;
					NumExpr numexpr = doParseNumExpr(ArrTok);
					i++;
					SetStmt stmt = new SetStmt(variable, numexpr);
					context.setVariable(variable.var, 0);
					if(ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR: Expected ) at position: "+ i);
					}
					else {
						if(block.list.size() == 0) {
				    		block.list.add(stmt);
							return stmt;

						}
						else {
							return stmt;

						}
					}
					
				case "IF":
					i++;
					BoolExpr boolexpr = doBoolExpr(ArrTok);
					i++;
					Block blocktrue = new Block();
					doRecursiveParse(ArrTok, blocktrue);
					i++;
					Block blockfalse = new Block();
					doRecursiveParse(ArrTok, blockfalse);
					i++;
					if(ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR: Expected ) at position: "+ i);
					}
					else {
						if(block.list.size() == 0) {
							IfStmt stmtif = new IfStmt(boolexpr, blocktrue, blockfalse);
				    		block.list.add(stmtif);
							return stmtif;

						}
						else {
							IfStmt stmtif = new IfStmt(boolexpr, blocktrue, blockfalse);
							return stmtif;

						}
					}
					
						
				case "WHILE":
					i++;
					BoolExpr boolexpr1 = doBoolExpr(ArrTok);
					i++;
					Block blockwhile = new Block();
					doRecursiveParse(ArrTok, blockwhile);
					i++;
					if(ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR: Expected ) at position: "+ i);
					}
					else {
						if(block.list.size() == 0) {
							WhileStmt stmtwhile = new WhileStmt(boolexpr1, blockwhile);
				    		block.list.add(stmtwhile);
							return stmtwhile;

						}
						else {
							WhileStmt stmtwhile = new WhileStmt(boolexpr1, blockwhile);
							return stmtwhile;

						}
					}

				case "INPUT":
					i++;
					if(ArrTok.get(i).t.toString() != "VAR" ) {
						throw new SyntaxError("ERROR: Token at position: "+ i+ " is not valid for the type VAR");
					}
					Variable variable1 = new Variable(ArrTok.get(i).o.toString());
					InputStmt inpstmt = new InputStmt(variable1);
					context.setVariable(variable1.var, 0);
					i++;
					if(ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR: Expected ) at position: "+ i);
					}
					else {
						if(block.list.size() == 0) {
				    		block.list.add(inpstmt);
							return inpstmt;

						}
						else {
							return inpstmt;

						}
					}

					
				case "PRINT":
					i++;
					NumExpr numexpr1 = doParseNumExpr(ArrTok);
					i++;
					if(ArrTok.get(i).o.toString() != ")") {
						throw new SyntaxError("ERROR: Expected ) at position: "+ i);
					}
					else {
						if(block.list.size() == 0) {
							PrintStmt printstmt = new PrintStmt(numexpr1);
				    		block.list.add(printstmt);
							return printstmt;

						}
						else {
							PrintStmt printstmt = new PrintStmt(numexpr1);
							return printstmt;

						}
					}

				default:
					return null;



					
					
					
			}
		}
		else {
			throw new SyntaxError("ERROR in parser: Misplaced token "+ ArrTok.get(i).o.toString());
		}
		return null;


		
	}
		
	
	private NumExpr doParseNumExpr(ArrayList<Token> ArrTok) {
		if(ArrTok.get(i).t.toString() == "NUM") {
			Number number = new Number((long)ArrTok.get(i).o);
			return number;
		}
		else if (ArrTok.get(i).t.toString() == "VAR") {
			Variable variable = new Variable(ArrTok.get(i).o.toString());
			return variable;
		}
		else if(ArrTok.get(i).o.toString() == "(") {
			i++;
			String possibility = ArrTok.get(i).o.toString();
			switch(possibility) {
			case "ADD":
				i++;
				NumExpr first = doParseNumExpr(ArrTok);
				i++;
				NumExpr second = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				Operator operator = new Operator(Operator.OpCode.ADD, first, second);
				
				return operator;

			case "SUB":
				i++;
				NumExpr first1 = doParseNumExpr(ArrTok);
				i++;
				NumExpr second1 = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				Operator operator1 = new Operator(Operator.OpCode.SUB, first1, second1);
				return operator1;
				
			case "MUL":
				i++;
				NumExpr first2 = doParseNumExpr(ArrTok);
				i++;
				NumExpr second2 = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				Operator operator2 = new Operator(Operator.OpCode.MUL, first2, second2);
				return operator2;
			case "DIV":
				i++;
				NumExpr first3 = doParseNumExpr(ArrTok);
				i++;
				NumExpr second3 = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				Operator operator3 = new Operator(Operator.OpCode.DIV, first3, second3);
				return operator3;
			default: 
				throw new SyntaxError("ERROR: Illegal token at position: "+ i);
				
			}
			
		}
		else {
			throw new SyntaxError("ERROR in parser: Misplaced token  "+ ArrTok.get(i).o.toString());

		}

	}
	
	private BoolExpr doBoolExpr(ArrayList<Token> ArrTok) {
		if(ArrTok.get(i).o.toString() == "TRUE") {
			BoolConst boolconst = new BoolConst((boolean)ArrTok.get(i).o);
			return boolconst;
		}
		
		if(ArrTok.get(i).o.toString() == "FALSE") {
			BoolConst boolconst = new BoolConst((boolean)ArrTok.get(i).o);
			return boolconst;
		}
		
		if(ArrTok.get(i).o.toString() == "(") {
			i++;
			String possibility = ArrTok.get(i).o.toString();
			switch(possibility) {
			case "LT":
				i++;
				NumExpr first = doParseNumExpr(ArrTok);
				i++;
				NumExpr second = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				RelOp relop = new RelOp(RelOp.Condition.LT, first, second);
				return relop;
				
			case "GT":
				i++;
				NumExpr first1 = doParseNumExpr(ArrTok);
				i++;
				NumExpr second1 = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				RelOp relop1 = new RelOp(RelOp.Condition.GT, first1, second1);
				return relop1;
				
			case "EQ":
				i++;
				NumExpr first2 = doParseNumExpr(ArrTok);
				i++;
				NumExpr second2 = doParseNumExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				RelOp relop2 = new RelOp(RelOp.Condition.EQ, first2, second2);
				return relop2;
				
			case "AND":
				i++;
				BoolExpr bool1 = doBoolExpr(ArrTok);
				i++;
				BoolExpr bool2 = doBoolExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Illegal token at position: "+ i);

				}
				BoolOp boolop = new BoolOp(BoolOp.Bool.AND, bool1, bool2);
				return boolop;
					
			case "OR":
				i++;
				BoolExpr bool3 = doBoolExpr(ArrTok);
				i++;
				BoolExpr bool4 = doBoolExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				BoolOp boolop1 = new BoolOp(BoolOp.Bool.OR, bool3, bool4);
				return boolop1;
				
			case "NOT":
				i++;
				BoolExpr bool5 = doBoolExpr(ArrTok);
				i++;
				if(ArrTok.get(i).o.toString() != ")") {
					throw new SyntaxError("ERROR: Expected ) at position: "+ i);

				}
				BoolOp boolop2 = new BoolOp(BoolOp.Bool.NOT, bool5, null);
				return boolop2;
				
			default: 
				throw new SyntaxError("ERROR in parser: Unrecognized operator: "+  ArrTok.get(i).o.toString());
			}
	
			
		}
		else {
			throw new SyntaxError("ERROR in parser: Misplaced token "+ ArrTok.get(i).o.toString());
		}
		
		
	}
	
	

}
