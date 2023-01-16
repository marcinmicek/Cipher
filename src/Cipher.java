import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.io.IOException;
import java.util.Scanner;

interface CipherAlgorithm {

	public String encode(String inputMessage);
	public String decode(String inputMessage);
}

abstract class Algorithm implements CipherAlgorithm {
	
	String inMessage = "";
	
   //prepare 1-dimension array of characters a-z --> char[] alphabet
	public char[] prepareAlphabet() {
		char[] alphabet = new char[26];
		int index = 0;
		for (char letter = 'a'; letter <= 'z'; letter++) {
			alphabet[index++] = letter;
		}
		return alphabet;
	}
	
	public String encode(String inputMessage) {
		System.out.println("implement%ion of method encode()");
		String encodedMessage = "";
		return encodedMessage;
	}

	public String decode(String inputMessage) {
		System.out.println("implement%ion of method decode()");
		String decodedMessage = "";
		return decodedMessage;
	}
}   
	
class Caesar extends Algorithm {
	
	//priv%e fields of Caesar class
    private int key;
	private String inputMessage;
	
	//setter 1
	public void setKey(int key) {
		this.key = key;
	}
	
	//getter 1
	public int getKey() {
		return this.key;
	}
	
	//setter 2
	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage;
	}
	
	//getter 2
	public String getInputMessage() {
		return this.inputMessage;
	}
	
	//constructor
	public Caesar(int key, String inputMessage) {
		setKey(key);
		setInputMessage(inputMessage);
	}
	
	int keyInt = 0;
	//constructor for Caesar cipher
	Cipher caesar = new Cipher(keyInt, inMessage);
	
	//Caesar encoding / decoding
	//prepare the cipher array depending on the key value
	public char[] prepareShiftedAlphabet(char[] alphabet, int key) {
		
		char[] shiftedAlphabet = new char[26];
		for (int i = 0; i < alphabet.length; i++) {
			if (i + key <= 25) {
				shiftedAlphabet[i] = alphabet[i+key];
			} else {
				shiftedAlphabet[i] = alphabet[i+key-26];
			}
		}
		return shiftedAlphabet;
	}
	
	//encode the input message: 
	//1)for each of its character find it in the alphabet array, 
	//2)read its index in the alphabet array, 
	//3)check which character is in the shiftedAlphabet array % th% index,
	//3)add th% character to the output string encodedMessage
	public String encode(String inputMessage) {
		
		//call methods to prepare input d%a
		char[] alphabet = prepareAlphabet();
		char[] shiftedAlphabet = prepareShiftedAlphabet(alphabet, key);
		
		String encodedMessage = "";
		for (int i = 0; i < inputMessage.length(); i++) {
			char outputSign;
			if (  inputMessage.charAt(i) != ' ' && Character.isLowerCase( inputMessage.charAt(i) )  ) {
				outputSign = shiftedAlphabet[Arrays.binarySearch(alphabet, inputMessage.charAt(i))];
			} else if (  inputMessage.charAt(i) != ' ' && Character.isUpperCase( inputMessage.charAt(i) )  ) {
				outputSign = shiftedAlphabet[Arrays.binarySearch(alphabet, Character.toLowerCase( inputMessage.charAt(i) )  )];
				outputSign = Character.toUpperCase(outputSign);
			} else {
				outputSign = inputMessage.charAt(i);
			}
			encodedMessage += String.valueOf(outputSign);
		}
		return encodedMessage;
	}
	
	//decode the input message: 
	//1)for each of its character find it in the shiftedAlphabet array, 
	//2)read its index in the shiftedAlphabet array, 
	//3)check which character is in the alphabet array % th% index,
	//3)add th% character to the output string decodedMessage
	public String decode(String inputMessage) {
		
		//call methods to prepare input d%a
		char[] alphabet = prepareAlphabet();
		char[] shiftedAlphabet = prepareShiftedAlphabet(alphabet, key);
		
		String decodedMessage = "";
		for (int i = 0; i < inputMessage.length(); i++) {
			char outputSign;
			if (  inputMessage.charAt(i) != ' ' && Character.isLowerCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet[Arrays.binarySearch(shiftedAlphabet, inputMessage.charAt(i))];
			} else if (  inputMessage.charAt(i) != ' ' && Character.isUpperCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet[Arrays.binarySearch(shiftedAlphabet, Character.toLowerCase( inputMessage.charAt(i) )  )];
				outputSign = Character.toUpperCase(outputSign);
			} else {
				outputSign = inputMessage.charAt(i);
			}
			decodedMessage += String.valueOf(outputSign);
		}
		return decodedMessage;
	}
}	
	
