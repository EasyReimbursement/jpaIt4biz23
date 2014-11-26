package br.com.reembolsofacil.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.util.UtlDate;

@WebServlet("/InsereDespesaServlet")
public class InsereDespesaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InsereDespesaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idViagem;
		String dataDespesa;
		String descricaoDespesa;
		String tipoDespesa;
		String valorDespesa;
		
		String result = "";
		try{
			idViagem 		 = URLDecoder.decode(request.getParameter("idViagem"),"UTF-8");
			dataDespesa 	 = URLDecoder.decode(request.getParameter("dataDespesa"),"UTF-8");
			descricaoDespesa = URLDecoder.decode(request.getParameter("descricaoDespesa"),"UTF-8");
			tipoDespesa 	 = URLDecoder.decode(request.getParameter("tipoDespesa"),"UTF-8");
			valorDespesa 	 = URLDecoder.decode(request.getParameter("valorDespesa"),"UTF-8");
			
			EntDespesa despesa = new EntDespesa();
			despesa.setDataDespesa(UtlDate.getDateFromString(dataDespesa,"dd/MM/yyyy"));
			despesa.setDescricaoDespesa(descricaoDespesa);
			despesa.setTipoDespesa(tipoDespesa);
			despesa.setValorDespesa(new BigDecimal(valorDespesa));
			
			EntViagem v = GenericDao.find(EntViagem.class, (Long)Long.parseLong(idViagem));
			despesa.setViagem(v);
			GenericDao.insert(despesa);
			result = "ok";
		}catch (Exception e) {
			e.printStackTrace();
			result = "erro";
		}
		response.getWriter().write(result);
		response.getWriter().flush();
	}
}
