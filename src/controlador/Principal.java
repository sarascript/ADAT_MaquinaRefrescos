package controlador;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import accesoDatos.AccesoMongo;
import logicaRefrescos.Deposito;

public class Principal {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		java.util.logging.Logger.getLogger("org.mongodb").setLevel(Level.OFF);
		System.out.println("Inicio Ejecucion - Maquina Refrescos");
		Scanner miScanner = new Scanner(System.in); // Para leer las opciones de teclado
		AccesoMongo am = new AccesoMongo();
		am.obtenerDepositos();

		Controlador miControlador = new Controlador(miScanner);

	}

}
