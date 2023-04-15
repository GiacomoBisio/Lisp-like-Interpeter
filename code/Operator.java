
public class Operator implements NumExpr {
	public enum OpCode { ADD, SUB, MUL, DIV } 
	
	public OpCode operator;
	public NumExpr left;
	public NumExpr right;
		
	public Operator(OpCode operator, NumExpr left, NumExpr right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(NumExprVisitor visitor) {
		visitor.visit(this);
		
	}

}
