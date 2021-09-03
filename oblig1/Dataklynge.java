import java.io.*;
import java.util.ArrayList;

//Dataklyngen holder styr på alle rackene og nodene.
class Dataklynge {
	ArrayList<Rack> alleRack ; //lager en ny arraylist som har oversikt over alle rackene.
	public int noderPerRack;
	public int antallRack  ;

	public Dataklynge( int noderPerRack) {
		this.alleRack =  new ArrayList<>();
		this.noderPerRack = noderPerRack;
		this.antallRack = 0 ;
	}

	//Dataklyngen tar imot en fil.
	public Dataklynge( String file) {
		this.alleRack =  new ArrayList<>();
		int minneStorrelse;
		int prosessorAntall ;
		int antallNoder;
		int k=0;
		// Hvis filen finnes, alt kjøres som normalt ellers sendes det feilmelding.
		try{
			FileInputStream inputFil=new FileInputStream(file);
			DataInputStream inputData= new DataInputStream(inputFil);
			BufferedReader leser=new BufferedReader(new InputStreamReader(inputData));
			String linje;
			while((linje= leser.readLine())!= null){ //leser så lenge det ikke er tom linje.
				String[] data =linje.split(" "); //linje splittes opp til elementer i en array
				// det er i if-løkke vi havner som gir oss max antall noder per rack.
				if (data.length  == 1){
					this.noderPerRack = Integer.parseInt(data[0]); // Max antall noder per rack.
					System.out.println("Max antall noder per rack er "+ Integer.toString(this.noderPerRack)); //skriver ut max antall noder per rack
				}
				//vi havner i else-løkken hvis det ikke er oppgitt max antall noder per rack i filen som vi får som input fra bruker.
				//vi skal ellers egt. havne i if-løkke.
				else{
					minneStorrelse = Integer.parseInt(data[1]); // antall minne.
					prosessorAntall = Integer.parseInt(data[2]); // antall prosessorer.
					antallNoder=Integer.parseInt(data[0]); //antall noder
					for(k=0;k<antallNoder;k++){ //// her lages det nye noder alt etter antall med tilhørende minne og prosessor
						Node nyNode= new Node(minneStorrelse,prosessorAntall); //nye noden settes inn
							this.addNode( nyNode);
					}
				}
			}
			inputData.close(); //lukker filen

		//får feilmelding hvis ikke filen åpnes/gylig.
		}catch(Exception ioException){
			System.out.println("filen finnes ikke");
		}
	}

	public void addNode(Node node) {
		if (this.numAntallRacks() == 0) { //ingen racks i alleRack liste, oppretter vi et nytt Rack objekt i listen alleRack
			this.alleRack.add(new Rack(this.noderPerRack));
			this.alleRack.get(antallRack).addNode(node); //adderer inn node i det rackobjektet.
		}
		else if (this.alleRack.get(antallRack).hentNoder() >= this.noderPerRack) { //om racket er fult
			this.antallRack += 1; //Øker indeksen. Dette fører til for å bytte til et nytt rack.
			this.alleRack.add(new Rack(this.noderPerRack)); //plasserer inn et nytt rack objekt.
			this.alleRack.get(antallRack).addNode(node); //plasserer inn node i det aktuelle racket
		}
		else {
			this.alleRack.get(antallRack).addNode(node);
		}
}
    //Lager en metode antProsessorer() som returnerer det totale antall prosessorer i racklisten(alleRack).
	public int antProsessorer() {
		int totalAntallProsessorer = 0;
		for (int i=0; i<alleRack.size(); i++) { //For hver rack i racklisten(alleRack)
			totalAntallProsessorer += alleRack.get(i).antProsessorer() ; //Initier antProsessorer og legger det til i totalAntallProsessorer
		}
		return totalAntallProsessorer;
	}

	//Lager en metode noderMedNokMinne(int paakrevdMinne) som returnerer antall noder med minst paakrevdMinne GB minne.
	public int noderMedNokMinne(int paakrevdMinne) {
		int totalAntall = 0;
		for (int i=0; i<alleRack.size(); i++) {
			totalAntall += alleRack.get(i).noderMedNokMinne(paakrevdMinne);
		}
		return totalAntall;
	}

	//metoden retunerer antall rack.
	public int numAntallRacks() {
		return this.alleRack.size();
	}
}
