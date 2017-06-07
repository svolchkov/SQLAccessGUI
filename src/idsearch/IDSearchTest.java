/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idsearch;

import javax.swing.JFrame;

/**
 *
 * @author sergeyv
 */
public class IDSearchTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLUtil.createDB();
        SQLUtil.createTable();
        JFrame frame = new Gui();
        // int key = 10000000;
        // int key2 = 10000000;
        // int updateSuccessful = SQLUtil.updateData(key2, "Nick Dawson");
        // System.out.println(updateSuccessful);
        
//        if (insertSuccessful == 0){
//            System.out.println("ID " + key + " already exists");
//             SQLUtil.selectData(key);
//        }
        
        // System.out.println(SQLUtil.insertData(key, "Dan Edge"));
        // TODO code application logic here
    }
    
}
