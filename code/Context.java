import java.util.HashMap;

public class Context {

	private HashMap<String, Long> symbolTable;
	
	public Context() {
		symbolTable = new HashMap<>();
	}
	
	public void setVariable(String id, long value) {
		symbolTable.put(id, value);
	}
	
	public long getVariable(String id) throws SemanticError{
		if(symbolTable.get(id) == null) {
			throw new SemanticError("ERROR in evaluator: Undefined variable " + id);
		}
		return symbolTable.get(id);
	}
	
	
}
