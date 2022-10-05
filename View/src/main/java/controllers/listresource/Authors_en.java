package controllers.listresource;

import java.util.ListResourceBundle;

public class Authors_en extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        Object[][] res = {
                {"Title","Authors"},
                {"Autor1","Milena Pawlak"},
                {"Autor2","Krzysztof Dwużnik"}
        };
        return res;
    }
}
