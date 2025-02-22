package di

import application.Router
import application.adapter.rest.HelloHandler
import com.sun.net.httpserver.HttpServer
import di.exception.BeanNotFoundException
import di.exception.DuplicateBeanException
import di.testbeans.BeanWithDeps
import di.testbeans.Dependency1
import di.testbeans.Dependency2
import spock.lang.Specification

class ApplicationContextTest extends Specification {
    class TheBean{}

    def setup() {
        ApplicationContext.clear()
    }

    def "A bean can be registered and found in the application context"() {
        given: "a bean instance"
        TheBean bean = new TheBean()

        when: "the bean is registered in the application context"
        ApplicationContext.register("theBean", TheBean, bean)

        then: "the bean can be found in the application context"
        ApplicationContext.findBean(TheBean) == bean
    }

    interface TheInterface {}
    class TheInterfaceBean implements TheInterface {}
    def "A bean can be found by its interface"() {
        given: "a bean instance"
        TheInterfaceBean theInterfaceBean = new TheInterfaceBean()

        when: "the bean is registered in the application context"
        ApplicationContext.register("theInterfaceBean", TheInterfaceBean, theInterfaceBean)

        then: "the bean can be found by its interface"
        ApplicationContext.findBean(TheInterface) == theInterfaceBean
    }

    def "An exception is thrown if a bean is not found"() {
        when: "a nonexistent bean is queried"
        ApplicationContext.findBean(Object)

        then: "a BeanNotFoundException is thrown"
        thrown(BeanNotFoundException)
    }

    def "An exception is thrown when a bean with the same name is already registered"() {
        given: "a duplicateBean instance"
        Object duplicateBean = new Object()

        when: "the duplicateBean is registered in the application context"
        ApplicationContext.register("duplicateBean", Object, duplicateBean)
        ApplicationContext.register("duplicateBean", Object, duplicateBean)

        then: "a DuplicateBeanException is thrown"
        thrown(DuplicateBeanException)
    }

    def "A bean with dependencies can be instantiated, when the dependencies are already instantiated"() {
        given: "dependencies already exist as bean instances"
        ApplicationContext.register("dependency1", Dependency1, new Dependency1())
        ApplicationContext.register("dependency2", Dependency2, new Dependency2())

        when: "the bean with dependencies is instantiated"
        ApplicationContext.instantiateBean("beanWithDeps", BeanWithDeps)

        then: "the application context contains the new bean"
        ApplicationContext.findBean(BeanWithDeps).class == BeanWithDeps
    }

    def "A bean with dependencies that are a list of implementors of an interface can be instantiated"() {
        given: "dependencies already exist as bean instances"
        ApplicationContext.register("HttpServer", HttpServer, HttpServer.create())
        ApplicationContext.register("helloHandler", HelloHandler, new HelloHandler())

        when: "the bean with dependencies is instantiated and a list of classes is found by their interface"
        ApplicationContext.instantiateBean("router", Router)

        then: "the application context contains the new bean"
        ApplicationContext.findBean(Router).class == Router
    }

    def "A bean *without* dependencies can be instantiated"() {
        when: "the bean is registered in the application context"
        ApplicationContext.instantiateBean("object", Object)

        then: "the bean can be found in the application context"
        ApplicationContext.findBean(Object) instanceof Object
    }

    def "A bean with dependencies should be instantiated but the dependencies do not exist"() {
        when: "the bean with dependencies is instantiated"
        ApplicationContext.instantiateBean("beanWithDeps", BeanWithDeps)

        then: "a BeanNotFound is thrown"
        thrown(BeanNotFoundException)
    }

    def "Beans marked by annotation '@Component' are instantiated when the package containing them is scanned"() {
        when: "the package 'di.testbeans' is scanned"
        ApplicationContext.scanPackage("di.testbeans")

        then: "the beans found in the package are instantiated"
        ApplicationContext.findBean(Dependency1) != null
        ApplicationContext.findBean(Dependency2) != null
        ApplicationContext.findBean(BeanWithDeps) != null
    }

    def "Beans defined in @Configuration-class are instantiated"() {
        when: "the package 'di.configurationclass' is scanned"
        ApplicationContext.scanPackage("di.configurationclass")

        then: "the bean Dependency1 is instantiated"
        ApplicationContext.findBean(Dependency1) != null
    }

}
