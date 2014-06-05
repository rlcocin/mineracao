package gui;

import org.apache.lucene.analysis.util.StopwordAnalyzerBase;

public enum IndexEnum {
	DEFAULT_INDEX(Analyzers.getAnalyzer()), STOPWORDS_INDEX(Analyzers.getAnalyzerStopwords()),
	STEMMING_INDEX(Analyzers.getAnalyzerStemming()), STOPWORDS_STEMMING_INDEX(Analyzers.getAnalyzerCompleto());
	
	private Analyzers analyzers;
	
	private StopwordAnalyzerBase valor;
	
	IndexEnum(StopwordAnalyzerBase valor) {
		this.valor = valor;
	}
	
	public StopwordAnalyzerBase getValor() {
		return this.valor;
	}
}
