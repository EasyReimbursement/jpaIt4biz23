package br.com.reembolsofacil.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.dao.GenericDao;
import br.com.reembolsofacil.entity.util.UtlCrypto;

@ManagedBean
@ViewScoped
public class UsuariosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntUsuario usuario;
	private EntUsuario selectedUsuario;
	private List<EntUsuario> usuarios = new ArrayList<EntUsuario>();
	private String idUsuario;
	private String cmdBtnValue;
	private boolean senhaDisabled;

	public UsuariosBean() {
		super();
		usuario = new EntUsuario();
		usuarios = GenericDao.listAll("EntUsuario");
		cmdBtnValue="Adicionar";
	}
	
	public String createOrUpdate() {
		if(usuario.getIdUsuario() >0)
			GenericDao.update(usuario);
		else{
			usuario.setSenha(UtlCrypto.criptografaSenha(usuario.getSenha()));
			GenericDao.insert(usuario);
		}
		usuarios = GenericDao.listAll("EntUsuario");
		usuario = new EntUsuario();
		cmdBtnValue = "Adicionar";
		
		return null;
	}
	
	public String loadForUpdate(){
		cmdBtnValue = "Atualizar";
		usuario = GenericDao.find(EntUsuario.class, selectedUsuario.getIdUsuario());
		return null;
	}
	
	public String goToViagens() {
        return "viagens.xhtml";
    }

	public EntUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntUsuario usuario) {
		this.usuario = usuario;
	}

	public List<EntUsuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<EntUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCmdBtnValue() {
		return cmdBtnValue;
	}

	public void setCmdBtnValue(String cmdBtnValue) {
		this.cmdBtnValue = cmdBtnValue;
	}

	public EntUsuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(EntUsuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public boolean isSenhaDisabled() {
		return senhaDisabled;
	}

	public void setSenhaDisabled(boolean senhaDisabled) {
		this.senhaDisabled = senhaDisabled;
	}
	
}
