package com.revature.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	
	private int id;
	private String accountNum;
	private String username;
	private String password;
	private double balance;
	
	public Account(){
		super();
		this.id = 0;
		this.accountNum = "";
		this.username = "";
		this.password = "";
		this.balance = 0;
	}
	
	public Account(int id, String accountNum, String username, String password, double balance){
		super();
		this.id = id;
		this.accountNum = accountNum;
		this.username = username;
		this.password = password;
		this.balance = balance;
	}
	
	public Account(ResultSet resultSet) throws SQLException{
		// TODO Auto-generated constructor stub
		this(resultSet.getInt("id"),
				resultSet.getString("accountnum"), //Convert
				resultSet.getString("username"),
				resultSet.getString("pass_word"),
				resultSet.getDouble("balance"));
	}

	public int getId(){
		return id;
	}
	public String getAccountNum(){
		return accountNum;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public double getBalance(){
		return balance;
	}
	
	
	public void setId(int id){
		this.id = id;
	}
	public void setAccountNum(String accountNum){
		//Format here
		this.accountNum = accountNum;
	}
	public void setUsername(String username){
		this.username = username;
	}

	public void setPassword(String password){
		this.password = password;
	}
	public void setBalance(double balance){
		//Format here
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "Account Info [id=" + id + ", username =" + username + "]";
	} 
}
