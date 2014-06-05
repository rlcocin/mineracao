package gui;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class Analyzers {

	// Usado para retirar o stoplist
	static CharArraySet vazio = new CharArraySet(Version.LUCENE_40, 0, false);

	// Com stemming e stopwords
	private static BrazilianAnalyzer analyzerCompleto = new BrazilianAnalyzer(
			Version.LUCENE_40);
	
	// Com stemming
	private static BrazilianAnalyzer analyzerStemming = new BrazilianAnalyzer(
			Version.LUCENE_40, vazio);
	
	// Com stopwords
	private static StandardAnalyzer analyzerStopwords = new StandardAnalyzer(
			Version.LUCENE_40);
	
	//Sem nada
	private static StandardAnalyzer analyzer = new StandardAnalyzer(
			Version.LUCENE_40, vazio);

	public static BrazilianAnalyzer getAnalyzerStemming() {
		return analyzerStemming;
	}
	
	public static BrazilianAnalyzer getAnalyzerCompleto() {
		return analyzerCompleto;
	}

	public static StandardAnalyzer getAnalyzerStopwords() {
		return analyzerStopwords;
	}
	
	public static StandardAnalyzer getAnalyzer() {
		return analyzer;
	}
}
