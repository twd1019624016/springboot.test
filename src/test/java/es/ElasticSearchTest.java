package es;

import java.util.Date;
import java.util.Iterator;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import start.Application;
import start.es.Article;
import start.es.ArticleSearchRepository;
import start.es.Author;
import start.es.Tutorial;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ElasticSearchTest {

	@Autowired
	private ArticleSearchRepository articleSearchRepository;
	
	@Test
	public void testSaveArticleIndex() {
		Author author = new Author();
		
		author.setId(1L);
		author.setName("天下无双");
		author.setRemark("java developer");
		
		Tutorial tutorial = new Tutorial();
		tutorial.setId(1L);
		tutorial.setName("elastic search");
		
		Article article = new Article();
		
		article.setId(1L);
		article.setTitle("springboot integreate elasticsearch");
		article.setAbstracts("springboot integreate elasticsearch is very easy");
		
		article.setTutorial(tutorial);
		article.setAuthor(author);
		
		article.setContent("elasticsearch based on lucene,"
				+ "sping-data-elasticsearch based on elaticsearch"
				+ ",this turotial tell you how to integrete springboot with "
				+ "spring-data-elasticsearch");
		
		article.setPostTime(new Date());
		
		article.setClickCount(1L);
		
		articleSearchRepository.save(article);
		
		
		
		
	}
	
	
	@Test
	public void testSearch(){
		String queryString="springboot";//搜索关键字
		QueryStringQueryBuilder builder=new QueryStringQueryBuilder(queryString);
		Iterable<Article> searchResult = articleSearchRepository.search(builder);
		Iterator<Article> iterator = searchResult.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
