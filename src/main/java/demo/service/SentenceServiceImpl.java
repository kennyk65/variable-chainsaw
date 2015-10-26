package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import demo.domain.Word;

/**
 * Build a sentence by assembling randomly generated subjects, verbs, 
 * articles, adjectives, and nouns.  The individual parts of speech will 
 * be obtained by calling the various DAOs.
 */
@Service
public class SentenceServiceImpl implements SentenceService {

	@Autowired WordService wordService;
	

	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {
		
		//	The following code works, but not consistently.  First we launch
		//	three calls to the subject, adjective, and noun services:
		Observable<Word> observableSubject = wordService.getSubject();
		Observable<Word> observableAdjective = wordService.getAdjective();
		Observable<Word> observableNoun = wordService.getNoun();

		//	This middle part of the sentence doesn't have to be reactive,
		//	but I thought I would experiment:
		Observable<String> observableTheRest = Observable.just(
				wordService.getVerb().getString(),
				" ",
				wordService.getArticle().getString(),
				" "
		);	

		//	These StringBuffers are only used within the Subscribers below to grab 
		//	results out of the Observables.  With a Future, you'd just say "get()":		
		StringBuffer subject = new StringBuffer();
		StringBuffer adjective = new StringBuffer();
		StringBuffer noun = new StringBuffer();
		StringBuffer theRest = new StringBuffer();
		
		//	Grab the results.  Note that these subscribers 
		//	could fire at any time and in any order:
		observableSubject.subscribe( (w) -> { subject.append(w.getWord()); } );
		observableAdjective.subscribe( (w) -> { adjective.append(w.getWord()); } );
		observableNoun.subscribe( (w) -> { noun.append(w.getWord()); } );
		observableTheRest.subscribe( (s) -> { theRest.append(s); } );
		
		//	And here is the problem:  it is possible that one or more 
		//	of these items have not yet been populated by the time this 
		//	return statement is reached.  How to check?
		return subject + " " + theRest + " " + adjective + " " + noun;

	}	
	
	
//	public String buildSentence() {
//		
//		Observable<Word> observableSubject = wordService.getSubject();
//		Observable<Word> observableAdjective = wordService.getAdjective();
//		Observable<Word> observableNoun = wordService.getNoun();
//		
//		
//		StringBuffer sentence = new StringBuffer();
//
//		observableSubject.subscribe( (w) -> { sentence.append(w.getWord() + " "); } );
//
//		sentence.append(wordService.getVerb().getString());
//		sentence.append(" ");
//		sentence.append(wordService.getArticle().getString());
//		sentence.append(" ");
//
//		observableAdjective.subscribe( (w) -> { sentence.append(w.getWord() + " "); } );
//		observableNoun.subscribe( (w) -> { sentence.append(w.getWord()); } );
//
//		return sentence.toString();
//	}		
}
