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

public class DatabazeHra extends Databaze{

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	private Map<String,Hrany>  prvkyDatabaze;
	
	DatabazeHra(){
		prvkyDatabaze=new HashMap<String,Hrany>();
	}	

	public boolean setHrany(String jmeno, int rokVydani, String reziser) {
		if (prvkyDatabaze.put(jmeno, new Hrany(jmeno, rokVydani, reziser)) == null)
			return true;
		else
			return false;
	}

	@Override
	public void vypis(DatabazeHercu seznamHercu) {
		Set<String> seznamFilmu = prvkyDatabaze.keySet();
		
		for(String hrany:seznamFilmu) {
			prvkyDatabaze.get(hrany).getInfoHodnoceni();
			System.out.println("Herci: ");
			System.out.println("");
			System.out.println(seznamHercu.getHerciFilmu1(prvkyDatabaze.get(hrany).getNazev()));
		}
	}
	
	public void vypisShodnocenim(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu) {
		String herci = seznamHercu.getHerciFilmu(film);
		prvkyDatabaze.get(film).getInfoHodnoceni();
		System.out.println("Herci:");
		System.out.println("");
		System.out.println(herci);
		seznamHodnoceni.vypisHodnoceniHrany(film);
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
	public void nactiTextak(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu) {
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
			int pocetHvezd;
			String slovniHodnoceni;
			String reziser;
			int rokVydani;
			int pocetHercu;
			String nactenyHerec;
			
			//nacteni typu
			nactenyTyp = in.readLine();
			nactenyNazev = in.readLine();
			rokVydani = Integer.valueOf(in.readLine());
			reziser = in.readLine();
			pocetHodnoceni = Integer.valueOf(in.readLine());
			if(nactenyTyp.equals("Hrany")) {
					setHrany(nactenyNazev, rokVydani, reziser);
					for(int i = 0; i < pocetHodnoceni; i++) {
						pocetHvezd = Integer.valueOf(in.readLine());
						slovniHodnoceni = in.readLine();
						seznamHodnoceni.addHodnoceni(film, pocetHvezd, slovniHodnoceni, "Hrany");
					}
					pocetHercu = Integer.valueOf(in.readLine());
					for(int i = 0; i < pocetHercu; i++) {
						nactenyHerec = in.readLine();
						seznamHercu.addHerce(film, nactenyHerec);
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
		System.out.println("co chcete upravit:");
		System.out.println("1...Nazev");
		System.out.println("2...Rezisera");
		System.out.println("3...Rok vydani");
		System.out.println("4...seznam hercu");
		int volba = pouzeCelaCisla(sc);
		switch(volba) {
		case 1:
			boolean run = true;
			System.out.println("Zadejte novy nazev");
			String novyNazev = sc.nextLine();
			novyNazev = sc.nextLine();
			while(run){
				if((existujeZaznam(novyNazev) || seznamAnim.existujeZaznam(novyNazev)) == false) {
					Hrany zaznam = prvkyDatabaze.get(film);
					prvkyDatabaze.remove(film);
					prvkyDatabaze.put(novyNazev, zaznam);
					prvkyDatabaze.get(novyNazev).setNazev(novyNazev);
					seznamHodnoceni.renameHodnoceni(film, novyNazev);
					seznamHercu.renameHerec(film, novyNazev);
					System.out.println(ANSI_GREEN+"Film uspesne prejmenovan"+ANSI_RESET);
					run = false;
				}else {
					System.out.println(ANSI_RED+"Film s timto nazvem již existuje"+ANSI_RESET);
					System.out.println("Zadej novy nazev");
					novyNazev = sc.nextLine();
					run = true;
				}
			}
			break;
		
		case 2:
			System.out.println("Zadejte jmeno rezisera");
			String novyReziser = sc.nextLine();
			novyReziser = sc.nextLine();
			prvkyDatabaze.get(film).setReziser(novyReziser);
			System.out.println(ANSI_GREEN+"Reziser filmu uspesne zmenen"+ANSI_RESET);
			break;
		
		case 3:
			System.out.println("Zadejte rok vydani");
			int rokVydani = pouzeCelaCisla(sc);
			prvkyDatabaze.get(film).setRok(rokVydani);
			System.out.println(ANSI_GREEN+"Rok vydani filmu uspesne zmenen"+ANSI_RESET);
			break;
			
		case 4:
			String herec;
			System.out.println("Seznam aktualnich Hercu:");
			System.out.println(seznamHercu.getHerciFilmu(film));
			System.out.println("");
			System.out.println("Chcete");
			System.out.println("1...Odebrat herce");
			System.out.println("2...Pridat herce");
			int volba2 = pouzeCelaCisla(sc);
			switch(volba2) {
			case 1:
				System.out.println("");
				System.out.println("Koho chcete odebrat");
				herec = sc.nextLine();
				herec = sc.nextLine();
				seznamHercu.removeAnimatorFilmu(herec, film);
				System.out.println(ANSI_GREEN+"Herec odebran!"+ANSI_RESET);
				break;
			case 2:
				seznamHercu.pridaniHerce(film, "Hrany");
				break;
			}
			
			boolean runHerec = true;
			while(runHerec) {
				System.out.println("");
				System.out.println("Seznam aktualnich hercu:");
				System.out.println(seznamHercu.getHerciFilmu(film));
				System.out.println("Chcete");
				System.out.println("1...Odebrat herce");
				System.out.println("2...Pridat herce");
				System.out.println("3...Uz nic");
				volba2 = pouzeCelaCisla(sc);
				switch(volba2) {
				case 1:
					System.out.println("");
					System.out.println("Koho chcete odebrat");
					herec = sc.nextLine();
					herec = sc.nextLine();
					seznamHercu.removeAnimatorFilmu(herec, film);
					System.out.println(ANSI_GREEN+"Herec odebran!"+ANSI_RESET);
					break;
					
				case 2:
					seznamHercu.pridaniHerce(film, "Hrany");
					System.out.println(ANSI_GREEN+"Herec pridan"+ANSI_RESET);
					break;
				
				case 3:
					runHerec = false;
					break;
				
				default:
					System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
					break;
				}
			}
			break;
			
		default:
			System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
			break;
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
		String typ = "Hrany";
		String reziser;
		int rokVydani;
		
		Set<String> seznamFilmu = prvkyDatabaze.keySet();
		for(String hrany:seznamFilmu) {
			nazev = prvkyDatabaze.get(hrany).getNazev();
			reziser = prvkyDatabaze.get(hrany).getReziser();
			rokVydani = prvkyDatabaze.get(hrany).getRok();
			sql.vlozeniHrany(nazev, typ, reziser, rokVydani);
		}
	}
	
}
