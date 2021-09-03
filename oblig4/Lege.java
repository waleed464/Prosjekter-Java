import java.util.ArrayList;
import java.util.Collections;

class Lege implements Comparable<Lege> {
    private String navn;
    private Lenkeliste<Resepter> skrevetutResepter=new Lenkeliste<Resepter>();


  public Lege(String navn) {
    this.navn = navn;
  }

  public String hentNavn() {
    return this.navn;
  }

  public void leggtilResept(Resepter r) {
    skrevetutResepter.leggTil(r);
  }

  public Lenkeliste<Resepter> hentResept() {
    return this.skrevetutResepter;
  }

  @Override
  public int compareTo(Lege annenLege){
    return  this.navn.compareTo(annenLege.navn);
    }

    public HviteResepter skrivHvitResept(Legemiddel legemiddel,Pasient pasient , int reit) throws UlovligUtskrift{
       if (this instanceof Spesialist){
        HviteResepter hvitResept =new HviteResepter(legemiddel, this, pasient, reit);
        this.leggtilResept(hvitResept);
        pasient.leggeNyResept(hvitResept);
        return hvitResept;
       }
        else if( legemiddel instanceof Narkotisk){
            throw new UlovligUtskrift(this , legemiddel);
        }
        else{
          HviteResepter hvitResept =new HviteResepter(legemiddel, this, pasient, reit);
          this.leggtilResept(hvitResept);
          pasient.leggeNyResept(hvitResept);
          return hvitResept;
        }
    }

    public P_resepter skrivPResept(Legemiddel legemiddel,Pasient pasient) throws UlovligUtskrift{
      if( this instanceof Spesialist){
        P_resepter p_resept =new P_resepter(legemiddel, this, pasient);
        this.leggtilResept(p_resept);
        pasient.leggeNyResept(p_resept);
        return p_resept;
      } 

      else if( legemiddel instanceof Narkotisk){
          throw new UlovligUtskrift(this , legemiddel);
      }
      
      else{
        P_resepter p_resept =new P_resepter(legemiddel, this, pasient);
        this.leggtilResept(p_resept);
        pasient.leggeNyResept(p_resept);
        return p_resept;
      }
  }

  public Militærresepter skrivMillitaerResept(Legemiddel legemiddel,Pasient pasient , int reit) throws UlovligUtskrift{
    if (this instanceof Spesialist){
      Militærresepter militærResept =new Militærresepter(legemiddel, this, pasient, reit);
      this.leggtilResept(militærResept);
      pasient.leggeNyResept(militærResept);
      return militærResept;
    } 
    
    else if( legemiddel instanceof Narkotisk){
        throw new UlovligUtskrift(this , legemiddel);
    }

    else{
      Militærresepter militærResept =new Militærresepter(legemiddel, this, pasient, reit);
      this.leggtilResept(militærResept);
      pasient.leggeNyResept(militærResept);
      return militærResept;
    }
  }

    public BlåResepter skrivBlaaResept(Legemiddel legemiddel,Pasient pasient , int reit) throws UlovligUtskrift{
      if (this instanceof Spesialist){
        BlåResepter blaaResept =new BlåResepter(legemiddel, this, pasient, reit);
        this.leggtilResept(blaaResept);
        pasient.leggeNyResept(blaaResept);
        return blaaResept;
      }
      
      else if( legemiddel instanceof Narkotisk){
          throw new UlovligUtskrift(this , legemiddel);
      }

      else{
        BlåResepter blaaResept =new BlåResepter(legemiddel, this, pasient, reit);
        this.leggtilResept(blaaResept);
        pasient.leggeNyResept(blaaResept);
        return blaaResept;
      }
}

  @Override
  public String toString() {
    return ("Type: Lege" + "\n" +
    "navn: " + hentNavn() + "\n");
  }

}