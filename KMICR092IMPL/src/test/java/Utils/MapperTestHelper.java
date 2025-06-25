package Utils;

import java.lang.reflect.Method;
import java.util.Date;

public class MapperTestHelper {

    public static String callGetString(Object input) {
        try {
            Method m = Mapper.class.getDeclaredMethod("getString", Object.class);
            m.setAccessible(true);
            return (String) m.invoke(null, input);
        } catch (Exception e) {
            return null;
        }
    }

    public static int callGetInt(Object input) {
        try {
            Method m = Mapper.class.getDeclaredMethod("getInt", Object.class);
            m.setAccessible(true);
            return (int) m.invoke(null, input);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double callGetDouble(Object input) {
        try {
            Method m = Mapper.class.getDeclaredMethod("getDouble", Object.class);
            m.setAccessible(true);
            return (double) m.invoke(null, input);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static Date callGetDate(Object input) {
        try {
            Method m = Mapper.class.getDeclaredMethod("getDate", Object.class);
            m.setAccessible(true);
            return (Date) m.invoke(null, input);
        } catch (Exception e) {
            return null;
        }
    }
}