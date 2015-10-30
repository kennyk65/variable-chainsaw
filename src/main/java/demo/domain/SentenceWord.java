package demo.domain;

public class SentenceWord {

	private String word;
	
	private Role role;
	
	public SentenceWord(String word, Role role) {
		
		this.word = word;
		this.role = role;
	}

	public String getWord() {
		return word;
	}

	public Role getRole() {
		return role;
	}



	public static enum Role {
		
		subject,verb,article,adjetive,noun;
		
	}
}
