import java.lang.Math;
public class Body {
	/**
	An instance of Body class can represent a planet,
	star, or various objects in this universe.
	*/

	public double xxPos = 0; // current x position
	public double yyPos = 0; // current y position
	public double xxVel = 0; // current velocity in the x direction
	public double yyVel = 0; // current velocity in the y direction
	public double mass = 0; // mass
	public String imgFileName; // name on the file that corresponds to the image that depicts the body
	public static double G = 6.67 * Math.pow(10, -11);

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
		// should I add imgFileName here?
		// imgFileName = "jupiter.gif";
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		return Math.sqrt((xxPos - b.xxPos) * (xxPos - b.xxPos) + (yyPos - b.yyPos) * (yyPos - b.yyPos));	
	}

	public double calcForceExertedBy(Body b) {
		return (G * mass * b.mass) / Math.pow(calcDistance(b), 2);
	}

	public double calcForceExertedByX(Body b) {
		return calcForceExertedBy(b) * (b.xxPos - xxPos) / calcDistance(b);
	}

	public double calcForceExertedByY(Body b) {
		return calcForceExertedBy(b) * (b.yyPos - yyPos) / calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] allBodys) {
		double res = 0;
		for (int i = 0; i < allBodys.length; i = i + 1) {
			if (allBodys[i] == this) {
				continue;
			}
			res += calcForceExertedByX(allBodys[i]);
		}
		return res;
	}

	public double calcNetForceExertedByY(Body[] allBodys) {
		double res = 0;
		for (int i = 0; i < allBodys.length; i = i + 1) {
			if (allBodys[i] == this) {
				continue;
			}
			res += calcForceExertedByY(allBodys[i]);
		}
		return res;
	}

	public void update(double t, double xF, double yF) {
		// calculate accelerationg
		double aX = xF / mass;
		double aY = yF / mass;
		xxVel = xxVel + t * aX;
		yyVel = yyVel + t * aY;

		xxPos = xxPos + t * xxVel;
		yyPos = yyPos + t * yyVel;
		return ;
	}

	public void draw() {
		String f = "./images/" + imgFileName;
		StdDraw.picture(xxPos, yyPos, f);
	}

}