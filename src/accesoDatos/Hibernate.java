package accesoDatos;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Hibernate implements I_Acceso_Datos {
    Session session;

    public Hibernate() {

        HibernateUtil util = new HibernateUtil();

        session = util.getSessionFactory().openSession();

    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer, Deposito> depositos = new HashMap<Integer, Deposito>();
        Deposito deposito;
        Query q = session.createQuery("select e from Deposito e");
        List results = q.list();

        Iterator depositosIterator = results.iterator();

        while (depositosIterator.hasNext()) {
            deposito = (Deposito) depositosIterator.next();
            depositos.put(deposito.getValor(), deposito);

        }


        return depositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String, Dispensador> dispensadores = new HashMap<String, Dispensador>();
        Dispensador dispensador;
        Query q = session.createQuery("select e from Dispensador e");
        List results = q.list();

        Iterator dispensadoresIT = results.iterator();

        while (dispensadoresIT.hasNext()) {
            dispensador = (Dispensador) dispensadoresIT.next();
            dispensadores.put(dispensador.getClave(), dispensador);

        }


        return dispensadores;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        boolean todoOK = true;
        session.beginTransaction();
        for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
            Deposito value = entry.getValue();
            session.save(value);

        }
        session.getTransaction().commit();

        return todoOK;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        boolean todoOK = true;
        session.beginTransaction();
        for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
            Dispensador value = entry.getValue();
            session.save(value);

        }
        session.getTransaction().commit();

        return todoOK;
    }
}
