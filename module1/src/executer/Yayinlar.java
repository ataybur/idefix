package executer;

import java.io.IOException;
import java.sql.SQLException;

import parser.Conn;
import database.DB;
import database.DB.idefixMeta_db;
import database.DB.idefix_db;

public class Yayinlar extends Basic_Class{
	
	public Yayinlar(String FileName)throws IOException{
		super(FileName);
		rows=new String[1];
		rows[0]=DB.idefixMeta_db.kitap_yay_url.url();
	}

	public void executor(){
		row_id=0;
		
				try {
					rs=sel.return_select(rows, Conn.idefixMeta_db,Conn.tb_kitap_kat_url, Conn.user, Conn.pswd);
				} catch (SQLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					while(rs.next())
					{
						
						par.get_kitap_ayrinti(rs.getString(1),row_id);
						put.put_in(Conn.idefix_db, Conn.tb_yayinlar, par.kitap_ayrinti);
						row_id++;
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

				
				
				
		
	}
	public void executor_with_limit(int limit_int,int offset_int){
	
		row_id=offset_int;
		Integer limit=new Integer(limit_int);
		Integer offset=new Integer(offset_int);
		try {
			rs=sel.return_select_with_limit(rows, Conn.idefixMeta_db,Conn.tb_kitap_yay_url,limit,offset, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			while(rs.next())
			{				
				par.get_kitap_ayrinti(rs.getString(1),row_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		

}
	public static void main(String[] args){
		
		Yayinlar yayinlar=null;
		
		
		try {
			yayinlar=new Yayinlar("Yayinlar.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer Offset=27583;
		Integer Limit=10;
		try {
			Limit=sel.return_count(idefixMeta_db.name(),idefixMeta_db.kitap_url.name(), Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=Offset;i<Limit;i++){
			System.out.println("i: "+i);
		try{
			yayinlar.executor_with_limit(1, i);
			}catch(Exception e){
				Offset--;
			}
		
		}
	

	}
}
