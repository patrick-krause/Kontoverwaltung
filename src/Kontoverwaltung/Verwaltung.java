package Kontoverwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.util.Scanner;

public class Verwaltung {

    // Liste aller Konten zur Laufzeit
    private static ArrayList<Konto> Konten = new ArrayList<Konto>();
    // Name der csv-Datei, in der alle Konten gespeichert werden
    private static final String FILE_NAME = "C:\\Users\\Patrick\\Documents\\AWE Java\\Konten.txt";

    public static void main(String[] args) {

        // Konten auslesen wenn die Datei vorhanden ist
        File file = new File(FILE_NAME);
        if(file.exists() && file.isFile()) {
            load();
        }

        // Konsole leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        // Willkommensnachricht
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("+            Kontoverwaltung            +");
        System.out.println("+           by Patrick Krause           +");
        System.out.println("+              Version 1.0              +");
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println();

        // Menü aufbauen
       showMenu();






        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
        // Testweise Konten erstellen und einfügen
        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-

//        //Girokonto konto1 = new Girokonto("Patrick Krause", 50.1389, 1000.0);
//        Girokonto konto1 = (Girokonto)Konten.get(0);
//        konto1.Auszahlung(20);
//
//        Sparkonto konto2 = new Sparkonto("Max Mustermann", 112.66, 1.2);
//        // Format yyyMMdd
//        konto2.setEroeffnungsdatum(LocalDate.of(2018,5,10));
//
//        Konten.add(konto2);
//
//        //konto2.berechneZinsen();
//        System.out.println("neuer Kontostand nach Zinsen: " + konto2.getSaldo() + "€");
//
//        konto2.ueberweisung(konto1, 30);
//
//        konto2.kontoauszug();

        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
        // Ende des Tests
        // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-

        // Konten speichern
        save();
    }

    // Lädt alle Konten aus einer csv-Datei
    private static void load(){

        try {
            FileReader fr = new FileReader(FILE_NAME);
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

    }

    // Speichert alle Konten in einer csv-Datei
    private static void save(){

        try {
            FileWriter writer = new FileWriter(FILE_NAME);

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


    private static void showMenu(){
        System.out.println("1 Konten ansehen");
        System.out.println("2 Neues Konto anlegen");
        System.out.println("3 Konto bearbeiten");
        System.out.println("4 Konto löschen");
        System.out.println();
        System.out.println("5 Programm beenden");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        switch(s) {

            case("1"):
            case("K"):
            case("k"):
                System.out.println("Kontoliste anzeigen");
                showList();
                break;
            case("2"):
            case("N"):
            case("n"):
                System.out.println("Neues Konto anlegen");
                showNewDialog();
                break;
            case("3"):
            case("B"):
            case("b"):
                System.out.println("Konto bearbeiten");
               // showEditDialog();
                break;
            case("4"):
            case("L"):
            case("l"):
                System.out.println("Konto löschen");
               // showDeleteDialog();
                break;
            case("5"):
            case("E"):
            case("e"):
                System.exit(0);
            default:
                System.out.println("Eingabe nicht erkannt!");
                System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
                String e = in.nextLine();
                showMenu();
                break;
        }
    }

    private static void showList(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        if(Konten.isEmpty()){
            System.out.println("Keine Konten vorhanden!");
            System.out.println("Drücken Sie 'N' um ein neues Konto anzulegen.");

            // Eingaben auswerten
            Scanner in = new Scanner(System.in);
            String e = in.nextLine();
            if(e == "N" || e == "n"){
                showNewDialog();
            }
            else{
                showMenu();
            }
        }
        else{
            System.out.println("Kontoliste:");
            for (Konto lKonto : Konten) {
                System.out.println(lKonto.Kontonummer + " [" + lKonto.Inhaber + "]");
            }
        }
    }

    private static void showNewDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Kontotyp auswählen:");
        System.out.println("1 Girokonto");
        System.out.println("2 Sparkonto");
    }

}
