import spock.lang.Specification

class ApplicationContextTest extends Specification {
    def "A bean can be registered in the application context"() {
        given: "a bean instance"
        Object bean = new Object()

        when: "the bean is registered in the application context"
        ApplicationContext.register("bean", bean)

        then: "the bean is registered in the application context"
        ApplicationContext.findBean("bean") == bean
    }

    def "An exception is thrown if a bean is not found"() {
        when: "a nonexistent bean is queried"
        ApplicationContext.findBean("there-is-no-bean-with-this-name")

        then: "a BeanNotFoundException is thrown"
        thrown(BeanNotFoundException)
    }

    def "An exception is thrown when a bean with the same name is already registered"() {
        given: "a duplicateBean instance"
        Object duplicateBean = new Object()

        when: "the duplicateBean is registered in the application context"
        ApplicationContext.register("duplicateBean", duplicateBean)
        ApplicationContext.register("duplicateBean", duplicateBean)

        then: "a DuplicateBeanException is thrown"
        thrown(DuplicateBeanException)
    }

}
