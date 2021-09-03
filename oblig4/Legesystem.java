import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;

public class Legesystem{

    private static SortertLenkeliste < Lege > leger = new SortertLenkeliste < Lege > ();
    private static Lenkeliste < Legemiddel > legemiddel = new Lenkeliste < Legemiddel > ();
    private static Lenkeliste < Resepter > resepter = new Lenkeliste < Resepter > ();
    private static Lenkeliste < Pasient > pasienter = new Lenkeliste < Pasient > ();

// Kommandoløkke som kjører frem til brukeren selv velger å avslutte programmet ved å skrive stopp.
    public static void main(String[] args) {

        Scanner kommando = new Scanner(System.in);
        meny();
        boolean kjoerer = true;

        while (kjoerer) {
            switch (kommando.nextLine()) {
                case "0":
                    Scanner d=new Scanner(System.in);
                    System.out.println("\nSkriv inn navnet på filen. Velg mellom filene inndata.txt eller myeInndata.txt (Unngå å bruke navnet til samme filen omigjen fordi det vil føre til at det blir dupliserte objekter.)\n");
                    lesFraFil(d.nextLine());
                    break;
                case "1":
                    skrivutInnholdet();
                    break;

                case "2":
                    leggTil();
                    break;

                case "3":
                    brukResept();
                    break;

                case "4":
                    visStatistikk();
                    break;

                case "5":
                    skrivTilFil();
                    break;

                case "stopp":
                    System.out.println("Program avsluttes....");
                    kjoerer = false;
                    break;

                default:
                    System.out.println("Skriv et gyldig kommando.");
                    break;
            }
        }
        kommando.close();
    }

// Meny: presenterer ulike alternativer
    public static void meny() {

        System.out.println("\n");
        System.out.println("Hei, og Velkommen!");
        System.out.println("\nBruk følgende kommandoer for å fortsette. Velg blant tallene som er oppgitt i parantesen. :\n");
        System.out.println("(0) - Les data fra en fil. Velg mellom filene inndata.txt eller myeInndata.txt. Tast inn navnet på filen, og trykk enter-knappen.");
        System.out.println("(1) - Skriver ut en fullstendig oversikt over pasienter, leger, legemidler og resepter");
        System.out.println("(2) - Oppretter og legger til nye elementer i systemet");
        System.out.println("(3) - Bruker en gitt resept fra listen til en pasient");
        System.out.println("(4) - Skriver ut forskjellige former for statistikk (");
        System.out.println("(5) - Skriver alle data til fil");
        System.out.println("skriv stopp for å avslutte programmet\n");

    }
//Leser innholdet av filen. Fil-navnet blir skrevet av brukeren.
    public static void lesFraFil(String filNavn) {
        try {
            Scanner input = new Scanner(new File(filNavn));


            while (input.hasNextLine()) {
                String linje= input.nextLine();
                if (linje.contains("# Pasienter (navn, fnr)")) {
                    System.out.println("Lesing startet");
                    continue;
                } else if (!linje.contains("# Legemidler (navn,type,pris,virkestoff,[styrke])")) {
                    System.out.println("Under behandling");
                    String[] splittet = linje.split(",");
                    Pasient p = new Pasient(splittet[0], splittet[1].trim());
                    pasienter.leggTil(p);
                }
                if (linje.contains("# Legemidler (navn,type,pris,virkestoff,[styrke])")) {
                    System.out.println("Lesing ferdig");
                    break;
                }
            }


            while (input.hasNextLine()) {
                String linje= input.nextLine();
                if (linje.contains("# Legemidler (navn,type,pris,virkestoff,[styrke])")) {
                    System.out.println("Under behandling");
                    continue;
                } else if (!linje.contains("# Leger (navn,kontrollid / 0 hvis vanlig lege)")) {
                    String[] splittet = linje.split(",");
                    if (splittet[1].trim().equals("narkotisk")){
                        System.out.println("behandling");
                        try{
                            if(splittet.length<5){
                                Narkotisk p = new Narkotisk(splittet[0], Double.parseDouble(splittet[2].trim()),  Double.parseDouble(splittet[3].trim()),0);
                                legemiddel.leggTil(p);
                            }else{
                                Narkotisk p = new Narkotisk(splittet[0], Double.parseDouble(splittet[2].trim()),  Double.parseDouble(splittet[3].trim()), Integer.parseInt(splittet[4].trim()));
                                legemiddel.leggTil(p);
                                }
                            System.out.println("narkotisk endt");
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                    else if (splittet[1].trim().equals("vanedannende") ){
                        if(splittet.length<5){
                            Vanedannende p = new Vanedannende(splittet[0], Double.parseDouble(splittet[2].trim()),  Double.parseDouble(splittet[3]), 0);
                            legemiddel.leggTil(p);
                        }else{
                            Vanedannende p = new Vanedannende(splittet[0], Double.parseDouble(splittet[2].trim()),  Double.parseDouble(splittet[3].trim()), Integer.parseInt(splittet[4].trim()));
                            legemiddel.leggTil(p);
                        }
                    }
                    else if (splittet[1].trim().equals("vanlig") ){
                        if(splittet.length<4){
                            Vanlig p = new Vanlig(splittet[0], Double.parseDouble(splittet[2].trim()),  0.0);
                            legemiddel.leggTil(p);
                        }else{
                            Vanlig p = new Vanlig(splittet[0], Double.parseDouble(splittet[2].trim()),  Double.parseDouble(splittet[3].trim()));
                            legemiddel.leggTil(p);
                        }
                    }
                }
                else if (linje.contains("# Leger (navn,kontrollid / 0 hvis vanlig lege)")) {
                    System.out.println("Under behandling");
                    break;
                }
            }


            while (input.hasNextLine()) {
                String linje= input.nextLine();

                if (linje.contains("# Leger (navn,kontrollid / 0 hvis vanlig lege)")) {
                    System.out.println("Under behandling");
                    continue;
                } else if (!linje.contains("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])")) {
                    String[] splittet = linje.split(",");
                    if(Integer.parseInt(splittet[1].trim())!=0){
                        Spesialist p =new Spesialist(splittet[0],Integer.parseInt(splittet[1].trim()));
                        leger.leggTil(p);
                    }
                    else{
                        Lege p=new Lege(splittet[0]);
                        leger.leggTil(p);
                    }
                }
                if (linje.contains("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])")) {
                    System.out.println("Under behandling");
                    break;
                }
            }


            while (input.hasNextLine()) {
                String linje= input.nextLine();

                if (linje.contains("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])")) {
                    System.out.println("Under behandling");
                    continue;
                } else  {
                    try{
                        String[] splittet = linje.split(",");
                        int legemiddel_no=Integer.parseInt(splittet[0]);
                        String lege_navn=splittet[1].trim();
                        int pasient_id=Integer.parseInt(splittet[2].trim());
                        String type=splittet[3].trim();

                        int reit;
                        if(splittet.length<5){
                            reit=0;
                        }else{
                            reit=Integer.parseInt(splittet[4].trim());
                        }

                        for (Lege doc : leger) {

                           if (lege_navn.equals(doc.hentNavn())){
                            System.out.println(lege_navn);
                            for(Pasient p: pasienter){

                                if(pasient_id==p.hentPasientId()){
                                    System.out.println(Integer.toString(pasient_id));
                                    for(Legemiddel m: legemiddel){

                                        if(legemiddel_no==m.hentId()){
                                            System.out.println("Under behandling");
                                            System.out.println(type);

                                            if(type.equals("hvit")){
                                                System.out.println("Under behandling");
                                                try{
                                                  HviteResepter hvit= doc.skrivHvitResept(m, p, reit);

                                                  resepter.leggTil(hvit);
                                                  System.out.println("Under behandling");
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                }
                                          }

                                           else if(type.equals("blaa")){
                                            System.out.println("Under behandling");

                                            if(m instanceof Narkotisk){

                                                if(doc instanceof Spesialist){
                                                    BlåResepter blaa= doc.skrivBlaaResept(m, p, reit);
                                                    resepter.leggTil(blaa);
                                                }

                                                else{
                                                    throw new UlovligUtskrift(doc, m);
                                                }
                                            }
                                            BlåResepter blaa= doc.skrivBlaaResept(m, p, reit);
                                            resepter.leggTil(blaa);
                                           }

                                           else if(type.equals("militaer")){
                                              System.out.println("Under behandling");

                                               Militærresepter militaer= doc.skrivMillitaerResept(m, p, reit);
                                               resepter.leggTil(militaer);
                                       }

                                           else if(type.equals("p")){
                                               P_resepter p_res= doc.skrivPResept(m, p);
                                               resepter.leggTil(p_res);
                                       }
                                   }
                                }
                            }
                        }
                   }
                }
            }catch(Exception e){
                System.out.println(e);
            }
                }
            }

            input.close();
            System.out.println("Ferdig lest filen");

        } catch (Exception unntak) {
            unntak.printStackTrace();
        }

        meny();
    }

//Skriver ut en fullstendig oversikt over pasienter, leger, legemidler og resepter
    public static void skrivutInnholdet() {
        int teller = 0;

        System.out.println("\nOversikt over Pasienter:\n");
        for (Pasient sient: pasienter) {
            System.out.println("Navn: " + sient.hentNavn());
            System.out.println("Foedselsnummer: " + sient.hentFødselsnummer() + "\n");
        }

        System.out.println("\nOversikt over Legemidler:\n");
        for (Legemiddel middel: legemiddel) {
            System.out.println(middel.toString());
        }

        System.out.println("\nOversikt over Leger:\n");
        for (Lege ege: leger) {
            System.out.println(ege + "ID: " + teller + "\n");
            teller++;
        }

        System.out.println("\nOversikt over Resepter:\n");
        for (Resepter septer: resepter) {
            System.out.println(septer.toString()+"\n");
        }
        meny();
    }

//Oppretter og legger til nye elementer i systemet
    public static void leggTil() throws UgyldigListeIndeks {

        System.out.println("\nHva vil du opprette? Velg blant tallene som er oppgitt i parantesen.");
        System.out.println("(1) - Pasient");
        System.out.println("(2) - Lege");
        System.out.println("(3) - Legemiddel");
        System.out.println("(4) - Resept\n");

        Scanner skriver = new Scanner(System.in);
        String velger;

        velger = skriver.nextLine();

        switch (velger) {
            case "1":
                System.out.println("\nPasient skal opprettes:\n");

                Scanner pasientSkriver = new Scanner(System.in);
                String leggerPasient;

                System.out.println("Skriv navn og fødselsnummer for å opprette pasienten. (Format: navn,fødselsnummer)");

                leggerPasient = pasientSkriver.nextLine();

                String[] pasienten = leggerPasient.split(",");
                String navnet = pasienten[0];
                String fnr=pasienten[1];
                Pasient nyPas = new Pasient(navnet, fnr);

                pasienter.leggTil(nyPas);

                System.out.println("\nPasient opprettet\n");
                System.out.println("Navn: "+ nyPas.hentNavn());
                System.out.println("Fødselsnummer: " + nyPas.hentFødselsnummer());

                break;

            case "2":
                System.out.println("\nLege skal opprettes\n");

                Scanner legeskriver = new Scanner(System.in);
                String leggerLege;

                System.out.println("Oppgi navn og eventuelt kontrollID nummer for å opprette lege. (Format: navn,avtalenummer)");
                System.out.println("Bruk 0 som kontrollID for å opprette vanlig lege ellers oppgi et annet kontrollID-nummer enn 0 for å opprette spesialist.\n");

                leggerLege = legeskriver.nextLine();

                String[] splittet = leggerLege.split(",");
                String leggernavn = splittet[0];

                try {

                    if (Integer.parseInt(splittet[1]) != 0) {
                        Spesialist nySpesialist = new Spesialist(leggernavn, Integer.parseInt(splittet[1]));

                        leger.leggTil(nySpesialist);

                        System.out.println("\nOpprettet spesialist.\n");
                        System.out.println(nySpesialist);


                    } else {
                        Lege leggerLeg = new Lege(leggernavn);

                        leger.leggTil(leggerLeg);

                        System.out.println("\nkontrollID er oppgitt til å være 0. Opprettet vanlig lege.\n");
                        System.out.println(leggerLeg);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("\nKontrollID er ikke gyldig\n");
                }

                break;

            case "3":

                Scanner legemiddelSkriver = new Scanner(System.in);
                String velge;

                System.out.println("\nHva slags legemiddel vil du opprette? Velg blant tallene som er oppgitt i parantesen.");
                System.out.println("(1) - Narkotisk");
                System.out.println("(2) - Vanedannende");
                System.out.println("(3) - Vanlig\n");

                velge = legemiddelSkriver.nextLine();

                switch (velge) {
                    case "1":

                        System.out.println("\nSkriv info om legemiddel: (format: navn,pris,virkestoff,narkotika styrke)\n");

                        velge = legemiddelSkriver.nextLine();

                            String[] splitNar = velge.split(",");

                                String leggerNar = splitNar[0];
                                try{
                                    Narkotisk nyttN = new Narkotisk(splitNar[0], Double.parseDouble(splitNar[1]), Double.parseDouble(splitNar[2]), Integer.parseInt(splitNar[3]));
                                    legemiddel.leggTil(nyttN);
                                    System.out.println("\nNarkotisk legemiddel opprettet.\n");
                                    System.out.println(nyttN);
                                }

                        catch(NumberFormatException e){
                            System.out.println("oppgi gyldig input");
                        }

                        break;

                    case "2":
                        System.out.println("\nSkriv info om legemiddel: (format: navn,pris,virkestoff,vannedannende styrke)\n");

                        velge = legemiddelSkriver.nextLine();

                        String[] splitVannd = velge.split(",");

                            String leggerLegemiddelVannd = splitVannd[0];
                            try{
                                Vanedannende nyttV = new Vanedannende(splitVannd[0], Double.parseDouble(splitVannd[1]), Double.parseDouble(splitVannd[2]), Integer.parseInt(splitVannd[3]));
                                legemiddel.leggTil(nyttV);
                                System.out.println("\nVanedannende legemiddel opprettet.\n");
                                System.out.println(nyttV);
                            }

                        catch(NumberFormatException e){
                            System.out.println("oppgi gyldig input");
                        }
                        break;

                    case "3":
                        System.out.println("\nSkriv info om legemiddel: (format: navn,pris,virkestoff)\n");

                        velge = legemiddelSkriver.nextLine();

                        String[] splitVanlig = velge.split(",");

                            String leggerLegemiddelVanndan = splitVanlig[0];
                            try{
                                Vanlig nyttVann = new Vanlig(splitVanlig[0], Double.parseDouble(splitVanlig[1]), Double.parseDouble(splitVanlig[2]));
                                legemiddel.leggTil(nyttVann);
                                System.out.println("\nVanlig legemiddel opprettet.\n");
                                System.out.println(nyttVann);
                            }

                        catch(NumberFormatException e){
                            System.out.println("oppgi gyldig input");
                        }

                        break;

                    case "stopp":
                        break;
                }
                break;

            case "4":

                Scanner reseptSkriver = new Scanner(System.in);
                String leggerResept;

                System.out.println("\nHvilken type resept vil du opprette? Velg blant tallene som er oppgitt i parantesen.\n");
                System.out.println("(1) -blaa resept");
                System.out.println("(2) - hvit resept");
                System.out.println("(3) - P resept");
                System.out.println("(4) - Militær resept\n");

                leggerResept = reseptSkriver.nextLine();

                System.out.println("\nOversikt over Pasienter:\n");
                int tellerPasient = 0;

                for (Pasient sient: pasienter) {
                    System.out.println(tellerPasient + " : " + sient.hentNavn());
                    tellerPasient++;
                }

                System.out.println("\nOversikt over leger:\n");
                int tellerLege = 0;

                for (Lege ege: leger) {
                    System.out.println(tellerLege + " : " + ege.hentNavn());
                    tellerLege++;
                }

                System.out.println("\nOversikt over legemidler:\n");
                int tellerLegemiddel = 0;

                for (Legemiddel lm: legemiddel) {
                    System.out.println(tellerLegemiddel + " : " + lm.hentNavn());
                    tellerLegemiddel++;
                }

                Lege lege;
                Pasient pasient;
                switch (leggerResept) {
                    case "1":

                        System.out.println("\nSkriv informasjon om resepten: (format: pasientId,legeId,legemiddelId,antallreit).ID til de alle velger du fra listene som vises over.\n");
                        String leggerBlaa;

                        leggerBlaa = reseptSkriver.nextLine();
                        try{

                        String[] splitBlaa = leggerBlaa.split(",");

                        lege = leger.hent(Integer.parseInt(splitBlaa[1]));

                        pasient = pasienter.hent(Integer.parseInt(splitBlaa[0]));

                        try{
                            BlåResepter nyBlaa  = lege.skrivBlaaResept(legemiddel.hent(Integer.parseInt(splitBlaa[2])),  pasient, Integer.parseInt(splitBlaa[3]));
                            resepter.leggTil(nyBlaa);
                            pasient. leggeNyResept(nyBlaa);
                            System.out.println("\nOpprettet blå resept.\n");
                            System.out.println(nyBlaa);
                        }
                        catch(UlovligUtskrift e){
                            System.out.println(e);
                     }
                 }
                        catch(NumberFormatException e){
                            System.out.println("\noppgi gyldig input");
                        }
                        break;

                    case "2":
                        System.out.println("\nSkriv informasjon om resepten: (format: pasientId,legeId,legemiddelId,antallreit) ID til de alle velger du fra listene som vises over.\n");
                        String leggerHvit;

                        leggerHvit = reseptSkriver.nextLine();
                        try{

                             String[] splitHvit = leggerHvit.split(",");

                                 lege = leger.hent(Integer.parseInt(splitHvit[1]));
                                 pasient = pasienter.hent(Integer.parseInt(splitHvit[0]));

                                try{
                                HviteResepter nyHvit = lege.skrivHvitResept(legemiddel.hent(Integer.parseInt(splitHvit[2])),  pasient, Integer.parseInt(splitHvit[3]));
                                resepter.leggTil(nyHvit);
                                pasient. leggeNyResept(nyHvit);
                                System.out.println("\nOpprettet hvit resept.\n");
                                System.out.println(nyHvit);
                            }
                            catch(UlovligUtskrift e){
                                System.out.println(e);
                         }
                     }
                        catch(NumberFormatException e){
                             System.out.println("\noppgi gyldig input");
                        }
                        break;

                    case "3":
                        System.out.println("\nSkriv informasjon om resepten: (format: pasientId,legeId,legemiddelId) ID til de alle velger du fra listene som vises over.\n");
                        String leggerP;

                        leggerP = reseptSkriver.nextLine();
                        try{

                            String[] splitP = leggerP.split(",");

                            lege = leger.hent(Integer.parseInt(splitP[1]));
                            pasient = pasienter.hent(Integer.parseInt(splitP[0]));

                                try{
                                    P_resepter nyP = lege.skrivPResept(legemiddel.hent(Integer.parseInt(splitP[2])),  pasient);
                                    resepter.leggTil(nyP);
                                    pasient. leggeNyResept(nyP);
                                    System.out.println("\nOpprettet P-resept.\n");
                                    System.out.println(nyP);
                                 }
                                 catch(UlovligUtskrift e){
                                     System.out.println(e);
                              }
                          }
                        catch(NumberFormatException e){
                             System.out.println("\noppgi gyldig input");
                        }
                       break;

                    case "4":
                        System.out.println("\nSkriv informasjon om resepten: (format: pasientId,legeId,legemiddelId,antallreit)ID til de alle velger du fra listene som vises over.\n");
                        String leggeMilitær;
                        try{

                        leggeMilitær = reseptSkriver.nextLine();

                           String[] splitMili= leggeMilitær.split(",");

                            lege = leger.hent(Integer.parseInt(splitMili[1]));
                            pasient = pasienter.hent(Integer.parseInt(splitMili[0]));

                                try{
                                    Militærresepter nyMilitaer = lege.skrivMillitaerResept(legemiddel.hent(Integer.parseInt(splitMili[2])),  pasient, Integer.parseInt(splitMili[3]));
                                    resepter.leggTil(nyMilitaer);
                                    pasient. leggeNyResept(nyMilitaer);
                                    System.out.println("\nOpprettet militær-resept.\n");
                                    System.out.println(nyMilitaer);
                                }
                                catch(UlovligUtskrift e){
                                    System.out.println(e);
                             }
                         }
                         catch(NumberFormatException e){
                             System.out.println("\noppgi gyldig input");
                     }
                    break;

                    case "stopp":
                        break;
                }
                break;
        }
       meny();
    }

//Bruker en gitt resept fra listen til en pasient.
    public static void brukResept(){
        int valgt_id;

        for (Pasient sient: pasienter) {
            System.out.println(sient.hentPasientId() + " : " + sient.hentNavn());
        }
        System.out.println("\nHvilken pasient vil du se resept på? Oppgi ID. (ID er tallet ved pasienten). Trykk deretter enter-knappen\n");

        Scanner skriver = new Scanner(System.in);
        try{
        valgt_id = Integer.parseInt(skriver.nextLine());
        Pasient pasient = pasienter.hent(valgt_id);
        System.out.println("\nValgt pasient: " + pasient.hentNavn() + "\nFødselsnummer: " + pasient.hentFødselsnummer() + "\n");
        System.out.println("Resepter på navnet til pasienten:");
        for(Resepter r: resepter){
            if(r.hentPasientId()==valgt_id){
                System.out.println(r.hentId()+" : " + r.hentLegemiddel().hentNavn() + " (Antall reit: " + r.hentReit() + ")");
            }
        }
        System.out.println("\nSkriv inn ID-en for resepten du vil bruke (Id er tall ved resept). Hvis ingen resept kommer, har pasient ingen resept på navnet sitt. I dette tilfellet trykker du på enter-knappen for å gå tilbake til hovedmeny. Hvis du taster inn noe annet får du skrevet ut feilmelding.\n");
        int velger = Integer.parseInt(skriver.nextLine());
        if(resepter.hent(velger).pasient.hentPasientId()==valgt_id){

        Resepter resept = resepter.hent(velger);

        if (resept.bruk()) {
            System.out.println("\nResept brukt. Reit igjen på denne resepten: " + resept.hentReit());
        } else {
            System.out.println(" Ingen reit igjen. Resept ble ikke brukt.");
        }
        }
        else{
            System.out.println("Skriv inn en gyldig ID");
        }
    }catch(NumberFormatException e){
        System.out.print("Skriv inn en gyldig input");
    }
        meny();
    }

//Skriver ut forskjellige former for statistikk.
    public static void visStatistikk() {

        System.out.println("hvilken statistikk vil du skrive ut?");
        System.out.println("(1) - Totalt antall utskrevne resepter på vanedannende legemidler");
        System.out.println("(2) - Totalt antall utskrevne resepter på narkotiske legemidler");
        System.out.println("(3) - Statistikk om mulig misbruk av narkotika:\n");

        Scanner skriver= new Scanner(System.in);
        String velger;

        velger = skriver.nextLine();

        switch (velger) {
            case "1":
                int totaltVanedannende = 0;

                for (Resepter res: resepter) {
                    if (res.hentLegemiddel() instanceof Vanedannende) {
                        totaltVanedannende++;
                    }
                }

                System.out.println("Antall av vanedannende resepter: " + totaltVanedannende);
                break;

                case "2":
                    int totaltNarkotisk = 0;

                    for (Resepter res: resepter) {
                        if (res.hentLegemiddel() instanceof Narkotisk) {
                                totaltNarkotisk++;
                        }
                    }
                    System.out.println("Antall av narkotika resepter: " + totaltNarkotisk);
                    break;

            case "3":
            System.out.println("\nNavnene på alle leger som har skrevet ut minst en resept på narkotiske legemidler, og antallet slike resepter per lege:\n");
                for (Lege leg: leger) {
                    int legeNark = 0;

                    for (Resepter res: leg.hentResept()) {
                        if (res.hentLegemiddel() instanceof Narkotisk) {
                            legeNark++;
                        }
                    }
                    if (legeNark > 0) {
                        System.out.println(leg.hentNavn() + " skriverut tilsammen " + legeNark + " narkotiske resepter.");
                    }
                }
                System.out.println("\nNavnene på alle pasienter som har minst en gyldig resept på narkotiske legemidler:\n");
                for (Pasient pes: pasienter) {
                    int narkotiskPasient = 0;

                    for (Resepter res: pes.hentReseptlisten()) {
                        if (res.hentLegemiddel() instanceof Narkotisk) {
                            if (res.hentReit() > 0) narkotiskPasient++;
                        }
                    }
                    if (narkotiskPasient > 0) {
                        System.out.println(pes.hentNavn() + " har " + narkotiskPasient + " narkotiske resepter.");
                    }
                }
                break;

            case "stopp":
                break;
        }
        meny();
    }

//Skriver alle data til fil.
    public static void skrivTilFil(){
        String filNavn= "System.txt";
        try {
            File minObj = new File(filNavn);
            if (minObj.createNewFile()) {
              System.out.println("\nFil opprettet: " + minObj.getName());
            } else {
              System.out.println("Filen finnes fra før av.");
            }
         } catch (IOException e) {
            System.out.println("En feil oppstod.");
            e.printStackTrace();
          }
          try {
            FileWriter minSkriver = new FileWriter(filNavn,true);
            minSkriver.write("#Pasienter(navn,fnr) \n");
            minSkriver.close();
          } catch (IOException e) {
            System.out.println("En feil oppstod.");
            e.printStackTrace();
          }
          for(Pasient p:pasienter){
                String tekst= p.hentNavn()+","+p.hentFødselsnummer()+"\n";
                try {
                    FileWriter minSkriver = new FileWriter(filNavn,true);
                    minSkriver.write(tekst);
                    minSkriver.close();

                  } catch (IOException e) {
                    System.out.println("En feil oppstod.");
                    e.printStackTrace();
                    }

          }
         try {
            FileWriter minSkriver = new FileWriter(filNavn,true);
            minSkriver.write("# Legemidler (navn,type,pris,virkestoff,[styrke]) \n");
            minSkriver.close();
          } catch (IOException e) {
            System.out.println("En feil oppstod..");
            e.printStackTrace();
          }

          for(Legemiddel m:legemiddel){
            String type=new String();
            String styrke=new String();
            if(m instanceof Narkotisk){
                  type="narkotisk";
                  styrke=Integer.toString(((Narkotisk) m).hentNarkotiskStyrke());
              }
              else if(m instanceof Vanedannende){
                  type="vanedannende";
                  styrke=Integer.toString(((Vanedannende) m).hentVanedannendeStyrke());
              }else{
                  type="vanlig";
                  styrke="";
              }
            String tekst= m.hentNavn()+","+type+","+m.hentPris()+","+m.hentVirkestoff()+","+styrke+"\n";
            try {
                FileWriter minSkriver = new FileWriter(filNavn,true);
                minSkriver.write(tekst);
                minSkriver.close();

              } catch (IOException e) {
                System.out.println("En feil oppstod..");
                e.printStackTrace();
                }

      }
          try {
            FileWriter minSkriver = new FileWriter(filNavn,true);
            minSkriver.write("# Leger (navn,kontrollid / 0 hvis vanlig lege) \n");
            minSkriver.close();
          } catch (IOException e) {
            System.out.println("En feil oppstod..");
            e.printStackTrace();
          }

          for(Lege p:leger){
            String id="0";
            if(p instanceof Spesialist){
                  id="123";
              }

            String tekst= p.hentNavn()+","+id+"\n";
            try {
                FileWriter minSkriver = new FileWriter(filNavn,true);
                minSkriver.write(tekst);
                minSkriver.close();

              } catch (IOException e) {
                System.out.println("En feil oppstod..");
                e.printStackTrace();
                }

      }
          try {
            FileWriter minSkriver = new FileWriter(filNavn,true);
            minSkriver.write("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit]) \n");
            minSkriver.close();
          } catch (IOException e) {
            System.out.println("En feil oppstod..");
            e.printStackTrace();
          }

          for(Resepter p:resepter){
            String type="";
            if(p instanceof P_resepter){
                type="p";
            }
            else if(p instanceof Militærresepter){
                type="militaer";
            }
            else if(p instanceof HviteResepter){
                type="hvit";
            }
            else if(p instanceof BlåResepter){
                type="blaa";
            }
            String tekst= p.legemiddel.hentId()+","+p.utskrivendeLege.hentNavn()+","+p.pasient.hentPasientId()+","+type+","+p.hentReit()+"\n";
            try {
                FileWriter minSkriver = new FileWriter(filNavn,true);
                minSkriver.write(tekst);
                minSkriver.close();

              } catch (IOException e) {
                System.out.println("En feil oppstod..");
                e.printStackTrace();
                }
      }
          System.out.println("\nTakk, ha en fin dag");

          meny();
     }
}
