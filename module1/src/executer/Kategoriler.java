package executer;

import java.io.IOException;
import java.sql.SQLException;

import database.DB.idefix_db;
import database.DB.idefix_db.kategoriler;

import parser.Conn;

public class Kategoriler extends Basic_Class {

	public Kategoriler(String FileName) throws IOException {
		super(FileName);
		String url_kategori="http://www.idefix.com/kitap/kategori_index.asp";
		this.Set_Url(url_kategori);
		rows=new String[1];
		rows[0]=idefix_db.kategoriler.url();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public void executor(Integer Limit, Integer Offset){
		par.get_kategoriler(url);		

		
		try {
			rs=sel.return_select_with_limit(rows, Conn.idefix_db, Conn.tb_kategoriler, Limit, Offset, Conn.user, Conn.pswd);			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			while(rs.next())
			{		
				par.get_kitap_url(rs.getString(1), "kategori");
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
		
	}
	public static void main(String[] args){
		Kategoriler kategori=null;
		try {
			kategori=new Kategoriler("Executer.txt");
		} catch (IOException e) {
			
			e.printStackTrace();			
		}
		Integer Limit=0;
		Integer Offset=0;
		try {
			Offset=103;
			Limit = sel.return_count(idefix_db.name(), kategoriler.name(), Conn.user, Conn.pswd)-Offset;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Offset+" "+Limit);	
		for(int i=Offset;i<=Limit+Offset;i++){
			System.out.println(i);
		kategori.executor(1,i);
		}

	}

}
