package seng202.group2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeorgeTestClassTest {

	@Test
	void test() {
		GeorgeTestClass g = new GeorgeTestClass();
		String str = g.simpleCheck();
		assertEquals("Worky worky?", str);
	}

}
