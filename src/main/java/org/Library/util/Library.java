package org.Library.util;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.sql.Statement;

public class Library  {
        public static void rezerveBook(TableView srchTable, TextField reservationInput, Label resLabel, Statement statement) {
                Object book = srchTable.getSelectionModel().getSelectedItem();
                String[] list = book.toString().split( ",");
                for (String s: list) {
                        System.out.println(s);
                }
                String ISBN = list[1].trim();
                String avaliability = list[list.length-2].trim();
                String resNumber = reservationInput.getText();

                if (!avaliability.equals("0")){
                        try {
                                String SQL = "UPDATE library.books SET isAvaliable = '0' WHERE (`ISBN` = "+ ISBN +")";
                                String SQL2 = "INSERT INTO library.booklend (`ISBN`,`memberID`) values ('"+ISBN + "', '"+ resNumber+"')";
                                System.out.println(SQL2);
                                statement.executeUpdate(SQL);
                                statement.executeUpdate(SQL2);

                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                }
                else resLabel.setText("This book is already Reserved!");

        }

        public static void returning(String list,Statement statement) {
                try {
                        String SQL = "DELETE FROM library.booklend WHERE (`ISBN` = "+list +")";
                        String SQL2 = "UPDATE library.books SET isAvaliable = 1 WHERE (`ISBN` = "+ list +")";

                        statement.executeUpdate(SQL);
                        statement.executeUpdate(SQL2);

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }



}
