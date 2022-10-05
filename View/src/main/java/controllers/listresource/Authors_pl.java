package controllers.listresource;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        Object[][] res = {
                {"Title","Autorzy"},
                {"Autor1","Milena Pawlak"},
                {"Autor2","Krzysztof Dwużnik"}
                };
        return res;
    }
}
