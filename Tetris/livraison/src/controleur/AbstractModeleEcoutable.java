package controleur;


import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

	private List<EcouteurModele> listeners = new ArrayList<>();
	@Override
	public void ajoutEcouteur(EcouteurModele listener) {
	    listeners.add(listener);
	}
	@Override
	public void retraitEcouteur(EcouteurModele listener) {
	    listeners.remove(listener);
	}
	protected void fireChange() {
	    for (EcouteurModele listener : listeners) {
	        listener.modeleMisAJour();
	    }
	}	
}
