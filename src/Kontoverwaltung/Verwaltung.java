package Kontoverwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class Verwaltung {

    private static ArrayList<Konto> Konten = new ArrayList<Konto>();

    public static void main(String[] args) {

        // Konten auslesen
        try {
            FileReader fr = new FileReader("C:\\Users\\Patrick\\Documents\\AWE Java\\Konten.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line;
            // Jedes Konto durchlaufen
            while((line = reader.readLine()) != null) {
                String[] lines = line.split(";");

                // Definieren wir die Attribute
                String Kontonummer = lines[0];
                String Inhaber = lines[1];
                double Saldo = Double.parseDouble(lines[2]);
                LocalDate Eroeffnungsdatum = LocalDate.parse(lines[3]);

                // Kontotyp bestimmen
                switch(lines[lines.length-1]) {
                    case "Girokonto":
                        int Dispo = lines.length > 4 ? Integer.parseInt(lines[4]) : 0;
                        int Dispozins = lines.length > 5 ? Integer.parseInt(lines[5]) : 0;
                        Konten.add(new Girokonto(Kontonummer, Inhaber, Saldo, Eroeffnungsdatum, Dispo, Dispozins));
                        break;
                    case "Sparkonto":
                        int Sparzins = lines.length > 4 ? Integer.parseInt(lines[4]) : 0;
                        LocalDate LetzterZins = lines.length > 5 ? (!lines[5].isEmpty() ? LocalDate.parse(lines[5]) : null) : null;
                        Konten.add(new Sparkonto(Kontonummer, Inhaber, Saldo, Eroeffnungsdatum, Sparzins, LetzterZins));
                        break;
                    default:
                        break;
                }
            }
            reader.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
        // Testweise Konten erstellen und einfügen
        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-

        //Girokonto konto1 = new Girokonto("Patrick Krause", 50.1389, 1000.0);
        Girokonto konto1 = (Girokonto)Konten.get(0);
        konto1.Auszahlung(20);

        Sparkonto konto2 = new Sparkonto("Max Mustermann", 112.66, 1.2);
        // Format yyyMMdd
        konto2.setEroeffnungsdatum(LocalDate.of(2018,5,10));

        Konten.add(konto2);

        //konto2.berechneZinsen();
        System.out.println("neuer Kontostand nach Zinsen: " + konto2.getSaldo() + "€");

        konto2.ueberweisung(konto1, 30);

        konto2.kontoauszug();

        //File file = new File("C:\\Users\\Patrick\\Documents\\AWE Java\\Konten.txt");
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Patrick\\Documents\\AWE Java\\Konten.txt");

            Iterator<Konto> ite = Konten.iterator();
            while(ite.hasNext()){
                writer.write(ite.next().toString());
            }

            writer.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
