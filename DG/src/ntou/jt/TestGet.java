package ntou.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestGet 
{
	public static void main(String arg[]) throws IOException
	{
		String start;
		String end;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("���ਤ��T���o�p�{��(Coding by JT)��");
		System.out.println("�п�J�_�l�I�G");
		start = scanner.nextLine();
		System.out.println("�п�J���I�G");
		end = scanner.nextLine();
		String url = "http://maps.google.com/maps/api/directions/json?origin="+start+"&destination="+end+"&mode=walking&language=zh-TW&sensor=true&alternatives=true";
		try {
			ArrayList<RoadInfo[]> temp = getRoadInfo.getItem(url);
			for(int i = 0;i < temp.size();i++)
			{
				System.out.println("=====���u"+(i+1)+"=====");
				RoadInfo[] check = temp.get(i);
				for(RoadInfo temp1:check)
				{
					System.out.println(temp1);
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��F���I�I�{�������o�I");
	}

}
