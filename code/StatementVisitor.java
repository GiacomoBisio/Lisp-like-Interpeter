
public interface StatementVisitor {
	public void visit(IfStmt ifstmt);
	public void visit(InputStmt inputstmt);
	public void visit(SetStmt setstmt);
	public void visit(WhileStmt whilestmt);
	public void visit(PrintStmt printstmt);
}
