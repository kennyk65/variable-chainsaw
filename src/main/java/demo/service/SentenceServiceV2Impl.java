package demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.functions.Action1;
import demo.domain.Sentence;
import demo.domain.SentenceWord;
import demo.domain.Word;

/**
 * Build a sentence by assembling randomly generated subjects, verbs, articles,
 * adjectives, and nouns. The individual parts of speech will be obtained by
 * calling the various DAOs.
 */
@Service
public class SentenceServiceV2Impl implements SentenceService {

	@Autowired
	SentenceWordService wordService;

	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {
		Sentence sentence = new Sentence();
		List<Observable<SentenceWord>> observables = createObservables();
		CountDownLatch latch = new CountDownLatch(observables.size());
		Observable
		.merge(observables)
		.subscribe(new Action1<SentenceWord>() {
	        @Override
	        public void call(SentenceWord sentenceWord) {
	            sentence.add(sentenceWord);
	            latch.countDown();
	        }
	    });
		waitForAll(latch);
		return sentence.toString();
	}


	private void waitForAll(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private List<Observable<SentenceWord>> createObservables(){
		List<Observable<SentenceWord>> observables = new ArrayList<>();
		observables.add(wordService.getSubject());
		observables.add(wordService.getVerb());
		observables.add(wordService.getArticle());
		observables.add(wordService.getAdjective());
		observables.add(wordService.getNoun());
		return observables;
	}
	
}
