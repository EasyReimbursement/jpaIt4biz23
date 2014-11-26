package br.com.reembolsofacil.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.reembolsofacil.entity.listener.DataHoraListener;
import br.com.reembolsofacil.entity.util.UtlEntViagem;


/**
 * The persistent class for the tb_viagem database table.
 * 
 */
@Entity
@Table(name="tb_viagem")
@EntityListeners({DataHoraListener.class})
public class EntViagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_VIAGEM_IDVIAGEM_GENERATOR", sequenceName="TB_VIAGEM_ID_VIAGEM_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_VIAGEM_IDVIAGEM_GENERATOR")
	@Column(name="id_viagem")
	private long idViagem;

	//bi-directional many-to-one association to EntUsuario
	@ManyToOne(cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinColumn(name="fk_usuario")
	private EntUsuario usuario;
	
    @Temporal( TemporalType.DATE)
	@Column(name="data_fim_viagem")
	private Date dataFimViagem;

	@Column(name="data_hora")
	@Temporal( TemporalType.TIMESTAMP)
	private Date dataHora;

    @Temporal( TemporalType.DATE)
	@Column(name="data_inic_viagem")
	private Date dataInicViagem;

	@Column(name="motivo_viagem")
	private String motivoViagem;
	
	private boolean aberta;
	
	@Column(name="adiantamento")
	private BigDecimal adiantamento;
	
	@Transient
	private BigDecimal totalDespesas;
	
	@Transient
	private BigDecimal saldo;
	
	//bi-directional many-to-one association to EntDespesa
	@OneToMany(mappedBy="viagem",cascade={CascadeType.DETACH,CascadeType.REFRESH})
	@OrderBy
	private List<EntDespesa> entDespesas;

    public EntViagem() {
    }

	public long getIdViagem() {
		return this.idViagem;
	}

	public void setIdViagem(long idViagem) {
		this.idViagem = idViagem;
	}

	public Date getDataFimViagem() {
		return dataFimViagem;
	}

	public void setDataFimViagem(Date dataFimViagem) {
		this.dataFimViagem = dataFimViagem;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Date getDataInicViagem() {
		return dataInicViagem;
	}

	public void setDataInicViagem(Date dataInicViagem) {
		this.dataInicViagem = dataInicViagem;
	}

	public String getMotivoViagem() {
		return this.motivoViagem;
	}

	public void setMotivoViagem(String motivoViagem) {
		this.motivoViagem = motivoViagem;
	}

	public EntUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntUsuario usuario) {
		this.usuario = usuario;
	}

	public List<EntDespesa> getEntDespesas() {
		return entDespesas;
	}

	public void setEntDespesas(List<EntDespesa> entDespesas) {
		this.entDespesas = entDespesas;
	}

	public boolean isAberta() {
		return aberta;
	}

	public void setAberta(boolean aberta) {
		this.aberta = aberta;
	}

	public BigDecimal getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(BigDecimal adiantamento) {
		this.adiantamento = adiantamento;
	}

	public BigDecimal getTotalDespesas() {
		/*totalDespesas = new BigDecimal(0);
		if(getEntDespesas().size()>0)
			for(EntDespesa d:getEntDespesas())
				totalDespesas = totalDespesas.add(d.getValorDespesa());*/
		//UtlEntViagem.setTotalDespesas(this);
		return totalDespesas;
	}

	public void setTotalDespesas(BigDecimal totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public BigDecimal getSaldo() {
		/*if(saldo==null)
			saldo = new BigDecimal(0);
		saldo = getAdiantamento().subtract(getTotalDespesas());*/
		//UtlEntViagem.setSaldo(this);
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "EntViagem [idViagem=" + idViagem + ", aberta=" + aberta
				+ ", dataInicViagem=" + dataInicViagem + ", dataFimViagem="
				+ dataFimViagem + ", adiantamento=" + adiantamento
				+ ", motivoViagem=" + motivoViagem + ", dataHora=" + dataHora
				+ "]";
	}

}