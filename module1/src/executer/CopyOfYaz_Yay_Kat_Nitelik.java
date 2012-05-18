package executer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import parser.Conn;

import database.DB;
import database.DB.idefix_db;
import database.DB.idefix_db.kategori_nitelik;
import database.DB.idefix_db.yayin_kategori;
import database.DB.idefix_db.yayin_yayinevi;
import database.DB.idefix_db.yayinevi_nitelik;
import database.DB.idefix_db.yazar_nitelik;
import database.DB.idefix_db.yazar_yayin;




public class CopyOfYaz_Yay_Kat_Nitelik extends Basic_Class{
	public CopyOfYaz_Yay_Kat_Nitelik(String FileName) {
		super(FileName);
	}

	public void executor_with_limit(int limit_int, int offset_int) {
		ResultSet rs_yazar_id=null;
		ResultSet rs_yayinevi_id=null;
		ResultSet rs_kategori_id=null;
		ResultSet rs_yazar_nitelik=null;
		ResultSet rs_yayinevi_nitelik=null;
		ResultSet rs_kategori_nitelik=null;
		Integer Limit=limit_int;
		Integer Offset=offset_int;
		
		String id_source=null;
		String yayin_source=null;
		String dest_nitelik=null;
		String dest_id=null;
		ResultSet rs_id=null;
		ResultSet rs_nitelik=null;
		id_source=yayin_yayinevi.id_yayinevi();
		yayin_source=yayin_yayinevi.name();
		dest_nitelik=yayinevi_nitelik.name();
		dest_id=yayinevi_nitelik.id_yayinevi();
		try {
//			rows=new Object[1];			
//			rows[0]=yazar_yayin.id_yazar();
//			rs_yazar_id=sel.return_select_with_limit(rows, idefix_db.name(), yazar_yayin.name(), Limit, Offset, Conn.user, Conn.pswd);

			rows=new Object[1];
			rows[0]=yayin_yayinevi.id_yayinevi();
			rs_yayinevi_id=sel.return_select_with_limit(rows, idefix_db.name(), yayin_yayinevi.name(), Limit, Offset, Conn.user, Conn.pswd);
			
//			rows=new Object[1];
//			rows[0]=idefix_db.yayin_kategori.id_kategori();
//			rs_kategori_id=sel.return_select_with_limit(rows, idefix_db.name(), yayin_kategori.name(), Limit, Offset, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
//			while(rs_yazar_id.next())
//			{
//				rs_yazar_nitelik=sel.select_nitelik(idefix_db.name(), yazar_yayin.name(), yazar_yayin.id_yazar(), rs_yazar_id.getInt(1), Conn.user, Conn.pswd);
//				while(rs_yazar_nitelik.next())
//				put.put_in_nitelikler(yazar_nitelik.name(),yazar_nitelik.id_yazar(),rs_yazar_id.getInt(1), rs_yazar_nitelik.getInt(1), rs_yazar_nitelik.getInt(2));												
//			}
			
			while(rs_yayinevi_id.next())
			{
				rs_yayinevi_nitelik=sel.select_nitelik(idefix_db.name(), yayin_yayinevi.name(), yayin_yayinevi.id_yayinevi(), rs_yayinevi_id.getInt(1), Conn.user, Conn.pswd);
				while(rs_yayinevi_nitelik.next())
				put.put_in_nitelikler(yayinevi_nitelik.name(),yayinevi_nitelik.id_yayinevi(),rs_yayinevi_id.getInt(1), rs_yayinevi_nitelik.getInt(1), rs_yayinevi_nitelik.getInt(2));
			}
			
//			while(rs_kategori_id.next())
//			{				
//				rs_kategori_nitelik=sel.select_nitelik(idefix_db.name(), yayin_kategori.name(), yayin_kategori.id_kategori(), rs_kategori_id.getInt(1), Conn.user, Conn.pswd);
//				while(rs_kategori_nitelik.next())
//				put.put_in_nitelikler(kategori_nitelik.name(),kategori_nitelik.id_kategori(),rs_kategori_id.getInt(1), rs_kategori_nitelik.getInt(1), rs_kategori_nitelik.getInt(2));				
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		CopyOfYaz_Yay_Kat_Nitelik yykn=new CopyOfYaz_Yay_Kat_Nitelik("deneme.txt");

		int limit_int = 100;
		int offset_int = 1;
		yykn.executor_with_limit(limit_int, offset_int);
		}
	
}
