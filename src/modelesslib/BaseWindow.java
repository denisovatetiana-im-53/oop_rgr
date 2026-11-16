package modelesslib;

import java.awt.*;

public abstract class BaseWindow {
    protected Window window;

    public Window getWindow() {
        return window;
    }

    public boolean isCreated() {
        return window != null && window.isDisplayable();
    }

    public boolean isVisible() {
        return window != null && window.isVisible();
    }

    public void showWindow() {
        if (window != null) {
            window.setVisible(true);
            window.toFront();
        }
    }

    public void hideWindow() {
        if (window != null) {
            window.setVisible(false);
        }
    }

    public void moveWindow(int x, int y) {
        if (window != null) {
            window.setLocation(x, y);
        }
    }
}