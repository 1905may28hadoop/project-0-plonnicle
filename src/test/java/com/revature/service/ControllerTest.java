package com.revature.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.revature.model.Account;
import com.revature.repository.AccountDAO;
import com.revature.repository.AccountDAOImplOJDBC;


public class ControllerTest {

	private static Logger log = Logger.getLogger(ControllerTest.class);
	
	@Test
	public void balanceGreaterOrEqualZero(){
		log.debug("Test: Balance of first account is greater than or equal to zero.");
		AccountDAO accountsDAO = new AccountDAOImplOJDBC();
		List<Account> bankAccounts = accountsDAO.getAccounts();
		assertEquals(true, (bankAccounts.get(1).getBalance() >= 0));
	}
	@Test
	public void usernameNotBlank(){
		log.debug("Test: Username isn't blank.");
		AccountDAO accountsDAO = new AccountDAOImplOJDBC();
		List<Account> bankAccounts = accountsDAO.getAccounts();
		assertEquals(false, (bankAccounts.get(1).getUsername().equals("")));
	}
}
