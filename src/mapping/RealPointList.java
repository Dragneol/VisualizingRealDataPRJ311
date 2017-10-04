/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author DuongPTH
 */
public class RealPointList extends ArrayList<RealPoint> {

    public double maxX, maxY, minX, minY;

    public RealPointList() {
        super();
    }

    public RealPointList(double[] values) {
        super();
        for (int i = 0; i < values.length; i++) {
            this.add(new RealPoint(i, values[i]));
        }
        getBoundaries();
    }

    public RealPointList(Collection<RealPoint> realPoints) {
        super();
        Iterator it = realPoints.iterator();
        while (it.hasNext()) {
            this.add((RealPoint) it.next());
        }
        getBoundaries();
    }

    public void getBoundaries() {

        Collections.sort(this);

        minX = this.get(0).x;
        minX = this.get(this.size() - 1).x;

        minY = maxY = this.get(0).y;
        for (int i = 1; i < this.size(); i++) {
            RealPoint p = this.get(i);
            if (minY > p.y) {
                minY = p.y;
            }
            if (maxY < p.y) {
                maxY = p.y;
            }
        }
    }
}
