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

	public void executor() {
		par.get_alphabetList(url);

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

	}

	public static void main(String[] args) {
		AlfabeList alfabe = new AlfabeList("Executer.txt");

		alfabe.executor();
	}

}
