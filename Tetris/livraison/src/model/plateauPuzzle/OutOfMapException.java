package model.plateauPuzzle;
/*
 * Exception to handle piece collisions
 */

 // BP: En général, on aime faire un package avec toutes les exceptions
public class OutOfMapException extends Exception {
    // BP: Votre message est toujours "impossible place already taken", vous pouvez donc ne pas mettre de paramètre dans le constructeur et utiliser `super("impossible place already taken")`
	public OutOfMapException(String message) {
        super(message);
    }
}
