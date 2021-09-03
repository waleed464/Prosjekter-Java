import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Oblig6 {
    public static void skrivUt(String s){
        System.out.println(s+"\n");
    }

    public static void main(String[] args) {
        String filnavn = null;

        if (args.length > 0) {
            filnavn = args[0];
        } else {
            System.out.println("FEIL! Riktig bruk av programmet: "
                               +"java Oblig6 <labyrintfil>");
            return;
        }
        File fil = new File(filnavn);
        Labyrint l = null;

        if(args.length>1 && args[1].equals("detaljert")){
            try {
                l = Labyrint.lesFraFil(fil,true);
            } catch (FileNotFoundException e) {
                System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
                System.exit(1);
            }
        }
        else{
           try {
               l = Labyrint.lesFraFil(fil);
           } catch (FileNotFoundException e) {
               System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
               System.exit(1);
           }
       }

        skrivUt(l.toString());
        Scanner inn = new Scanner(System.in);
        System.out.println("Skriv inn koordinater <kolonne> <rad> ('a' for aa avslutte)");
        String[] ord = inn.nextLine().split(" ");
        while (!ord[0].equals("a")) {

            try {
                int startKol = Integer.parseInt(ord[0]);
                int startRad = Integer.parseInt(ord[1]);

                l.finnUtveiFra(startKol, startRad);
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig input!");
            }

            System.out.println("Skriv inn nye koordinater ('a' for aa avslutte)");
            ord = inn.nextLine().split(" ");
        }
    }
}
