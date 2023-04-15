
public class Token {

	public enum Type {KEY_WORD, OPEN, CLOSE, NUM, VAR}
	
	public Object o;
	public Type t;
	
	
	public Token(Object o, Type t) {
		this.o = o;
		this.t = t;

	}
	
}
