package dao;

import entity.Contact;
import util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoImpl {

    private DBconnection connector;
    private Connection connection;
    private Contact contact;

    public List<Contact> findAll () {
        List<Contact> contactList = new ArrayList();
        try {
            Statement st = this.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contacts");
            while (rs.next()){
                Contact tmp = new Contact();
                tmp.setId(rs.getInt("ID"));
                tmp.setName(rs.getString("NAME"));
                tmp.setSurname(rs.getString("SURNAME"));
                tmp.setPhoneNumber(rs.getString("PHONE_NUMBER"));

                contactList.add(tmp);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return contactList;
    }

    public void insert (Contact contact) {
        try {
            String sql = "insert into contacts (NAME, SURNAME, PHONE_NUMBER) values (?,?,?)";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, contact.getName());
            st.setString(2, contact.getSurname());
            st.setString(3, contact.getPhoneNumber());
            st.execute();
            st.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update (Contact contact){
        try {
            String sql = "update contacts set NAME = ?, SURNAME = ?, PHONE_NUMBER = ? where ID = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, contact.getName());
            st.setString(2, contact.getSurname());
            st.setString(3, contact.getPhoneNumber());
            st.setString(4, String.valueOf(contact.getId()));
            st.execute();
            st.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete (Contact contact) {
        try {
            String sql = "delete from contacts where ID = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, String.valueOf(contact.getId()));
            st.execute();
            st.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private DBconnection getConnector() {
        if(this.connector == null)
            this.connector = new DBconnection();
        return connector;
    }

    private Connection getConnection() {
        if(this.connection == null)
            this.connection = this.getConnector().connect();
        return connection;
    }

}