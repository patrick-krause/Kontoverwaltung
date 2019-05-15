package Kontoverwaltung;

import java.time.LocalDate;

public class Girokonto extends Konto{

    private int Dispo; // Der Betrag bis zu dem mann ins Minus gehen kann
    private static int Dispozins; // Zinssatz fürs Minus


    // Konstruktor mit Variablen
    public Girokonto(String pInhaber, double pSaldo, double pDispo) {
        super(pInhaber, pSaldo);
        this.setDispo(pDispo);
    }

    // Konstruktor zum auslesen aus der Datei
    public Girokonto(String pKontonummer, String pInhaber, double pSaldo, LocalDate pDatum, double pDispo, double pDispozins) {
        super(pKontonummer, pInhaber, pSaldo, pDatum);
        setDispozins(pDispozins);
        setDispo(pDispo);
    }


    // Führt eine Barauszahlung durch
    // return 0 wenn Dispo überschritten ist, sonst ausgezahlter Betrag
    public boolean Auszahlung(double pBetrag) {

        int kontostand = (int)Math.round(this.Saldo - pBetrag*100);
        if(kontostand < -this.Dispo) {
            System.out.println("Der Betrag kann nicht ausgezahlt werden!");
            return false; // Dispo wurde überschritten
        }
        else
        {
            System.out.println("Auszahlung von " + pBetrag + "€");
            this.Saldo = kontostand;
            System.out.println("Neuer Kontostand: " + getSaldo() + "€");
            return true;
        }

    }

    // Führt eine Bareinzahlung durch und gibt den neuen Kontostand zurück
    public void Einzahlung(double pBetrag) {

        this.Saldo += Math.round(pBetrag*100);
        System.out.println("Einzahlung von " + pBetrag + "€");
        System.out.println("Neuer Kontostand: " + getSaldo() + "€");
    }

    // Führt eine Überweisung durch
    public void ueberweisung(Konto pReceiver, double pValue) {
        int kontostand = this.Saldo - (int)Math.round(pValue*100);
        if(kontostand < -this.Dispo)
            System.out.println("Transaktion kann nicht durchgeführt werden, da der Saldo zu gering ist.");

        Transaktion lTrans = new Transaktion(this, pReceiver, (int)Math.round(pValue*100));
        this.Transaktionen.add(lTrans);
        lTrans.Commit();
    }

    public double getDispo() {
        return (double)Dispo/100;
    }

    public void setDispo(double dispo) {
        Dispo = (int)Math.round(dispo*100);
    }

    public double getDispozins() {
        return (double)Dispozins/100;
    }

    public void setDispozins(double dispozins) {
        Dispozins = (int)Math.round(dispozins*100);
    }

    // Zum speichern des Objekts
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(';');
        builder.append(this.Dispo);
        builder.append(';');
        builder.append(Girokonto.Dispozins);
        builder.append(';');
        builder.append("Girokonto");
        builder.append(System.lineSeparator());
        return builder.toString();
    }

}
