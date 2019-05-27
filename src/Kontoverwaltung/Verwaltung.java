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
    private static final String FILE_NAME = "Konten.csv";

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
        System.out.println("5 Kontoauszug");
        System.out.println("6 Überweisung");
        System.out.println("7 Einzahlung");
        System.out.println("8 Auszahlung");
        System.out.println();
        System.out.println("9 Programm beenden");

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
               showEditDialog();
                break;
            case("4"):
            case("L"):
            case("l"):
                System.out.println("Konto löschen");
               showDeleteDialog();
                break;
            case("5"):
                System.out.println("Kontoauszug");
                showKontoauszugDialog();
                break;
            case("6"):
                System.out.println("Überweisung");
                showTransactionDialog();
                break;
            case("7"):
                System.out.println("Einzahlung");
                showAuszahlungDialog(true);
                break;
            case("8"):
                System.out.println("Auszahlung");
                showAuszahlungDialog(false);
                break;
            case("9"):
            case("E"):
            case("e"):
                // Speichern und Schließen
                save();
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
            if(e.equals("N") || e.equals("n")){
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
            System.out.println();
            System.out.println();
            System.out.println("2 Neues Konto anlegen");
            System.out.println("3 Konto bearbeiten");
            System.out.println("4 Konto löschen");
            System.out.println("5 Kontoauszug");
            System.out.println("6 Überweisung");
            System.out.println("7 Einzahlung");
            System.out.println("8 Auszahlung");
            System.out.println();
            System.out.println("9 Programm beenden");

            // Eingaben auswerten
            Scanner in = new Scanner(System.in);
            String e = in.nextLine();
            switch(e) {
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
                    showEditDialog();
                    break;
                case("4"):
                case("L"):
                case("l"):
                    System.out.println("Konto löschen");
                    showDeleteDialog();
                    break;
                case("5"):
                    System.out.println("Kontoauszug");
                    showKontoauszugDialog();
                    break;
                case("6"):
                    System.out.println("Überweisung");
                    showTransactionDialog();
                    break;
                case("7"):
                    System.out.println("Einzahlung");
                    showAuszahlungDialog(true);
                    break;
                case("8"):
                    System.out.println("Auszahlung");
                    showAuszahlungDialog(false);
                    break;
                case("9"):
                case("E"):
                case("e"):
                    // Speichern und Schließen
                    save();
                    System.exit(0);
                default:
                    System.out.println("Eingabe nicht erkannt!");
                    System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
                    String s = in.nextLine();
                    showMenu();
                    break;
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

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        if(s.equals("1")){
            showNewGiroDialog();
        }
        if(s.equals("2")){
            showNewSparDialog();
        }
        else{
            System.out.println("Eingabe nicht erkannt!");
            System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
            String e = in.nextLine();
            showMenu();
        }
    }

    private static void showNewGiroDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Girokonto");
        System.out.println();
        System.out.println("Kontoinhaber angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        // Eingabe nur gültig, wenn nur Text eingegeben wurde
        while(!name.matches("[a-zA-Z ]+")) {
            System.out.println("Bitte einen gültigen Namen eingeben!");
            name = in.nextLine();
        }

        System.out.println();
        System.out.println("Saldo angeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Saldo angeben!");
            in.nextLine();
        }
        double saldo = in.nextDouble();

        System.out.println();
        System.out.println("Dispo angeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Dispo angeben!");
            in.nextLine();
        }
        double dispo = in.nextDouble();

        Konten.add(new Girokonto(name, saldo, dispo));

        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String e = in.nextLine();
        showMenu();

    }

    private static void showNewSparDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Sparkonto");
        System.out.println();
        System.out.println("Kontoinhaber angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        // Eingabe nur gültig, wenn nur Text eingegeben wurde
        while(!name.matches("[a-zA-Z ]+")) {
            System.out.println("Bitte einen gültigen Namen eingeben!");
            name = in.nextLine();
        }

        System.out.println();
        System.out.println("Saldo angeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Saldo angeben!");
            in.nextLine();
        }
        double saldo = in.nextDouble();

        System.out.println();
        System.out.println("Sparzins angeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Zinssatz angeben!");
            in.nextLine();
        }
        double zins = in.nextDouble();

        Konten.add(new Sparkonto(name, saldo, zins));

        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String e = in.nextLine();
        showMenu();

    }

    private static void showEditDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Bitte die Kontonummer des zu bearbeitenden Kontos angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String nummer = in.nextLine();

        // Ist das gesuchte Konto vorhanden?
        Konto lKonto = null;

        for(Konto konto : Konten){
            if(konto.getKontonummer().equals(nummer)){
                lKonto = konto;
                break;
            }
        }

        if(lKonto != null){
             showEditKontoDialog(lKonto);
        }
        else{
            System.out.println("Gesuchtes Konto nicht vorhanden");
            System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
            String s = in.nextLine();
            showMenu();
        }

    }

    private static void showEditKontoDialog(Konto pKonto){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        // Alle Properties des Kontos anzeigen
        System.out.println("Konto[" + pKonto.getKontonummer() + "]:");
        System.out.print("1 Inhaber:              ");
        System.out.println(pKonto.getInhaber());
        System.out.print("2 Saldo:                ");
        System.out.println(pKonto.getSaldo());
        System.out.print("  Eröffnungsdatum:      ");
        System.out.println(pKonto.getEroeffnungsdatum());

        // Je nach Kontotyp die weiteren Properties anzeigen
        if(pKonto.getClass().getName().equals("Kontoverwaltung.Girokonto")){
            System.out.print("3 Dispozins:            ");
            System.out.println(((Girokonto)pKonto).getDispozins());
            System.out.print("4 Dispo:                ");
            System.out.println(((Girokonto)pKonto).getDispo());
        }
        else{
            System.out.print("3 Sparzins:             ");
            System.out.println(((Sparkonto)pKonto).getSparzins());
        }

        System.out.println();
        System.out.println("5 Zurück zum Menü");
        System.out.println();
        System.out.println("Zum Ändern eines Wertes die entsprechende Zahl wählen");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String e = in.nextLine();

        switch(e){
            case ("1"):
                System.out.println("Kontoinhaber angeben:");

                // Eingaben auswerten
                String name = in.nextLine();
                // Eingabe nur gültig, wenn nur Text eingegeben wurde
                while(!name.matches("[a-zA-Z ]+")) {
                    System.out.println("Bitte einen gültigen Namen eingeben!");
                    name = in.nextLine();
                }
                pKonto.setInhaber(name);
                showEditKontoDialog(pKonto);
                break;
            case("2"):
                System.out.println("Saldo angeben:");
                while(!in.hasNextDouble()){
                    System.out.println("Bitte einen gültigen Saldo angeben!");
                    in.nextLine();
                }
                pKonto.setSaldo(in.nextDouble());
                showEditKontoDialog(pKonto);
                break;
            case("3"):
                System.out.println("Zinssatz angeben:");
                while(!in.hasNextDouble()){
                    System.out.println("Bitte einen gültigen Zinssatz angeben!");
                    in.nextLine();
                }
                // Je nach Kontotyp das richtige Property ändern
                if(pKonto.getClass().getName().equals("Kontoverwaltung.Girokonto")){
                    ((Girokonto)pKonto).setDispozins(in.nextDouble());
                }
                else{
                    ((Sparkonto)pKonto).setSparzins(in.nextDouble());
                }
                showEditKontoDialog(pKonto);
                break;
            case("4"):
                System.out.println("Dispo angeben:");
                while(!in.hasNextDouble()){
                    System.out.println("Bitte einen gültigen Dispo angeben!");
                    in.nextLine();
                }
                ((Girokonto)pKonto).setDispo(in.nextDouble());
                showEditKontoDialog(pKonto);
                break;
            case("5"):
                showMenu();
            default:
                System.out.println("Eingabe nicht erkannt!");
                System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
                String s = in.nextLine();
                showMenu();
                break;
        }

    }

    private static void showDeleteDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Bitte die Kontonummer des zu löschenden Kontos angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String nummer = in.nextLine();

        // Ist das gesuchte Konto vorhanden?
        Konto lKonto = null;

        for(Konto konto : Konten){
            if(konto.getKontonummer().equals(nummer)){
                lKonto = konto;
                break;
            }
        }
        if(lKonto != null){
            Konten.remove(lKonto);
            System.out.println("Konto erfolgreich gelöscht");
        }
        else{
            System.out.println("Gesuchtes Konto nicht vorhanden");
        }

        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String s = in.nextLine();
        showMenu();

    }

    private static void showKontoauszugDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Bitte die Kontonummer angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String nummer = in.nextLine();

        // Ist das gesuchte Konto vorhanden?
        Konto lKonto = null;

        for(Konto konto : Konten){
            if(konto.getKontonummer().equals(nummer)){
                lKonto = konto;
                break;
            }
        }
        if(lKonto != null){
            lKonto.kontoauszug();
        }
        else{
            System.out.println("Gesuchtes Konto nicht vorhanden");
        }

        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String s = in.nextLine();
        showMenu();

    }

    private static void showTransactionDialog(){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Kontonummer des zu belastenden Kontos angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String nummer = in.nextLine();

        // Ist das gesuchte Konto vorhanden?
        Konto sender = null;

        for(Konto konto : Konten){
            if(konto.getKontonummer().equals(nummer)){
                sender = konto;
                break;
            }
        }
        // Wenn nicht, brechen wir hier ab
        if(sender == null){
            System.out.println("Gesuchtes Konto nicht vorhanden");
            System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
            String s = in.nextLine();
            showMenu();
        }

        System.out.println("Kontonummer des zu begünstigen Kontos angeben:");
        nummer = in.nextLine();
        Konto receiver = null;

        while(receiver == null){
            // Empfänger suchen
            for(Konto konto : Konten){
                if(konto.getKontonummer().equals(nummer)){
                    receiver = konto;
                    break;
                }
            }
            if(receiver == null){
                System.out.println("Bitte eine gültige Kontonummer eingeben!");
                nummer = in.nextLine();
            }
        }

        // Wert erfragen
        System.out.println("Betrag eingeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Betrag angeben!");
            in.nextLine();
        }
        double value = in.nextDouble();

        // Überweisung durchführen
        sender.ueberweisung(receiver,value);

        System.out.println();
        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String s = in.nextLine();
        showMenu();

    }

    private static void showAuszahlungDialog(boolean pEinzahlung){

        // Anzeige leeren
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Bitte die Kontonummer angeben:");

        // Eingaben auswerten
        Scanner in = new Scanner(System.in);
        String nummer = in.nextLine();

        // Ist das gesuchte Konto vorhanden?
        Konto lKonto = null;

        for(Konto konto : Konten){
            if(konto.getKontonummer().equals(nummer)){
                lKonto = konto;
                break;
            }
        }

        // Wenn nicht, brechen wir hier ab
        if(lKonto == null){
            System.out.println("Gesuchtes Konto nicht vorhanden");
            System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
            String s = in.nextLine();
            showMenu();
        }

        // Prüfen ob wir überhaupt auszahlen dürfen
        if(!lKonto.getClass().getName().equals("Kontoverwaltung.Girokonto")){
            System.out.println("Das gewählte Konto ist kein Girokonto!");
            System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
            String s = in.nextLine();
            showMenu();
        }

        // Wert erfragen
        System.out.println("Betrag eingeben:");
        while(!in.hasNextDouble()){
            System.out.println("Bitte einen gültigen Betrag angeben!");
            in.nextLine();
        }
        double value = in.nextDouble();

        // Müssen wir eine Auszahlung oder Einzahlung tätigen?
        if(pEinzahlung){
            // Einzahlung des gewählten Betrags
            ((Girokonto)lKonto).Einzahlung(value);
        }
        else{
            // Die tatsächliche Auszahlung
            ((Girokonto)lKonto).Auszahlung(value);
        }

        System.out.println("Beliebige Einagbe um zum Menü zurückzukehren...");
        String s = in.nextLine();
        showMenu();
    }

}
