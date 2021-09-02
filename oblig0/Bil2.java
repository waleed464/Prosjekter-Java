
//Oppgave 2

class Bil2{
    String carNr;
    Bil2(String carNummer){
        carNr=carNummer;
    }

    public void skriv(){
        System.out.println("Jeg er en bil!");
        System.out.println("Bilnummer: " + carNr);
    }
}
