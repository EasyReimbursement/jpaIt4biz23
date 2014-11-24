package br.com.reembolsofacil.entity.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.util.UtlCrypto;

public class EntUsuarioListener {

	public EntUsuarioListener() {
	}

	@PostLoad
	public void postLoad(EntUsuario usuario) {
		
	}

	@PrePersist
	public void prePersist(EntUsuario usuario) {
		//usuario.setSenha(UtlCrypto.criptografaSenha(usuario.getSenha()));
	}

	@PostPersist
	public void postPersist(EntUsuario usuario) {
		
	}

	@PreRemove
	public void preRemove(EntUsuario usuario) {
		
	}

	@PostRemove
	public void postRemove(EntUsuario usuario) {
		
	}

	@PreUpdate
	public void preUpdate(EntUsuario usuario) {
		usuario.setSenha(UtlCrypto.criptografaSenha(usuario.getSenha()));
	}

	@PostUpdate
	public void postUpdate(EntUsuario usuario) {
		
	}
}
