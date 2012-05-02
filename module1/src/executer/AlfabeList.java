package executer;

import java.io.IOException;
import java.sql.SQLException;

import database.DB;

import parser.Conn;
import parser.Turler;

public class AlfabeList extends Basic_Class {

	public AlfabeList(String FileName) {
		super(FileName);
		String url_yayinevi = "http://www.idefix.com/kitap/firma_index.asp";
		this.Set_Url(url_yayinevi);
	}

	public void executor(Integer Limit, Integer Offset) {
		try {
			par.get_alphabetList(url);
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			rs = sel.return_alfabe();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				par.get_yayinevleri_ilk(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		AlfabeList alfabe = new AlfabeList("Executer.txt");
		Integer limit = 1000;
		for (int i = 1; i <= limit; i++) {
			System.out.println("limit: " + limit);
			alfabe.executor(1, i);
		}
	}

}
