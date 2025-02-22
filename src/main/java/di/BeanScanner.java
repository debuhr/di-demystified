package di;

import di.annotation.Component;
import di.annotation.Configuration;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class BeanScanner {
    List<Class<?>> scanPackage(String packageName) {
        return findClasses(packageName).stream()
                .filter(clazz -> clazz.isAnnotationPresent(Component.class)
                                 || clazz.isAnnotationPresent(Configuration.class))
                .toList();
    }

    public List<Class<?>> findClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            throw new RuntimeException("Package with name '" + packageName + "' not found!");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = reader.lines().toList();

        List<Class<?>> classes = findClassesInPackage(packageName, lines);
        List<Class<?>> classesInSubpackages = findClassesInSubpackage(packageName, lines);

        classes.addAll(classesInSubpackages);
        return classes;
    }

    private static @NotNull List<Class<?>> findClassesInPackage(String packageName, List<String> lines) {
        return lines.stream()
                .filter(BeanScanner::isClassName)
                .map(name -> findClassByName(packageName, name))
                .collect(Collectors.toList());
    }

    private @NotNull List<Class<?>> findClassesInSubpackage(String packageName, List<String> lines) {
        return lines.stream()
                .filter(BeanScanner::isPackageName)
                .flatMap(maybePackage -> this.findClasses(
                        packageName + "." + maybePackage).stream())
                .toList();
    }

    private static boolean isClassName(String line) {
        return line.endsWith(".class");
    }

    private static boolean isPackageName(String line) {
        return !line.contains(".");
    }

    private static Class<?> findClassByName(String packageName, String name) {
        try {
            return Class.forName(packageName + "." + name.substring(0, name.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // TODO (jdb): handle exception
            throw new RuntimeException(e);
        }
    }
}
