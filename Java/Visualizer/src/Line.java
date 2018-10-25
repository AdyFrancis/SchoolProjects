
public class Line {
	private Point x;
	private Point y;
	private double distance;
	
	public Line (Point x, Point y) {
		this.x = x;
		this.y = y;
		distance = calcDistance();
	}
	
	public Point getX() {
		return x;
	}
	
	public Point getY() {
		return y;
	}
	
	public double getDist() {
		return distance;
	}
	
	private double calcDistance() {
		double distance = Math.hypot(x.getX() - y.getX(), x.getY()-y.getY());
		return distance;
	}
}
