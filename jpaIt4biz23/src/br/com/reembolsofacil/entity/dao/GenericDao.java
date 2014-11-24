package br.com.reembolsofacil.entity.dao;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.reembolsofacil.entity.helper.EntityManagerHelper;
import br.com.reembolsofacil.entity.util.UtlEntity;
import br.com.reembolsofacil.entity.vo.OrderParametersVo;

public class GenericDao {

	public static <T> T find(Class<T> clazz,Object id){
		EntityManager em = null;
		
		T entity = null;
		try{
			em = EntityManagerHelper.getEntityManager();
			entity = em.find(clazz, id);
			em.detach(entity);
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return entity;
	}
	
	public static <T> void delete(T... ts){
		EntityManager em = null;
		
		try{
			em = EntityManagerHelper.getEntityManager();
			EntityTransaction tx = em.getTransaction();
			Object[] ents = new Object[ts.length];
			if (!tx.isActive())
				tx.begin();
			
			for(int i=0;i<ts.length;i++)
				ents[i] = em.merge(ts[i]);
			for(Object entidade:ents)
				em.remove(entidade);
			
			tx.commit();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
	}
	
	public static <T> void update(T... ts){
		EntityManager em = null;
		
		try{
			em = EntityManagerHelper.getEntityManager();
			em.getTransaction().begin();
			for(T t:ts)
				t=em.merge(t);
			em.getTransaction().commit();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
	}
	
	public static <T> void insert(T... ts){
		EntityManager em = null;
		
		try{
			em = EntityManagerHelper.getEntityManager();
			em.getTransaction().begin();
			for(T t:ts)
				em.persist(t);
			
			em.getTransaction().commit();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		
	}
	
	/**
	 * Faz um list all simples, não ordenando o resultado (não recomendado para paginação)
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> listAll(String entity){
		EntityManager em = null;
		List<T> list = null;
		try{
			em = EntityManagerHelper.getEntityManager();
			
			list = em.createQuery("select t from "+entity+" as t").getResultList();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return list;
	}
	
	public static <T > List<T> listAll(Class<T> entidade) {
        return listAll(null, null, entidade, "asc", UtlEntity.getIdFieldName(entidade));
    }

    public static <T> List<T> listAll(Integer firstResult, Integer maxResult, Class<T> entidade) {
        return listAll(firstResult, maxResult, entidade, "asc", UtlEntity.getIdFieldName(entidade));
    }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> listAll(Integer firstResult, Integer maxResult, Class<T> entity,
			String order, String... orderFields) {
    	
    	EntityManager em = null;
    	OrderParametersVo orderParametersVo = new OrderParametersVo(order, orderFields);
    	    	
        Query q = null;
        List<T> lista = Collections.EMPTY_LIST;
        try {
        	StringBuilder[] sbOrderArray= processOrderParametersVo(entity, orderParametersVo);
	        em = EntityManagerHelper.getEntityManager();
	
	        StringBuilder sb = new StringBuilder();
	        sb.append("select e from ").append(entity.getSimpleName()).append(" as e ")
	        	.append(sbOrderArray[0]).append("order by ").append(sbOrderArray[1]);
	                
            q = em.createQuery(sb.toString());
            if (firstResult != null && firstResult >= 0) {
                q.setFirstResult(firstResult);
            }
            if (maxResult != null && maxResult > 0) {
                q.setMaxResults(maxResult);
            }
            lista = q.getResultList();
            for (T ih : lista) 
                em.detach(ih);
            
        }catch(Exception e){
        	String msgEx="Exceção em listAll, parâmetros: firstResult="
        		+firstResult+" maxResult="+maxResult+", order="+order
        		+" entidade="+(entity==null?null:entity.getName());
        	for(String s:orderFields)
        		msgEx+=", orderFields="+s;
        	throw new RuntimeException(msgEx, e);
        } finally {
        	if(em!=null && em.isOpen())
        		em.close();
        }
        return lista;
    }
    
    /**
     * No jpa dado duas entidades A e B. Tendo uma associação manyToOne de A para B. quando se deseja listar A ordenado por um campo de B
     * 
     * @param <T>
     * @param entity
     * @param orderParametersVo
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    private  static <T> StringBuilder[] processOrderParametersVo(Class<T> entity, OrderParametersVo orderParametersVo) 
    	throws SecurityException, NoSuchFieldException{
    	
    	StringBuilder sbFrom = new StringBuilder();
    	StringBuilder sbOrder = new StringBuilder();
    	int internalCount=0;
    	List<Object[]> o=orderParametersVo.getLista();
    	boolean isFirst=true;
    	
		for(Object[] ob:o){
			String s =(String) ob[0];
			String s2=null;
			String s3=null;
			//o tratamento diferenciado ocorre apenas em uma associação direta, ex.: a.b.field
			//se for a.b.c.field ou mais o tratamento não ocorre
			if(s.contains(".") && s.indexOf(".") == s.lastIndexOf(".")){
				s2=s.substring(0,s.indexOf("."));
				s3=s.substring(s.indexOf("."));
			
				sbFrom.append(" left join e.").append(s2).append(" as c").append(internalCount).append(" ");
				if(!isFirst)
					sbOrder.append(", ");
				sbOrder.append("c").append(internalCount++).append(s3).append(" ").append(((String)ob[1]));
							
			}else{
				if(!isFirst)
					sbOrder.append(", ");
				else
					isFirst=false;
				sbOrder.append("e.").append(ob[0]).append(" ").append(((String)ob[1])).append(" ");
			}
		}
		return new StringBuilder[]{sbFrom,sbOrder};
    }
}
