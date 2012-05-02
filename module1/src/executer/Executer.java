package executer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.putter;
import database.selector;
import parser.Conn;
import parser.parser;

public class Executer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		parser par=new parser();
		selector sel=new selector();
		putter put=new putter();
		ResultSet rs=null;
		int row_id=0;
		FileWriter fstream=new FileWriter("/home/ataybur/workspace/module1/ek/Executer.txt",false);		
		BufferedWriter out=new BufferedWriter(fstream);
		
		String[] rows=new String[1];
		rows[0]="url";
		
		String url_kategori="http://www.idefix.com/kitap/kategori_index.asp";
		Thread.sleep(100);
		String url_yayinevi_index="http://www.idefix.com/kitap/firma_index.asp";
	
		par.get_kategoriler(url_kategori);
		Thread.sleep(100);
		//out.write("EXECUTOR-> kitapTurList internetten alındı");
		//out.newLine();
		put.put_in(Conn.idefix_db, Conn.tb_kategoriler, par.kitapTurList);
		Thread.sleep(100);
		//out.write("EXECUTOR-> kitapTurList veritabanına eklendi");
		//out.newLine();
		
		par.get_alphabetList(url_yayinevi_index);
		Thread.sleep(100);
		//out.write("EXECUTOR-> alphabetList internetten alındı");
		//out.newLine();
		
		put.put_in(Conn.idefixMeta_db, Conn.tb_alfabe, par.alphabetList);		
		Thread.sleep(100);
		//out.write("EXECUTOR-> alphabetList veritabanına eklendi");
		//out.newLine();
		
		rs=sel.return_alfabe();
		
		while(rs.next())
		{
			//out.write("EXECUTOR-> yayin_evi_list alınıyor");
			while(rs.next())
			{
				//out.write("EXECUTOR-> yayin_evi_list alınıyor");
				//out.newLine();
				par.get_yayinevleri_ilk(rs.getString(2));
				put.put_in(Conn.idefix_db, Conn.tb_yayinevleri, par.yayin_evi_list);
			}		
			//out.write("EXECUTOR-> Yayinevleri ve URL'leri idefixMeta_db'ye yazıldı");
			//out.newLine();
			//out.newLine();
			par.get_yayinevleri_ilk(rs.getString(2));
			put.put_in(Conn.idefix_db, Conn.tb_yayinevleri, par.yayin_evi_list);
		}		
		//out.write("EXECUTOR-> Yayinevleri ve URL'leri idefixMeta_db'ye yazıldı");
		//out.newLine();
		
		
		rs=sel.return_select(rows, Conn.idefix_db, Conn.tb_kategoriler, Conn.user, Conn.pswd);
		while(rs.next())
		{		
			par.get_kitap_url(rs.getString(1), "kategori");
			put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_kat_url, par.kitap_url_list);
		}
		//out.write("EXECUTOR-> Kategoriler ve URL'leri idefixMeta_db'ye yazıldı.");
		//out.newLine();
		
		//Seri		
		rs=sel.return_select(rows, Conn.idefix_db, Conn.tb_yayinevleri, Conn.user, Conn.pswd);
		while(rs.next())
		{
			////gerelkiSystem.out.println(rs.getString(1));
			par.get_kitap_url(rs.getString(1), "yayinevi");
			put.put_in(Conn.idefixMeta_db, Conn.tb_kitap_yay_url, par.kitap_url_list);
		}
		//out.write("EXECUTOR-> Yayınevleri ve URL'leri idefixMeta_db'ye yazıldı.");
		//out.newLine();
		
		//Seri
		rs=sel.return_select(rows, Conn.idefixMeta_db,Conn.tb_kitap_yay_url, Conn.user, Conn.pswd);
		while(rs.next())
		{
			
			par.get_kitap_ayrinti(rs.getString(1),row_id);
			row_id++;
			put.put_in(Conn.idefix_db, Conn.tb_yayinlar, par.kitap_ayrinti);
			
		}
		//out.write("EXECUTOR-> Kitap ayrıntıları idefix_db.yayin tablosuna yazıldı.");
		//out.newLine();
		
		//out.flush();
	}

}
