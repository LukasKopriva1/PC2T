package projectv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DatabazeHercu {

	public List<Herec> seznamHercu;
	static final Scanner sc = new Scanner(System.in);
	
	DatabazeHercu(){
		seznamHercu = new ArrayList<>();
	}
		
	public void addHerce(String film, String jmenoHerce){
		if(existujeZaznam(film, jmenoHerce) == false)
			seznamHercu.add(new Herec(film, jmenoHerce));
		else
			System.out.println("Tento herec/animator jiz je prirazen k tomuto filmu!");
	}
	
	public void pridaniHerce(String film, String typFilmu) {
		String herec;
		int dalsi = 0;
		boolean run = true;
			if(typFilmu.equals("Anim"))
				System.out.println("Zadej jmeno animatora:");
			else
				System.out.println("Zadej jmeno herce:");
			herec = sc.nextLine();
			if(existujeZaznam(film, herec) == false)
				addHerce(film, herec);
			else
				System.out.println("Tento herec/animator jiz u tohoto filmu je zapsan!");
		while(run) {
			System.out.println("Pridat dalsiho");
			System.out.println("1...Ano");
			System.out.println("2...Ne");
			dalsi = sc.nextInt();
			herec = sc.nextLine();
			switch(dalsi) {
		
				case 1:
						if(typFilmu.equals("Anim"))
							System.out.println("Zadej jmeno animatora:");
						else
							System.out.println("Zadej jmeno herce:");
						herec = sc.nextLine();
						if(existujeZaznam(film, herec) == false)
							addHerce(film, herec);
						else
							System.out.println("Tento herec/animator jiz u tohoto filmu je zapsan!");
						run = true;
						break;
				case 2:
						run = false;
			}
		}
	}
	
	public void vypisHercu() {
		
		for(Herec herec:seznamHercu)
			System.out.println(herec.getFilm()+" "+herec.getJmeno());
	}
	
	public int pocetHercuFilmu(String film) {
		String nactenyHerec;
		int pocitadlo = 0;
		for (Herec herec:seznamHercu) {
			nactenyHerec = herec.getFilm();
			if(nactenyHerec.equals(film)) {
				pocitadlo = pocitadlo +1;
			}
		}
		return pocitadlo;
	}
	
	public String getHerciFilmu(String film) {
		String nactenyFilm;
		String vysledek ="";
		for (Herec herci:seznamHercu) {
			nactenyFilm = herci.getFilm();
			if(nactenyFilm.equals(film)) {
				vysledek = vysledek+" - "+String.valueOf(herci.getJmeno())+"\n";
			}
		}
		return vysledek;
	}
	
	public String getHerciFilmu1(String film) {
		String nactenyFilm;
		String vysledek ="";
		int pocitadlo = 0;
		for (Herec herci:seznamHercu) {
			nactenyFilm = herci.getFilm();
			if(nactenyFilm.equals(film)) {
				if(pocitadlo == 0) {
				vysledek = vysledek+String.valueOf(herci.getJmeno());
				pocitadlo ++;
				}else {
					vysledek = vysledek+", "+String.valueOf(herci.getJmeno());
				}
			}
		}
		return vysledek;
	}
	
	public String getHercikFilmu(String film) {
		String nactenyFilm;
		String vysledek ="";
		for (Herec herci:seznamHercu) {
			nactenyFilm = herci.getFilm();
			if(nactenyFilm.equals(film)) {
				vysledek = vysledek+String.valueOf(herci.getJmeno())+"\n";
			}
		}
		return vysledek;
	}
	
	public void removeAnimatora(String film) {
		List<Herec> keSmazani = new ArrayList<>();
		for(Herec herec:seznamHercu) {
			if(herec.getFilm().equals(film) == true) {
				keSmazani.add(herec);
			}
		}
		seznamHercu.removeAll(keSmazani);
		
	}
	
	public void removeAnimatorFilmu(String animator, String film) {
		List<Herec> keSmazani = new ArrayList<>();
		for(Herec herec:seznamHercu) {
			if((herec.getFilm().equals(film) && herec.getJmeno().equals(animator)) == true) {
				keSmazani.add(herec);
			}
		}
		seznamHercu.removeAll(keSmazani);
		
	}
	
	public boolean existujeZaznam(String film, String jmeno) {
		boolean vysledek = false;
		for(Herec herec:seznamHercu) {
			if((herec.getFilm().equals(film) && herec.getJmeno().equals(jmeno))  == true) {
				vysledek = true;
			}
			else
				vysledek = false;
		}
		return vysledek;
	}
	
	public void renameHerec(String film, String novyNazev){
		for(Herec herec:seznamHercu) {
			if(herec.getFilm().equals(film) == true) {
				herec.setFilm(novyNazev);
			}
		}
	}
	
	public String vypisFilmyHerce(String herec) {
		int pocitadlo = 0;
		String vysledek = "";
		for(Herec nactenyHerec:seznamHercu) {
			if(nactenyHerec.getJmeno().equals(herec) == true) {
				pocitadlo ++;
				vysledek = vysledek + " - "+nactenyHerec.getFilm()+"\n";
			}
		}
		if(pocitadlo == 0) {
			vysledek = "Herec nenalezen!";
		}
		return vysledek;
	}
	
	public void pocetFilmuVsechHercuAdmin() {
		Map<String, Integer>  herciPocet = new HashMap<String, Integer>(); 
		List<Herec> seznamHercuPracovni = new ArrayList<>();
		seznamHercuPracovni.addAll(seznamHercu);
		int pocitadlo = 0;
		int kolikrat = 0;
		String jmenoPrvniho = "";
		boolean run = true;
		while(run){
			List<Herec> keSmazani = new ArrayList<>();
			pocitadlo = 0;
			kolikrat = 0;
			for(Herec nactenyHerec:seznamHercuPracovni) {
				if(pocitadlo == 0) {
					jmenoPrvniho = nactenyHerec.getJmeno();
					System.out.println("Jmeno prvniho: "+jmenoPrvniho);
				}
				if(nactenyHerec.getJmeno().equals(jmenoPrvniho)) {
					keSmazani.add(nactenyHerec);
					kolikrat ++;
				}
				pocitadlo  ++;
			}
			System.out.println(jmenoPrvniho+" pocet "+kolikrat);
			seznamHercuPracovni.removeAll(keSmazani);
			if(kolikrat > 1) {
				herciPocet.put(jmenoPrvniho, kolikrat);
			}
			
			if(seznamHercuPracovni.size() == 0) {
				run = false;
			}
		}
	}
	
	public String pocetFilmuVsechHercu() {
		Map<String, Integer>  herciPocet = new HashMap<String, Integer>(); 
		List<Herec> seznamHercuPracovni = new ArrayList<>();
		seznamHercuPracovni.addAll(seznamHercu);
		int pocitadlo = 0;
		int kolikrat = 0;
		String jmenoPrvniho = "";
		String vysledek = "Zadny herec se neobjevuje vice nez 1 krat.";
		boolean run = true;
		
		while(run){
			List<Herec> keSmazani = new ArrayList<>();
			pocitadlo = 0;
			kolikrat = 0;
			for(Herec nactenyHerec:seznamHercuPracovni) {
				if(pocitadlo == 0) {
					jmenoPrvniho = nactenyHerec.getJmeno();
				}
				if(nactenyHerec.getJmeno().equals(jmenoPrvniho)) {
					keSmazani.add(nactenyHerec);
					kolikrat ++;
				}
				pocitadlo  ++;
			}
			seznamHercuPracovni.removeAll(keSmazani);
			if(kolikrat > 1) {
				herciPocet.put(jmenoPrvniho, kolikrat);
			}
			
			if(seznamHercuPracovni.size() == 0) {
				run = false;
			}
		}
		
		if(herciPocet.size()> 0) {
			vysledek = "\n";
			Set<String> seznamHercu = herciPocet.keySet();
			for(String herec:seznamHercu) {
				vysledek = vysledek + herec + " hral/podilel se na: \n" + vypisFilmyHerce(herec);
			}
		}
		
		return vysledek;
	}
	
	public void ulozeniDatabaze(SQLDatabaze sql) {
		String film;
		String jmeno;
		
		for(Herec herec:seznamHercu) {
			film = herec.getFilm();
			jmeno = herec.getJmeno();
			sql.vlozeniHerce(film, jmeno);
		}
	}
	
	
}

