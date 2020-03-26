package fr.slixe.benchmarks.http;

import fr.litarvan.paladin.http.routing.RequestException;

public class UnauthorizedOperationException extends RequestException
{
	public UnauthorizedOperationException()
	{
		super("You don't have the rights to do that");
	}
}