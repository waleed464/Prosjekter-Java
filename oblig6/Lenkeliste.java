import java.util.Iterator;
import java.util.ArrayList;
class Lenkeliste <T> implements Liste<T>{
    Node hode;
    public Lenkeliste(){
        hode = null;
    }

    // oppretter klassen Node
     public class Node{
         public T data ;
         public Node neste;

         public Node(T x){
            data=x;
            neste=null;
         }

         public Node(){
            data=null;
            neste=null;
         }
     }

      // adderer noden paa slutten
     public void leggTil(T x){

         Node p,q,nyNode;
         q=new Node();
         nyNode=new Node(x);

         if (nyNode == null){
             System.out.println("Minnefeil");
         }

         p= this.hode;
         //hvis listen er tom, adder den til hode
         if(this.hode == null){
             this.hode= nyNode;
         }

         //gaa til siste noden
         else{
             while(p!= null){
                q=p;
                p=p.neste;
             }
             q.neste = nyNode;
             nyNode.neste=p;
         }
     }

     //legg til paa spesifikk posisjon.
     public void leggTil(int pos, T x){

        if (pos < 0 || pos > this.stoerrelse()) {
			throw new UgyldigListeIndeks(pos);
		}
        int k=0;
        Node p,q,nyNode;
        q=new Node();
        nyNode=new Node(x);
        if (nyNode == null){
            System.out.println("Minnefeil");
        }

        p= this.hode;

        if(pos==0){
            nyNode.neste=p;
            this.hode=nyNode;
        }else{
            //transverserer til noden paa spesifisert posisjon
            while(p!= null && k<pos){
                k++;
                q=p;
                p=p.neste;
            }
            q.neste=nyNode;
            nyNode.neste=p;
        }
     }

     class LenkelisteIterator implements Iterator<T>{
    
        private Liste<T> liste;
        private int teller = 0;
    
        public LenkelisteIterator(Liste<T> lx) {
            liste = lx;
        }
        public boolean hasNext() {
            return teller < liste.stoerrelse();
        }
        public T next() {
            return liste.hent(teller++);
        }

        public void remove(){
        }
    } 
     //erstatte noden i den gitte posisjonen
     public void sett(int pos, T x){

        if (pos < 0 || pos >= this.stoerrelse()) {
			throw new UgyldigListeIndeks(pos);
		}
        int k=0;
        Node p,q,nyNode;
        q=new Node();
        nyNode=new Node(x);
        if (nyNode == null){
            System.out.println("Minnefeil");
        }

        p= this.hode;

        if(pos == 0){
            nyNode.neste=p;
            nyNode.neste=this.hode.neste;
            this.hode=nyNode;

        }else{
            //transverserer til noden paa spesifisert posisjon
            while(p!= null && k<pos){
            k++;
            q=p;
            p=p.neste;
            }
            q.neste=nyNode;
            nyNode.neste=p.neste;
         }
     }

     public T fjern(){
         
         Node p=this.hode;
         if(this.hode == null){
             //System.out.println("Listen er tom");
             throw new UgyldigListeIndeks(-1);
         }
         else{
             //slett hode
             this.hode= this.hode.neste;
         }
         return p.data;
     }
     public void fjernIkkeReturner(){

        Node p=this.hode;
        if(this.hode == null){
            //System.out.println("Listen er tom");
            throw new UgyldigListeIndeks(-1);
        }
        else{
            //slett hode
            this.hode= this.hode.neste;
        }       
}

     public void fjernIkkeReturner(int pos){

        if (pos < 0 || pos >= this.stoerrelse()) {
			throw new UgyldigListeIndeks(pos);
		}

        int k=0;
        Node q= new Node();
        Node p=this.hode;
        if(this.hode == null){
            System.out.println("Listen er tom");
        }

        else if(pos==0){
            if(this.hode == null){
                System.out.println("Listen er tom");
                throw new UgyldigListeIndeks(-1);
            }
            else{
                //slett hode
                this.hode= this.hode.neste;
            }
            
        }

        else{
            //gaa til den spesifikke posisjon.
            while(p.neste!=null&& (k<pos)){
                k++;
                q=p;
                p=p.neste;
            }
                if(p==null){
                    System.out.println("Posisjon eksisterer ikke");
                }
                else{
                    //fjernet det midterste elementet
                    q.neste=p.neste;
                }
        }
       }

       public T fjern(int pos){

        if (pos < 0 || pos >= this.stoerrelse()) {
			throw new UgyldigListeIndeks(pos);
		}

        int k=0;
        Node q= new Node();
        Node p=this.hode;
        if(this.hode == null){
            System.out.println("Listen er tom");
        }

        else if(pos==0){
            if(this.hode == null){
                System.out.println("Listen er tom");
                throw new UgyldigListeIndeks(-1);
            }
            else{
                //slett hode
                this.hode= this.hode.neste;
            }
            return p.data;
        }

        else{
            //gaa til den spesifikke posisjon.
            while(p.neste!=null&& (k<pos)){
                k++;
                q=p;
                p=p.neste;
            }
                if(p==null){
                    System.out.println("Posisjon eksisterer ikke");
                }
                else{
                    //fjernet det midterste elementet
                    q.neste=p.neste;
                }
        }
        return p.data;
       }

     public int stoerrelse(){
         int teller =0;
         Node naavaerende= this.hode;

         //telle til null
         while(naavaerende!=null){
             teller++;
             naavaerende=naavaerende.neste;
         }
        return teller;
     }

     public T hent(int pos) {
         int k=0;
         if (pos < 0 || pos >= this.stoerrelse()) {
			throw new UgyldigListeIndeks(pos);
		}

         Node p=new Node();
         p=this.hode;
         if(this.hode == null){
             System.out.println("Listen er tom");
         }
         else{
             if(pos==(this.stoerrelse())){
                while(p.neste!=null){
                    p=p.neste;
                   }

             }else{
                while(p.neste!=null&& k<pos){
                    //System.out.println("hei");
                    k++;
                    p=p.neste;
                   }
             }
             }
         return p.data;
     }

     public void skrivutListe()
     {
         Node naaNode = this.hode;

         //System.out.print("Lenkeliste: ");

         // Gaar gjennom Lenkeliste
         while (naaNode != null) {

             // Skriver ut data paa naavaerende node
             System.out.print(naaNode.data + " ");

             // Gaar til neste node
             naaNode = naaNode.neste;
         }
          System.out.print( "\n");
     }

     public boolean inneholder( T x){
          for(T r:this){
              if(r==x){
                  return true;
              }
          }
          return false;
     }

     //kopierer elementet i en annen liste til denne listen
     public void kopierElementer(Lenkeliste<T> elem) {
        for (T x : elem) {
            leggTil(x);
        }
        }
    

    @Override
     public Iterator iterator(){
        return new LenkelisteIterator(this);
    }
    
}
