package drawing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import javax.swing.JOptionPane;
import mapping.DevicePointList;
import mapping.DeviceWindow;

import mapping.RealPoint;
import mapping.RealPointList;
import mapping.RealToDeviceWindowMapping;
import mapping.RealWindow;

public class LineChartPanel extends javax.swing.JPanel {
    
    private RealPointList realPoints;
    private String xLabel = "x-axis";
    private String yLabel = "y-axis";
    private int chartPadding = 24;
    private final int axesPadding = 12;
    private final int labelPadding = 24;
    private int numberPadding = 24;

    public LineChartPanel() {
        initComponents();
    }

    public RealPointList getRealPoints() {
        return realPoints;
    }

    public void setRealPoints(RealPointList realPoints) {
        this.realPoints = realPoints;
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public int getChartPadding() {
        return chartPadding;
    }

    public void setChartPadding(int chartPadding) {
        this.chartPadding = chartPadding;
    }
    
    public void drawChart(RealPointList realPoints) {
        setRealPoints(realPoints);
        drawChart(getGraphics());
    }
    
    private void drawChart(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        
        /**
         * Resize chart
         */
        
        int padding = chartPadding*2 + axesPadding + labelPadding + numberPadding;
        
        Rectangle chartArea = new Rectangle(new Dimension(
                w - padding,
                h - padding
        ));
        
        double minX = Collections.<RealPoint>min(realPoints, (RealPoint lhs, RealPoint rhs) -> 
                Double.compare(lhs.x, rhs.x)
        ).x;
        
        double maxX = Collections.<RealPoint>max(realPoints, (RealPoint lhs, RealPoint rhs) -> 
                Double.compare(lhs.x, rhs.x)
        ).x;
        
        String strMinX = String.format("%.1f", minX);
        String strMaxX = String.format("%.1f", maxX);
        String strMaxLengthX = strMinX.length() > strMaxX.length() ? strMinX : strMaxX;
        
        double minY = Collections.<RealPoint>min(realPoints, (RealPoint lhs, RealPoint rhs) -> 
                Double.compare(lhs.y, rhs.y)
        ).y;
        
        double maxY = Collections.<RealPoint>max(realPoints, (RealPoint lhs, RealPoint rhs) -> 
                Double.compare(lhs.y, rhs.y)
        ).y;
        
        String strMinY = String.format("%.1f", minY);
        String strMaxY = String.format("%.1f", maxY);
        String strMaxLengthY = strMinY.length() > strMaxY.length() ? strMinY : strMaxY;
        
        String strMaxLength = strMaxLengthX.length() > strMaxLengthY.length() ? strMaxLengthX : strMaxLengthY;
        
        numberPadding = g.getFontMetrics(g.getFont()).stringWidth(strMaxLength);
        
        /**
         * Draw axes
         */
        
        int xyOffset = labelPadding + axesPadding + numberPadding;
        int arrowW = 8;
        int arrowH = 12;
        
        // Draw y-axis
        g.drawLine(xyOffset, h - xyOffset, xyOffset, 0);
        
        // Draw y-axis's arrows
        g.drawLine(xyOffset, 0, xyOffset - arrowW/2, arrowH/2);
        g.drawLine(xyOffset, 0, xyOffset + arrowW/2, arrowH/2);
        
        // Draw x-axis
        g.drawLine(xyOffset, h - xyOffset, w, h - xyOffset);
        
        // Draw x-axis's arrows
        g.drawLine(w, h - xyOffset, w - arrowH/2, h - xyOffset + arrowW/2);
        g.drawLine(w, h - xyOffset, w - arrowH/2, h - xyOffset - arrowW/2);

        /**
         * Draw labels
         */
        
        int labelOffset = labelPadding/2;
        
        int xLabelW = g.getFontMetrics(g.getFont()).stringWidth(xLabel);
        int yLabelW = g.getFontMetrics(g.getFont()).stringWidth(yLabel);
        int labelH = g.getFontMetrics(g.getFont()).getHeight();
        
        int centerX = (labelOffset + w)/2;
        
        // Draw x-axis's label
        g.drawString(xLabel, centerX - xLabelW/2, h - labelOffset + labelH/2);
        
        int centerY = (labelOffset + h)/2;
        
        // Draw y-axis's label
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform orig = g2D.getTransform();
        g2D.rotate(-Math.PI/2);
        g2D.drawString(yLabel, labelH/2 - centerY - yLabelW/2, labelOffset);
        g2D.setTransform(orig);

        /**
         * Draw x-scales
         */
        
        int xyScaleH = g.getFontMetrics(g.getFont()).getHeight();
        
        int xScaleW = g.getFontMetrics(g.getFont()).stringWidth(strMaxLengthX);
       
//        int xScaleN = chartArea.width/(xScaleW + xScaleW/2);
        int xScaleN = chartArea.width/(2*xyScaleH);
        
        int xScaleOffset = labelPadding + axesPadding + numberPadding + chartPadding;
        
        for (int i = 0; i <= xScaleN; ++i) {
            double frac = ((double)i/xScaleN);
            
            double xi = frac*(maxX - minX) + minX;
            
            String strXi = String.format("%.1f", xi);
            
            int xiW = g.getFontMetrics(g.getFont()).stringWidth(strXi);
            
            int x = (int) (frac*chartArea.getWidth());
            
            g.drawLine(xScaleOffset + x, h - xyOffset, xScaleOffset + x, h - xyOffset + 8);
//            g.drawString(strXi, xScaleOffset + x - xiW/2, h - labelPadding - xyScaleH/2);
            orig = g2D.getTransform();
            g2D.rotate(-Math.PI/2);
            g.drawString(strXi, -(h - labelPadding - xyScaleH/2), xScaleOffset + x);
            g2D.setTransform(orig);
        }
        
        /**
         * Draw y-scales
         */
        
        int yScaleN = chartArea.height/(2*xyScaleH);
        
        int yScaleOffset = labelPadding + numberPadding/2;
        
        for (int i = 0; i <= yScaleN; ++i) {
            double frac = (double)i/yScaleN;
            
            double yi = frac*(maxY - minY) + minY;
            
            String strYi = String.format("%.1f", yi);
            
            int yiW = g.getFontMetrics(g.getFont()).stringWidth(strYi);
            
            int y = (int) (frac*chartArea.getHeight());
            
            g.drawLine(xyOffset, h - y - xScaleOffset, xyOffset - 8, h - y - xScaleOffset);
            g.drawString(strYi, yScaleOffset - yiW/2, h - y - xScaleOffset + xyScaleH/2);
        }
        
        System.out.println("Tada");
        
        /**
        * Map and draw lines
        */
        RealToDeviceWindowMapping mapper = null;
        try {
            mapper = new RealToDeviceWindowMapping(
                    new RealWindow(minX, minY, Math.abs(maxX - minX), Math.abs(maxY - minY)),
                    new DeviceWindow(0, 0, (int)chartArea.getHeight(), (int)chartArea.getWidth())
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid dimensions.");
        }
        
        int xPadding = labelPadding + axesPadding + numberPadding + chartPadding;
        
        if (mapper != null) {
            DevicePointList devicePoints = mapper.map(realPoints);
            for (int i = 0, n = devicePoints.size() - 1; i < n; ++i) {
                g.drawLine(
                    devicePoints.get(i).x + xPadding, devicePoints.get(i).y + chartPadding,
                    devicePoints.get(i+1).x + xPadding, devicePoints.get(i+1).y + chartPadding
                );
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChart(g);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 739, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
