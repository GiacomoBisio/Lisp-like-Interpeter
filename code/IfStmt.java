
public class IfStmt implements Statement {
	public Block block1;
	public Block block2;
	public BoolExpr boolexpr;
	
	
	public IfStmt(BoolExpr boolexpr, Block block1, Block block2){
		this.block1 = block1;
		this.block2 = block2;
		this.boolexpr = boolexpr;
	}


	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
