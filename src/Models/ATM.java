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
		// transaction interface between ATM and bank
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
		// transfer money between banks
		// acc
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
				updateCustomerAccDB(cId);
				currentBank.addTransaction(cId.getIndex(), cId.getName(), acc1, acc2, Bank.TR_TRANSFER, b);
				break;
			}
		}
		// target acc
		ArrayList<CustomerID> cIds = currentBank.getCustomers();
		for (int i = 0; i < cIds.size(); i++) {
			CustomerID cId2 = cIds.get(i);
			for (int j = 0; j < cId2.getAccounts().size(); j++) {
				Account account = cId2.getAccounts().get(j);
				if (account.getAccountNumber().equals(acc2)) {
					double money = account.getBalances().get(balanceType).getMoney() + amount;
					account.getBalances().get(balanceType).setMoney(money);
					cId2.getAccounts().set(j, account);
					updateCustomerAccDB(cId2);
					Balance b = new Balance(amount, balanceType, Bank.CURRENCY_LIST[balanceType] + 1);
					currentBank.addTransaction(cId2.getIndex(), cId2.getName(), acc2, acc1, Bank.TR_TRANSFER, b);
					return;
				}
			}
		}
	}

	public void createAccount(CustomerID cId, int type) {
		// create a certain type of account
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
		// stop an account, change its condition attribute to 0
		currentBank.stopAccount(cId, curAccount);
	}

	public void getInterest(CustomerID cId, Account acc) {
		// get Saving account 's interest
		currentBank.getInterest(cId, acc);
	}

	public ArrayList<Transaction> getTransactionsDB(int cIdIndex) {
		// get transactions from database
		ArrayList<Transaction> trans = new ArrayList<Transaction>();
		trans.addAll(currentBank.getTransactions());
		if (cIdIndex == -1) { // if this is a check by manager, we need to return all the transactions
			return trans;
		} else { // only remain transactions of this customer
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
		// update customer 's info
		currentBank.updateCustomer(cId);

	}

	public void updateCustomerAccDB(CustomerID cId) {
		// update customer 's account information and info
		currentBank.updateCustomerAccs(cId.getIndex(), cId.getAccounts());

	}

	public boolean ifAccountDB(String accn) {
		// check if there is an account of this accountnumber
		return currentBank.ifAccount(accn);
	}

	public boolean addStockDB(Stock stock) {
		if (db.addStock(stock)) {
			return true;
		} else {
			return false;
		}

	}

	public void deleteStockDB(String stockId) {
		db.deleteStock(stockId);
	}

	public void modifyStockPriceDB(String stockId, Double modifyPrice) {
		db.modifyStockPrice(stockId, modifyPrice);
	}

	public ArrayList<Stock> getStocksDB() {
		return db.getStocks();
	}

	public boolean hasBindingSecurityAccountDB(String savingNum) {
		return db.hasBindingSecurityAccount(savingNum);
	}

	public void addSecurityDB(String savingId) {
		db.addSecurity(savingId);
	}

	public String getSecureIdDB(String savingId) {
		return db.getSecureId(savingId);
	}

	public void buySharesDB(String secureId, String stockId, int shareNumber) {
		db.buyShares(secureId, stockId, shareNumber);
	}

	public ArrayList<String> getStockIdFromSecureDB(String secureId) {
		return db.getStockIdFromSecure(secureId);
	}

	public ArrayList<String> getStockInfoAmountFromSecureDB(String secureId) {
		return db.getStockInfoAmountFromSecure(secureId);
	}

	public void deleteShareInfoDB(String secureId, String stockId) {
		db.deleteShareInfo(secureId, stockId);
	}

	public void modifyShareInfoDB(String secureId, String stockId, int currentAmount) {
		db.modifyShareInfo(secureId, stockId, currentAmount);

	}

	public double getSecurityThresold() {
		return currentBank.getSecurityThresold();
	}
}
