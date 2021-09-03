import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Monitor {
	private final Lock laas = new ReentrantLock();
	private Lenkeliste<Lenkeliste<Rute>> muligStiList = new Lenkeliste<>();

	public void leggStiTilListen(Lenkeliste<Rute> r) {
		laas.lock();
		try{
			muligStiList.leggTil(r);
				
		} finally{
			laas.unlock();
		}

	}

	public Lenkeliste<Lenkeliste<Rute>> hentListe(){
		return muligStiList;
	}
}