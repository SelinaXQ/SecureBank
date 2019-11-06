package Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import JDBC.*;
import Views.*;

public class ATM {
	private Bank currentBank;
	private Date date;
	private SimpleDateFormat dateFormat;
	private DB db;

	public ATM() {
		Locale.setDefault(new Locale("en", "US"));
		date = new Date();
		dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		dateFormat.format(date);
		db = new DB();
		currentBank = new SCBank("SC", db);
	}

	public Date getDate() {
		return date;
	}

	public static void main(String[] args) {
		new LoginWin(new ATM());
	}

	public int loginToBank(String username, String pwd, int type) { // By database
		switch (type) {
		case 1:
			int ifManager = currentBank.ifManager(username, pwd);
			return ifManager | 0;
		case 2:
			int ifCustomer = currentBank.ifCustomer(username, pwd);
			return ifCustomer | 0;
		default:
			break;
		}
		return 0;
	}

	public ManagerID getManagerByDB(int index) { // By database
		return currentBank.getManager(index);
	}

	public CustomerID getCustomerByDB(int index) { // By database
		return currentBank.getCustomer(index);
	}

	public ArrayList<CustomerID> getCustomersByDB() { // By database
		return currentBank.getCustomers();
	}


	public void transaction(CustomerID cId, Account acc, int inOrOut, double amount, int type) {
		int accType = acc.getType();
		if (accType == Bank.LOANS_ACCOUNT) {
			if (inOrOut == 1) {
				currentBank.borrowLoans(cId, acc, amount, type);
			} else if (inOrOut == 2) {
				currentBank.returnLoans(cId, acc, amount, type);
			}
		} else {
			if (inOrOut == 1) {
				currentBank.deposit(cId, acc, amount, type);
			} else if (inOrOut == 2) {
				currentBank.withdrawal(cId, acc, amount, type);
			}
		}
		return;
	}

	public void transfer(CustomerID cId, Account acc, String acc2, double amount, int balanceType) {

		double newM = acc.getBalances().get(balanceType).getMoney() - amount;
		String acc1 = null;
		String acc0 = acc.getAccountNumber();
		acc.getBalances().get(balanceType).setMoney(newM);
		for (int i = 0; i < cId.getAccounts().size(); i++) {
			Account account = cId.getAccounts().get(i);
			acc1 = account.getAccountNumber();
			if (acc1.equals(acc0)) {
				Balance b = new Balance(-amount, balanceType, Bank.CURRENCY_LIST[balanceType] + 1);
				cId.getAccounts().set(i, acc);
				break;
			}
		}
		ArrayList<CustomerID> cIds = currentBank.getCustomers();
		for (int i = 0; i < cIds.size(); i++) {
			for (int j = 0; j < cIds.get(i).getAccounts().size(); j++) {
				Account account = cIds.get(i).getAccounts().get(j);
				if (account.getAccountNumber().equals(acc2)) {
					double money = account.getBalances().get(balanceType).getMoney() + amount;
					account.getBalances().get(balanceType).setMoney(money);
					cIds.get(i).getAccounts().set(j, account);
					updateCustomerAccDB(cId);
					updateCustomerAccDB(cIds.get(i));
					Balance b = new Balance(amount, balanceType, Bank.CURRENCY_LIST[balanceType] + 1);
					currentBank.addTransaction(cId.getIndex(), cId.getName(), acc1, acc2, Bank.TR_TRANSFER, b);
					currentBank.addTransaction(cIds.get(i).getIndex(), cIds.get(i).getName(), acc2, acc1, Bank.TR_TRANSFER,
							b);
					return;
				}
			}
		}
	}

	public void createAccount(CustomerID cId, int type) {
		switch (type) {
		case Bank.CHECKING_ACCOUNT:
			currentBank.createCheckingAccount(cId);
			break;
		case Bank.SAVING_ACCOUNT:
			currentBank.createSavingAccount(cId);
			break;
		case Bank.LOANS_ACCOUNT:
			currentBank.createLoansAccount(cId);
			break;

		default:
			break;
		}
	}

	public void stopAccount(CustomerID cId, Account curAccount) {
		currentBank.stopAccount(cId, curAccount);
	}

	public void newCustomerWin(ATM atm, CustomerID cId, boolean b, int win) {
		new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, win, 0);

	}


	public void loans(CustomerID cId, Account acc, int inOrOut, double amount, int type) {
		if (inOrOut == 1) {
			currentBank.borrowLoans(cId, acc, amount, type);
		} else if (inOrOut == 2) {
			currentBank.returnLoans(cId, acc, amount, type);
		}
		return;

	}

	public void getInterest(CustomerID cId, Account acc) {
		currentBank.getInterest(cId, acc);
	}

	public ArrayList<Transaction> getTransactionsDB(int cIdIndex) {
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		trans.addAll(currentBank.getTransactions());
		if (cIdIndex == -1) {
			return trans;
		} else {
			for (int i = 0; i < trans.size(); i++) {
				if (trans.get(i).getCusID() != cIdIndex) {
					trans.remove(i);
					i--;
				}
			}
			return trans;
		}
	}

	public void deleteCustomerDB(int index) {
		currentBank.deleteCustomer(index);

	}

	public void createCustomerIdDB(String name, String userName, String pwd, String address, String phone,
			String collateral) {
		currentBank.createCustomer(name, userName, pwd, address, phone, collateral);
	}

	public void updateCustomerInfoDB(CustomerID cId) {
		currentBank.updateCustomer(cId);

	}

	public void updateCustomerAccDB(CustomerID cId) {
		currentBank.updateCustomerAccs(cId.getIndex(), cId.getAccounts());

	}

	public boolean ifAccountDB(String accn) {
		return currentBank.ifAccount(accn);
	}

	public boolean ifSecurityDB(Account cur) {
		return currentBank.ifSecurity(cur);
	}

	public void addStockDB(Stock stock) {
		db.addStock(stock);
//		return true;

	}

	public ArrayList<Stock> getStocksDB() {
		return db.getStocks();
	}

}
