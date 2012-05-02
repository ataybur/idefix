package executer;

import java.io.IOException;
import java.sql.SQLException;

import database.DB;

import parser.Conn;
import parser.Turler;

public class YayineviMeta extends Basic_Class{

	public YayineviMeta(String FileName) {
		super(FileName);
		String url_yayinevi="http://www.idefix.com/kitap/firma_index.asp";
		this.Set_Url(url_yayinevi);
	}

	public void executor(){
	
		rows=new String[1];
		rows[0]=DB.idefix_db.yayinevleri.url();
		try {
			rs=sel.return_select(rows, Conn.idefix_db, Conn.tb_yayinevleri, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			while(rs.next())
			{
				
				par.get_kitap_url(rs.getString(1), Turler.yayinevi);
				for(Object[] kitap : par.kitap_url_list)
				put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_yay_url, par.kitap_url_list);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		
	}
	public static void main(String[] args) {
		YayineviMeta alfabe=new YayineviMeta("Executer.txt");
		alfabe.executor();
	}

}
