package com.exercise1.brunadonatoni_comp228lab5;
import java.sql.*;
import java.util.Vector;
import javafx.stage.Stage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;


public class DisplayTable extends JFrame {
    private Statement statement;
    private ResultSet results;
    private DefaultTableModel tableModel;
    private ResultSetMetaData metaData;
    private Vector<Vector<String>> rows;
    private Vector<String> columns;
    private JTable table;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private Connection connection;



          public void displayPlayers(Connection conn) throws SQLException {

            //SQL Connection
//            try {
//                System.out.println("> Start Program ...");
//                Class.forName("oracle.jdbc.driver.OracleDriver");
//                System.out.println("> Driver Loaded successfully.");
//                //use when in person in Centennial
////            conn = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD", "COMP228_M22_sy_59", "password");
//                //use when not in centennial
//                conn =DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD","COMP228_M22_sy_59","password");
//                System.out.println("Database connected successfully.");
//            } catch (Exception e) {
//                System.out.printf("Error connecting to DB: %s%n", e);
//
//            }

            rows = new Vector<>();
            columns = new Vector<>();

            tableModel = new DefaultTableModel();
            table = new JTable(tableModel);
            scrollPane = new JScrollPane(table);//ScrollPane
            //
            mainPanel = new JPanel();
            setSize(950,300);
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add("Center", scrollPane);

            mainPanel.setBackground(Color.white);
            table.getParent().setBackground(Color.lightGray);
            getContentPane().add(mainPanel);
            setVisible(true);

            statement = conn.createStatement();
            results = statement.executeQuery(
                    "SELECT PLAYER_ID AS ID, " +
                            "FIRST_NAME || ' ' || LAST_NAME AS NAME, " +
                            "ADDRESS, " +
                            "POSTAL_CODE, " +
                            "PHONE_NUMBER, " +
                            "GAME_TITLE, " +
                            "SCORE, " +
                            "PLAYING_DATE " +
                            "FROM PLAYER NATURAL JOIN PLAYERANDGAME NATURAL JOIN GAME"
            );
            metaData = results.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.addElement(metaData.getColumnName(i));
            }
            int row = 0;
            while(results.next())
            {
                //to store the current row
                Vector<String> vRow = new Vector<>();


                for (int i = 1; i <= metaData.getColumnCount(); i++)
                {
                    Object columnValue = results.getObject(i);
                    if (columnValue == null){
                        columnValue = "Null";
                        vRow.addElement(columnValue.toString());
                    } else {
                        vRow.addElement(columnValue.toString());
                    }
                }
                row += 1;
                rows.addElement(vRow);
            }

            tableModel.setDataVector(rows, columns);
            results.close();
          }

        public static void main(String[] args) {
            DisplayTable tc = new DisplayTable();
            tc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }