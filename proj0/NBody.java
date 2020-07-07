public class NBody{
	public static double readRadius(String fileName){
		In in = new In(fileName);

		int numberOfPlanets = in.readInt();
		double r = in.readDouble();

		return r;

	}

	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);

		int numberOfPlanets = in.readInt();
		double r = in.readDouble();

		Planet[] planets = new Planet[numberOfPlanets];

		for (int i=0;i<numberOfPlanets;i+=1){
			
			double xPos = in.readDouble();
			double yPos = in.readDouble();
			double xVel = in.readDouble();
			double yVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Planet p = new Planet(xPos,yPos,xVel,yVel,mass, imgFileName);

			planets[i] = p;
		}

		return planets;

	}




	public static void main(String[] args) {

		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		String backgroundPic = "images/starfield.jpg";
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0,0,backgroundPic);
		StdDraw.show(); 
		
		for (Planet p: planets){
			p.draw();
		}

		StdDraw.enableDoubleBuffering();

		for (double t=0; t<T; t+=dt){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for(int i=0; i<planets.length; i+=1){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}

			for(int i=0; i<planets.length; i+=1){
				planets[i].update(dt, xForces[i], yForces[i]);
			
			}
			StdDraw.clear();
			StdDraw.picture(0,0,backgroundPic);
			StdDraw.show(); 

			for (Planet p: planets){
				p.draw();
			}

			StdDraw.pause(1);

		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
  		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
	}
}