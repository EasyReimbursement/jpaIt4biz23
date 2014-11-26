/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testebanco;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeh Santos
 */
public class DataHelper {

    private static Connection conSqlite;
    private Connection connectionPostgre;

    public void createTableConsumidor() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            conSqlite = DriverManager.getConnection("jdbc:sqlite:testedb.db");
            Statement stat = conSqlite.createStatement();
            stat.executeUpdate("drop table if exists user");

            //creating table
            stat.executeUpdate("create table consumidor(sk_consumidor INT," + "version INT,"
                    + "date_from varchar(30)," + "date_to varchar(30)," + "cepconsumidor varchar(30),"
                    + "cod_sexoconsumidor varchar(15)," + "faixaetariaconsumidor varchar(15)," + "desc_sexoconsumidor varchar(15),"
                    + "primary key (sk_consumidor));");
            stat.close();
            conSqlite.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String,String> buscarCoordenadasEndereco(String endereco) {
        HashMap<String, String> dados = new HashMap<String, String>();
        
        Geocoder geocoder = new Geocoder();
        GeocoderRequest geoReq = new GeocoderRequestBuilder().setAddress(endereco).getGeocoderRequest();
        GeocodeResponse geoResponse = geocoder.geocode(geoReq);
        List<GeocoderResult> results = geoResponse.getResults();
        
        BigDecimal latitude = results.get(0).getGeometry().getLocation().getLat();
        BigDecimal longitude = results.get(0).getGeometry().getLocation().getLng();
        dados.put("latitude",latitude.toString());
        dados.put("longitude",longitude.toString());
        LatLng position = new LatLng(latitude, longitude);
        Geocoder geocoder2 = new Geocoder();
        GeocoderRequest geoReq2 = new GeocoderRequestBuilder().setLocation(position).getGeocoderRequest();
        GeocodeResponse geoResponse2 = geocoder2.geocode(geoReq2);
        List<GeocoderResult> results2 = geoResponse2.getResults();
        
        List<GeocoderAddressComponent> resultados = results2.get(0).getAddressComponents();
        
        for(int i=0; i<resultados.size();i++){
            if(resultados.get(i).getTypes().get(0).contains("number")){
        String numero = resultados.get(i).getLongName();
            dados.put("numero",numero);}
            else if(resultados.get(i).getTypes().contains("route")){
        String rua = resultados.get(i).getLongName();
        dados.put("rua",rua);}
            else if(resultados.get(i).getTypes().contains("sublocality")){
        String bairro = resultados.get(i).getLongName();
        dados.put("bairro",bairro);}
            else if(resultados.get(i).getTypes().contains("locality")){
        String cidade = resultados.get(i).getLongName();
        dados.put("cidade",cidade);}
            else if(resultados.get(i).getTypes().get(0).contains("administrative")){
        String estado = resultados.get(i).getLongName();
        dados.put("estado",estado);}
        else if(resultados.get(i).getTypes().contains("country")){
        String pais = resultados.get(i).getLongName();
        dados.put("pais",pais);}
        }
        
        
        
        
        

        //System.out.println(numero+" "+rua+" "+bairro+" "+cidade+" "+estado+" "+pais);
        return dados;
    }

    public void createTableEndereco() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            conSqlite = DriverManager.getConnection("jdbc:sqlite:testedb.db");
            Statement stat = conSqlite.createStatement();
            stat.executeUpdate("drop table if exists user");

            //creating table
            stat.executeUpdate("create table endereco(sk_consumidor INT," + "cepconsumidor varchar(30),"
                    + "rua varchar(50)," + "numero varchar(50)," + "bairro varchar(50)," + "cidade varchar(50),"
                    + "estado varchar(50)," + "pais varchar(50)," + "latitude varchar(50)," + "longitude varchar(50),"
                    + "primary key (sk_consumidor));");
            stat.close();
            conSqlite.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEndereco() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs = null;
            PreparedStatement stmt;
            this.connectionPostgre = new ConnectionFactory().getConnection();
            stmt = this.connectionPostgre.prepareStatement("Select * From dw_procon.dim_consumidor ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                conSqlite = DriverManager.getConnection("jdbc:sqlite:testedb.db");
                PreparedStatement prep = conSqlite.prepareStatement("insert into endereco values(?,?,?,?,?,?,?,?,?,?);");
                prep.setInt(1, rs.getInt("sk_consumidor"));
                String cep = rs.getString("cepconsumidor");
                if (!cep.equals("0") && !cep.equals("NULL")) {
                    if (cep.length() == 7) {
                        cep = 0 + cep;
                    }
                    cep = cep.substring(0, 5) + "-" + cep.substring(5);
                    HashMap<String,String> dados = buscarCoordenadasEndereco(cep);
                    prep.setString(2, cep);
                    prep.setString(3, dados.get("rua"));
                    prep.setString(4, dados.get("numero"));
                    prep.setString(5, dados.get("bairro"));
                    prep.setString(6, dados.get("cidade"));
                    prep.setString(7, dados.get("estado"));
                    prep.setString(8, dados.get("pais"));
                    prep.setString(9, dados.get("latitude"));
                    prep.setString(10, dados.get("longitude"));
                    conSqlite.setAutoCommit(false);
                    prep.execute();
                    conSqlite.setAutoCommit(true);
                    prep.close();
                    conSqlite.close();
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void transferir() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            // database path, if it's new database,
            // it will be created in the project folder
            ResultSet rs = null;
            PreparedStatement stmt;
            this.connectionPostgre = new ConnectionFactory().getConnection();
            stmt = this.connectionPostgre.prepareStatement("Select * From dw_procon.dim_consumidor");
            rs = stmt.executeQuery();
            while (rs.next()) {
                conSqlite = DriverManager.getConnection("jdbc:sqlite:testedb.db");
                PreparedStatement prep = conSqlite.prepareStatement("insert into consumidor values(?,?,?,?,?,?,?,?);");
                prep.setInt(1, rs.getInt("sk_consumidor"));
                prep.setInt(2, rs.getInt("version"));
                prep.setString(3, rs.getString("date_from"));
                prep.setString(4, rs.getString("date_to"));
                prep.setString(5, rs.getString("cepconsumidor"));
                prep.setString(6, rs.getString("cod_sexoconsumidor"));
                prep.setString(7, rs.getString("faixaetariaconsumidor"));
                prep.setString(8, rs.getString("desc_sexoconsumidor"));
                conSqlite.setAutoCommit(false);
                prep.execute();
                conSqlite.setAutoCommit(true);
                prep.close();
                conSqlite.close();
            }
            rs.close();
            stmt.close();


        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DataHelper dh = new DataHelper();
        //dh.createTableEndereco();
        //dh.buscarCoordenadasEndereco("08588-250");
        //dh.createTableEndereco();
        dh.addEndereco();
        //dh.transferir();
    }
}
