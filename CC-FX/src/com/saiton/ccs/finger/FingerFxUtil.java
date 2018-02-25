package com.saiton.ccs.finger;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Utilities for JavaFX for Finger Print
 *
 * @author Saitonya
 */
public class FingerFxUtil {

    public static javafx.scene.image.Image createImage(java.awt.Image image)
            throws IOException {

        if (!(image instanceof RenderedImage)) {
            BufferedImage bufferedImage
                    = new BufferedImage(image.getWidth(null),
                            image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = bufferedImage;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image, "png", out);
        out.flush();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return new javafx.scene.image.Image(in, 250, 180, false, true);
    }
}
