package pl.kielce.tu;

import com.datastax.driver.core.Session;
import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.User;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.CassandraShowRepository;
import pl.kielce.tu.repository.KeyspaceRepository;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.session.LoginService;
import pl.kielce.tu.util.PropertiesUtil;
import pl.kielce.tu.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ciepluchs
 */
public class CinemaApp {

    public static final String SIMPLE_STRATEGY = "SimpleStrategy";
    private static final String STORE = "store";
    private static final String CASSANDRA = "cassandra";
    private static final String SCYLLA_DB = "scyllaDb";
    private static final String CASSANDRA_HOST_PROPERTY = "store.cassandra.host";
    private static final String CASSANDRA_PORT_PROPERTY = "store.cassandra.port";
    private static final String CASSANDRA_KEYSPACE_PROPERTY = "store.cassandra.keyspace";

    public static void main(String[] args) {
        ShowRepository showRepository = initializeShowRepository();
        if (showRepository == null) {
            System.exit(0);
        }
        LoginService loginService = new LoginService(getDemoUsers());
        ViewUtil.cls();
        while (true) {
            System.out.println("################################################ LOGIN  #################################################");
            ViewUtil.displayLoginPrompt(loginService);
            if (pl.kielce.tu.session.Session.getUser() == null) {
                ViewUtil.cls();
                System.out.println("Wrong personal ID / password!");
                continue;
            }
            ViewUtil.displayMainMenu(showRepository);
            ViewUtil.cls();
        }
    }

    private static Map<String, User> getDemoUsers() {
        Map<String, User> users = new HashMap<>();
        User customer = new User("Adam", "Nowak", "12345678912", "pass123", Role.CUSTOMER);
        User travelAgent = new User("Jan", "Kowalski", "21987654321", "password123", Role.EMPLOYEE);
        users.put(customer.getPersonalId(), customer);
        users.put(travelAgent.getPersonalId(), travelAgent);
        return users;
    }

    private static ShowRepository initializeShowRepository() {
        String store = PropertiesUtil.getProperty(STORE);
        if (CASSANDRA.equals(store)) {
            Session session = initializeCassandraSession();
            initializeKeyspace(session);
            return new CassandraShowRepository(session);
//        } else if (SCYLLA_DB.equals(store)) {
//            return new CassandraShowRepository(session);
        } else {
            System.err.println("Incorrect configuration!");
            return null;
        }
    }

    private static void initializeKeyspace(Session session) {
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        String keyspace = PropertiesUtil.getProperty(CASSANDRA_KEYSPACE_PROPERTY);
        keyspaceRepository.createKeyspace(keyspace, SIMPLE_STRATEGY, 1);
        keyspaceRepository.useKeyspace(keyspace);
    }

    private static Session initializeCassandraSession() {
        String host = PropertiesUtil.getProperty(CASSANDRA_HOST_PROPERTY);
        String port = PropertiesUtil.getProperty(CASSANDRA_PORT_PROPERTY);
        CassandraConnector connector = new CassandraConnector();
        connector.connect(host, port);
        return connector.getSession();
    }
}
