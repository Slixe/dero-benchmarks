package fr.slixe.benchmarks.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.litarvan.paladin.ISessionManager;
import fr.litarvan.paladin.Paladin;
import fr.litarvan.paladin.Session;
import fr.litarvan.paladin.http.Header;
import fr.litarvan.paladin.http.Request;
import fr.litarvan.paladin.http.Response;
import fr.slixe.benchmarks.JsonIgnore;
import fr.slixe.benchmarks.User;

public class SessionManager implements ISessionManager {

	private final Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			return f.getAnnotation(JsonIgnore.class) != null;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	}).create();

	private Paladin paladin;
	private Algorithm algorithm;
	private long expirationDelay;

	public SessionManager()
	{
		this.expirationDelay = TimeUnit.DAYS.toSeconds(30);
	}

	public void init(Paladin paladin, String secret)
	{
		this.paladin = paladin;
		this.algorithm = Algorithm.HMAC256(secret);
	}

	@Override
	public Session get(Request request, Response response)
	{
		String token = request.getHeaderValue(Header.AUTHORIZATION);

		if (token != null)
		{
			if (token.startsWith("Bearer") && token.length() > 7)
			{
				token = token.substring(7);
			}

			try {
				JWTVerifier verifier = JWT.require(this.algorithm).build();
				DecodedJWT decodedToken = verifier.verify(token);
				token = decodedToken.getToken();

				return getSession(decodedToken);
			} catch (SignatureVerificationException | TokenExpiredException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean isValidToken(Request request)
	{
		String token = request.getHeaderValue(Header.AUTHORIZATION);
		DecodedJWT decodedToken = null;

		if (token != null)
		{
			if (token.startsWith("Bearer") && token.length() > 7)
			{
				token = token.substring(7);
			}

			try {
				JWTVerifier verifier = JWT.require(this.algorithm).build();
				decodedToken = verifier.verify(token);
			} catch (SignatureVerificationException | TokenExpiredException ignored) {}
		}

		return decodedToken != null;
	}

	@Override
	public long getExpirationDelay()
	{
		return expirationDelay;
	}

	@Override
	public void setExpirationDelay(long expirationDelay)
	{
		this.expirationDelay = expirationDelay;
	}

	private Session getSession(DecodedJWT jwt)
	{
		long expiresAt = expirationDelay <= 0 ? -1 : LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000 + expirationDelay;
		Session session = new Session(expiresAt, jwt.getToken());

		session.putAt(User.class, gson.fromJson(jwt.getClaim("user").asString(), User.class));

		return session;
	}

	public Session createSession(User user)
	{
		long expiresAt = expirationDelay <= 0 ? -1 : LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000 + expirationDelay;
		String token = JWT.create().withIssuer(paladin.getAppInfo().name())
				.withClaim("user", gson.toJson(user))
				.withIssuedAt(new Date(System.currentTimeMillis())).withExpiresAt(new Date(expiresAt))
				.sign(this.algorithm);

		return new Session(expiresAt, token);
	}
}
