package model.piecePuzzle;

public class PointTetris {

    private int positionX;
    private int positionY;
    private boolean occupied;
    private int partOf;

    /*
     * initialisation du point
     */
    public PointTetris(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.occupied = false;
        this.partOf = -1;
    }

    /*
     * renvoie -1 si un tetris point ne fait pas partie d'une piece
     */
    public int getPartOf() {
        return this.partOf;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    // BP: Si le point est affecté, alors il devient occupé ! Il faut ajouter un this.setOccupied(true) ?
    /*
     * affecter un point a une piece
     */
    public void affectTo(int piece) {
        this.partOf = piece;
    }

    public void setOccupied(boolean b) {
        this.occupied = b;
    }

    /*
     * si le point est a true ou pas
     */
    public Boolean isOccupied() { // BP: Pourquoi un objet `Boolean` et pas simplement le type primitif `boolean`
        return this.occupied;
    }

    public void setPositionX(int x) {
        this.positionX = x;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointTetris) {
            return this.positionX == ((PointTetris) obj).positionX && this.positionY == ((PointTetris) obj).positionY;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.positionX + this.positionY;
    }

    @Override
    public String toString() {
        return this.isOccupied() ? "#" : "-";
    }
}