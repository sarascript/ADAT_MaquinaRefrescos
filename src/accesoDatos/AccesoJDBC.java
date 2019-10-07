package accesoDatos;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

    private String driver, urlbd, user, password; // Datos de la conexion
    private Connection conn1;

    public AccesoJDBC() {
        System.out.println("ACCESO A DATOS - Acceso JDBC");

        try {
            HashMap<String, String> datosConexion;

            LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
            datosConexion = properties.getHash();

            driver = datosConexion.get("driver");
            urlbd = datosConexion.get("urlbd");
            user = datosConexion.get("user");
            password = datosConexion.get("password");
            conn1 = null;

            Class.forName(driver);
            conn1 = DriverManager.getConnection(urlbd, user, password);
            if (conn1 != null) {
                System.out.println("Conectado a la base de datos");
            }

        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
            //e1.printStackTrace();
            System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("ERROR: No se ha podido conectar con la base de datos");
            System.out.println(e.getMessage());
            //e.printStackTrace();
            System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
            System.exit(1);
        }
    }

    public int cerrarConexion() {
        try {
            conn1.close();
            System.out.println("Cerrada conexion");
            return 0;
        } catch (Exception e) {
            System.out.println("ERROR: No se ha cerrado corretamente");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() throws SQLException {

        HashMap<Integer, Deposito> deposito = new HashMap<>();

        String Query = "SELECT * FROM depositos";
        PreparedStatement preparedStmt = conn1.prepareStatement(Query);
        java.sql.ResultSet resultSet;
        resultSet = preparedStmt.executeQuery(Query);

        while (resultSet.next()) {

            Deposito depositoauxiliar = new Deposito();

            System.out.println("ID: " + " " + resultSet.getString("ID") + "\n" + "Nombre: " + " "
                    + resultSet.getString("Nombre") + "\n" + "valor" + " "
                    + resultSet.getString("valor") + "\n" + "cantidad: " + " "
                    + resultSet.getString("cantidad") + "\n");

            depositoauxiliar.setId(resultSet.getInt("ID"));
            depositoauxiliar.setNombreMoneda(resultSet.getString("nombre"));
            depositoauxiliar.setValor(resultSet.getInt("valor"));
            depositoauxiliar.setCantidad(resultSet.getInt("cantidad"));

            deposito.put(resultSet.getInt("valor"), depositoauxiliar);
        }


        return deposito;

    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() throws SQLException {

        HashMap<String, Dispensador> dispensador = new HashMap<>();

        String Query = "SELECT * FROM dispensadores";
        PreparedStatement preparedStmt = conn1.prepareStatement(Query);
        java.sql.ResultSet resultSet;
        resultSet = preparedStmt.executeQuery(Query);

        while (resultSet.next()) {

            Dispensador dispensadorauxiliar = new Dispensador();

            System.out.println("ID: " + " " + resultSet.getString("ID") + "\n" + "Nombre: " + " "
                    + resultSet.getString("Nombre") + "\n" + "clave" + " "
                    + resultSet.getString("clave") + "\n" + "cantidad: " + " "
                    + resultSet.getString("cantidad") + "\n" + "precio: " + " "
                    + resultSet.getString("precio") + "\n");

            dispensadorauxiliar.setId(resultSet.getInt("ID"));
            dispensadorauxiliar.setNombreProducto(resultSet.getString("nombre"));
            dispensadorauxiliar.setPrecio(resultSet.getInt("precio"));
            dispensadorauxiliar.setCantidad(resultSet.getInt("cantidad"));
            dispensadorauxiliar.setClave(resultSet.getString("clave"));

            dispensador.put(resultSet.getString("ID"), dispensadorauxiliar);
        }


        return dispensador;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) throws SQLException {
        boolean todoOK = true;

        for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
            Integer k = entry.getKey();

            Deposito depositoauxiliar;

            depositoauxiliar = depositos.get(k);

            int id = depositoauxiliar.getId();
            String nombre = depositoauxiliar.getNombreMoneda();
            int valor = depositoauxiliar.getValor();
            int cantidad = depositoauxiliar.getCantidad();

            String query = "update depositos set cantidad=? where id=?";

            int r = 0;

            PreparedStatement preparedStmt = conn1.prepareStatement(query);
            preparedStmt.setInt(2, id);
            preparedStmt.setInt(1, cantidad);

            r = preparedStmt.executeUpdate();


        }


        return todoOK;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) throws SQLException {
        boolean todoOK = true;

        for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {

            String k = entry.getKey();

            Dispensador dispensadorauxiliar;

            dispensadorauxiliar = dispensadores.get(k);

            int id = dispensadorauxiliar.getId();
            int cantidad = dispensadorauxiliar.getCantidad();

            String query = "update dispensadores set cantidad=? where id=?";

            int r = 0;

            PreparedStatement preparedStmt = conn1.prepareStatement(query);
            preparedStmt.setInt(2, id);
            preparedStmt.setInt(1, cantidad);

            r = preparedStmt.executeUpdate();


        }

        return todoOK;
    }

} // Fin de la clase