
public class BoolOp implements BoolExpr {
	public enum Bool { AND, OR, NOT };
	
	public Bool bool;
	public BoolExpr boolexpr1;
	public BoolExpr boolexpr2;

	public BoolOp(Bool bool, BoolExpr boolexpr1, BoolExpr boolexpr2){
		this.bool = bool;
		this.boolexpr1 = boolexpr1;
		this.boolexpr2 = boolexpr2;
	}

	@Override
	public void accept(BoolExprVisitor visitor) {
		visitor.visit(this);
	}
}
