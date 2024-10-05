package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.MenuPersistence;
import catering.persistence.ShiftPersistence;

public class CatERing {
    private static CatERing singleInstance;


    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private ShiftManager shiftMgr;

    private MenuPersistence menuPersistence;
    private ShiftPersistence shiftPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        shiftMgr = new ShiftManager();
        menuPersistence = new MenuPersistence();
        shiftPersistence = new ShiftPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        shiftMgr.addEventReceiver(shiftPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

}
