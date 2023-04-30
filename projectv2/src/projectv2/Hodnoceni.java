package projectv2;

public class Hodnoceni implements Comparable<Hodnoceni> {

	String film;
	String typFilmu;
	String slovniHodnoceni;
	int bodoveHodnoceni;
	
	Hodnoceni(String film, String slovniHodnoceni, int pocetBodu, String typFilmu){
		
		this.film = film;
		this.typFilmu = typFilmu;
		this.slovniHodnoceni = slovniHodnoceni;
		this.bodoveHodnoceni = pocetBodu;
		
	}
	
	public void setFilm(String nazev) {
		this.film = nazev;
	}
	
	public String getFilm() {
		return film;
	}
	
	public String getSlovniHodnoceni() {
		return slovniHodnoceni;
	}
	
	public int getBodoveHodnoceni() {
		return bodoveHodnoceni;
	}
	
	public String getTyp() {
		return typFilmu;
	}

	@Override
	public int compareTo(Hodnoceni o) {
		
		if(bodoveHodnoceni < o.getBodoveHodnoceni())
			return 1;
		if(bodoveHodnoceni > o.getBodoveHodnoceni())
			return -1;
		else
			return 0;
	}
}
