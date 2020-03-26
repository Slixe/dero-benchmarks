package fr.slixe.benchmarks;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;

import fr.slixe.benchmarks.serialization.BenchmarkAdapter;
import fr.slixe.benchmarks.serialization.UserAdapter;

public class MyModule extends AbstractModule {

	@Override
	protected void configure()
	{
		bind(Gson.class).toInstance(new GsonBuilder().registerTypeAdapter(Benchmark.class, new BenchmarkAdapter())
				.registerTypeAdapter(User.class, new UserAdapter()).setPrettyPrinting()
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						return f.getAnnotation(JsonIgnore.class) != null;
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				}).create());
	}
}
