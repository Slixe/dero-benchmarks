package fr.slixe.benchmarks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import fr.litarvan.paladin.OnStart;
import fr.litarvan.paladin.OnStop;
import fr.litarvan.paladin.PaladinApp;
import fr.slixe.benchmarks.service.BenchmarkService;
import spark.Spark;

@PaladinApp(name = "DERO Benchmarks", version = App.VERSION, author = "Slixe")
public class App
{
	public static final String VERSION = "0.0.1";

	private static final Logger log = LoggerFactory.getLogger("DERO Benchmarks");
	
	@Inject
	private BenchmarkService benchmarkService;
	
	@OnStart
	public void start()
	{
		log.info("Loading benchmarks...");
		benchmarkService.loadBenchmarks();
	}

	@OnStop
	public void stop()
	{
		log.info("Shutting down http service...");
		Spark.stop();
		
		log.info("Saving benchmarks...");
		benchmarkService.saveBenchmarks();
		
	}
}
