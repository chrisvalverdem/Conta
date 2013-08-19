package com.ts.interprete;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.ts.interprete.Token.TokenType;
import com.ts.objects.CommandException;

public class Lexer {
   
	private int puntero = 0;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	
    public Lexer(String input) {

        StringBuffer bufferDePatrones = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
        {
        	bufferDePatrones.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }
        Pattern tokenPatterns = Pattern.compile(new String(bufferDePatrones.substring(1)));
        Matcher matcher = tokenPatterns.matcher(input);
        matcher = tokenPatterns.matcher(input);
        
        while (matcher.find()) 
        {
        	 for (TokenType tokenType : TokenType.values())
             {
             	boolean seDebeAgregarATokens = matcher.group(tokenType.name()) != null && !tokenType.skip;
        		if(seDebeAgregarATokens)
             	{
        			tokens.add(new Token(tokenType, matcher.group()));
             	}
             }
        }
    }

    public Token getToken() {
		return tokens.get(puntero);
	}
    
    public Token sgtToken() {
    	int newPuntero = puntero+1;
    	
    	if(newPuntero < tokens.size())
    	{
    		return tokens.get(newPuntero);
    	}
    	else
    	{
    		return null;
    	}
		
	}

	public void accept() {
		puntero ++;		
	}

	public void accept(TokenType tipo) {
		TokenType currentType = tokens.get(puntero).getType();
		if(currentType != tipo)
		{
			throw new CommandException("Se esperaba "+tipo.name()+" y se encontro "+currentType.name()+"." );
		}
		puntero ++;	
	}
}