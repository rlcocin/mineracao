import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.br.BrazilianStemmer;
import org.apache.lucene.analysis.core.StopAnalyzer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.ArrayList;

public class IndexText {

	// Usado para retirar o stoplist
	static CharArraySet vazio = new CharArraySet(Version.LUCENE_40, 0, false);

	// Com steamming e stoplist
	private static BrazilianAnalyzer analyzer = new BrazilianAnalyzer(
			Version.LUCENE_40,vazio);

	

	private IndexWriter writer;
	private ArrayList<File> queue = new ArrayList<File>();

	public IndexText(String indexDir) throws IOException {
		FSDirectory dir = FSDirectory.open(new File(indexDir));

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40,
				analyzer);

		writer = new IndexWriter(dir, config);
	}
	
	public static BrazilianAnalyzer getBrazilianAnalyzer() {
		return analyzer;
	}
	
	

	public void indexFileOrDirectory(String fileName) throws IOException {
		addFiles(new File(fileName));

		int originalNumDocs = writer.numDocs();
		for (File f : queue) {
			FileReader fr = null;
			try {
				Document doc = new Document();

				fr = new FileReader(f);
				doc.add(new TextField("contents", fr));
				doc.add(new StringField("path", f.getPath(), Field.Store.YES));
				doc.add(new StringField("filename", f.getName(),
						Field.Store.YES));

				writer.addDocument(doc);
				System.out.println("Added: " + f);
			} catch (Exception e) {
				System.out.println("Could not add: " + f);
			} finally {
				fr.close();
			}
		}

		int newNumDocs = writer.numDocs();

		System.out.println((newNumDocs - originalNumDocs)
				+ " documentos adicionados");

		queue.clear();
	}

	private void addFiles(File file) {
		if (!file.exists()) {
			System.out.println(file + " does not exist.");
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFiles(f);
			}
		} else {
			String filename = file.getName().toLowerCase();
			
			if (filename.endsWith(".txt")) {
				queue.add(file);
			} else {
				System.out.println("Skipped " + filename);
			}
		}
	}


	public void closeIndex() throws IOException {
		writer.close();
	}
}
