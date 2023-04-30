package projectv2;


import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public abstract class Databaze {
	
	private Map<String,Film>  prvkyDatabaze;
	
	
	Databaze(){
		//prvkyDatabaze=new HashMap<String,Film>();
	}
		
	/*public boolean setFilm(String jmeno, String typ) {
		
		if (prvkyDatabaze.put(jmeno, new Anim(jmeno)) == null)
			return true;
		else
			return false;
		
	}
	*/
	public boolean existujeZaznam(String film) {
		return prvkyDatabaze.containsKey(film); 

	}
	public void vypis(DatabazeHercu seznamHercu) {
		Set<String> seznamFilmu = prvkyDatabaze.keySet();
		
		for(String film:seznamFilmu)
			prvkyDatabaze.get(film).getInfo();
	}
	
	public abstract void ulozTextak(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu);
	public abstract void nactiTextak(String film, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu);
	public abstract void smazZaznam(String film);
	public abstract void upravFilm(String film, Scanner sc, DatabazeHodnoceni seznamHodnoceni, DatabazeHercu seznamHercu, DatabazeAnim seznamAnimovanych, DatabazeHra seznamHranych);
}
