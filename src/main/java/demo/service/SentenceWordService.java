package demo.service;

import rx.Observable;
import demo.domain.SentenceWord;

public interface SentenceWordService {

	Observable<SentenceWord> getSubject();
	Observable<SentenceWord> getVerb();
	Observable<SentenceWord> getArticle();
	Observable<SentenceWord> getAdjective();
	Observable<SentenceWord> getNoun();	

}
