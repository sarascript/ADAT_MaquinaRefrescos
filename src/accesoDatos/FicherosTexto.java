package accesoDatos;

import java.io.*;
import java.util.*;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

    File archivo = new File("C:\\Users\\Pablo\\Desktop\\U-tad Segundo Curso\\WorkSpace\\ADAT_MaquinaRefrescos_Alumnos\\Ficheros\\datos\\depositos.txt");
    File archivodis = new File("C:\\Users\\Pablo\\Desktop\\U-tad Segundo Curso\\WorkSpace\\ADAT_MaquinaRefrescos_Alumnos\\Ficheros\\datos\\dispensadores.txt");

    public FicherosTexto() {
        System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() throws FileNotFoundException {

        HashMap<Integer, Deposito> depositosCreados = new HashMap<>();

        Scanner sc = new Scanner(archivo);
        while (sc.hasNextLine()) {

            Deposito depositoauxiliar = new Deposito();

            String arrayauxiliar = sc.nextLine();
            String[] parts1 = arrayauxiliar.split(";");

            depositoauxiliar.setId(Integer.parseInt(parts1[1]));
            depositoauxiliar.setNombreMoneda(parts1[0]);
            depositoauxiliar.setValor(Integer.parseInt(parts1[1]));
            depositoauxiliar.setCantidad(Integer.parseInt(parts1[2]));

            depositosCreados.put(Integer.parseInt(parts1[1]), depositoauxiliar);

        }


        return depositosCreados;
    }


    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() throws FileNotFoundException {

        HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();

        Scanner sc = new Scanner(archivodis);
        while (sc.hasNextLine()) {

            Dispensador dispensadorauxiliar = new Dispensador();

            String arrayauxiliar = sc.nextLine();
            String[] parts = arrayauxiliar.split(";");

            dispensadorauxiliar.setNombreProducto(parts[1]);
            dispensadorauxiliar.setClave(parts[0]);
            dispensadorauxiliar.setPrecio(Integer.parseInt(parts[2]));
            dispensadorauxiliar.setCantidad(Integer.parseInt(parts[3]));

            dispensadoresCreados.put(parts[0], dispensadorauxiliar);

        }


        return dispensadoresCreados;

    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

        boolean todoOK = true;
        try {
            FileWriter fw = new FileWriter(archivo, false);
            BufferedWriter bw = new BufferedWriter(fw);
            fw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
            try {
                FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                fw.write(entry.getValue().getNombreMoneda() + ";");
                fw.write(entry.getKey() + ";");
                fw.write(entry.getValue().getCantidad() + ";" + "\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return todoOK;

    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

        boolean todoOK = true;
        try {
            FileWriter fw = new FileWriter(archivodis, false);
            BufferedWriter bw = new BufferedWriter(fw);
            fw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
            try {
                FileWriter fw = new FileWriter(archivodis, true);
                BufferedWriter bw = new BufferedWriter(fw);
                fw.write(entry.getKey() + ";");
                fw.write(entry.getValue().getNombreProducto() + ";");
                fw.write(entry.getValue().getCantidad() + ";");
                fw.write(entry.getValue().getCantidad() + ";" + "\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return todoOK;
    }

} // Fin de la clase