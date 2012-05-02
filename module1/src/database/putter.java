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
	public void put_in(String db_name, String table_name, List<Object[]> data)
			throws SQLException, IOException, InterruptedException {
		String connString = "jdbc:postgresql://localhost/" + db_name;
		String userName = "idefixer";
		String psword = "123456";
		Connection c = DriverManager
				.getConnection(connString, userName, psword);
		String query = "INSERT INTO " + table_name + " (";

		selector sel = new selector();
		parser par = new parser();
		putter put = new putter();
		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);
		//out.flush();
		String ad_kitap = null;
		String ad_yazar = null;
		Integer id_kitap = null;
		Integer id_yazar = null;

		// data.get(0) bir String dizisidir.
		// Hangi tablo çekilecekse sütun adlarını barındırır.
		ResultSet rs = sel.return_select(data.get(0), db_name, table_name,
				Conn.user, Conn.pswd);

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
				ps = c.prepareStatement(query);
				
				//sql cümleciğine parametreler ekleniyor.
				//j1 indisli her bir for döngüsünde bir satır ekleniyor.
				for (int j1 = 1; j1 < data.size(); j1++) {
					for (int i = 1; i <= data.get(j1).length; i++) {
						//gerekli////System.out.println("length "+data.get(j1).length+", PUTTER->data.get(j1)[i-1] :"+data.get(j1)[i-1]);						
						if (data.get(j1)[i - 1].getClass().getName() == "java.lang.String")
							// TODO Burada String halinde tutulan
							// TODO çoklu yazarlar birbirinden ayrılacak.
							// TODO Geçici bi String[]'e atılacaklar.
							ps.setString(i, data.get(j1)[i - 1].toString()
									.trim().replace('\'', ' '));													
						else if (data.get(j1)[i - 1].getClass().getName() == "java.lang.Integer")
							ps.setInt(i, (Integer) data.get(j1)[i - 1]);

					}
					//sql cümleciğine parametreler eklendi.
					
					//sql cümleciği execute ediliyor.
					will_duplicate=put.willDuplicate(db_name, table_name, data.get(0), data.get(j1), Conn.user, Conn.pswd);
					if (!will_duplicate)
					try {			
						ps.executeUpdate();
					} catch (PSQLException e) {
						e.printStackTrace();
						e.getSQLState();
					}
					//sql cümleciği execute edildi.
					
					
					// Bu noktadan sonra geçici String[] içinde tutulan
					// yazarlar
					// tabloya eklenmiş yazarın id'si ile birlikte
					// yazar_yayin tablosuna yerleştirilecekler.
//					if (table_name == DB.idefix_db.yayinlar.name()) {
//						//gerelki//gerekli////System.out.println("par.kitap_yazarList.toString(): "+par.kitap_yazarList.toString());						
//						//System.out.println("par.kitap_yazarList.size(): "+par.kitap_yazarList.size());
//			
//						for (int i = 1; i < par.kitap_yazarList.size(); i++) {
//							ad_yazar = par.kitap_yazarList.get(i)[0].toString();
//							ad_kitap = par.kitap_yazarList.get(i)[1].toString();
//							id_yazar = sel.return_selectedId(Conn.idefix_db,
//									Conn.tb_yazarlar, "ad", ad_yazar,
//									Conn.user, Conn.pswd);
//							id_kitap = sel.return_selectedId(Conn.idefix_db,
//									Conn.tb_yayinlar, "ad", ad_kitap, Conn.user,
//									Conn.pswd);
//							par.kitap_yazarList.get(i)[0] = id_yazar;
//							par.kitap_yazarList.get(i)[1] = id_kitap;
//						}
//						put.put_in(Conn.idefix_db, Conn.tb_yazar_yayin,
//								par.kitap_yazarList);
//					}
				}
				c.close();
				data.clear();
				Thread.sleep(100);

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
