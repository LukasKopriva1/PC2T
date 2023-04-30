package projectv2;

public class Herec {
	
	String film;
	String jmeno;
	
	Herec(String film, String jmeno){
		this.film = film;
		this.jmeno = jmeno;
	}
	
	public String getFilm() {
		return film;
	}
	
	public String getJmeno() {
		return jmeno;
	}
		
	public void setFilm(String novyNazev) {
		this.film = novyNazev;
	}
	
}
