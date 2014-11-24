package br.com.reembolsofacil.entity.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.helper.EntityManagerHelper;

public class ViagemDao extends GenericDao{

	@SuppressWarnings("unchecked")
	public static List<EntViagem> listViagensAbertasHoje(long idUsuario){
		EntityManager em = null;
		List<EntViagem> list = null;
		try{
			em = EntityManagerHelper.getEntityManager();
			Query q = em.createQuery("select v from EntViagem v where v.usuario.idUsuario=:idUsuario and " +
					"v.aberta=true and :dtHoje between v.dataInicViagem and v.dataFimViagem " +
					"order by v.dataInicViagem");
			q.setParameter("dtHoje", new Date());
			q.setParameter("idUsuario", idUsuario);
			list = q.getResultList();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<EntViagem> listViagensAbertas(long idUsuario){
		EntityManager em = null;
		List<EntViagem> list = null;
		try{
			em = EntityManagerHelper.getEntityManager();
			Query q = em.createQuery("select v from EntViagem v where v.usuario.idUsuario=:idUsuario and " +
					"v.aberta=true order by v.dataInicViagem");
			q.setParameter("idUsuario", idUsuario);
			list = q.getResultList();
			em.clear();
		}finally{
			if(em!=null && em.isOpen())
				em.close();
		}
		return list;
	}
}
