package br.com.reembolsofacil.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntViagem;
import br.com.reembolsofacil.entity.dao.GenericDao;

@ManagedBean
@ViewScoped
public class DespesasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String[] tipos = new String[]{"Alimentação","Taxi","Hotel","Ônibus","Pedágio","Combustível","Aluguel de Carro","Outros"};
	
	//@ManagedProperty(value="#{param.idViagem}")
	private String idViagem;
	//@ManagedProperty(value="#{param.idUsuario}")
	private String idUsuario;
	private EntViagem viagem;
	private EntDespesa despesa;
	private EntDespesa selectedDespesa;
	private List<EntDespesa> despesas;
	private String cmdBtnValue;

	public DespesasBean() {
		super();
		despesas = new ArrayList<EntDespesa>();
		despesa = new EntDespesa();
		despesa.setDataDespesa(new Date());
		cmdBtnValue="Adicionar";
	}
	
	@PostConstruct
	public void postCreate() {
		idUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUsuario"); 
		idViagem = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idViagem"); 
		if(idViagem != null){
			viagem = GenericDao.find(EntViagem.class, Long.parseLong(idViagem));
			idUsuario = ((Long)viagem.getUsuario().getIdUsuario()).toString();
			despesas = viagem.getEntDespesas();
		}
	}
	
	public String createOrUpdate() {
		viagem = GenericDao.find(EntViagem.class, Long.parseLong(idViagem));
		despesa.setViagem(viagem);
		if(despesa.getIdDespesa() >0)
			GenericDao.update(despesa);
		else
			GenericDao.insert(despesa);
	
		despesas = viagem.getEntDespesas();
		despesa = new EntDespesa();
		despesa.setDataDespesa(new Date());
		cmdBtnValue = "Adicionar";
		return null;
	}
	
	public String loadForUpdate(){
		cmdBtnValue = "Atualizar";
		despesa = GenericDao.find(EntDespesa.class, selectedDespesa.getIdDespesa());
		return null;
	}

	public String getIdViagem() {
		return idViagem;
	}

	public void setIdViagem(String idViagem) {
		this.idViagem = idViagem;
	}

	public EntViagem getViagem() {
		return viagem;
	}

	public void setViagem(EntViagem viagem) {
		this.viagem = viagem;
	}

	public EntDespesa getDespesa() {
		return despesa;
	}

	public void setDespesa(EntDespesa despesa) {
		this.despesa = despesa;
	}

	public List<EntDespesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<EntDespesa> despesas) {
		this.despesas = despesas;
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

	public EntDespesa getSelectedDespesa() {
		return selectedDespesa;
	}

	public void setSelectedDespesa(EntDespesa selectedDespesa) {
		this.selectedDespesa = selectedDespesa;
	}

	public String[] getTipos() {
		return tipos;
	}

	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

}
