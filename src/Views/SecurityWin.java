package Views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Models.ATM;
import Models.Account;
import Models.CustomerID;
import Models.Stock;

public class SecurityWin {

	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JFrame frame;

	private JLabel securityLabel;
	private JLabel savingLabel;

	private JPanel blankPanel, p1, p2, p3, p4;
	private JPanel infoPanel;
//	private JPanel buyPanel, buyButtonPanel;
//	private JPanel sellPanel, sellButtonPanel;
	private JPanel boughtPanel, stockMarketPanel, buyAmountPanel, sellAmountPanel;
	private JList<String> stockList;
	private JList<String> boughtList;
	private ArrayList<Stock> stocks;

	private JLabel stockMarket;
	private JLabel boughtStock;
	private JLabel buyAmountLabel;
	private JTextField buyAmountField;
	private JLabel sellAmountLabel;
	private JTextField sellAmountField;

	private JButton buyButton;
	private JButton sellButton;
	private JButton backButton;

	private int curBuyListId;
	private String curSellStockInfo;

	private ATM atm;
	private CustomerID cId;
	private Account acc;
	private int curAccIndex;
	private String secureId;

	public SecurityWin(ATM atm, CustomerID cId, Account acc, int curAccIndex) {

		this.atm = atm;
		this.cId = cId;
		this.atm = atm;
		this.acc = acc;
		this.curAccIndex = curAccIndex;
		stocks = atm.getStocksDB();
		secureId = atm.getSecureIdDB(acc.getAccountNumber());

		setSecurityWin();
		initSecurityWin();

	}

	private void setSecurityWin() {
		frame = new JFrame();
		securityLabel = new JLabel("---- Security Account ----" );
		savingLabel = new JLabel("Saving Account: " + acc.getAccountNumber());

		buyAmountLabel = new JLabel("How many shares: ");
		buyAmountField = new JTextField(10);
		sellAmountLabel = new JLabel("How many shares: ");
		sellAmountField = new JTextField(10);

		infoPanel = new JPanel();
	//	buyPanel = new JPanel();
	//	sellPanel = new JPanel();
		blankPanel = new JPanel();
		stockMarketPanel = new JPanel();
		buyAmountPanel = new JPanel();
		sellAmountPanel = new JPanel();

	//	buyButtonPanel = new JPanel();
		boughtPanel = new JPanel();
	//	sellButtonPanel = new JPanel();

		p1 = new JPanel();
		p2 = new JPanel();

		p3 = new JPanel();
		p4 = new JPanel();

		stockList = new JList<>();
		boughtList = new JList<>();

		stockMarket = new JLabel("Stock Market");
		boughtStock = new JLabel("Your Stock");

		buyButton = new JButton("Buy");
		sellButton = new JButton("Sell");
		backButton = new JButton("Back");

		String[] stockStr = new String[stocks.size()];
		if (stocks != null) {

			for (int i = 0; i < stocks.size(); i++) {
				Stock curStock = stocks.get(i);
				String stockInfo = curStock.getId() + "   " + curStock.getStockName() + "   " + curStock.getPrice();
				stockStr[i] = stockInfo;
			}
			// add stocks
		}
		stockList = new JList<String>(stockStr);

		ArrayList<String> boughtStockInfoList = atm.getStockInfoAmountFromSecureDB(secureId);
		String[] stockStr2 = new String[boughtStockInfoList.size()];
		for (int i = 0; i < boughtStockInfoList.size(); i++) {
			String stockInfo = boughtStockInfoList.get(i);
			stockStr2[i] = stockInfo;
		}
		boughtList = new JList<String>(stockStr2);
		scrollPane = new JScrollPane(stockList);
		scrollPane2 = new JScrollPane(boughtList);
		stockList.setVisibleRowCount(5);
		boughtList.setVisibleRowCount(5);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

	}

