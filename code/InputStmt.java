
public class InputStmt implements Statement {
	public Variable variable;
	
	public InputStmt(Variable variable){
		this.variable = variable;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}
