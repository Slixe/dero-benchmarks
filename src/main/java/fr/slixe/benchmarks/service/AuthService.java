package fr.slixe.benchmarks.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.slixe.benchmarks.User;
import fr.slixe.benchmarks.http.RateLimitException;

@Singleton
public class AuthService {

	private static final int RATE_LIMIT = 1500;
	private static final Type USER_TYPE = new TypeToken<List<User>>(){}.getType();
	private static final Logger log = LoggerFactory.getLogger("DERO Auth Service");

	private final Map<String, Long> requests = Collections.synchronizedMap(new WeakHashMap<>()); //there is no weak map impl for TObjectLongMap (trove4j)
	private final Map<String, User> users = new ConcurrentHashMap<>();
	
	private final File file = new File("admins.json");
	
	@Inject
	private Gson gson;
	
	public void loadUsers()
	{
		if (!this.file.exists())
			try {
				log.info("admins.json not found, creating it with default user.");

				this.file.createNewFile();
				createExampleUser();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		else
			try {
				JsonReader jsonReader = new JsonReader(new FileReader(file));
				List<User> users = gson.fromJson(jsonReader, USER_TYPE);

				for (User user : users)
				{
					this.users.put(user.getUsername(), user);
				}
				jsonReader.close();	
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		saveUsers();
	}

	private void saveUsers()
	{
		try (FileWriter fileWriter = new FileWriter(this.file)) {
			gson.toJson(this.users.values(), USER_TYPE, fileWriter);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void createExampleUser()
	{
		String username = "Slixe";
		String password = "password123";
		String salt = "salt123";

		this.users.put(username, new User(username, AuthService.hash(password, salt), salt));
	}

	public User loginUsername(String username, String password, String ip) throws RateLimitException
	{
		return login(users.get(username), password, ip);
	}

	public User login(User user, String password, String ip) throws RateLimitException
	{
		if (!this.requests.containsKey(ip)) {
			this.requests.put(ip, System.currentTimeMillis());
		} else {
			long lastTime = this.requests.get(ip);
			this.requests.put(ip, System.currentTimeMillis());
			if (System.currentTimeMillis() <= (lastTime + RATE_LIMIT)) {
				log.warn("Rate limit for IP {}", ip);
				throw new RateLimitException("You must wait 1.5s between each request!");
			}
		}

		if (user == null) {
			return null;
		}

		String hash = AuthService.hash(password, user.getSalt());

		if (!hash.equals(user.getHashedPassword()))
		{
			return null;
		}
	
		if (this.requests.containsKey(ip)) {
			this.requests.remove(ip);
		}

		return user;
	}
	
	public static String hash(String password, String salt)
	{
		byte[] passwordSalted = Bytes.concat(salt.getBytes(), password.getBytes());
		return Hashing.sha512().hashBytes(passwordSalted).toString();
	}
}