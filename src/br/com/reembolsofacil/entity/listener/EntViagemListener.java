package br.com.reembolsofacil.entity.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import br.com.reembolsofacil.entity.EntViagem;

public class EntViagemListener {

	public EntViagemListener() {
	}

	@PostLoad
	public void postLoad(EntViagem viagem) {
		
	}

	@PrePersist
	public void prePersist(EntViagem viagem) {
		//usuario.setSenha(UtlCrypto.criptografaSenha(usuario.getSenha()));
	}

	@PostPersist
	public void postPersist(EntViagem viagem) {
		
	}

	@PreRemove
	public void preRemove(EntViagem viagem) {
		
	}

	@PostRemove
	public void postRemove(EntViagem viagem) {
		
	}

	@PreUpdate
	public void preUpdate(EntViagem viagem) {
	}

	@PostUpdate
	public void postUpdate(EntViagem viagem) {
		
	}
}
