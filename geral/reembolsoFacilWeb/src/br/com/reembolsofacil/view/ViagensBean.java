package br.com.reembolsofacil.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;

@ManagedBean
@ViewScoped
public class ViagensBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@ManagedProperty(value="#{param.idUsuario}")
	private String idUsuario;
	private EntViagem viagem;
	private EntViagem selectedViagem;
	private EntUsuario usuario;
	private List<EntViagem> viagens = new ArrayList<EntViagem>();
	private String cmdBtnValue;

	public ViagensBean() {
		super();
		viagem = new EntViagem();
		cmdBtnValue="Adicionar";
	}
	
	@PostConstruct
	public void postCreate() {
		idUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUsuario"); 
		if(idUsuario != null){
			usuario = GenericDao.find(EntUsuario.class, Long.parseLong(idUsuario));
			viagens = usuario.getEntViagens();
		}
	}
	
	public String createOrUpdate() {
		usuario = GenericDao.find(EntUsuario.class, Long.parseLong(idUsuario));
		viagem.setUsuario(usuario);
		if(usuario.getIdUsuario() >0)
			GenericDao.update(viagem);
		else
			GenericDao.insert(viagem);
		viagens = usuario.getEntViagens();
		viagem = new EntViagem();
		cmdBtnValue = "Adicionar";
		return null;
	}
	
	public String loadForUpdate(){
		cmdBtnValue = "Atualizar";
		viagem = GenericDao.find(EntViagem.class, selectedViagem.getIdViagem());
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
