public abstract class Rute{

    private enum Retninger {
        NORD,
        SYD,
        OEST,
        VEST
    }

    public Rute nord;
    public Rute syd;
    public Rute oest;
    public Rute vest;

    int rad;
    int kol;

    Labyrint labyrint;

    protected boolean besoekt=false;

    public Rute(int rad, int kol, Labyrint labyrint){
        this.kol=kol;
        this.rad=rad;
        this.labyrint=labyrint;
        this.nord=null;
        this.syd=null;
        this.oest=null;
        this.vest=null;

    }

    public void skrivUt(String s){
        System.out.println(s+"\n");
    }
    public int hentRad(){
        return this.rad;
    }
    public int hentKol(){
        return this.kol;
    }

    // sjekker utvei
    public boolean tarSlutt(int rad,int kol){
        if(rad==this.labyrint.rute.length-1 || rad==0 || kol==0 || kol==this.labyrint.rute[0].length-1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean apner(Rute r){
        boolean resultat=false;
        if((r instanceof HvitRute) && tarSlutt(r.hentRad(), r.hentKol())){
            resultat=true;
        }
        return resultat;
    }

    public boolean erBesoekt(){
        return this.besoekt;
    }

    public int totaleNaboer(){
        int i=0;
        if(kanGaaNord()){
             i++; //begynner aa telle antall tilgjengelige stier
        }
        if(kanGaaSyd()){
            i++; 
       }
       if(kanGaaOest()){
        i++;
       }
        if(kanGaaVest()){
            i++; 
       }
       return i;
        
    }

    //returnerer listen over ekstra stier i tilfelle de tilgjengelige stier er mer enn én
    public Lenkeliste<Rute> ekstra_Rute(){
        Lenkeliste<Rute> ekstra_naboer=new Lenkeliste<>();
        int i=0;
        if( totaleNaboer()>1){
             if(kanGaaNord()){
                 if(i==0){
                     i++;  // lar alltid vaere den foerste stien, den blir fulgt av original traad.
                 }
                 else{
                     ekstra_naboer.leggTil(this.nord); // setter ekstra sti paa listen
                                                          // vil bli fulgt av nye traader
                 }
             }
             if(kanGaaSyd()){
                if(i==0){
                    i++;
                }
                else{
                    ekstra_naboer.leggTil(this.syd);
                }
            }
            if(kanGaaOest()){
                if(i==0){
                    i++;
                }
                else{
                    ekstra_naboer.leggTil(this.oest);
                }
            }
            if(kanGaaVest()){
                if(i==0){
                    i++;
                }
                else{
                    ekstra_naboer.leggTil(this.vest);
                }
            }
        }
        return ekstra_naboer;
    }

    public Rute eneste_vei_igjen(){
        Lenkeliste<Rute> list= ekstra_Rute();
        if(kanGaaNord()){
            // ekstra_Rute inneholder alle stiene bortsett fra en
            // den stien, som ikke er inkludert i ekstra_Rute
            // vil bli fulgt av den gamle traaden
            if(!list.inneholder(this.nord)){ 
                return this.nord;
            }
        }
        else if(kanGaaSyd()){
            if(!list.inneholder(this.syd)){
                return this.syd;
            }
        }
        else if(kanGaaOest()){
            if(!list.inneholder(this.oest)){
                return this.oest;
            }
        }
        else if(kanGaaVest()){
            if(!list.inneholder(this.vest)){
                return this.vest;
            }
        }
        
           return this;
        
    }

    
    public boolean kanGaaSyd(){
        //skrivUt("i kanGaaSyd metode");
        if(this.syd!=null ){
            return true ;
        }
        else{ 
        //skrivUt("syd er null");
            return false;
        }
    }

    public boolean kanGaaNord(){
       // skrivUt("i kanGaaNord metode");
       if(this.nord!=null ){
           return true;
       }
       else{ 
          //skrivUt("nord er null");
           return false;
       }
   }

    public boolean kanGaaOest(){
        //skrivUt("i kanGaaOest metode");
        if(this.oest!=null ){
            return true;
        }
        else{ 
            //skrivUt("oest er null");
            return false;
        }
    }

    public boolean kanGaaVest(){
        //skrivUt("i kanGaaVest metode");
        if(this.vest!=null ){
            return true;
        }
        else{ 
           //skrivUt("vest er null");
            return false;
        }
    }
    

                    /* Svar paa SPOERSMAAL fra innlevering
                       Spoersmaal: Hva skjer om den gamle traaden foerst gaar videre til neste rute og saa etterpaa
                       starter opp nye traader?

                       Svar: I oppgaven kreves det at vi finner veien til aapningen paa en slik maate at i tilfelle
                             mer enn en vei dukker opp fra en hvit rute, skal soeket gjennom hele veien fortsette
                             parallelt.Uaansett, hvis vi flytter den gamle traaden foer vi starter en ny traad, vil
                             den nye traaden ikke startes foer den gamle traaden er ferdig. Aarsaken bak dette er at
                             vi rekursivt kaller gaa-funksjonen. Saa naar den gamle traaden er flyttet foer du starter
                             en ny traad, paa grunn av rekursjon, vil den gamle traaden fortsette aa kalle gaa-funksjonen
                             til den finner slutten og bare etter det, stopper den. Kompilatoren kjoerer den neste
                             linjen der den nye traaden startes. Derfor vil det ikke muliggjoere parallell soek.

                         */
                         


    public void gaa(Lenkeliste<Rute> minRute){
        //skrivUt("i gaa() "+koordTilStreng());
        
        Lenkeliste<Rute> minListe=new Lenkeliste<>();
        minListe.kopierElementer(minRute);
        minListe.leggTil(this);
        if(apner(this)){
            //skrivUt("fant slutten");

            labyrint.alle_mulige_ruter.leggStiTilListen(minListe); 
        }
        else{
            Lenkeliste<Thread> traadListe=new Lenkeliste<>();
            int antall_naboer= totaleNaboer();
            
            if(antall_naboer>1){
                Lenkeliste<Rute> ekstra_rute_liste= ekstra_Rute();
                
                for(Rute r : ekstra_rute_liste){
                    if(!alleredeBesoekt(minListe, r)){
                    Runnable oppg =new MinTraad(r,minListe);
                    Thread minTraad1 = new Thread(oppg);
                    //skrivUt("opprettet en traad for "+r.koordTilStreng());
                    traadListe.leggTil(minTraad1);
                    }  
                }

                //starter traader
                for(Thread t: traadListe){
                    t.start();

                }
            }       
                    // neste trinn for original traad
                    Rute neste= eneste_vei_igjen();
                    
                    if(! alleredeBesoekt(minListe, neste)){
                //        selv gaar foran
                        neste.gaa(minListe);
              //          rekursive kall avsluttet
                    }
           
            for(Thread t: traadListe){
                try{
                    //skrivUt(" venter til alle tradene slutter");
                   t.join();
                }catch(Exception e){
                    //skrivUt("noe gikk galt i trader");
                }
            }
        }

    }

   
    public boolean alleredeBesoekt(Lenkeliste<Rute> rute_liste, Rute rute){
        for(Rute r: rute_liste){
            if(r.hentRad()==rute.hentRad() && r.hentKol()==rute.hentKol()){
                return true;
            }
        
        }
    
        return false;
    }

    // konverter koordinatene til String
    public String koordTilStreng() {
        return "(" + rad + ", " + kol + ")";
    }

    abstract char charTilTegn();

}
