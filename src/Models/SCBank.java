package Models;

import java.util.ArrayList;
import java.util.HashMap;

import JDBC.DB;

public class SCBank extends Bank {
	public static final float SC_CHECKING_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_CHECKING_ACCOUNT_CLOSE_FEE = 10;
	public static final float SC_SAVING_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_SAVING_ACCOUNT_CLOSE_FEE = 10;
	public static final float SC_LOANS_ACCOUNT_OPEN_FEE = 10;
	public static final float SC_LOANS_ACCOUNT_CLOSE_FEE = 10;

	public static final float SC_TRANSACTION_FEE = 10;
	public static final float SC_WITHDRAWAL_FEE = 10;

	public static final double SC_LOANS_INTEREST = 0.001;
	public static final double SC_SAVING_INTEREST = 0.002;

	public static final double SC_SAVING_INTEREST_THRESOLD = 5000;

	public static final int SC_SECURE_THRESOLD = 500;

	public SCBank(String name, DB db) {
		super(name, db);
		CHECKING_ACCOUNT_OPEN_FEE = SC_CHECKING_ACCOUNT_OPEN_FEE;
		CHECKING_ACCOUNT_CLOSE_FEE = SC_CHECKING_ACCOUNT_CLOSE_FEE;
		SAVING_ACCOUNT_OPEN_FEE = SC_SAVING_ACCOUNT_OPEN_FEE;
		SAVING_ACCOUNT_CLOSE_FEE = SC_SAVING_ACCOUNT_CLOSE_FEE;
		LOANS_ACCOUNT_OPEN_FEE = SC_LOANS_ACCOUNT_OPEN_FEE;
		LOANS_ACCOUNT_CLOSE_FEE = SC_LOANS_ACCOUNT_CLOSE_FEE;

		TRANSACTION_FEE = SC_TRANSACTION_FEE;

		LOANS_INTEREST = SC_LOANS_INTEREST;
		SAVING_INTEREST = SC_SAVING_INTEREST;

		SAVING_INTEREST_THRESOLD = SC_SAVING_INTEREST_THRESOLD;
		SECURE_THRESOLD = SC_SECURE_THRESOLD;
	}

	public void createCustomer(String name, String userName, String pwd, String address, String phone,
			String collateral) {
		CustomerID cId = new CustomerID(name, userName, pwd, getCounts("Customer") + 1);
		cId.setAddress(address);
		cId.setPhone(phone);
		cId.setCollateral(collateral);
		createCheckingAccount(cId);
		db.createCustomer(cId);
		Account acc = cId.getAccounts().get(0);
		HashMap<Integer, Balance> bs = acc.getBalances();
		for (Balance b : bs.values()) {
			db.insertAccount(cId.getIndex(), acc, b);
		}
	}

	@Override
	public int ifManager(String username, String pwd) { // By database
		return db.ifManagerID(username, pwd);
	}

	@Override
	public int ifCustomer(String username, String pwd) { // By database
		return db.ifCustomerID(username, pwd);
	}

	@Override
	public ManagerID getManager(int id) { // By database
		return db.getManagerID(id);
	}

	@Override
	public CustomerID getCustomer(int id) { // By database
		CustomerID cId = db.getCustomerID(id);
		ArrayList<Account> accounts = db.getAccounts(id);
		cId.setAccounts(accounts);
		return cId;
	}

	@Override
	public ArrayList<CustomerID> getCustomers() { // By database
		return db.getCustomersID();
	}

	@Override
	public void updateCustomer(CustomerID cId) { // By database
		db.updateCustomerInfo(cId);
	}

	@Override
	public void updateCustomerAccs(int cIdIndex, ArrayList<Account> accs) { // By database
		for (int i = 0; i < accs.size(); i++) {
			Account acc = accs.get(i);

			HashMap<Integer, Balance> bs = acc.getBalances();

			for (Balance b : bs.values()) {
				Account accQuery = db.getAccount(acc.getAccountNumber(), b);
				if (accQuery == null) {

					db.insertAccount(cIdIndex, acc, b);
				} else {
					db.updateAccount(acc, b);
				}
			}
		}

	}

	public static int getCounts(String classstr) { // By database 
		int c = 0;
		if (classstr.equals("Account")) {
			c = db.getCountsAcc();
		}else if(classstr.equals("Customer")) {
			c = db.getCountsCust();
		}
		return c;
	}

	@Override
	public void deleteCustomer(CustomerID cId) { // By database
		for (int i = 0; i < cId.getAccounts().size(); i++) {
			stopAccount(cId, cId.getAccounts().get(i));
		}
		updateCustomerAccs(cId.getIndex(), cId.getAccounts());
		db.deleteCustomer(cId.getIndex());
	}

	@Override
	public boolean ifAccount(String accn) { // By database
		boolean flag = false;
		Account account = null;
		account = db.getAccount(accn, null);
		if (account != null && account.getType() != Bank.LOANS_ACCOUNT) {
			flag = true;
		}
		return flag;
	}

	@Override
	public ArrayList<Transaction> getTransactions() { // By database
		return db.getTransactions();
	}

}
