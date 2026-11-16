package modelesslib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class ModelessDialog extends BaseWindow {
    protected JDialog dialog;
    protected Frame owner;
    protected String title;

    public ModelessDialog(Frame owner, String title) {
        this.owner = owner;
        this.title = title;
    }

    public void createAndShow() {
        if (dialog != null && dialog.isDisplayable()) {
            showWindow();
            return;
        }

        dialog = new JDialog(owner, title, false);
        this.window = dialog;

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCloseDialog();
            }
        });

        initComponents(dialog);
        onInitDialog();

        dialog.pack();
        dialog.setResizable(false);

        DialogManager.getInstance().registerDialog(this);
        showWindow();
    }

    public void destroy() {
        if (dialog != null) {
            DialogManager.getInstance().unregisterDialog(this);
            dialog.dispose();
            dialog = null;
            window = null;
        }
    }

    protected abstract void initComponents(JDialog dlg);

   
    protected void onInitDialog() {}

    protected void onCloseDialog() {
        destroy();
    }
}