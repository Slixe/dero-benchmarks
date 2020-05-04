package fr.slixe.benchmarks.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.litarvan.paladin.AfterEvent;
import fr.litarvan.paladin.BeforeEvent;
import fr.litarvan.paladin.Session;
import fr.litarvan.paladin.http.Middleware;
import fr.litarvan.paladin.http.Request;
import fr.litarvan.paladin.http.Response;
import fr.litarvan.paladin.http.routing.RequestException;
import fr.litarvan.paladin.http.routing.Route;
import fr.slixe.benchmarks.User;

public class AuthMiddleware extends Middleware
{
	private static final Logger log = LoggerFactory.getLogger("Auth Middleware");

	@Override
	public void before(BeforeEvent event, Request request, Response response, Route route) throws RequestException
	{
		log.info("{}", request.getMethod());
		log.info(request.getIp());
		log.info(request.getUri());
		Session session = request.getSession();
		if (session == null || session.get(User.class) == null) {
			log.info("NOT LOGGED IN");
			throw new UnauthorizedOperationException();
		}
		else {
			log.info("{}", session.get(User.class));
		}
	}

	@Override
	public void after(AfterEvent event, Request request, Response response, Route route) throws RequestException {}
}