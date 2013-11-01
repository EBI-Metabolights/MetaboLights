package uk.ac.metabolights.species.readers;

/**
 * User: conesa
 * Date: 31/10/2013
 * Time: 10:04
 */
public class GenericTaxonomyReaderConfig {

	public enum KnownConfigs{
		NEWT(new GenericTaxonomyReaderConfigDataStructure(new String[]{"NEWT","2013-10", "Uniprot taxonomy:Organisms are classified in a hierarchical tree structure. Our taxonomy database contains every node (taxon) of the tree. UniProtKB taxonomy data is manually curated.", "0,2,3,9" }));

		private GenericTaxonomyReaderConfigDataStructure dataStructure;

		KnownConfigs(GenericTaxonomyReaderConfigDataStructure dataStructure){
			this.dataStructure = dataStructure;
		}

		public GenericTaxonomyReaderConfigDataStructure getDataStructure() {
			return dataStructure;
		}
	}

	private GenericTaxonomyReaderConfigDataStructure configDataStructure;
	private String taxonomyPath;

	public GenericTaxonomyReaderConfig(String taxonomyPath, GenericTaxonomyReaderConfigDataStructure configDataStructure){

		this.configDataStructure = configDataStructure;
		this.taxonomyPath = taxonomyPath;

	}

	public GenericTaxonomyReaderConfig(String taxonomyPath, KnownConfigs knownConfigs){

		this.taxonomyPath = taxonomyPath;
		this.configDataStructure = knownConfigs.dataStructure;

	}
	public GenericTaxonomyReaderConfig(String taxonomyPath){
		this.taxonomyPath = taxonomyPath;
	}

	public GenericTaxonomyReaderConfigDataStructure getConfigDataStructure() {
		return configDataStructure;
	}

	public void setConfigDataStructure(GenericTaxonomyReaderConfigDataStructure configDataStructure) {
		this.configDataStructure = configDataStructure;
	}

	public String getTaxonomyPath() {
		return taxonomyPath;
	}

	public void setTaxonomyPath(String taxonomyPath) {
		this.taxonomyPath = taxonomyPath;
	}
}
