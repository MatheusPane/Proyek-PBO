/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controllers;

import models.Booking;
import models.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLDocumentController {

    @FXML
    private ComboBox<String> seatComboBox;
    @FXML
    private RadioButton regularRadio, ultraRadio, goldRadio;
    @FXML
    private TextField priceField, buyerNameField, quantityField, totalField, paymentField, changeField;
    @FXML
    private Button inputButton, exitButton, printButton;
    @FXML
    private TextArea outputArea;

    // Harga tiket
    private double regularPrice = 50000; // Contoh harga tiket Reguler
    private double ultraPrice = 75000;   // Contoh harga tiket Ultra XD
    private double goldPrice = 100000;    // Contoh harga tiket Gold

    @FXML
    public void initialize() {
        // Mengisi ComboBox dengan pilihan kursi
        seatComboBox.getItems().addAll("A1", "A2", "A3", "B1", "B2", "B3");
        
        // Listener untuk menangani perubahan pemilihan kursi
        seatComboBox.setOnAction(event -> updatePrice());
    }

    private void updatePrice() {
        // Menentukan harga berdasarkan jenis tiket yang dipilih
        double price = 0;
        if (regularRadio.isSelected()) {
            price = regularPrice;
        } else if (ultraRadio.isSelected()) {
            price = ultraPrice;
        } else if (goldRadio.isSelected()) {
            price = goldPrice;
        }
        priceField.setText(String.valueOf(price));
    }

    @FXML
    private void handleInput() {
        try {
            String buyerName = buyerNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double payment = Double.parseDouble(paymentField.getText());

            double total = quantity * price;
            double change = payment - total;

            // Set total dan kembalian
            totalField.setText(String.valueOf(total));
            changeField.setText(String.valueOf(change));

            // Cetak ke TextArea
            outputArea.setText("Data Pembelian:\n" +
                    "Nama Pembeli: " + buyerName + "\n" +
                    "Jumlah Beli: " + quantity + "\n" +
                    "Total Bayar: " + total + "\n" +
                    "Pembayaran: " + payment + "\n" +
                    "Kembalian: " + change + "\n");
        } catch (NumberFormatException e) {
            outputArea.setText("Input tidak valid. Masukkan angka untuk jumlah, harga, dan pembayaran.");
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handlePrint() {
        System.out.println(outputArea.getText());
    }
}
