/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapping;

import java.awt.Point;

/**
 *
 * @author DuongPTH
 */
public class RealToDeviceWindowMapping {

    RealWindow rWin;
    DeviceWindow dWin;
    double cw, ch, c1, c2;

    public RealToDeviceWindowMapping(RealWindow rWin, DeviceWindow dWin) {
        this.rWin = rWin;
        this.dWin = dWin;

        cw = dWin.width / rWin.width;
        ch = dWin.height / rWin.height;

        c1 = dWin.left - cw * rWin.minX;
        c2 = ch * rWin.minY + dWin.top + dWin.height;

        //Calculation of mapping a point
        //x2 = Cwx1  + left – CwminX
        //x2 = Cwx1  + C1
        //y2 = - Chy1 + ChminY + top + h
        //y2 = - Chy1 + C2
    }

    public Point map(RealPoint p) {
        int x2 = (int) (Math.round(cw * p.x + c1));
        int y2 = (int) (Math.round(-ch * p.y + c2));
        return new Point(x2, y2);
    }

    public DevicePointList map(RealPointList rList) {
        DevicePointList dList = new DevicePointList();
        for (RealPoint realPoint : rList) {
            dList.add(map(realPoint));
        }
        return dList;
    }

}
