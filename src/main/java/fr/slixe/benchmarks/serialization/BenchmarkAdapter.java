package fr.slixe.benchmarks.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.slixe.benchmarks.Benchmark;
import fr.slixe.benchmarks.Benchmark.Vendor;

public class BenchmarkAdapter implements JsonSerializer<Benchmark>, JsonDeserializer<Benchmark> {

	@Override
	public JsonElement serialize(Benchmark src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject json = new JsonObject();

		json.addProperty("id", src.getId());
		json.addProperty("vendor", src.getVendor().name());
		json.addProperty("model", src.getModel());
		json.addProperty("memory", src.getMemory());
		json.addProperty("hashrate", src.getHashrate());
		json.addProperty("watts", src.getWatts());
		json.addProperty("minerVersion", src.getMinerVersion());
		json.addProperty("owner", src.getOwner());
		json.addProperty("timestamp", src.getTimestamp());

		return json;
	}

	@Override
	public Benchmark deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject json = jsonElement.getAsJsonObject();

		int id = json.get("id").getAsInt();
		Vendor vendor = Vendor.valueOf(json.get("vendor").getAsString());
		String model = json.get("model").getAsString();
		String memory = json.get("memory").getAsString();
		long hashrate = json.get("hashrate").getAsLong();
		String minerVersion = json.get("minerVersion").getAsString();
		String owner = json.get("owner").getAsString();
		long timestamp = json.get("timestamp").getAsLong();

		int watts = 0;
		if (json.has("watts")) {
			watts = json.get("watts").getAsInt();
		}

		return new Benchmark(id, vendor, model, memory, hashrate, watts, minerVersion, owner, timestamp);
	}
}
