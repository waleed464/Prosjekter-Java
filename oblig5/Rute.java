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

    int rad; int kol;

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
    
    public void skrivut(String s){
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
    
    public boolean erBesoekt(){
        return this.besoekt;
    }

    public boolean kanGaaSyd(){
        //skriv ut("i kanGaaSyd metode");
        if(this.syd!=null ){
            if(this.syd.erBesoekt()){
               // skriv ut("syd noden er allerede besoekt");
                return false;
            }
            else{
                return true;
            }
        }
        else{
            //skriv ut("syd noden er null");
            return false;
        }
    }

    public boolean kanGaaNord(){
       // skriv ut("i kanGaaNord metode");
        if(this.nord!=null ){
            if(this.nord.erBesoekt()){
              //  skriv ut("nord noden er allerede besoekt");
                return false;
            }
            else{
                return true;
            }
        }
        else{
           // skriv ut("nord noden er null");
            return false;
        }
    }

    public boolean kanGaaOest(){
        //skriv ut("i kanGaaOest metode");
        if(this.oest!=null ){
            if(this.oest.erBesoekt()){
             //   skriv ut("oest noden er allerede besoekt");
                return false;
            }
            else{
                return true;
            }
        }
        else{
            //skriv ut("oest noden er null");
            return false;
        }
    }

    public boolean kanGaaVest(){
        //skriv ut("i kanGaaVest metode");
        if(this.vest!=null ){
            if(this.vest.erBesoekt()){
             //   skriv ut("vest noden er allerede besoekt");
                return false;
            }
            else{
                return true;
            }
        }
        else{
           // skriv ut("vest noden er null");
            return false;
        }
    }

// rekursiv metode for aa la hver rute spoerre naboer om veien
  public void gaa(int i, int j, String vei,Retninger ret){
    if(alleredeBesoekt(vei)){
       // skriv ut("gaar tilbake");
        return;
    }
    vei+=" --> "+koordTilStreng();
   // skriv ut(vei);

    if(tarSlutt(i, j)){
        //vei+=koordTilStreng();
        labyrint.alle_mulige_ruter.leggTil(vei);

        return;
    }

    if(ret!=Retninger.NORD){ // hvis det ikke kom fra nord
        if(this.kanGaaNord()){
         //   skriv ut("gaar nord");
            this.nord.gaa(this.nord.hentRad(),this.nord.hentKol(),vei,Retninger.SYD);
        }
    }

    if(ret!=Retninger.SYD){ // hvis den ikke kom fra syd
        if(this.kanGaaSyd()){
           // skriv ut("gaar syd");
            this.syd.gaa(this.syd.hentRad(),this.syd.hentKol(),vei,Retninger.NORD);
        }
    }

    if(ret!=Retninger.OEST){// hvis det ikke kom fra oest
        if(this.kanGaaOest()){
            //skriv ut("gaar oest");
            this.oest.gaa(this.oest.hentRad(),this.oest.hentKol(),vei,Retninger.VEST);
        }
    }

    if(ret!=Retninger.VEST){// hvis det ikke kom fra vest
        if(this.kanGaaVest()){
            //skriv ut("gaar vest ");
            this.vest.gaa(this.vest.hentRad(),this.vest.hentKol(),vei,Retninger.OEST);
        }
    }

}

// finn utvei ved aa gjenta gaa-metoden gjentatte ganger
  public void finnUtvei(){
    if (tarSlutt(this.rad, this.kol)) {
            // naar utvei er funnet, lagre den i listen
            //skriv ut("fant utvei");
            labyrint.alle_mulige_ruter.leggTil(koordTilStreng());
        return;
    }

    if (kanGaaNord()){
        //skriv ut("sjekker nord");
        nord.gaa(nord.hentRad(),nord.hentKol(),this.koordTilStreng() ,Retninger.SYD);
    }

    if (kanGaaSyd()){
        //skriv ut("sjekker syd");
        syd.gaa(syd.hentRad(),syd.hentKol(),this.koordTilStreng(),Retninger.NORD);
    }

    if (kanGaaOest()){
        //skriv ut("sjekker oest");
        oest.gaa(oest.hentRad(),oest.hentKol(),this.koordTilStreng() ,Retninger.VEST);
    }

    if (kanGaaVest()){
        //skriv ut("sjekker vest");
        vest.gaa(vest.hentRad(),vest.hentKol(),this.koordTilStreng() ,Retninger.OEST);
    }
}

public boolean alleredeBesoekt(String vei){
    return vei.contains(koordTilStreng());
}
// konverter koordinatene til String
private String koordTilStreng() {
    return "(" + rad + ", " + kol + ")";
}

  abstract char charTilTegn();

}
