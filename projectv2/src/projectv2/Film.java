package projectv2;

public abstract class Film {

	//promnene tridy Film
			String nazev;
			String typ;
			String reziser;
			int rokVydani;
			
			
			//konstruktor
			Film() {
			}
			
			//getter
			public String getNazev(){
				return nazev;
			}
			
			
			public String getTyp() {
				return typ;
			}
			
			public void getInfo() {
				
				System.out.println(nazev);
				
			}
			
			public int getRok() {
				return rokVydani;
			}
			
			public String getReziser() {
				return reziser;
			}
			//settery
			
			public void setNazev(String nazev) {
				
				this.nazev = nazev;				
				
			}
			
			public void setReziser(String reziser) {
				
				this.reziser = reziser;
			}
			
			public void setRok(int rokVydani) {
				this.rokVydani = rokVydani;
			}
			

}
