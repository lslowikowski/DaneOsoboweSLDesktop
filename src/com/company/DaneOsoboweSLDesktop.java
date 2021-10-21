package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DaneOsoboweSLDesktop {

        private static JFrame frmRamka;
        private static JPanel paPanel;
        private static JLabel lblNazwisko;
        private static JTextField txtNazwisko;
        private static JLabel lblImie;
        private static JTextField txtImie;
        private static JLabel lblPesel;
        private static JTextField txtPesel;
        private static JButton btnZapisz;
        private static JButton btnPoprzedni;
        private static JButton btnNastepny;
        private static int licznikNacisniec = 0;
        private static JButton btnZmien;
        private static JButton btnUsun;
        private static JLabel lblWiek;
        private static JTextField txtWiek;

        public static void main(String[] args) throws Exception {
            SQLiteData sqliteData = new SQLiteData();
            //sqliteData.zalozTabele();
            //sqliteData.aktualizujStrukture();
            frmRamka = new JFrame("Dane osobowe");
            frmRamka.setLocation(200,100);
            frmRamka.setSize(600,400);
            frmRamka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            paPanel = new JPanel();
            paPanel.setLayout(new GridBagLayout());

            //kontrolki nazwiska
            lblNazwisko = new JLabel("Nazwisko:", SwingConstants.RIGHT);
            paPanel.add(lblNazwisko, new MyGridBagConstraints(0,0, 1,1));
            txtNazwisko = new JTextField(SwingConstants.LEFT);
            paPanel.add(txtNazwisko, new MyGridBagConstraints(1,0,4, 1));

            //kontrolki imienia
            lblImie = new JLabel("Imię:", SwingConstants.RIGHT);
            paPanel.add(lblImie, new MyGridBagConstraints(0, 1,1,1));
            txtImie = new JTextField(SwingConstants.LEFT);
            paPanel.add(txtImie, new MyGridBagConstraints(1, 1,4,1));

            //kontrolki wiek
            lblWiek = new JLabel("Wiek:", SwingConstants.RIGHT);
            paPanel.add(lblWiek, new MyGridBagConstraints(0, 2,1,1));
            txtWiek = new JTextField(SwingConstants.LEFT);
            paPanel.add(txtWiek, new MyGridBagConstraints(1, 2,4,1));

            //kontrolki pesel
            lblPesel = new JLabel("Pesel:", SwingConstants.RIGHT);
            paPanel.add(lblPesel, new MyGridBagConstraints(0, 3,1,1));
            txtPesel = new JTextField(SwingConstants.LEFT);
            paPanel.add(txtPesel, new MyGridBagConstraints(1, 3,4,1));

            //wypełniamy pierwszym rekordem
            try {
                String odczytaneDane = sqliteData.odczytajDane(0);
                String[] tablicaDanych = odczytaneDane.split(";");
                txtNazwisko.setText(tablicaDanych[0]);
                txtImie.setText(tablicaDanych[1]);
                txtWiek.setText(tablicaDanych[2]);
                txtPesel.setText(tablicaDanych[3]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //kontrolki przycisków
            btnZapisz = new JButton("Zapisz");
            paPanel.add(btnZapisz, new MyGridBagConstraints(0,4,1,1 ));
            btnZapisz.addActionListener((ActionEvent e)->{
                try {
                    sqliteData.wstawDane(txtNazwisko.getText(), txtImie.getText(), txtWiek.getText(), txtPesel.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String zapisywaneDane = txtNazwisko.getText() + ";" + txtImie.getText() + ";" + txtWiek.getText() +";" + txtPesel.getText();
                txtNazwisko.setText("");
                txtImie.setText("");
                txtWiek.setText("");
                txtPesel.setText("");
                JOptionPane.showMessageDialog( null,
                        zapisywaneDane, "Zapisano dane osobowe", JOptionPane.INFORMATION_MESSAGE );
            });

            btnPoprzedni = new JButton("Poprzedni");
            paPanel.add(btnPoprzedni, new MyGridBagConstraints(1,4,1,1 ));
            btnPoprzedni.addActionListener((ActionEvent e)->{
                licznikNacisniec --;
                if(licznikNacisniec < 0) licznikNacisniec = 0;
                try {
                    String odczytaneDane = sqliteData.odczytajDane(licznikNacisniec);
                    String[] tablicaDanych = odczytaneDane.split(";");
                    txtNazwisko.setText(tablicaDanych[0]);
                    txtImie.setText(tablicaDanych[1]);
                    txtWiek.setText(tablicaDanych[2]);
                    txtPesel.setText(tablicaDanych[3]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            });

            btnNastepny = new JButton("Następny");
            paPanel.add(btnNastepny, new MyGridBagConstraints(2,4,1,1 ));
            btnNastepny.addActionListener((ActionEvent e)->{
                licznikNacisniec ++;
                try {
                    int ileDanych = sqliteData.ileDanych();
                    if(ileDanych>licznikNacisniec) {
                        String odczytaneDane = sqliteData.odczytajDane(licznikNacisniec);
                        String[] tablicaDanych = odczytaneDane.split(";");
                        txtNazwisko.setText(tablicaDanych[0]);
                        txtImie.setText(tablicaDanych[1]);
                        txtWiek.setText(tablicaDanych[2]);
                        txtPesel.setText(tablicaDanych[3]);
                    } else{
                        licznikNacisniec = ileDanych-1;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            btnZmien = new JButton("Zmień");
            paPanel.add(btnZmien, new MyGridBagConstraints(3,4,1,1 ));
            btnZmien.addActionListener((ActionEvent e)->{
                try {
                    sqliteData.zmienDane(txtNazwisko.getText(), txtImie.getText(), txtWiek.getText(), txtPesel.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            });

            btnUsun = new JButton("Usuń");
            paPanel.add(btnUsun, new MyGridBagConstraints(4,4,1,1 ));
            btnUsun.addActionListener((ActionEvent e)->{
                try {
                    sqliteData.usunDane(txtPesel.getText());
                    licznikNacisniec--;
                    if(licznikNacisniec<0) licznikNacisniec=0;
                    String odczytaneDane = sqliteData.odczytajDane(licznikNacisniec);
                    String[] tablicaDanych = odczytaneDane.split(";");
                    txtNazwisko.setText(tablicaDanych[0]);
                    txtImie.setText(tablicaDanych[1]);
                    txtWiek.setText(tablicaDanych[2]);
                    txtPesel.setText(tablicaDanych[3]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            });

            frmRamka.add(paPanel);
            frmRamka.pack();
            frmRamka.setVisible(true);

        }
}
