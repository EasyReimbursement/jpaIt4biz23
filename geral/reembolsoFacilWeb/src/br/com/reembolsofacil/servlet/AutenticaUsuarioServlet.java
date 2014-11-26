package br.com.reembolsofacil.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;

@WebServlet("/AutenticaUsuarioServlet")
public class AutenticaUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AutenticaUsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUsuario = request.getParameter("idUsuario");
		String idViagem = "";
		
		if(idUsuario != null && idUsuario.length()>0){
			EntUsuario usuario = GenericDao.find(EntUsuario.class, Long.parseLong(idUsuario));
			//busca ultima viagem (colecao entViagens esta ordenado pelo id)
			EntViagem viagem = usuario.getEntViagens().get(usuario.getEntViagens().size()-1);
			idViagem = Long.toString(viagem.getIdViagem());
		}
		response.getWriter().write(idViagem);
		response.getWriter().flush();
	}
}
