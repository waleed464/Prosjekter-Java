class Pasient  {
    public static int s_no=0;
    int id;
    String navn;
    String fødselsnummer;
    Stabel<Resepter> pasientensResepter=new Stabel<Resepter>();

    public Pasient(String navn, String fødselsnummer){
        this.navn=navn;
        this.fødselsnummer=fødselsnummer;

        this.id=s_no++;
    }

    public void leggeNyResept(Resepter p){
        this.pasientensResepter.leggPaa(p);
    }

    public Stabel<Resepter> hentReseptlisten(){
        return this.pasientensResepter;
    }

    public int hentPasientId(){
        return this.id;
    }

    public String hentNavn(){
        return this.navn;
    }

    public String hentFødselsnummer(){
        return this.fødselsnummer;
    }

}
