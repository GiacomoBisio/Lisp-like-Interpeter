
public interface BoolExprVisitor {
	public void visit(RelOp relop);
	public void visit(BoolConst boolconst);
	public void visit(BoolOp boolop);
}
