package org.Library;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.Library.util.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class optionsController implements Initializable {

    @FXML
    private Label resLabel;

    @FXML
    private TextField srchAuthorText;

    @FXML
    private TextField srchTitleText;

    @FXML
    private TextField rtrnUserID;


    @FXML
    public ComboBox addLang;

    @FXML
    private TextField reservationInput;

    @FXML
    private TableView srchTable;

    @FXML
    private TableView userTable;

    @FXML
    private TableView userTable1;

    @FXML
    private TextField userName;

    @FXML
    private TextField addUserNm;


    @FXML
    private TextField addBookName;

    @FXML
    private TextField addAuthorName;

    @FXML
    private ComboBox addGenre;

    @FXML
    private TextField addDate;

    /**************Database Connection*************/

    Connection conn = null;
    Statement statement = null;
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user = "root";
    String pass = "root";

    private ObservableList<ObservableList> data;
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(url, user, pass);
            statement = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /********** Database Connection*******************/
    @FXML
    public void rezBook() {
        Library.rezerveBook(srchTable, reservationInput, resLabel , statement);
    }

    @FXML
    public void searchBook() throws SQLException {

        String[] columns = {"Title","ISBN","Author", "Genre", "Date", "State"};
        data = FXCollections.observableArrayList();
        String sql = String.valueOf(SQLbuilder());
        ResultSet rst = conn.createStatement().executeQuery(sql);
        srchTable.setItems(SearchClass.cleanAndReset(rst,columns,srchTable, data));
    }
    @FXML
    public void addUser() {
        try {
            String SQL = "INSERT INTO library.member (memberName) values ('"+addUserNm.getText()+"')";
            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addUserNm.clear();
    }
    @FXML
    public void addBook() {
        try {
            String SQL = "INSERT INTO library.books (`bookTitle`, `bookAuthor`,bookLanguage,bookGenre,publishDate) values (" +
                                                                                                    "'"+addBookName.getText()+
                                                                                                    "','"+addAuthorName.getText()+
                                                                                                    "','"+addLang.getValue()+
                                                                                                    "','"+addGenre.getValue()+
                                                                                                    "','"+addDate.getText()+"')";
            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void blockUn() {
        Object member = userTable.getSelectionModel().getSelectedItem();
        String[] list = member.toString().split( ",");
        String memberID = list[list.length-2].trim();

        Member.blocked(memberID, statement);
    }

    public void unblock() {
        Object member = userTable.getSelectionModel().getSelectedItem();
        String[] list = member.toString().split( ",");
        String memberID = list[list.length-2].trim();

        Member.unblocked(memberID, statement);
    }
    public void srchUser() throws SQLException {
        String userIDText = userName.getText();
        String[] columns = {"User Name","User ID", "Status"};
        data = FXCollections.observableArrayList();

        String sql = "SELECT memberName,memberID,status FROM library.member where memberID=" + userIDText;
        ResultSet rst = conn.createStatement().executeQuery(sql);
        //FINALLY ADDED TO TableView
        userTable.setItems(SearchClass.cleanAndReset(rst, columns,userTable,data));
    }

    public String SQLbuilder(){
        StringBuilder sql = new StringBuilder("select bookTitle, ISBN , bookAuthor, bookGenre, publishDate, isAvaliable  from books ");

        try {
            boolean added = false;

            if (!srchTitleText.getText().trim().isEmpty()) {
                sql.append(" where bookTitle = '").append(srchTitleText.getText()).append("'");
                added = true;
            }
            if (!srchAuthorText.getText().trim().isEmpty()) {
                if (added)
                    sql.append(" AND bookAuthor = '").append(srchAuthorText.getText()).append("'");

                else
                    sql.append("where bookAuthor = '").append(srchAuthorText.getText()).append("'");
            }
            sql.append(";");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(sql);

    }

    public void srchRtrnUser(){
        String userIDText = rtrnUserID.getText();
        String[] columns = {"ISBN"};
        data = FXCollections.observableArrayList();

        String sql = "SELECT ISBN FROM library.booklend where memberID=" + userIDText;

        try {
            ResultSet rst = conn.createStatement().executeQuery(sql);
            userTable1.setItems(SearchClass.cleanAndReset(rst, columns,userTable1,data));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void returnBook() {
        Object loan = userTable1.getSelectionModel().getSelectedItem();
        String list = loan.toString().replace("]", " ").replace("[", " ").trim();
        System.out.println(list);

        Library.returning(list, statement);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

