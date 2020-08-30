package com.service1;

public class Encryption {
	
	
	public static void main(String[] args) {
		String letter="hey! my name is Sagar Das.";
		String a="";
		System.out.println(letter);
		char[] charArray=letter.toCharArray();
		for(char c:charArray) {
			c+=5;
			a+=c+"";
			System.out.print(c);	
		}
		System.out.println("----------------------------------------");
		char[] charArrayDe=a.toCharArray();
		
		
		for(char c:charArrayDe) {
			c-=5;
			
			System.out.print(c);	
		}
		
	}

}
