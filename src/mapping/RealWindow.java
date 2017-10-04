/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapping;

/**
 *
 * @author DuongPTH
 */
public class RealWindow {

    double minX, minY, width, height;

    public RealWindow(double minX, double minY, double width, double height) throws Exception {
        this.minX = minX;
        this.minY = minY;
        if (width <= 0) {
            throw new Exception("Negative Width");
        }
        this.width = width;
        if (height <= 0) {
            throw new Exception("Negative Height");
        }
        this.height = height;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
