package ntou.jt;

public class NearBy 
{
	private String intro;
	private location coordinates;
	private String add;
	private String name;
	private double distance;
	private double position;
	private double rating;
	public NearBy(String intro,String add,location coordinates,String name,double distance,double position,double rating)
	{
		this.intro = intro;
		this.coordinates = coordinates;
		this.add = add;
		this.name = name;
		this.distance = distance;
		this.position = position;
		this.rating = rating;
		
	}
	public location getCoordinates() 
	{
		return coordinates;
	}
	public void setCoordinates(location coordinates) 
	{
		this.coordinates = coordinates;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public double getDistance() 
	{
		return distance;
	}
	public void setDistance(double distance) 
	{
		this.distance = distance;
	}
	public String toString()
	{
		String Direction = GetDirection();
		return this.name + "[" +this.coordinates.X+","+this.coordinates.Y +"]\n�\�U���СG" + this.intro;
	}
	public double getPosition() 
	{
		return position;
	}
	public void setPosition(double position) 
	{
		this.position = position;
	}
	public String GetDirection()
	{
	    if ((this.position <= 10 ) || (this.position > 350)) return "�k��";
	    if ((this.position > 10) && (this.position <= 80)) return "�k�e��";
	    if ((this.position > 80) && (this.position <= 100)) return "�e��";
	    if ((this.position > 100) && (this.position <= 170)) return "���e��";
	    if ((this.position > 170) && (this.position <= 190)) return "����";
	    if ((this.position > 190) && (this.position <= 260)) return "�����";
	    if ((this.position > 260) && (this.position <= 280)) return "���";
	    if ((this.position > 280) && (this.position <= 350)) return "�k���";
	    return null;
	}
	public double getRating() 
	{
		return rating;
	}
	public void setRating(double rating) 
	{
		this.rating = rating;
	}
	
}
