package br.com.reembolsofacil.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.reembolsofacil.entity.EntUsuario;

@ManagedBean
@SessionScoped
public class MyBean {

	private String t;
	private EntUsuario usuario;
	
	public MyBean() {
		super();
		//usuario = GenericDao.find(EntUsuario.class, 2L);
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public EntUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntUsuario usuario) {
		this.usuario = usuario;
	}
	
}
