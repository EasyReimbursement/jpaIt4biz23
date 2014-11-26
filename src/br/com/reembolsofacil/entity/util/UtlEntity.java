package br.com.reembolsofacil.entity.util;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.metamodel.Metamodel;

import br.com.reembolsofacil.entity.helper.EntityManagerHelper;

public class UtlEntity {

	/**
	 * Apenas para entidades já carregadas, lança exceção se for uma entidade ainda não persistida
	 * @param entity
	 * @return Objeto que representa o id (int, long, EmbeddedId, etc). obs.: Testar com idClass
	 */
	public static Object getEntityId(Object entity){
		EntityManager em = null;
		Object idObject = null;

		try{
			em = EntityManagerHelper.getEntityManager();
			idObject = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return idObject;
	}
	
	/**
	 * Retorna o nome do atributo (java) usado como chave primária. Note que este nome pode ser
	 * diferente do nome da coluna usada como pk. Não serve  para chave primárias composta.
	 * @param Class c classe da entidade
	 * @return String nome do atributo da chave primária
	 */
	public static String getIdFieldName(Class<?> c){
		String idField=null;

		if(c.getAnnotation(Entity.class)==null)
		    throw new RuntimeException("Classe informada não é uma entidade, não possui @Entity");
		
		Field[] fields = c.getDeclaredFields();
		
		for (Field field : fields) {
			if((field.getAnnotation(Id.class))!=null){
				idField = field.getName();
				break;
			}
		}
				
		return idField;
	}
	
	/*public static void d(){
		EntityManager em = null;
		Object idObject = null;
		Metamodel metamodel = null;
		try{
			em = EntityManagerHelper.getEntityManager();
			metamodel = em.getEntityManagerFactory().getMetamodel();
			
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
	}*/
}
