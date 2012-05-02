package executer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import parser.Conn;

public class Yayinevleri extends Basic_Class {

	public Yayinevleri(String FileName) {
		super(FileName);

	}

	public void executor() {
		try {
			rs = sel.return_alfabe();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				//gerelkiSystem.out.println("ALFABELİST->rs.getString(2): "
						//+ rs.getString(2));
				//out.write("EXECUTOR-> yayin_evi_list alınıyor");
				//out.newLine();
				par.get_yayinevleri_ilk(rs.getString(2));
				put.put_in(Conn.idefix_db, Conn.tb_yayinevleri,
						par.yayin_evi_list);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void exeutor(Map<String, String> harf_code_map) {
		String proper_url = null;
		try {
			rs = sel.return_alfabe();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		Iterator<Entry<String, String>> it = harf_code_map.entrySet()
				.iterator();	

		while (it.hasNext()) {

			
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			try {
				while (rs.next()) {
							if (entry.getKey().compareTo(rs.getString(1)) == 0) {
							System.out.print("Burda1");
							proper_url = rs.getString(2).replace(entry.getKey(),
									entry.getValue());
							par.get_yayinevleri_ilk(proper_url);
							proper_url = new String();
							for (Object[] yayin_evleri : par.yayin_evi_list)
								for (Object yayin_evi : yayin_evleri)
							put.put_in(Conn.idefix_db, Conn.tb_yayinevleri,
									par.yayin_evi_list);
						}
					}
				}
			 catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

	}
	}

	public static void main(String[] args) {
		Yayinevleri yayinevleri = null;
		yayinevleri = new Yayinevleri("Executor.txt");
		// yayinevleri.executor();

		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();
		Map<String, String> map3 = new HashMap<String, String>();
		Map<String, String> map4 = new HashMap<String, String>();
		Map<String, String> map5 = new HashMap<String, String>();
		Map<String, String> map6 = new HashMap<String, String>();
		map.put("Ö", "%D6");
		map2.put("İ", "%DD");
		map3.put("Ş", "%DE");
		map4.put("Ü", "%DC");
		map5.put("Ç", "%C7");
		map6.put("Y", "Y");

		
//			yayinevleri.exeutor(map);
//			yayinevleri.exeutor(map2);
//			yayinevleri.exeutor(map3);
//			yayinevleri.exeutor(map4);
			yayinevleri.exeutor(map5);
	
	}

}
