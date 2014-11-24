package br.com.reembolsofacil.test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.queries.ReadObjectQuery;
import org.eclipse.persistence.sessions.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.dao.UsuarioDao;
import br.com.reembolsofacil.entity.dao.ViagemDao;
import br.com.reembolsofacil.entity.util.UtlCalendar;
import br.com.reembolsofacil.entity.util.UtlCrypto;
import br.com.reembolsofacil.entity.util.UtlDate;
import br.com.reembolsofacil.entity.util.UtlRandom;

public class Teste {

	public static final String PERSISTENCE_UNIT_NAME = "jpaIt4biz23PU";

	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	protected static EntityTransaction tx;
	
	
	
	/*static void a(){
		
		EntUsuario u = new EntUsuario();
		u.setChaveAppUsuario("chave");
		u.setDataHora(Calendar.getInstance());
		u.setEmailUsuario("email@email.com");
		u.setTipoUsuario("cliente");
		
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}
	
	static void a2(){
		EntViagem v = new EntViagem();
		v.setDataFimViagem(Calendar.getInstance());
		v.setDataHora(Calendar.getInstance());
		v.setDataInicViagem(Calendar.getInstance());
		v.setMotivoViagem("motivo viagem");
		v.setUsuario(em.find(EntUsuario.class, 2L));
		
		em.getTransaction().begin();
		em.persist(v);
		em.getTransaction().commit();
	}*/
	
	static void a3(){
		EntUsuario u = em.find(EntUsuario.class, 2L);
		
		System.out.println(u);
		System.out.println(u.getEntViagens().get(0));
		System.out.println(u.getEntViagens().get(0).getEntDespesas().get(0));
	}
	
	/*static void a4(){
		EntDespesa d = new EntDespesa();
		d.setDataDespesa(Calendar.getInstance());
		d.setDataHora(Calendar.getInstance());
		d.setDescricaoDespesa("almoco");
		d.setTipoDespesa("refeicao");
		d.setValorDespesa(new BigDecimal(132131.654654));
		d.setViagem(em.find(EntViagem.class, 2L));
		
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}*/
	
	static void a5(){
		EntityManagerImpl e =(EntityManagerImpl) em.getDelegate();
		Session s =e.getSession();
		
		ReadObjectQuery query = new ReadObjectQuery();
		EntUsuario u = new EntUsuario();
		
		u.setChaveAppUsuario("chave");
		u.setEmailUsuario("email@email.com");
		
		query.setExampleObject(u);
		
		EntUsuario result = (EntUsuario) s.executeQuery(query);
		
		System.out.println(result);
		
	}
	
	static void a6(){
		Calendar c1 = UtlCalendar.getCalendar(1, 1, 2000);
		Calendar c2 = UtlCalendar.getCalendar(1, 1, 2000, 22, 45);
		
		System.out.println(UtlCalendar.getStringFromCalendar(c1));
		System.out.println(UtlCalendar.getStringFromCalendar(c1, "dd-MM-yyyy"));
		
		System.out.println(UtlCalendar.getStringFromCalendar(c2));
		System.out.println(UtlCalendar.getStringFromCalendar(c2, "dd-MM-yyyy HH:mm"));
	}
	
	static void a7(){
		Calendar c1 = UtlCalendar.getCalendar(1, 1, 2000);
		Calendar c2 = UtlCalendar.getCalendar(25, 11, 1988, 22, 45);
		
		System.out.println(UtlCalendar.getMedium(c1, Locale.ENGLISH));
		System.out.println(UtlCalendar.getMedium(c1, Locale.CHINA));
		
		System.out.println(UtlCalendar.getMedium(c2, Locale.JAPAN));
		System.out.println(UtlCalendar.getMedium(c2, Locale.ITALY));
		Locale loc1 = new Locale("pt", "BR");
		
		System.out.println(UtlCalendar.getShort(c1, loc1));
		System.out.println(UtlCalendar.getMedium(c1, loc1));
		System.out.println(UtlCalendar.getLong(c2, loc1));
	}
	
	static void a8(){
		EntUsuario u = new EntUsuario();
		u.setChaveAppUsuario("chave123");
		u.setEmailUsuario("email123@email.com");
		u.setTipoUsuario("cliente123");
		
		GenericDao.insert(u);
		
		System.out.println(u);
	}
	
	static void a9(){
		List<EntUsuario> usuarios = new ArrayList<EntUsuario>();

		
		usuarios = GenericDao.listAll("EntUsuario");
		System.out.println(usuarios.size());
	}
	
	static void a10(){
		EntViagem v = GenericDao.find(EntViagem.class, 2L);
		
		for(EntDespesa d:v.getEntDespesas()){
			System.out.println(d);
		}
		
		EntDespesa d = new EntDespesa();
		d.setDataDespesa(new Date());
		d.setDataHora(new Date());
		d.setDescricaoDespesa("desc");
		d.setTipoDespesa("tipo");
		d.setValorDespesa(new BigDecimal(1));
		d.setViagem(v);
		
		GenericDao.insert(d);
		
		v = GenericDao.find(EntViagem.class, 2L);
		
		for(EntDespesa d2:v.getEntDespesas()){
			System.out.println(d2);
		}
	}
	
