package executer;

import java.io.IOException;
import java.sql.SQLException;

import parser.Conn;
import database.selector;
import database.DB.idefixMeta_db;

public class Thread3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		selector sel=new selector();
		Yayinlar yayinlar=null;
				
		try {
			yayinlar=new Yayinlar("Yayinlar.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer Offset=19;
		Integer Limit=0;
		try {
			Offset=26200;
			Limit=sel.return_count(idefixMeta_db.name(),idefixMeta_db.kitap_yay_url.name(), Conn.user, Conn.pswd)*4/9;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=Offset;i<Limit;i++){
			System.out.println("i: "+i);
		yayinlar.executor_with_limit(1, i);
		}
	}

}
