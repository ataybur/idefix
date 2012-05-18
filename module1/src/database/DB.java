package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import parser.Conn;
import util.Trim;

public final class DB {
	public final static class idefix_db {
		public static String name() {
			return "idefix_db";
		}

		public final static class kategoriler {
			public static String name() {
				return "kategoriler";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String url() {
				return "url";
			}

			public static String kategori_ust() {
				return "kategori_ust";
			}

			public static String kategori_alt() {
				return "kategori_alt";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();
				rows[1] = kategori_ust();
				rows[2] = kategori_alt();
				rows[3] = url();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = kategori_ust();
				rows[1] = kategori_alt();
				rows[2] = url();
				return rows;
			}
		}

		public final static class yayinlar {
			public static String name() {
				return "yayinlar";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String url() {
				return "url";
			}

			public static String ad() {
				return "ad";
			}

			public static String ad_ikinci() {
				return "ad_ikinci";
			}

			public static String id_yayinevi() {
				return "id_yayinevi";
			}

			public static String tanitim_yazi() {
				return "tanitim_yazi";
			}

			public static Integer sutun_sayisi() {
				return 6;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();
				rows[1] = ad();
				rows[2] = ad_ikinci();
				rows[3] = id_yayinevi();
				rows[4] = tanitim_yazi();
				rows[5] = url();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = ad();
				rows[1] = ad_ikinci();
				rows[2] = id_yayinevi();
				rows[3] = tanitim_yazi();
				rows[4] = url();
				return rows;
			}
		}

		public final static class yayinevleri {
			public static String name() {
				return "yayinevleri";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String url() {
				return "url";
			}

			public static String ad() {
				return "ad";
			}

			public static String urun_sayisi() {
				return "urun_sayisi";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();
				rows[1] = ad();
				rows[2] = urun_sayisi();
				rows[3] = url();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = ad();
				rows[1] = urun_sayisi();
				rows[2] = url();
				return rows;
			}
		}

		public final static class yazarlar {
			public static String name() {
				return "yazarlar";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String url() {
				return "url";
			}

			public static String ad() {
				return "ad";
			}

			public static Integer sutun_sayisi() {
				return 3;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();
				rows[1] = ad();
				rows[2] = url();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = ad();
				rows[1] = url();
				return rows;
			}

		}

		public final static class diziler {
			public static String name() {
				return "diziler";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String ad() {
				return "ad";
			}

			public static String ad_dizi() {
				return "ad_dizi";
			}

			public static String id_yayinevi() {
				return "id_yayinevi";
			}

			public static String url() {
				return "url";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();				
				rows[1] = ad_dizi();
				rows[2] = id_yayinevi();
				rows[3] = url();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = ad_dizi();
				rows[1] = id_yayinevi();
				rows[2] = url();
				return rows;
			}

		}

		public final static class cok_satanlar {
			public static String name() {
				return "cok_satanlar";
			}

			public static String id_yayin() {
				return "id_yayin";
			}
			

			public static String tarih() {
				return "tarih";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()-1];
				rows[0] = id_yayin();				
				return rows;
			}

		}

		public final static class nitelikler {
			public static String name() {
				return "nitelikler";
			}

			public static String id() {
				return "id";
			}
			public static Integer default_id() {
				return 1;
			}
			public void restart_id(){
				putter put=new putter();
				put.restart_id(name());
			}
			public static String nitelik() {
				return "nitelik";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows_with_id() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id();
				rows[1] = nitelik();
				return rows;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi() - 1];
				rows[0] = nitelik();
				return rows;
			}

