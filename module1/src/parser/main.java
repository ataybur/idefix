package parser;

import java.io.IOException;

import database.DB.idefix_db.cok_satanlar;
import database.DB.idefix_db.yeniler;

public class main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) {
		parser par = null;
		try {
			par = new parser();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//par.get_gunceller(TestUrl.url_cok_satanlar, cok_satanlar.name());
		par.get_gunceller(TestUrl.url_yeniler, yeniler.name());
	}

}
