package Views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SecurityWin {

	private JFrame frame;
	
	private JLabel  securityLabel;
	private JLabel  savingLabel;
	
	private JPanel  blankPanel,p1,p2;
	private JPanel infoPanel;
	private JPanel buyPanel;
	private JPanel sellPanel;
	private JList<String> stockList;
	private JList<String> boughtList;
	
	private JLabel stockMarket;
	private JLabel boughtStock;
	
	private JButton buyButton;
	private JButton sellButton;
	
	
	
	
	public SecurityWin() {
		
		
		
		setSecurityWin();
		initSecurityWin();
		
		
		
	}
	
	private void setSecurityWin() {
		frame = new JFrame();
		securityLabel = new JLabel("Security Account: ");   //TODO  security account number
		savingLabel = new JLabel("Saving Account: "); //TODO  saving account number
		
		infoPanel = new JPanel();
		buyPanel = new JPanel();
		sellPanel = new JPanel();
		blankPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		
		stockList = new JList<>();
		boughtList = new JList<>();
		
		stockMarket = new JLabel("Stock Market");
		boughtStock = new JLabel("Your Stock");
		
		buyButton = new JButton("Buy");
		sellButton = new JButton("Sell");
		
		
	}
	
	
	private void initSecurityWin() {
		frame.setLayout(new GridLayout(4, 1));
		frame.add(blankPanel);
		frame.add(infoPanel);
		frame.add(buyPanel);
		frame.add(sellPanel);
		
		infoPanel.setLayout(new GridLayout(2, 1));
		
		infoPanel.add(p1);  
		infoPanel.add(p2);  
		p1.add(securityLabel);
		p2.add(savingLabel);
		
		buyPanel.add(stockMarket);
		buyPanel.add(stockList);
		buyPanel.add(buyButton);
		
		
		sellPanel.add(boughtStock);
		sellPanel.add(boughtList);
		sellPanel.add(sellButton);
		
		
		
		buyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		frame.setTitle("Stock Market");
		frame.setSize(500, 400);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	
	
}

