package com.revature;

import com.revature.controller.Controller;

import com.revature.repository.AccountDAO;
import com.revature.repository.AccountDAOImplOJDBC;


/** 
 * Create an instance of your controller and launch your application.
 *
 * Try not to have any logic at all on this class.
 */
public class Main {

	public static void main(String[] args) {
		AccountDAO accountsDAO = new AccountDAOImplOJDBC();
		Controller controller = new Controller(accountsDAO);
		controller.menuRun();

//		System.out.println(accountsDAO.getAccounts());
//		System.out.println(accountsDAO.getAccount(1));
		
		

	}
}
