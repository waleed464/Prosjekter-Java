class MinTraad implements Runnable{
	
	Lenkeliste<Rute> sti;
	
	Rute r;
	
	public MinTraad(Rute r, Lenkeliste<Rute> sti){
		
		this.sti = sti;
		this.r=r;
	}

	public void run(){
		r.gaa(sti);
	}

}