			public static boolean isContain(String nitelik) {
				selector sel = new selector();
				Integer id = null;
				boolean is_contain = false;
				try {
					id = sel.return_selectedId(DB.idefix_db.name(), name(),
							nitelik(), nitelik, Conn.user, Conn.pswd);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (id != null)
					is_contain = true;
				return is_contain;
			}

			public static ResultSet getNitelik_with_id(String nitelik) {
				selector sel = new selector();
				ResultSet rs = null;
				try {
					rs = sel.return_select(get_rows_with_id(),
							DB.idefix_db.name(), name(), Conn.user, Conn.pswd);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return rs;
			}
		}

		public final static class kategori_nitelik {
			public static String name() {
				return "kategori_nitelik";
			}

			public static String id_kategori() {
				return "id_kategori";
			}

			public static String id_nitelik() {
				return "id_nitelik";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_kategori();
				rows[1] = id_nitelik();
				return rows;
			}
		}

		public final static class yayin_kategori {
			public static String name() {
				return "yayin_kategori";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static String id_kategori() {
				return "id_kategori";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yayin();
				rows[1] = id_kategori();
				return rows;
			}
		}

		public final static class yayin_nitelik {
			public static String name() {
				return "yayin_nitelik";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static String id_nitelik() {
				return "id_nitelik";
			}

			public static String sayi() {
				return "sayi";
			}

			public static String agirlik() {
				return "agirlik";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yayin();
				rows[1] = id_nitelik();
				rows[2] = sayi();
				rows[3] = agirlik();
				return rows;
			}

		}

		public final static class dizi_yayin {
			public static String name() {
				return "dizi_yayin";
			}

			public static String id_dizi() {
				return "id_dizi";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_dizi();
				rows[1] = id_yayin();
				return rows;
			}
		}

		public final static class yayin_yayinevi {
			public static String name() {
				return "yayin_yayinevi";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static String id_yayinevi() {
				return "id_yayinevi";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yayin();
				rows[1] = id_yayinevi();
				return rows;
			}
		}

		public final static class yeniler {
			public static String name() {
				return "yeniler";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static String tarih() {
				return "tarih";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()-1];
				rows[0] = id_yayin();				
				return rows;
			}
		}

		public final static class yazar_yayin {
			public static String name() {
				return "yazar_yayin";
			}

			public static String id_yazar() {
				return "id_yazar";
			}

			public static String id_yayin() {
				return "id_yayin";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yazar();
				rows[1] = id_yayin();
				return rows;
			}
		}

		public final static class yazar_nitelik {
			public static String name() {
				return "yazar_nitelik";
			}

			public static String id_yazar() {
				return "id_yazar";
			}

			public static String id_nitelik() {
				return "id_nitelik";
			}

			public static String sayi() {
				return "sayi";
			}

			public static String agirlik() {
				return "agirlik";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static List<Object[]> get_yazar_nitelik_sayi(Integer id_yazar) {
				HashMap<Object, Integer> hm = new HashMap<Object, Integer>();
				selector sel = new selector();
				ResultSet rs = null;
				ResultSet rs_yayin_nitelik = null;
				String db = DB.idefix_db.name();
				String tb = DB.idefix_db.yazar_yayin.name();
				String user = Conn.user;
				String pswd = Conn.pswd;
				Trim tr=new Trim();
				Integer yazar_sayi = 0;
				Object[] rows = new Object[1];
				Object[] data_contents = new Object[1];
				rows[0] = idefix_db.yazar_yayin.id_yazar();
				data_contents[0] = id_yazar;
				rs = sel.return_select_with_where(rows, db, tb, data_contents,
						user, pswd);
				try {
					while (rs.next()) {
						System.out.println("in here");
						rows = new Object[1];
						data_contents = new Object[1];
						rows[0] = idefix_db.yayin_nitelik.id_yayin();
						data_contents[0] = rs.getInt(2);
						//her bir yayın'ın niteliğini alıyor.
						rs_yayin_nitelik = sel.return_select_with_where(rows,
								idefix_db.name(), yayin_nitelik.name(),
								data_contents, user, pswd);
						while (rs_yayin_nitelik.next()) {
							System.out.println("rs_yayin_nitelik.getInt(2): "+rs_yayin_nitelik.getInt(2));
							if (!hm.containsKey(rs_yayin_nitelik.getInt(2))) {
								if(hm.get(rs_yayin_nitelik.getInt(2))!=null)
								yazar_sayi = hm.get(rs_yayin_nitelik.getInt(2));								
								hm.put(rs_yayin_nitelik.getInt(2), yazar_sayi
										+ rs_yayin_nitelik.getInt(3));

							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return tr.returnArray(hm);
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yazar();
				rows[1] = id_nitelik();
				rows[2] = sayi();
				rows[3] = agirlik();
				return rows;
			}
		}

		public final static class yazar_kategori {
			public static String name() {
				return "yazar_kategori";
			}

			public static String id_yazar() {
				return "id_yazar";
			}

			public static String id_kategori() {
				return "id_kategori";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yazar();
				rows[1] = id_kategori();
				return rows;
			}
		}

		public final static class yayinevi_nitelik {
			public static String name() {
				return "yayinevi_nitelik";
			}

			public static String id_yayinevi() {
				return "id_yayinevi";
			}

			public static String id_nitelik() {
				return "id_nitelik";
			}

			public static String sayi() {
				return "sayi";
			}

			public static String agirlik() {
				return "agirlik";
			}

			public static Integer sutun_sayisi() {
				return 4;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yayinevi();
				rows[1] = id_nitelik();
				rows[2] = sayi();
				rows[3] = agirlik();
				return rows;
			}
		}

		public final static class yayinevi_kategori {
			public static String name() {
				return "yayinevi_kategori";
			}

			public static String id_yayinevi() {
				return "id_yayinevi";
			}

			public static String id_kategori() {
				return "id_kategori";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0] = id_yayinevi();
				rows[1] = id_kategori();
				return rows;
			}
		}

	}

	public final static class idefixMeta_db {
		public static String name() {
			return "idefixMeta_db";
		}

		public final static class alfabe {
			public static String name() {
				return "alfabe";
			}

			public static String harf() {
				return "harf";
			}

			public static String url() {
				return "url";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0]=harf();
				rows[1]=url();
				return rows;
			}
		}

		public final static class kitap_yay_url {
			public static String name() {
				return "kitap_yay_url";
			}

			public static String yayinevi() {
				return "yayinevi";
			}

			public static String url() {
				return "url";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0]=yayinevi();
				rows[1]=url();
				return rows;
			}
		}

		public final static class kitap_url {
			public static String name() {
				return "kitap_url";
			}

			public static String url() {
				return "url";
			}

			public static Integer sutun_sayisi() {
				return 1;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0]=url();
				return rows;
			}
		}
		public final static class kitap_kat_url {
			public static String name() {
				return "kitap_kat_url";
			}

			public static String kategori() {
				return "kategori";
			}

			public static String url() {
				return "url";
			}

			public static Integer sutun_sayisi() {
				return 2;
			}

			public static Object[] get_rows() {
				Object[] rows = new String[sutun_sayisi()];
				rows[0]=kategori();
				rows[1]=url();
				return rows;
			}
		}
	}

}
