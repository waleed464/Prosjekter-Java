
class Hovedprogram {
    public static void main(String[] args) {

       Dataklynge abel =new Dataklynge("regneklynge.txt");

       //Sjekker hvor mange noder som har minst 32 GB, 64 GB og 128 GB minne.
       //Finner totalt antall prosessorer, og sjekker hvor mange rack som brukes.
       System.out.println("Noder med minst 32 GB:"+ Integer.toString(abel.noderMedNokMinne(32)));
       System.out.println("Noder med minst 64 GB:"+ Integer.toString(abel.noderMedNokMinne(64)));
       System.out.println("Noder med minst 128 GB:"+ Integer.toString(abel.noderMedNokMinne(128)));
       System.out.println("Antall prosessorer:"+ Integer.toString(abel.antProsessorer()));
       System.out.println("Antall rack:"+ Integer.toString(abel.numAntallRacks()));

    }
}
