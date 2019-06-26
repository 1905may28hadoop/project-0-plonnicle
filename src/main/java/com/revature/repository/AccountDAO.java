package com.revature.repository;

import java.util.List;

import com.revature.model.Account;

public interface AccountDAO {
	//There are a few standard names for these types of methods
	//get, find (to get / find things from the DB)
	//save, update (to add new ones / update)
	//delete, remove
	//create (to add something new)
	Account getAccount(int id);
	
	Account getAccount(long accountNum);
	
	boolean createAccount(Account account);
	
	boolean updateAccount(Account account, double newBalance);
	
	List<Account> getAccounts();
	
}
