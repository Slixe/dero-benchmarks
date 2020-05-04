package fr.slixe.benchmarks.http.controller;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.google.inject.Inject

import fr.litarvan.paladin.Paladin
import fr.litarvan.paladin.Session
import fr.litarvan.paladin.http.Controller
import fr.litarvan.paladin.http.routing.JsonBody
import fr.litarvan.paladin.http.routing.RequestParams
import fr.slixe.benchmarks.User
import fr.slixe.benchmarks.http.InvalidParameterException
import fr.slixe.benchmarks.service.AuthService
import fr.slixe.benchmarks.service.SessionManager

public class AuthController extends Controller {

	private static final Logger log = LoggerFactory.getLogger("DERO HTTP Auth Controller")

	@Inject
	private AuthService authService

	@Inject
	private Paladin paladin;

	@JsonBody
	@RequestParams(required = ["username", "password"])
	def login(String username, String password)
	{
		if (password.length() > 64) {
			throw new InvalidParameterException("Password is too long")
		}
		
		User user = authService.loginUsername(username, password)

		if (user == null) {
			throw new InvalidParameterException("Username or password is incorrect")
		}

		log.info(String.format("User %s is now logged in.", user.getUsername()))

		Session session = ((SessionManager) paladin.getSessionManager()).createSession(user)
		[
			token: session.token
		]
	}

	def validate(Session session)
	{
		[
			token: session.token,
			logged: session[User] != null
		]
	}

	def logout(Session session)
	{
		session[User] = null
	}
}