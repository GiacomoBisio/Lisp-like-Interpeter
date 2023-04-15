import java.util.Stack;

public class EvaluationNumExpr implements NumExprVisitor {
	
	public Context context;
	public Stack<Long> stack;
	
	public EvaluationNumExpr(Context context) {
		this.context = context;
		stack = new Stack<Long>();
	}

	@Override
	public void visit(Operator operator) throws SemanticError{
		operator.left.accept(this);
		operator.right.accept(this);
		long r_value = stack.pop();
		long l_value = stack.pop();
		switch (operator.operator) 
		{
		case ADD:
			stack.push(l_value + r_value);
			return;
		case SUB:
			stack.push(l_value - r_value);
			return;
		case MUL:
			stack.push(l_value * r_value);
			return;
		case DIV:
			if(r_value != 0) {
				stack.push(l_value / r_value);
			}
			else {
				throw new SemanticError("ERROR in evaluator: Division by zero");
			}
			return;
		default:
			return;
		}
	}

	@Override
	public void visit(Variable variable) {
		long variable_value = context.getVariable(variable.var);
		stack.push(variable_value);
	}

	@Override
	public void visit(Number number) {
		stack.push(number.number);	
	}
	
	public long getValue() 
	{
		return stack.lastElement();
	}
	
	public void reset() 
	{
		stack.clear();
	}
	

}
