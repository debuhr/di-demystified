package di

import di.configurationclass.TestConfiguration
import di.testbeans.BeanWithDeps
import di.testbeans.Dependency1
import di.testbeans.Dependency2
import di.testbeans.moretestbeans.BeanInSubpackage
import spock.lang.Specification

class BeanScannerTest extends Specification {
    BeanScanner beanScanner = new BeanScanner()

    def "All beans in the scanned package are found"() {
        when: "the package 'di.testbeans' is scanned"
        List<Class<?>> beans = beanScanner.scanPackage("di.testbeans")

        then: "all four beans are found"
        beans.size() == 4

        and: "the 3 beans in the toplevel package are found"
        beans.contains(Dependency1)
        beans.contains(Dependency2)
        beans.contains(BeanWithDeps)

        then: "the bean in a subpackage is found"
        beans.contains(BeanInSubpackage)
    }

    def "@Configuration-classes are found"() {
        when: "the package 'di.configurationclass' is scanned"
        List<Class<?>> beans = beanScanner.scanPackage("di.configurationclass")

        then: "@Configuration-class 'TestConfiguration' is found"
        beans.contains(TestConfiguration)
    }
}
