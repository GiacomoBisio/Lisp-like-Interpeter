
public class BoolConst implements BoolExpr {
	public boolean boolconst;
	
	public BoolConst(boolean boolconst){
		this.boolconst = boolconst;
	}

	@Override
	public void accept(BoolExprVisitor visitor) {
		visitor.visit(this);
	}
}
