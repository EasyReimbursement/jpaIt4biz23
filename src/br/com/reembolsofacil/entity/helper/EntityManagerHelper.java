package br.com.reembolsofacil.entity.helper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerHelper {
	
	private static final EntityManagerFactory emf; 
	private static final ThreadLocal<EntityManager> threadLocal;
	private static final String PU_NAME = "jpaIt4biz23-persistence-unit-name";
	private static String persistenceUnitName = null;
	
	static {
		persistenceUnitName = System.getProperties().getProperty(PU_NAME);
		
		if(persistenceUnitName == null){
			//tenta recuperar do pacote padrao
		    InputStream inputStream =
		    	EntityManagerHelper.class.getClassLoader().getResourceAsStream("br/com/reembolsofacil/resources/entities.properties");
		    if(inputStream != null){
		    	Properties prop = new Properties();
		    	try {
		    		prop.load(inputStream);
				} catch (IOException e) {
				    throw new RuntimeException(e);
				}
				persistenceUnitName = prop.getProperty(PU_NAME);
				
		    }
		}
		
		if(persistenceUnitName==null)
			persistenceUnitName = "jpaIt4biz23PU";
		
		System.out.println("EntityManagerFactory configurado com: " + persistenceUnitName);
		
		emf = Persistence.createEntityManagerFactory(persistenceUnitName); 		
		threadLocal = new ThreadLocal<EntityManager>();
	}
		
	public static EntityManager getEntityManager() {
		EntityManager manager = threadLocal.get();		
		if (manager == null || !manager.isOpen()) {
			manager = emf.createEntityManager();
			threadLocal.set(manager);
		}
		return manager;
	}
	
	 public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null) em.close();
    }
    
    public static void beginTransaction() {
    	getEntityManager().getTransaction().begin();
    }
    
    public static void commit() {
    	getEntityManager().getTransaction().commit();
    }  
    
    public static Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}
	
}