	private void initSecurityWin() {
		frame.setLayout(new GridLayout(6, 1));
		frame.add(blankPanel);
		frame.add(infoPanel);
		frame.add(stockMarketPanel);
		// frame.add(buyPanel);
		frame.add(buyAmountPanel);
		// frame.add(buyButtonPanel);
		frame.add(boughtPanel);
		// frame.add(sellPanel);
		frame.add(sellAmountPanel);
	//	frame.add(sellButtonPanel);

		infoPanel.setLayout(new GridLayout(2, 1));

		infoPanel.add(p1);
		infoPanel.add(p2);
		stockMarketPanel.add(stockMarket);
		stockMarketPanel.add(scrollPane);
		stockList.setFixedCellWidth(380);
		p1.add(securityLabel);
		p2.add(savingLabel);

		// buyPanel.add(stockMarket);
		// buyPanel.add(scrollPane);

		buyAmountPanel.add(buyAmountLabel);
		buyAmountPanel.add(buyAmountField);
		buyAmountPanel.add(buyButton);

		boughtPanel.add(boughtStock);
		boughtPanel.add(scrollPane2);
		boughtList.setFixedCellWidth(380);

		sellAmountPanel.setLayout(new GridLayout(2, 1));
		sellAmountPanel.add(p3);
		sellAmountPanel.add(p4);
		p3.add(sellAmountLabel);
		p3.add(sellAmountField);
		p4.add(backButton);
		p4.add(sellButton);

		buyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String stockId = stocks.get(curBuyListId).getId();
//				acc
				if (buyAmountField.getText() == null || buyAmountField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter how many shares you want to buy!");
				} else {
					int buyAmount = Integer.valueOf(buyAmountField.getText());
					// check current sharesinfo
					// add a new record to sharesinfo
					atm.buySharesDB(secureId, stockId, buyAmount);

					// boughtList add one
					ArrayList<String> boughtStockInfoList = atm.getStockInfoAmountFromSecureDB(secureId);
					String[] stockStr = new String[boughtStockInfoList.size()];
					for (int i = 0; i < boughtStockInfoList.size(); i++) {
						String stockInfo = boughtStockInfoList.get(i);
						stockStr[i] = stockInfo;
					}
					boughtList.setListData(stockStr);
					// minus account balance
					atm.transaction(cId, acc, 2, buyAmount*stocks.get(curBuyListId).getPrice(), 1);
					atm.updateCustomerAccDB(cId);
				}

			}

		});
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] infoStr = curSellStockInfo.split("  ");
//				System.out.println(infoStr.length);
				String stockId = infoStr[0].trim();
				int shareAmount = Integer.valueOf(infoStr[3].trim());
				System.out.println("stockId" + stockId);
				System.out.println("shareAmount" + shareAmount);
//				sellAmountField
				if (sellAmountField.getText() == null || sellAmountField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter how many shares you want to sell!");
				} else {
					int sellAmount = Integer.valueOf(sellAmountField.getText());
					if (sellAmount > shareAmount) {
						JOptionPane.showMessageDialog(null, "Please enter an integer less than your current amount!");
					} else if (sellAmount == shareAmount) {// delete info in sharesinfo

						atm.deleteShareInfoDB(secureId, stockId);
						// update list
						ArrayList<String> boughtStockInfoList = atm.getStockInfoAmountFromSecureDB(secureId);
						String[] stockStr2 = new String[boughtStockInfoList.size()];
						for (int i = 0; i < boughtStockInfoList.size(); i++) {
							String stockInfo = boughtStockInfoList.get(i);
							stockStr2[i] = stockInfo;
						}
						JOptionPane.showMessageDialog(null, "Sell succeed!");
						boughtList.setListData(stockStr2);

					} else {// modify info in sharesinfo

						int currentAmount = shareAmount - sellAmount;
						atm.modifyShareInfoDB(secureId, stockId, currentAmount);
						// update list
						ArrayList<String> boughtStockInfoList = atm.getStockInfoAmountFromSecureDB(secureId);
						String[] stockStr2 = new String[boughtStockInfoList.size()];
						for (int i = 0; i < boughtStockInfoList.size(); i++) {
							String stockInfo = boughtStockInfoList.get(i);
							stockStr2[i] = stockInfo;
						}
						JOptionPane.showMessageDialog(null, "Sell succeed!");
						boughtList.setListData(stockStr2);

					}
					atm.transaction(cId, acc, 1, sellAmount*stocks.get(curBuyListId).getPrice(), 1);
					atm.updateCustomerAccDB(cId);
				}
			}

		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, 2, curAccIndex);
				frame.dispose();
			}

		});

		stockList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				curBuyListId = stockList.getSelectedIndex();
				// String stockId = stocks.get(curBuyListId).getId();

			}

		});

		boughtList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				curSellStockInfo = boughtList.getSelectedValue();

			}

		});

		frame.setTitle("Stock Market");
		frame.setSize(500, 600);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

}
