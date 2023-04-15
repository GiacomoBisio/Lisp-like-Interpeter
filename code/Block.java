import java.util.ArrayList;

public class Block implements Program{
	public ArrayList<Statement> list;
	
	public Block() {
		this.list = new ArrayList<Statement>();
	}

	@Override
	public void accept(ProgramVisitor visitor) {
		visitor.visit(this);
		
	}

}
