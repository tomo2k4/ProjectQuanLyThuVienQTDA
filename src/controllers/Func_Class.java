
package controllers;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ADMIN
 */
public class Func_Class {

    public void displayImage(int width, int height, String imagePath,JLabel label) {
        // lay anh
        ImageIcon imgIco = new ImageIcon(getClass().getResource(imagePath));
        // lam anh vua voi jlabel
        Image image = imgIco.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH);
        // dat hinh anh vao jlabel
        label.setIcon(new ImageIcon(image));
    }
    
    
    
}
