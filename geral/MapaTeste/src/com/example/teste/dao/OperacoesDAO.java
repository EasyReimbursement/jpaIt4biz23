package com.example.teste.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class OperacoesDAO {

	private String[] colunas = { "sk_consumidor", "cepconsumidor", "rua",
			"numero", "bairro", "cidade",
			"estado", "pais", "latitude", "longitude" };

	private SQLiteDatabase db;
	private DBHelper helper;

	public OperacoesDAO(Context context) {
		helper = new DBHelper(context);
		db = helper.getDatabase();
	}

	public ArrayList<Endereco> retornaCEPs() {
		Cursor c = null;
		try {
			c = db.query("endereco", colunas, null, null, null, null, null);
		} catch (SQLException e) {
			System.err.println(e.toString());
		}

		if (c == null) {
			System.out.println("Entrou: null");
			return null;
		}
		c.moveToFirst();
		int tam = c.getCount();
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		while (tam > 0) {
			Endereco end = new Endereco();
			int sk = c.getInt(c.getColumnIndex("sk_consumidor"));
			end.setSk_consumidor(sk);
			String cep = c.getString(c.getColumnIndex("cepconsumidor"));
			end.setCep(cep);
			String rua = c.getString(c.getColumnIndex("rua"));
			end.setRua(rua);
			String num = c.getString(c.getColumnIndex("numero"));
			end.setNumero(num);
			String bairro = c.getString(c.getColumnIndex("bairro"));
			end.setBairro(bairro);
			String cidade = c.getString(c.getColumnIndex("cidade"));
			end.setCidade(cidade);
			String estado = c.getString(c.getColumnIndex("estado"));
			end.setEstado(estado);
			String pais = c.getString(c.getColumnIndex("pais"));
			end.setPais(pais);
			String lat = c.getString(c.getColumnIndex("latitude"));
			end.setLatitude(lat);
			String lon = c.getString(c.getColumnIndex("longitude"));
			end.setLongitude(lon);
			
			
			
			enderecos.add(end);
			tam--;
			c.moveToNext();
		}
		c.close();

		return enderecos;
	}

}
