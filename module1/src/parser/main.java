package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collections;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

import util.Trim;

public class main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException {

		String url = "http://www.idefix.com/kitap/kategori_index.asp";
		String url1 = "http://www.idefix.com/kitap/firma_index.asp";
		String url2 = "http://www.idefix.com/kitap/cocuk-bakimi/kategori.asp?tree=01001003";
		String url3 = "http://www.idefix.com/kitap/cocuk-bakimi/kategori_urun.asp?tree=01001003";
		String url4 = "http://www.idefix.com/Kitap/tanim.asp?sid=DR3THWNYH6O1EDI96ZKI";
		String url5 = "http://www.idefix.com/kitap/firma_index.asp?harf=A";

		parser par = new parser();
		database.putter put = new database.putter();
		database.selector sel = new database.selector();

		// ResultSet rs=sel.return_alfabe();
		// String[] rows=new String[2];
		// rows[0]="harf";
		// rows[1]="url";
		// ResultSet rs=sel.return_select(rows,
		// Conn.idefixMeta_db,Conn.tb_alfabe, Conn.user, Conn.pswd);
		//
		// while(rs.next())
		// System.out.println(rs.getString(1));

		// par.get_kitaptur(url);

		// Iterator i=par.kategoriList.iterator();

		// for(int i=0;i< par.kitapList.size();i++ )
		// par.get_kitap_ismi(par.tanimkat(par.kitapList.get(i)));

		// par.get_alphabetList(url1);

		// par.get_kitaptur(url);
		// System.out.println(par.tanimkat(url2));
		// par.get_kitap_ismi(par.tanimkat(url2));

		// par.get_kitap_ayrinti(url4);
		// for(String[] elements:par.kitap_ayrinti)
		// for(String element:elements)
		// System.out.println(element);

		//
		// while(rs.next()){
		// //System.out.println(par.alphabetList.get(i)[1]);
		// par.get_yayinevleri_ilk(rs.getString(2));
		// put.put_in(Conn.idefix_db, "yayinevleri",par.yayin_evi_list);}

		// for(String[] elements:par.yayin_evi_list)
		// for(String element:elements)
		// System.out.println(element);

		// put.put_in(Connection.idefix_db, Connection.tb_yayin,
		// par.kitap_ayrinti);
		// System.out.println(par.kitapTurList.size());
		// for(String[] a:par.kitapTurList){
		// // System.out.println(a[0]);
		// // System.out.println(a[1]);
		// // System.out.println(a[2]);
		// //put.put_in_kategoriler(a[0],a[1] ,a[2]);
		// //System.getProperty("line.seperator");
		// }
		// for(String[] alp:par.alphabetList)
		// {
		// // System.out.println(alp[0]);
		// // System.out.println(alp[1]);
		// }
		// // put.put_in("idefixMeta_db", "alfabe", par.alphabetList);
		// // put.put_in("idefix_db", "kategoriler", par.kitapTurList);

		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		FileWriter fstream=new FileWriter("/home/ataybur/workspace/module1/ek/out.txt");
		BufferedWriter out=new BufferedWriter(fstream);
		Kelime[] cozumler = null;
		String[] rows = new String[1];
		String eklenecek_nitelik = new String();
		List<Object> nitelikler = new LinkedList<Object>();
		Trim tr = new Trim();
		rows[0] = "tanitim_yazi";
		ResultSet rs = sel.return_select(rows, Conn.idefix_db, Conn.tb_yayinlar,
				Conn.user, Conn.pswd);
		ResultSet rs_nitelik = null;
		rows = new String[2];
		rows[0] = "id";
		rows[1] = "nitelik";
		// while(rs.next())
		
			while(rs.next()){
			nitelikler = tr.trim_tanitim_yazi(rs.getString(1));
			for (Object nitelik : nitelikler) {
				System.out.println(nitelik.toString());
				//out.write(nitelik.toString());
				//out.newLine();
				cozumler = zemberek.kelimeCozumle(nitelik.toString());
				rs_nitelik = sel.return_select(rows, Conn.idefix_db,
						Conn.tb_nitelikler, Conn.user, Conn.pswd);
				for (Kelime cozum : cozumler) {
					eklenecek_nitelik = cozum.toString();
					System.out.println(eklenecek_nitelik);
					//out.write(eklenecek_nitelik);
					//out.newLine();
					while (rs_nitelik.next())
						if (rs_nitelik.getString(2)
								.compareTo(eklenecek_nitelik) == 0) {
							System.out.println("if: "+eklenecek_nitelik);
						} else {
							System.out.println("else: "+eklenecek_nitelik);
						}
				}
			}
		}
			//out.close();
	}

}
