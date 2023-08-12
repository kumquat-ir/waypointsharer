package hellfall.waypointsharer.colorutils;

import java.awt.*;

public class XaeroColorConverter {

    private static final double[][] xaerosColorsLAB = { ColorUtility.getLab(new Color(0, 0, 0)),
        ColorUtility.getLab(new Color(0, 0, 128)), ColorUtility.getLab(new Color(0, 128, 0)),
        ColorUtility.getLab(new Color(0, 128, 128)), ColorUtility.getLab(new Color(128, 0, 0)),
        ColorUtility.getLab(new Color(128, 0, 128)), ColorUtility.getLab(new Color(128, 128, 0)),
        ColorUtility.getLab(new Color(192, 192, 192)), ColorUtility.getLab(new Color(128, 128, 128)),
        ColorUtility.getLab(new Color(0, 0, 255)), ColorUtility.getLab(new Color(0, 255, 0)),
        ColorUtility.getLab(new Color(0, 255, 255)), ColorUtility.getLab(new Color(255, 0, 0)),
        ColorUtility.getLab(new Color(255, 0, 255)), ColorUtility.getLab(new Color(255, 255, 0)),
        ColorUtility.getLab(new Color(255, 255, 255)), };

    private static final int[] xaerosColorsRGB = { new Color(0, 0, 0).getRGB(), new Color(0, 0, 128).getRGB(),
        new Color(0, 128, 0).getRGB(), new Color(0, 128, 128).getRGB(), new Color(128, 0, 0).getRGB(),
        new Color(128, 0, 128).getRGB(), new Color(128, 128, 0).getRGB(), new Color(192, 192, 192).getRGB(),
        new Color(128, 128, 128).getRGB(), new Color(0, 0, 255).getRGB(), new Color(0, 255, 0).getRGB(),
        new Color(0, 255, 255).getRGB(), new Color(255, 0, 0).getRGB(), new Color(255, 0, 255).getRGB(),
        new Color(255, 255, 0).getRGB(), new Color(255, 255, 255).getRGB(), };

    public static int rgbToXaeroColor(int rgb) {
        int red = clampColor(rgb >> 16 & 0xFF);
        int green = clampColor(rgb >> 8 & 0xFF);
        int blue = clampColor(rgb & 0xFF);

        Color wpc = new Color(red, green, blue);
        double[] labWPC = ColorUtility.getLab(wpc);
        int bestColorIndex = 0;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < xaerosColorsLAB.length; i++) {
            double[] c = xaerosColorsLAB[i];
            double diffLInner = Math.abs(c[0] - labWPC[0]);
            double diffAInner = Math.abs(c[1] - labWPC[1]);
            double diffBInner = Math.abs(c[2] - labWPC[2]);
            double distance = diffLInner * diffLInner + diffAInner * diffAInner + diffBInner * diffBInner;
            if (distance < closestDistance) {
                closestDistance = distance;
                bestColorIndex = i;
            }
        }

        return bestColorIndex;
    }

    public static int xaeroToRGBColor(int xaeros) {
        return xaerosColorsRGB[xaeros];
    }

    private static int clampColor(int color) {
        if (color < 32) {
            return 0;
        } else if (color < 128) {
            return 128;
        } else if (color < 192) {
            return 192;
        } else {
            return 255;
        }
    }
}
