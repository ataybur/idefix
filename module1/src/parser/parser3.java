package parser;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.Trim;

import database.putter;
import database.selector;

public class parser3 {
	public List<Object[]> alphabetList;
	public List<Object[]> yayin_evi_list;
	public List<Object[]> kitap_ayrinti;
	public List<Object[]> kitap_url_list;
	public List<Object[]> dizilist;
	public List<Object> kategoriList;
	public List<Object> kitapList;
	public List<Object[]> kitapTurList;

	public void get_alphabetList(String url) {
		alphabetList = new LinkedList<Object[]>();
		Object[] temp = new Object[2];
		Document doc = null;

		try {
			doc = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {
			e.printStackTrace();
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
				alphabetList.add(temp);
				temp = new Object[2];
			}
		}

	}

	public void get_kategoriler(String url) throws IOException {
		Object[] temp = new Object[3];
		File sourceCode=new File(" ");
		kitapTurList = new LinkedList<Object[]>();		
		String attributeKey = "abs:href";
		Document doc = null;
		String ust_baslik = "isimsiz";
		if(url.contains("<HTML>")){
			
		//sourceCode=new File(url);
		//charSetName olarak ne vermem gerektiğini bilmiyorum.
		doc=Jsoup.parse(url);
		}
		else
		doc = Jsoup.connect(url).timeout(0).get();
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

	}

	public void get_yayinevleri_ilk(String url) throws IOException,
			SQLException, InterruptedException {
		yayin_evi_list = new LinkedList<Object[]>();
		putter put = new putter();
		Object[] temp = new Object[3];
		temp[0] = "ad";
		temp[1] = "urun_sayisi";
		temp[2] = "url";
		yayin_evi_list.add(temp);
		Trim tr = new Trim();
		temp = new Object[3];

		Document doc = Jsoup.connect(url).timeout(0).get();
		Document doc2 = null;

		Elements yayin_evleri = doc.select("div.isimkutu > a[href]");
		Element eser_sayisi;
		for (Element yayin_evi : yayin_evleri) {
			doc2 = Jsoup.connect(yayin_evi.attr("abs:href")).timeout(0).get();
			eser_sayisi = doc2.select("div.contenttitle").after("a[href]")
					.after("b").get(0);

			System.out.println(tr.trim_urun_sayisi(eser_sayisi.text()));

			temp[0] = yayin_evi.text();
			temp[1] = tr.trim_urun_sayisi(eser_sayisi.text());
			temp[2] = yayin_evi.attr("abs:href");

			yayin_evi_list.add(temp);
			this.get_diziler(temp[0].toString(), temp[2].toString());
			put.put_in(Conn.idefix_db, Conn.tb_diziler, this.dizilist);
			temp = new Object[3];
		}

	}

	public void get_kitap_ismi(String url) {
		kitap_url_list = new LinkedList<Object[]>();
		Object[] temp = new Object[3];
		temp[0] = "";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Yazar adı için
		Elements yayin_evleri2 = doc.select("div.listeurun ~ a");

		// Yayınevi adı için
		Elements yayin_evleri3 = doc.select("div.listeurun ~ i > a");

		// ilk parametre kitap adları için
		Elements yayin_evleri = doc
				.select("div.listeurun > a[href],div.listeurun ~ a,div.listeurun ~ i > a");

		for (Element yayin_evi : yayin_evleri) {
			// yayin_evi_list.add(yayin_evi.text());
			// print("deneme: ",yayin_evi.text());
			// print("Yayin evi: %s",yayin_evi.attr("title"));
			System.out.println(yayin_evi.text());
		}
		for (Element yayin_evi : yayin_evleri2) {
			// print("deneme2 %s",yayin_evi.text());
			// print("deneme3 %s",yayin_evi.attr("title"));
		}
		for (Element yayin_evi : yayin_evleri3) {
			// print("deneme2 %s",yayin_evi.text());
			// print("deneme3 %s",yayin_evi.text());
		}
	}

