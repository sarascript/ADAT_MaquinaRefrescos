package accesoDatos;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import javassist.bytecode.Descriptor.Iterator;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoMongo implements I_Acceso_Datos{
	
	MongoClient mongo = crearConexion();
	DB db = mongo.getDB("maquinarefrescos");
	DBCollection tableDepositos = db.getCollection("depositos");
	DBCollection tableDispensadores = db.getCollection("dispensadores");

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() throws FileNotFoundException, SQLException {
		DBCursor cur = tableDepositos.find();
		HashMap<Integer, Deposito> depositos= null;
		int i = 0;
		while (cur.hasNext()) {
			i++;
			Deposito dep = new Deposito(cur.next().get("nombre").toString(), Integer.parseInt(cur.next().get("valor").toString()), Integer.parseInt(cur.next().get("cantidad").toString()));
			depositos.put(i, dep);
		}
		return depositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() throws FileNotFoundException, SQLException {
		DBCursor cur = tableDispensadores.find();
		HashMap<Integer, Dispensador> dispensadores= null;
		int i = 0;
		while (cur.hasNext()) {
			i++;
			Dispensador dep = new Dispensador(cur.next().get("clave").toString(), cur.next().get("nombre").toString(), Integer.parseInt(cur.next().get("precio").toString()), Integer.parseInt(cur.next().get("cantidad").toString()));
			dispensadores.put(i, dep);
		}
		return dispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) throws SQLException {
		BasicDBObject document1 = new BasicDBObject();
		depositos.values();
		document1.put("nombreColor", "blanco");
		tableDepositos.insert(document1);
		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) throws SQLException {
		BasicDBObject document1 = new BasicDBObject();
		dispensadores.values();
		document1.put("nombreColor", "blanco");
		tableDepositos.insert(document1);
		return false;
	}
	
	private static MongoClient crearConexion() {
		MongoClient mongo = null;
		mongo = new MongoClient("localhost", 27017);
		System.out.println("Conexión establecida");
		return mongo;
	}

}
