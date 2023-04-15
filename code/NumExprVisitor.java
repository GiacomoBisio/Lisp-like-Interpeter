
public interface NumExprVisitor {
	public void visit(Operator operator);
	public void visit(Variable variable);
	public void visit(Number number);
}