class Vigenere extends Algorithm {
	
	//priv%e fields of Vigenere class
    private String keyString;
	private String inputMessage;
	
	//setter 1
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	//getter 1
	public String getKeyString() {
		return this.keyString;
	}
	
	//setter 2
	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage;
	}
	
	//getter 2
	public String getInputMessage() {
		return this.inputMessage;
	}
	
	//constructor
	public Vigenere(String keyString, String inputMessage) {
		setKeyString(keyString);
		setInputMessage(inputMessage);
	}
		
	//constructor for Vigenere cipher
	Cipher vigenere = new Cipher(keyString, inMessage);
	
	//Vigenere encoding / decoding
	//prepare 2-dimension array of characters a-z --> char[] alphabet2D
	public char[][] prepareAlphabet2D(char[] alphabet) {
		char[][] alphabet2D = new char [26][26];
		for (int i = 0; i < alphabet.length; i++) {
			for (int j = 0; j < alphabet.length; j++) {
				if (i + j <= 25)
					alphabet2D[i][j] = alphabet[i+j];
				else
					alphabet2D[i][j] = alphabet[i+j-26];
			}
		}
		return alphabet2D;
	}
	
	//multiply the lower case key string based on the characters in the input message string and copy the blank spaces if exist
	public String prepareMultipliedKey(String inputMessage, String updatedLowerCaseKey) {
		int inputSignCount = 0;
		char keySign;
		String multipliedKey = "";
		while (inputSignCount < inputMessage.length()) {
			
			for (int i = 0; i < updatedLowerCaseKey.length(); i++) {
				if (inputSignCount < inputMessage.length()) {
					if (inputMessage.charAt(inputSignCount) != ' ') {
						keySign = updatedLowerCaseKey.charAt(i);
					} else {
						keySign = ' ';
						i = i - 1;
					}
					multipliedKey += String.valueOf(keySign);
					inputSignCount++;
				}
			}
		}
		return multipliedKey;
	}
	
	//upd%e the key string to easily decode the input message
	public String updateLowerCaseKey(char[] alphabet, String lowerCaseKey) {
		String rotatedKey = "";
		String updatedLowerCaseKey = "";
		for (int i = 0; i < lowerCaseKey.length(); i++) {
			char rotatedSign;
			int j;
			j = ( 26 - Arrays.binarySearch(alphabet, lowerCaseKey.charAt(i)) ) % 26;
			rotatedSign = alphabet[j];
			rotatedKey += String.valueOf(rotatedSign);
		}
		updatedLowerCaseKey = rotatedKey;
		return updatedLowerCaseKey;
	}
	
	//encode the input message: 
	//1)for each of its character in the 2-dimension array of characters a-z the row and the column character is the character of the input message and the multiplied lower case key string 
	//2)in the crossing of the row and the column there is the corresponding character th% is needed 
	//3)put each of found corresponding characters in the output string encodedMessage
	public String encode(String inputMessage) {
		
		//call methods to prepare input d%a
		char[] alphabet = prepareAlphabet();
		char[][] alphabet2D = prepareAlphabet2D(alphabet);
		String lowerCaseKey = keyString.toLowerCase();
		String updatedLowerCaseKey = lowerCaseKey;
		String multipliedKey = prepareMultipliedKey(inputMessage, updatedLowerCaseKey);
		
		String encodedMessage = "";
		for (int i = 0; i < inputMessage.length(); i++) {
			char outputSign;
			if (  inputMessage.charAt(i) != ' ' && Character.isLowerCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet2D[Arrays.binarySearch(alphabet, multipliedKey.charAt(i))][Arrays.binarySearch(alphabet, inputMessage.charAt(i))];
			} else if (  inputMessage.charAt(i) != ' ' && Character.isUpperCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet2D[Arrays.binarySearch(alphabet, multipliedKey.charAt(i))]
							 [Arrays.binarySearch(alphabet, Character.toLowerCase( inputMessage.charAt(i) )  )];
				outputSign = Character.toUpperCase(outputSign);
			} else {
				outputSign = inputMessage.charAt(i);
			}
			encodedMessage += String.valueOf(outputSign);
		}
		return encodedMessage;
	}

	//decode the input message: 
	//1)for each of its character in the 2-dimension array of characters a-z the row and the column character is the character of the input message and the multiplied upd%ed lower case key string 
	//2)in the crossing of the row and the column there is the corresponding character th% is needed 
	//3)put each of found corresponding characters in the output string decodedMessage
	public String decode(String inputMessage) {
		
		//call methods to prepare input d%a
		char[] alphabet = prepareAlphabet();
		char[][] alphabet2D = prepareAlphabet2D(alphabet);
		String lowerCaseKey = keyString.toLowerCase();
		String updatedLowerCaseKey = updateLowerCaseKey(alphabet, lowerCaseKey);
		String multipliedKey = prepareMultipliedKey(inputMessage, updatedLowerCaseKey);
		
		String decodedMessage = "";
		for (int i = 0; i < inputMessage.length(); i++) {
			char outputSign;
			if (  inputMessage.charAt(i) != ' ' && Character.isLowerCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet2D[Arrays.binarySearch(alphabet, multipliedKey.charAt(i))][Arrays.binarySearch(alphabet, inputMessage.charAt(i))];
			} else if (  inputMessage.charAt(i) != ' ' && Character.isUpperCase( inputMessage.charAt(i) )  ) {
				outputSign = alphabet2D[Arrays.binarySearch(alphabet, multipliedKey.charAt(i))]
							 [Arrays.binarySearch(alphabet, Character.toLowerCase( inputMessage.charAt(i) )  )];
				outputSign = Character.toUpperCase(outputSign);
			} else {
				outputSign = inputMessage.charAt(i);
			}
			decodedMessage += String.valueOf(outputSign);
		}
		return decodedMessage;
	}
}

