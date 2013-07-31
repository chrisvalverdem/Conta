package com.ts.main;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Repo;

public class InterpreteComandos {

	
	@Test
	public void pruebaCargaArchivo() throws IOException, CommandException{

		InterpreteMandatos interprete = new InterpreteMandatos(false);
		interprete.ejecutaComando("crear_edificio Occidente;");
		interprete.ejecutaComando("crear_compannia 12, ts;");
		interprete.ejecutaComando("crear_compannia 11, Cecropia;");
		
		new InterpreteMandatos(false);
		
		Compannia compannia= Repo.getCompannia(12);
		Edificio edificio= Repo.getEdificio("occidente");
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(compannia.getNombre(),"ts");
		Assert.assertEquals(Repo.getTamannoCompannia(), 2);
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(),"occidente");
		Assert.assertEquals(Repo.getTamannoEdificio(), 1);
	}
	

	
}
