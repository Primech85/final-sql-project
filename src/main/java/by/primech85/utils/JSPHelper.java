package by.primech85.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JSPHelper {
    private final static String JSP_FORMAT = "/WEB-INF/%s.jsp";
    public String getPath(String jsp){
        return JSP_FORMAT.formatted(jsp);
    }
}
