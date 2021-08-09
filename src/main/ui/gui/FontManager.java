package ui.gui;

import javax.swing.*;
import java.awt.*;

// Represents all fonts used in this application
public class FontManager extends UIManager {

    public static final Font UIFont = new Font("Open Sans", Font.PLAIN, 22);
    public static final Font messageFont = new Font("Verdana", Font.PLAIN, 22);
    public static final Font contentFont = new Font("Verdana", Font.PLAIN, 24);

    // EFFECTS: sets the fonts of this application
    public static void setFonts() {
        // Setting message fonts
        put("OptionPane.font", messageFont);
        put("OptionPane.messageFont", messageFont);
        put("Label.font", messageFont);
        put("CheckBox.font", messageFont);
        put("TextField.font", messageFont);
        put("RadioButton.font", messageFont);

        // Setting UI fonts
        put("OptionPane.buttonFont", UIFont);
        put("MenuBar.font", UIFont);
        put("Menu.font", UIFont);
        put("MenuItem.font", UIFont);
        put("MenuItem.acceleratorFont", UIFont);

        // Setting content fonts
        put("TextArea.font", contentFont);
    }
}
