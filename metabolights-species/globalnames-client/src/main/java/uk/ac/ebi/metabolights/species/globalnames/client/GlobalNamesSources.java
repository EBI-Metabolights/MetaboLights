package uk.ac.ebi.metabolights.species.globalnames.client;

public	enum GlobalNamesSources {
	ITIS(3, "ITIS"),
	NCBI(4, "NCBI"),
	Index_Fungorum(5, "IF"),
	WoRMS(9, "WORMS"),
	//ZooBank(132,),
	The_International_Plant_Names_Index(167, "IPNI");

	private final int dataSourceId;
	private final String prefix;

	GlobalNamesSources(int dataSourceId, String prefix) {
		this.dataSourceId = dataSourceId;
		this.prefix = prefix;
	}

	public int getDataSourceId() {
		return dataSourceId;
	}
}
