import java.util.stream.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
    Uses reflection to find and collect children of the Field class.
*/
public class ShapesCollector {

    private static Class getClass(String name) {
        try {
            return Class.forName(name.substring(0, name.lastIndexOf(".")));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Set<Class> childrenOf(Class parent) {

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(".");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line))
            .filter(cls -> cls.getSuperclass() == parent)
            .collect(Collectors.toSet());
    }

    private static Set<Class> fieldClasses() {
        return childrenOf(Field.class);
    }

    public static Field makeAField(Class<?> fieldClass, int tilesCount, int minesCount) {
        try {
            Constructor ctor = fieldClass.getConstructor(int.class, int.class);
            
            return (Field) ctor.newInstance(tilesCount, minesCount);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getIconPath(Class<?> f) {
        try {
            return (String) f.getField("iconPath").get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<FieldclassAndIconPath> getFieldImplementations() {
        List<FieldclassAndIconPath> fis = new ArrayList<>();

        for (Class<?> fieldClass : fieldClasses()) {
            fis.add(new FieldclassAndIconPath(fieldClass, getIconPath(fieldClass)));
        }
        return fis;
    }
}

class FieldclassAndIconPath {
    public final Class<?> fieldClass;
    public final String iconPath;

    FieldclassAndIconPath(Class<?> fieldClass, String iconPath) {
        this.fieldClass = fieldClass;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return "(" + fieldClass + ", " + iconPath + ")";
    }
}
