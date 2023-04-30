package projectv2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabazeHodnoceni  {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	public List<Hodnoceni> seznamHodnoceni;
	
	DatabazeHodnoceni(){
		seznamHodnoceni = new ArrayList<>();
	}
	
	public void addHodnoceni(String film, int pocetBodu, String slovniHodnoceni, String typFilmu){
		seznamHodnoceni.add(new Hodnoceni(film, slovniHodnoceni, pocetBodu, typFilmu));
	
	}
	
	public void vypisHodnoceniAll() {
		
		for(Hodnoceni hodnoceni:seznamHodnoceni)
			System.out.println(hodnoceni.getFilm()+" "+hodnoceni.getSlovniHodnoceni());
	}

	public void vypisHodnoceniFilmu(String film) {
		String nactenyFilm;
		int pocitadlo = 0;
		Collections.sort(seznamHodnoceni);
		System.out.println(film+":");
		System.out.println("hodnoceni: ");
		for (Hodnoceni hodnoceni:seznamHodnoceni) {
			nactenyFilm = hodnoceni.getFilm();
			if(nactenyFilm.equals(film)) {
				pocitadlo = pocitadlo + 1;
				if(hodnoceni.getSlovniHodnoceni().equals("null")) {
					System.out.println("Hodnoceni cislo "+pocitadlo+". :");
					System.out.println("Pocet bodu: "+hodnoceni.getBodoveHodnoceni());
					System.out.println("-------------------------------------");
				}else {
					System.out.println("Hodnoceni cislo "+pocitadlo+". :");
					System.out.println("Pocet bodu: "+hodnoceni.getBodoveHodnoceni()+" slovni hodnoceni filmu: "+hodnoceni.getSlovniHodnoceni());
					System.out.println("-------------------------------------");
				}
				
			}
		}
		if(pocitadlo == 0)
			System.out.println(ANSI_RED+"Film nenalezen!"+ANSI_RESET);
	}
	
	public void vypisHodnoceniHrany(String film) {
		String nactenyFilm;
		int pocitadlo = 0;
		Collections.sort(seznamHodnoceni);
		//System.out.println(film+":");
		System.out.println("hodnoceni: ");
		for (Hodnoceni hodnoceni:seznamHodnoceni) {
			nactenyFilm = hodnoceni.getFilm();
			if(nactenyFilm.equals(film)) {
				pocitadlo = pocitadlo + 1;
				if(hodnoceni.getSlovniHodnoceni().equals("null")) {
					System.out.println("Hodnoceni cislo "+pocitadlo+".:");
					System.out.println(" - Pocet bodu: "+hodnoceni.getBodoveHodnoceni());
					//System.out.println("-------------------------------------");
					System.out.println("");
				}else {
					System.out.println("Hodnoceni cislo "+pocitadlo+".:");
					System.out.println(" - Pocet hvezdicek: "+hodnoceni.getBodoveHodnoceni());
					System.out.println(" - slovni hodnoceni filmu: "+hodnoceni.getSlovniHodnoceni());
					//System.out.println("-------------------------------------");
					System.out.println("");
				}
				
			}
		}
		if(pocitadlo == 0)
			System.out.println("Hodnoceni filmu nenalezeno!");
	}
	
	public void vypisHodnoceniAnim(String film) {
		String nactenyFilm;
		int pocitadlo = 0;
		Collections.sort(seznamHodnoceni);
		//System.out.println(film+":");
		System.out.println("hodnoceni: ");
		System.out.println("");
		for (Hodnoceni hodnoceni:seznamHodnoceni) {
			nactenyFilm = hodnoceni.getFilm();
			if(nactenyFilm.equals(film)) {
				pocitadlo = pocitadlo + 1;
				if(hodnoceni.getSlovniHodnoceni().equals("null")) {
					System.out.println("Hodnoceni cislo "+pocitadlo+".:");
					System.out.println(" - Pocet bodu: "+hodnoceni.getBodoveHodnoceni());
					System.out.println("");
				}else {
					System.out.println("Hodnoceni cislo "+pocitadlo+".:");
					System.out.println(" - Pocet bodu: "+hodnoceni.getBodoveHodnoceni());
					System.out.println(" - slovni hodnoceni filmu: "+hodnoceni.getSlovniHodnoceni());
					System.out.println("");
				}
				
			}
		}
		if(pocitadlo == 0)
			System.out.println("Hodnoceni filmu nenalezeno!");
	}
	
	public int pocetHodnoceniFilmu(String film) {
		String nactenyFilm;
		int pocitadlo = 0;
		for (Hodnoceni hodnoceni:seznamHodnoceni) {
			nactenyFilm = hodnoceni.getFilm();
			if(nactenyFilm.equals(film)) {
				pocitadlo = pocitadlo +1;
			}
		}
		return pocitadlo;
	}
	
	public String getHodnoceniFilmu(String film) {
		String nactenyFilm;
		String vysledek ="";
		Collections.sort(seznamHodnoceni);
		for (Hodnoceni hodnoceni:seznamHodnoceni) {
			nactenyFilm = hodnoceni.getFilm();
			if(nactenyFilm.equals(film)) {
				vysledek = vysledek+String.valueOf(hodnoceni.getBodoveHodnoceni())+"\n"+hodnoceni.getSlovniHodnoceni()+"\n";
			}
		}
		return vysledek;
	}

	public void removeHodnoceni(String film) {
		List<Hodnoceni> keSmazani = new ArrayList<>();
		for(Hodnoceni hodnoceni:seznamHodnoceni) {
			if(hodnoceni.getFilm().equals(film) == true) {
				keSmazani.add(hodnoceni);
			}
		}
		seznamHodnoceni.removeAll(keSmazani);
		
	}
	
	public void renameHodnoceni(String film, String novyNazev) {
		for(Hodnoceni hodnoceni:seznamHodnoceni) {
			if(hodnoceni.getFilm().equals(film) == true){
				hodnoceni.setFilm(novyNazev);
			}
		}
	}
	
	public void ulozeniDatabaze(SQLDatabaze sql) {
		String film;
		String typ;
		String slovniHodnoceni;
		int bodoveHodnoceni;
		
		for(Hodnoceni hodnoceni:seznamHodnoceni) {
			film = hodnoceni.getFilm();
			typ = hodnoceni.getTyp();
			slovniHodnoceni = hodnoceni.getSlovniHodnoceni();
			bodoveHodnoceni = hodnoceni.getBodoveHodnoceni();
			sql.vlozeniHodnoceni(film, typ, slovniHodnoceni, bodoveHodnoceni);
		}
	}
	
}
