package WHM;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * imports the grenze font from file and includes a method to be able to acess the font. 
 */
public class FontLoader {
    private final Font grenze;

    /**
     * contructor to get the font when object created. 
     */
    public FontLoader(float fontSize) throws IOException, FontFormatException {
        InputStream fontStream =  getClass().getResourceAsStream("/Grenze-SemiBold.ttf");
        grenze = Font.createFont(
            Font.TRUETYPE_FONT,
            fontStream
        ).deriveFont(fontSize);
    }

    public Font getFont() {
        return grenze;
    }
}
