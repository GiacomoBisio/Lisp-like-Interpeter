
public class WhileStmt implements Statement {
	public Block block;
	public BoolExpr boolexpr;
	
	public WhileStmt(BoolExpr boolexpr, Block block){
		this.block = block;
		this.boolexpr = boolexpr;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