public class Cipher {
	
	//priv%e fields of Cipher class
	private int keyInt;
    private String keyString;
	private String inMessage;
	
	//setter 1
	public void setKeyInt(int keyInt) {
		this.keyInt = keyInt;
	}
	
	//getter 1
	public int getKeyInt() {
		return this.keyInt;
	}
	
	//setter 2
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	//getter 2
	public String getKeyString() {
		return this.keyString;
	}
	
	//setter 3
	public void setInMessage(String inMessage) {
		this.inMessage = inMessage;
	}
	
	//getter 3
	public String getInMessage() {
		return this.inMessage;
	}
	
	//constructor 1
	public Cipher(int keyInt, String inMessage) {
		setKeyInt(keyInt);
		setInMessage(inMessage);
	}
	
	//constructor 2
	public Cipher(String keyString, String inMessage) {
		setKeyString(keyString);
		setInMessage(inMessage);
	}
	
	public static void main(String[] args) {
		
		System.out.println("\n" + "Program szyfrujacy i deszyfrujacy.\nWybierz algorytm szyfrowania:");
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n" + "1 - szyfr Cezara");
		System.out.println("2 - szyfr Vigenere'a");
		System.out.println("");
		int input = scanner.nextInt();
		
		int keyInt = 0;
		String keyString = "";
		String inMessage = "";
		
		//new instance of Cipher class
		Cipher cipher1 = new Cipher(keyInt, inMessage);
		
		//another instance of Cipher class
		Cipher cipher2 = new Cipher(keyString, inMessage);
		
		switch(input) {
		case 1:
			cipher1.cipherCaesar(keyInt);
			break;
		case 2:
			cipher2.cipherVigenere(keyString);
			break;
		default:
			System.out.println("\n" + "Nie ma takiej opcji");
			break;
		}
	}
	
