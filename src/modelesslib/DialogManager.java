package modelesslib;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogManager {
    private static DialogManager instance;
    private final List<ModelessDialog> activeDialogs = new ArrayList<>();

    private DialogManager() {}

    public static synchronized DialogManager getInstance() {
        if (instance == null) {
            instance = new DialogManager();
        }
        return instance;
    }

    public void registerDialog(ModelessDialog dlg) {
        if (dlg != null && !activeDialogs.contains(dlg)) {
            activeDialogs.add(dlg);
            Window owner = dlg.owner;
            if (owner != null) {
                int offset = (activeDialogs.size() - 1) * 40;
                int x = owner.getX() + owner.getWidth() + 10 + offset;
                int y = owner.getY() + offset;
                dlg.moveWindow(x, y);
            }
        }
    }

    public void unregisterDialog(ModelessDialog dlg) {
        activeDialogs.remove(dlg);
    }

    public int getActiveCount() {
        return activeDialogs.size();
    }

    public void closeAllDialogs() {
        List<ModelessDialog> copy = new ArrayList<>(activeDialogs);
        for (ModelessDialog dlg : copy) {
            dlg.destroy();
        }
    }
}