package com.revature.foundationproject.util;

import com.revature.foundationproject.screens.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenRouter {
    private final List<Screen> appScreens;

    public ScreenRouter() {
        appScreens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        appScreens.add(screen);
    }

    public void navigate(String route) {
        for (Screen screen : appScreens) {
            if (screen.getRoute().equals(route)) {
                try {
                    screen.render();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
