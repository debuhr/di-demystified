package di;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The ApplicationContext stores information about component class instances ("beans") globally and makes the objects
 * accessible.
 */
public class ApplicationContext {
    private static final Map<Class<?>, BeanInstance<?>> beans = new HashMap<>();

    // TODO (jdb): make it possible to query all beans of the same type

    public static void register(String name, Class<?> clazz, Object bean) {
        // TODO (jdb): make it possible to register several beans of the same class as long as the name is different
        if (beans.containsKey(clazz)) {
            throw new DuplicateBeanException("Bean with class:'" + clazz.getSimpleName() + "' and name: '" + name
                                             + "' already exists in application context");
        }
        beans.put(clazz, BeanInstance.of(name, bean));
    }

    public static <T> T findBean(Class<T> clazz) {
        return findBean(null, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(@Nullable String name, Class<T> clazz) {
        // TODO (jdb): use name to disambiguate if there is more than one bean of a type
        BeanInstance<T> beanInstance = (BeanInstance<T>) beans.get(clazz);
        if (beanInstance == null) {
            throw new BeanNotFoundException("Bean with class:'" + clazz.getSimpleName() + "' and name: '" + name
                                            + "' not found in application context");
        }
        return beanInstance.getBean();
    }

    public static void instantiateBean(String name, Class<?> clazz)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {

        // TODO (jdb): currently this always uses the first constructor - instead, check if one exists that can be
        //  called with the types we have beans for
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];

        Object[] beanArguments = Arrays.stream(constructor.getParameters())
                .map(ApplicationContext::extractActualType)
                .map(ApplicationContext::findBean)
                .toArray();

        Object bean = constructor.newInstance(beanArguments);
        ApplicationContext.register(name, clazz, bean);
    }

    private static Class<?> extractActualType(Parameter parameter) {
        // TODO (jdb): is there a better way to get the class from a type (or the other way around and store the type
        //  in the map instead of the class) than Class.forName()?
        try {
            Type type = parameter.getParameterizedType();
            if (type instanceof ParameterizedType parameterizedType) {
                // TODO (jdb): make this work for more complicated generic types, now it always uses the first type
                return Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
            }

            return Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
