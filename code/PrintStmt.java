
public class PrintStmt implements Statement {
	public NumExpr numexpr;
	
	public PrintStmt(NumExpr numexpr){
		this.numexpr = numexpr;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
