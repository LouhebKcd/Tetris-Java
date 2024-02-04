package model.piecePuzzle;

/*
 * Interface qui declare les méthodes utiles des formes
 */
public interface PiecePuzzle {
    // rotation horaire
    public void rightRotation();

    // rotation antihoraire
    public void leftRotation();

    // accesseurs + setters
    public int getHauteur();

    public int getLargeur();

    public PointTetris getCenter();

    public void updateCenter(PointTetris p);

    public Boolean[][] getShape();

    public String getColor();

    public void setColor(String c);
}
