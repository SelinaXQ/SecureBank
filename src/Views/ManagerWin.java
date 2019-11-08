package Views;

import Models.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class ManagerWin {
	private JFrame frame;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbc;

	private JButton findInfo, viewTrans, viewTransByID, deleteStockButton, addStockButton;
	private JLabel headlineL1, headlineL2, lb, lb1, stockIdLabel, stockCompanyLabel, stockPriceLabel;
	private JTextField customerTF, tf1, stockIdField, stockCompanyField, stockPriceField;
	private JList<String> cList;
	private JList<String> sList;
	private JPanel p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
	private JTabbedPane tabbedPane;

	private ArrayList<CustomerID> customers;
	private ArrayList<Stock> stocks;
	private String welcomeInfo;

	private SharedListSelectionHandler listener1;
	private FindCustomerListener listener2;
	private FindTransactionListener listener3;
	private StockListSelectionHanlder listener4;

	private ATM atm;

	public ManagerWin(ATM atm, ManagerID mId) {
		this.atm = atm;
		welcomeInfo = "Hello " + mId.getUserName() + "!";
		customers = atm.getCustomersByDB();
		stocks = atm.getStocksDB();
		setManagerWin();
		initManagerWin();
	}

	private void setManagerWin() {
		frame = new JFrame();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		gbLayout = new GridBagLayout();
		gbc = new GridBagConstraints();

		listener1 = new SharedListSelectionHandler();
		listener2 = new FindCustomerListener();
		listener3 = new FindTransactionListener();
		listener4 = new StockListSelectionHanlder();

		headlineL1 = new JLabel(welcomeInfo);
		headlineL2 = new JLabel("All customers: ");

		findInfo = new JButton("Find ");
		viewTransByID = new JButton("View his transaction");
		viewTrans = new JButton("Today's transaction");

		customerTF = new JTextField(15);

		String[] customerStr = new String[customers.size()];
		if (stocks != null) {
			String[] stockStr = new String[stocks.size()];
			sList = new JList<String>(stockStr);
			sList.addListSelectionListener(listener4);
		}

		for (int j = 0; j < customers.size(); j++) {
			CustomerID cId = customers.get(j);
			customerStr[j] = cId.getUserName() + "    " + cId.getName() + "    " + cId.getIndex();
		}

		cList = new JList<String>(customerStr);


		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p8 = new JPanel();
		p7 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p11 = new JPanel();
		p12 = new JPanel();

		p = (JPanel) frame.getContentPane();
	}

	private void initManagerWin() {
		frame.setTitle("ATM - Manager");
		frame.setSize(500, 600);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		p.add(tabbedPane);

		initPanel1();
		initPanel2();

		tabbedPane.addTab("Transaction & Info", p4);
		tabbedPane.addTab("Stocks", p5);

	}

	private void initPanel2() {

		deleteStockButton = new JButton("Delete this Stock");
		addStockButton = new JButton("Add this Stock");
		tf1 = new JTextField(25);
		stockIdField = new JTextField(15);
		stockCompanyField = new JTextField(15);
		stockPriceField = new JTextField(15);
		lb = new JLabel("Stocks market: ");
		lb1 = new JLabel("Create a new stock:  ");
		stockIdLabel = new JLabel("Stock 's ID: ");
		stockCompanyLabel = new JLabel("Company/Name: ");
		stockPriceLabel = new JLabel("Price/per: ");

		p5.setLayout(new GridLayout(10, 1));
		p5.add(p6);
		p5.add(lb);
		p5.add(p12);
		p5.add(p7);
		p5.add(lb1);
		p5.add(p8);
		p5.add(p9);
		p5.add(p10);
		p5.add(p11);
		p5.add(addStockButton);

		p7.add(tf1);
		p7.add(deleteStockButton);

		p9.add(stockIdLabel);
		p9.add(stockIdField);

		p10.add(stockCompanyLabel);
		p10.add(stockCompanyField);

		p11.add(stockPriceLabel);
		p11.add(stockPriceField);

		addStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String stockId = stockIdField.getText();
//				System.out.println("stockId:" + stockId + "!");
				String stockCompany = stockCompanyField.getText();
				String stockPriceStr = stockPriceField.getText();
//				Double price = Double.valueOf();

				if (stockId == null || stockId.equals("") || stockCompany == null || stockCompany.equals("")
						|| stockPriceStr == null || stockPriceStr.equals("")) {
//					System.out.println("null");
					JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Message",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Double price = Double.valueOf(stockPriceStr);
					Stock newStock = new Stock(stockId, stockCompany, price);

					atm.addStockDB(newStock);

					JOptionPane.showMessageDialog(null, "Succeed!");

					stockIdField.setText("");
					stockCompanyField.setText("");
					stockPriceField.setText("");
				}

			}

		});

		deleteStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// delete a stock by tf1 's text
				//

			}

		});

	}

	private void initPanel1() {
		p4.add(headlineL1);
		p4.add(p1);
		p4.add(customerTF);
		p4.add(findInfo);
		p4.add(viewTransByID);
		p4.add(viewTrans);
		p4.add(p2);
		p4.add(headlineL2);
		p4.add(p3);
		p4.add(cList);

		p4.setLayout(gbLayout);

		cList.addListSelectionListener(listener1);
		findInfo.addActionListener(listener2);
		viewTrans.addActionListener(listener3);
		viewTransByID.addActionListener(listener3);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(headlineL1, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p1, gbc);

		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(customerTF, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(findInfo, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(viewTransByID, gbc);

		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(viewTrans, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p2, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(headlineL2, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbLayout.setConstraints(p3, gbc);

		gbc.gridwidth = 0;
		gbc.gridheight = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbLayout.setConstraints(cList, gbc);

	}

	class FindCustomerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = false;
			for (int i = 0; i < customers.size(); i++) {
				CustomerID cId = customers.get(i);
				if (cId.getName().equals(customerTF.getText())) {
					flag = true;
					new CustomerWin(atm, atm.getCustomerByDB(cId.getIndex()), false, 0, 0);
				}
			}
			if (flag == false) {
				JOptionPane.showMessageDialog(null, "No such a customer!", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class FindTransactionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			if (object == viewTrans) {
				new ViewTransactionWin(atm, -1);
			} else if (object == viewTransByID) {
				int index = cList.getSelectedIndex();
				new ViewTransactionWin(atm, index + 1);
			}
		}
	}

	class SharedListSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int j = cList.getSelectedIndex();
			customerTF.setText(customers.get(j).getName());
		}
	}

	class StockListSelectionHanlder implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int j = sList.getSelectedIndex();
			tf1.setText(stocks.get(j).getStockName());
		}
	}

}
