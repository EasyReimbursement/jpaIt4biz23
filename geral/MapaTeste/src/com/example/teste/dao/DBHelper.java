package com.example.teste.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	/**
	   * O construtor necessita do contexto da aplica��o
	   */
	  public DBHelper(Context context) {
	    /* O primeiro argumento � o contexto da aplicacao
	     * O segundo argumento � o nome do banco de dados
	     * O terceiro � um ponteiro para manipula��o de dados, 
	     *   n�o precisaremos dele.
	     * O quarto � a vers�o do banco de dados
	     */
	    super(context, "AdaFarma.db", null, 1);
	    this.context = context;
	  }

/**
   * Este � o endere�o onde o android salva os bancos de dados criado pela aplica��o,
   * /data/data/<namespace da aplicacao>/databases/
   */
  private static String DBPATH = "/data/data/com.example.teste/databases/";

  // Este � o nome do banco de dados que iremos utilizar
  private static String DBNAME ="testedb.db";
  private Context context;
  
  
  
  /**
   * Os m�todos onCreate e onUpgrade precisam ser sobreescrito
   */
  @Override
  public void onCreate(SQLiteDatabase db) {}
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
  
  /**
   * M�todo auxiliar que verifica a existencia do banco
   * da aplica��o.
   */
  private boolean checkDataBase() {
    
    SQLiteDatabase db = null;
    
    try {
      String path =DBPATH + DBNAME;
      System.out.println(path);
      db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
      db.close();
    } catch (SQLiteException e) {
    	System.err.println(e.toString());
      // O banco n�o existe
    }
    
    // Retorna verdadeiro se o banco existir, pois o ponteiro ira existir,
    // se n�o houver referencia � porque o banco n�o existe
    return db != null;
  }
  
  private void createDataBase() 
  throws Exception {
    
    // Primeiro temos que verificar se o banco da aplica��o
    // j� foi criado
    boolean exists = checkDataBase();
    
    if(!exists) {
      // Chamaremos esse m�todo para que o android
      // crie um banco vazio e o diret�rio onde iremos copiar
      // no banco que est� no assets.
      this.getReadableDatabase();
      this.close();
      // Se o banco de dados n�o existir iremos copiar o nosso
      // arquivo em /assets para o local onde o android os salva
      try {
        copyDatabase();
      } catch (IOException e) {
        System.err.println(e.toString());
      }
      
    }
  }
  
  /**
   * Esse m�todo � responsavel por copiar o banco do diret�rio
   * assets para o diret�rio padr�o do android.
   */
  private void copyDatabase()
  throws IOException {
    
    String dbPath = DBPATH + DBNAME;
    
    // Abre o arquivo o destino para copiar o banco de dados
    OutputStream dbStream = new FileOutputStream(dbPath);
    
    // Abre Stream do nosso arquivo que esta no assets
    InputStream dbInputStream = 
      context.getAssets().open(DBNAME);
    
    byte[] buffer = new byte[1024];
    int length;
    while((length = dbInputStream.read(buffer)) > 0) {
      dbStream.write(buffer, 0, length);
    }
    
    dbInputStream.close();
    
    dbStream.flush();
    dbStream.close();
    
  }
  
  public SQLiteDatabase getDatabase() {
    
    try{
      // Verificando se o banco j� foi criado e se n�o foi o
      // mesmo � criado.
      createDataBase();
      
      // Abrindo database
      String path = DBPATH + DBNAME;
      
      return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }catch (Exception e) {
      // Se n�o conseguir copiar o banco um novo ser� retornado
      return getWritableDatabase();
    }

  }
}
