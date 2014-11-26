package br.com.reembolsofacil.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class UsuariosBaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected FacesContext fc = FacesContext.getCurrentInstance();
	protected ExternalContext ec = fc.getExternalContext();
	protected HttpSession session = (HttpSession)ec.getSession(false);
	
	public String logout(){
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
}
