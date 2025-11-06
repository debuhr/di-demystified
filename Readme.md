# Java-Rest & Demystifying Dependency Injection
The code in this repository is very unpolished and not production ready in any way.
It is however, IMHO, interesting. The purpose of this code is to demonstrate with minimal effort, that there is no magic
to dependency injection.

This was written for a presentation that I held with the goal of showing that the form of dependency injection that is
seen in frameworks like Spring Boot is not magic, even if it may look like it.
To do this, I used an example of a simple HTTP API written in pure Java and gradually added dependency injection to it
in many small steps.

You can take a look at the individual commits and the tests that are added in each commit to get a good idea what each 
one adds/changes.

This was written without any prior research into the correct way to implement dependency injection and I left errors and
dead ends I reached in the commit history. I also accepted/kept "dirty" solutions in a few places e.g. recursive 
scanning of packages for components. Implementing a clean way of doing dependency injection in Java was *not* the point
here.

The strategy of the presentation was to first show the end result, that could, at a glance, be mistaken for spring boot
and then show step by step how to arrive at the end result with plain java.
Each commit takes a small step from tight coupling and manually creating instances in the place they are used towards
annotation based dependency injection.

# Architecture
The DI framework lives in the `di` folder.

The application is in the `application` folder and is divided into a `domain` directory that serves to demonstrate that 
the DI framework can be used to apply dependency inversion, and an `adapter` package containing the parts external to 
the domain in the sense of the *ports and adapters* architecture.

The `Application` class is the main class of the application and is used to run it (i.e. it contains the `main`-method).

The `Router` class creates the routes in the HttpContext when it is created.

The `AppConfiguration` class is a configuration class that can be used to create beans that cannot be created by using 
annotations, e.g. an instance of a class from a dependency that we cannot change. In this case, it creates an instance
of `HttpServer`.
