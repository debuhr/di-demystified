import java.util.HashMap;
import java.util.Map;

/**
 * The ApplicationContext stores information about component class instances ("beans") globally and makes the objects
 * accessible.
 */
public class ApplicationContext {
    private static Map<String, Object> beans = new HashMap<>();

    // TODO (jdb): what information to store? is the bean name enough or do we need the class as well?
    // TODO (jdb): automatically find the depencencies of a bean from the argument names in its constructor
    // TODO (jdb): make it possible to query all beans of the same type

    public static void register(String name, Object bean) {
        if (beans.containsKey(name)) {
            throw new DuplicateBeanException("Bean with name: '" + name + "' already exists in application context");
        }
        beans.put(name, bean);
    }

    public static Object findBean(String name) {
        // TODO (jdb): increase type safety
        Object bean = beans.get(name);
        if (bean == null) {
            throw new BeanNotFoundException("Bean with name: '" + name + "' not found in application context");
        }
        return bean;
    }
}
