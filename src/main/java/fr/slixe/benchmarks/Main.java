package fr.slixe.benchmarks;

import fr.litarvan.paladin.Paladin;
import fr.litarvan.paladin.PaladinBuilder;
import fr.slixe.benchmarks.http.SparkHttpServer;

public class Main
{
	private static Paladin paladin;
	
	public static void main(String[] args)
	{		
		paladin = PaladinBuilder.create(App.class)
							 			.addModule(new MyModule())
										.loadCommandLineArguments(args)
										.build();

		paladin.start(new SparkHttpServer(paladin, paladin.getConfig().get("port", int.class)));
	}
	
	public static Paladin getPaladin()
	{
		return paladin;
	}
}
