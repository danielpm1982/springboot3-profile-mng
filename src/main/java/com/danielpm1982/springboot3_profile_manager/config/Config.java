package com.danielpm1982.springboot3_profile_manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Primary
    @Bean("objectMapper")
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean("xmlMapper")
    public ObjectMapper xmlMapper(){
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper;
    }
}

/*
At this @Configuration class, some beans are created in order to be injected, for instance, at PersonController and at PersonMapper.
When we create a class ourselves, it is possible to use @Component (and derived Annotations) at those classes' declarations, to create
a bean from them. In this case, it is also possible to create beans at @Configuration classes using @Bean (as here) or through xml
configuration files.
When we import classes from already compiled jars, for instance, from ModelMapper and ObjectMapper .jar dependencies at the pom.xml,
it is NOT possible to use @Component, as we do NOT have the source classes (.java), only the jars with classes already compiled (.class).
In such cases, if we want to create beans from those classes, we gotta use either @Configuration classes with @Bean Annotations, as here,
or xml configuration files.
In the current project, the classes we created and declared ourselves, for instance, PersonMapper, we set Spring Context to create beans
from them by using @Component, but we could have created beans from them here, or at other @Configuration classes, by using @Bean. On the
other hand, the classes we imported already compiled from jar dependencies, we had to create beans from them at this @Configuration class,
by using @Bean, as we do not want to use xml configuration files and as their source classes are not part of this application.
So, here, at this @Configuration class, we create beans from some dependencies' compiled classes, using @Bean Annotation, in order to inject
them at any other class, as needed.
In the case of more than one @Bean of same Object type (for instance, the objectMapper and xmlMapper beans above - both of type ObjectMapper,
ambiguity Exception will be thrown at injection time, although there are 4 ways of resolving such injection ambiguity:
 - using @Qualifier, with the name of the actual bean to inject, for instance: @Qualifier("objectMapper") or @Qualifier("xmlMapper"), at the
 constructor where those beans are being injected (or at fields or at assessor methods at the class of injection);
 - naming the parameter variables, that are to be associated with the injected bean instances (arguments), with the same name of the beans
 at the ApplicationContext... the type of such variables tells what type of bean is going to be injected and, if there are multiple beans of
 that type, the name of the parameters - if equal to the name of the beans - qualify what particular bean of that type should be injected;
 - using @Primary to set what of the multiple beans of a single type should be chosen when ambiguity happens at injection;
 - at a class level (instead of at a method level as above), when using class-level @Component or derived annotations, it's also possible to
 use @Order or @Priority to set the ordering of creation and loading of the beans at the ApplicationContext as well as the order of injection
 when ambiguity happens (actually only @Priority will solve the injection ambiguity effectively). These two do not work for method level
 annotations as above. See example at the mapper folder classes.

 PS.: when multiple beans exist for a same Object type, and if other APIs use those same beans, altering the preference of the bean to be
 injected may cause issues at those APIs implementations, even if it would work in our own code. For example, ObjectMapper above is a bean
 type with multiple beans, used not only at the code we wrote ourselves here, but at the code of some dependencies we imported, as at Swagger
 dependency. If we set @Primary at the "objectMapper" bean declaration above, it does not affect Swagger jar compiled classes, but if we set
 @Primary at the "xmlMapper" bean above, it completely breaks Swagger loading of this profile-mng app, as it won't be able to load some of
 the resources of the current application with "xmlMapper" bean instance - it will then keep loading a default remote app instead. In other
 words, setting a particular bean as @primary, or altering the order through which beans of a same type should be selected for injection, may
 generate inconsistencies at dependencies we're using as imports to our Spring Boot application. The safer way to define what bean should be
 injected, when resolving bean injection ambiguities, is to use the @Qualifier at each parameter to be associated with each bean, setting
 exactly the name of the bean to be injected and only at that place of our application, without messing with injections at other places or at
 other applications (imported as dependencies). Or use the name of the parameter variables exactly the same of the name of the beans. Using
 @Qualifier contributes to better readability, though.
*/
