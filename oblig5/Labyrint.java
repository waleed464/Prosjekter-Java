import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



class Labyrint{
    protected Rute[][] rute;
    protected Lenkeliste<String> alle_mulige_ruter; // for lagring av alle mulige stier til utvei
    protected int rader;
    protected int kolonner;
    private Labyrint (int rader, int kolonner,Rute[][] rute){
        this.rader=rader;
        this.kolonner=kolonner;
        this.rute=rute;
        alle_mulige_ruter = new Lenkeliste<String>();
    }

    // til aa skrive ut paastander mellom programmet for testing
    public static void skrivut(String s){
        System.out.println(s+"\n");
    }

    //hent metode for rad
    public int hentRader(){
        return this.rader;
    }

    // hent metode for kolonne
    public int hentKolonner(){
        return this.kolonner;
    }

    // sjekker om ruten kan gaa
    public static boolean erHvit(String r) {
        if (r.equals(".")) {
            return true;
        } else {
            return false;
        }
    }

    // sjekker om vi har naadd kanten av labyrinten
    public static boolean erKant(int i, int j, Rute[][] r) {
        if (i == 0 || j == 0 || i == r.length - 1 || j == r[0].length - 1) {
            return true;
        } else {
            return false;
        }
    }
/* En statisk fabrikkmetode er en offentlig statisk metode paa objektet som returnerer en ny forekomst 
  av objektet.Dette moensteret gir et lag mellom den som kaller og skapelsen (fabrikk)

  Fordeler:
     - I motsetning til konstruktoerer har de navn
     - I motsetning til konstruktoerer, er de ikke paalagt aa opprette et nytt objekt hver gang de paaberopes
     - I motsetning til konstruktoerer, kan de returnere et objekt av returtypen
     -De reduserer verbositeten ved aa opprette parametrisert type
*/

