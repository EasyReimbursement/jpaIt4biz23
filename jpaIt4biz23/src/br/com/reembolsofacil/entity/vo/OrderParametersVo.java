package br.com.reembolsofacil.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Determina o ordenamento do resultado da query
 * @author fausto
 *
 */
public class OrderParametersVo {

	private List<Object[]> lista;
	
	/**
	 * Todos os campos/colunas (orderFields) são ordenados
	 * pelo tipo determinado no parâmetro order (asc ou desc)
	 * 
	 * @param order
	 * @param orderFields
	 */
	public OrderParametersVo(String order, String...orderFields){
		
		lista = new ArrayList<Object[]>();
		for(String s:orderFields)
			lista.add(new Object[]{s, order});
	}
	
	/**
	 * O padrão de ordenação é ASC
	 * @param orderFields
	 */
	public OrderParametersVo(String...orderFields){
		
		lista = new ArrayList<Object[]>();
		for(String s:orderFields)
			lista.add(new Object[]{s, "asc"});
	}

	public OrderParametersVo add(String ordenacao,String atributo){
		lista.add(new Object[]{atributo, ordenacao});
		return this;
	}
	
	public OrderParametersVo add(String atributo){
		lista.add(new Object[]{atributo, "asc"});
		return this;
	}

	public List<Object[]> getLista() {
		return lista;
	}
	
}
