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

	private JFrame frame;
	
	private JLabel  securityLabel;
	private JLabel  savingLabel;
	
	private JPanel  blankPanel,p1,p2;
	private JPanel infoPanel;
	private JPanel buyPanel;
	private JPanel sellPanel;
	private JPanel buyButtonPanel, boughtPanel, sellButtonPanel, stockMarketPanel, buyAmountPanel, sellAmountPanel;
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
		securityLabel = new JLabel("Security Account: ");   //TODO  security account number
		savingLabel = new JLabel("Saving Account: "); //TODO  saving account number
		
		buyAmountLabel = new JLabel("How many shares: ");
		buyAmountField = new JTextField(10);
		sellAmountLabel = new JLabel("How many shares: ");
		sellAmountField = new JTextField(10);
		
		infoPanel = new JPanel();
		buyPanel = new JPanel();
		sellPanel = new JPanel();
		blankPanel = new JPanel();
		stockMarketPanel = new JPanel();
		buyAmountPanel = new JPanel();
		sellAmountPanel = new JPanel();
		
		buyButtonPanel = new JPanel();
		boughtPanel = new JPanel();
		sellButtonPanel = new JPanel();
		
		p1 = new JPanel();
		p2 = new JPanel();
		
		stockList = new JList<>();
		boughtList = new JList<>();
		
		stockMarket = new JLabel("Stock Market");
		boughtStock = new JLabel("Your Stock");
		
		buyButton = new JButton("Buy");
		sellButton = new JButton("Sell");
		backButton = new JButton("Back");
		
		String[] stockStr = new String[stocks.size()];
		if (stocks != null) {
			
			for(int i = 0 ; i < stocks.size(); i++) {
				Stock curStock = stocks.get(i);
				String stockInfo = curStock.getId() + "   " + curStock.getStockName() + "   " + curStock.getPrice();					
				stockStr[i] = stockInfo;
			}
			//add stocks	
		}
		
		stockList = new JList<String>(stockStr);
	}
	
	
	private void initSecurityWin() {
		frame.setLayout(new GridLayout(8, 1));
//		frame.add(blankPanel);
//		frame.add(infoPanel);
		frame.add(stockMarketPanel);
		frame.add(buyPanel);
		frame.add(buyAmountPanel);
		frame.add(buyButtonPanel);
		frame.add(boughtPanel);
		frame.add(sellPanel);
		frame.add(sellAmountPanel);
		frame.add(sellButtonPanel);
		
		
//		infoPanel.setLayout(new GridLayout(4, 1));
		
//		infoPanel.add(p1);  
//		infoPanel.add(p2);  
		stockMarketPanel.add(stockMarket);
//		p1.add(securityLabel);
//		p2.add(savingLabel);
		
//		buyPanel.add(stockMarket);
		buyPanel.add(stockList);
		buyAmountPanel.add(buyAmountLabel);
		buyAmountPanel.add(buyAmountField);
		
		buyButtonPanel.add(buyButton);
		
		
		boughtPanel.add(boughtStock);
		sellPanel.add(boughtList);
		sellAmountPanel.add(sellAmountLabel);
		sellAmountPanel.add(sellAmountField);
		sellButtonPanel.add(backButton);
		sellButtonPanel.add(sellButton);
		
		
		
		
		
		buyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String stockId = stocks.get(curBuyListId).getId();
//				acc
				if(buyAmountField.getText() == null || buyAmountField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter how many shares you want to buy!");
				}else {
					int buyAmount = Integer.valueOf(buyAmountField.getText());
					//check current sharesinfo
					//add a new record to sharesinfo
					atm.buySharesDB(secureId, stockId, buyAmount);
					
					//boughtList add one 
					ArrayList<String> secureStockId= atm.getStockIdFromSecureDB(secureId);
					Set<String> stockSet = new HashSet<>();
					stockSet.addAll(secureStockId);
					stocks = atm.getStocksDB();
					String[] stockStr = new String[stocks.size()];
					for(int i = 0 ; i < stocks.size(); i++) {
						Stock curStock = stocks.get(i);
						if(stockSet.contains(curStock.getId())){
							String stockInfo = curStock.getId() + "   " + curStock.getStockName() + "   " + curStock.getPrice() + "   " + buyAmount;					
							stockStr[i] = stockInfo;
						}	
					}
					boughtList.setListData(stockStr);
					//minus account balance
					
					
				}
				
				
				
				

			}
			
		});
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), true, 2, curAccIndex);
				
				frame.dispose();
			}
			
		});
		
		stockList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				curBuyListId = stockList.getSelectedIndex();
				//String stockId = stocks.get(curBuyListId).getId();
				
			}
			
		});
		
		
		frame.setTitle("Stock Market");
		frame.setSize(500, 600);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	
	
}

