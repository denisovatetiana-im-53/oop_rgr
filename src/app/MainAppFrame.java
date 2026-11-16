package app;

import modelesslib.DialogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainAppFrame extends JFrame {
    private final ShapeState shapeState = new ShapeState();
    private final JPanel drawPanel;
    private final StyleDialog styleDialog;
    private final InfoDialog infoDialog;

    public MainAppFrame() {
        setTitle("РГР (Варіант 10) — Бібліотека класів та немодальні діалоги");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(120, 150);

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                g2.drawString("Клікніть у будь-якому місці вікна або відкрийте немодальні діалоги в меню.", 15, 22);
                g2.drawString("Активних немодальних діалогів: " + DialogManager.getInstance().getActiveCount(), 15, 42);

                int d = shapeState.radius * 2;
                int x = shapeState.centerX - shapeState.radius;
                int y = shapeState.centerY - shapeState.radius;

                if (shapeState.isFilled) {
                    g2.setColor(shapeState.color);
                    g2.fillOval(x, y, d, d);
                }
                g2.setColor(shapeState.color.darker());
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(x, y, d, d);
            }
        };
        drawPanel.setBackground(Color.WHITE);

        Runnable refreshUI = drawPanel::repaint;
        styleDialog = new StyleDialog(this, shapeState, refreshUI);
        infoDialog = new InfoDialog(this, shapeState, refreshUI);

        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                shapeState.centerX = e.getX();
                shapeState.centerY = e.getY();
                infoDialog.updateCoordinatesText();
                drawPanel.repaint();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menuDialogs = new JMenu("Вікна діалогу");

        JMenuItem itemStyle = new JMenuItem("Палітра та стиль (Немодальний 1)");
        JMenuItem itemInfo = new JMenuItem("Координати та рух (Немодальний 2)");
        JMenuItem itemOpenBoth = new JMenuItem("Відкрити обидва діалоги");
        JMenuItem itemCloseAll = new JMenuItem("Закрити всі немодальні вікна");
        JMenuItem itemExit = new JMenuItem("Вихід");

        itemStyle.addActionListener(e -> {
            styleDialog.createAndShow();
            drawPanel.repaint();
        });
        itemInfo.addActionListener(e -> {
            infoDialog.createAndShow();
            drawPanel.repaint();
        });
        itemOpenBoth.addActionListener(e -> {
            styleDialog.createAndShow();
            infoDialog.createAndShow();
            drawPanel.repaint();
        });
        itemCloseAll.addActionListener(e -> {
            DialogManager.getInstance().closeAllDialogs();
            drawPanel.repaint();
        });
        itemExit.addActionListener(e -> System.exit(0));

        menuDialogs.add(itemStyle);
        menuDialogs.add(itemInfo);
        menuDialogs.addSeparator();
        menuDialogs.add(itemOpenBoth);
        menuDialogs.add(itemCloseAll);
        menuDialogs.addSeparator();
        menuDialogs.add(itemExit);

        menuBar.add(menuDialogs);
        setJMenuBar(menuBar);
        add(drawPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppFrame().setVisible(true));
    }
}