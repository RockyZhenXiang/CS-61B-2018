import java.lang.Math;

public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private static double G = 6.67e-11;

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

	public double calcForceExertedByX(Planet p){

		double dx = p.xxPos-xxPos;
		double r = this.calcDistance(p);
		double sumForce = this.calcForceExertedBy(p);

		return sumForce*dx/r;
	}

	public double calcForceExertedByY(Planet p){

		double dy = p.yyPos-yyPos;
		double r = this.calcDistance(p);
		double sumForce = this.calcForceExertedBy(p);

		return sumForce*dy/r;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets){
		double netForceX = 0.0;

		for (Planet p: allPlanets){
			if (p.equals(this)){
				continue;
			}
			else{
				netForceX += this.calcForceExertedByX(p);
			}

		}

		return netForceX;	

	}
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double netForceY = 0.0;

		for (Planet p: allPlanets){
			if (p.equals(this)){
				continue;
			}
			else{
				netForceY += this.calcForceExertedByY(p);
			}

		}
		
		return netForceY;
	}

	public void update(double dt, double fX, double fY){
		double aX = fX/mass;
		double aY = fY/mass;

		this.xxVel = aX*dt+xxVel;
		this.yyVel = yyVel+aY*dt;

		this.xxPos = xxPos + this.xxVel*dt;
		this.yyPos = yyPos + this.yyVel*dt;
	}

	public void draw(){
		String img = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, img);
		StdDraw.show();
	}
}














