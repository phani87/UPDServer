package com.udp.project;

import java.io.BufferedReader;

import com.itextpdf.*;
import com.itextpdf.text.pdf.PdfArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class Server {

	public String malFormedRequest = "ENTS/1.0 Request";
	public String protocol = "1.0";
	public String folderName = "C://tmp";
	public String responseString;
	public String responseCode;
	

	public void server() throws IOException {
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			String response = getResponse(sentence);
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}

	public String getResponse(String sentence) throws IOException {

		String[] inputSplitNewLine = sentence.split("/r/n");
		String[] inputSplit = inputSplitNewLine[0].split("\\d");
		System.out.println(inputSplit[1]);
		if (malFormedRequest.equals(inputSplitNewLine[0].toString())) {

			if (protocol.equals(inputSplit[0])) {
				if (isfileFound(inputSplitNewLine[1])) {
					String Content = readFileContent(folderName + "//"
							+ inputSplitNewLine[1]);
					Integer length = readFileContentLength(folderName + "//"
							+ inputSplitNewLine[1]);
					responseCode = "0";
					responseString = malFormedRequest + "\\r\\n" + responseCode
							+ "\\r\\n" + length.toString() + "\\r\\n" + Content
							+ "\\r\\n" + "9";

					System.out.println(responseString);
				} else {
					Integer errorNo = 3;
					String Error = getError(errorNo);
					responseString = responseString = malFormedRequest
							+ "\\r\\n" + errorNo.toString() + "\\r\\n" + Error
							+ "\\r\\n" + "9";
				}
			} else {
				Integer errorNo = 4;
				String Error = getError(errorNo);
				responseString = responseString = malFormedRequest + "\\r\\n"
						+ errorNo.toString() + "\\r\\n" + Error + "\\r\\n"
						+ "9";
			}
		} else {
			Integer errorNo = 2;
			String Error = getError(errorNo);
			responseString = responseString = malFormedRequest + "\\r\\n"
					+ errorNo.toString() + "\\r\\n" + Error + "\\r\\n" + "9";
		}
		System.out.println(responseString);
		return responseString;
	}

	public boolean isfileFound(String name) {
		boolean fileFound = false;
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getAbsolutePath());
				if (listOfFiles[i].getName().equals(name)) {
					fileFound = true;
					break;
				} else {
					fileFound = false;
				}

			}
		}

		return fileFound;
	}

	public String readFileContent(String fileName) throws IOException {
		String everything;
		FileInputStream inputStream = new FileInputStream(fileName);
		try {
			everything = IOUtils.toString(inputStream);
			System.out.println(everything.length());
		} finally {
			inputStream.close();
		}
		return everything;

	}

	public int readFileContentLength(String fileName) throws IOException {
		String everything;
		FileInputStream inputStream = new FileInputStream(fileName);
		try {
			everything = IOUtils.toString(inputStream);
		} finally {
			inputStream.close();
		}
		return everything.length();

	}

	public String getError(Integer errorNo) {
		String avalue = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0,
				"OK. The response has been created according to the request.");
		map.put(1,
				"Error: integrity check failure. The request has one or more bit errors.");
		map.put(2,
				"Error: malformed request. The syntax of the request message is not correct.");
		map.put(3,
				"Error: non-existent file. The file with the requested name does not exist.");
		map.put(4,
				"Error: wrong protocol version. The version number in the request is different from 1.0");

		for (Integer aKey : map.keySet()) {
			if (aKey.equals(errorNo)) {
				avalue = map.get(aKey);
			}
		}
		return avalue;
	}

	/*
	 * public String integrityCheck() {
	 * 
	 * String str = "rohit"; short x[] = new short[((str.length()) / 2)];// all
	 * values inialised to // zero. short temp; // for 1st two characters 'r' &
	 * 'o' create the 16 bit value as: int ch = (int) str.charAt(0); // ch = 'r'
	 * int ch2 = (int) str.charAt(1); // ch = 'o' StringBuilder sb = new
	 * StringBuilder().append(ch).append(ch2);
	 * 
	 * String ascString = sb.toString(); Long asciiInt =
	 * Long.parseLong(ascString); System.out.println(asciiInt); return "";
	 * 
	 * }
	 */

	public void integrityCheck(String S) {

		int x[] = new int[S.length()];

		int p = 0;

		Integer s = 0;

		int index = 0;

		int c = 7919;

		int d = 65536;

		int length = 0;

		if (S.length() % 2 != 0)

		{

			// S += '0';

			length = S.length() / 2 + 1;

		}

		else

		{

			length = S.length() / 2;

		}

		// System.out.println("L: " + S.length());

		// System.out.println("Length: " + length);

		for (int i = 0; i < length; i++)

		{

			/*
			 * if(S.length()%2!=0)
			 * 
			 * {
			 * 
			 * if (i==length-1)
			 * 
			 * {
			 * 
			 * x[i]=(int) (S.charAt(p)<<8 | 0);
			 * 
			 * }
			 * 
			 * }
			 * 
			 * else
			 */

			if (p == (S.length() - 1))

			{

				x[i] = (int) (S.charAt(p) << 8 | 0);

				System.out.println("in bin:" + Integer.toBinaryString(x[i]));

				break;

			}

			else

			{

				// System.out.println("P: " + p);

				x[i] = (int) (S.charAt(p) << 8 | S.charAt(p + 1));

				p += 2;

				System.out.println("in bin:" + Integer.toBinaryString(x[i]));

			}

		}

		if ((p + 1) % 2 != 0)

		{

			// x[p]=

		}

		for (int j = 0; j < S.length() / 2; j++)

		{

			index = s ^ x[j];

			s = (c * index) % d;

			System.out.println(s);

		}

		String fu = s.toString();

		System.out.println(fu);

		/*
		 * int msg = 'A';
		 * 
		 * char *ptr = (char *)&msg;
		 * 
		 * msg = msg << 8;
		 * 
		 * msg |= 'B';
		 * 
		 * 
		 * 
		 * printf("First byte of msg: %c\n", ptr[0]);
		 * 
		 * printf("Second byte of msg: %c\n", ptr[1]);
		 */

	}

	// main method
	public static void main(String[] args) throws IOException {
		String sentence = "ENTS/1.0 Request/r/ndirectors_message.txt/r/n9/r/n";
		Server server = new Server();
		server.getResponse(sentence);
	}

}