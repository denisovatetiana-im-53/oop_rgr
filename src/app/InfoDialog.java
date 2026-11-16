package app;

import modelesslib.ModelessDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoDialog extends ModelessDialog {
    private final ShapeState state;
    private final Runnable onStateChanged;
    private JLabel lblCoords;

    public InfoDialog(Frame owner, ShapeState state, Runnable onStateChanged) {
        super(owner, "Координати та рух (Немодальний 2)");
        this.state = state;
        this.onStateChanged = onStateChanged;
    }

    @Override
    protected void initComponents(JDialog dlg) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(new TitledBorder("Стан у реальному часі"));
        lblCoords = new JLabel();
        infoPanel.add(lblCoords);

        JPanel movePanel = new JPanel(new GridLayout(2, 3, 5, 5));
        movePanel.setBorder(new TitledBorder("Керування положенням"));

        JButton btnUp = new JButton("Вгору");
        JButton btnLeft = new JButton("Вліво");
        JButton btnDown = new JButton("Вниз");
        JButton btnRight = new JButton("Вправо");

        final int step = 15;
        btnUp.addActionListener(e -> moveShape(0, -step));
        btnDown.addActionListener(e -> moveShape(0, step));
        btnLeft.addActionListener(e -> moveShape(-step, 0));
        btnRight.addActionListener(e -> moveShape(step, 0));

        movePanel.add(new JLabel());
        movePanel.add(btnUp);
        movePanel.add(new JLabel());
        movePanel.add(btnLeft);
        movePanel.add(btnDown);
        movePanel.add(btnRight);

        JButton btnClose = new JButton("Закрити вікно");
        btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClose.addActionListener(e -> onCloseDialog());

        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(movePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnClose);

        dlg.setContentPane(mainPanel);
    }

    private void moveShape(int dx, int dy) {
        state.centerX += dx;
        state.centerY += dy;
        updateCoordinatesText();
        onStateChanged.run();
    }

    public void updateCoordinatesText() {
        if (lblCoords != null) {
            lblCoords.setText(String.format("Координати центру: X = %d, Y = %d", state.centerX, state.centerY));
        }
    }

    @Override
    protected void onInitDialog() {
        updateCoordinatesText();
    }
}