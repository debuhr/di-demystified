package di;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The ApplicationContext stores information about component class instances ("beans") globally and makes the objects
 * accessible.
 */
public class ApplicationContext {
    private static final Map<Class<?>, BeanInstance<?>> beans = new HashMap<>();
    private static final BeanCreator beanCreator = new BeanCreator(ApplicationContext::findBeans);
    public static final BeanScanner beanScanner = new BeanScanner();

    public static void clear() {
        beans.clear();
    }

    public static void register(String name, Class<?> clazz, Object bean) {
        // TODO (jdb): make it possible to register several beans of the same class as long as the name is different
        if (beans.containsKey(clazz)) {
            throw new DuplicateBeanException("Bean with class:'" + clazz.getSimpleName() + "' and name: '" + name
                                             + "' already exists in application context");
        }
        beans.put(clazz, BeanInstance.of(name, bean));
    }

    public static <T> T findBean(Class<T> clazz) {
        List<T> beanList = findBeans(null, clazz);
        if (beanList.size() > 1) {
            throw new MultipleBeansException("Found multiple matching beans for class:'" + clazz.getSimpleName()
                                             + "', disambiguate by supplying a bean name!");
        }
        return beanList.getFirst();
    }

    public static <T> List<T> findBeans(Class<T> clazz) {
        return findBeans(null, clazz);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> findBeans(@Nullable String name, Class<T> clazz) {
        // TODO (jdb): still unsure if it is better to make the interface the "type of the bean" and use it as a key,
        //  sorting the concrete classes below
        if (clazz.isInterface()) {
            List<T> beanInstances = findBeansByInterface(clazz);
            if (beanInstances.isEmpty()) {
                throw new BeanNotFoundException("Bean with interface:'" + clazz.getSimpleName() + "' and name: '" + name
                                                + "' not found in application context");
            }
            return beanInstances;
        }

        // TODO (jdb): use name to disambiguate if there is more than one bean of a type
        BeanInstance<T> beanInstance = (BeanInstance<T>) beans.get(clazz);
        if (beanInstance == null) {
            throw new BeanNotFoundException("Bean with class:'" + clazz.getSimpleName() + "' and name: '" + name
                                            + "' not found in application context");
        }
        return List.of(beanInstance.getBean());
    }

    @SuppressWarnings("unchecked")
    private static <T> @NotNull List<T> findBeansByInterface(Class<T> clazz) {
        return (List<T>) beans.entrySet().stream()
                .filter((entry ->
                        Arrays.asList(entry.getKey().getInterfaces()).contains(clazz)))
                .map(Map.Entry::getValue)
                .map(BeanInstance::getBean)
                .toList();
    }

    public static void instantiateBean(String name, Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object bean = beanCreator.instantiateBean(clazz);
        ApplicationContext.register(name, clazz, bean);
    }

    public static void scanPackage(String packageName) {
        // TODO (jdb): CONTINUE HERE - use BeanScanner to find all beans, then sort them by order of dependency
        //  (bring them into an order in which they can be instantiated) and instantiate them

        // The first two approaches that I can think of to instantiate all beans with their dependencies and find out if
        // there are beans with missing dependencies are:
        // 1.
        //   - have a list of known beans
        //   - iterate through the list and instantiate all beans in the list whose dependencies exist as beans
        //   - remove instantiated beans from list or have a separate list of skipped beans
        //   - repeat, als long as the last pass reduced the number of skipped beans, i.e. actually created beans
        //     - otherwise fail and log offending beans and missing dependencies so the developer can fix them
        // 2.
        //   - have a list of known beans
        //   - start instantiating the beans from the beginning
        //   - if a bean has dependencies, instantiate those first
        //   - if a dependency has dependencies, instantiate those first, i.e. walk the tree
        //   - check for cyclic dependencies while doing this
        //   - for every new/next bean first check if it was already instantiated as a dependency

        List<Class<?>> foundBeans = beanScanner.scanPackage(packageName);
        int oldNumberOfBeans;

        do {
            oldNumberOfBeans = foundBeans.size();
            foundBeans = tryToCreateBeans(foundBeans);
        } while (oldNumberOfBeans > foundBeans.size());

        if (!foundBeans.isEmpty()) {
            foundBeans.forEach(foundBean -> {
                // TODO (jdb): add error logging that helps the developer, at least mention the missing dependency
                System.out.println("Could not instantiate: " + foundBean.getSimpleName());
            });
        }
    }

    private static @NotNull List<Class<?>> tryToCreateBeans(List<Class<?>> foundBeans) {
        List<Class<?>> skippedBeans = new ArrayList<>();
        foundBeans.forEach(foundBean -> {
            try {
                Object bean = beanCreator.instantiateBean(foundBean);
                register(null, foundBean, bean);
            } catch (BeanNotFoundException e) {
                // TODO (jdb): it's a bit dirty to use the exception this way, needs refactoring
                // this is normal, silence the exception
                skippedBeans.add(foundBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return skippedBeans;
    }

}
