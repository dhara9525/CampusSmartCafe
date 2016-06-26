package scu.ood.project.CafeClasses;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Map Panel for campus screen
 */
public class CampusMap extends JPanel {

    public ArrayList<Cafe> cafes;
    public ArrayList<VendingMachine> vendingMachines;
    public int CAFE_RADIUS;
    String mapPath;
    private final ThreadLocal<BufferedImage> campusMap = new ThreadLocal<>();

    /**
     * Constructor
     *
     * @throws IOException
     */
    public CampusMap() throws IOException {
        super();

        campusMap.set(ImageIO.read(CampusMap.class.getResource("/map.png")));
        cafes = new ArrayList<>();
        vendingMachines = new ArrayList<>();
        CAFE_RADIUS = 10;
    }

    /**
     * @param cafe
     */
    public void insertCafe(Cafe cafe) {
        this.cafes.add(cafe);
    }

    /**
     * @param vendingMachine
     */
    public void insertVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachines.add(vendingMachine);
    }

    /**
     * @param graphics
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(campusMap.get(), 0, 0, 700, 500, null);

        if (null != cafes) {
            for (Cafe cafe : cafes) {
                graphics.setColor(Color.MAGENTA);
                graphics.fillRect(cafe.getCafe_location().x, cafe.getCafe_location().y, CAFE_RADIUS, CAFE_RADIUS);
            }
        }

        if (null != vendingMachines) {
            for (VendingMachine vendingMachine : vendingMachines) {
                graphics.setColor(Color.YELLOW);
                graphics.fillRect(vendingMachine.getCafe_location().x, vendingMachine.getCafe_location().y, CAFE_RADIUS, CAFE_RADIUS);
            }

        }

        super.paintChildren(graphics);
    }
}


