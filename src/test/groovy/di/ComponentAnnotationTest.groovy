package di

import spock.lang.Specification

class ComponentAnnotationTest extends Specification {
    def "The @Component annotation is available at runtime"() {
        when: "the class Dependency1 is loaded"
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("di.testbeans.Dependency1")

        then: "the annotation @Component is available"
        Component componentAnnotation = clazz.getAnnotation(Component.class)
        componentAnnotation != null
    }
}
