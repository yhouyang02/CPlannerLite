package ui.gui;

import javax.swing.*;
import java.awt.*;

// Represents all fonts used in this application
public class FontManager extends UIManager {

    public static final Font UI_FONT = new Font("Open Sans", Font.PLAIN, 22);
    public static final Font MESSAGE_FONT = new Font("Verdana", Font.PLAIN, 22);
    public static final Font CONTENT_FONT = new Font("Verdana", Font.PLAIN, 24);

    // EFFECTS: sets the fonts of this application
    public static void setFonts() {
        // Setting message fonts
        put("OptionPane.font", MESSAGE_FONT);
        put("OptionPane.messageFont", MESSAGE_FONT);
        put("Label.font", MESSAGE_FONT);
        put("CheckBox.font", MESSAGE_FONT);
        put("TextField.font", MESSAGE_FONT);
        put("RadioButton.font", MESSAGE_FONT);
        put("ComboBox.font", MESSAGE_FONT);

        // Setting UI fonts
        put("OptionPane.buttonFont", UI_FONT);
        put("MenuBar.font", UI_FONT);
        put("Menu.font", UI_FONT);
        put("MenuItem.font", UI_FONT);
        put("MenuItem.acceleratorFont", UI_FONT);

        // Setting content fonts
        put("TextArea.font", CONTENT_FONT);
    }

}
