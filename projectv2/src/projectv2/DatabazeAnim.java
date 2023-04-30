package projectv2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DatabazeAnim extends Databaze {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	private Map<String,Anim>  prvkyDatabaze;
	
	DatabazeAnim(){
		prvkyDatabaze=new HashMap<String,Anim>();
	}
			
	public boolean setAnim(String jmeno, int doporucenyVek, int rokVydani, String reziser) {
		if (prvkyDatabaze.put(jmeno, new Anim(jmeno, doporucenyVek, rokVydani, reziser)) == null)
			return true;
		else
			return false;
	}

	@Override
	public void vypis(DatabazeHercu seznamHercu) {
		Set<String> seznamFilmu = prvkyDatabaze.keySet();
		
		for(String anim:seznamFilmu) {
			prvkyDatabaze.get(anim).getInfoHodnoceni();
			System.out.println("Animatori: ");
			System.out.println("");
			System.out.println(seznamHercu.getHerciFilmu1(prvkyDatabaze.get(anim).getNazev()));
		}
	}
	
	public void vypisShodnocenim(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamAnimatoru) {
		String herci = seznamAnimatoru.getHerciFilmu(film);
	
		prvkyDatabaze.get(film).getInfoHodnoceni();
		System.out.println("Animatori:");
		System.out.println("");
		System.out.println(herci);
		seznamHodnoceni.vypisHodnoceniAnim(film);
		System.out.println("----------------------------------");
		}
		
	
	@Override
	public boolean existujeZaznam(String film) {
		return prvkyDatabaze.containsKey(film); 

	}


	@Override
	public void ulozTextak(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu) {
		String textakNazev = film+".txt";
		File vystupniSoubor = new File(textakNazev);
		FileWriter fw = null;
		BufferedWriter out = null;
		int pocetHodnoceni = seznamHodnoceni.pocetHodnoceniFilmu(film);
		int pocetHercu = seznamHercu.pocetHercuFilmu(film);

		try {
		
		fw = new FileWriter(vystupniSoubor);
		out = new BufferedWriter(fw);
		out.write(new String(prvkyDatabaze.get(film).getTyp()+"\n"));
		out.write(new String(prvkyDatabaze.get(film).getNazev()+"\n"));
		out.write(new String(prvkyDatabaze.get(film).getRok()+"\n"));
		out.write(new String(prvkyDatabaze.get(film).getDoporucenyVek()+"\n"));
		out.write(new String(prvkyDatabaze.get(film).getReziser()+"\n"));
		out.write(new String(String.valueOf(pocetHodnoceni)+"\n"));
		if(pocetHodnoceni > 0) {
			out.write(new String(seznamHodnoceni.getHodnoceniFilmu(film)));
			 }
		out.write(new String(String.valueOf(pocetHercu)+"\n"));
		if(pocetHercu > 0) {
			out.write(new String(seznamHercu.getHercikFilmu(film)));
		}
		out.close();
		fw.close();
		System.out.println(ANSI_GREEN+"Film ulozen do souboru"+ANSI_RESET);
		System.out.println("----------------------");
		}catch (IOException e) {
			System.out.println(ANSI_RED+"Chyba pri ukladani do souboru"+ANSI_RESET); 
			System.out.println("----------------------------");

		}
	}


	@Override
	public void nactiTextak(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamAnimatoru) {
		FileReader fr = null;
		BufferedReader in = null;
		String vstupniSoubor = film+".txt";
		try {
			fr = new FileReader(vstupniSoubor);
			in = new BufferedReader(fr);
			
			//vytvoreni lokalnich promnenych
			String nactenyTyp;
			String nactenyNazev;
			int pocetHodnoceni;
			int pocetBodu;
			String slovniHodnoceni;
			int rokVydani;
			int doporucenyVek;
			String reziser;
			int pocetAnimatoru;
			String nactenyHerec;
			
			//nacteni typu
			nactenyTyp = in.readLine();
			nactenyNazev = in.readLine();
			rokVydani = Integer.valueOf(in.readLine());
			doporucenyVek = Integer.valueOf(in.readLine());
			reziser = in.readLine();
			pocetHodnoceni = Integer.valueOf(in.readLine());
			if(nactenyTyp.equals("Anim")) {
					setAnim(nactenyNazev, doporucenyVek, rokVydani, reziser);
					for(int i = 0; i < pocetHodnoceni; i++) {
						pocetBodu = Integer.valueOf(in.readLine());
						slovniHodnoceni = in.readLine();
						seznamHodnoceni.addHodnoceni(film, pocetBodu, slovniHodnoceni, "Anim");
					}
					pocetAnimatoru = Integer.valueOf(in.readLine());
					for(int i = 0; i < pocetAnimatoru; i++) {
						nactenyHerec = in.readLine();
						seznamAnimatoru.addHerce(film, nactenyHerec);
					}
					System.out.println(ANSI_GREEN+"Film uspesne ulozen."+ANSI_RESET);
					System.out.println("----------------------");
				}else {
					System.out.println(ANSI_RED+"Spatny typ Filmu/poskozeny soubor."+ANSI_RESET);
					System.out.println("----------------------");
							
						}
			
			in.close();
			fr.close();			
			
			}catch(IOException | NumberFormatException e){
			System.out.println(ANSI_RED+"Chyba pri nacitani ze souboru"+ANSI_RESET);
			System.out.println("----------------------");
		}
	}


	@Override
	public void smazZaznam(String film) {
		if(existujeZaznam(film)) {
			prvkyDatabaze.remove(film);
		}else {
			System.out.println(ANSI_RED+"film neexistuje"+ANSI_RESET);
			System.out.println("----------------------");
		}
		
		
	}


	@Override
	public void upravFilm(String film, Scanner sc, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu, DatabazeAnim seznamAnim, DatabazeHra seznamHra) {
		System.out.println("co chces upravit:");
		System.out.println("1...Nazev");
		System.out.println("2...Rezisera");
		System.out.println("3...Rok vydani");
		System.out.println("4...seznam animatoru");
		System.out.println("5...Doporuceny vek");
		int volba = pouzeCelaCisla(sc);
		switch(volba) {
		case 1:
			System.out.println("Zadej novy nazev");
			String novyNazev = sc.nextLine();
			novyNazev = sc.nextLine();
			boolean run = true;
			while(run) {
				if((existujeZaznam(novyNazev) || seznamHra.existujeZaznam(novyNazev)) == false) {
						Anim zaznam = prvkyDatabaze.get(film);
						prvkyDatabaze.remove(film);
						prvkyDatabaze.put(novyNazev, zaznam);
						prvkyDatabaze.get(novyNazev).setNazev(novyNazev);
						seznamHodnoceni.renameHodnoceni(film, novyNazev);
						seznamHercu.renameHerec(film, novyNazev);
						System.out.println(ANSI_GREEN+"Film uspesne prejmenovan"+ANSI_RESET);
						run = false;
				}else {
					System.out.println(ANSI_RED+"Film s timto nazvem již existuje!"+ANSI_RESET);
					System.out.println("Zadej novy nazev");
					novyNazev = sc.nextLine();
					run = true;
				}
			}
			break;
		
		case 2:
			System.out.println("Zadej jmeno rezisera");
			String novyReziser = sc.nextLine();
			novyReziser = sc.nextLine();
			prvkyDatabaze.get(film).setReziser(novyReziser);
			System.out.println(ANSI_GREEN+"Reziser filmu uspesne zmenen"+ANSI_RESET);
			break;
		
		case 3:
			System.out.println("Zadej rok vydani");
			int rokVydani = pouzeCelaCisla(sc);
			prvkyDatabaze.get(film).setRok(rokVydani);
			System.out.println(ANSI_GREEN+"Rok vydani filmu uspesne zmenen"+ANSI_RESET);
			break;
			
		case 4:
			String animator;
			System.out.println("Seznam aktualnich animatoru:");
			System.out.println(seznamHercu.getHerciFilmu(film));
			System.out.println("");
			System.out.println("Chcete");
			System.out.println("1...Odebrat animatora");
			System.out.println("2...Pridat animatora");
			int volba2 = pouzeCelaCisla(sc);
			switch(volba2) {
			case 1:
				System.out.println("");
				System.out.println("Koho chceze odebrat");
				animator = sc.nextLine();
				animator = sc.nextLine();
				seznamHercu.removeAnimatorFilmu(animator, film);
				System.out.println("Animator odebran!");
				break;
				
			case 2:
				seznamHercu.pridaniHerce(film, "Anim");
				break;
			}
			
			boolean runAnimator = true;
			while(runAnimator) {
				System.out.println("Seznam aktualnich animatoru:");
				System.out.println(seznamHercu.getHerciFilmu(film));
				System.out.println("Chcete");
				System.out.println("1...Odebrat animatora");
				System.out.println("2...Pridat animatora");
				System.out.println("3...Uz nic");
				volba2 = pouzeCelaCisla(sc);
				switch(volba2) {
				case 1:
					System.out.println("");
					System.out.println("Koho chceze odebrat");
					animator = sc.nextLine();
					animator = sc.nextLine();
					seznamHercu.removeAnimatorFilmu(animator, film);
					System.out.println(ANSI_GREEN+"Animator odebran!"+ANSI_RESET);
					break;
					
				case 2:
					seznamHercu.pridaniHerce(film, "Anim");
					System.out.println(ANSI_GREEN+"Animator pridan"+ANSI_RESET);
					break;
				
				case 3:
					runAnimator = false;
					break;
				
				default:
					System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
					break;
				}
			}
			
			break;
		
		case 5:
			System.out.println("Zadej doporuceny vek divaka");
			int doporucenyVek = pouzeCelaCisla(sc);
			prvkyDatabaze.get(film).setVek(doporucenyVek);
			break;
		
		default:
			System.out.println("Neplatna volba!");
		}
		 
	}
	
	public static int pouzeCelaCisla(Scanner sc){
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public void ulozeniDatabaze(SQLDatabaze sql) {
		String nazev;
		String typ = "Anim";
		String reziser;
		int rokVydani;
		int doporucenyVek;
		
		Set<String> seznamFilmu = prvkyDatabaze.keySet();
		for(String anim:seznamFilmu) {
			nazev = prvkyDatabaze.get(anim).getNazev();
			reziser = prvkyDatabaze.get(anim).getReziser();
			rokVydani = prvkyDatabaze.get(anim).getRok();
			doporucenyVek = prvkyDatabaze.get(anim).getDoporucenyVek();
			sql.vlozeniAnimovany(nazev, typ, reziser, rokVydani, doporucenyVek);
		}
	}
	
}
