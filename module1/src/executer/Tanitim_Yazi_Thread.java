package executer;

import java.io.IOException;
import java.sql.SQLException;

import parser.Conn;
import database.DB;
import database.selector;

public class Tanitim_Yazi_Thread extends Thread{
	private Integer limit;
	private Integer offset;
		private Tanitim_Yazi ty=new Tanitim_Yazi("null.txt");
	Tanitim_Yazi_Thread(Integer limit_new,Integer offset_new){		
		limit=limit_new;
		offset=offset_new;
		
	}
	public void run(){
		try {
			ty.executor_with_limit(limit, offset);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer nofYayin=0;
		selector sel=new selector();
		try {
			nofYayin=sel.return_count(DB.idefix_db.name(),
					DB.idefix_db.yayinlar.name(), Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tanitim_Yazi_Thread ty1=new Tanitim_Yazi_Thread(10,1);
		Tanitim_Yazi_Thread ty2=new Tanitim_Yazi_Thread(10,11);
		ty1.start();
	//	ty2.start();
	}

}
