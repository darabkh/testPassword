package testpassword.strongpassword;

import org.apache.log4j.Logger;

class Password {
	
	final static Logger log = Logger.getLogger(Password.class);
	private static final String SPECIAL_CHARACTERS_ALLOWED = "!@#$&*";
	private static final int MIN_PASSWORD_SIZE = 18;
	private static final int MAX_DUPLICATE_ALLOWED = 4;
	private static final int MAX_SPECIAL_CHARACTERS_ALLOWED = 4;

	private static boolean containsUpperCase(String password) {
		int length = password.length();
		for (int i = 0; i < length; i++) {
			if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
				return true;
			}
		}
		return false;
	}

	private static boolean containsLowerCase(String password) {
		int length = password.length();
		for (int i = 0; i < length; i++) {
			if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
				return true;
			}
		}
		return false;
	}

	private static boolean containsNumeric(String password) {
		int length = password.length();
		for (int i = 0; i < length; i++) {
			if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
				return true;
			}
		}
		return false;
	}

	private static boolean containsSpecialCharacter(String password) {
		int length = password.length();
		for (int i = 0; i < length; i++) {
			if (isSpecialCharacter(password.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private static boolean duplicateCharacters(String password) {
		int count[] = new int[256];
		int length = password.length();
		for (int i = 0; i < length; i++) {
			count[password.charAt(i)]++;
			if (count[password.charAt(i)] > MAX_DUPLICATE_ALLOWED) {
				return true;
			}
		}
		return false;
	}

	private static int specialCharactersCount(String password) {
		int count = 0;
		int length = password.length();
		for (int i = 0; i < length; i++) {
			String currentCharacter = null;
			currentCharacter = "" + password.charAt(i);
			if (SPECIAL_CHARACTERS_ALLOWED.contains(currentCharacter)) {
				count++;
			}
		}
		return count;
	}

	private static boolean isSpecialCharacter(char c) {
		String currentCharacter = "" + c;
		return SPECIAL_CHARACTERS_ALLOWED.contains(currentCharacter);
	}

	private static boolean areCharactersValid(String password) {
		int length = password.length();
		boolean isValid = true;
		for (int i = 0; i < length; i++) {
			char c = password.charAt(i);
			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || isSpecialCharacter(c)) {
				isValid = true;
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean isNumberRatioOk(String password) {
		int length = password.length();
		int count = 0;
		for (int i = 0; i < length; i++) {
			char c = password.charAt(i);
			if (c >= '0' && c <= '9') {
				count++;
			}
		}
		return count <= (length / 2);
	}

	private static boolean isPasswordValid(String password) {
		if (password == null || password == "") {
			log.error("Password is blank");
			return false;
		}
		if (!areCharactersValid(password)) {
			log.error("Password contains invalid characters");
			return false;
		}
		log.info("Password contains valid characters");
		if (password.length() < MIN_PASSWORD_SIZE) {
			log.error("password size is small");
			return false;
		}
		log.info("password size is ok");
		if (!containsUpperCase(password)) {
			log.error("password doesnt contain upper case character");
			return false;
		}
		log.info("password contains upper case character");
		if (!containsLowerCase(password)) {
			log.error("password doesnt contain lower case character");
			return false;
		}
		log.info("password contains lower case character");
		if (!containsNumeric(password)) {
			log.error("password doesnt contain number");
			return false;
		}
		log.info("password contains number");
		if (!containsSpecialCharacter(password)) {
			log.error("password doesnt contain special character");
			return false;
		}
		log.info("password contains special character");
		if (duplicateCharacters(password)) {
			log.error("password contains 4 duplicate characters");
			return false;
		}
		log.info("password doesnt contain 4 duplicate characters");
		if (specialCharactersCount(password) > MAX_SPECIAL_CHARACTERS_ALLOWED) {
			log.error("password contains more than 4 special characters");
			return false;
		}
		log.info("password doesnt contain more than 4 special characters");
		if (!isNumberRatioOk(password)) {
			log.error("password contains more than 50% numbers");
			return false;
		}
		log.info("password doesnt contain more than 50% numbers");
		log.debug("Password : " + password + " is valid");
		return true;
	}

	private static String getCurrentPasswordEncrypted() {
		String currentPasswordEncrypted = "oldPasswordsecure345678@!@!"; //get from DB
		log.info("current encrypted password is : " + currentPasswordEncrypted);
		return currentPasswordEncrypted;
	}

	private static String encryptPassword(String password) {
		log.info("Encrypting password");
		return password;
	}

	private static boolean isOldPasswordCorrect(String oldPassword) {
		String currentPasswordEncrypted = getCurrentPasswordEncrypted();
		String oldPasswordEncrypted = encryptPassword(oldPassword);
		log.info("Check if old password is correct");
		return currentPasswordEncrypted.equals(oldPasswordEncrypted);
	}

	private static boolean updatePassword(String newPassword) {
		log.info("Update password in database");
		return true;
	}

	public static boolean changePassword(String oldPassword, String newPassword) {

		if (!isOldPasswordCorrect(oldPassword)) {
			log.error("Failed to update password : old password doesn't match");
			return false;
		}
		log.info("old password match success");
		if (!isPasswordValid(newPassword)) {
			log.error("Failed to update password : new password is not valid");
			return false;
		}
		log.info("New password is valid");
		double similarityIndex = StringHelper.similarity(oldPassword, newPassword);
		log.info("Similarity: " + similarityIndex);
		if (similarityIndex >= 0.8) {
			log.error("Failed to update password : password is similar :" + similarityIndex);
			return false;
		}
		log.info("New password is different from old");
		return updatePassword(newPassword);
		
	}
}