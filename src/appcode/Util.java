/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appcode;

import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author MyBook Z Series
 */
public class Util {
    public enum FontStyle {
        BOLD,
        BOLDER,
        NORMAL,
        THIN,
        MEDIUM
    }
    public static Font getFont(FontStyle fontStyle, float fontSize){
        try{
            InputStream is;
            switch (fontStyle){
                case BOLD:
                    is = Util.class.getResourceAsStream("/fonts/Poppins-Bold.ttf");
                    break;
                default:
                    is = Util.class.getResourceAsStream("/fonts/Poppins-Regular.ttf");
                    break;
            }
            Font poppinsFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
            return poppinsFont;
        }catch (Exception e){
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) fontSize);
        }
    }
}
