
public class EvaluationProgram implements ProgramVisitor{

	public Context context;

	public EvaluationProgram(Context context) {
		this.context = context;
	}
	
	@Override
	public void visit(Block block) {
		for(int i=0; i < block.list.size(); i++) {
			EvaluationStmt block_program = new EvaluationStmt(context);
			block.list.get(i).accept(block_program);
		}		
	}
	

}
