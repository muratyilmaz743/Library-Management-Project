package org.Library.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchClass {

    public static ObservableList<ObservableList> cleanAndReset(ResultSet rst, String[] columns, TableView table, ObservableList<ObservableList> data) throws SQLException {
        for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++)
            table.getColumns().clear();
        for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(columns[i]);
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty( param.getValue().get(j).toString()));

            table.getColumns().addAll(col);
        }
        while(rst.next()){
            //Iterate Row

            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rst.getMetaData().getColumnCount(); i++){
                //Iterate Column
                row.add(rst.getString(i));
            }
            data.add(row);
        }
        return data;

    }


}
