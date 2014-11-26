package br.com.reembolsofacil.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.dao.UsuarioDao;
import br.com.reembolsofacil.entity.dao.ViagemDao;
import br.com.reembolsofacil.entity.util.UtlDate;
import br.com.reembolsofacil.entity.util.UtlEntViagem;

@WebServlet("/ServiceServlet")
public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Actions
	private static final int GET_VIAGENS 	 = 0;
	private static final int SEND_DESPESA 	 = 1;
	private static final int GET_DESPESAS 	 = 2;
	private static final int GET_USUARIO 	 = 3;
	private static final int GET_ALL_VIAGENS = 4;
	private static final int ADD_USUARIO 	 = 5;
	private static final int SEND_VIAGEM 	 = 6;
	private static final int DELETE_VIAGEM 	 = 7;
	private static final int DELETE_DESPESA  = 8;
	
	private static final String RETURN_STATUS  = "RETURN_STATUS";
	private static final String RETURN_MESSAGE = "RETURN_MESSAGE";
	//Return status
	private static final int STATUS_ERROR		= 0;
	private static final int STATUS_OK			= 1;
	private static final int STATUS_EMPTY		= 2;
	private static final int STATUS_EMPTY_LIST  = 3;
	
    public ServiceServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request,response);
	}

	/**
	 * Pesquisa pela ação informada e chama o serviço correspondente
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
			    
		String action = request.getParameter("action");
		Integer iAction = null;
		if(action == null || action.length() == 0){
			returnErrorMessage(request,response, "Nenhuma ação informada");
		}else{
			try {
				iAction = Integer.parseInt(action);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				returnErrorMessage(request,response, "Ação informada inválida");
				return;
			}
			
			switch (iAction) {
				case GET_VIAGENS:
					getViagens(request,response);
					break;
					
				case SEND_DESPESA:
					sendDespesa(request,response);
					break;
	
				case GET_DESPESAS:
					getDespesas(request,response);
					break;
				
				case GET_USUARIO:
					getUsuario(request,response);
					break;
				
				case GET_ALL_VIAGENS:
					getAllViagens(request,response);
					break;
				
				case ADD_USUARIO:
					addUsuario(request,response);
					break;
					
				case SEND_VIAGEM:
					sendViagem(request,response);
					break;
				
				case DELETE_DESPESA:
					deleteDespesa(request,response);
					break;
					
				case DELETE_VIAGEM:
					deleteViagem(request,response);
					break;
					
				default:
					returnErrorMessage(request,response, "Ação informada não encontrada");
					break;
			}
		}
		
	}
	
	/**
	 * Insere/atualiza no banco uma nova despesa
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idViagem=null;
		Long idDespesa=null;
		Date dataDespesa=null;
		String descricaoDespesa=null;
		String tipoDespesa=null;
		BigDecimal valorDespesa=null;
		
		EntUsuario usuario=null;
		String login=null;
		String senha=null;
		
		try{
			if(request.getParameter("idDespesa") != null)
				idDespesa 		 = Long.parseLong(	URLDecoder.decode(request.getParameter("idDespesa")	,"UTF-8")	);
			login			 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha			 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			idViagem 		 = Long.parseLong(	URLDecoder.decode(request.getParameter("idViagem")	,"UTF-8")	);
			dataDespesa 	 = UtlDate.getDateFromString(	URLDecoder.decode(request.getParameter("dataDespesa")	,"UTF-8"),"dd/MM/yyyy");
			descricaoDespesa = URLDecoder.decode(request.getParameter("descricaoDespesa"),"UTF-8");
			tipoDespesa 	 = URLDecoder.decode(request.getParameter("tipoDespesa"),"UTF-8");
			valorDespesa 	 = new BigDecimal(	URLDecoder.decode(request.getParameter("valorDespesa")	,"UTF-8"));
			
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		try{
			EntDespesa despesa = null;
			if(idDespesa!=null){
				despesa = GenericDao.find(EntDespesa.class, idDespesa);
			}else
				despesa = new EntDespesa();
			despesa.setDataDespesa(dataDespesa);
			despesa.setDescricaoDespesa(descricaoDespesa);
			despesa.setTipoDespesa(tipoDespesa);
			despesa.setValorDespesa(valorDespesa);
			
			EntViagem v = GenericDao.find(EntViagem.class, idViagem);
			despesa.setViagem(v);
			if(idDespesa!=null)
				GenericDao.update(despesa);
			else
				GenericDao.insert(despesa);
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível salvar a despesa");
			return;
		}
		JSONArray jsonArray = createJsonResponse(STATUS_OK, "Despesa enviada");
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
		response.getWriter().flush();
	}
	
	private void deleteDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idDespesa=null;
		EntUsuario usuario=null;
		String login=null;
		String senha=null;
		
		try{
			login 	  = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha	  = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			idDespesa = Long.parseLong(	URLDecoder.decode(request.getParameter("idDespesa")	,"UTF-8")	);
			
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		try{
			EntDespesa despesa = GenericDao.find(EntDespesa.class, idDespesa);
			if(despesa !=null)
				GenericDao.delete(despesa);
			else{
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível apagar a despesa");
				return;
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível excluir a despesa");
			return;
		}
		JSONArray jsonArray = createJsonResponse(STATUS_OK, "Despesa excluída");
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
		response.getWriter().flush();
	}
	
	private void deleteViagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idViagem=null;
		
		EntUsuario usuario=null;
		String login=null;
		String senha=null;
		
		try{
			login	 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha	 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			idViagem = Long.parseLong(	URLDecoder.decode(request.getParameter("idViagem")	,"UTF-8")	);
			
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		try{
			EntViagem viagem = GenericDao.find(EntViagem.class, idViagem);
			if(viagem !=null){
				Object[] entidades = new Object[viagem.getEntDespesas().size()+1];
				for(int i=0;i<entidades.length-1;i++)
					entidades[i]=viagem.getEntDespesas().get(i);
				entidades[viagem.getEntDespesas().size()]=viagem;
				GenericDao.delete(entidades);
			}else{
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível apagar a viagem");
				return;
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível excluir a viagem");
			return;
		}
		JSONArray jsonArray = createJsonResponse(STATUS_OK, "Viagem excluída");
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
		response.getWriter().flush();
	}
	
	private void sendViagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idViagem=null;
		Date dataFimViagem=null;
		Date dataInicViagem=null;
		String motivoViagem=null;
		boolean aberta=false;
		BigDecimal adiantamento=null;
		
		EntUsuario usuario=null;
		String login=null;
		String senha=null;
		
		try{
			if(request.getParameter("idViagem") != null)
				idViagem 		 = Long.parseLong(	URLDecoder.decode(request.getParameter("idViagem")	,"UTF-8")	);
			login			 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha			 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			dataInicViagem 	 = UtlDate.getDateFromString(	URLDecoder.decode(request.getParameter("dataInicViagem")	,"UTF-8"),"dd/MM/yyyy");
			dataFimViagem 	 = UtlDate.getDateFromString(	URLDecoder.decode(request.getParameter("dataFimViagem")	,"UTF-8"),"dd/MM/yyyy");
			motivoViagem	 = URLDecoder.decode(request.getParameter("motivoViagem"),"UTF-8");
			aberta			 = Boolean.parseBoolean(URLDecoder.decode(request.getParameter("aberta"),"UTF-8"));
			adiantamento 	 = new BigDecimal(	URLDecoder.decode(request.getParameter("adiantamento")	,"UTF-8"));
			
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		try{
			EntViagem viagem = null;
			if(idViagem!=null){
				viagem = GenericDao.find(EntViagem.class, idViagem);
			}else
				viagem = new EntViagem();
			viagem.setAdiantamento(adiantamento);
			viagem.setAberta(aberta);
			viagem.setDataInicViagem(dataInicViagem);
			viagem.setDataFimViagem(dataFimViagem);
			viagem.setMotivoViagem(motivoViagem);
			viagem.setUsuario(usuario);
			if(idViagem!=null)
				GenericDao.update(viagem);
			else
				GenericDao.insert(viagem);
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível salvar a viagem");
			return;
		}
		
		JSONArray jsonArray = createJsonResponse(STATUS_OK, "Viagem enviada");
		
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
		response.getWriter().flush();
	}
	
	private void addUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login		= null;
		String senha		= null;
		String emailUsuario = null;
		try{
			login		 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha		 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			emailUsuario = URLDecoder.decode(request.getParameter("emailUsuario"),"UTF-8")	;
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		try {
			if(UsuarioDao.existByLogin(login)){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Login já existe, escolha outro");
				return;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Erro interno, tente novamente");
			return;
		}
		try{
			EntUsuario usuario= new EntUsuario();
			usuario.setEmailUsuario(emailUsuario);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setTipoUsuario("viajante");
			
			GenericDao.insert(usuario);
			
			JSONObject ob1 = createJsonUsuario(usuario);
			JSONArray jsonArray = createJsonResponse(STATUS_OK, "", ob1);
			
			response.setContentType("application/json");
			response.getWriter().write(jsonArray.toString());
			response.getWriter().flush();
			
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível criar usuário");
			return;
		}
	
	}
	
	/**
	 * Retorna em formato json as viagens do usuário informado
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getAllViagens(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntUsuario 	usuario=null;
		String 		login=null;
		String 		senha=null;
		
		try {
			login			 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha			 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		
		try {
			List<EntViagem> viagens = usuario.getEntViagens();
			
			if(viagens !=null && viagens.size()>0){
				JSONArray jsonArray = new JSONArray();
				JSONObject ob1 = null;
				
				for(EntViagem v : viagens){
					UtlEntViagem.setTotalDespesas(v);
					UtlEntViagem.setSaldo(v);
					ob1 = new JSONObject();					
					ob1.put("idViagem", v.getIdViagem());
					ob1.put("motivoViagem", v.getMotivoViagem());
					ob1.put("dataInicViagem", UtlDate.getStringFromDate(v.getDataInicViagem(), "dd/MM/yyyy"));
					ob1.put("dataFimViagem", UtlDate.getStringFromDate(v.getDataFimViagem(), "dd/MM/yyyy"));
					ob1.put("aberta", v.isAberta());
					ob1.put("adiantamento", v.getAdiantamento().toString().replace(".", ","));
					ob1.put("totalDespesas", v.getTotalDespesas().toString().replace(".", ","));
					ob1.put("saldo", v.getSaldo().toString().replace(".", ","));
					
					jsonArray.put(ob1);
				}
				
				JSONArray jsonArrayResponse = createJsonResponse(STATUS_OK, "", jsonArray);
				response.setContentType("application/json");
				response.getWriter().write(jsonArrayResponse.toString());
				response.getWriter().flush();
				
			}else{
				returnJsonErrorMessage(request,response, STATUS_EMPTY_LIST, "Não há viagens para este usuário");
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível recuperar as viagens");
			return;
		}
		
	}
	
	private void getViagens(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntUsuario 	usuario=null;
		String 		login=null;
		String 		senha=null;
		
		try {
			login			 = URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha			 = URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		
		try {
			List<EntViagem> viagens = ViagemDao.listViagensAbertas(usuario.getIdUsuario());
			
			if(viagens !=null && viagens.size()>0){
				JSONArray jsonArray = new JSONArray();
				JSONObject ob1 = null;
				
				for(EntViagem v : viagens){
					UtlEntViagem.setTotalDespesas(v);
					UtlEntViagem.setSaldo(v);
					ob1 = new JSONObject();
					ob1.put("idViagem", v.getIdViagem());
					ob1.put("motivoViagem", v.getMotivoViagem());
					ob1.put("dataInicViagem", UtlDate.getStringFromDate(v.getDataInicViagem(), "dd/MM/yyyy"));
					ob1.put("dataFimViagem", UtlDate.getStringFromDate(v.getDataFimViagem(), "dd/MM/yyyy"));
					ob1.put("aberta", v.isAberta());
					ob1.put("adiantamento", v.getAdiantamento().toString().replace(".", ","));
					ob1.put("totalDespesas", v.getTotalDespesas().toString().replace(".", ","));
					ob1.put("saldo", v.getSaldo().toString().replace(".", ","));
					
					jsonArray.put(ob1);
				}
				JSONArray jsonArrayResponse = createJsonResponse(STATUS_OK, "", jsonArray);
				response.setContentType("application/json");
				response.getWriter().write(jsonArrayResponse.toString());
				response.getWriter().flush();
				
			}else{
				returnJsonErrorMessage(request,response, STATUS_EMPTY_LIST, "Não há viagens para este usuário");
				return;
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível recuperar as viagens");
			return;
		}
		
	}
	
	private void getDespesas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long 		idViagem = null;
		EntUsuario 	usuario  = null;
		EntViagem 	viagem   = null;
		String 		login    = null;
		String 		senha    = null;
		
		try {
			login		= URLDecoder.decode(request.getParameter("login"),"UTF-8")	;
			senha		= URLDecoder.decode(request.getParameter("senha"),"UTF-8")	;
			idViagem  	= Long.parseLong(	URLDecoder.decode(request.getParameter("idViagem")	,"UTF-8")	);
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não encontrado");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Usuário não existe");
			return;
		}
		
		try {
			viagem = GenericDao.find(EntViagem.class, idViagem);
			
		} catch (Exception e) {
			e.printStackTrace();
			returnErrorMessage(request,response, "Viagem não existe");
			return;
		}
		
		try {
			List<EntDespesa> despesas = viagem.getEntDespesas();
			
			if(despesas !=null && despesas.size()>0){
				JSONArray jsonArray = new JSONArray();
				JSONObject ob1 = null;
				
				for(EntDespesa d : despesas){
					ob1 = new JSONObject();
					
					ob1.put("idDespesa", d.getIdDespesa());
					ob1.put("descricaoDespesa", d.getDescricaoDespesa());
					ob1.put("tipoDespesa", d.getTipoDespesa());
					ob1.put("valorDespesa", d.getValorDespesa().toString().replace(".", ","));
					ob1.put("dataDespesa", UtlDate.getStringFromDate(d.getDataDespesa(), "dd/MM/yyyy"));
					
					jsonArray.put(ob1);
				}
				JSONArray jsonArrayResponse = createJsonResponse(STATUS_OK, "", jsonArray);
				response.setContentType("application/json");
				response.getWriter().write(jsonArrayResponse.toString());
				response.getWriter().flush();
				
			}else{
				returnJsonErrorMessage(request,response, STATUS_EMPTY_LIST, "Não há despesas para esta viagem");
				return;
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível recuperar as despesas");
			return;
		}
		
	}
	
	private void getUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntUsuario usuario=null;
		String login=null;
		String senha=null;
		
		try {
			login	= URLDecoder.decode(request.getParameter("login"),"UTF-8");
			senha	= URLDecoder.decode(request.getParameter("senha"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			//returnErrorMessage(request,response, "Os valores informados não puderam ser convertidos");
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Os valores informados não puderam ser convertidos");
			return;
		}
		
		try {
			usuario = UsuarioDao.getByLoginSenha(login, senha);
			if(usuario==null){
				//returnErrorMessage(request,response, "Login/senha incorretos");
				returnJsonErrorMessage(request,response, STATUS_EMPTY, "Login/senha incorretos");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//returnErrorMessage(request,response, "Usuário não existe");
			returnJsonErrorMessage(request,response, STATUS_EMPTY, "Usuário não existe");
			return;
		}
		
		JSONObject ob1 = createJsonUsuario(usuario);
		JSONArray jsonArray = createJsonResponse(STATUS_OK, "", ob1);
		if(jsonArray==null){
			//returnErrorMessage(request,response, "Não foi possível recuperar o usuário");
			returnJsonErrorMessage(request,response, STATUS_ERROR, "Não foi possível recuperar o usuário");
			return;
		}else{
			response.setContentType("application/json");
			response.getWriter().write(jsonArray.toString());
			response.getWriter().flush();
		}
				
	}
	
	/**
	 * Retorna ao usuário a mensagem de erro informada
	 * @param request
	 * @param response
	 * @param msg
	 * @throws ServletException
	 * @throws IOException
	 */
	private void returnErrorMessage(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
		response.getWriter().write(msg);
		response.getWriter().flush();
	}
	
	private void returnJsonErrorMessage(HttpServletRequest request, HttpServletResponse response, int responseStatus, String msg) throws ServletException, IOException {
		JSONObject ob1 = null;
		JSONArray jsonArray = new JSONArray();
		try {
			ob1 = new JSONObject();	
			ob1.put(RETURN_STATUS, responseStatus);
			ob1.put(RETURN_MESSAGE, msg);
			jsonArray.put(ob1);
		} catch (JSONException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,"Serviço temporariamente indisponível");
			return;
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
		response.getWriter().flush();
	}
	
	private JSONObject createJsonUsuario(EntUsuario usuario){
		
		JSONObject ob1 = null;
		try {
			
			ob1 = new JSONObject();
			
			ob1.put("idUsuario", usuario.getIdUsuario());
			ob1.put("emailUsuario", usuario.getEmailUsuario());
			ob1.put("login", usuario.getLogin());
			ob1.put("tipoUsuario", usuario.getTipoUsuario());
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ob1;
	}
	
	private JSONArray createJsonResponse(int responseStatus, String msg){
		JSONArray jsonArray = new JSONArray();
		JSONObject ob1 = null;
		try {
			ob1 = new JSONObject();	
			ob1.put(RETURN_STATUS, responseStatus);
			ob1.put(RETURN_MESSAGE, msg);
			
			jsonArray.put(ob1);
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	private JSONArray createJsonResponse(int responseStatus, String msg,JSONObject jsonObject){
		JSONArray jsonArray = new JSONArray();
		JSONObject ob1 = null;
		try {
			ob1 = new JSONObject();	
			ob1.put(RETURN_STATUS, responseStatus);
			ob1.put(RETURN_MESSAGE, msg);
			
			jsonArray.put(ob1);
			if(jsonObject!=null)
				jsonArray.put(jsonObject);
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	private JSONArray createJsonResponse(int responseStatus, String msg,JSONArray jsonObject){
		JSONArray jsonArray = new JSONArray();
		JSONObject ob1 = null;
		try {
			ob1 = new JSONObject();	
			ob1.put(RETURN_STATUS, responseStatus);
			ob1.put(RETURN_MESSAGE, msg);
			
			jsonArray.put(ob1);
			if(jsonObject!=null)
				jsonArray.put(jsonObject);
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
}
