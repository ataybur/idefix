package parser;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.postgresql.util.PSQLException;

import util.Trim;

import database.DB;
import database.DB.idefixMeta_db;
import database.DB.idefix_db;
import database.DB.idefix_db.cok_satanlar;
import database.DB.idefix_db.dizi_yayin;
import database.DB.idefix_db.kategoriler;
import database.DB.idefix_db.yayin_kategori;
import database.DB.idefix_db.yayin_yayinevi;
import database.DB.idefix_db.yayinevleri;
import database.DB.idefix_db.yayinlar;
import database.DB.idefix_db.yazar_yayin;
import database.DB.idefix_db.yazarlar;
import database.DB.idefix_db.yeniler;
import database.putter;
import database.selector;

public class parser {
	public List<Object[]> alphabetList;
	public List<Object[]> yayin_evi_list;
	public List<Object[]> kitap_ayrinti;
	public List<Object[]> kitap_url_list;
	public List<Object[]> dizilist;
	public List<Object> kategoriList;
	public List<Object> kitapList;
	public List<Object[]> kitapTurList;
	static public List<Object[]> kitap_yazarList;
	FileWriter fstream;
	BufferedWriter out;

	public parser() throws IOException {
		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);
	}

	protected void finalize() {

		try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public Document get_sayfa(String url, Map<String, String> data,
			Integer Sayfa_No) {
		/*
		 * Map<String, String> data = new HashMap<String, String>();
		 * data.put("kisiid", ""); data.put("tree", ""); data.put("query", "");
		 * data.put("fid", "8827"); data.put("dzid", ""); data.put("sira", "0");
		 * data.put("sayfa", Sayfa_No.toString());
		 */
		data.put("sayfa", Sayfa_No.toString());
		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).data(data).post();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).data(data).post();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}
		return doc;
	}

	public Integer get_sayfa_sayisi(String url, Map<String, String> data) {
		// ////System.out.print("ic_url:" + url);
		Integer sayfa_sayisi = new Integer(0);

		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).timeout(0).data(data).post();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Elements sayfa_sayi = doc.select("div.pagingbox > a[href]");
		if (!sayfa_sayi.isEmpty()) {
			for (Element no : sayfa_sayi)
				sayfa_sayisi++;
			sayfa_sayisi = sayfa_sayisi / 2;
		} else
			sayfa_sayisi = 1;
		return sayfa_sayisi;
	}

	public String[] get_Data(String url) {
		String[] temp_array = new String[2];
		String[] temp_ret_String_Array = new String[2];
		String[] ret_String_Array = null;

		String temp_data = new String();
		temp_array = url.split("\\?");
		temp_data = temp_array[1];
		temp_array = null;
		temp_array = temp_data.split("&");
		temp_data = null;
		if (temp_array.length == 1) {
			ret_String_Array = new String[2];
			ret_String_Array = temp_array[0].split("=");
		} else if (temp_array.length == 2) {
			ret_String_Array = new String[4];
			temp_ret_String_Array = temp_array[0].split("=");
			ret_String_Array[0] = temp_ret_String_Array[0];
			ret_String_Array[1] = temp_ret_String_Array[1];
			temp_ret_String_Array = null;
			temp_ret_String_Array = temp_array[1].split("=");
			if (temp_ret_String_Array.length == 1) {
				ret_String_Array[2] = temp_ret_String_Array[0];
				ret_String_Array[3] = "bos";
			} else if (temp_ret_String_Array.length == 2) {
				ret_String_Array[2] = temp_ret_String_Array[0];
				ret_String_Array[3] = temp_ret_String_Array[1];
			}
		}
		return ret_String_Array;
	}

	public Map<String, String> get_Map(String url, String tur, String sayfa_no) {
		Map<String, String> data = new HashMap<String, String>();
		String[] inputs = null;
		if (cok_satanlar.name().compareTo(tur) != 0
				&& yeniler.name().compareTo(tur) != 0)
			inputs = get_Data(url);
		if (tur.compareTo(Turler.kategori) == 0) {
			data.put("kisiid", "");
			data.put("tree", inputs[1]);
			data.put("query", "");
			data.put("fid", "");
			data.put("dzid", "");
			data.put("sira", "0");
			data.put("sayfa", sayfa_no);
		} else if (tur.compareTo(Turler.yayinevi) == 0) {
			data.put("kisiid", "");
			data.put("tree", "");
			data.put("query", "");
			data.put("fid", inputs[1]);
			data.put("dzid", "");
			data.put("sira", "0");
			data.put("sayfa", sayfa_no);
		} else if (tur.compareTo(Turler.dizi_yayinevi) == 0) {
			data.put("kisiid", "");
			data.put("tree", "");
			data.put("query", "");
			data.put("fid", inputs[1]);
			data.put("dzid", inputs[3]);
			data.put("sira", "0");
			data.put("sayfa", sayfa_no);
		} else if (tur.compareTo(Turler.yazar) == 0) {
			data.put("kisiid", inputs[1]);
			data.put("tree", "");
			data.put("query", "");
			data.put("fid", "");
			data.put("dzid", "");
			data.put("sira", "0");
			data.put("sayfa", sayfa_no);
		} else if (cok_satanlar.name().compareTo(tur) == 0)
			data.put("sayfa", sayfa_no);
		else if (yeniler.name().compareTo(tur) == 0)
			data.put("sayfa", sayfa_no);
		return data;

	}

	public void get_alphabetList(String url) {
		alphabetList = new LinkedList<Object[]>();
		putter put = new putter();
		Object[] temp = new Object[2];
		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}
		Elements alphabet = doc.select("a[href].poster");
		temp[0] = "harf";
		temp[1] = "url";
		alphabetList.add(temp);
		temp = new Object[2];
		for (Element letter : alphabet) {
			if (!letter.text().contentEquals("Hepsi")) {
				temp[0] = letter.text();
				temp[1] = letter.attr("abs:href");
				if (temp[0].toString().compareTo("Ö") == 0)
					temp[1] = temp[1].toString().replace("Ö", "%D6");
				else if (temp[0].toString().compareTo("İ") == 0)
					temp[1] = temp[1].toString().replace("İ", "%DD");
				else if (temp[0].toString().compareTo("Ş") == 0)
					temp[1] = temp[1].toString().replace("Ş", "%DE");
				else if (temp[0].toString().compareTo("Ü") == 0)
					temp[1] = temp[1].toString().replace("Ü", "%DC");
				else if (temp[0].toString().compareTo("Ç") == 0)
					temp[1] = temp[1].toString().replace("Ü", "%C7");
				alphabetList.add(temp);
				temp = new Object[2];
			}
		}
		for (Object[] object : alphabetList)
			System.out.println(object[0].toString() + " "
					+ object[1].toString());
		try {
			put.put_in(idefixMeta_db.name(), idefixMeta_db.alfabe.name(),
					alphabetList);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void get_kategoriler(String url) {
		Object[] temp = new Object[3];
		putter put = new putter();
		kitapTurList = new LinkedList<Object[]>();
		String attributeKey = "abs:href";
		String ust_baslik = "isimsiz";

		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Elements kategori = doc.select("a[href].txt2,a[href].kitap");
		temp[0] = "kategori_ust";
		temp[1] = "kategori_alt";
		temp[2] = "url";
		kitapTurList.add(temp);
		temp = new Object[3];
		for (Element kat : kategori) {
			if (ust_baslik == "isimsiz")
				ust_baslik = kat.text().toString();
			else if (kat.className().compareTo("txt2") == 0)
				ust_baslik = kat.text().toString();

			// Üst başlık ve altbaşlığı beraber koy
			if (kat.className().compareTo("txt2") != 0) {
				temp[0] = ust_baslik;
				temp[1] = kat.text();
				temp[2] = kat.attr(attributeKey);
				kitapTurList.add(temp);
				temp = new Object[3];
			}

		}
		try {
			put.put_in(Conn.idefix_db, Conn.tb_kategoriler, kitapTurList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Integer> get_kategori(String url) {

		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Elements kategori = doc.select("div.tanimaltmargin > ul.aliste > li");
		ResultSet rs = null;
		LinkedList<Integer> kategori_id_list = new LinkedList<Integer>();
		String[] kategori_ad_array = null;
		selector sel = new selector();
		Object[] rows = new Object[2];
		rows[0] = kategoriler.kategori_ust();
		rows[1] = kategoriler.kategori_alt();
		for (Element kat : kategori) {
			kategori_ad_array = kat.text()
					.replace(" + Favori Kategorilerime Ekle", "").split(" / ");
			kategori_id_list.add(sel.return_select_id_with_where(rows,
					idefix_db.name(), idefix_db.kategoriler.name(),
					kategori_ad_array, Conn.user, Conn.pswd));
		}
		return kategori_id_list;
	}

	public void get_diziler(String yayinevi, String url) {

		selector sel = new selector();
		putter put = new putter();
		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Elements diziler = doc.select("ul.cliste > li > a[href]");
		
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Integer id_yayinevi = DB.idefix_db.yayinevleri.default_id();
		try {

			id_yayinevi = sel.return_selectedId(idefix_db.name(),
					yayinevleri.name(), yayinevleri.ad(), yayinevi, Conn.user,
					Conn.pswd);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Object[] rows = new Object[database.DB.idefix_db.diziler.sutun_sayisi() - 1];
		rows[0] = idefix_db.diziler.ad_dizi();
		rows[1] = idefix_db.diziler.id_yayinevi();
		rows[2] = idefix_db.diziler.url();
		data.add(rows);

		for (Element kat : diziler) {
			rows = new Object[database.DB.idefix_db.diziler.sutun_sayisi() - 1];

			rows[0] = (String) kat.text().trim();
			rows[1] = (Integer) id_yayinevi;
			rows[2] = kat.attr("abs:href");
			data.add(rows);
		}
		try {
			put.put_in(idefix_db.name(), idefix_db.diziler.name(), data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void set_dizi_yayin(String url, Integer id_yayin, Integer id_yayinevi) {

		selector sel = new selector();
		putter put = new putter();
		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Elements yayin_dizisi = doc
				.select("div.hreview-aggregate > div > a[href]");
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Integer id_dizi = idefix_db.diziler.default_id();
		String dizi = "dizisiz";

		Object[] rows = new Object[2];
		Object[] data_contents = new Object[2];
		rows[0] = idefix_db.diziler.ad_dizi();
		rows[1] = idefix_db.diziler.id_yayinevi();
		data.add(rows);

		for (Element yayin_dizi : yayin_dizisi) {
			if (yayin_dizi.attr("abs:href").contains("dzid"))
				dizi = yayin_dizi.text();
		}

		data_contents[0] = dizi;
		data_contents[1] = id_yayinevi;
		id_dizi = sel.return_select_id_with_where(rows, idefix_db.name(),
				idefix_db.diziler.name(), data_contents, Conn.user, Conn.pswd);

		data = new LinkedList<Object[]>();
		rows = new Object[idefix_db.dizi_yayin.sutun_sayisi()];
		data.add(dizi_yayin.get_rows());
		rows = new Object[idefix_db.dizi_yayin.sutun_sayisi()];
		rows[0] = id_dizi;
		rows[1] = id_yayin;
		data.add(rows);
		try {
			put.put_in(idefix_db.name(), dizi_yayin.name(), data);
		} catch (SQLException e) {
			for (Object[] objec : data)
				for (Object obj : objec)
					System.out.println("set_dizi_yayin: " + obj);
			e.printStackTrace();
		}

	}

	public void get_yayinevleri_ilk(String url) throws IOException,
			SQLException, InterruptedException {

		putter put = new putter();
		Object[] temp = new Object[3];
		yayin_evi_list = new LinkedList<Object[]>();
		yayin_evi_list.add(DB.idefix_db.yayinevleri.get_rows());
		Trim tr = new Trim();
		temp = new Object[DB.idefix_db.yayinevleri.sutun_sayisi() - 1];

		boolean flag = true;
		Document doc = null;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}

		Document doc2 = null;

		Elements yayin_evleri = doc.select("div.isimkutu > a[href]");

		Element eser_sayisi = null;
		for (Element yayin_evi : yayin_evleri) {

			flag = true;
			while (flag) {
				try {
					doc2 = Jsoup.connect(yayin_evi.attr("abs:href")).get();
					flag = false;
				} catch (IOException e) {
					try {
						Thread.sleep(1000);
						doc = Jsoup.connect(url).get();
						flag = false;
					} catch (IOException e1) {
					} catch (InterruptedException e2) {
					}
					flag = true;
				}
			}
			try {
				eser_sayisi = doc2.select("div.contenttitle").after("a[href]")
						.after("b").get(0);
			} catch (Exception e) {
				System.out.println("url: " + url);
				System.out.println(doc.baseUri());
			}
			if (eser_sayisi.hasText()) {
			}
			temp[0] = yayin_evi.text().replace("\'", "");
			temp[1] = tr.trim_urun_sayisi(eser_sayisi.text());
			temp[2] = yayin_evi.attr("abs:href");

			yayin_evi_list.add(temp);
			put.put_in(idefix_db.name(), yayinevleri.name(), yayin_evi_list);
			yayin_evi_list = new LinkedList<Object[]>();
			yayin_evi_list.add(DB.idefix_db.yayinevleri.get_rows());
			get_diziler(temp[0].toString(), temp[2].toString());
			temp = new Object[3];
		}
	}

	// parametre olarak bir yayinevi ya da kategori urlsi alıyor.
	// yayınevlerinin kitaplarının url adreslerini tutar.
	// yayınevi ya da kategori için
	// String tur="yayinevi";
	// ya da
	// String tur="kategori"
	public void get_kitap_url(String url, String tur) {
		kitap_url_list = new LinkedList<Object[]>();
		putter put = new putter();
		Map<String, String> data = new HashMap<String, String>();
		String url_proper = null;
		try {
			url_proper = new String(tanimkat(url));
		} catch (IOException e) {
			System.out.println("url_proper: " + url_proper);
			e.printStackTrace();
		}
		Object[] temp = new Object[2];
		temp[0] = tur;
		temp[1] = "url";
		kitap_url_list.add(temp);
		temp = new Object[2];
		data = get_Map(url_proper, tur, "1");
		Integer sayfa_sayisi = this.get_sayfa_sayisi(url_proper, data);

		for (Integer sayfa_no = 1; sayfa_no <= sayfa_sayisi; sayfa_no++) {
			Document doc = this.get_sayfa(url_proper, data, sayfa_no);

			// Kitap url'si için
			Elements url_satir2 = doc.select("div.listeurun > a[href]");
			Elements url_satir3 = null;
			if (tur == Turler.yayinevi)
				url_satir3 = doc.select("div.listeurun ~ i > a");
			else
				url_satir3 = doc.select("a[href].muzik");
			try {
				for (int i = 0; i < url_satir2.size() - 1; i++) {
					if (tur == Turler.yayinevi)
						temp[0] = url_satir3.get(i).text();
					else
						temp[0] = url_satir3.get(1).text();
					temp[1] = url_satir2.get(i).attr("abs:href");
					kitap_url_list.add(temp);
					temp = new Object[2];

				}
			} catch (Exception e) {
				System.out.println("url_proper: " + url_proper);
				e.printStackTrace();
			}
		}

		try {
			if (tur == Turler.yayinevi)
				put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_yay_url,
						kitap_url_list);
			else
				put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_kat_url,
						kitap_url_list);
		} catch (PSQLException e) {
			e.printStackTrace();
		}

	}

	public void get_kitap_ayrinti(String url, int row_id) {
		kitap_ayrinti = new LinkedList<Object[]>();
		kitap_yazarList = new LinkedList<Object[]>();
		List<Object[]> yayinevi_yeni = new LinkedList<Object[]>();
		Object[] kitap_yazarTemp = new Object[2];
		selector sel = new selector();
		putter put = new putter();
		Trim tr = new Trim();
		Object[] temp = new Object[DB.idefix_db.yayinlar.sutun_sayisi() - 1];
		Object[] yayinevi_temp = new Object[DB.idefix_db.yayinevleri
				.sutun_sayisi() - 1];
		Element eser_sayisi = null;
		Integer id_yayin = idefix_db.yayinlar.default_id();
		Integer id_yayinevi = idefix_db.yayinevleri.default_id();
		LinkedList<Integer> kategori_id_List = new LinkedList<Integer>();
		LinkedList<Object[]> kategori_yayin = new LinkedList<Object[]>();
		LinkedList<Object[]> yayin_yayinevi = new LinkedList<Object[]>();
		LinkedList<String> yazar_ad_list = new LinkedList<String>();
		LinkedList<Integer> yazar_id_list = new LinkedList<Integer>();
		String[] temp_yayinevi_dizi = new String[2];
		Object[] string_yazar = new Object[2];
		List<Object[]> list_yazar = new LinkedList<Object[]>();
		kitap_yazarTemp = new Object[DB.idefix_db.yazar_yayin.sutun_sayisi()];
		string_yazar[0] = "ad";
		string_yazar[1] = "url";
		string_yazar = new Object[2];
		if (kitap_ayrinti.size() == 0) {
			kitap_ayrinti.add(DB.idefix_db.yayinlar.get_rows());
		}
		Document doc = null;
		boolean flag = true;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}
		Element ad_kitap = null;
		try {
			// ilk parametre kitap adları için
			ad_kitap = doc.select("div.boxTanimisim > div").get(0);
		} catch (Exception e) {
			System.out
					.println("parser-> kitap_ayrinti -> Kitap adi alinamadi.");
			System.out.println("url: " + url);
			return;
		}
		Element adIkinci_kitap = null;
		temp[1] = "yok";
		// Eğer get(1) null değilse get(1) ikinci ad için
		if (doc.select("div.boxTanimisim > div").size() != 1) {
			adIkinci_kitap = doc.select("div.boxTanimisim > div").get(1);
			temp[1] = adIkinci_kitap.text().replace('\'', ' ');
		}

		// Yazar adı için
		Elements ad_yazar = doc.select("div.boxTanimVideo");

		// Yayınevi adı için
		Element ad_yayinevi = doc.select("h3.boxTanimyayinevi").get(0);
		Elements yayin_dizisi = doc.select("div.hreview-aggregate > a[href]");
		// Tanıtım yazısı için
		Element tanitim = null;
		temp[3] = "tanıtım yazısı yok";
		if (doc.select("div#tanimimagesfix").size() > 0) {
			tanitim = doc.select("div#tanimimagesfix").get(0);
			temp[3] = tanitim.text().replace("'", " ");
		}

		// Ayrıntılı bilgi için
		// Lakin bunu çözemedim.
		// Elements yayin_evleri5=doc.select("div#tanimimagesfix ~ hr");
		Elements yayin_evleri5 = doc.select("div.boxTanimcontent");

		temp[0] = ad_kitap.text().replace('\'', ' ');

		// TODO Çok yazarlı kitaplar ile kitabın yazarlarını ayrı bir tabloda
		// TODO eşlemek gerekiyor.
		// TODO Bunun için putter sınıfındaki methodlar üzerinde değişiklik
		// TODO yapmak gerekecektir.
		// TODO Fakat öncelikle kitap_ayrinti çoklularının tabloya eklenmesi
		// TODO gerekli. Çünkü kitabın id'si ile yazarların id'leri eşleşecek.
		for (Element yazar_ad : ad_yazar) {
			string_yazar[0] = yazar_ad.text().trim().replace('\'', ' ');
			yazar_ad_list.add(string_yazar[0].toString());
			string_yazar[1] = yazar_ad.attr("abs:href").trim()
					.replace('\'', ' ');
			list_yazar = new LinkedList<Object[]>();
			list_yazar.add(DB.idefix_db.yazarlar.get_rows());
			list_yazar.add(string_yazar);
			try {
				put.put_in(Conn.idefix_db, Conn.tb_yazarlar, list_yazar);
			} catch (PSQLException e) {
				e.printStackTrace();
			}

			// Henüz veritabanına eklenen yazarın id'sini alıp bir listede
			// tutarız.
			try {
				yazar_id_list.add(sel.return_selectedId(Conn.idefix_db,
						Conn.tb_yazarlar, yazarlar.ad(),
						string_yazar[0].toString(), Conn.user, Conn.pswd));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			string_yazar = new Object[2];
		}

		temp_yayinevi_dizi = tr.trim_dizi(ad_yayinevi.text());
		try {
			id_yayinevi = sel.return_selectedId(Conn.idefix_db,
					Conn.tb_yayinevleri, "ad", temp_yayinevi_dizi[0],
					Conn.user, Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (id_yayinevi == null) {
			yayinevi_temp[0] = temp_yayinevi_dizi[0];
			yayinevi_temp[2] = doc.select("h3.boxTanimyayinevi > a[href]")
					.get(0).attr("abs:href")
					.replace("firma.asp", "firma_urun_listele.asp");
			// Yeni bir yayınevi bulundu.
			// Bu yayinevinin kitaplarını idefixMeta_db.kitap_kat_url tablosuna
			// ekleyeceğim.
			try {
				get_kitap_url(yayinevi_temp[2].toString(), Turler.kategori);
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			// burdan itibaren while bitene kadar bağlantıyı sağlayıp kaynak
			// kodunu almaya çalışıyor.
			flag = true;
			while (flag) {
				try {
					eser_sayisi = Jsoup.connect(yayinevi_temp[2].toString())
							.timeout(0).get().select("div.contenttitle")
							.after("a[href]").after("b").get(0);
					flag = false;
				} catch (IOException e) {
					try {
						Thread.sleep(1000);
						eser_sayisi = Jsoup
								.connect(yayinevi_temp[2].toString())
								.timeout(0).get().select("div.contenttitle")
								.after("a[href]").after("b").get(0);
						flag = false;
					} catch (IOException e1) {
					} catch (InterruptedException e2) {
					}
					flag = true;
				}
			}

			yayinevi_temp[1] = tr.trim_urun_sayisi(eser_sayisi.text());
			yayinevi_yeni.add(DB.idefix_db.yayinevleri.get_rows());
			yayinevi_yeni.add(yayinevi_temp);
			try {
				put.put_in(DB.idefix_db.name(),
						DB.idefix_db.yayinevleri.name(), yayinevi_yeni);
			} catch (PSQLException e) {
				e.printStackTrace();
			}
			yayinevi_temp = new Object[DB.idefix_db.yayinevleri.sutun_sayisi() - 1];
			try {
				id_yayinevi = sel.return_selectedId(Conn.idefix_db,
						Conn.tb_yayinevleri, "ad", temp_yayinevi_dizi[0],
						Conn.user, Conn.pswd);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		temp[2] = id_yayinevi;

		if (temp_yayinevi_dizi.length > 1)
			this.set_diziler(temp_yayinevi_dizi[0].replace('\'', ' '),
					temp_yayinevi_dizi[1].replace('\'', ' '));

		temp[4] = ad_kitap.baseUri();

		kitap_ayrinti.add(temp);

		try {
			put.put_in(Conn.idefix_db, Conn.tb_yayinlar, kitap_ayrinti);
		} catch (PSQLException e) {
			e.printStackTrace();
		}

		kitap_ayrinti = new LinkedList<Object[]>();

		id_yayin = sel.return_select_id_with_where(yayinlar.get_rows(),
				idefix_db.name(), yayinlar.name(), temp, Conn.user, Conn.pswd);
		temp = new Object[idefix_db.yazar_yayin.sutun_sayisi()];
		kitap_yazarList.add(yazar_yayin.get_rows());
		for (Integer id_yazar : yazar_id_list) {
			temp[0] = id_yazar;
			temp[1] = id_yayin;
			kitap_yazarList.add(temp);
		}
		// yazar_yayin tablosunu doldurmak için
		try {
			put.put_in(idefix_db.name(), yazar_yayin.name(), kitap_yazarList);
		} catch (PSQLException e) {
			e.printStackTrace();
		}
		kitap_yazarList = new LinkedList<Object[]>();

		// yayin_yayinevi tablosunu doldurmak için
		temp = new Object[idefix_db.yayin_yayinevi.sutun_sayisi()];
		yayin_yayinevi.add(idefix_db.yayin_yayinevi.get_rows());
		temp[0] = id_yayin;
		temp[1] = id_yayinevi;
		yayin_yayinevi.add(temp);
		try {
			put.put_in(idefix_db.name(), idefix_db.yayin_yayinevi.name(),
					yayin_yayinevi);
		} catch (PSQLException e1) {
			e1.printStackTrace();
		}

		// kategori_yayin tablosunu doldurmak için
		kategori_id_List = get_kategori(url);
		temp = new Object[idefix_db.yayin_kategori.sutun_sayisi()];
		kategori_yayin.add(yayin_kategori.get_rows());
		for (Integer id_kategori : kategori_id_List) {
			if (id_yayin != null)
				temp[0] = (Integer) id_yayin;
			else
				temp[0] = (Integer) yayinlar.default_id();
			if (id_kategori != null)
				temp[1] = (Integer) id_kategori;
			else
				temp[1] = (Integer) kategoriler.default_id();
			kategori_yayin.add(temp);
			temp = new Object[idefix_db.yayin_kategori.sutun_sayisi()];
		}
		try {
			put.put_in(idefix_db.name(), yayin_kategori.name(), kategori_yayin);
		} catch (PSQLException e) {
			e.printStackTrace();
		}
		kategori_yayin = new LinkedList<Object[]>();
		set_dizi_yayin(url, id_yayin, id_yayinevi);
		temp = new Object[yayinlar.sutun_sayisi() - 1];
	}

	public void get_diziler1(String yayinevi, String url) throws IOException,
			SQLException {
		dizilist = new LinkedList<Object[]>();
		Document doc = null;
		boolean flag = true;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}
		Object[] temp = new Object[3];
		temp[0] = "ad_dizi";
		temp[1] = "id_yayinevi";
		temp[2] = "url";
		dizilist.add(temp);
		temp = new Object[3];
		Integer id_yayinevi = yayinevleri.default_id();
		Elements diziler = doc.select("ul.cliste > a[href]");
		selector sel = new selector();

		for (Element dizi : diziler) {
			temp[0] = dizi.text();
			id_yayinevi = sel.return_selectedId(Conn.idefix_db,
					Conn.tb_yayinevleri, "ad", yayinevi, Conn.user, Conn.pswd);
			temp[1] = id_yayinevi;

			temp[2] = dizi.attr("abs:href");
			dizilist.add(temp);
			temp = new Object[3];
		}
	}

	public void set_diziler(String ad_yayinevi, String ad_dizi) {
		selector sel = new selector();
		putter put = new putter();
		List<Object[]> data = new LinkedList<Object[]>();
		Object[] temp = new Object[2];
		String[] rows = new String[1];
		Integer id_yayinevi = DB.idefix_db.yayinevleri.default_id();
		temp[0] = "ad_dizi";
		temp[1] = "id_yayinevi";
		data.add(temp);

		temp = new Object[2];

		rows[0] = ad_yayinevi;
		// gerelki//////System.out.println("ad_yayinevi: "+ad_yayinevi);
		try {
			id_yayinevi = sel.return_selectedId(Conn.idefix_db,
					Conn.tb_yayinevleri, "ad", ad_yayinevi, Conn.user,
					Conn.pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		temp[0] = ad_dizi;
		temp[1] = id_yayinevi;
		// gerelki//////System.out.println("Set_diziler.temp[0]"+temp[0]);
		// gerelki//////System.out.println("Set_diziler.temp[1]"+temp[1]);
		data.add(temp);
		try {
			put.put_in(Conn.idefix_db, Conn.tb_diziler, data);
		} catch (PSQLException e) {
			e.printStackTrace();
		}
		temp = new Object[2];

	}

	public void get_gunceller(String url, String tur) {
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Object[] rows = new Object[1];
		Object[] data_content = new Object[1];
		rows[0] = yayinlar.url();
		putter put = new putter();
		selector sel = new selector();

		String url_proper = null;
		try {
			url_proper = new String(tanimkat(url));
		} catch (IOException e) {
			System.out.println("url_proper: " + url_proper);
			e.printStackTrace();
		}
		Object[] temp = new Object[1];
		temp[0] = "id_yayin";

		data.add(temp);
		temp = new Object[1];

		Integer sayfa_sayisi = this.get_sayfa_sayisi(url_proper,
				get_Map(url_proper, tur, "1"));
		for (Integer sayfa_no = 1; sayfa_no <= sayfa_sayisi; sayfa_no++) {
			Document doc = null;
			try {
				doc = Jsoup.connect(url_proper)
						.data("sayfa", sayfa_no.toString()).post();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// Kitap url'si için
			Elements url_satir2 = doc.select("div.listeurun > a[href]");

			try {
				for (int i = 0; i < url_satir2.size() - 1; i++) {
					data_content[0] = url_satir2.get(i).attr("abs:href");
					temp[0] = sel.return_select_id_with_where(rows,
							idefix_db.name(), yayinlar.name(), data_content,
							Conn.user, Conn.pswd);

					if (temp[0].toString().length() > 0
							|| temp[0].toString().isEmpty()) {
						// if(true){
						get_kitap_ayrinti(data_content[0].toString(), 0);
						temp = new Object[1];
						temp[0] = sel.return_select_id_with_where(rows,
								idefix_db.name(), yayinlar.name(),
								data_content, Conn.user, Conn.pswd);
					}
					data.add(temp);
					temp = new Object[1];
					data_content = new Object[1];
				}
			} catch (Exception e) {
				System.out.println("url_proper: " + url_proper);
				e.printStackTrace();
			}
		}

		try {
			if (tur.compareTo(cok_satanlar.name()) == 0)
				put.put_in(idefix_db.name(), cok_satanlar.name(), data);
			else if (tur.compareTo(yeniler.name()) == 0)
				put.put_in(idefix_db.name(), yeniler.name(), data);
		} catch (PSQLException e) {
			e.printStackTrace();
		}

	}

	// Bütün ürün listelerini görebilmek için
	public String tanimkat(String url) throws IOException {
		// this.out.flush();
		// gerelki//////System.out.println("tanimkat->URL: " + url);
		String ret_url = url;
		Document doc = null;
		boolean flag = true;
		while (flag) {
			try {
				doc = Jsoup.connect(url).get();
				flag = false;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
					doc = Jsoup.connect(url).get();
					flag = false;
				} catch (IOException e1) {
				} catch (InterruptedException e2) {
				}
				flag = true;
			}
		}
		// gerelki//////System.out.println("tanimkat_is_empty: "
		// + doc.select("div.tanimkat > a[href]").isEmpty());
		Elements tanimkat = doc.select("div.tanimkat > a[href]");
		Element tur_kitap;
		if (!tanimkat.isEmpty()) {
			tur_kitap = tanimkat.first();
			ret_url = tur_kitap.attr("abs:href").toString();
		}
		return ret_url;
	}

}
