//package gui;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopScoreDocCollector;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        String indexLocation = "C:/Users/Luiz/Workspace/mineracao/index";
//        IndexText indexer = null;
//        String docsLocation = "C:/Users/Luiz/Workspace/mineracao/file";
//
//        System.out.println("Deseja indexar os arquivos da pasta "
//                + docsLocation);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String s;
//        s = "";
//        try {
//            s = br.readLine();
//        } catch (IOException e1) {
//
//        }
//        if (s.equalsIgnoreCase("Y")) {
//
//            try {
//
//                indexer = new IndexText(indexLocation);
//
//                indexer.indexFileOrDirectory(docsLocation);
//                
//                
//                indexer.closeIndex();
//            } catch (Exception ex) {
//                System.out.println("N�o foi poss�vel criar o indexador..."
//                        + ex.getMessage());
//                System.exit(-1);
//            }
//        }
//
//        s = "";
//        while (!s.equalsIgnoreCase("q")) {
//            try {
//                IndexReader reader = DirectoryReader.open(FSDirectory
//                        .open(new File(indexLocation)));
//                IndexSearcher searcher = new IndexSearcher(reader);
//                TopScoreDocCollector collector = TopScoreDocCollector.create(10,
//                        true);
//
//                System.out.println("Digite a consulta:");
//                s = br.readLine();
//                if (s.equalsIgnoreCase("q")) {
//                    break;
//                }
//                Query q = new QueryParser(Version.LUCENE_40, "contents",
//                        indexer.getBrazilianAnalyzer()).parse(s);
//                searcher.search(q, collector);
//                ScoreDoc[] hits = collector.topDocs().scoreDocs;
//
//                int totalhits = collector.getTotalHits();
//
//                System.out.println(hits.length + " resultados encontrados.");
//                for (int i = 0; i < hits.length; ++i) {
//                    int docId = hits[i].doc;
//                    Document d = searcher.doc(docId);
//                    System.out.println((i + 1) + ". " + d.get("path")
//                            + " score=" + hits[i].score);
//                }
//
//				//System.out.println("TotalHits: " + totalhits);
//            } catch (Exception e) {
//                System.out.println("Error searching " + s + " : "
//                        + e.getMessage());
//            }
//        }
//    }
//
//}
