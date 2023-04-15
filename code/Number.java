
public class Number implements NumExpr {
	public long number;
	
	public Number(long number){
		this.number = number;
	}

	@Override
	public void accept(NumExprVisitor visitor) {
		visitor.visit(this);
		
	}
}
