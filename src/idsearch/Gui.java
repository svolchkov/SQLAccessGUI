/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idsearch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author sergeyv
 */
public class Gui extends JFrame implements ActionListener {
    private JPanel textPanel;
    private JPanel buttonPanel;
    private final JFormattedTextField IDField;
    private final JTextField nameField;
    private static JLabel statusLabel;
    
    public Gui(){
        setTitle("ID Query");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        textPanel = new JPanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        textPanel.setLayout(new GridLayout(0,2,5,15));
        textPanel.add(new JLabel("  ID:"));
        
        NumberFormatter formatter = new NumberFormatter(
                              new DecimalFormat("000000000"));
        IDField = new JFormattedTextField(formatter);
        // IDField.setValue(0);
        IDField.setColumns(20);
        IDField.setToolTipText("Enter an ID (up to 9 digits long)");
        textPanel.add(IDField);
        
        textPanel.add(new JLabel("  Name:"));
        
        nameField = new JTextField();
        // IDField.setValue(0);
        nameField.setColumns(20);
        nameField.setToolTipText("Enter a name (up to 20 characters long)");
        textPanel.add(nameField);
        
        // status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        
        
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // System.out.println(IDField.getCaretPosition());
                            doIDSearch();
                            IDField.requestFocus();
                            // System.out.println(IDField.getText().length());
                            SwingUtilities.invokeLater(new Runnable(){
                                

                                @Override
                                public void run() {
                                    IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                            
                            //System.out.println(IDField.getCaretPosition());
                        }
                    
                     } //To change body of generated methods, choose Tools | Templates.
            
        );
        
        IDField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    doIDSearch();
                    statusLabel.requestFocus();
                    IDField.setText(IDField.getText());
                    IDField.requestFocus();
                    // IDField.setText(IDField.getText());
                    
                    SwingUtilities.invokeLater(new Runnable(){
                        
                        
                        @Override
                        public void run() {
                            IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                
            }
        });
        
        IDField.addFocusListener(new FocusListener() {
          public void focusGained(FocusEvent e) {
              // if (IDField.getCaretPosition() == 0){
                  SwingUtilities.invokeLater(new Runnable(){
                                

                                @Override
                                public void run() {
                                    IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
              // }
           
          }

          public void focusLost(FocusEvent e) {

          }





        });
        
//        IDField.addInputMethodListener(new InputMethodListener() {
//              
//
//            @Override
//            public void inputMethodTextChanged(InputMethodEvent event) {
//                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void caretPositionChanged(InputMethodEvent event) {
//                System.out.println("Caret postion:" + IDField.getCaretPosition()); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
        
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                             try{
                                int key = Integer.parseInt(IDField.getText());
                                if (key > 0 && key <= 999999999){
                                    String name = nameField.getText();
                                    if (name.isEmpty() || name.length() > 20){
                                        statusLabel.setText("Name must be between 1 and 20 characters");
                                    }else{
                                        int insertSuccessful = SQLUtil.insertData(key, name);
                                        if (insertSuccessful != 0){
                                            statusLabel.setText("Successfully inserted new ID & name");                                            
                                        }
                                        else{
                                            statusLabel.setText("Could not insert - key already exists");
                                        }
                                            
                                    }
                                    
                                     
                                } 
                                else
                                {
                                    statusLabel.setText("Incorrect ID format");
                                }
                                
                                
                            }catch (NumberFormatException ex){
                                statusLabel.setText("Incorrect ID format");
                            }
                       IDField.requestFocus();
                       SwingUtilities.invokeLater(new Runnable(){
                                

                                @Override
                                public void run() {
                                    IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                    }
                        } 
                    
            
        );
        
        Map firstRecord = SQLUtil.selectFirst();
        for (Object o: firstRecord.keySet()){
            if ((int) o != 0){
               // IDField.setText(String.format("%09d", o)); 
               IDField.setValue(o); //DEBUG
               // IDField.setText(o.toString());
               IDField.setText(IDField.getText()); // This is needed to achieve correct positioning of the cursor
               IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                
            }else{
                IDField.setText("000000000");
            }
            
            nameField.setText(firstRecord.get(o).toString());
            break;
        }
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try{
                                int key = Integer.parseInt(IDField.getText());
                                if (key > 0 && key <= 999999999){
                                    String name = nameField.getText();
                                    if (name.isEmpty() || name.length() > 20){
                                        statusLabel.setText("Name must be between 1 and 20 characters");
                                    }else{
                                        int updateSuccessful = SQLUtil.updateData(key, 
                                             name);
                                        if (updateSuccessful != 0){
                                            statusLabel.setText("Update successful");                                            
                                        }
                                        else{
                                            statusLabel.setText("Update unsuccessful - key does not exist");
                                        }
                                            
                                    }
                                    
                                     
                                } 
                                else
                                {
                                    statusLabel.setText("Incorrect ID format");
                                }
                                
                                
                            }catch (NumberFormatException ex){
                                statusLabel.setText("Incorrect ID format");
                            }
                        IDField.requestFocus();
                        SwingUtilities.invokeLater(new Runnable(){
                                

                                @Override
                                public void run() {
                                    IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                    }
                        } //To change body of generated methods, choose Tools | Templates.
            
        );
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try{
                                int key = Integer.parseInt(IDField.getText());
                                if (key > 0 && key <= 999999999){
                                    String name = nameField.getText();
                                    if (name.isEmpty() || name.length() > 20){
                                        statusLabel.setText("Name must be between 1 and 20 characters");
                                    }else{
                                        int deleteSuccessful = SQLUtil.deleteData(key);
                                        if (deleteSuccessful != 0){
                                            
                                            try{
                                                key = Integer.parseInt(IDField.getText());
                                                if (key > 0 && key <= 999999999){
                                                    nameField.setText(SQLUtil.selectData(key));
                                                } 



                                            }catch (NumberFormatException ex){
                                                // incorrect ID, ignore key press
                                            }
                                           if (key != -1){
                                                statusLabel.setText(" ");
                                                Map nextRecord = SQLUtil.selectNextOrPrevious(key,true);
                                                for (Object o: nextRecord.keySet()){
                                                    if ((int) o != 0){
                                                       IDField.setValue(o); 
                                                       IDField.setText(IDField.getText());
                                                       nameField.setText(nextRecord.get(o).toString());
                                                    }else{
                                                        // statusLabel.setText("At end of the table");
                                                       
                                                            statusLabel.setText(" ");
                                                            Map previousRecord = SQLUtil.selectNextOrPrevious(key,false);
                                                            for (Object obj: previousRecord.keySet()){
                                                                if ((int) obj != 0){
                                                                   IDField.setValue(obj); 
                                                                   IDField.setText(IDField.getText());
                                                                   nameField.setText(previousRecord.get(obj).toString());
                                                                }else{
                                                                    statusLabel.setText("At start of the table");
                                                                    IDField.setText("000000000");
                                                                    nameField.setText("***Not found***");
                                                                }
                                                                   

                            break;
                        }
                    
                                                    }


                                                    break;
                                                }
                                           }
                                        statusLabel.setText("Delete successful"); 
                                        }
                                        else{
                                            statusLabel.setText("Delete unsuccessful - key does not exist");
                                        }
                                            
                                    }
                                    
                                     
                                } 
                                else
                                {
                                    statusLabel.setText("Incorrect ID format");
                                }
                                
                                
                            }catch (NumberFormatException ex){
                                statusLabel.setText("Incorrect ID format");
                            }
                        IDField.requestFocus();
                        SwingUtilities.invokeLater(new Runnable(){
                                

                                @Override
                                public void run() {
                                    IDField.setCaretPosition(IDField.getText().length()); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                    }
                        } //To change body of generated methods, choose Tools | Templates.
            
        );
        
        
        buttonPanel.add(selectButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        
        
        getContentPane().add(textPanel, BorderLayout.PAGE_START);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        
        // status panel
        
        
        
        setSize(400,150);
        setLocationRelativeTo(null);
        
        IDField.addKeyListener(new KeyListener(){
        @Override
            public void keyPressed(KeyEvent e) {
                
                // System.out.println("Key pressed "+e.getKeyCode());
                // System.out.println(e.VK_KP_DOWN + e.VK_KP_UP);
               
                if (e.getKeyCode() == e.VK_DOWN){
                    int key = -1;
                    try{
                        key = Integer.parseInt(IDField.getText());
                        if (key > 0 && key <= 999999999){
                            nameField.setText(SQLUtil.selectData(key));
                        } 



                    }catch (NumberFormatException ex){
                        // incorrect ID, ignore key press
                    }
                   if (key != -1){
                        statusLabel.setText(" ");
                        Map nextRecord = SQLUtil.selectNextOrPrevious(key,true);
                        for (Object o: nextRecord.keySet()){
                            if ((int) o != 0){
                               IDField.setValue(o); 
                               IDField.setText(IDField.getText());
                               nameField.setText(nextRecord.get(o).toString());
                            }else{
                                statusLabel.setText("At end of the table");
                            }


                            break;
                        }
                   }
                   
                 }else if(e.getKeyCode() == e.VK_UP){
                     int key = -1;
                    try{
                        key = Integer.parseInt(IDField.getText());
                        if (key > 0 && key <= 999999999){
                            nameField.setText(SQLUtil.selectData(key));
                        } 



                    }catch (NumberFormatException ex){
                        // incorrect ID, ignore key press
                    }
                    if (key != -1){
                        statusLabel.setText(" ");
                        Map previousRecord = SQLUtil.selectNextOrPrevious(key,false);
                        for (Object o: previousRecord.keySet()){
                            if ((int) o != 0){
                               IDField.setValue(o);
                               IDField.setText(IDField.getText());
                               nameField.setText(previousRecord.get(o).toString());
                            }else{
                                statusLabel.setText("At start of the table");
                            }


                            break;
                        }
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            if (JOptionPane.showConfirmDialog(null, 
            "Are you sure to close this window?", "Really Closing?", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            
            System.exit(0);
                }
            }
        });
        IDField.requestFocus();
        IDField.setCaretPosition(IDField.getText().length());
        setVisible(true);
        
    }
    
    public void doIDSearch(){
                            try{
                                int key = Integer.parseInt(IDField.getText());
                                if (key > 0 && key <= 999999999){
                                    nameField.setText(SQLUtil.selectData(key));
                                    statusLabel.setText("Search completed");
                                } 
                                else
                                {
                                    statusLabel.setText("Incorrect ID format");
                                }
                                
                                
                            }catch (NumberFormatException ex){
                                statusLabel.setText("Incorrect ID format");
                            }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
