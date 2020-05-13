package fr.slixe.benchmarks.http;

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
	//private static final Logger log = LoggerFactory.getLogger("Auth Middleware");

	@Override
	public void before(BeforeEvent event, Request request, Response response, Route route) throws RequestException
	{
		Session session = request.getSession();
		if (session == null || session.get(User.class) == null) {
			throw new UnauthorizedOperationException();
		}
		/*else {
			log.info("{} from {}", session.get(User.class).getUsername(), request.getIp());
		}*/
	}

	@Override
	public void after(AfterEvent event, Request request, Response response, Route route) throws RequestException {}
}