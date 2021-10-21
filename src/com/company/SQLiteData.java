package com.company;

import java.sql.*;

public class SQLiteData {
    Connection con;

    public SQLiteData() throws Exception {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:daneosobowe.db");
    }

    public void zalozTabele() throws SQLException {
        Statement stm = con.createStatement();
        stm.executeUpdate("drop table if exists daneosobowe;");
        stm.executeUpdate("create table daneosobowe (nazwisko, imie, wiek, pesel);");
        stm.close();
    }

    public void aktualizujStrukture() throws SQLException {
        Statement stm = con.createStatement();
        stm.executeUpdate("alter table daneosobowe add column wiek;");
        stm.close();
    }


    public void wstawDane(String nazwisko, String imie,  String wiek, String pesel) throws SQLException {
        PreparedStatement ps = con.prepareStatement("insert into daneosobowe values(?, ?, ?, ?);");
        ps.setString(1, nazwisko);
        ps.setString(2, imie);
        ps.setString(3, wiek);
        ps.setString(4, pesel);
        ps.addBatch();
        ps.executeBatch();
    }

    public String odczytajDane(int numer) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select nazwisko, imie, wiek, pesel from daneosobowe limit 1 offset " + numer + ";");
        String wynik =" ; ; ";
        while(rs.next()){
            wynik = rs.getString("nazwisko") + ";" +
                    rs.getString("imie") + ";" +
                    rs.getString("wiek") + ";" +
                    rs.getString("pesel");
        }
        return wynik;
    }
    public int ileDanych() throws SQLException {
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select count() from daneosobowe;");
        int wynik = 0;
        while(rs.next()){
            wynik = rs.getInt(1);
        }
        return wynik;
    }

    public void zmienDane(String nazwisko, String imie, String wiek, String pesel) throws SQLException {
        PreparedStatement ps = con.prepareStatement("update daneosobowe set nazwisko=?, imie=?, wiek=? where pesel=?;");
        ps.setString(1, nazwisko);
        ps.setString(2, imie);
        ps.setString(3, wiek);
        ps.setString(4, pesel);
        ps.addBatch();
        ps.executeBatch();
    }

    public void usunDane(String pesel) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from daneosobowe where pesel=?;");
        ps.setString(1, pesel);
        ps.addBatch();
        ps.executeBatch();
    }
}
