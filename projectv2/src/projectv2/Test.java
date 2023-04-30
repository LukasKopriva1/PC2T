package projectv2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/*TODO 	
 * 		
 * 		2,5) upravit vypis vsech
 * 		3) metodu pro vyprazdneni bufferu :D
 * 		4) zbytek zadani
 * 		4,5) pridat do switchu default
 * 		pridat barvicky
 * 		5) ucesani kodu
 * 		7) predenali nextLine na nacteniNextLine
 */

public class Test {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	static final Scanner sc = new Scanner(System.in);
	
	public static String nacteniNextLine() {
		String text;
		text = sc.nextLine();
		text = sc.nextLine();
		return text;
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
			System.out.println("");
			System.out.println("zadejte prosim cele cislo:");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public static void pridaniFilmu(DatabazeAnim seznamAnimaku, DatabazeHra seznamHranych, DatabazeHercu seznamHercu) {
		
		String reziser;
		boolean run = true;
		
		while(run) {
			System.out.println("Vyberte o jaký druh se jedna:");
			System.out.println("1...Animak");
			System.out.println("2...Hrany");
			
			int volbaDruh = pouzeCelaCisla(sc);
			int rokVydani;
			
			switch(volbaDruh) {	
				case 1:			
					System.out.println("");
					System.out.println("Zadejte nazev animaku");
					String nazev = nacteniNextLine();
					if((seznamAnimaku.existujeZaznam(nazev) || seznamHranych.existujeZaznam(nazev)) == false) {
						System.out.println("Zadejte rok vydani");
						rokVydani = pouzeCelaCisla(sc);
						System.out.println("Zadejte doporuceny vek divaka");
						int doporucenyVek = pouzeCelaCisla(sc);
						System.out.println("Zadejte jmeno rezisera");
						reziser = nacteniNextLine();
						if(!seznamAnimaku.setAnim(nazev, doporucenyVek, rokVydani, reziser)) {
							System.out.println("------------------------------");
							System.out.println(ANSI_RED+"Chyba pri ukladani filmu!"+ANSI_RESET);
							System.out.println("------------------------------");
						}else {
							seznamHercu.pridaniHerce(nazev, "Anim");
							System.out.println("------------------------------");
							System.out.println(ANSI_GREEN+"Film uspesne pridan!"+ANSI_RESET);
							System.out.println("------------------------------");
						}
					}else {
						System.out.println("------------------------------");
						System.out.println(ANSI_RED+"Tento film je jiz v databazi!"+ANSI_RESET);
						System.out.println("------------------------------");
					}
					run = false;
					break;
			 
				case 2:
					System.out.println("");
					System.out.println("Zadejte nazev filmu");
					nazev = nacteniNextLine();
					if((seznamHranych.existujeZaznam(nazev) || seznamAnimaku.existujeZaznam(nazev)) == false) {
						System.out.println("Zadejte rok vydani");
						rokVydani = pouzeCelaCisla(sc);
						System.out.println("Zadejte jmeno rezisera");
						reziser = nacteniNextLine();
						if(!seznamHranych.setHrany(nazev, rokVydani, reziser)) {
							System.out.println("------------------------------");
							System.out.println(ANSI_RED+"Chyba pri ukladani filmu!"+ANSI_RESET);
							System.out.println("------------------------------");
						}else {
							seznamHercu.pridaniHerce(nazev, "Hrany");
							System.out.println("------------------------------");
							System.out.println(ANSI_GREEN+"Film uspesne pridan!"+ANSI_RESET);
							System.out.println("------------------------------");
						}
					}else {
						System.out.println("------------------------------");
						System.out.println(ANSI_RED+"Tento film je jiz v databazi!"+ANSI_RESET);
						System.out.println("------------------------------");
					}
					
					run = false;
					break;
			
				default:
						System.out.println("------------------------------");
						System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
						System.out.println("------------------------------");
						break;
			
		}
		
		
		
		}	
	}
	
	public static void nacteniZeSouboru(DatabazeAnim seznamAnimaku, DatabazeHra seznamHranych, String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu){
		
		FileReader fr = null;
		BufferedReader in = null;
		String vstupniSoubor = film+".txt";
		
		try {
			fr = new FileReader(vstupniSoubor);
			in = new BufferedReader(fr);
			String nactenyTyp;
			
			nactenyTyp = in.readLine();
			if(nactenyTyp.equals("Anim")) {
					seznamAnimaku.nactiTextak(film, seznamHodnoceni, seznamHercu);
				}else {
					seznamHranych.nactiTextak(film, seznamHodnoceni, seznamHercu);
							
						}
			fr.close();
			in.close();
		}catch(IOException e){
			System.out.println("------------------------------");
			System.out.println(ANSI_RED+"Chyba pri nacitani ze souboru!"+ANSI_RESET);
			System.out.println("------------------------------");
		}
	}
	
	public static boolean kontrolaExistence(DatabazeAnim seznamAnimaku, DatabazeHra seznamHranych, String film) {
		if((seznamAnimaku.existujeZaznam(film) || seznamHranych.existujeZaznam(film)) == true){
			return true;
		}else {
			return false;
		}
	}
	

	public static void main(String[] args) {
		String film;
		DatabazeAnim seznamAnimaku = new DatabazeAnim();
		DatabazeHra seznamHra = new DatabazeHra();
		DatabazeHodnoceni seznamHodnoceni = new DatabazeHodnoceni();
		DatabazeHercu seznamHercu = new DatabazeHercu();
		SQLDatabaze sql = new SQLDatabaze();
		
		sql.connect();
		//sql.dropTable("animovane");
		//sql.dropTable("hrane");
		sql.createTable();
		//sql.vlozeniAnimovany("Animovany", "Anim", "Reziser 1", 1990, 15);
		//sql.vlozeniHrany("Hrany", "Hrany", "Reziser 1", 1990, 15);
		//sql.vybratVsechno();
		System.out.println("Nacitani animaku...");
		sql.nacteniAnimovane(seznamAnimaku);
		System.out.println("Nacitani hranych...");
		sql.nacteniHrane(seznamHra);
		System.out.println("Nacitani hodnoceni...");
		sql.nacteniHodnoceni(seznamHodnoceni);
		System.out.println("Nacitani seznamu hercu");
		sql.nacteniHercu(seznamHercu);
		System.out.println(ANSI_GREEN+"Hotovo"+ANSI_RESET);
		sql.disconnect();
		
		
		boolean run=true;
		int volba;
		//seznamHodnoceni.addHodnoceni();
		//seznamHodnoceni.vypisHodnoceniAll();
		while(run) {
			//vypis moznosti
			System.out.println("");
			System.out.println("Vyberte moznost:");
			System.out.println("1...Pridani noveho filmu");
			System.out.println("2...Vypis cele databaze");
			System.out.println("3...Odebrani filmu");
			System.out.println("4...Upraveni filmu");
			System.out.println("5...Pridani hodnoceni");
			System.out.println("6...Vyhledani filmu");
			System.out.println("7...Animatori/Herci podilejicise se na vice nez jednom filmu");
			System.out.println("8...Hledani filmu podle herce/animatora");
			System.out.println("9...Ulozeni filmu do souboru");
			System.out.println("10...Nacteni filmu ze souboru");
			System.out.println("0...Ukoncit program");
			
			volba = pouzeCelaCisla(sc);
			switch(volba) {
			
			case 1:
				
					pridaniFilmu(seznamAnimaku, seznamHra, seznamHercu);
					
					break;
			case 2:

					System.out.println("--------------------------------------");
					System.out.println("");
					System.out.println("Seznam animovanych filmu:");
					seznamAnimaku.vypis(seznamHercu);
					System.out.println("----------------------------------");
					System.out.println("");
					System.out.println("Seznam hranych filmu:");
					seznamHra.vypis(seznamHercu);
					System.out.println("--------------------------------------");
					
					break;
			
			case 3:
					System.out.println("-------------------------");
					System.out.println("Jaky film chcete odebrat?");
					film = nacteniNextLine();
					if(kontrolaExistence(seznamAnimaku, seznamHra, film) == true) {
						if((seznamAnimaku.existujeZaznam(film))) {
							seznamAnimaku.smazZaznam(film);
							seznamHodnoceni.removeHodnoceni(film);
							seznamHercu.removeAnimatora(film);
						}else{
							seznamHra.smazZaznam(film);
							seznamHodnoceni.removeHodnoceni(film);
							seznamHercu.removeAnimatora(film);
						}
					System.out.println("");
					System.out.println(ANSI_GREEN+"Film odebran"+ANSI_RESET);
					System.out.println("------------");
					}else {
						System.out.println("");
						System.out.println(ANSI_RED+"film neexistuje"+ANSI_RESET);
						System.out.println("---------------");
					}

					break;
			
			case 4:
					System.out.println("-------------------------");
					System.out.println("Ktery film chcete upravit?");
					film = nacteniNextLine();
					if(kontrolaExistence(seznamAnimaku, seznamHra, film) == true) {
						if((seznamAnimaku.existujeZaznam(film)) == true) {
							seznamAnimaku.upravFilm(film, sc, seznamHodnoceni, seznamHercu, seznamAnimaku, seznamHra);
						}else
							seznamHra.upravFilm(film, sc, seznamHodnoceni, seznamHercu, seznamAnimaku, seznamHra);
					}else {
						System.out.println(ANSI_RED+"Film nenalezen!"+ANSI_RESET);
					}
					System.out.println("-------------------------");
					break;
					
			case 5:
					System.out.println("");
					System.out.println("-------------------------------------");
					System.out.println("Jakemu filmu chcete pridat hodnoceni?");
					film = nacteniNextLine();
					if(kontrolaExistence(seznamAnimaku, seznamHra, film) == true) { //kontrola existence filmu pred pridanim
						int pocetBodu = 0;
						String slovniHodnoceni;
						if((seznamAnimaku.existujeZaznam(film)) == true) { //hodnoceni pro animaky
							boolean validni = true; //kontrola validyty cisla 
							while(validni) {
								System.out.println("Zadejte pocet bodu 1-10");
								pocetBodu = pouzeCelaCisla(sc);
								if(((pocetBodu <= 10) && (pocetBodu >= 1)) == true) {
									validni = false; //spravne cislo
								}else {
									System.out.println(ANSI_RED+"Pocet bodu neni v zadanem rozsahu!"+ANSI_RESET);
									validni = true; //nespravne cislo
								}
							}
							boolean uspesny = true;
							while(uspesny) {
								System.out.println("Chcete zadat slovni hodnoceni? 1...ANO, 2...NE");
								int anoNE = pouzeCelaCisla(sc);
								switch(anoNE) {
									case 1:
										System.out.println("Zadejte slovni hodnoceni");
										slovniHodnoceni = nacteniNextLine();
										seznamHodnoceni.addHodnoceni(film, pocetBodu, slovniHodnoceni, "Anim");
										uspesny = false;
									break;
								
									case 2:
										slovniHodnoceni = "null";
										seznamHodnoceni.addHodnoceni(film,  pocetBodu, slovniHodnoceni, "Anim");
										uspesny = false;
									break;
									
									default:
										System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
										uspesny = true;
										break;
								}
							}

							System.out.println("");
							System.out.println(ANSI_GREEN+"Hodnoceni pridano."+ANSI_RESET);
							System.out.println("------------------");
							
						}else {
							 //hodnoceni pro hrane filmy
							boolean validni = true; //kontrola validyty cisla 
							while(validni) {
								System.out.println("Zadejte pocet hvezdicek 1-5");
								pocetBodu = pouzeCelaCisla(sc);
								if(((pocetBodu <= 5) && (pocetBodu >= 1)) == true) {
									validni = false; //spravne cislo
								}else {
									System.out.println(ANSI_RED+"Pocet hvezdicek neni v zadanem rozsahu!"+ANSI_RESET);
									validni = true; //nespravne cislo
								}
							}
							boolean uspesny = true;
							while(uspesny) {
								System.out.println("Chcete zadat slovni hodnoceni? 1...ANO, 2...NE");
								int anoNE = pouzeCelaCisla(sc);
								switch(anoNE) {
									case 1:
										System.out.println("Zadejte slovni hodnoceni");
										slovniHodnoceni = nacteniNextLine();
										seznamHodnoceni.addHodnoceni(film, pocetBodu, slovniHodnoceni, "Hrany");
										uspesny = false;
									break;
								
									case 2:
										slovniHodnoceni = "null";
										seznamHodnoceni.addHodnoceni(film,  pocetBodu, slovniHodnoceni, "Hrany");
										uspesny = false;
									break;
								
									default:
										System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
										uspesny = true;
									break;
								}
							}
							
								System.out.println("");
								System.out.println(ANSI_GREEN+"Hodnoceni pridano."+ANSI_RESET);
								System.out.println("------------------");
										
						}
						
								
					}else {
						System.out.println(ANSI_RED+"film neexistuje"+ANSI_RESET);
						System.out.println("---------------");
					}
					break;
					
			case 6:
					System.out.println("------------------------");
					System.out.println("Jaky film chcete najit?");
					film = nacteniNextLine();
					if(kontrolaExistence(seznamAnimaku, seznamHra, film) == true) {
						if(seznamAnimaku.existujeZaznam(film) == true) {
							seznamAnimaku.vypisShodnocenim(film, seznamHodnoceni, seznamHercu);
							//seznamHodnoceni.vypisHodnoceniAnim(film);
						}else {
							seznamHra.vypisShodnocenim(film, seznamHodnoceni, seznamHercu);
							//seznamHodnoceni.vypisHodnoceniHrany(film);
						}
					}else {
						System.out.println(ANSI_RED+"Film nenalezen!"+ANSI_RESET);
						System.out.println("---------------");
					}
					break;
			
			case 7:
					System.out.println(seznamHercu.pocetFilmuVsechHercu());
					break;
					
			case 8:
					System.out.println("------------------------------------------------------------------------------------------------");
					System.out.println("Pro jakeho herce/animatora chceste najit seznam filmu, ve kterych hral, nebo se na nich podilel?");
					String herec = nacteniNextLine();
					System.out.println("");
					System.out.println("Filmy na kterych se podilel/hral v nich herec "+herec+":");
					System.out.println(seznamHercu.vypisFilmyHerce(herec));
					break;
					
			case 9:
					System.out.println("--------------------------------------");
					System.out.println("Zadejte nazev filmu k ulozeni do souboru");
					film = nacteniNextLine();
					if((seznamAnimaku.existujeZaznam(film) || seznamHra.existujeZaznam(film)) == true) {
						if((seznamAnimaku.existujeZaznam(film))) {
							seznamAnimaku.ulozTextak(film, seznamHodnoceni, seznamHercu);
						}else{
							seznamHra.ulozTextak(film, seznamHodnoceni, seznamHercu);
						}
					}else {
						System.out.println(ANSI_RED+"film neexistuje"+ANSI_RESET);
						System.out.println("---------------");
					}
					
					break;
					
			case 10:
					System.out.println("-------------------");
					System.out.println("Zadejte nazev souboru k nacteni");
					String nazev = nacteniNextLine();
					if((seznamAnimaku.existujeZaznam(nazev) || seznamHra.existujeZaznam(nazev)) == true) {
						System.out.println(ANSI_RED+"Film jiz v databazi existuje!"+ANSI_RESET);
						System.out.println("-----------------------------");
					}else
						nacteniZeSouboru(seznamAnimaku, seznamHra, nazev, seznamHodnoceni, seznamHercu);
					break;
					
			case 0:
					run = false;
					sql.connect();
					sql.dropTable("animovane");
					sql.dropTable("hrane");
					sql.dropTable("hodnoceni");
					sql.dropTable("herci");
					sql.createTable();
					System.out.println("Ukladani animovanych filmu...");
					seznamAnimaku.ulozeniDatabaze(sql);
					System.out.println("Ukladani hranych filmu...");
					seznamHra.ulozeniDatabaze(sql);
					System.out.println("Ukladani hodnoceni filmu...");
					seznamHodnoceni.ulozeniDatabaze(sql);
					System.out.println("Ukladani hercu...");
					seznamHercu.ulozeniDatabaze(sql);
					sql.disconnect();
					System.out.println("Hotovo.");
					break;
			
			case 999:
					System.out.println("admistratorsky vypis");
					System.out.println("--------------------");
					System.out.println("Seznam hodnoceni:");
					seznamHodnoceni.vypisHodnoceniAll();
					System.out.println("--------------------------------------");
					System.out.println("");
					seznamAnimaku.vypis(seznamHercu);
					seznamHra.vypis(seznamHercu);
					System.out.println("Seznam hercu:");
					seznamHercu.vypisHercu();
					System.out.println("");
					System.out.println("Kolikrat se kdo objevuje:");
					System.out.println("");
					seznamHercu.pocetFilmuVsechHercuAdmin();
					break;
					
			default:
					System.out.println(ANSI_RED+"Neplatna volba!"+ANSI_RESET);
			}
		}
	}

}
