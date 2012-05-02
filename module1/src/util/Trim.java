package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import database.DB;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;

public class Trim {
	public Trim() {
	}

	public void print(String msg, Object... args) {
		// gerelki//gerekliSystem.out.println(String.format(msg, args));
	}

	public String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	public String[] trim_dizi(String yayinevi_dizi) {
		String yayinevi = new String();
		String[] deneme;
		deneme = yayinevi_dizi.split(" / ");

		return deneme;
	}

	public Integer trim_urun_sayisi(String urun_string) {
		if (urun_string.compareTo("0") == 0)
			return -1;
		String[] ilk = urun_string.split("\\D");

		return Integer.parseInt(ilk[ilk.length - 1]);
	}

	public String kelime_cozumle(Kelime cozum) {

		return null;
	}

	public String[] tanitim_yazi_dan_cumle(String tanitim_yazi) {
		tanitim_yazi = tanitim_yazi.replace("(Arka", " ");
		tanitim_yazi = tanitim_yazi.replace("Kapak)", " ");
		tanitim_yazi = tanitim_yazi.replace("(Tanıtım", " ");
		tanitim_yazi = tanitim_yazi.replace("Bülteninden)", " ");
		String[] cumle = tanitim_yazi.split("\\. ");
		for (int i = 0; i < cumle.length; i++)
			cumle[i] = cumle[i].trim() + ".";
		return cumle;
	}

	public boolean nitelik_mi(Kelime cozum) {
		KelimeTipi tip = cozum.kok().tip();
		return ((tip.compareTo(tip.BAGLAC) != 0)
				&& (tip.compareTo(tip.EDAT) != 0)
				&& (tip.compareTo(tip.HATALI) != 0) && (tip.compareTo(tip.IMEK) != 0));
		// return tip.compareTo(tip.FIIL) != 0 || tip.compareTo(tip.ISIM) != 0
		// || tip.compareTo(tip.KISALTMA) != 0
		// || tip.compareTo(tip.OZEL) != 0
		// || tip.compareTo(tip.SIFAT) != 0
		// || tip.compareTo(tip.ZAMAN) != 0
		// || tip.compareTo(tip.ZAMIR) != 0
		// || tip.compareTo(tip.UNLEM) != 0;
	}

