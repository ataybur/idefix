package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

import database.putter;
import database.selector;

import parser.Conn;
import parser.TestUrl;
import parser.parser;

public class tester {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SQLException, IOException,
			InterruptedException {
parser par=new parser();
TestUrl tst=new TestUrl();

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
		out.writeBytes(content);
		out.flush();
		out.close();
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
