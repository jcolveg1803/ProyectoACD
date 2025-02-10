package proyectoACD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import proyectoACD.exceptions.UserAlreadyExistsException;
import proyectoACD.exceptions.UserNotExistsException;


public class GestorBaseDatos {
	private Connection conn=null;
	private final String BD_NAME="KartingJACV";
	private final String BD_URL="jdbc:mysql://localhost:3306/"+BD_NAME;
	private final String BD_USER="root";
	private final String BD_PASS="";
	
	
	public boolean crearEstructuraTablas()
	{
		String sqlClientes = "CREATE TABLE clientes("
				+ "id_cliente CHAR(9) PRIMARY KEY,"
				+ "nombre VARCHAR(50),"
				+ "apellido VARCHAR(100),"
				+ "email VARCHAR(100),"
				+ "telefono CHAR(9));";
		String sqlKarts = "CREATE TABLE karts("
				+ "id_kart INT PRIMARY KEY AUTO_INCREMENT,"
				+ "numero_kart INT,"
				+ "distancia_recorrida INT,"
				+ "estado ENUM(disponible, mantenimiento, retirado));";
		String sqlSesiones = "CREATE TABLE sesiones("
				+ "id_sesion INT PRIMARY KEY AUTO_INCREMENT,"
				+ "fecha TIMESTAMP,"
				+ "descripcion varchar(100));";
		String sqlParticipacion = "CREATE TABLE participaciones("
				+ "id_participacion INT PRIMARY KEY,"
				+ "id_cliente char(9),"
				+ "id_sesion INT,"
				+ "id_kart INT,"
				+ "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),"
				+ "FOREIGN KEY (id_sesion) REFERENCES sesiones(id_sesion),"
				+ "FOREIGN KEY (id_kart) REFERENCES karts(id_kart));";
		String sqlVueltas = "CREATE TABLE vueltas("
				+ "id_vuelta INT PRIMARY KEY,"
				+ "id_participacion INT,"
				+ "numero_vuelta INT,"
				+ "tiempo INT"
				+ "FOREIGN KEY (id_participacion) REFERENCES participaciones(id_participacion));";
		
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conn.prepareStatement(sqlClientes);
			sentencia.executeUpdate();
			sentencia = conn.prepareStatement(sqlKarts);
			sentencia.executeUpdate();
			sentencia = conn.prepareStatement(sqlSesiones);
			sentencia.executeUpdate();
			sentencia = conn.prepareStatement(sqlParticipacion);
			sentencia.executeUpdate();
			sentencia = conn.prepareStatement(sqlVueltas);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				sentencia.close();
				return true;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean conectar() {
		boolean res=false;
		
		try {
			conn=DriverManager.getConnection(BD_URL, BD_USER, BD_PASS);
			res=true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return res;
	}
	
	public boolean cerrar() {
		
		boolean res = false;
		try {
			conn.close();
			res=true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return res;
	}
	
	//comprueba que un usuario existe por su id
	private boolean compruebaExiste(String id)
	{
		boolean existe = false;
		
		String sql = "SELECT id_cliente FROM clientes WHERE id_cliente = ?;"; //crea la sentencia sql
		
		PreparedStatement sentencia = null; //declara un statement en el que ejecutar la consulta
		ResultSet resultado = null; //declara un resultSet para guardar el resultado de la consulta
		
		try {
			sentencia = conn.prepareStatement(sql); //inicializa la sentencia a ejecutar
			
			sentencia.setString(0, id); //establece el valor del id del cliente a la consulta
			
			resultado = sentencia.executeQuery(); //asigna el valor de la consulta al ResultSet anterior
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			if (resultado.getString(0).equals(id)) { //comprueba que el resultado obtenido es el deseado
				existe = true; //devuelve True si el usuario existe
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return existe;
	}
	
	public boolean crearUsuario(String id, String nombre, String apellidos, String email, String telefono) throws UserAlreadyExistsException
	{
		
		if (compruebaExiste(id)) //comprueba que el usuario no exista antes de crearlo
		{ 
			throw new UserAlreadyExistsException("El usuario que quieres introducir ya existe");
		}
		
		String insertSQL = "Insert into clientes(id_cliente, nombre, apellido, email, telefono) VALUES (?, ?, ?, ?, ?);";
		
		PreparedStatement op = null;
		
		try
		{
			op = conn.prepareStatement(insertSQL);
			
			op.setString(0, id);
			op.setString(1, nombre);
			op.setString(2, apellidos);
			op.setString(3, email);
			op.setString(4, telefono);
			
			op.executeUpdate();
			
		}
		catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		finally
		{
			try
			{
				op.close();
				return true;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean borrarUsuario(String id) throws UserNotExistsException
	{
		
		if (!compruebaExiste(id))
		{
			throw new UserNotExistsException("El usuario con el dni " + id + " no existe");
		}
		
		String querySQL = "DELETE FROM usuarios WHERE id = ?";
		
		PreparedStatement sentencia = null;
		
		try
		{
			sentencia = conn.prepareStatement(querySQL);
			sentencia.setString(0, id);
			sentencia.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sentencia.close();
			}
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean nuevoKart(int numeroKart, int distancia, String estado)
	{
		String query = "INSERT INTO karts(numero_kart, distancia_recorrida, estado) values(?, ?, ?);";
		
		PreparedStatement sp = null;
		
		try
		{
			sp = conn.prepareStatement(query);
			
			sp.setInt(0, numeroKart);
			sp.setInt(1, distancia);
			sp.setString(2, estado);
			
			sp.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sp.close();
				return true;
			} 
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		
		return false;
	}

	public boolean borrarKart(int idKart)
	{
		String query = "DELETE FROM karts WHERE id = ?;";
		
		PreparedStatement sp = null;
		try 
		{
			sp = conn.prepareStatement(query);
			
			sp.setInt(0, idKart);
			
			sp.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			try
			{
				sp.close();
				return true;
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean cambiarEstadoKart(int idKart, String nuevoEstado)
	{
		String query = "UPDATE karts SET estado=? where id=?;";
		
		PreparedStatement sp = null;
		
		try
		{
			sp=conn.prepareStatement(query);
			sp.setInt(0, idKart);
			sp.setString(1, nuevoEstado);
			sp.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sp.close();
				return true;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean crearSesion()
	{
		String sql = "INSERT INTO sesiones (fecha, descripcion) values (?, ?);";
		
		Date todayDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaActual = sdf.format(todayDate);
		
		PreparedStatement sp = null;
		
		try
		{
			sp = conn.prepareStatement(sql);
			
			sp.setTimestamp(0, (Timestamp) todayDate);
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(fechaActual);
		
		return false;
	}

	
}
