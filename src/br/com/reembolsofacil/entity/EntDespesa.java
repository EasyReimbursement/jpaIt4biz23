package br.com.reembolsofacil.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.reembolsofacil.entity.listener.DataHoraListener;


/**
 * The persistent class for the tb_despesa database table.
 * 
 */
@Entity
@Table(name="tb_despesa")
@EntityListeners({DataHoraListener.class})
public class EntDespesa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_DESPESA_IDDESPESA_GENERATOR", sequenceName="TB_DESPESA_ID_DESPESA_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_DESPESA_IDDESPESA_GENERATOR")
	@Column(name="id_despesa")
	private long idDespesa;

	//bi-directional many-to-one association to EntViagem
	@ManyToOne(cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinColumn(name="fk_viagem")
	private EntViagem viagem;
	
    @Temporal( TemporalType.DATE)
	@Column(name="data_despesa")
	private Date dataDespesa;

	@Column(name="data_hora")
	@Temporal( TemporalType.TIMESTAMP)
	private Date dataHora;

	@Column(name="descricao_despesa")
	private String descricaoDespesa;

	@Column(name="tipo_despesa")
	private String tipoDespesa;

	@Column(name="valor_despesa")
	private BigDecimal valorDespesa;

    public EntDespesa() {
    }

	public long getIdDespesa() {
		return idDespesa;
	}

	public void setIdDespesa(long idDespesa) {
		this.idDespesa = idDespesa;
	}

	public Date getDataDespesa() {
		return dataDespesa;
	}

	public void setDataDespesa(Date dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricaoDespesa() {
		return descricaoDespesa;
	}

	public void setDescricaoDespesa(String descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}

	public String getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public BigDecimal getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(BigDecimal valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public EntViagem getViagem() {
		return viagem;
	}

	public void setViagem(EntViagem viagem) {
		this.viagem = viagem;
	}

	@Override
	public String toString() {
		return "EntDespesa [idDespesa=" + idDespesa + ", dataDespesa="
				+ dataDespesa + ", dataHora=" + dataHora
				+ ", descricaoDespesa=" + descricaoDespesa + ", tipoDespesa="
				+ tipoDespesa + ", valorDespesa=" + valorDespesa + "]";
	}

}