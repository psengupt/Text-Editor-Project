package document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * A naive implementation of the Document abstract class. 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document 
{
	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		//TODO: Implement this method.  See the Module 1 support videos 
	    // if you need help.
		String txt= super.getText();
		if(txt.equals(""))return 0;
		String[] sA = txt.split("[^A-Za-z]+");
		//for(String i:sA){ System.out.println(i);}
	    return sA.length;
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help.
		String txt= super.getText();
		if(txt.equals(""))return 0;
		String[] sA = txt.split("[!.?]+");
	    return sA.length;
       // return 0;
	}
	
	/**
	 * Get the number of syllables in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for a lone "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help
		String text = super.getText();
		int count = 0;
		String[] sA = text.split("[^A-Za-z]+");
		for(String txt: sA){
			txt = txt.toLowerCase();
			if(txt.length()==0)continue;
			if(txt.charAt(txt.length()-1)=='e'){
				if(silente(txt)){
					String nW = txt.substring(0, txt.length()-1);
					count+=counte(nW);
					//System.out.println(txt+" "+count);
				}else{
					count++;
					//System.out.println(txt+" "+count);
				}
			}else{
				count+=counte(txt);
				//System.out.println(txt+" "+count);
			}
		}
        return count;
	}
	
	boolean silente(String word){
		String nW = word.substring(0, word.length()-1);
		Pattern p = Pattern.compile("[aeiouy]");
		Matcher m = p.matcher(nW);
		return m.find();
	}
	int counte(String word){
		int count=0;
		Pattern p = Pattern.compile("[^aeiouy]*[aeiouy]+");
		Matcher m = p.matcher(word);
		while(m.find())count++;
		return count;
	}
	@Override
	public double getFleschScore()
	{
	    // TODO: Implement this method
		double ans = 206.858 - (1.015*getNumWords()/getNumSentences())-(84.6*getNumSyllables()/getNumWords());
	    return ans;
	}
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args)
	{
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue"), 2, 1, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
		
		
	}
	
}
