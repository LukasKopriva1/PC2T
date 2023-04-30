package projectv2;

public class Anim extends Film {
	
	int doporucenyVek = 0;
	//konstruktor
	public Anim(String nazevAnim, int doporucenyVek, int rokVydani, String reziser){
		this.rokVydani = rokVydani;
		this.doporucenyVek = doporucenyVek;
		this.nazev = nazevAnim;
		this.typ = "Anim";
		this.reziser = reziser;
		
	}	
	public int getDoporucenyVek() {
		return doporucenyVek;
	}
	
	@Override
	public void getInfo() {
		System.out.println("");
		System.out.println("----------------------------------");
		System.out.println("");
		System.out.println(nazev);
		System.out.println("");
		System.out.println("rok vydani:     "+rokVydani);
		System.out.println("reziser:        "+reziser);
		System.out.println("doporuceny vek: "+doporucenyVek);
		System.out.println("");
		System.out.println("----------------------------------");
		
	}
	
	public void getInfoHodnoceni() {
		System.out.println("");
		System.out.println("----------------------------------");
		System.out.println("");
		System.out.println(nazev);
		System.out.println("");
		System.out.println("rok vydani:     "+rokVydani);
		System.out.println("reziser:        "+reziser);
		System.out.println("doporuceny vek: "+doporucenyVek);
		System.out.println("");
		
	}
	public void setVek(int doporucenyVek) {
		this.doporucenyVek = doporucenyVek;
	}
}
