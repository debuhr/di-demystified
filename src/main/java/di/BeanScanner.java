package di;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BeanScanner {
    List<Class<?>> scanPackage(String packageName) {
        return findClasses(packageName).stream()
                .filter(clazz -> clazz.getAnnotation(Component.class) != null)
                .toList();
    }

    public List<Class<?>> findClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            throw new RuntimeException("Package with name '" + packageName +"' not found!");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = reader.lines().toList();
        List<String> classNames = lines.stream()
                .filter(line -> line.endsWith(".class"))
                .toList();

        List<Class<?>> classesInSubpackages = lines.stream()
                .filter(line -> !line.contains("."))
                .flatMap(maybePackage -> this.findClasses(
                        packageName + "." + maybePackage).stream())
                .toList();

        List<Class<?>> classes = new ArrayList<>();
        for (String name : classNames) {
            try {
                Class<?> clazz = Class.forName(
                        packageName + "." + name.substring(0, name.lastIndexOf('.')));
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                // TODO (jdb): handle exception
                throw new RuntimeException(e);
            }
        }

        classes.addAll(classesInSubpackages);
        return classes;
    }
}
