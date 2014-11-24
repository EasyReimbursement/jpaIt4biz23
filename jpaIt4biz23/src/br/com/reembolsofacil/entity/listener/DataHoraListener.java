package br.com.reembolsofacil.entity.listener;

import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntUsuario;
import br.com.reembolsofacil.entity.EntViagem;

public class DataHoraListener {

	public DataHoraListener() {
	}

	@PostLoad
	public void postLoad(Object entity) {
		
	}

	@PrePersist
	public void prePersist(Object entity) {
		if(entity instanceof EntDespesa){
			EntDespesa e = (EntDespesa) entity;
			e.setDataHora(new Date());
		}else if(entity instanceof EntUsuario){
			EntUsuario e = (EntUsuario) entity;
			e.setDataHora(new Date());
		}else if(entity instanceof EntViagem){
			EntViagem e = (EntViagem) entity;
			e.setDataHora(new Date());
		}
	}

	@PostPersist
	public void postPersist(Object entity) {
		
	}

	@PreRemove
	public void preRemove(Object entity) {
		
	}

	@PostRemove
	public void postRemove(Object entity) {
		
	}

	@PreUpdate
	public void preUpdate(Object entity) {
		
	}

	@PostUpdate
	public void postUpdate(Object entity) {
		
	}
}
