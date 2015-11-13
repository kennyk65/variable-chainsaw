package demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

import demo.dao.AdjectiveClient;
import demo.dao.ArticleClient;
import demo.dao.NounClient;
import demo.dao.SubjectClient;
import demo.dao.VerbClient;
import demo.domain.SentenceWord;
import demo.domain.Word;
import demo.domain.SentenceWord.Role;

@Service
public class SentenceWordServiceImpl implements SentenceWordService {

	@Autowired VerbClient verbClient;
	@Autowired SubjectClient subjectClient;
	@Autowired ArticleClient articleClient;
	@Autowired AdjectiveClient adjectiveClient;
	@Autowired NounClient nounClient;
	
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackSubject")
	public Observable<SentenceWord> getSubject() {
	    return new ObservableResult<SentenceWord>() {
	        @Override
	        public SentenceWord invoke() {
	        	Word word = subjectClient.getWord();
	        	return new SentenceWord(word.getString(),Role.subject);
	        }
	    };
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackVerb")
	public Observable<SentenceWord> getVerb() {
		 return new ObservableResult<SentenceWord>() {
		        @Override
		        public SentenceWord invoke() {
		        	Word word = verbClient.getWord();
		        	return new SentenceWord(word.getString(),Role.verb);
		        }
		    };
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackArticle")
	public Observable<SentenceWord> getArticle() {
		 return new ObservableResult<SentenceWord>() {
		        @Override
		        public SentenceWord invoke() {
		        	Word word = articleClient.getWord();
		        	return new SentenceWord(word.getString(),Role.article);
		        }
		    };
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackAdjective")
	public Observable<SentenceWord> getAdjective() {
		 return new ObservableResult<SentenceWord>() {
		        @Override
		        public SentenceWord invoke() {
		        	Word word = adjectiveClient.getWord();
		        	return new SentenceWord(word.getString(),Role.adjetive);
		        }
		    };
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackNoun")
	public Observable<SentenceWord> getNoun() {
		 return new ObservableResult<SentenceWord>() {
		        @Override
		        public SentenceWord invoke() {
		        	Word word = nounClient.getWord();
		        	return new SentenceWord(word.getString(),Role.noun);
		        }
		    };
	}	
	
	
	
	public SentenceWord getFallbackSubject() {
		return new SentenceWord("Someone",Role.subject);
	}
	
	public SentenceWord getFallbackVerb() {
		return new SentenceWord("does",Role.verb);
	}
	
	public SentenceWord getFallbackAdjective() {
		return new SentenceWord("",Role.adjetive);
	}
	
	public SentenceWord getFallbackArticle() {
		return new SentenceWord("",Role.article);
	}
	
	public SentenceWord getFallbackNoun() {
		return new SentenceWord("something",Role.noun);
	}

}
