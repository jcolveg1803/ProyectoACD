package proyectoACD;

import java.util.Scanner;

public class KartApp {
	
	static Scanner sc = new Scanner(System.in);
	private GestorBaseDatos gDb;
	
	public KartApp()
	{
		gDb = new GestorBaseDatos(); 
	}
	
	public void appInit()
	{
		int opc = -1;
		while(opc!=3)
		{
			menuInicio();
			opc = sc.nextInt(); sc.nextLine();
			switch(opc)
			{
				case 1:
					menuUsuario(inicioSesion());
					break;
				case 2:
					menuAdmin();
					break;
				case 3:
					break;
				default:
					break;
			}
		}
			
	}
	
	private void menuInicio()
	{
		System.out.println("Bienvenido!");
		System.out.println("1- Iniciar sesión");
		System.out.println("2- Crear nuevo usuario");
		System.out.println("3- Salir");
		System.out.println("Elija una opción: ");
	}
	
	private void menuUsuario(String nombreUsuario)
	{

		System.out.println("Bienvenido " + nombreUsuario + ", ¿qué desea hacer?");
		System.out.println("1-Unirse a una sesión");
		System.out.println("2-Ver mis sesiones anteriores");
		System.out.println("3-Cambiar información personal");
		System.out.println("4-Darse de baja");
		System.out.println("5-Cerrar sesión");

	}
	
	private void menuAdmin()
	{
		System.out.println("Bienvenido, ¿qué desea hacer?");
		System.out.println("1-Crear sesión");
		System.out.println("2-Ver mis sesiones anteriores");
		System.out.println("3-Cambiar información personal");
		System.out.println("4-Darse de baja a un usuario");
		System.out.println("5-Cerrar sesión");
	}
	
	private String inicioSesion()
	{
		System.out.print("Introduzca su dni: ");

		return sc.nextLine();
	}
	
}
