
public class SetStmt implements Statement {
	Variable variable; 
	NumExpr numexpr;
	
	public SetStmt(Variable variable, NumExpr numexpr){
		this.numexpr = numexpr;
		this.variable = variable;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
