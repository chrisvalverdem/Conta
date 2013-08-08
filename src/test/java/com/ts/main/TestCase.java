package com.ts.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.testng.annotations.BeforeClass;

public class TestCase {
	protected File err;
	
	String outTestDirectory;
	
	@BeforeClass
	public void setUp() throws FileNotFoundException
	{
		outTestDirectory = "test-output"+File.separator+getClass().getSimpleName()+"_logs"+File.separator;
		File outTestDirectoryFile = new File(outTestDirectory);
		File[] listOfFiles = outTestDirectoryFile.listFiles();
		
		outTestDirectoryFile.mkdirs();
		if(listOfFiles != null )
		{
			for(File file : listOfFiles )
			{
				file.delete();
			}
		}
		
		

	}
	
	protected void setErrorsFileOutput(String path) throws FileNotFoundException
	{
		err = new File(outTestDirectory+path);
		PrintStream printStream = new PrintStream(new FileOutputStream(err));
		System.setErr(printStream);	
	}
	
	protected ArrayList<String> getErrors() throws IOException
	{
		ArrayList<String> result = new ArrayList<String>();
		Path path = Paths.get(err.getPath());
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())){
			String line = null;
		    while ((line = reader.readLine()) != null) {
		    	result.add(line);
		    }      
		}
		return result;
	}
	

}
