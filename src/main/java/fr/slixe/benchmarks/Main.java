package fr.slixe.benchmarks;

import fr.litarvan.paladin.Paladin;
import fr.litarvan.paladin.PaladinBuilder;
import fr.slixe.benchmarks.http.SparkHttpServer;

public class Main
{
	public static void main(String[] args)
	{		
		Paladin paladin = PaladinBuilder.create(App.class)
							 			.addModule(new MyModule())
										.loadCommandLineArguments(args)
										.build();
		
		paladin.start(new SparkHttpServer(paladin, paladin.getConfig().get("port", int.class)));
	}
}
