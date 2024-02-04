package controleur;


public interface ModeleEcoutable {
    void ajoutEcouteur(EcouteurModele listener);
    void retraitEcouteur(EcouteurModele listener);
}
