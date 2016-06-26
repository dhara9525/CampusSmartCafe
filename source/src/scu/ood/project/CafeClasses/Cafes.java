package scu.ood.project.CafeClasses;

import javax.swing.*;
import java.awt.*;

/**
 * Cafe parent class
 */
public class Cafes extends JComponent{

    private int cafeId;
    String cafeName;
    private Point cafeLocation;

    /**
     * constructor
     * @param x_coordinate
     * @param y_coordinate
     */
    public Cafes(int x_coordinate, int y_coordinate) {
        super();
        setCafe_location(new Point(x_coordinate, y_coordinate));
        this.setBounds(getCafe_location().x, getCafe_location().y, 10, 10);
    }

    public Cafes(Point point){
        this(point.x, point.y);
    }

    /**
     * @return cafe id
     */
    public int getCafeID() {
        return this.cafeId;
    }

    /**
     * @param newCafeId
     */
    public void setCafeID(int newCafeId) {
        this.cafeId = newCafeId;
    }

    /**
     * @return cafe name
     */
    @Override
    public String toString() {
        return cafeName;
    }

    /**
     * @return cafe location
     */
    public Point getCafe_location() {
        return cafeLocation;
    }

    /**
     * @param cafeLocation
     */
    public void setCafe_location(Point cafeLocation) {
        this.cafeLocation = cafeLocation;
    }
}

