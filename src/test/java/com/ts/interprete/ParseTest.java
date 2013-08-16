package com.ts.interprete;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.ts.interprete.Token.TokenType;
import com.ts.interprete.libraries.Expression;
import com.ts.interprete.libraries.Literal;
import com.ts.interprete.libraries.Variable;

public class ParseTest {
	
	@Test
	public void showTest()
	{
		Parser paser;
		Expression result;
		
		paser = new Parser("show 1");
		result = paser.process();
		Assert.assertEquals("1", result.toShow());

		paser = new Parser("show \"Hola Mmundo\"");
		result = paser.process();
		Assert.assertEquals("Hola Mmundo", result.toShow());
		
		paser = new Parser("show 'Hola Mmundo'");
		result = paser.process();
		Assert.assertEquals("Hola Mmundo", result.toShow());
		
		paser = new Parser("show $45.34");
		result = paser.process();
		Assert.assertEquals("$45.34", result.toShow());
		
		paser = new Parser("show 1/1/2013");
		result = paser.process();
		Assert.assertEquals("01/01/2013", result.toShow());
		
		paser = new Parser("show 1/1/2013 9:45:10");
		result = paser.process();
		Assert.assertEquals("01/01/2013 09:45:10", result.toShow());
		
	}
	
	@Test
	public void createColaradorTest()
	{
		Parser paser;
		Variable result;
			
		paser = new Parser("cguillen = Colaborador(\"cristian\", 12, 12)");
		result = (Variable)paser.process();
		Assert.assertEquals("Colaborador", result.getComando());
		Assert.assertEquals("cguillen", result.getNombreInstancia());
		Assert.assertEquals(""+result.getParams().size(), "3" );
		Assert.assertEquals(result.getParams().get(0).toShow(), "cristian");
		Assert.assertEquals(result.getParams().get(1).toShow(), "12");
		Assert.assertEquals(result.getParams().get(2).toShow(), "12");
		
	}

}
