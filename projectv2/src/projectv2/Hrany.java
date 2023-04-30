package projectv2;

public class Hrany extends Film {

	//konstruktor
	public Hrany(String nazevFilm, int rokVydani, String reziser){
		this.nazev = nazevFilm;
		this.rokVydani = rokVydani;
		this.reziser = reziser;
		this.typ = "Hrany";
		
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
		System.out.println("");
		
	}
	
}
