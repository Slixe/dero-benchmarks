package fr.slixe.benchmarks;

public class Benchmark {

	private int id;
	private Vendor vendor; //AMD
	private String model; //Ryzen 5 3600X	
	private long hashrate; //550h/s
	private String minerVersion; //XMRig 5.9.0
	private String owner; //Owner
	private long timestamp; //Submitted on 08/03/2020
	
	public Benchmark(int id, Vendor vendor, String model, long hashrate, String minerVersion, String owner, long timestamp)
	{
		this.id = id;
		this.vendor = vendor;
		this.model = model;
		this.hashrate = hashrate;
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

	public long getHashrate()
	{
		return hashrate;
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
		return String.format("Benchmark[vendor=%s, model=%s, hashrate=%d, owner=%s, timestamp=%d]", vendor.name(), model, hashrate, owner, timestamp);
	}

	public static enum Vendor {
		AMD,
		NVIDIA,
		INTEL
	}
}
