
//Oppgave 3c

class BilBruk3{
    public static void main(String[] args) {
        System.out.println("Hei, jeg er en bil!");
        Bil3 bilNummer = new Bil3("LL10892");
        Person John = new Person(bilNummer);
        John.skriver();
    }
}
