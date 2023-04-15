
  
import java.io.*;
import java.util.regex.Pattern;
import java.util.ArrayList;



public class Main
{
    public static void main(String[] args) throws LessError, IOException
    {
    	try {
    		String input_file = args[0];
	    	ArrayList<Token> ArrTok = new ArrayList<Token>();
	    	FileReader filereader = new FileReader(input_file);

	    	//Lexical Analysis
	    	BufferedReader bufferread = new BufferedReader(filereader);
	    	StreamTokenizer string = new StreamTokenizer(bufferread);
	    	string.ordinaryChar('(');
	    	string.ordinaryChar(')');
	    	while (string.nextToken() != StreamTokenizer.TT_EOF)
	    	{
	    		if(string.ttype == StreamTokenizer.TT_NUMBER)
	    		{
	    			Token t = new Token((long) string.nval, Token.Type.NUM);
	    			ArrTok.add(t);
	    		}
	    		else if(string.ttype == StreamTokenizer.TT_WORD)
	    		{
	    			int k = 0;
	    			int i = 0;
	    			String[] Keys = {"SET", "PRINT", "INPUT", "IF", "BLOCK", "WHILE", "ADD", "SUB", "MUL", "DIV", "LT", "GT", "EQ", "AND", "OR", "NOT", "TRUE", "FALSE"};
					for(; i < Keys.length; i++)
					{
						if(string.sval.equals(Keys[i]))
						{
						k = 1;
						Token t = new Token(string.sval, Token.Type.KEY_WORD);
			    		ArrTok.add(t);
			    		break;
						}
					}
					
					if(k == 0)
					{
						if(!Pattern.matches("[a-zA-Z]+", string.sval))
			    			throw new LessError("ERROR in tokenizer: variable is not valid ");
						Token t = new Token(string.sval, Token.Type.VAR);
						ArrTok.add(t);
					}
	    		}
	    		else if(string.ttype == '(')
	    		{
	    			Token t = new Token("(", Token.Type.OPEN);
	    			ArrTok.add(t);
					continue;
	
	    		}
	    		else if(string.ttype == ')')
	    		{
	    			Token t = new Token(")", Token.Type.CLOSE);
	    			ArrTok.add(t);
					continue;
	
	    		}
	    		else
	    		{
	    			string.toString();
	    			throw new LessError("ERROR in tokenizer: stray character '" + string + "'");
	    		}
	    		
	    	}
	    	
	    	Parser parser = new Parser();
	    	Program p  = parser.doParse(ArrTok);
			Context c = new Context();
	    	EvaluationProgram program_evaluation = new EvaluationProgram(c);
			p.accept(program_evaluation);
    	}
    	catch(Error error){
    		System.out.println(error);
    	}
    	catch(FileNotFoundException e) {
    		System.out.println("File not found");
    	}
    	

    }
}

