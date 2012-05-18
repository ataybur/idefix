package executer;

import database.DB.idefix_db.cok_satanlar;
import database.DB.idefix_db.yeniler;
import parser.TestUrl;

public class Gunceller extends Basic_Class{
public Gunceller(String filename){
	super(filename);
}
	public void executor(){
		par.get_gunceller(TestUrl.url_cok_satanlar, cok_satanlar.name());
		par.get_gunceller(TestUrl.url_yeniler, yeniler.name());
	}
	public static void main(String[] args) {
		Gunceller gunc=new Gunceller("deneme.txt");
		gunc.executor();
		
	}

}
