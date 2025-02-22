import adapter.http.ResponseSender
import spock.lang.Specification

class ApplicationContextTest extends Specification {
    def "A bean can be registered in the application context"() {
        given: "a bean instance"
        ResponseSender responseSender = new ResponseSender()

        when: "the bean is registered in the application context"
        ApplicationContext.register("responseSender", responseSender)

        then: "the bean is registered in the application context"
        ApplicationContext.findBean("responseSender") == responseSender
    }

    def "An exception is thrown if a bean is not found"() {
        when: "a nonexistent bean is queried"
        ApplicationContext.findBean("there-is-no-bean-with-this-name")

        then: "a BeanNotFoundException is thrown"
        thrown(BeanNotFoundException)
    }

    // TODO (jdb): test for duplicate registration of beans with same name
    def "An exception is thrown when a bean with the same name is already registered"() {
        given: "a bean instance"
        Object bean = new Object()

        when: "the bean is registered in the application context"
        ApplicationContext.register("bean", bean)
        ApplicationContext.register("bean", bean)

        then: "a DuplicateBeanException is thrown"
        thrown(DuplicateBeanException)
    }

}
