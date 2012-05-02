package parser;

import java.io.IOException;
import java.sql.SQLException;

public class Turler {
public static String kategori="kategori";
public static String yayinevi="yayinevi";
public static String dizi_yayinevi="dizi_yayinevi";
public static String yazar="yazar";
public static void main(String[] args) {
	String deneme="ökuz";
	System.out.println(deneme);
	System.out.println(deneme.replace("ö", "%D6"));
}
}

