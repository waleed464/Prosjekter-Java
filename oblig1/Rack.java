//Rack har oversikt over nodene.
import java.util.ArrayList;

class Rack extends Dataklynge {
    // Lager tom arrayList som har oversikt over alle nodene.
    private  ArrayList<Node> listeNode;
	public Rack(int noderPerRack) {
		   super(noderPerRack);
	       this.listeNode =new  ArrayList<>();

    }

    //adderer ny node til nodeliste.
    public void addNode(Node node) {
    	listeNode.add(node);
    }

    //retunerer antall noder.
    public int hentNoder() {
		return listeNode.size();
    }

    //metoden retunerer antall prosessorer som nodene i Rack har tilsammen. Det gjør
    //metoden ved å gå gjennom alle noder.
    public int antProsessorer() {
    		int antallProsessorer = 0;
    		for (int i=0; i<listeNode.size(); i++) {
    			antallProsessorer += listeNode.get(i).antProsessorer();
    		}
    		return antallProsessorer;
    }

    //metoden retunerer antall noder (med påkrevd minne) ved å gå gjennom alle noder i rack
    public int noderMedNokMinne( int paakrevdMinne) {
		int antallNoder = 0 ;
		for (int i=0; i<listeNode.size(); i++) {
			if (listeNode.get(i).nokMinne(paakrevdMinne)){
				antallNoder += 1;
			}
		}
		return antallNoder ;
    }
}
