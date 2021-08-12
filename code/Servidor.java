package projeto4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
	public static void main(String[] args)
	{
		try
		{
			Scholar obj = new Scholar();	
			Registry registry = LocateRegistry.createRegistry(8000);
			registry.bind("Scholar", obj);
		}
		
		catch (Exception e)
		{                
			System.err.println("Ocorreu um erro:");
			e.printStackTrace();
		}
	}

}
