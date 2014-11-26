package br.com.reembolsofacil.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.reembolsofacil.entity.listener.DataHoraListener;
import br.com.reembolsofacil.entity.listener.EntUsuarioListener;


/**
 * The persistent class for the tb_usuario database table.
 * 
 */
@Entity
@Table(name="tb_usuario")
@EntityListeners({DataHoraListener.class,EntUsuarioListener.class})
public class EntUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USUARIO_IDUSUARIO_GENERATOR", sequenceName="TB_USUARIO_ID_USUARIO_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario")
	private long idUsuario;

	@Column(name="chave_app_usuario")
	private String chaveAppUsuario;

	@Column(name="data_hora")
	@Temporal( TemporalType.TIMESTAMP)
	private Date dataHora;

	@Column(name="email_usuario")
	private String emailUsuario;

	@Column(name="tipo_usuario")
	private String tipoUsuario;
	
	@Column(name="login",length=30)
	private String login;
	
	@Column(name="senha",length=150)
	private String senha;

	//bi-directional many-to-one association to EntViagem
	@OneToMany(mappedBy="usuario",cascade={CascadeType.DETACH,CascadeType.REFRESH})
	@OrderBy
	private List<EntViagem> entViagens;

    public EntUsuario() {
    }

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getChaveAppUsuario() {
		return this.chaveAppUsuario;
	}

	public void setChaveAppUsuario(String chaveAppUsuario) {
		this.chaveAppUsuario = chaveAppUsuario;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getEmailUsuario() {
		return this.emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<EntViagem> getEntViagens() {
		return entViagens;
	}

	public void setEntViagens(List<EntViagem> entViagens) {
		this.entViagens = entViagens;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "EntUsuario [idUsuario=" + idUsuario + ", chaveAppUsuario="
				+ chaveAppUsuario + ", dataHora=" + dataHora
				+ ", emailUsuario=" + emailUsuario + ", tipoUsuario="
				+ tipoUsuario + ", login=" + login + "]";
	}

}