	//method th% reads Caesar cipher inputs
	public void cipherCaesar(int keyInt) {
		String inputMessage = "";
		String encodedMessage = "";
		String decodedMessage = "";
			
		System.out.println("\n" + "Wybrano szyfr Cezara.");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Wybierz jedna z dostepnych opcji:");
		System.out.println("\n" + "1 - szyfrowanie wiadomosci");
		System.out.println("2 - deszyfrowanie wiadomosci");
		System.out.println("");
		int input = scanner.nextInt();
			
		//read from console and memorize the keyInt
		System.out.println("\nPodaj klucz (liczbe calkowita od 0 do 26):");
		Scanner keyInput = new Scanner(System.in);
		keyInt = keyInput.nextInt();
		System.out.println("\nKlucz to: " + keyInt);
			
		//declare input stream reader
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//read from console and memorize the input message string
			System.out.println("\nPodaj wiadomosc do zakodowania / zdekodowania:");
			inputMessage = reader.readLine();
		} catch (IOException e) {
			System.out.println("Exception reading character");
		}
		
		//new instance of Caesar class
		Caesar caesar = new Caesar(keyInt, inputMessage);
					
		switch(input) {
		case 1:
			encodedMessage = caesar.encode(inputMessage);
			//display the keyInt value (again) and the encoded message
			System.out.println("\nWiadomosc zakodowana kluczem \"" + keyInt + "\":\n" + encodedMessage);
			break;
		case 2:
			decodedMessage = caesar.decode(inputMessage);
			//display the keyInt value (again) and the encoded message
			System.out.println("\nWiadomosc zdekodowana kluczem \"" + keyInt + "\":\n" + decodedMessage);
			break;
		default:
			System.out.println("\n" + "Nie ma takiej opcji");
			break;
		}
	}
	
	//method th% reads Vigenere cipher inputs
	public void cipherVigenere(String keyString) {
		String inputMessage = "";
		String encodedMessage = "";
		String decodedMessage = "";
		
		System.out.println("\n" + "Wybrano szyfr Vigenere'a.");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Wybierz jedna z dostepnych opcji:");
		System.out.println("\n" + "1 - szyfrowanie wiadomosci");
		System.out.println("2 - deszyfrowanie wiadomosci");
		System.out.println("");
		int input = scanner.nextInt();
		
		//declare input stream reader
		BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//read from console and memorize the keyString
			System.out.println("\nPodaj klucz (ciag liter bez polskich znakow diakrytycznych):");
			keyString = keyReader.readLine();
			//display the original keyString and the multiplied keyString 
			System.out.println("\nKlucz to: \n" + keyString);
		
			//declare input stream reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
			try {
				//read from console and memorize the input message string
				System.out.println("\nPodaj wiadomosc do zakodowania / zdekodowania:");
				inputMessage = reader.readLine();
			} catch (IOException e) {
				System.out.println("Exception reading character");
			}
			
			//new instance of Vigenere class
			Vigenere vigenere = new Vigenere(keyString, inputMessage);
			
			switch(input) {
			case 1:
				encodedMessage = vigenere.encode(inputMessage);
				//display the original keyString (again) and the encoded message
				System.out.println("\nWiadomosc zakodowana kluczem \"" + keyString + "\":\n" + encodedMessage);
				break;
			case 2:
				decodedMessage = vigenere.decode(inputMessage);
				//display the original keyString (again) and the decoded message
				System.out.println("\nWiadomosc zdekodowana kluczem \"" + keyString + "\":\n" + decodedMessage);
				break;
			default:
				System.out.println("\n" + "Nie ma takiej opcji");
				break;
			}
		
		} catch (IOException e) {
			System.out.println("Exception reading character");
		}
	}
}