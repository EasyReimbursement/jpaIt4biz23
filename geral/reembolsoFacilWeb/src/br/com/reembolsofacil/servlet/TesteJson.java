package br.com.reembolsofacil.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.util.UtlDate;

@WebServlet("/TesteJson")
public class TesteJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TesteJson() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			response.getWriter().write(jsonArray.toString());
			response.getWriter().flush();
		} catch (JSONException e) {
			e.printStackTrace();
			response.getWriter().write("erro");
			response.getWriter().flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
