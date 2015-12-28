package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
	private final static String ZIPCODE_PATTERN ="(^[ABCEGHJKLMNPRSTVXY]{1}[0-9]{1}[A-Z]{1}\\s[0-9]{1}[A-Z]{1}[0-9]{1}$)|(^\\d{5}(-\\d{4})?$)";
	private final static String ZIPCODE_INDIA = "^([0-9]{6})$";
	
	public static boolean specialCharacterValidation(String str){
		String splChrs = "-/!@#$%^&*_+=()?<:;'>~`.," ;
		return !(str.matches("[" + splChrs + "]+"));
		
		/*Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		boolean b = m.find();
		return !b;*/
	}
	
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	
	public static boolean isValidUsername(String username) {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);

		return matcher.matches();
	}

	public static boolean isValidFirstname(String username) {
		if (username.toString().matches("[a-zA-Z ]+") && !username.equals("") && username.length() > 1)
			return true;
		else
			return false;
	}
	
	public static boolean isValidPassword(final String password) {

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
	public static boolean isValidZipCode(final String zipCode) {
		//Log.v("zipCode", zipCode);
		
		if  (zipCode.matches(ZIPCODE_PATTERN) || zipCode.matches(ZIPCODE_INDIA)) 
			return true;
		else 
			return false;
		
	}
}