	public boolean is_integer(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public LinkedList<String> cumle_den_kelime(String cumle) {
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		Kelime[] cozumler = null;
		Integer flag = new Integer(0);
		LinkedList<String> nitelikler = new LinkedList<String>();
		String son_sozcuk = ".";
		if (cumle.length() > 3&&cumle.contains(" "))
			son_sozcuk = cumle.substring(cumle.trim().lastIndexOf(" "));

		// gerelki//gerekliSystem.out.println("son_sozcuk: " + son_sozcuk);
		son_sozcuk = son_sozcuk.trim();
		Locale trLoc = new Locale("tr", "TR");
		String[] kelimeler = null;
		kelimeler = cumle.split(" ");
		KelimeTipi tip = null;
		for (String sozcuk : kelimeler) {
			if (!(sozcuk.length() <= 2) && !is_integer(sozcuk))

				if (sozcuk.compareTo(son_sozcuk) == 0) {
					sozcuk.replace(".", " ");
					sozcuk.trim();
					sozcuk.toLowerCase(trLoc);
					cozumler = zemberek.kelimeCozumle(sozcuk);
					for (Kelime cozum : cozumler) {
						if (nitelik_mi(cozum)
								&& cozum.kok().tip().compareTo(KelimeTipi.FIIL) == 0) {
							nitelikler.add(cozum.kok().icerik().toString());

							break;
						}
						flag++;
						if (flag == cozumler.length)
							nitelikler.add(cozumler[0].kok().icerik()
									.toString());
					}
				} else {
					cozumler = zemberek.kelimeCozumle(sozcuk);

					if (cozumler.length > 0 && nitelik_mi(cozumler[0])) {
						nitelikler.add(cozumler[0].kok().icerik().toString());
					} else
						nitelikler.add(sozcuk.toLowerCase(trLoc));

				}
		}
		return nitelikler;
	}

	public static LinkedList<String> return_nitelik(String tanitim_yazi) {
		String[] cumleler = null;
		LinkedList<String> nitelikler = new LinkedList<String>();
		LinkedList<String> nitelikler_temp = new LinkedList<String>();

		Trim tr = new Trim();
		cumleler = tr.tanitim_yazi_dan_cumle(tanitim_yazi);
		for (String cumle : cumleler) {
			//gerekliSystem.out.println("Cumle: " + cumle);
			nitelikler_temp = tr.cumle_den_kelime(cumle);
			for (String nitelik : nitelikler_temp)
				nitelikler.add(nitelik);
		}
		return nitelikler;
	}
public HashMap<Object,Integer> returnHashMap(String tanitim_yazi){
	//gerekliSystem.out.println("in returnHashMap");
	
	HashMap<Object,Integer> hm=new HashMap<Object,Integer>();
	LinkedList<String> nitelikler=new LinkedList<String>();
	nitelikler=Trim.return_nitelik(tanitim_yazi);
	Integer nof=new Integer(1);
	//gerekliSystem.out.println("nitelikler.toString(): "+nitelikler.toString());
	for(String nitelik:nitelikler)
	{
		//gerekliSystem.out.println(nitelik);
		
		if(hm.containsKey(nitelik))
		{
			nof=hm.get(nitelik);
			hm.put(nitelik, nof+1);
		}
		else hm.put(nitelik, 1);
	}
	return hm;
}
public List<Object[]> returnArray(HashMap<Object,Integer> hm){
	
	Object[] arrayMap=hm.entrySet().toArray();
	Object[] arrayEntry=new Object[2];
	LinkedList<Object[]> arrayList=new LinkedList<Object[]>();
	Trim tr=new Trim(); 
	Integer length=0; 
	for(Object entry:arrayMap)
	{			//System.out.println(entry.toString());
		if(hm.keySet().getClass().getName().contains("String"))
		arrayEntry[0]=entry.toString().split("=")[0].toString();
		//else if(hm.keySet().getClass().getName().contains("Integer"))
			arrayEntry[0]=Integer.parseInt(entry.toString().split("=")[0].toString());
		length=entry.toString().split("=").length;
		if(tr.is_integer( entry.toString().split("=")[1]))		
			arrayEntry[1]=Integer.parseInt(entry.toString().split("=")[1]);
		else
		arrayEntry[1]=Integer.parseInt(entry.toString().split("=")[2]);
		arrayList.add(arrayEntry);
		arrayEntry=new Object[2];
	}
	return arrayList;
}
	public List<Object> trim_tanitim_yazi(String tanitim_yazi) {
		List<Object> nitelikList = new LinkedList<Object>();
		String[] nitelikler = null;
		Locale trLoc = new Locale("tr", "TR");

		tanitim_yazi = tanitim_yazi.replace("(Arka", " ");
		tanitim_yazi = tanitim_yazi.replace("Kapak)", " ");
		tanitim_yazi = tanitim_yazi.replace("(Tanıtım", " ");
		tanitim_yazi = tanitim_yazi.replace("Bülteninden)", " ");
		// Bütün rakamlar elenmedi.
		tanitim_yazi = Arrays.toString(tanitim_yazi.split("/d"));
		tanitim_yazi = tanitim_yazi.toLowerCase(trLoc);
		tanitim_yazi = tanitim_yazi.replace(",", " ");
		tanitim_yazi = tanitim_yazi.replace(";", " ");
		tanitim_yazi = tanitim_yazi.replace("'", " ");
		tanitim_yazi = tanitim_yazi.replace(":", " ");
		tanitim_yazi = tanitim_yazi.replace("\"", " ");
		tanitim_yazi = tanitim_yazi.replace("-", " ");
		tanitim_yazi = tanitim_yazi.replace("(", " ");
		tanitim_yazi = tanitim_yazi.replace(")", " ");
		tanitim_yazi = tanitim_yazi.replace("?", " ");
		tanitim_yazi = tanitim_yazi.replace("!", " ");
		tanitim_yazi = tanitim_yazi.replace("[", " ");
		tanitim_yazi = tanitim_yazi.replace("]", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" ve ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" nın ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" nin ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" nun ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" nün ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" bu ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" şu ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" o ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" bir ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" ya ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" da ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" de ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" ile ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" bile ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" için ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" çünkü ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" her ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" şey ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" daha ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" gibi ", " ");
		tanitim_yazi = tanitim_yazi.replaceAll(" en ", " ");
		tanitim_yazi = tanitim_yazi.replace(" a ", " ");
		tanitim_yazi = tanitim_yazi.replace(" b ", " ");
		tanitim_yazi = tanitim_yazi.replace(" c ", " ");
		tanitim_yazi = tanitim_yazi.replace(" ç ", " ");
		tanitim_yazi = tanitim_yazi.replace(" d ", " ");
		tanitim_yazi = tanitim_yazi.replace(" e ", " ");
		tanitim_yazi = tanitim_yazi.replace(" f ", " ");
		tanitim_yazi = tanitim_yazi.replace(" g ", " ");
		tanitim_yazi = tanitim_yazi.replace(" h ", " ");
		tanitim_yazi = tanitim_yazi.replace(" ı ", " ");
		tanitim_yazi = tanitim_yazi.replace(" i ", " ");
		tanitim_yazi = tanitim_yazi.replace(" j ", " ");
		tanitim_yazi = tanitim_yazi.replace(" k ", " ");
		tanitim_yazi = tanitim_yazi.replace(" l ", " ");
		tanitim_yazi = tanitim_yazi.replace(" n ", " ");
		tanitim_yazi = tanitim_yazi.replace(" o ", " ");
		tanitim_yazi = tanitim_yazi.replace(" ö ", " ");
		tanitim_yazi = tanitim_yazi.replace(" p ", " ");
		tanitim_yazi = tanitim_yazi.replace(" q ", " ");
		tanitim_yazi = tanitim_yazi.replace(" r ", " ");
		tanitim_yazi = tanitim_yazi.replace(" s ", " ");
		tanitim_yazi = tanitim_yazi.replace(" ş ", " ");
		tanitim_yazi = tanitim_yazi.replace(" t ", " ");
		tanitim_yazi = tanitim_yazi.replace(" u ", " ");
		tanitim_yazi = tanitim_yazi.replace(" ü ", " ");
		tanitim_yazi = tanitim_yazi.replace(" v ", " ");
		tanitim_yazi = tanitim_yazi.replace(" w ", " ");
		tanitim_yazi = tanitim_yazi.replace(" x ", " ");
		tanitim_yazi = tanitim_yazi.replace(" y ", " ");
		tanitim_yazi = tanitim_yazi.replace(" z ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 0 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 1 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 2 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 3 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 4 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 5 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 6 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 7 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 8 ", " ");
		tanitim_yazi = tanitim_yazi.replace(" 9 ", " ");
		// tanitim_yazi = tanitim_yazi.replace("(tanıtım", " ");
		// tanitim_yazi = tanitim_yazi.replace("bülteninden)", " ");

		nitelikler = tanitim_yazi.split(" ");
		String temp = new String();
		for (String nitelik : nitelikler) {
			temp = nitelik.trim();
			if (temp.length() > 3) {
				// //gerelki//gerekliSystem.out.println(temp);
				nitelikList.add(temp);
			}
			temp = new String();
		}
		return nitelikList;

	}

}
