package com.ts.interprete;

public class Token {

	private TokenType type;
	private String valor;
	 public static enum TokenType {
		 	monto("[$]|[¢]"),
		 	fecha("[0-3]?[0-9]/[0-1]?[0-9]/[0-2][0-9][0-9][0-9]"),
		 	hora("[0-2]?[0-9]:[0-6]?[0-9]:[0-6]?[0-9]"),
		 	decimal("-?[0-9]+[.][0-9]+"), 
		 	numero("-?[0-9]+"), 
	        igual("[=]"),
	        suma("[+]"),
	        resta("[-]"),
	        multiplicacion("[*]"),
	        division("[/]"),
	        coma("[,]"),
	        punto("[.]"),
	        puntoComa("[;]"),
	        rParentesis("[)]"),
	        lParentesis("[(]"),
	        show("show"),
	        spacio("[ \t\f\r\n]+", true),
	        literal("\"(.*?)\"|\'(.*?)\'"),
	        variable("[\\w]+");//TODO este ultimo es un id, y es un objeto de conta, para usarlo de variable de instancia
	        
	        public String pattern;
	        public boolean skip= false ;
	        
	        private TokenType(String pattern) {
	            this.pattern = pattern;
	        }
	        
	        private TokenType(String pattern, boolean skip) {
	            this.pattern = pattern;
	            this.skip = skip;
	        }
	        
	        public String toString()
	        {
	        	return "Pattern: "+this.pattern+" skip:"+this.skip;
	        }
	    }
	
	
	public Token(TokenType type, String valor) {
		super();
		this.type = type;
		
		if(this.type == TokenType.literal)
		{
			this.valor = valor.substring(1, valor.length()-1);
		}
		else
		{
			this.valor = valor;
		}
		
	}

	public TokenType getType() {
		return type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String signo) {
		this.valor = signo;
	}

	public String toString() {
		return "Valor: "+valor+" Tipo: "+type.name();
	}
	
}
