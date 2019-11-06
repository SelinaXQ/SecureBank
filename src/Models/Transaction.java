package Models;

public class Transaction {
	private String name;
	private String info;
	private Balance balance;
	private String operaAccNum;
	private String targetAccNum;
	private int cusID;
	private int index;

	public int getCusID() {
		return cusID;
	}

	public void setCusID(int cusID) {
		this.cusID = cusID;
	}

	public Transaction(int index, int cIdIndex, String name, String accNum, String tarNum, String info,
			Balance balance) {
		// for query
		this.index = index;
		this.cusID = cIdIndex;
		this.name = name;
		this.operaAccNum = accNum;
		this.targetAccNum = tarNum;
		this.info = info;
		this.balance = balance;
	}
	
	public Transaction(int cIdIndex, String name, String accNum, String tarNum, String info,
			Balance balance) {
		// for insert
		this.cusID = cIdIndex;
		this.name = name;
		this.operaAccNum = accNum;
		this.targetAccNum = tarNum;
		this.info = info;
		this.balance = balance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public String getOperaAccNum() {
		return operaAccNum;
	}

	public void setOperaAccNum(String operaAccNum) {
		this.operaAccNum = operaAccNum;
	}

	public String getTargetAccNum() {
		return targetAccNum;
	}

	public void setTargetAccNum(String targetAccNum) {
		this.targetAccNum = targetAccNum;
	}

	public String toString() {
		String inOrOut = null;
		if (balance.getMoney() > 0) {
			inOrOut = "from";
		} else {
			inOrOut = "to";
		}
		String s = " (" + inOrOut + " " + targetAccNum + ")";
		if (targetAccNum.trim().equals("") || targetAccNum.trim().isEmpty()) {
			return name + "     " + operaAccNum + "     " + info + "     " + balance;
		} else {
			return name + "     " + operaAccNum + "     " + info + s + "     " + balance;
		}
	}
}
