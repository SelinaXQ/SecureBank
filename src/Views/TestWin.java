package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TestWin extends JFrame{
	
	//frame 
    static JFrame f; 
      
    //lists 
    static JList b;
    static DefaultListModel model = new DefaultListModel();
  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
	    
	  //create a new frame  
        f = new JFrame("frame"); 
          
        //create a object 
        TestWin s=new TestWin(); 
        
        //create a panel 
        JPanel p =new JPanel(); 
          
        //create a new label 
        JLabel l= new JLabel("select the day of the week"); 
        
        //String array to store weekdays 
        String week[]= { "Monday","Tuesday","Wednesday", 
                         "Thursday","Friday","Saturday","Sunday"}; 
        
        
  
       
        model.addElement("Money");
        //create list 
        b= new JList(model); 
          
        JButton btn = new JButton("add");
        btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int weekSize = week.length;
//				String[] newWeek = new String[weekSize+1];
				for(int i = 0; i<5; i++) {
					model.addElement("one");
					    
					
				
				}
//				b= new JList(week); 
			}
        	
        });
        //set a selected index 
       // b.setSelectedIndex(2); 
          
        //add list to panel 
        p.add(b); 
        p.add(btn);
   
        f.add(p); 
        
        //set the size of frame 
        f.setSize(400,400); 
           
        f.show(); 
	}

}
