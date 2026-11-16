package app;

import modelesslib.ModelessDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class StyleDialog extends ModelessDialog {
    private final ShapeState state;
    private final Runnable onStateChanged;

    private JRadioButton rbRed;
    private JRadioButton rbGreen;
    private JRadioButton rbBlue;
    private JCheckBox cbFill;

    public StyleDialog(Frame owner, ShapeState state, Runnable onStateChanged) {
        super(owner, "Палітра та стиль (Немодальний 1)");
        this.state = state;
        this.onStateChanged = onStateChanged;
    }

    @Override
    protected void initComponents(JDialog dlg) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorPanel.setBorder(new TitledBorder("Колір фігури"));

        rbRed = new JRadioButton("Червоний");
        rbGreen = new JRadioButton("Зелений");
        rbBlue = new JRadioButton("Синій");

        ButtonGroup group = new ButtonGroup();
        group.add(rbRed);
        group.add(rbGreen);
        group.add(rbBlue);

        rbRed.addActionListener(e -> {
            state.color = new Color(220, 50, 50);
            onStateChanged.run();
        });
        rbGreen.addActionListener(e -> {
            state.color = new Color(40, 170, 70);
            onStateChanged.run();
        });
        rbBlue.addActionListener(e -> {
            state.color = new Color(40, 100, 220);
            onStateChanged.run();
        });

        colorPanel.add(rbRed);
        colorPanel.add(rbGreen);
        colorPanel.add(rbBlue);

        cbFill = new JCheckBox("Заливка фігури кольором");
        cbFill.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbFill.addActionListener(e -> {
            state.isFilled = cbFill.isSelected();
            onStateChanged.run();
        });

        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizePanel.setBorder(new TitledBorder("Розмір об'єкта"));

        JButton btnDec = new JButton("Зменшити (-)");
        JButton btnInc = new JButton("Збільшити (+)");

        btnDec.addActionListener(e -> {
            if (state.radius > 20) state.radius -= 10;
            onStateChanged.run();
        });
        btnInc.addActionListener(e -> {
            if (state.radius < 150) state.radius += 10;
            onStateChanged.run();
        });

        sizePanel.add(btnDec);
        sizePanel.add(btnInc);

        JButton btnClose = new JButton("Закрити вікно");
        btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClose.addActionListener(e -> onCloseDialog());

        mainPanel.add(colorPanel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(cbFill);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(sizePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnClose);

        dlg.setContentPane(mainPanel);
    }

    @Override
    protected void onInitDialog() {
        if (state.color.equals(new Color(220, 50, 50))) rbRed.setSelected(true);
        else if (state.color.equals(new Color(40, 170, 70))) rbGreen.setSelected(true);
        else rbBlue.setSelected(true);

        cbFill.setSelected(state.isFilled);
    }
}