package br.com.reembolsofacil.entity.util;

import br.com.reembolsofacil.entity.EntDespesa;
import br.com.reembolsofacil.entity.EntViagem;

import java.math.BigDecimal;

/**
 * Hack para carregar os dados de totalDespesas e saldo.
 * Deixando direto no getters da entidade causa null pointer
 * ao usar o jar em projeto web, apesar de funcionar em teste
 * standalone. Se possível buscar alternativa melhor no futuro
 * @author fausto
 *
 */
public class UtlEntViagem {

	public static void setTotalDespesas(EntViagem viagem){
		viagem.setTotalDespesas(new BigDecimal(0));
		if(viagem.getEntDespesas().size()>0)
			for(EntDespesa d:viagem.getEntDespesas())
				viagem.setTotalDespesas(viagem.getTotalDespesas().add(d.getValorDespesa()));
	}
	
	public static void setSaldo(EntViagem viagem){
		if(viagem.getSaldo()==null)
			viagem.setSaldo(new BigDecimal(0));
		viagem.setSaldo(viagem.getAdiantamento().subtract(viagem.getTotalDespesas()));
	}
}
