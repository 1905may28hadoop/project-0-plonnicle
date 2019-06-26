package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Account;
import com.revature.repository.CloseStreams;
import com.revature.repository.ConnectionUtil;

public class AccountDAOImplOJDBC implements AccountDAO {
	@Override
	public Account getAccount(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccount(long accountNum) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Account account = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM accounts");
			statement.setString(1, Long.toString(accountNum));
			statement.execute();
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			account = new Account(resultSet);
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		return account;
	}

	@Override
	public boolean createAccount(Account account) {
		// TODO Auto-generated method stub
		// Prepared Statement protects us from SQL injection attacks
		// Also nicer aesthetics -- less String +
		PreparedStatement statement = null;
		// We'll do our try with resources again
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?)");
			// Question mark indices                                         1  2  3  4  5
			// Then we modify the indices in Java
			statement.setInt(1, account.getId());
			statement.setString(2, account.getAccountNum());
			statement.setString(3, account.getUsername());
			statement.setString(4, account.getPassword());
			statement.setDouble(5, account.getBalance());
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			CloseStreams.close(statement);
		}
		return true;
	}

	@Override
	public boolean updateAccount(Account account, double newBalance) {
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		//ResultSet resultSet = null;
		//String query = "UPDATE ACCOUNTS SET balance = " + newBalance + " WHERE username = " + account.getUsername();
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE accountnum = ?");
			statement.setDouble(1, newBalance);
			statement.setString(2, account.getAccountNum());
			statement.execute();
			statement2 = conn.prepareStatement("COMMIT");
			statement2.execute();
			//statement.setDouble(1, newBalance);
			//statement.setString(2, account.getUsername());
			//statement2 = conn.prepareStatement(query2);
			//resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //SQL to update information
		return false;
	}

	@Override
	public List<Account> getAccounts() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<Account> accounts = new ArrayList<>();
		// We're going to do a try-with-resources
		// This lets us open a resource, like a DB connection as a part of starting our try block
		// And it will be closed automatically when the try block finishes.
		try(Connection conn = ConnectionUtil.getConnection()){
			// Create our statement:
			statement = conn.createStatement();
			// Statement objects can execute SQL queries
			// The most basic form being:
			resultSet = statement.executeQuery("SELECT * FROM accounts");
			// ResultSet stores all the results from a successful query
			
			// Create ourselves a new PetType object for each row:
			System.out.println(resultSet.next());
			do{
				accounts.add(new Account(resultSet));
			}while(resultSet.next());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		
		return accounts;
	}
}
