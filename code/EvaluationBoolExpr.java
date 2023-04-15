import java.util.Stack;

public class EvaluationBoolExpr implements BoolExprVisitor{

	public Context context;
	public Stack<Boolean> stack;
	
	public EvaluationBoolExpr(Context context) {
		this.context = context;
		stack = new Stack<Boolean>();
	}
	
	@Override
	public void visit(RelOp relop) {
		EvaluationNumExpr visitor_right = new EvaluationNumExpr(context);
		relop.right.accept(visitor_right);
		long right = visitor_right.getValue();
		visitor_right.reset();
		EvaluationNumExpr visitor_left = new EvaluationNumExpr(context);
		relop.left.accept(visitor_left);
		long left = visitor_left.getValue();
		visitor_left.reset();
		String possibility = relop.condition.toString();
		switch(possibility) {
		
		case "LT":
			if(left < right) {
				boolean a = true;
				stack.push(a);
			}
			else
				stack.push(false);
			return;
			
		case "GT":
			if(left > right) {
				boolean a = true;
				stack.push(a);
			}
			else
				stack.push(false);
			return;
			
		case "EQ":
			if(left == right) {
				boolean a = true;
				stack.push(a);
			}
			else
				stack.push(false);
		return;
			
		default: 
			return;
			
		}
	}

	@Override
	public void visit(BoolConst boolconst) {
		stack.push(boolconst.boolconst);
	}

	@Override
	public void visit(BoolOp boolop) {
		boolop.boolexpr1.accept(this);
		boolean left = stack.pop();
		String possibility = boolop.bool.toString();
		switch(possibility) {
		
		case "NOT":
			stack.push(!left);
			return;
		
		case "AND":
			if(left == true) {
				boolop.boolexpr2.accept(this);
				boolean right = stack.pop();
				stack.push(right);	
			}
			else 
				stack.push(left);
			return;
			
			
		case "OR":
			if(left != true) {
				boolop.boolexpr2.accept(this);
				boolean right = stack.pop();
				stack.push(right);	
			}
			else 
				stack.push(left);
			return;
		
		default: 
			return;
		}

	}
	
	public boolean getValue() 
	{
		return stack.lastElement();
	}
	
	public void reset() 
	{
		stack.clear();
	}
	
}
