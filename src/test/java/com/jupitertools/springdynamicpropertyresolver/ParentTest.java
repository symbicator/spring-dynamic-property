package com.jupitertools.springdynamicpropertyresolver;


import org.springframework.boot.test.util.TestPropertyValues;

public abstract class ParentTest {

	@DynamicTestProperty
	private static TestPropertyValues parentProperties(){
		return TestPropertyValues.of("first=001",
		                             "second=002");
	}
}
