import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;



public class op {
	
	static double r, t, l, m, a, f;
	
	public static void main(String[] args) {
		
		String queryFile = args[0];
		String configFile = "config.txt";
		List<Double> selectivities = new ArrayList<Double>();
		HashMap<String, Double> config = new HashMap<String, Double>();
		Properties configProp = new Properties();
		try {
			configProp.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r = Double.valueOf(configProp.getProperty("r"));
		t = Double.valueOf(configProp.getProperty("t"));
		l = Double.valueOf(configProp.getProperty("l"));
		m = Double.valueOf(configProp.getProperty("m"));
		a = Double.valueOf(configProp.getProperty("a"));
		f = Double.valueOf(configProp.getProperty("f"));
		
		System.out.println(r + " " + t + " " + l + " " + m + " " + a + " " + f);
		
		Scanner in = null;
		String queryline = null;
		try {
			in = new Scanner(new File(queryFile));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (in.hasNextLine()) {
			queryline = in.nextLine();
			//In this loop for each line of query, calculate for the optimal plan and print out
			
		}

		
	}
	
	private double calFixCost(int n){
		return n*r+(n-1)*l+n*f+t;
	}
	
	private double[] calMetricC(int n, double p){
		double fcost = calFixCost(n);
		double[] rs = new double[2];
		rs[0] = ((p-1)/fcost);
		rs[1] = p;
		return rs;
	}
	
	
	
}
