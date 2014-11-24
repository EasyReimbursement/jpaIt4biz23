package br.com.reembolsofacil.entity.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.helper.EntityManagerHelper;

public class UsuarioDao extends GenericDao{

	public static EntUsuario getByLoginSenha(String login, String senha){
		EntityManager em = null;
		EntUsuario usuario=null;
		try{
			em = EntityManagerHelper.getEntityManager();
			Query q = em.createQuery("select u from EntUsuario u where u.login=:login and u.senha=:senha ");
			q.setParameter("login", login);
			q.setParameter("senha", senha);
			usuario = (EntUsuario)q.getSingleResult();
			em.detach(usuario);
		}catch (NoResultException e) {
			// nenhum usuario existente
		}catch (NonUniqueResultException e) {
			// mais de um usuario com mesmo login/senha
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return usuario;
	}
	
	public static boolean existByLogin(String login){
		EntityManager em = null;
		EntUsuario usuario=null;
		try{
			em = EntityManagerHelper.getEntityManager();
			Query q = em.createQuery("select u from EntUsuario u where u.login=:login ");
			q.setParameter("login", login);
			usuario = (EntUsuario)q.getSingleResult();
			em.detach(usuario);
		}catch (NoResultException e) {
			// nenhum usuario existente
		}catch (NonUniqueResultException e) {
			// mais de um usuario com mesmo login/senha
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return usuario!=null;
	}
}
