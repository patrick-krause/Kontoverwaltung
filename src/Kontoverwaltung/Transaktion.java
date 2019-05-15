package Kontoverwaltung;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaktion {

    protected LocalDateTime Date;
    protected Konto Receiver;
    protected Konto Sender;
    protected int Value;

    public Transaktion(Konto pSender, Konto pReceiver, int pValue) {
        this.Sender = pSender;
        this.Receiver = pReceiver;
        this.Value = pValue;
    }

    public void Commit() {
        this.Date = LocalDateTime.now();
        Sender.Saldo = Sender.Saldo - Value;
        Receiver.Saldo = Receiver.Saldo + Value;
        System.out.println("Überweisung wurde um " + getDate() + " ausgeführt.");
        System.out.println("Neuer Kontostand: " + Sender.getSaldo() + "€");
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return Date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public Konto getReceiver() {
        return Receiver;
    }

    public void setReceiver(Konto receiver) {
        Receiver = receiver;
    }

    public Konto getSender() {
        return Sender;
    }

    public void setSender(Konto sender) {
        Sender = sender;
    }

    public double getValue() {
        return ((double)Value)/100;
    }

    public void setValue(int value) {
        Value = value;
    }

}
