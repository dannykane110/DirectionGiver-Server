package ntou.jt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetWeather 
{
	public static final String TARGET_URL = "http://www.cwb.gov.tw/V7/forecast/week/week.htm";
	public static final String PREFIX = "../../";
	public static final String NEW_PREFIX = "http://www.cwb.gov.tw/V7/";
	public static final String All_Weather = "http://www.cwb.gov.tw/V7/forecast/week/w11.htm";
	public static final String unAdd_City = "http://www.cwb.gov.tw/V7/forecast/taiwan/";
	public static TemperatureBundle getTemperature (String location)
			throws IOException, IndexOutOfBoundsException
	{
		Connection.Response res = Jsoup
				.connect(TARGET_URL)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0")
				.method(Method.GET).execute();
		Document doc = res.parse();

		Element weekDays = doc.select("tr:contains(�P����)").get(0);
		String weekDates[] = getWeekDates(weekDays);

		Element dayWeathers = doc.select("tr:contains(" + location + ")")
				.get(0);
		Element nightWeathers = dayWeathers.nextElementSibling();

		String dayInfo[][] = getTempratureInfo(dayWeathers);
		String nightInfo[][] = getTempratureInfo(nightWeathers);

		TemperatureBundle tempBundle = new TemperatureBundle(location,
				weekDates, dayInfo[0], nightInfo[0], dayInfo[1], nightInfo[1]);
		return tempBundle;
	}
	
	public static String getAllWeather() throws IOException
	{
		Connection con = Jsoup.connect(All_Weather).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000);
	    Connection.Response resp = con.execute();
	    Document doc = null;
	    if (resp.statusCode() == 200) 
	    {
	        doc = con.get();
	    }

		Element allWeathers = doc.select("pre")
				.get(0);

	    
	    String content = allWeathers.html();
	    //System.out.println(content);
	    content = content.substring(content.lastIndexOf("�Ѯ𷧪p�G"));
	    content = content.replaceAll("�Ѯ𷧪p�G", "");
		return content;
	}
	public static String getWeather(String location,String date) throws IOException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     //get current date time with Date()
	    Date dateToday = new Date();	     		//System.out.println(dateFormat.format(dateToday));
	    String content = "";
		String weatherUrl = unAdd_City;
		switch(location)
		{
		case "�򶩥�":weatherUrl += "Keelung_City.htm";break;
		case "�O�_��":weatherUrl += "Taipei_City.htm";break;
		case "�s�_��":weatherUrl += "New_Taipei_City.htm";break;
		case "��饫":weatherUrl += "Taoyuan_City.htm";break;
		case "�s�˥�":weatherUrl += "Hsinchu_City.htm";break;
		case "�s�˿�":weatherUrl += "Hsinchu_County.htm";break;
		case "�]�߿�":weatherUrl += "Miaoli_County.htm";break;
		case "�x����":weatherUrl += "Taichung_City.htm";break;
		case "���ƿ�":weatherUrl += "Changhua_County.htm";break;
		case "�n�뿤":weatherUrl += "Nantou_County.htm";break;
		case "���L��":weatherUrl += "Yunlin_County.htm";break;
		case "�Ÿq��":weatherUrl += "Chiayi_City.htm";break;
		case "�Ÿq��":weatherUrl += "Chiayi_County.htm";break;
		case "�y����":weatherUrl += "Yilan_County.htm";break;
		case "�Ὤ��":weatherUrl += "Hualien_County.htm";break;
		case "�x�F��":weatherUrl += "Taitung_County.htm";break;
		case "�O�n��":weatherUrl += "Tainan_City.htm";break;
		case "������":weatherUrl += "Kaohsiung_City.htm";break;
		case "�̪F��":weatherUrl += "Pingtung_County.htm";break;
		case "�s����":weatherUrl += "Lienchiang_County.htm";break;
		case "������":weatherUrl += "Kinmen_County.htm";break;
		case "���":weatherUrl += "Penghu_County.htm";break;
		default:
			content = "�d�ߤ���Ѯ𪬺A�I";
			return content;
		}

		content = location+"���Ѯ𪬪p\n";
		Connection con = Jsoup.connect(weatherUrl).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(3000);
	    Connection.Response resp = con.execute();
	    Document doc = null;
	    if (resp.statusCode() == 200) 
	    {
	        doc = con.get();
	    }

		Element allWeathers = doc.select("table.FcstBoxTable01").get(0);
		Elements tokens = allWeathers.select("tbody > tr");
		int status;
		if(dateFormat.format(dateToday).equals(date))
		{
			//�N��O���Ѫ��Ѯ�
			status = 0;
		}
		else
		{
			status = 1;
		}
		//���R�Ѯ�������c�Ϊ�token~
		for(Element tmp:tokens)
		{
			String contentTmp = "";
			//����ɬq
			String time = tmp.select("th").html().toString();
			String[] timeTokens = time.split(" ");
			time = timeTokens[0];//���F~
			contentTmp += time + "���";
			//�A�ӧ���~
			Elements temp = tmp.select("td");
			contentTmp += temp.get(0).html() + "�סA" + temp.get(2).html() + "���B�v" + temp.get(3).html() + "\n";
			if(status == 0)
			{
				if(contentTmp.contains("��"))
				{
					content += contentTmp;
				}
			}
			else
				content += contentTmp;
		}
	    //System.out.println(allWeathers.html());
	    //String content = allWeathers.html();
	    //content = content.substring(content.lastIndexOf("�Ѯ𷧪p�G"));
	    //content = content.replaceAll("�Ѯ𷧪p�G", "");
		return content;
	}
	
	private static String[] getWeekDates (Element dateFragment)
	{
		ArrayList<String> datesList = new ArrayList<String>();
		Elements dates = dateFragment.getElementsByTag("th");

		for (Element cell : dates)
		{
			if (cell.text().contains("�P��"))
			{
				String text = cell.text();
				int splitIndex = text.indexOf(" ");
				String dateStr = text.substring(0, splitIndex);
				datesList.add(dateStr);
			}
		}
		String[] datesAry = new String[datesList.size()];
		datesAry = datesList.toArray(datesAry);
		return datesAry;
	}

	// ���o�դѩαߤW����g�Ҧ����C��ŻP��Թϥܺ��}
	private static String[][] getTempratureInfo (Element tempFragment)
			throws IOException, IndexOutOfBoundsException
	{
		ArrayList<String> tempsList = new ArrayList<String>();
		ArrayList<String> imagesList = new ArrayList<String>();
		Elements temps = tempFragment.getElementsByTag("td");
		for (Element cell : temps)
		{
			String seperator = " ~ ";
			if (cell.text().contains(seperator))
			{
				String url = cell.getElementsByTag("img").attr("src");
				url = NEW_PREFIX + url.substring(PREFIX.length());
				String temp = cell.text();
				tempsList.add(temp);
				imagesList.add(url);
			}
		}
		String[] tempsAry = new String[tempsList.size()];
		String[] imagesAry = new String[imagesList.size()];
		tempsAry = tempsList.toArray(tempsAry);
		imagesAry = imagesList.toArray(imagesAry);
		String combined[][] = new String[2][];
		combined[0] = tempsAry;
		combined[1] = imagesAry;
		return combined;
	}
	public static void main (String args[])
	{
		try
		{
			String resultAll = getAllWeather();
			String result = getWeather("�s�_��","2017-08-21");
			//System.out.println(resultAll);
			System.out.println(result);
		} catch (IOException ie)
		{
			System.err.println(ie);
		} catch (IndexOutOfBoundsException iobe)
		{
			System.err.println(iobe);
		}
	}
}
