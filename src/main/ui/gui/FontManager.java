package ui.gui;

import javax.swing.*;
import java.awt.*;

// Represents all fonts used in this application
public abstract class FontManager extends UIManager {

    public static final Font UIFont = new Font("Open Sans", Font.PLAIN, 22);
    public static final Font messageFont = new Font("Verdana", Font.PLAIN, 22);
    public static final Font contentFont = new Font("Verdana", Font.PLAIN, 24);

    // EFFECTS: sets the fonts of this application
    public static void setFonts() {
        // Setting message fonts
        put("OptionPane.messageFont", FontManager.messageFont);

        // Setting UI fonts
        put("OptionPane.buttonFont", FontManager.UIFont);
        put("Button.font", FontManager.UIFont);
        put("MenuBar.font", FontManager.UIFont);
        put("Menu.font", FontManager.UIFont);
        put("MenuItem.acceleratorFont", FontManager.UIFont);
        put("MenuItem.font", FontManager.UIFont);

        // Setting content fonts
        put("Label.font", FontManager.contentFont);
        put("TextArea.font", FontManager.contentFont);
    }
}
