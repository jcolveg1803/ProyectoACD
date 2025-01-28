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
		
		do //bucle que controla que el usuario introduzca un número entero
		{
			System.out.println("Bienvenido!");
			System.out.println("1- Iniciar sesión");
			System.out.println("2- Crear nuevo usuario");
			System.out.println("3- Salir");
			System.out.println("Elija una opción: ");			
		}while(!sc.hasNextInt());
		
		return sc.nextInt();
	}
	
	private int menuUsuario(String nombreUsuario)
	{
		do //bucle que controla que el usuario introduzca un número entero
		{
			System.out.println("Bienvenido " + nombreUsuario + ", ¿qué desea hacer?");
			System.out.println("1-Unirse a una sesión");
			System.out.println("2-Ver mis sesiones anteriores");
			System.out.println("3-Cambiar información personal");
			System.out.println("4-Darse de baja");
			System.out.println("5-Cerrar sesión");			
		}while(!sc.hasNextInt());
		
		return sc.nextInt();
	}
	
}
