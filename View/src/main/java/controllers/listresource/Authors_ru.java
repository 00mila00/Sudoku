package controllers.listresource;

import java.util.ListResourceBundle;

public class Authors_ru extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        Object[][] res = {
                {"Title","Авторы"},
                {"Autor1","Milena Pawlak"},
                {"Autor2","Krzysztof Dwużnik"}
        };
        return res;
    }
}
