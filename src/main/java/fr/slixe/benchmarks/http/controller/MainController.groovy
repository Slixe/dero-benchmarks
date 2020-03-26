package fr.slixe.benchmarks.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;

import fr.litarvan.paladin.http.Controller;
import fr.litarvan.paladin.http.routing.RequestParams;
import fr.slixe.benchmarks.Benchmark;
import fr.slixe.benchmarks.Benchmark.Vendor
import fr.slixe.benchmarks.service.BenchmarkService;

public class MainController extends Controller {

	private static final Logger log = LoggerFactory.getLogger("DERO HTTP Controller")

	@Inject
	private BenchmarkService benchmarkService

	@Inject
	private Gson gson

	/**
	 * accessible from: api/benchmarks
	 * @return all confirmed benchmarks
	 */
	def benchmarks()
	{
		log.debug("Benchmarks requested")

		return benchmarkService.getBenchmarks()
	}

	def unconfirmedBenchmarks()
	{
		log.debug("Unconfirmed Benchmarks requested")

		return benchmarkService.getUnconfirmedBenchmarks()
	}
	
	/** Submit a benchmark
	 * @param Vendor vendor (AMD/INTEL/NVIDIA)
	 * @param String model (Ryzen 5 3600X)
	 * @param long hashrate (in h/s)
	 * @param String minerVersion (XMRig 5.9.0)
	 * @param String owner (Slixe)
	 */
	@RequestParams(required = ["vendor", "model", "hashrate", "minerVersion", "owner"])
	def submit(Vendor vendor, String model, long hashrate, String minerVersion, String owner)
	{
		log.debug("A new benchmark has been submitted!")
		
		Benchmark benchmark = new Benchmark(benchmarkService.lastBenchId(), vendor, model, hashrate, minerVersion, owner)
		benchmarkService.addUnconfirmedBenchmarks(benchmark)

		[
			success: true,
			message: "Your benchmark has been submitted! It will be reviewed soon. Thanks.",
			result: null
		]
	}

	/** Confirm a submitted benchmark.
	 * accessible from: api/confirm
	 * @param int benchID
	 */
	@RequestParams(required = ["benchID"])
	def confirm(int benchID)
	{
		log.debug("Confirmation of Benchmark $benchID")
		
		def result = benchmarkService.confirmBenchmark(benchID)

		[
			success: result,
			message: result ? "The benchmark $benchID is now confirmed!" : "The benchmark $benchID was not found!",
			result: null
		]
	}

	/** Delete a benchmark using the unique ID.
	 * accessible from: api/delete
	 * @param int benchID
	 */
	@RequestParams(required = ["benchID"])
	def delete(int benchID)
	{
		log.debug("Removing Benchmark $benchID")
		
		def result = benchmarkService.removeBenchmark(benchID)

		[
			success: result,
			message: result ? "The benchmark $benchID has been removed!" : "Benchmark $benchID not found!",
			result: null
		]
	}
}
