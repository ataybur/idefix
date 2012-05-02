package executer;

import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import parser.Conn;

import database.DB;
import database.DB.idefix_db;
import database.DB.idefix_db.yazar_nitelik;
import database.DB.idefix_db.yazar_yayin;

public class Yazar_Nitelik extends Basic_Class {

	public Yazar_Nitelik(String FileName) {
		super(FileName);
	}

	public void executor_with_limit(Integer Limit, Integer Offset) {
		rows = new Object[DB.idefix_db.yazar_yayin.sutun_sayisi()];
		Object[] data_contents = new Object[1];
		rows[0] = DB.idefix_db.yazar_yayin.id_yayin();
		rows[1] = DB.idefix_db.yazar_yayin.id_yazar();
		LinkedList<Object[]> yayin_nitelik_sayi = new LinkedList<Object[]>();
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		LinkedList<Integer> id_yazar_list = new LinkedList<Integer>();
		try {
			id_yazar_list = sel.return_selectedColumnDiscinct(idefix_db.name(),
					yazar_yayin.name(), yazar_yayin.id_yazar(), Limit, Offset,
					Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Integer id_yazar_entry : id_yazar_list) {
			
			rows = new Object[1];
			rows[0] = yazar_yayin.id_yazar();
			data_contents[0] = id_yazar_entry;
			
			// bir yazarın bütün kitaplarını döndürüyor.
			rs = sel.return_select_with_where(rows, idefix_db.name(),
					yazar_yayin.name(), data_contents, Conn.user, Conn.pswd);
			
			try {
				while (rs.next()) {
					rows = new Object[yazar_nitelik.sutun_sayisi()];
					rows=yazar_nitelik.get_rows();
					data=new LinkedList<Object[]>();
					data.add(rows);
					rows = new Object[yazar_nitelik.sutun_sayisi()];
					
					yayin_nitelik_sayi = (LinkedList<Object[]>) yazar_nitelik
							.get_yazar_nitelik_sayi(id_yazar_entry);
					for (Object[] obj : yayin_nitelik_sayi) {
						rows = new Object[yazar_nitelik.sutun_sayisi()];
						rows[0] = (Integer) id_yazar_entry;
						rows[1] = (Integer) obj[0];
						rows[2] = (Integer) obj[1];
						rows[3] = (Integer) 1;
						data.add(rows);
					}
					try {
						put.put_in(idefix_db.name(), yazar_nitelik.name(), data);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Yazar_Nitelik yaz_nit=new Yazar_Nitelik("deneme.txt");
		yaz_nit.executor_with_limit(100, 210);
	}

}