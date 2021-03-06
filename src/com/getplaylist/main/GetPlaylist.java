package com.getplaylist.main;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

public class GetPlaylist {
	public static final int TYPE_NHACCUATUI = 0;
	public static final int TYPE_MP3ZING = 1;

	public static final String REGEX_XMLKEY_nhaccuatui = "(http://www.nhaccuatui.com/flash/xml\\?key)(.+?)(\")";
	public static final String REGEX_XMLKEY_mp3Zing = "(data-xml=\")(.+?)(\")";
	public static final String REGEX_MP3LIST_nhaccuatui = "(<location>    <!\\[CDATA\\[)(.+?)(mp3)";
	public static final String REGEX_MP3LIST_mp3Zing = "(<source><!\\[CDATA\\[)(.+?)(]]></source>)";
	Logger logger = Logger.getLogger("GetPlayList.java");

	private String prepareUrl(String rawUrl) {
		return rawUrl;

	}

	public String getXmlKey(String respone, int siteType) {
		ArrayList<String> resultList = new ArrayList<String>();
		boolean isMatch = true;
		String xmlKey = "";
		String sPattern = "";
		Pattern pattern = null;
		Matcher matcher = null;
		switch (siteType) {
		case TYPE_NHACCUATUI:
			sPattern = REGEX_XMLKEY_nhaccuatui;
			pattern = Pattern.compile(sPattern);
			matcher = pattern.matcher(respone);
			while (matcher.find()) {
				resultList.add(matcher.group(1) + matcher.group(2));
			}
			break;
		case TYPE_MP3ZING:
			sPattern = REGEX_XMLKEY_mp3Zing;
			System.out.println(respone);
			pattern = Pattern.compile(sPattern);
			matcher = pattern.matcher(respone);
			while (matcher.find()) {
				resultList.add(matcher.group(2));
			}
			break;
		default:
			break;
		}

		if (resultList.size() > 0) {
			if (resultList.size() >= 1) {
				for (int i = 0; i < resultList.size() - 1; i++) {
					if (!resultList.get(i).equals(resultList.get(i + 1))) {
						isMatch = false;
					}
				}
			}

			if (isMatch) {
				xmlKey = resultList.get(0);
				logger.log(Level.INFO, "getXmlKey. RESULT OK. Size = "
						+ resultList.size());
			} else {
				logger.log(Level.INFO, "getXmlKey. RESULT NOTMATCH. Size = "
						+ resultList.size() + "" + ". Got first element");
				xmlKey = resultList.get(0);
			}
		} else {
			logger.log(Level.INFO, "getXmlKey. RESULT FAIL.");
		}
		return xmlKey;

	}

	public String getRespone(String url, boolean isGzipCompression)
			throws Exception {
		System.out.println("getResponse URL" + url);
		InputStream inputStream = null;
		if (url == null || url.equals("")) {
			return "";
		}
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		if (isGzipCompression) {
			inputStream = new GZIPInputStream(con.getInputStream());
		} else {
			inputStream = con.getInputStream();
		}

		Reader reader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader in = new BufferedReader(reader);
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();

	}

	public ArrayList<String> getMp3ListFromJSonData(String response,
			int siteType) {
		switch (siteType) {
		case TYPE_NHACCUATUI:
			return getMp3ListFromJsonData_Nhaccuatui(response);
		case TYPE_MP3ZING:
			return getMp3ListFromJsonData_mp3Zing(response);
		default:
			break;
		}

		return null;
	}

	private ArrayList<String> getMp3ListFromJsonData_mp3Zing(String respone) {
		ArrayList<String> list = new ArrayList<String>();
		Pattern pattern = Pattern
				.compile(REGEX_MP3LIST_mp3Zing);
		Matcher m = pattern.matcher(respone);
		while (m.find()) {
			list.add(m.group(2));
			;
		}
		;
		return list;

	}

	private ArrayList<String> getMp3ListFromJsonData_Nhaccuatui(String respone) {
		ArrayList<String> list = new ArrayList<String>();
		Pattern pattern = Pattern
				.compile(REGEX_MP3LIST_nhaccuatui);
		Matcher m = pattern.matcher(respone);
		while (m.find()) {
			list.add(m.group(2) + m.group(3));
		}

		return list;
	}

	public ArrayList<String> getMp3List(String url, int siteType,
			boolean isGzipCompression) throws Exception {
		String tmpResponseContents;
		String xmlKey;
		ArrayList<String> responseContents = new ArrayList<String>();
		System.out.println("url" + url +"isCmpree" +isGzipCompression);
		tmpResponseContents = getRespone(url, isGzipCompression);
		responseContents.add(tmpResponseContents);
		System.out.println("content: "+ tmpResponseContents);
		xmlKey = getXmlKey(responseContents.get(responseContents.size() - 1),
				siteType);
		tmpResponseContents = getRespone(xmlKey, isGzipCompression);
		responseContents.add(tmpResponseContents);
		return getMp3ListFromJSonData(
				responseContents.get(responseContents.size() - 1), siteType);

	}

	public ArrayList<String> getMp3List(String rawUrl) throws Exception {
		String url = rawUrl.replaceAll("\\s", "");//remove all spaces
		if (!rawUrl.contains("http://")&&!rawUrl.contains("https://")) {
			url = "http://" + rawUrl;
		}
		
		if (url.contains("mp3.zing.vn")) {
			return getMp3List(url, TYPE_MP3ZING, true);
		}

		if (url.contains("nhaccuatui.com")) {
			return getMp3List(url, TYPE_NHACCUATUI, false);
		}

		return null;
	}

	public static void main(String[] args) {
		GetPlaylist getPlaylist = new GetPlaylist();
		try {

			ArrayList<String> resultList = getPlaylist
					.getMp3List("http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-"
							+ "ca-khuc-hay-cua-cac-nhom-nhac-vol-3-va.zFriPcrlzd0C.html");
			for (String string : resultList) {
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}
}
