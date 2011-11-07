package de.langmi.spring.batch.tutorials.helloworld.groovytests

import groovy.util.GroovyTestCase

/**
 * Tests for the {@link Example} class.
 */
class ExampleTest
extends GroovyTestCase
{
    void testShow() {
        new Example().show()
    }
}