	static void a11(){
		System.out.println(UtlDate.getStringFromDate(new Date()));
		System.out.println(UtlDate.getStringFromDate(UtlDate.getDateFromString("30/10/2010", "dd/MM/yyyy")));
		
	}
	
	static void a12(){
		String s = "Alimenta%C3%A7%C3%A3o";
		System.out.println(s);
		try {
			System.out.println(URLDecoder.decode(s,"UTF-8"));
			System.out.println(URLDecoder.decode(s,"CP1250"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	static void a13(){
		try {
			EntUsuario u = GenericDao.find(EntUsuario.class, 2L);
			List<EntViagem> viagens = u.getEntViagens();
			
			JSONArray jsonArray = new JSONArray();
			JSONObject ob1 = null;
			
			for(EntViagem v : viagens){
				ob1 = new JSONObject();
				
				ob1.put("IdViagem", v.getIdViagem());
				ob1.put("MotivoViagem", v.getMotivoViagem());
				ob1.put("DataInicViagem", UtlDate.getStringFromDate(v.getDataInicViagem(), "dd/MM/yyyy"));
				ob1.put("DataFimViagem", UtlDate.getStringFromDate(v.getDataFimViagem(), "dd/MM/yyyy"));
				
				jsonArray.put(ob1);
			}
			
			System.out.println("\n\njsonArray:\n\n"+jsonArray+"\n\n");
			
			
			JSONObject ob2 = new JSONObject(jsonArray);
			
			System.out.println(ob2);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	static void a14(){
		System.out.println(GenericDao.find(EntViagem.class, 7L));
	}
	
	static void a15(){
		List<EntDespesa> despesas = GenericDao.listAll(EntDespesa.class);
		
		for (EntDespesa d : despesas) {
			System.out.println(d);
		}
	}
	
	static void a16(){
		List<EntDespesa> despesas = GenericDao.listAll(null,null,EntDespesa.class,"asc","dataDespesa");
		
		for (EntDespesa d : despesas) {
			System.out.println(d);
		}
	}
	
	static void a17(){
		List<EntViagem> l = ViagemDao.listViagensAbertasHoje(3L);
		for (EntViagem entViagem : l) {
			System.out.println(entViagem);
		}
	}
	
	static void a18(){

		try{
			System.out.println();
			for(int i=0;i<100;i++){
				String s = UtlRandom.randomString();
				System.out.println(s);
				System.out.println(UtlCrypto.criptografaSenha(s));
				System.out.println(UtlCrypto.criptografaSenha(s));
				System.out.println(UtlCrypto.encripta(s));
				System.out.println(UtlCrypto.encripta(s));
				System.out.println();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void a19(){
		try{
		System.out.println(UtlCrypto.criptografaSenha("123"));
		System.out.println(UtlCrypto.criptografaSenha("teste2@gmail.com"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void a20(){
		List<EntViagem> viagens = GenericDao.listAll(null, null, EntViagem.class, "asc", "dataInicViagem","dataFimViagem");
	}
	
	static void a21(){
		System.out.println(UsuarioDao.existByLogin("teste"));
		System.out.println(UsuarioDao.existByLogin("asd"));
	}
	
	static void a22(){
		EntViagem v = GenericDao.find(EntViagem.class, 23L);
		Object[] ds = new Object[v.getEntDespesas().size()+1];
		for(int i=0;i<ds.length-1;i++)
			ds[i]=v.getEntDespesas().get(i);
		ds[v.getEntDespesas().size()]=v;
		GenericDao.delete(ds);
		System.out.println(v);
	}
	
	static void a23(){
		System.out.println(UtlDate.getStringFromDate(new Date(), "dd/MM/yyyy"));
	}
	
	static void a24(){
		List<EntViagem>  vs= GenericDao.listAll(EntViagem.class);
		for(EntViagem v:vs){
			System.out.println(v.getAdiantamento());
			System.out.println(v.getTotalDespesas());
			System.out.println(v.getSaldo());
			System.out.println();
		}
	}
	
	static void a25(){
		long i = 1292850209275L;
		         //64838773
		         //1292849994869
		Date d = new Date(i);
		System.out.println(d);
	}
	
	static void a26(){
		BigDecimal b1 = new BigDecimal(123.456);
		System.out.println(b1);
		System.out.println(b1.setScale(2, RoundingMode.HALF_UP));
	}
	
	static void a27(){
		long defaultWorkTime = 60*9+59;
		long segundos = defaultWorkTime%60;
		long minutos = (defaultWorkTime-segundos)/60;
		
		
		System.out.println("defaultWorkTime "+defaultWorkTime);
		System.out.println("segundos "+segundos);
		System.out.println("minutos "+minutos);
		
	}
	
	public static void main(String[] args) {
		/*emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();*/
		try{
			a27();
		}catch (Exception e) {
			e.printStackTrace();
			/*if(emf!=null && emf.isOpen())
				emf.close();*/
		}
	}
}
