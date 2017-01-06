import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDb {
    private static Datastore datastore = null;

    public static Datastore getInstance() {
        if (datastore == null) {
            System.out.println("MongoDb connecting...");
            MongoClientURI connectionString = new MongoClientURI("mongodb://test:test@ds031975.mlab.com:31975/ads"/*System.getenv("MONGO_DB_URI")*/);
            MongoClient mongoClient = new MongoClient(connectionString);

            Morphia morphia = new Morphia();
            morphia.mapPackage("model");
            datastore = morphia.createDatastore(mongoClient,"ads" /*System.getenv("MONGO_DB_NAME")*/);
            datastore.ensureIndexes();
        }
        return datastore;
    }
}
