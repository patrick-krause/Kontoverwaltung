package Kontoverwaltung;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Konto {

    protected String Kontonummer;
    protected String Inhaber;
    protected int Saldo;
    protected LocalDate Eroeffnungsdatum;
    protected ArrayList<Transaktion> Transaktionen;


    // Standardkonstruktor
    public Konto() {
        this.setEroeffnungsdatum();
        this.setKontonummer();
        System.out.println("Konto ["+ getKontonummer() +"] erstellt.");
        Transaktionen = new ArrayList<Transaktion>();
    }

    // Standardkonstruktor mit Variablen
    public Konto(String pInhaber, double pSaldo) {
        this.setInhaber(pInhaber);
        this.setKontonummer();
        this.setEroeffnungsdatum();
        // negativen Saldo dürfen wir nicht haben
        if(pSaldo < 0) {
            System.out.println("Negativer Saldo nicht möglich!");
            this.setSaldo(0);
        }
        else
            this.setSaldo(pSaldo);
        System.out.println("Konto ["+ getKontonummer() +"] mit Inhaber: " + getInhaber() + " erstellt.");
        System.out.println("Aktueller Kontostand: " + getSaldo() + "€");
        this.Transaktionen = new ArrayList<Transaktion>();
    }

    // Konstruktor zum auslesen aus der Datei
    public Konto(String pKontonummer, String pInhaber, double pSaldo, LocalDate pDatum) {
        this.Kontonummer = pKontonummer;
        this.Inhaber = pInhaber;
        setSaldo(pSaldo);
        this.Eroeffnungsdatum = pDatum;
    }


    public String getKontonummer() {
        return this.Kontonummer;
    }

    private void setKontonummer() {
        this.Kontonummer = generateKontonummer();
    }

    public String getInhaber() {
        return this.Inhaber;
    }

    public void setInhaber(String inhaber) {
        this.Inhaber = inhaber;
    }

    public double getSaldo() {
        return (double)this.Saldo/100;
    }

    public void setSaldo(double saldo) {
        this.Saldo = (int)Math.round(saldo*100);
    }

    public LocalDate getEroeffnungsdatum() {
        return this.Eroeffnungsdatum;
    }

    public void setEroeffnungsdatum(LocalDate eroeffnungsdatum) {
        this.Eroeffnungsdatum = eroeffnungsdatum;
    }

    public void setEroeffnungsdatum() {
        this.Eroeffnungsdatum = LocalDate.now();
    }


    // Generiert eine neue 10 stellige KontoNr
    public static String generateKontonummer() {

        String lResult = "";
        // 10 mal Zufallszahl
        for(int i=0; i<10; i++) {
            // Zufallszahl zwischen 0 & 9
            lResult += (int)(Math.random()*10);
        }
        return lResult;
    }

    // Gibt die Umsätze und den Kontostand als Kontoauszug aus
    public void kontoauszug() {
        System.out.println("+-------------------------------------+");
        System.out.println("+	Kontonummer: " + this.getKontonummer());
        System.out.println("+	Inhaber: " + this.getInhaber());
        System.out.println("+");
        System.out.println("+");
        // Durchlaufen wir alle Transaktionen
        for(int i = 0; i < this.Transaktionen.size(); i++){
            Transaktion lTrans = this.Transaktionen.get(i);
            System.out.println("+	Überweisung: " + lTrans.getDate());
            System.out.println("+	Empfänger  : " + lTrans.Receiver.getInhaber() + " [" + lTrans.Receiver.getKontonummer() + "]");
            System.out.println("+	Betrag     : " + lTrans.getValue());
            System.out.println("+");
        }
        System.out.println("+	Kontostand: " + this.getSaldo());
        System.out.println("+-------------------------------------+");
    }

    // Unsere eigene Rundungsmethode auf 2 Nachkommastellen
    public int round(int pValue) {

        return Math.round(pValue);
    }

    // FÜhrt eine Überweisung durch
    public void ueberweisung(Konto pReceiver, double pValue) {
        if(this.getSaldo() < 0)
            System.out.println("Transaktion kann nicht durchgeführt werden, da der Saldo zu gering ist.");

        Transaktion lTrans = new Transaktion(this, pReceiver, (int)Math.round(pValue*100));
        this.Transaktionen.add(lTrans);
        lTrans.Commit();
    }

    // Dient zum speichern des Objekts
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.Kontonummer);
        builder.append(';');
        builder.append(this.Inhaber);
        builder.append(';');
        builder.append(this.Saldo);
        builder.append(';');
        builder.append(this.Eroeffnungsdatum);
        return builder.toString();
    }

}

