package scu.ood.project.CampusSmartCafe;

import javax.swing.*;

import scu.ood.project.CafeClasses.LoginScreen;

import java.awt.EventQueue;

/**
 * Main application class
 */
public class CampusSmartCafe {

    /**
     * Bootstrap app here
     * @param args
     */
    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            System.out.println(ex);
        } catch (IllegalAccessException ex) {
            System.out.println(ex);
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }

}
