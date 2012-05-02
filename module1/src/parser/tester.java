package parser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class tester {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// parser3 par=new parser3();
		// String url="/home/ataybur/workspace/module1/ek/WebSourceCode";
		// String url2="http://www.idefix.com/kitap/kategori_index.asp";
		// String
		// url3="http://www.idefix.com/kitap/sayfa6-yayinlari/firma_urun_listele.asp?fid=8827";
		// System.out.println(doSubmit(url3,"2"));
		// par.get_kategoriler(doSubmit(url3,"2"));
		// for(Object[] objs:par.kitapTurList)
		// for(Object obj:objs)
		// System.out.println(obj);
		parser par = new parser();
		String url_kategori = "http://www.idefix.com/kitap/anne-baba-kitaplari/kategori_urun.asp?tree=01001001";
		String url_yayinevi = "http://www.idefix.com/kitap/can-yayinlari/firma_urun_listele.asp?fid=456&dzid=";
		String url_dizi_yayinevi = "http://www.idefix.com/kitap/can-yayinlari-anlati-dizisi/firma_urun.asp?fid=456&dzid=9968";
		String url_yazar = "http://www.idefix.com/kitap/andre-gide/urun_liste.asp?kid=1349";
		Map<String, String> inputs = new HashMap<String, String>();
		for (String data : par.get_Data(url_kategori))
			System.out.println("kategori: " + data);
		for (String data : par.get_Data(url_yayinevi))
			System.out.println("yayinevi: " + data);
		for (String data : par.get_Data(url_dizi_yayinevi))
			System.out.println("dizi_yayinevi: " + data);
		for (String data : par.get_Data(url_yazar))
			System.out.println("yazar: " + data);
		inputs = par.get_Map(TestUrl.url_kategori, Turler.kategori, "1");
		System.out.println(inputs.toString());
		inputs = new HashMap<String, String>();
		inputs = par.get_Map(TestUrl.url_yayinevi, Turler.yayinevi, "1");
		System.out.println(inputs.toString());
		inputs = new HashMap<String, String>();
		inputs = par.get_Map(TestUrl.url_dizi_yayinevi, Turler.dizi_yayinevi, "1");
		System.out.println(inputs.toString());
		inputs = new HashMap<String, String>();
		inputs = par.get_Map(TestUrl.url_yazar, Turler.yazar, "1");
		System.out.println(inputs.toString());
	}

	public static String doSubmit(String url, String Sayfa_No) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("kisiid", "");
		data.put("tree", "");
		data.put("query", "");
		data.put("fid", "8827");
		data.put("dzid", "");
		data.put("sira", "0");
		data.put("sayfa", Sayfa_No);

		URL siteUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());

		Set<String> keys = data.keySet();
		Iterator<String> keyIter = keys.iterator();
		String content = "";
		for (int i = 0; keyIter.hasNext(); i++) {
			Object key = keyIter.next();
			if (i != 0) {
				content += "&";
			}
			content += key + "="
					+ URLEncoder.encode(data.get(key), "iso-8859-9");
		}
		//out.writeBytes(content);
		//out.flush();
		//out.close();
		// in nesnesi, sitenin kaynak kodunu tutan nesnedir.
		// Bunu parser methodlarına url adresi gibi yedirebilirsem
		// süper olacak!!! =))))))))))))))))))))))))))))))))))))))
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "iso-8859-9"));
		String line = "";
		String Ret = new String();
		while ((line = in.readLine()) != null) {
			Ret = Ret.concat(line);
		}
		in.close();
		return Ret;
	}

}
