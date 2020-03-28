package fr.slixe.benchmarks.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.slixe.benchmarks.Benchmark;

@Singleton
public class BenchmarkService {

	private static final Type BENCHMARK_TYPE = new TypeToken<List<Benchmark>>(){}.getType();
	private static final Logger log = LoggerFactory.getLogger("DERO Benchmark Service");

	private final List<Benchmark> confirmedBenchmarks = new ArrayList<>();
	private final List<Benchmark> unconfirmedBenchmarks = new ArrayList<>();
	
	private final File confirmedBenchmarksFile = new File("benchmarks-confirmed.json");
	private final File unconfirmedBenchmarksFile = new File("benchmarks-unconfirmed.json");
	
	@Inject
	private Gson gson;
	
	private void loadBenchmarks(File file, List<Benchmark> benchmarks)
	{
		if (!file.exists())
			try {
				file.createNewFile();
				saveBenchmarks(file, benchmarks);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		else
			try {
				JsonReader jsonReader = new JsonReader(new FileReader(file));
				benchmarks.addAll(gson.fromJson(jsonReader, BENCHMARK_TYPE));
				log.info(benchmarks.toString());
				jsonReader.close();	
			} catch (IOException e) {
				log.error(e.getMessage());
			}
	}

	private void saveBenchmarks(File file, List<Benchmark> benchmarks)
	{
		try (FileWriter fileWriter = new FileWriter(file)) {
			gson.toJson(benchmarks, BENCHMARK_TYPE, fileWriter);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public void loadBenchmarks()
	{
		loadBenchmarks(this.confirmedBenchmarksFile, this.confirmedBenchmarks);
		loadBenchmarks(this.unconfirmedBenchmarksFile, this.unconfirmedBenchmarks);
	}

	public void saveBenchmarks()
	{
		saveBenchmarks(this.confirmedBenchmarksFile, this.confirmedBenchmarks);
		saveBenchmarks(this.unconfirmedBenchmarksFile, this.unconfirmedBenchmarks);
	}
	
	public boolean addUnconfirmedBenchmarks(Benchmark benchmark)
	{
		boolean result = this.unconfirmedBenchmarks.add(benchmark);
		saveBenchmarks(this.unconfirmedBenchmarksFile, this.unconfirmedBenchmarks);

		return result;
	}

	public boolean confirmBenchmark(int benchID)
	{
		Optional<Benchmark> opt = this.unconfirmedBenchmarks.parallelStream().filter(e -> e.getId() == benchID).findFirst();

		if (opt.isPresent()) {
			Benchmark benchmark = opt.get();
			this.unconfirmedBenchmarks.remove(benchmark);
			benchmark.setId(lastBenchId());
			this.confirmedBenchmarks.add(benchmark);
			saveBenchmarks();
		}
		
		return opt.isPresent();
	}

	public boolean removeBenchmark(int benchID)
	{
		boolean result = this.unconfirmedBenchmarks.removeIf(e -> e.getId() == benchID);

		if (!result) {
			result = this.confirmedBenchmarks.removeIf(e -> e.getId() == benchID);
		}

		if (result) {
			this.saveBenchmarks();
		}

		return result;
	}

	public int lastUnconfirmedBenchId()
	{
		if (this.unconfirmedBenchmarks.size() == 0)
			return 0;
		int b = this.unconfirmedBenchmarks.get(this.unconfirmedBenchmarks.size() - 1).getId() + 1;

		return b;
	}

	public int lastBenchId()
	{
		if (this.confirmedBenchmarks.size() == 0)
			return 0;

		int a = this.confirmedBenchmarks.get(this.confirmedBenchmarks.size() - 1).getId() + 1;
		return a;
	}
	
	public List<Benchmark> getBenchmarks()
	{
		return this.confirmedBenchmarks;
	}
	
	public List<Benchmark> getUnconfirmedBenchmarks()
	{
		return this.unconfirmedBenchmarks;
	}
}
