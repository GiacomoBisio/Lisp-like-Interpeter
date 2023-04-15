
public class Variable implements NumExpr {
	public String var;
	
	public Variable(String var) {
		this.var = var;
	}


	@Override
	public void accept(NumExprVisitor visitor) {
		visitor.visit(this);		
	}
	
}
