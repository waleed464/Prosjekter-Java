//subklassen HviteResepter arver egenskaper fra klassen  Resepter.
//I tillegg, har den metoder farge() og prisAaBetale() som legger til
//farge og pris å betale.
public class HviteResepter extends Resepter{

    public HviteResepter(Legemiddel legemiddel,Lege utskrivendeLege, Pasient pasient, int reit){
        super(legemiddel,utskrivendeLege,pasient,reit);
    }

    public String farge(){
        return "Hvit";
    }

    public double prisAaBetale(){
        return this.legemiddel.hentPris();
    }

    public String toString(){
        return ("HviteResepte ID: " + id +"\nLegemiddel: "+ legemiddel.hentNavn() + "\nLege: " + utskrivendeLege.hentNavn())+ "\nPasient : " + pasient.hentNavn()+ "\nReit: " + reit + "\nPris å betale: "+prisAaBetale();
    }
}
