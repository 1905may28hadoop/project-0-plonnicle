package com.revature.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.exception.LimitException;
import com.revature.model.Account;
import com.revature.repository.AccountDAO;
//import com.revature.repository.AccountDAOImplOJDBC;

public class Controller {
	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat af = new DecimalFormat("0000000000");
	LimitException limitException = new LimitException();
	List<Account> bankAccounts = new ArrayList<Account>();
	Account currentAccount = new Account();
	AccountDAO accountsDAO = null;
	int activeAccountLoc = 0;
	public Controller(AccountDAO accountsDAO){
		this.accountsDAO = accountsDAO;
		this.bankAccounts = accountsDAO.getAccounts(); //Loads all of the bank accounts into the object for local use.
	}
	
	Scanner input = new Scanner(System.in);
	boolean accountFound = false;
	String letterEntry = "";
	String usernameEntry = "";
	String passwordEntry = "";
	
	public void menuRun(){
		homeScreen();
	}
	
	// Home Screen
	
	public void homeScreen(){
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("==== OAK RIDGE NUCLEAR FEDERAL CREDIT UNION ====");
		System.out.println("====                                        ====");
		System.out.println("====              HOME PAGE                 ====");
		System.out.println("====                                        ====");
		System.out.println("====               Welcome!                 ====");
		System.out.println("====                                        ====");
		System.out.println("==== Returning customer? Y (or) N           ====");
		letterEntry = input.nextLine();
		if(letterEntry.equals("Y") || letterEntry.equals("y")){
			homeScreenUsername();
		}else if(letterEntry.equals("N") || letterEntry.equals("n")){
			homeScreenSignUp();
		}else{
			System.out.println("Invalid entry! Try again.");
		}
	}
	
	public void homeScreenSignUp(){
		String newAccountNum = homeScreenNewAccountNum();
		String newUsername = homeScreenNewUsername();
		String newPassword = homeScreenNewPassword();
		double newDeposit = homeScreenInitialDeposit();
		Account madeAccount = new Account(bankAccounts.size() + 1,
											newAccountNum, 
											newUsername, 
											newPassword, 
											newDeposit);
		currentAccount = madeAccount;
		accountsDAO.createAccount(currentAccount);
		bankAccounts.add(currentAccount);
		accountScreen(currentAccount);
	}
	
	public void homeScreenUsername(){
		System.out.println("==== Enter Account Username:                ====");
		usernameEntry = input.nextLine();
		for(int i = 0; i < bankAccounts.size(); i++){
			if(usernameEntry.equals(bankAccounts.get(i).getUsername())){
				activeAccountLoc = i;
				currentAccount = bankAccounts.get(i);
				homeScreenPassword();
				accountFound = true;
			}
		}
		if(!accountFound){
			System.out.println("Username not found! Try again.");
			homeScreen();
		}
		System.out.println("Have a good day!");
	}
	
	public void homeScreenPassword(){
		System.out.println("==== Enter Account Password:                ====");
		passwordEntry = input.nextLine();
		if(passwordEntry.equals(currentAccount.getPassword())){
			accountScreen(currentAccount);
		}
	}
	private String homeScreenNewUsername(){
		String username = "";
		String temp = "";
		System.out.println("==== Enter New Account Username:            ====");
		temp = input.nextLine();
		if(temp.equals("") || temp.length() == 0){
			System.out.println("No username entered! Try again.");
			homeScreenNewUsername();
		}else{
			username = temp;
		}
		return username;
	}
	
	public String homeScreenNewAccountNum(){
		double tempNum = 0;
		String accountNum;
		tempNum = bankAccounts.size() + 1;
		
		accountNum = af.format(tempNum); //Integer.toString((bankAccounts.size() + 1));
		//Add formatting for 10 digit places
		return accountNum;
	}
	
	public String homeScreenNewPassword(){
		String password = "";
		System.out.println("==== Enter New Account Password:            ====");
		password = input.nextLine();
		return password;
	}
	
	public double homeScreenInitialDeposit(){
		double deposit = 0;
		String depositParse = "";
		System.out.println("==== Enter Deposit:                         ====");
		depositParse = input.nextLine();
		if(depositParse.matches("\\d+")){
			deposit = Double.parseDouble(depositParse);
			try{
				if(deposit <= 0){
					throw new LimitException();
				}
			}catch(LimitException e){
				System.out.println("Your first deposit must be greater than $0.00! Try again.");
				homeScreenInitialDeposit();
			}
		}else{
			System.out.println("Non-numeric entry! Try again.");
			homeScreenInitialDeposit();
		}
		return deposit;
	}
	
	// Account Screen
	
	public void accountScreen(Account account){
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("==== OAK RIDGE NUCLEAR FEDERAL CREDIT UNION ====");
		System.out.println("====                                        ====");
		System.out.println("====             ACCOUNT INFO               ====");
		System.out.println("====                                        ====");
		// Username
		// Account Number                       //Total
		System.out.println("==== Welcome " + account.getUsername() + "!");
		System.out.println("====________________________________________====");
		System.out.println("==== Account Number: " + account.getAccountNum() + "    Balance: $" + df.format(account.getBalance()));
		System.out.println("==| Deposit [D] | Withdrawl [W] | Logout [E] |==");
		accountScreenMain();
	}
	
	public void accountScreenMain(){
		String action = "";
		action = input.nextLine();
		if(action.equals("D")){
			accountScreenDeposit();
		}else if(action.equals("W")){
			accountScreenWithdrawl();
		}else if(action.equals("E")){
			currentAccount = new Account();
			System.out.println("You have logged out.");
			homeScreen();
		}else{
			System.out.println("Please enter a valid command.");
			accountScreenMain();
		}
	}
	
	public void accountScreenDeposit(){
		double deposit = 0;
		double newBalance = 0;
		String depositAmount = "";
		System.out.println("Enter amount for deposit.");
		depositAmount = input.nextLine();
		//Add Exception here
		if(depositAmount.matches("\\d+")){
			deposit = Double.parseDouble(depositAmount);
		}
		newBalance = deposit + currentAccount.getBalance();
		accountsDAO.updateAccount(currentAccount, newBalance);
		System.out.println("Deposit successful!");
		//System.out.println(currentAccount.getBalance() + " + " + deposit);
		currentAccount.setBalance(newBalance);
		accountScreen(currentAccount);
	}
	
	public void accountScreenWithdrawl(){
		double withdrawl = 0;
		double newBalance = 0;
		String withdrawlAmount = "";
		try{
			System.out.println("Enter amount for withdrawl.");
			withdrawlAmount = input.nextLine();
			if(withdrawlAmount.matches("\\d+")){
				withdrawl = Double.parseDouble(withdrawlAmount);
				if(withdrawl > currentAccount.getBalance()){
					throw new LimitException();
				}else{
					newBalance = currentAccount.getBalance() - withdrawl;
					accountsDAO.updateAccount(currentAccount, newBalance);
					System.out.println("Deposit successful!");
					System.out.println(currentAccount.getBalance() + " - " + withdrawl);
					currentAccount.setBalance(newBalance);
				}
			}
			accountScreen(currentAccount); //Sends user back to the account home screen
		}catch(LimitException e){
			System.out.println("You don't have that much money. Try again.");
			accountScreenWithdrawl();
		}
	}
}
