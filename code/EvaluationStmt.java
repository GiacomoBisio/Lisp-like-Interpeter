
import java.util.Scanner;

public class EvaluationStmt implements StatementVisitor{

	public Context context;

	public EvaluationStmt(Context context) {
		this.context = context;
	}
	
	@Override
	public void visit(IfStmt ifstmt) {
		EvaluationBoolExpr verify = new EvaluationBoolExpr(context);
		ifstmt.boolexpr.accept(verify);
		boolean verify_value = verify.getValue();
		verify.reset();
		if(verify_value == true) {
			
			for(int i=0; i < ifstmt.block1.list.size(); i++) {
				EvaluationStmt blocktrue = new EvaluationStmt(context);
				ifstmt.block1.list.get(i).accept(blocktrue);
			}
		}
		else {
			for(int i=0; i < ifstmt.block2.list.size(); i++) {
				EvaluationStmt blockfalse = new EvaluationStmt(context);
				ifstmt.block2.list.get(i).accept(blockfalse);
			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void visit(InputStmt inputstmt) throws SemanticError{
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		if (input == null) {

			throw new SemanticError("Unable to get a console to read input");
		}

		String inputText = null;

		try {

			inputText = input.nextLine();

		} catch (Exception e) {

			throw new SemanticError("Unable to read input from console");

		}	

		long input_value = 0;

		try {

			input_value = Long.parseLong(inputText);

		} catch (NumberFormatException e) {

			throw new SemanticError	("Expected long number, got '" + inputText + "'");

		}
		context.setVariable(inputstmt.variable.var, input_value);
	}

	@Override
	public void visit(SetStmt setstmt) {
		EvaluationNumExpr set = new EvaluationNumExpr(context);
		setstmt.numexpr.accept(set);
		long set_value = set.getValue();
		set.reset();
		context.setVariable(setstmt.variable.var, set_value);
	}

	@Override
	public void visit(WhileStmt whilestmt) {
		EvaluationBoolExpr verify = new EvaluationBoolExpr(context);
		whilestmt.boolexpr.accept(verify);
		boolean verify_value = verify.getValue();
		verify.reset();
		while(verify_value == true) {
			for(int i=0; i < whilestmt.block.list.size(); i++) {
				EvaluationStmt block = new EvaluationStmt(context);
				whilestmt.block.list.get(i).accept(block);
			}
			whilestmt.boolexpr.accept(verify);
			verify_value = verify.getValue();
			verify.reset();
			
		}
	}

	@Override
	public void visit(PrintStmt printstmt) {
		EvaluationNumExpr print = new EvaluationNumExpr(context);
		printstmt.numexpr.accept(print);
		long print_value = print.getValue();
		print.reset();
		System.out.println(print_value);
		
	}

}
