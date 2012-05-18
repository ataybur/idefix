package executer;

import java.io.IOException;
import java.sql.SQLException;

import parser.Conn;
import parser.Turler;
import database.DB;
import database.DB.idefix_db;
import database.DB.idefix_db.yayinevleri;

public class Kitap_yay_url extends Basic_Class{

	public Kitap_yay_url(String FileName) {
		super(FileName);
	}
	public void executor(Integer Limit, Integer Offset) {

		rows = new String[1];
		rows[0] = DB.idefix_db.yayinevleri.url();
		try {
			rs = sel.return_select_with_limit(rows, Conn.idefix_db,
					Conn.tb_yayinevleri, Limit, Offset, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				System.out.println("rs.getString(1): " + rs.getString(1));
				par.get_kitap_url(rs.getString(1), Turler.yayinevi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {

		Kitap_yay_url kyu=new Kitap_yay_url("deneme.txt");
		Integer Limit=1;
		Integer Offset=1447;
		try {
			Limit = sel.return_count(idefix_db.name(), yayinevleri.name(), Conn.user, Conn.pswd);		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for(int i=Offset;i<=Limit;i++)
		kyu.executor(1, i);
	}

}

