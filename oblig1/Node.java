//I denne klassen beskriver vi Node. Node kommer til å ha et visst antall
//minne. I tillegg, skal den ha en eller to prosessorer.
class Node  {
	public int minneStorrelse;
	public int prosessorAntall;

    //Konstruktør
	public Node(int minneStorrelse, int prosessorAntall) {
        //Deklarerer instantsvariabelene:
		this.minneStorrelse = minneStorrelse; //størrelsen til minne
		this.prosessorAntall=prosessorAntall; //antall prosessorer
	}

    //Metodene returnerer verdien av antall prosessorer og minne størrelse
    public int antProsessorer() {
        return this.prosessorAntall;
    }
    public boolean nokMinne( int paakrevdMinne) {
        if (paakrevdMinne <= this.minneStorrelse){
            return true;
        }
        else {
        	return false;
        }
    }
}
