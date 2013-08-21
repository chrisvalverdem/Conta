package com.ts.interprete;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ts.db.Repo;
import com.ts.interprete.Token.TokenType;
import com.ts.interprete.libraries.CallComando;
import com.ts.interprete.libraries.Comando;
import com.ts.interprete.libraries.CreateComando;
import com.ts.interprete.libraries.Expression;
import com.ts.interprete.libraries.ShowComando;
import com.ts.libraries.Colaborador;
import com.ts.libraries.Compania;
import com.ts.libraries.Hilera;

public class ParseTest {
		
	@Test
	public void createColaradorTest() throws Exception
	{
		Parser paser;
		Comando result;
		
		paser = new Parser("01/01/2013 09:45:10 ts = Compania(\"j456\", \"el nombre de prueba\", '$');");
		result = (CreateComando)paser.comandoProcess();
		result.execute();
		
		Assert.assertEquals("j456", ((Compania)result.getExpression().getObjecto()).getCedulaJuridica().valor);
		Assert.assertEquals("el nombre de prueba", ((Compania)result.getExpression().getObjecto()).getNombre().valor);
		Assert.assertEquals(""+Repo.get(Compania.class).size(), ""+1);
		
		paser = new Parser("01/01/2013 09:45:10 cecropia = Compania(\"233243\", \"Cecropia\", '$');");
		result = (CreateComando)paser.comandoProcess();
		result.execute();
		
		Assert.assertEquals("233243", ((Compania)result.getExpression().getObjecto()).getCedulaJuridica().valor);
		Assert.assertEquals("Cecropia", ((Compania)result.getExpression().getObjecto()).getNombre().valor);
		Assert.assertEquals(Repo.get(Compania.class).size(), 2);
		
		paser = new Parser("01/01/2013 09:45:10 show ts.getNombre();");
		result = (ShowComando)paser.comandoProcess();
		result.execute();
		
		paser = new Parser("01/01/2013 09:45:10 show ts.getNombre();");
		result = (ShowComando)paser.comandoProcess();
		result.execute();
		
		paser = new Parser("03/07/2013 20:53:10  jLopez = Colaborador('Jahzeel', '1-1111-1111', 15/12/1988, 08/07/2013, true, '8445-1544', 0, $1000)");
		result = (CreateComando)paser.comandoProcess();
		result.execute();	
		
		paser = new Parser("03/07/2013 20:53:10  jLopez.agregarVacaciones(15/12/1988);");
		result = (CallComando)paser.comandoProcess();
		result.execute();
		
		
	}

}
