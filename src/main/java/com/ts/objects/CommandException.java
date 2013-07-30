package com.ts.objects;

public class CommandException extends Exception{
	private String message;
	
	public CommandException(String message)
	{
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
