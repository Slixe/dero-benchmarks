package fr.slixe.benchmarks;

public class Benchmark {

	private int id;
	private Vendor vendor; //AMD
	private String model; //Ryzen 5 3600X
	private String memory;
	private long hashrate; //550h/s
	private int watts;
	private String minerVersion; //XMRig 5.9.0
	private String owner; //Owner
	private long timestamp; //Submitted on 08/03/2020

	public Benchmark(int id, Vendor vendor, String model, String memory, long hashrate, String minerVersion, String owner, long timestamp)
	{
		this(id, vendor, model, memory, hashrate, 0, minerVersion, owner, timestamp);
	}

	public Benchmark(int id, Vendor vendor, String model, String memory, long hashrate, int watts, String minerVersion, String owner, long timestamp)
	{
		this.id = id;
		this.vendor = vendor;
		this.model = model;
		this.memory = memory;
		this.hashrate = hashrate;
		this.watts = watts;
		this.minerVersion = minerVersion;
		this.owner = owner;
		this.timestamp = timestamp;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}

	public Vendor getVendor()
	{
		return vendor;
	}

	public String getModel()
	{
		return model;
	}

	public String getMemory()
	{
		return memory;
	}

	public long getHashrate()
	{
		return hashrate;
	}

	public int getWatts()
	{
		return watts;
	}
	
	public String getMinerVersion()
	{
		return minerVersion;
	}

	public String getOwner()
	{
		return owner;
	}

	public long getTimestamp()
	{
		return timestamp;
	}
	
	@Override
	public String toString()
	{
		return String.format("Benchmark[vendor=%s, model=%s, memory=%s, hashrate=%d, watts=%d, owner=%s, timestamp=%d]", vendor.name(), model, memory, hashrate, watts, owner, timestamp);
	}

	public static enum Vendor {
		AMD,
		NVIDIA,
		INTEL,
		ARM,
		OTHER
	}
}
