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
public class DeviceWindow {

    public int left, top, height, width;

    public DeviceWindow(int left, int top, int height, int width) throws Exception {
        this.left = left;
        this.top = top;
        if (width <= 0) {
            throw new Exception("Negative Width");
        }
        this.width = width;
        if (height <= 0) {
            throw new Exception("Negative Height");
        }
        this.height = height;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
