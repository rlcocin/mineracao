import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Main {

	public static void main(String[] args) {

		String indexLocation = "C:/Users/Rafael/workspace_Mineracao/MineracaoDemo/index";

		IndexText indexer = null;
		try {
			indexer = new IndexText(indexLocation);

			indexer.indexFileOrDirectory("C:/Users/Rafael/workspace_Mineracao/MineracaoDemo/file");

			indexer.closeIndex();
		} catch (Exception ex) {
			System.out.println("Cannot create index..." + ex.getMessage());
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		s = "";
		while (!s.equalsIgnoreCase("q")) {
			try {
				IndexReader reader = DirectoryReader.open(FSDirectory
						.open(new File(indexLocation)));
				IndexSearcher searcher = new IndexSearcher(reader);
				TopScoreDocCollector collector = TopScoreDocCollector.create(29,
						true);
				
				

				System.out.println("Enter the search query (q=quit):");
				s = br.readLine();
				if (s.equalsIgnoreCase("q")) {
					break;
				}
				Query q = new QueryParser(Version.LUCENE_40, "contents",
						indexer.getAnalyzer()).parse(s);
				searcher.search(q, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;
				
				int totalhits = collector.getTotalHits();
						

				// 4. display results
				System.out.println("Found " + hits.length + " hits.");
				for (int i = 0; i < hits.length; ++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					System.out.println((i + 1) + ". " + d.get("path")
							+ " score=" + hits[i].score);
				}
				
				
				System.out.println("TotalHits: " + totalhits);

			} catch (Exception e) {
				System.out.println("Error searching " + s + " : "
						+ e.getMessage());
			}
		}
	}

}
