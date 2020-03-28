package fr.slixe.benchmarks.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.slixe.benchmarks.User;
import fr.slixe.benchmarks.service.AuthService;

public class UserAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

	@Override
	public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();

		String username = jsonObject.get("username").getAsString();
		String salt = jsonObject.get("salt").getAsString();

		String hashedPassword = "no password";
		
		if (jsonObject.has("password")) {
			String password = jsonObject.get("password").getAsString();
			hashedPassword = AuthService.hash(password, salt);
			
		}
		else if (jsonObject.has("hashedPassword")) {
			hashedPassword = jsonObject.get("hashedPassword").getAsString();	
		}
		
		return new User(username, hashedPassword, salt);
	}

	@Override
	public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject json = new JsonObject();

		json.addProperty("username", src.getUsername());
		json.addProperty("hashedPassword", src.getHashedPassword());
		json.addProperty("salt", src.getSalt());

		return json;
	}
}