    // Denne metoden skriver ut info uten detaljer i terminalen. Altså vi ikke taster inn ordet "detaljert" når vi kjører programmet
    // i terminalen. Da havner vi altså inn i metoden under:
    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {

        Scanner les = new Scanner(fil);
       // skrivut("skanner stjerne, leser den foerste linjen");

        String linje = les.nextLine();
        //skrivut("lest foerste linje, naa splitter tekst");

        String[] splittet_linje = linje.split(" ");
        //skrivut("splitting ferdig");

        int rader = Integer.parseInt(splittet_linje[0]);
        int kolonner = Integer.parseInt(splittet_linje[1]);
        //skrivut("faatt rader and kolonner");

        Rute[][] ny_rute = new Rute[rader][kolonner];
        //skrivut("erklaert ny_rute");
        skrivut("\nrad: "+Integer.toString(rader)+"\nkol: "+Integer.toString(kolonner));
        Labyrint labyrint = new Labyrint(rader, kolonner, ny_rute);

        for (int i = 0; i < rader; i++) {
            splittet_linje = les.nextLine().split("");
            for (int j = 0; j < kolonner; j++) {
                if (erHvit(splittet_linje[j])) {
                    if (erKant(i, j,ny_rute)) {
                        labyrint.rute[i][j]= new Aapning(i,j,labyrint);
                        //skrivut("Aapning er paa "+Integer.toString(i)+" , "+Integer.toString(j));
                    }
                    else{
                        labyrint.rute[i][j]= new HvitRute(i,j,labyrint);
                        //skrivut("Hvit rute er paa "+Integer.toString(i)+" , "+Integer.toString(j));
                    }
                }
                else{
                    labyrint.rute[i][j]= new SortRute(i,j,labyrint);
                }
            }
        }
        les.close();

        //kobler sammen naborutene.
        for(int i=0;i<labyrint.rute.length;i++){
            for(int j=0;j<labyrint.rute[0].length;j++){
                if(labyrint.rute[i][j] instanceof HvitRute ||labyrint.rute[i][j] instanceof Aapning ){
                    if(i==0){
                        if(j==0){
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                labyrint.rute[i][j].oest= labyrint.rute[i][j+1];
                            }
                        }
                        else if(j==labyrint.rute[0].length -1){
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                        }
                        else{
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                labyrint.rute[i][j].oest= labyrint.rute[i][j+1];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                        }
                    }

                    else if(i==labyrint.rute.length-1){
                        if(j==0){
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }

                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                            }
                        }
                        else if(j==labyrint.rute[0].length -1){
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                       }
                       else{
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                        if(labyrint.rute[i][j+1] instanceof HvitRute){
                            labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                        }
                        if(labyrint.rute[i][j-1] instanceof HvitRute){
                            labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                        }
                       }
                    }

                    else if(i>0 && j==0){
                        if(labyrint.rute[i][j+1] instanceof HvitRute){
                            labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                        }
                        if(labyrint.rute[i+1][j] instanceof HvitRute){
                            labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                        }
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                    }
                    else if(i>0 && j==labyrint.rute[0].length-1){
                        if(labyrint.rute[i][j-1] instanceof HvitRute){
                            labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                        }
                        if(labyrint.rute[i+1][j] instanceof HvitRute){
                            labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                        }
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                    }
                     else{
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }
                        }

                    }
                }
            }


        return labyrint;
    }

    // Denne metoden skriver ut info med detaljer i terminalen. Altså vi taster inn ordet "detaljert" etter
    // hvordan det er forklart i oppgaven når vi kjører programmet i terminalen. Da havner vi altså inn i metoden under:
    public static Labyrint lesFraFil(File fil,boolean b) throws FileNotFoundException {

        Scanner les = new Scanner(fil);
        skrivut("scanner stjerner, leser foerste linje");

        String linje = les.nextLine();
        skrivut("lest foerste linje, naa splitter tekst");

        String[] splittet_linje = linje.split(" ");
        skrivut("splitting ferdig");

        int rader = Integer.parseInt(splittet_linje[0]);
        int kolonner = Integer.parseInt(splittet_linje[1]);
        skrivut("har faatt rader og kolonner");

        Rute[][] ny_rute = new Rute[rader][kolonner];
        skrivut("erklaert ny_rute");

        Labyrint labyrint = new Labyrint(rader, kolonner, ny_rute);

        for (int i = 0; i < rader; i++) {
            splittet_linje = les.nextLine().split("");
            for (int j = 0; j < kolonner; j++) {
                if (erHvit(splittet_linje[j])) {
                    if (erKant(i, j,ny_rute)) {
                        labyrint.rute[i][j]= new Aapning(i,j,labyrint);
                        skrivut("laget en Aapning paa "+"("+Integer.toString(i)+","+Integer.toString(j)+")");
                        //skrivut("Aapning er paa "+Integer.toString(i)+" , "+Integer.toString(j));
                    }
                    else{
                        skrivut("laget en hvit rute paa "+"("+Integer.toString(i)+","+Integer.toString(j)+")");
                        labyrint.rute[i][j]= new HvitRute(i,j,labyrint);
                        //skrivut("Hvit rute er paa "+Integer.toString(i)+" , "+Integer.toString(j));
                    }
                }
                else{
                    skrivut("laget en sort rute paa "+"("+Integer.toString(i)+","+Integer.toString(j)+")");
                    labyrint.rute[i][j]= new SortRute(i,j,labyrint);
                }
            }
        }
        les.close();

        // kobler sammen naborutene
        for(int i=0;i<labyrint.rute.length;i++){
            for(int j=0;j<labyrint.rute[0].length;j++){
                if(labyrint.rute[i][j] instanceof HvitRute ||labyrint.rute[i][j] instanceof Aapning ){
                    if(i==0){
                        if(j==0){
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].oest= labyrint.rute[i][j+1];
                            }
                        }
                        else if(j==labyrint.rute[0].length -1){
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til"+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                        }
                        else{
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){

                                skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");

                                labyrint.rute[i][j].oest= labyrint.rute[i][j+1];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");

                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                        }
                    }

                    else if(i==labyrint.rute.length-1){
                        if(j==0){
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");

                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }

                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");

                                labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                            }
                        }
                        else if(j==labyrint.rute[0].length -1){
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                       }
                       else{
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                        if(labyrint.rute[i][j+1] instanceof HvitRute){
                            skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                        }
                        if(labyrint.rute[i][j-1] instanceof HvitRute){
                            skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                        }
                       }
                    }

                    else if(i>0 && j==0){
                        if(labyrint.rute[i][j+1] instanceof HvitRute){
                            skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                        }
                        if(labyrint.rute[i+1][j] instanceof HvitRute){
                            skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                        }
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                    }
                    else if(i>0 && j==labyrint.rute[0].length-1){
                        if(labyrint.rute[i][j-1] instanceof HvitRute){
                            skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                        }
                        if(labyrint.rute[i+1][j] instanceof HvitRute){
                            skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                        }
                        if(labyrint.rute[i-1][j] instanceof HvitRute){
                            skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                            labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                        }
                    }
                     else{
                            if(labyrint.rute[i+1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i+1)+","+Integer.toString(j)+" er syd til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].syd=labyrint.rute[i+1][j];
                            }
                            if(labyrint.rute[i][j+1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j+1)+" er oest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].oest=labyrint.rute[i][j+1];
                            }
                            if(labyrint.rute[i][j-1] instanceof HvitRute){
                                skrivut(Integer.toString(i)+","+Integer.toString(j-1)+" er vest til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].vest=labyrint.rute[i][j-1];
                            }
                            if(labyrint.rute[i-1][j] instanceof HvitRute){
                                skrivut(Integer.toString(i-1)+","+Integer.toString(j)+" er nord til "+ "("+Integer.toString(i)+","+Integer.toString(j)+")");
                                labyrint.rute[i][j].nord=labyrint.rute[i-1][j];
                            }
                        }

                    }
                }
            }


        return labyrint;
    }

    public String toString(){
        String element="";
        for (int i=0; i<this.hentRader();i++){
            for (int j=0;j<this.hentKolonner();j++){
                element= element + this.rute[i][j].charTilTegn();
            }
            element=element+"\n";
        }
        return element;
    }

    public Liste<String> finnUtveiFra(int rad, int kol) {
        // Fjern alt fra listen
        try{
        while (alle_mulige_ruter.stoerrelse()>0) {
            //alle_mulige_ruter.fjernIkkeReturner();
            alle_mulige_ruter.fjern();
        }
           //skrivut("kaller paa finn utvei metoden");
            rute[rad][kol].finnUtvei();
           // skrivut("retunerer alle mulige ruter");
     }
     catch(IndexOutOfBoundsException e){
         skrivut("vennligst skriv inn en gyldig koordinat");
     } return alle_mulige_ruter;
    }
}
