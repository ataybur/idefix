package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.postgresql.PGStatement;
import org.postgresql.util.PSQLException;

import parser.Conn;
import parser.parser;

public class putter {
	/**
	 * @param db_name
	 * @param table_name
	 * @param data
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void put_in(String db_name, String table_name, List<Object[]> data) throws PSQLException
			{
		String connString = "jdbc:postgresql://localhost/" + db_name;
		String userName = "idefixer";
		String psword = "123456";
		Connection c=null;
		try {
			c = DriverManager
					.getConnection(connString, userName, psword);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String query = "INSERT INTO " + table_name + " (";

		selector sel = new selector();
		putter put = new putter();


		// data.get(0) bir String dizisidir.
		// Hangi tablo çekilecekse sütun adlarını barındırır.
		try {
			ResultSet rs = sel.return_select(data.get(0), db_name, table_name,
					Conn.user, Conn.pswd);
		} catch (SQLException e1) {
			System.out.println("db_name: "+db_name+", table_name: "+table_name);
			for(Object obj:data.get(0))
				System.out.println("data[]: "+obj.toString());
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("db_name: "+db_name+", table_name: "+table_name);
			for(Object obj:data.get(0))
				System.out.println("data[]: "+obj.toString());
			e1.printStackTrace();
		}
		

		// data.get(0) sütun adlarını içerdiğinden dolayı
		// sayaç j=1'den başlıyor.
		for (int j = 1; j < data.size(); j++) {
			//elimizdeki eklenecek çoklu haklihazırda veritabanında var mı diye bakar?
			//eklenecek çoklu, veritabanında yer alan bütün çokluları tutan ResultSet'le
			//karşılaştırılır.
			boolean will_duplicate=put.willDuplicate(db_name, table_name, data.get(0), data.get(j), Conn.user, Conn.pswd);
					
			if (!will_duplicate) {
				//sql insert into cümleciği hazırlanıyor.
				for (int i = 0; i < data.get(0).length; i++) {
					query = query + data.get(0)[i];
					if (i != data.get(0).length - 1)
						query = query + ",";
					else
						query = query + ")";
				}
				query = query + " VALUES (";
				for (int i = 0; i < data.get(0).length; i++) {
					query = query + "?";
					if (i != data.get(0).length - 1)
						query = query + ",";
					else
						query = query + ");";
				}
				//sql insert into cümleciği hazırlandı.
				
				PreparedStatement ps = null;
				try {
					ps = c.prepareStatement(query);
				} catch (SQLException e1) {
					System.out.println("quey: "+query);
					e1.printStackTrace();
				}
				
				//sql cümleciğine parametreler ekleniyor.
				//j1 indisli her bir for döngüsünde bir satır ekleniyor.
				for (int j1 = 1; j1 < data.size(); j1++) {
					for (int i = 1; i <= data.get(j1).length; i++) {
						if (data.get(j1)[i - 1].getClass().getName() == "java.lang.String")
							try {
								ps.setString(i, data.get(j1)[i - 1].toString()
										.trim().replace('\'', ' '));
							} catch (SQLException e) {
								System.out.println("1: "+data.get(j1)[i - 1].toString()
										.trim().replace('\'', ' '));
								System.out.println("2: "+data.get(j1)[i - 1]);
								e.printStackTrace();
							}
						else if (data.get(j1)[i - 1].getClass().getName() == "java.lang.Integer")
							try {
								ps.setInt(i, (Integer) data.get(j1)[i - 1]);
							} catch (SQLException e) {
								e.printStackTrace();
							}

					}
					//sql cümleciğine parametreler eklendi.
					
					//sql cümleciği execute ediliyor.
					will_duplicate=put.willDuplicate(db_name, table_name, data.get(0), data.get(j1), Conn.user, Conn.pswd);
					if (!will_duplicate)
					 try {
							ps.executeUpdate();
						} catch (SQLException e) {							
							e.printStackTrace();
						}
								
				}
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				data.clear();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void put_in_kategoriler(String ust, String alt, String url)
			throws SQLException {
		Connection c = DriverManager.getConnection(
				"jdbc:postgresql://localhost/idefix_db", "idefixer", "123456");

		String query = "INSERT INTO kategoriler (kategori_ust, kategori_alt, url ) VALUES (?,?,?);";
		PreparedStatement ps = c.prepareStatement(query);

		ps.setString(1, ust.trim());
		ps.setString(2, alt.trim());
		ps.setString(3, url.trim());
		ps.executeUpdate();

		c.close();

	}
	
	public void put_in_nitelikler(String tb,String column,Integer id_temp,Integer id_nitelik,Integer sayi){
		Connection c=null;	
		
		String query = "INSERT INTO "+tb+" ("+column+", id_nitelik, sayi ) VALUES (?,?,?);";
		
		PreparedStatement ps=null;
		
		try {
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost/idefix_db", "idefixer", "123456");			
			ps = c.prepareStatement(query);
			ps.setInt(1, id_temp);
			ps.setInt(2, id_nitelik);
			ps.setInt(3, sayi);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("query?: "+ps.toString());
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void restart_id(String tb_name){
		Connection c=null;
		PreparedStatement ps;
		String query = "ALTER SEQUENCE "+tb_name+"_id_seq RESTART WITH 1;";

		try {
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost/idefix_db", "idefixer", "123456");
			ps = c.prepareStatement(query);
			ps.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}



		

		

	}
	public boolean willDuplicate(String db,String tb,Object[] data_columns,Object[] data_contents,String user,String pswd) {
		
		boolean will_duplicate=false;
		selector sel=new selector();
		Integer count=0;
		////System.out.println("data_contents.length: "+data_contents.length);
		////System.out.println("data_contents[1]: "+data_contents[1]);
		count = sel.return_select_count_with_where(data_columns, db, tb, data_contents, user, pswd);
		//gerekli////System.out.println("count: "+count);
		if(count>0)
			will_duplicate=true;
		return will_duplicate;
	}
}
