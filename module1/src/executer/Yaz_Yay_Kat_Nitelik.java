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




public class Yaz_Yay_Kat_Nitelik extends Basic_Class{
	public Yaz_Yay_Kat_Nitelik(String FileName) {
		super(FileName);
	}

	public void executor_with_limit(String tb, int limit_int, int offset_int) {

		Integer Limit=limit_int;
		Integer Offset=offset_int;
		
		String id_source=null;
		String yayin_source=null;
		String dest_nitelik=null;
		String dest_id=null;
		ResultSet rs_id=null;
		ResultSet rs_nitelik=null;
		
		
		if(tb.compareTo(yayinevi_nitelik.name())==0){
		id_source=yayin_yayinevi.id_yayinevi();
		yayin_source=yayin_yayinevi.name();
		dest_nitelik=yayinevi_nitelik.name();
		dest_id=yayinevi_nitelik.id_yayinevi();
		}else if(tb.compareTo(yazar_nitelik.name())==0){
			id_source=yazar_yayin.id_yazar();
			yayin_source=yazar_yayin.name();
			dest_nitelik=yazar_nitelik.name();
			dest_id=yazar_nitelik.id_yazar();
		}else if(tb.compareTo(kategori_nitelik.name())==0){
			id_source=yayin_kategori.id_kategori();
			yayin_source=yayin_kategori.name();
			dest_nitelik=kategori_nitelik.name();
			dest_id=kategori_nitelik.id_kategori();
		}
		try {
			rows=new Object[1];
			rows[0]=id_source;;
			rs_id=sel.return_select_with_limit(rows, idefix_db.name(), yayin_source, Limit, Offset, Conn.user, Conn.pswd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {			
			while(rs_id.next())
			{
				rs_nitelik=sel.select_nitelik(idefix_db.name(), yayin_source, id_source, rs_id.getInt(1), Conn.user, Conn.pswd);
				while(rs_nitelik.next())
				put.put_in_nitelikler(dest_nitelik,dest_id,rs_id.getInt(1), rs_nitelik.getInt(1), rs_nitelik.getInt(2));
			}					
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Yaz_Yay_Kat_Nitelik yykn=new Yaz_Yay_Kat_Nitelik("deneme.txt");

		int limit_int = 100;
		int offset_int = 100;
		//yykn.executor_with_limit(yayinevi_nitelik.name(),limit_int, offset_int);
		yykn.executor_with_limit(yazar_nitelik.name(),limit_int, offset_int);
		//yykn.executor_with_limit(kategori_nitelik.name(),limit_int, offset_int);
		}
	
}
