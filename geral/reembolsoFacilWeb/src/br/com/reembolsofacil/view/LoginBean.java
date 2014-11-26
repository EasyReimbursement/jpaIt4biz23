package br.com.reembolsofacil.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.dao.UsuarioDao;
import br.com.reembolsofacil.entity.util.UtlCrypto;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntUsuario usuario;
	
	public LoginBean() {
		super();
		usuario = new EntUsuario();
	}
	
	public String doLogin() {
		
		EntUsuario u = UsuarioDao.getByLoginSenha(usuario.getLogin(), UtlCrypto.criptografaSenha(usuario.getSenha()));
		
		if(u==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login ou senha incorretos",""));
			return null;
		}else{
			((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("usuarioLogado", u);
			return "users/viagens.xhtml?faces-redirect=true";
		}
	}
	
	/*public String doLoginAction(ActionEvent actionEvent) {
		
		EntUsuario u = UsuarioDao.getByLoginSenha(usuario.getLogin(), UtlCrypto.criptografaSenha(usuario.getSenha()));
		
		if(u==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login ou senha incorretos"));
			return null;
		}else
			return "usuarios";
	}*/

	public EntUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntUsuario usuario) {
		this.usuario = usuario;
	}
		
}
