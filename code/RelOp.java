

public class RelOp implements BoolExpr {
	public enum Condition { LT, GT, EQ };
	
	public Condition condition;
	public NumExpr left;
	public NumExpr right;
		
	public RelOp(Condition condition, NumExpr left, NumExpr right) {
		this.condition = condition;
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(BoolExprVisitor visitor) {
		visitor.visit(this);
		
	}

}
