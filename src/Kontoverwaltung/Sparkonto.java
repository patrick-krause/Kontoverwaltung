package Kontoverwaltung;

import java.time.LocalDate;

public class Sparkonto extends Konto {

    private int Sparzins; // Zinssatz
    private LocalDate LetzterZins; // Datum der letzten Zinsberechnung

    // Konstruktor mit Parametern
    public Sparkonto(String pInhaber, double pSaldo, double pSparzins) {
        super(pInhaber, pSaldo);
        this.setSparzins(pSparzins);
    }

    // Konstruktor zum auslesen aus der Datei
    public Sparkonto(String pKontonummer, String pInhaber, double pSaldo, LocalDate pDatum, double pSparzins, LocalDate pLetzterZins) {
        super(pKontonummer, pInhaber, pSaldo, pDatum);
        setSparzins(pSparzins);
        this.LetzterZins = pLetzterZins;
    }


    public int berechneZinsen() {

        // Wenn wir noch nie Zinsen ausgezahlt haben, setzen wir das Datum
        if(LetzterZins == null) {
            LetzterZins = getEroeffnungsdatum();
        }

        // Wenn wir einen negativen Saldo haben, brauchen wir nichts zu tun
        if(getSaldo() <= 0)
            return 0;

        // Berechnet die Differenz zwischen dem letzten Zinsdatum und jetzt
        int diffYears = Math.abs(LetzterZins.getYear() - LocalDate.now().getYear());
        int diffMonths = diffYears * 12 + Math.abs(LetzterZins.getMonthValue() - LocalDate.now().getMonthValue());

        // Die berechneten Zinsen, die wir hinterher ausgeben
        int zinsbetrag = 0;

        // Nun berechnen wir die Zinsen für jeden Monat
        for(int i = 0; i < diffMonths; i++) {
            int zins = round(this.Saldo * this.Sparzins/100);
            zinsbetrag += zins;
            setSaldo( getSaldo() + zins );
        }

        // Und geben den Zinsbetrag aus
        return zinsbetrag;
    }


    public double getSparzins() {
        return (double)Sparzins/100;
    }

    public void setSparzins(double sparzins) {
        Sparzins = (int)Math.round(sparzins*100);
    }

    // Zum speichern des Objekts
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(';');
        builder.append(this.Sparzins);
        builder.append(';');
        // Wir müssen hier auf null prüfen, weil die ToString-Methode sonst "null" ausgeben würde
        builder.append(this.LetzterZins != null ? this.LetzterZins : "");
        builder.append(';');
        builder.append("Sparkonto");
        builder.append(System.lineSeparator());
        return builder.toString();
    }

}
