package executer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import database.DB;

import parser.Conn;
import util.Trim;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

public class Tanitim_Yazi extends Basic_Class {

	public Tanitim_Yazi(String FileName) {
		super(FileName);

	}

	public void executor() {
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		Kelime[] cozumler = null;
		rows = new Object[1];
		String eklenecek_nitelik = new String();
		List<Object> nitelikler = new LinkedList<Object>();
		Trim tr = new Trim();
		rows[0] = DB.idefix_db.yayinlar.tanitim_yazi();
		ResultSet rs_method = null;
		try {
			rs_method = sel.return_select(rows, Conn.idefix_db,
					Conn.tb_yayinlar, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ResultSet rs_nitelik = null;
		rows = new Object[DB.idefix_db.nitelikler.sutun_sayisi()];
		rows = DB.idefix_db.nitelikler.get_rows_with_id();

		try {
			while (rs_method.next()) {
				nitelikler = tr.trim_tanitim_yazi(rs_method.getString(1));
				for (Object nitelik : nitelikler) {
					// gerekliSystem.out.println(nitelik.toString());
					cozumler = zemberek.kelimeCozumle(nitelik.toString());
					rs_nitelik = sel.return_select(rows, Conn.idefix_db,
							Conn.tb_nitelikler, Conn.user, Conn.pswd);
					for (Kelime cozum : cozumler) {
						eklenecek_nitelik = cozum.toString();

						// gerekliSystem.out.println("kelime: " +
						// eklenecek_nitelik
						// + " kök: " + cozum.kok() + " tip: "
						// + cozum.kok().tip());

						// while (rs_nitelik.next())
						// if (rs_nitelik.getString(2).compareTo(
						// eklenecek_nitelik) == 0) {
						// //gerekliSystem.out.println("if: " +
						// eklenecek_nitelik);
						// } else {
						// //gerekliSystem.out
						// .println("else: " + eklenecek_nitelik);
						// }
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void executor_with_limit(int limit_int, int offset_int) {
		Integer limit = new Integer(limit_int);
		Integer offset = new Integer(offset_int);
		Integer yayin_id = null;
		Integer yayinevi_id = null;
		Integer kategori_id = null;
		Integer nitelik_id = null;
		LinkedList<Integer> yazar_id=null;
		HashMap<Object, Integer> nitelik_sayi_set = new HashMap<Object, Integer>();
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Trim tr = new Trim();
		ResultSet rs_method = null;
		Integer count = null;
		List<Object[]> object_array = null;
		// nitelikler kümesinin boş olup olmadığını bulmak için tablo satır
		// sayısını count'a yazıyorum.
		try {
			count = sel.return_count(DB.idefix_db.name(),
					DB.idefix_db.nitelikler.name(), Conn.user, Conn.pswd);
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		rows = new Object[3];

		rows[0] = DB.idefix_db.yayinlar.id();
		rows[1] = DB.idefix_db.yayinlar.tanitim_yazi();
		rows[2] = DB.idefix_db.yayinlar.id_yayinevi();

		// yayinlar tablosunun id ve tanitim_yazi sütunlarını alıyorum.
		try {
			rs_method = sel.return_select_with_limit(rows, Conn.idefix_db,
					Conn.tb_yayinlar, limit, offset, Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (rs_method.next()) {
				rows = new Object[DB.idefix_db.nitelikler.sutun_sayisi() - 1];
				rows = DB.idefix_db.nitelikler.get_rows();
				data.add(rows);
				yayin_id = rs_method.getInt(1);
				//System.out.println("Yayin_id: "+yayin_id);
				yayinevi_id = rs_method.getInt(3);
				yazar_id = sel.return_selectedIdArray(DB.idefix_db.name(),
						DB.idefix_db.yazar_yayin.name(),
						DB.idefix_db.yazar_yayin.id_yazar(),
						DB.idefix_db.yazar_yayin.id_yayin(),
						yayin_id.toString(), Conn.user, Conn.pswd);
				nitelik_sayi_set = tr.returnHashMap(rs_method.getString(2));

				// object_array: nitelik ve sayılarını array biçiminde tutan bir
				// liste
				object_array = tr.returnArray(nitelik_sayi_set);
				for (Object[] obj : object_array) {
					rows = new Object[DB.idefix_db.nitelikler.sutun_sayisi() - 1];
					rows[0] = obj[0];
					data.add(rows);
				}

				rows = new Object[DB.idefix_db.nitelikler.sutun_sayisi()];
				rows = DB.idefix_db.nitelikler.get_rows_with_id();
				if (count == 0) {
					try {
						put.put_in(DB.idefix_db.name(),
								DB.idefix_db.nitelikler.name(), data);
						data = new LinkedList<Object[]>();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} else {
					data = new LinkedList<Object[]>();
					rows = new Object[DB.idefix_db.nitelikler.sutun_sayisi() - 1];
					rows = DB.idefix_db.nitelikler.get_rows();
					data.add(rows);
					// gerekliSystem.out.println("rows.toString(): " +
					// rows.toString());

					for (Object[] obj : object_array) {
						rows = new Object[DB.idefix_db.nitelikler
								.sutun_sayisi() - 1];
						rows[0] = obj[0].toString();
						if (!DB.idefix_db.nitelikler.isContain(rows[0]
								.toString()))
							data.add(rows);
					}

					// gerekliSystem.out.println("deneme");
					try {
						put.put_in(DB.idefix_db.name(),
								DB.idefix_db.nitelikler.name(), data);
					} catch (InterruptedException e) {
						e.printStackTrace();

					}

				}
				data = new LinkedList<Object[]>();
				LinkedList<Object[]> data_yazar_nitelik = new LinkedList<Object[]>();
				rows = new Object[DB.idefix_db.yayin_nitelik.sutun_sayisi()];
				rows = DB.idefix_db.yayin_nitelik.get_rows();
				data.add(rows);
				rows = new Object[DB.idefix_db.yazar_nitelik.sutun_sayisi()];
				rows = DB.idefix_db.yazar_nitelik.get_rows();
				data_yazar_nitelik.add(rows);
				for (Object[] obj : object_array) {
					// gerekliSystem.out.println("obj[0].toString(): "+obj[0].toString());
					rows = new Object[DB.idefix_db.yayin_nitelik.sutun_sayisi()];
					nitelik_id = sel.return_selectedId(DB.idefix_db.name(),
							DB.idefix_db.nitelikler.name(),
							DB.idefix_db.nitelikler.nitelik(),
							obj[0].toString(), Conn.user, Conn.pswd);
					rows[0] = (Integer) yayin_id;
					rows[1] = (Integer) nitelik_id;
					rows[2] = (Integer) Integer.parseInt(obj[1].toString());
					rows[3] = (Integer) 1;
					data.add(rows);
//					rows = new Object[DB.idefix_db.yazar_nitelik.sutun_sayisi()];
//					for(int i=0;i<yazar_id.size();i++){
//					rows[0] = (Integer) yazar_id.get(i);
//					rows[1] = (Integer) nitelik_id;
//					rows[2] = (Integer) 1;
//					rows[3] = (Integer) 1;
//					data_yazar_nitelik.add(rows);
//					}
				}

				try {
					put.put_in(DB.idefix_db.name(),
							DB.idefix_db.yayin_nitelik.name(), data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// yayin_nitelik için bitir

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Tanitim_Yazi tanitim_yazi = new Tanitim_Yazi("out2.txt");

		int limit_int = 0;
		int offset_int = 0;
		try {
			//en son burada kaldım
			offset_int = 1697;
			limit_int = sel.return_count(DB.idefix_db.name(),
					DB.idefix_db.yayinlar.name(), Conn.user, Conn.pswd)
					- offset_int;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		for (int i = offset_int; i <= limit_int; i++) {
			System.out.println("offset:" + i);
			tanitim_yazi.executor_with_limit(1, i);
		}

	}

}
