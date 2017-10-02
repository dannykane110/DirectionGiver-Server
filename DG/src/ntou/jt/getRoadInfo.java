package ntou.jt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;


public class getRoadInfo 
{
	public static ArrayList<RoadInfo[]> getItem(String url) throws Exception 
	{
		String Result = null;
		//URL url = new URL(http);
		String url2 = new String();
		ArrayList<RoadInfo[]> totalRoads = new ArrayList<RoadInfo[]>();
		for (int j = 0; j < url.length(); j++)
		{
			if (url.substring(j, j + 1).matches("[\\u4e00-\\u9fa5]+"))
			{
				try 
				{
					url2 = url2 + URLEncoder.encode(url.substring(j, j + 1),"UTF-8");
				}
				catch (UnsupportedEncodingException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				url2 = url2 + url.substring(j, j + 1).toString();
			}
		}
		InputStream is = null;
		try 
		{
			is = new URL(url2).openStream();
			StringBuilder sb = new StringBuilder();
            int cp;
			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while ((cp = rd.read()) != -1) {
				     sb.append((char) cp);
				 }
			Result = sb.toString();
            //System.out.println(sb.toString());
		} 
		catch (MalformedURLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //�קK����ýX���D  
        finally 
        {
			is.close();
		}
		JSONObject jsonObj = new JSONObject(Result);//���o���������
		JSONArray jsonArray = jsonObj.getJSONArray("routes");//��routes��X��

		//System.out.println("�`�@��" + jsonArray.length() + "�����u");
		for(int top = 0;top < jsonArray.length();top++)
		{
			//System.out.println("=====���u" + (top+1) + "=====");
			JSONObject jsonItem = jsonArray.getJSONObject(top);//���top��Object(���u��T�Ҧb�B)
			JSONArray legs = jsonItem.getJSONArray("legs");//�̫���ڭ̭n�Ϊ�legs
			JSONObject leginfo = legs.getJSONObject(0);//legs�̭����Ĥ@��Object
			String start = leginfo.getString("start_address");
			//System.out.println("�X�o�G" + start);
			String end = leginfo.getString("end_address");
			//System.out.println("�ت��a�G" + end);
			JSONArray steps = leginfo.getJSONArray("steps");//����u
			//System.out.println("�`�@��" + steps.length() + "�����u��T");
			//Object object = jsonObj.getJSONObject("routes");
			RoadInfo[] temp = new RoadInfo[steps.length()];
			for(int count = 0;count < steps.length();count++)
			{
				String distance = steps.getJSONObject(count).getJSONObject("distance").getString("text").toString();
				String duration = steps.getJSONObject(count).getJSONObject("duration").getString("text").toString();
				
				JSONObject tempString = steps.getJSONObject(count).getJSONObject("end_location");
				location end_location = new location(tempString.getDouble("lng"),tempString.getDouble("lat"));
				tempString = steps.getJSONObject(count).getJSONObject("start_location");
				location start_location = new location(tempString.getDouble("lng"),tempString.getDouble("lat"));
			    String temp_html = steps.getJSONObject(count).getString("html_instructions").toString();
			    String html_instructions = delHTMLTag(temp_html);
			    System.out.println("�ק�e���u:"+html_instructions);
			    if(html_instructions.contains("/"))
			    	html_instructions = simplify(html_instructions);
			    System.out.println("�ק����u:"+html_instructions);
			    
			    String polyline = steps.getJSONObject(count).getJSONObject("polyline").getString("points").toString();
			    String travel_mode = steps.getJSONObject(count).getString("travel_mode").toString();
			    temp[count] = new RoadInfo(distance,duration,end_location,start_location,html_instructions,polyline,travel_mode);
			}
			totalRoads.add(temp);
		}
		return totalRoads;
	}
	
	public static String delHTMLTag(String htmlStr)//�ΨӮ���html�̭���tag
	{ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //Script��TAG��
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //CSS��TAG��
        String regEx_html="<[^>]+>"; //HTML��TAG��
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //�L�oScript 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //�L�ocss
        
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //�L�ohtml

       return htmlStr.trim(); //�Ǧ^�h��
	} 
	public static String simplify(String a) {
        int idxFirstSlashPosition = a.indexOf("/");//��Ĥ@�ӱ׽u����m
        int idxSecondSlashPosition;//�s��ĤG�ӱ׽u����m�]���ޭȡ^
        int idxFirstBoundary;//�s��Ĥ@���������r
        String sub1 = a.substring(0, idxFirstSlashPosition);//���o�Ĥ@�Ӧr��׽u���r��
        String sub2;//�s��Ӧr��ĤG����
        String instruction;//�̲�²�ƹL���r��
        int aLength = a.length();//���o�o�Ӧr�ꪺ����
        if (a.matches("^((?!�a����p).)*�e�i$")) {//�W�h1
            instruction = sub1 + "�e�i";
        } else if (a.matches("^((?!�a����p)(?!�a�k��p)(?!�V�k��)(?!�V����)(?!�~��}�b).)*$") || a.matches("^((?!�a����p)(?!�a�k��p).).*((�D|�u|��|��|��|�e�i)�~��}�b).*$") || a.matches("^(�a�k��p|�a����p).*")) {//�W�h2�B4�B8
            instruction = sub1;
        } else if (a.matches("^.*(�V����|�V�k��)$")) {//�W�h3
            sub2 = a.substring(aLength - 3, aLength);
            instruction = sub1 + sub2;
        } else if (a.matches("^.*(�a�k��p|�V����|�V�k��|�V�k����)�~��}�b.*")) {//�W�h5
            if (a.contains("�V����") || a.contains("�V�k��")) {//�p�G�O�V�k��ΦV����
                idxFirstBoundary = a.indexOf("�V");//����������r
                sub2 = a.substring(idxFirstBoundary, idxFirstBoundary + 3);
                instruction = sub1 + sub2;
            } else if (a.contains("�a�k��p")) {//�p�G�O�a�k��p
                idxFirstBoundary = a.indexOf("�a");//����������r
                sub2 = a.substring(idxFirstBoundary, idxFirstBoundary + 4);
                instruction = sub1 + sub2;
            } else {//�p�G�O��L�A�p�G�V�k����
                idxFirstBoundary = a.indexOf("�V");//����������r
                sub2 = a.substring(idxFirstBoundary, idxFirstBoundary + 4);
                instruction = sub1 + sub2;
            }
        } else if (a.matches("^.*�a�k��p$")) {//�W�h6
            instruction = sub1 + "�a�k��p";
        } else if (a.matches("^.*�a�k��p��.*$")) {//�W�h7
            idxFirstBoundary = a.indexOf("�a");//����������r
            sub2 = a.substring(idxFirstBoundary, aLength);
            instruction = sub1 + sub2;
        } else if (a.matches("^.*((�a�k��p|�a����p)�A).*$")) {//�W�h9
            idxFirstBoundary = a.indexOf("�a");//����������r�����ޭ�
            idxSecondSlashPosition = a.indexOf("/", idxFirstSlashPosition + 1);//���ĤG�ӱ׽u
            sub2 = a.substring(idxFirstBoundary, idxSecondSlashPosition);
            instruction = sub1 + sub2;
        } else if (a.matches("^.*(�V����|�V�k��)�ت��a(�b����|�b�k��)$")) {//�W�h10
            int k = a.indexOf("�V", idxFirstSlashPosition + 1);//���Ĥ@�ӱ׽u�᭱�A�Ĥ@�ӡu�V�v����m
            sub2 = a.substring(k, aLength);
            instruction = sub1 + sub2;
        } else {
            instruction = a;
        }
        return instruction;
    }
}
