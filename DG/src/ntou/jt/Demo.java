package ntou.jt;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Demo {
public static void main(String[] args) {
String addr = GetAddr("25.0360641", "121.452156");
System.out.println(addr);
//getCoordinate("����");
}
/**
* �ھڸg�n�פϦV�ѪR��}�A���ɻݭn�h���մX��
* �`�N:(�K�ۡGHTTP://code.google.com/intl/zh-CN/apis/maps/faq.html
* ���檺��}�ѪR�ШD���ƬO�_������H) �p�G�b 24 �p�ɮɬq������Ӧۤ@�� IP ��}�W�L 2500 �Ӧ�}�ѪR�ШD�A �αq�@�� IP
* ��}���檺��}�ѪR�ШD�t�v�L�֡AGoogle �a�� API �s�X���N�� 620 ���A�X�}�l�^���C �p�G��}�ѪR�����ϥΤ��M�L�h�A�h�q�� IP
* ��}�� Google �a�� API ��}�ѪR�����X�ݥi��Q�ä[����C
*
* @param latitude
* �n��
* @param longitude
* �g��
* @return
*/
public static String GetAddr(String latitude, String longitude) {
String addr = "";
//�]�i�H�OHTTP://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s�A���L�ѪR�X�Ӫ��O�^���}
//���_�i�H�H�K�g�@��key=abc
//output=csv,�]�i�H�Oxml��json�A���L�ϥ�csv��^����Ƴ�²���K�ѪR
String url = String.format(
"HTTP://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",
latitude, longitude);
URL myURL = null;
URLConnection HTTPsConn = null;
try {
myURL = new URL(url);
} catch (MalformedURLException e) {
e.printStackTrace();
return null;
}
try {
HTTPsConn = (URLConnection) myURL.openConnection();
if (HTTPsConn != null) {
InputStreamReader insr = new InputStreamReader(
HTTPsConn.getInputStream(), "UTF-8");
BufferedReader br = new BufferedReader(insr);
String data = null;
if ((data = br.readLine()) != null) {
System.out.println(data);
String[] retList = data.split(",");
if (retList.length > 2 && ("200".equals(retList[0]))) {
addr = retList[2];
addr = addr.replace("\"", "");
} else {
addr = "";
}
}
insr.close();
}
} catch (IOException e) {
e.printStackTrace();
return null;
}
return addr;
}

public static void getCoordinate(String addr)
{
String addrs = "";
String address = null;
try {
address = java.net.URLEncoder.encode(addr,"UTF-8");
} catch (UnsupportedEncodingException e1) {
e1.printStackTrace();
};
String output = "csv";
String key = "abc";
String url = String.format("HTTP://maps.google.com/maps/geo?q=%s&output=%s&key=%s", address, output, key);
URL myURL = null;
URLConnection HTTPsConn = null;
//�i����X
try {
myURL = new URL(url);
} catch (MalformedURLException e) {
e.printStackTrace();
}

try {
HTTPsConn = (URLConnection) myURL.openConnection();
if (HTTPsConn != null) {
InputStreamReader insr = new InputStreamReader(
HTTPsConn.getInputStream(), "UTF-8");
BufferedReader br = new BufferedReader(insr);
String data = null;
if ((data = br.readLine()) != null) {
System.out.println(data);
String[] retList = data.split(",");
/*
String latitude = retList[2];
String longitude = retList[3];

System.out.println("�n��"+ latitude);
System.out.println("�g��"+ longitude);
*/
if (retList.length > 2 && ("200".equals(retList[0]))) {
addrs = retList[2];
addrs = addr.replace("\"", "");
} else {
addrs = "";
}
}
insr.close();
}
} catch (IOException e) {
e.printStackTrace();
}
System.out.println(addrs);
}
}
