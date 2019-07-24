package com.alijabobija.Entity;

/**
 * Entity class for Relation
 */
public class Relation {
	private Word word1;
	private Word word2;
	
	public Relation() {}
	
	public Relation(Word w1, Word w2) {
		this.word1 = w1;
		this.word2 = w2;
	}
	
	public Word getWord1() {
		return word1;
	}
	public void setWord1(Word word1) {
		this.word1 = word1;
	}
	public Word getWord2() {
		return word2;
	}
	public void setWord2(Word word2) {
		this.word2 = word2;
	}
}
