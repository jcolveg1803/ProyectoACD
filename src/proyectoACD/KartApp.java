package proyectoACD;

import java.util.Scanner;

public class KartApp {
	
	static Scanner sc = new Scanner(System.in);
	private GestorBaseDatos gDb;
	
	public KartApp()
	{
		gDb = new GestorBaseDatos(); 
	}
	
	
	private int menuInicio()
	{
		System.out.println("Bienvenido!");
		System.out.println("1- Iniciar sesión");
		System.out.println("2- Crear nuevo usuario");
		System.out.println("3- Salir");
		System.out.println("Elija una opción: ");
		int opt = sc.nextInt();
		
		return opt;
	}
	
	private void menUsuario(String nombreUsuario)
	{
		System.out.println("Bienvenido " + nombreUsuario + ", ¿qué desea hacer?");
		System.out.println("1-Unirse a una sesión");
		System.out.println("2-Ver mis sesiones anteriores");
		System.out.print("3-Salir");
		int opt = sc.nextInt();
	}
	
}
