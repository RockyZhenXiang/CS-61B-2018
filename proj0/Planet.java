import java.lang.Math;

public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public static double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;

	}

	public Planet(Planet b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Planet p){

		double xSqure = (xxPos-p.xxPos)*(xxPos-p.xxPos);
		double ySqure = (yyPos-p.yyPos)*(yyPos-p.yyPos);


		return Math.sqrt(xSqure+ySqure);

	}

	public double calcForceExertedBy(Planet p){

		double distance = this.calcDistance(p);

		return G*mass*p.mass / (distance*distance);
	}

}