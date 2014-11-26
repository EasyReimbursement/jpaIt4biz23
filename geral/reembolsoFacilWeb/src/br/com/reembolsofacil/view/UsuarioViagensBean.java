package br.com.reembolsofacil.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.util.UtlEntViagem;

@ManagedBean
@ViewScoped
public class UsuarioViagensBean extends UsuariosBaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private EntUsuario usuarioLogado;
	
	//@ManagedProperty(value="#{param.idUsuario}")
	private String idUsuario;
	private EntViagem viagem;
	private EntViagem selectedViagem;
	private EntUsuario usuario;
	private List<EntViagem> viagens = new ArrayList<EntViagem>();
	private String cmdBtnValue;

	public UsuarioViagensBean() {
		super();
		viagem = new EntViagem();
		if(session == null || (usuarioLogado = (EntUsuario)session.getAttribute("usuarioLogado")) == null){
			try {//TODO mensagem não está funcionando
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Favor realizar login",""));
				ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@PostConstruct
	public void postCreate() {
		viagens = usuarioLogado.getEntViagens();
		for(EntViagem v:viagens){
			UtlEntViagem.setTotalDespesas(v);
			UtlEntViagem.setSaldo(v);
		}
		idUsuario = Long.toString(usuarioLogado.getIdUsuario());
		cmdBtnValue = "Adicionar";
	}
	
	public String createOrUpdate() {
		usuario = GenericDao.find(EntUsuario.class, Long.parseLong(idUsuario));
		viagem.setUsuario(usuario);
		if(usuario.getIdUsuario() >0)
			GenericDao.update(viagem);
		else
			GenericDao.insert(viagem);
		viagens = usuario.getEntViagens();
		for(EntViagem v:viagens){
			UtlEntViagem.setTotalDespesas(v);
			UtlEntViagem.setSaldo(v);
		}
		viagem = new EntViagem();
		cmdBtnValue = "Adicionar";
		return null;
	}
	
	public String loadForUpdate(){
		cmdBtnValue = "Atualizar";
		viagem = GenericDao.find(EntViagem.class, selectedViagem.getIdViagem());
		UtlEntViagem.setTotalDespesas(viagem);
		UtlEntViagem.setSaldo(viagem);
		return null;
	}
	
	public EntViagem getViagem() {
		return viagem;
	}

	public void setViagem(EntViagem viagem) {
		this.viagem = viagem;
	}

	public List<EntViagem> getViagens() {
		return viagens;
	}

	public void setViagens(List<EntViagem> viagens) {
		this.viagens = viagens;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public EntUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntUsuario usuario) {
		this.usuario = usuario;
	}

	public String getCmdBtnValue() {
		return cmdBtnValue;
	}

	public void setCmdBtnValue(String cmdBtnValue) {
		this.cmdBtnValue = cmdBtnValue;
	}

	public EntViagem getSelectedViagem() {
		return selectedViagem;
	}

	public void setSelectedViagem(EntViagem selectedViagem) {
		this.selectedViagem = selectedViagem;
	}

}
