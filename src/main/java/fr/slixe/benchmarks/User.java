package fr.slixe.benchmarks;

public class User {

	private final String username;
	@JsonIgnore
	private final String hashedPassword;
	@JsonIgnore
	private final String salt;

	public User(String username, String hashedPassword, String salt)
	{
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getHashedPassword()
	{
		return this.hashedPassword;
	}
	
	public String getSalt()
	{
		return this.salt;
	}
}
