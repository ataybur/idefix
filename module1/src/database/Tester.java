package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import parser.Conn;
import parser.Turler;
import parser.parser;

public class Tester {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		util.Trim test=new util.Trim();
		test.trim_dizi("deneme");
		Object[] abc=new Object[2];		
		parser par=new parser();
		putter put=new putter();
		selector sel=new selector();
		String url="http://www.idefix.com/Kitap/tanim.asp?sid=DR3THWNYH6O1EDI96ZKI";
		String url2="http://www.idefix.com/kitap/can-yayinlari/firma_urun_listele.asp?fid=456";
		String url3="http://www.idefix.com/kitap/firma_index.asp?harf=D";
		String url4="http://www.idefix.com/kitap/anne-baba-kitaplari/kategori_urun.asp?tree=01001001";
		String ad_yayinevi;
		String ad_dizi;
		int a=5;
		Integer b=4;
		String[] rows=new String[1];
		
		//b=sel.return_selectedId(Conn.idefix_db, Conn.tb_yayinevleri, "ad", "+1 Kitap", Conn.user, Conn.pswd);
		////gerelkiSystem.out.println(b);
		//abc[0]=b;
		//abc[1]=url;
		//for(Object ba:abc)
		////gerelkiSystem.out.println(ba.getClass().getName());
		//par.get_kitap_ayrinti(url);
		//put.put_in(Conn.idefix_db , Conn.tb_yayin,par.kitap_ayrinti);
		//par.get_diziler("Can Yayınları",url2);
		//put.put_in(Conn.idefix_db, Conn.tb_diziler, par.dizilist);
		//par.get_yayinevleri_ilk(url3);
		//put.put_in(Conn.idefix_db, Conn.tb_yayinevleri,par.yayin_evi_list );
		//par.get_kitap_url(url2,"yayinevi");
		//put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_yay_url, par.kitap_url_list);
		//par.get_kitap_url(url4,"kategori");
		//put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_kat_url, par.kitap_url_list);
/*		rows[0]="url";
		String url_proper=new String();
		ResultSet rs=sel.return_select(rows, Conn.idefix_db, Conn.tb_kategoriler, Conn.user, Conn.pswd);
		while(rs.next())
		{			
			//gerelkiSystem.out.println(rs.getString(1));
			par.get_kitap_url(rs.getString(1), Turler.kategori);
			put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_kat_url, par.kitap_url_list);
		}*/
		Object[] data_contents=new Object[DB.idefix_db.kategoriler.sutun_sayisi()-1];
		data_contents[0]="Aile (Kadın, Erkek ve Çocuk)";
		data_contents[1]="Anne Baba Kitapları";
		data_contents[2]="http://www.idefix.com/kitap/anne-baba-kitaplari/kategori.asp?tree=01001001";
		if(put.willDuplicate(DB.idefix_db.name(), DB.idefix_db.kategoriler.name(), DB.idefix_db.kategoriler.get_rows(), data_contents, Conn.user, Conn.pswd))
			System.out.println("It will duplicate");
	}

}
