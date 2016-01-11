package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import player.Account;

public class testAccount {
	
	private Account acc;
	
	@Before
	public void setUp() throws Exception{
		acc = new Account();
	}
	
	
	
	@Test
	// tester om vores account konstruktør opretter 30000 til balancen.
	public void test_getBalance() {
		assertEquals(30000, acc.getBalance());
	}
	@Test
	public void test_adjustBalance(){
		acc.adjustBalance(-5000);
		assertEquals(25000,acc.getBalance());
	}
	@Test
	public void test_BalanceMinus() {
		acc.adjustBalance(-35000);
		assertEquals(-5000, acc.getBalance());
	}
	@Test
	public void test_BalancePlus() {
		acc.adjustBalance(35000);
		assertEquals(65000, acc.getBalance());
	}

}
