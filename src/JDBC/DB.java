package JDBC;

import java.sql.*;
import java.util.*;

import Models.*;

public class DB {
    public static MySQLHelper mHelper;

    public DB() {
        mHelper = new MySQLHelper();
    }

    public Account getAccount(String acnumber) {
        // get accounts ID By accountnumber
        Account acc = null;
        String sql = "select * from accountinfo where ID=" + acnumber;
        ResultSet pResultSet;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {

                String accNumber = pResultSet.getString(1);
                int custID = pResultSet.getInt(2);
                int accType = pResultSet.getInt(3);
                int condition = pResultSet.getInt(6);
                acc = new Account(accNumber, accType, condition);
                HashMap<Integer, Balance> balances = getBalances(accNumber, custID);
                acc.setBalances(balances);

            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public void close() {
        mHelper.close();
    }

    public ArrayList<Account> getAccounts(int id) {

        // get accounts ID By customer id(index)

        String sql = "select * from accountinfo where custID=" + id;
        ResultSet pResultSet;
        ArrayList<Account> accs = new ArrayList<>();
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {

                String accNumber = pResultSet.getString(1);
                int accType = pResultSet.getInt(3);
                int condition = pResultSet.getInt(6);
                if (condition == 1) {
                    Account acc = new Account(accNumber, accType, 1);
                    HashMap<Integer, Balance> balances = getBalances(accNumber, id);
                    acc.setBalances(balances);
                    accs.add(acc);
                }
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accs;

    }

    //get specific stock by its id
    public Stock getStock(String id) {

        String sql = "select * from stockinfo where ID = '" + id + "'";
        ResultSet pResultSet;
        Stock res = null;
        try {
            pResultSet = mHelper.query(sql);
            while (pResultSet.next()) {
                String stockId = pResultSet.getString(1);
                String stockName = pResultSet.getString(2);
                Double stockPrice = pResultSet.getDouble(3);
                res = new Stock(stockId, stockName, stockPrice);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private HashMap<Integer, Balance> getBalances(String accNum, int cId) {

        HashMap<Integer, Balance> balances = new HashMap<>();
        String sql = "select CurrencyID,money from accountinfo where custID=" + cId + " and id='" + accNum + "'";
        ResultSet pResultSet;

        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {

                int currencyID = pResultSet.getInt(1);
                double balance = pResultSet.getDouble(2);
                String currType = getCurrencyType(currencyID);
                balances.put(currencyID, new Balance(balance, currencyID, currType));
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balances;
    }

    private String getCurrencyType(int currencyID) {
        String sql = "select name from currencytype where ID=" + currencyID;
        ResultSet pResultSet;
        String currType = null;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {

                currType = pResultSet.getString(1);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currType;
    }

    public CustomerID getCustomerID(int id) {

        // getCustomerID By index

        String sql = "select * from customerinfo where id=" + id;
        ResultSet pResultSet;
        CustomerID cID = null;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {
                int index = pResultSet.getInt(1);
                String name = pResultSet.getString(2);
                String add = pResultSet.getString(3);
                String password = pResultSet.getString(4);
                String userName = pResultSet.getString(5);
                String phone = pResultSet.getString(6);
                String collateral = pResultSet.getString(7);
                cID = new CustomerID(name, userName, password, index);
                cID.setAddress(add);
                cID.setCollateral(collateral);
                cID.setPhone(phone);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cID;
    }

    public ManagerID getManagerID(int id) {

        // getManagerID By index

        String sql = "select * from managerinfo where id=" + id;
        ResultSet pResultSet;
        ManagerID mID = null;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {
                int index = pResultSet.getInt(1);
                String password = pResultSet.getString(2);
                String userName = pResultSet.getString(3);
                mID = new ManagerID(userName, password, index);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mID;
    }

//	public Stock getStocks() { // getCustomerID By index
//		return null;
//
//	}

    public int ifCustomerID(String username, String pwd) {

        // check db if this username and pwd could match a customer ID

        String sql = "select ID from customerinfo where username='" + username + "' and password='" + pwd + "'";
        ResultSet pResultSet;
        int id = 0;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {
                id = Integer.parseInt(pResultSet.getString(1));

            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int ifManagerID(String username, String pwd) {

        // check db if this username and pwd could match a manager ID

        String sql = "select ID from managerinfo where username='" + username + "' and password='" + pwd + "'";
        ResultSet pResultSet;
        int id = 0;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {
                id = pResultSet.getInt(1);
                System.out.println(id);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<CustomerID> getCustomersID() {
        // getCustomerIDs

        ArrayList<CustomerID> cIDs = new ArrayList<CustomerID>();
        String sql = "select * from customerinfo";
        ResultSet pResultSet;
        CustomerID cID;
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {
                int index = pResultSet.getInt(1);
                String name = pResultSet.getString(2);
                String add = pResultSet.getString(3);
                String password = pResultSet.getString(4);
                String userName = pResultSet.getString(5);
                String phone = pResultSet.getString(6);
                String collateral = pResultSet.getString(7);
                cID = new CustomerID(name, userName, password, index);
                cID.setAddress(add);
                cID.setCollateral(collateral);
                cID.setPhone(phone);
                cIDs.add(cID);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cIDs;
    }


    public void updateCustomerInfo(CustomerID cId) {
        String add = cId.getAddress();
        String password = cId.getPassword();
        String phone = cId.getPhone();
        String collateral = cId.getCollateral();
        int index = cId.getIndex();
        String sql = "Update customerinfo set address='" + add + "',password='" + password + "',phoneno='" + phone
                + "',collateral='" + collateral + "' where id=" + index;
        try {
            mHelper.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAccount(int cIdIndex, Account acc, Balance b) {

        String id = acc.getAccountNumber();
        int custId = cIdIndex;
        int accountType = acc.getType();
        int currencyID = b.getCurID();
        double money = b.getMoney();
        int condition = 1;
        String sql = "Insert into accountinfo values ('" + id + "'," + custId + "," + accountType + "," + currencyID
                + "," + money + "," + condition + ")";
        try {
            mHelper.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int getCountsAcc1() {
        String sql = "Select * from accountinfo";
        return mHelper.getCount(sql);
    }


    public void updateAccount(Account acc, Balance b) {
        String id = acc.getAccountNumber();
        int currencyID = b.getCurID();
        double money = b.getMoney();
        int condition = acc.getCondition();
        String sql = "Update accountinfo set money=" + money + ",Con=" + condition + " where id='" + id
                + "' and currencyID=" + currencyID;
        try {
            mHelper.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCountsAcc() {
        String sql = "Select * from accountinfo";
        return mHelper.getCount(sql);
    }


    public boolean addStock(Stock stock) {

        String stockId = stock.getId();
        String name = stock.getStockName();
        Double price = stock.getPrice();

        Stock checkStock = getStock(stockId);
        if (checkStock != null) {
            System.out.println("Already have the same ID!");
            return false;
        } else {

            String sql = "Insert into stockinfo values ('" + stockId + "', '" + name + "'," + price + ")";
            try {
                mHelper.update(sql);
                System.out.println("Succeed add a stock!");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }

    }

    public void deleteStock(String stockId) {

        String sql = "delete from stockinfo where id = '" + stockId + "'";
        try {
            mHelper.update(sql);
            System.out.println("Succeed delete a stock");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modifyStockPrice(String stockId, Double modifyPrice) {
        String sql = "Update stockinfo set CurrentPrice = " + modifyPrice + " where id = '" + stockId + "'";
        try {
            mHelper.update(sql);
            System.out.println("Succeed modify the price!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    get the informations of all transactions
     */
    public ArrayList<Transaction> getTransactions() {
        //TODO
        String sql = "SELECT id,opaccountid,targetaccountid,amount,currencyid,name,customerid,info FROM transactiondetails";
        ResultSet tResultSet;
        ArrayList<Transaction> transArr = new ArrayList<>();
        try {
            tResultSet = mHelper.query(sql);

            while (tResultSet.next()) {
                int index = tResultSet.getInt(1);
                String operaAccNum = tResultSet.getString(2);
                String targetAccNum = tResultSet.getString(3);
                double amount = tResultSet.getDouble(4);
                int curID = tResultSet.getInt(5);
                String name = tResultSet.getString(6);
                int cusID = tResultSet.getInt(7);
                String info = tResultSet.getString(8);

                //get the name from currencytype
                String getCurrecyIdSql = "select name from currencytype where ID = '" + curID + "'";

                ResultSet cResultSet = mHelper.query(getCurrecyIdSql);

                String currencyName = null;
                if (tResultSet.next()) {
                    currencyName = cResultSet.getString(1);
                }
                //create balance instance
                Balance newBalance = new Balance(amount, curID, currencyName);

                Transaction newTransaction = new Transaction(index, cusID, name, operaAccNum, targetAccNum, info, newBalance);

                transArr.add(newTransaction);
            }
            tResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transArr;
    }


    public ArrayList<Stock> getStocks() {
        //TODO
        String sql = "Select * from stockinfo";
        ResultSet pResultSet;
        ArrayList<Stock> stockArr = new ArrayList<>();
        try {
            pResultSet = mHelper.query(sql);

            while (pResultSet.next()) {

                String stockId = pResultSet.getString(1);
                String stockName = pResultSet.getString(2);
                double currentPrice = pResultSet.getDouble(3);
                Stock stock = new Stock(stockId, stockName, currentPrice);
                stockArr.add(stock);
            }
            pResultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//		System.out.println("Stock size:" + stockArr.size());

        return stockArr;
    }

    public boolean hasBindingSecurityAccount(String savingId) {

        String sql = "select * from securityinfo where AccountId = '" + savingId + "'";
        ResultSet pResultSet;
        pResultSet = mHelper.query(sql);
        boolean hasSecure = false;

        try {
            while (pResultSet.next()) {
                try {
                    if (!pResultSet.getString(1).isEmpty()) {
                        hasSecure = true;
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            pResultSet.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hasSecure;
    }

    public void addSecurity(String savingId) {
        String sql = "Select * from securityinfo";
        int securityCnt = mHelper.getCount(sql);

        String newSecureId = String.valueOf(securityCnt + 1);
        System.out.println("newSecureId" + newSecureId);

        String sqlInsert = "Insert into securityinfo values ('" + newSecureId + "', '" + savingId + "')";

        try {
            mHelper.update(sqlInsert);
            System.out.println("Succeed add a security account!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getSecureId(String savingId) {
        String sql = "select * from securityinfo where AccountId = '" + savingId + "'";
        ResultSet pResultSet;
        pResultSet = mHelper.query(sql);
        String secureId = "";
        try {
            while (pResultSet.next()) {
                try {
                    secureId = pResultSet.getString(1);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            pResultSet.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return secureId;
    }

    public void buyShares(String secureId, String stockId, int shareNumber) {

        String sqlModify = "Select * from sharesinfo where SecId = '" + secureId + "' and StockId = '" + stockId + "'";
        ResultSet pResultSet;
        pResultSet = mHelper.query(sqlModify);
        boolean exist = false;
        int shareHas = 0;
        try {
            while (pResultSet.next()) {
                try {
                    if (!pResultSet.getString(1).isEmpty()) {
                        exist = true;
                        shareHas = pResultSet.getInt(4);
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            pResultSet.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (exist) {

            System.out.println("current stock exists!");

            String sqlAddShare = "Update sharesinfo set ShareNumber = " + (shareHas + shareNumber) + " where SecId = '" + secureId + "' and StockId = '" + stockId + "'";
            try {
                mHelper.update(sqlAddShare);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String sql = "Select * from sharesinfo";
            int sharesCnt = mHelper.getCount(sql);

            String newShareId = String.valueOf(sharesCnt + 1);

            String sqlInsert = "Insert into sharesinfo values ('" + newShareId + "', '" + secureId + "', '" + stockId + "', " + shareNumber + ")";
            try {
                mHelper.update(sqlInsert);
                System.out.println("Succeed add a share record!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<String> getStockIdFromSecure(String secureId) {
        ArrayList<String> res = new ArrayList<>();
        String sql = "Select * from sharesinfo where SecID = '" + secureId + "'";
        ResultSet pResultSet;
        pResultSet = mHelper.query(sql);

        try {
            while (pResultSet.next()) {
                res.add(pResultSet.getString(3) + "   " + pResultSet.getInt(4));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<String> getStockInfoAmountFromSecure(String secureId) {
        ArrayList<String> res = new ArrayList<>();
//		select * from sharesinfo, stockinfo where sharesinfo.stockId = stockInfo.ID;
//		select * from sharesinfo, stockinfo where sharesinfo.SecId = "2" and sharesinfo.stockId = stockInfo.ID;
        String sql = "Select * from stockinfo, sharesinfo where sharesinfo.SecID = '" + secureId + "' and sharesinfo.stockId = stockInfo.ID";
        ResultSet pResultSet;
        pResultSet = mHelper.query(sql);

        try {
            while (pResultSet.next()) {
                res.add(pResultSet.getString(1) + "   " + pResultSet.getString(2) + "   " + pResultSet.getString(3) + "   " + pResultSet.getInt(7));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public void deleteShareInfo(String secureId, String stockId) {
        String sql = "delete from sharesinfo where SecID = '" + secureId + "' and StockID = '" + stockId + "'";
        try {
            mHelper.update(sql);
            System.out.println("Succeed delete!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modifyShareInfo(String secureId, String stockId, int currentAmount) {

        String sqlModifyShare = "Update sharesinfo set ShareNumber = " + currentAmount + " where SecId = '" + secureId + "' and StockId = '" + stockId + "'";
        try {
            mHelper.update(sqlModifyShare);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTransaction(Transaction t) {
        //TODO
        String name = t.getName();
        String info = t.getInfo();
        Balance balance = t.getBalance();
        String operaAccNum = t.getOperaAccNum();
        String targetAccNum = t.getTargetAccNum();
        int cusID = t.getCusID();

        double money = balance.getMoney();
        int curID = balance.getCurID();

        String insertTransSql = "insert into transactiondetails values(null, '"
                + operaAccNum + "', '" + targetAccNum + "', " + money + ", " + curID + ", '" + name + "', " + cusID + ", '" + info + "')";
        try {
            mHelper.update(insertTransSql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createCustomer(CustomerID cId) {
        //TODO
    }

    public void deleteCustomer(int CIdIndex) {
        //TODO
    }

    public void updateStockShares() {  // convey a class
        // TODO
    }


}
