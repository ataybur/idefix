package executer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;

import parser.parser;
import database.putter;
import database.selector;

public class Basic_Class {
	public static parser par;
	

	
	public static selector sel=new selector();
	public static putter put=new putter();
	public static ResultSet rs=null;
	protected static FileWriter fstream=null;
	protected static BufferedWriter out=null;
	public static Object[] rows=null;
	public static String url=null;
	public static int row_id;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public Basic_Class(String FileName){
		try {
			par=new parser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sel=new selector();
		put=new putter();
		rs=null;
		FileName="/home/ataybur/workspace/module1/ek/"+FileName;
		try {
			fstream=new FileWriter(FileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		out=new BufferedWriter(fstream);
		rows=null;
		url=null;
	}
	
	protected void finalize() {
	
			try {
				out.flush();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
	
	
	

		try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void Set_Url(String url_new){
		url=new String(url_new);
	}
	public static void main(String[] args) throws IOException {

		
	}

}