	// parametre olarak bir yayinevi ya da kategori urlsi alıyor.
	// yayınevlerinin kitaplarının url adreslerini tutar.
	// yayınevi ya da kategori için
	// String tur="yayinevi";
	// ya da
	// String tur="kategori"
	public void get_kitap_url(String url, String tur) throws IOException {
		kitap_url_list = new LinkedList<Object[]>();
		Object[] temp = new Object[2];
		temp[0] = tur;
		temp[1] = "url";
		kitap_url_list.add(temp);
		temp = new Object[2];
		Document doc = Jsoup.connect(url).timeout(0).get();

		// Kitap url'si için
		Elements url_satir2 = doc.select("div.listeurun > a[href]");
		Elements url_satir3 = null;
		if (tur == "yayinevi")
			url_satir3 = doc.select("div.listeurun ~ i > a");
		else
			url_satir3 = doc.select("a[href].muzik");

		for (int i = 0; i < url_satir2.size() - 1; i++) {
			if (tur == "yayinevi")
				temp[0] = url_satir3.get(i).text();
			else
				temp[0] = url_satir3.get(1).text();
			temp[1] = url_satir2.get(i).attr("abs:href");
			kitap_url_list.add(temp);
			temp = new Object[2];

		}
	}

	public void get_kitap_ayrinti(String url) throws SQLException, IOException, InterruptedException {
		kitap_ayrinti = new LinkedList<Object[]>();
		selector sel = new selector();
		putter put = new putter();
		Trim tr = new Trim();
		Object[] temp = new Object[6];
		Document doc = null;
		Integer id_yazar = null;
		Integer id_yayinevi = null;
		String[] temp_yayinevi_dizi = new String[2];
		Object[] string_yazar = new Object[2];
		List<Object[]> list_yazar = new LinkedList<Object[]>();
		string_yazar[0] = "ad";
		string_yazar[1] = "url";
		list_yazar.add(string_yazar);
		string_yazar = new Object[2];
		if (kitap_ayrinti.size() == 0) {
			temp[0] = "ad";
			temp[1] = "ad_ikinci";
			temp[2] = "id_yazar";
			temp[3] = "id_yayinevi";
			temp[4] = "tanitim_yazi";
			temp[5] = "url";
			kitap_ayrinti.add(temp);
			temp = new Object[6];
		}
		try {
			doc = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ilk parametre kitap adları için
		Element ad_kitap = doc.select("div.boxTanimisim > div").get(0);

		Element adIkinci_kitap = null;
		temp[1] = "yok";
		// Eğer get(1) null değilse get(1) ikinci ad için
		if (doc.select("div.boxTanimisim > div").size() != 1) {
			adIkinci_kitap = doc.select("div.boxTanimisim > div").get(1);
			temp[1] = adIkinci_kitap.text().replace('\'', ' ');
		}

		// Yazar adı için
		Element ad_yazar = doc.select("div.boxTanimVideo").get(0);
		System.out.println("ad_yazar_base_uri: " + ad_yazar.baseUri());
		// Yayınevi adı için
		Element ad_yayinevi = doc.select("h3.boxTanimyayinevi").get(0);

		// Tanıtım yazısı için
		Element tanitim = null;
		temp[4] = "tanıtım yazısı yok";
		if (doc.select("div#tanimimagesfix").size() > 0) {
			tanitim = doc.select("div#tanimimagesfix").get(0);
			temp[4] = tanitim.text();
		}

		// Ayrıntılı bilgi için
		// Lakin bunu çözemedim.
		// Elements yayin_evleri5=doc.select("div#tanimimagesfix ~ hr");
		Elements yayin_evleri5 = doc.select("div.boxTanimcontent");
		// System.out.println(yayin_evleri5.text());

		temp[0] = ad_kitap.text().replace('\'', ' ');
		System.out.println("in parser : ad_kitap.text()" + temp[0]);
		string_yazar[0] = ad_yazar.text().trim().replace('\'', ' ');
		System.out.println(string_yazar[0]);
		string_yazar[1] = ad_yazar.attr("abs:href").trim().replace('\'', ' ');
		System.out.println(string_yazar[1]);
		list_yazar.add(string_yazar);
		put.put_in(Conn.idefix_db, Conn.tb_yazarlar, list_yazar);
		string_yazar = new Object[2];
		id_yazar = sel.return_selectedId(Conn.idefix_db, Conn.tb_yazarlar,
				"ad", ad_yazar.text().trim().replace('\'', ' '), Conn.user,
				Conn.pswd);
		System.out.println(id_yazar);
		temp[2] = id_yazar;
		System.out.println("id_yazar: " + temp[2]);
		temp_yayinevi_dizi = tr.trim_dizi(ad_yayinevi.text());
		System.out.println("temp_yayinevi_dizi: " + temp_yayinevi_dizi);
		id_yayinevi = sel.return_selectedId(Conn.idefix_db,
				Conn.tb_yayinevleri, "ad", temp_yayinevi_dizi[0], Conn.user,
				Conn.pswd);
		System.out.println("id_yayinevi: " + id_yayinevi);
		temp[3] = id_yayinevi;
		System.out.println("id_yayinevi: " + temp[3]);
		if (temp_yayinevi_dizi.length > 1)
			this.set_diziler(temp_yayinevi_dizi[0].replace('\'', ' '),
					temp_yayinevi_dizi[1].replace('\'', ' '));

		System.out.println("tanitim: " + temp[4]);
		temp[5] = ad_kitap.baseUri();
		System.out.println("ad_kitap.base_uri: " + temp[5]);
		kitap_ayrinti.add(temp);
		temp = new Object[6];
	}

	public void get_diziler(String yayinevi, String url) throws IOException,
			SQLException {
		dizilist = new LinkedList<Object[]>();
		Document doc = Jsoup.connect(url).timeout(0).get();
		Object[] temp = new Object[3];
		temp[0] = "ad_dizi";
		temp[1] = "id_yayinevi";
		temp[2] = "url";
		dizilist.add(temp);
		temp = new Object[3];
		Integer id_yayinevi = null;
		Elements diziler = doc.select("li > a[href]");
		selector sel = new selector();

		for (Element dizi : diziler) {
			System.out.println(dizi.text());
			temp[0] = dizi.text();
			id_yayinevi = sel.return_selectedId(Conn.idefix_db,
					Conn.tb_yayinevleri, "ad", yayinevi, Conn.user, Conn.pswd);
			System.out.println(id_yayinevi);
			temp[1] = id_yayinevi;
			System.out.println(dizi.attr("abs:href"));
			temp[2] = dizi.attr("abs:href");
			dizilist.add(temp);
			temp = new Object[3];
		}

	}

	public void set_diziler(String ad_yayinevi, String ad_dizi)
			throws IOException, SQLException, InterruptedException {
		selector sel = new selector();
		putter put = new putter();
		List<Object[]> data = new LinkedList<Object[]>();
		Object[] temp = new Object[2];
		String[] rows = new String[1];
		Integer id_yayinevi = null;
		temp[0] = "ad_dizi";
		temp[1] = "id_yayinevi";
		data.add(temp);

		temp = new Object[2];

		rows[0] = ad_yayinevi;
		id_yayinevi = sel.return_selectedId(Conn.idefix_db,
				Conn.tb_yayinevleri, "ad", ad_yayinevi, Conn.user, Conn.pswd);

		temp[0] = ad_dizi;
		temp[1] = id_yayinevi;
		data.add(temp);
		put.put_in(Conn.idefix_db, Conn.tb_diziler, data);
		temp = new Object[2];

	}

	// Bütün ürün listelerini görebilmek için
	public String tanimkat(String url) {
		// yayin_evi_list=new LinkedList<String>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element tur_kitap = doc.select("div.tanimkat > a[href]").first();
		// System.out.println(tur_kitap.toString());

		return tur_kitap.attr("abs:href").toString();
	}

}
