public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);

		int numPlanets = in.readInt();
		double radius = in.readDouble();

		return radius;
	}

	public static Body[] readBodies(String fileName) {
		In in = new In(fileName);

		int numPlanets = in.readInt();
		double radius = in.readDouble();

		Body[] bodies = new Body[5];
		int i = 0;

		while (i < 5){
			
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String f = in.readString();

			// System.out.println(f);

			Body b = new Body(xP, yP, xV, yV, m, f);

			bodies[i] = b;
			// System.out.println(i);
			i = i + 1;
		}	

		return bodies;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		Body[] bodies = readBodies(filename);
		double radius = readRadius(filename);

		StdDraw.enableDoubleBuffering();

		StdDraw.setScale(-radius, radius);

		StdDraw.clear();

		StdDraw.picture(0, 0, "./images/starfield.jpg");

		StdDraw.show();

		for (Body b : bodies) {
			b.draw();
			StdDraw.show();
		}

		double time = 0;
		while (time < T) {
			double[] xForces = new double[5];
			double[] yForces = new double[5];


			for (int i = 0; i < bodies.length; i = i + 1) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}

			

			for (int i = 0; i < bodies.length; i = i + 1) {
				bodies[i].update(time, xForces[i], yForces[i]);
			}

			StdDraw.picture(0, 0, "./images/starfield.jpg");

			for (Body b : bodies) {
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(70);

			time = time + dt;
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
}

	}
}