import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeSet;



public class op {
	
	static double r, t, l, m, a, f;
	static ArrayList<ArrayList<Double>> sLists;
	
	public static void main(String[] args) {
		
//		String queryFile = args[0];
//		String configFile = args[1];
		String queryFile = "query.txt";
		String configFile = "config.txt";
		
		readQuery(queryFile);
		readConfig(configFile);
		
		for (ArrayList<Double> sList : sLists) {
			ArrayList<Subset> subs = subsets(sList); 
		}
//		TreeSet<Integer> set = new TreeSet<Integer>();
//		for (int j = 0; j < 3; j++)
//			set.add(j);
//		ArrayList<TreeSet<Integer>> s = subsetsOfSize(set, 2);
//		for (TreeSet<Integer> ss : s) {
//			for (int i : ss)
//				System.out.print(i + " ");
//			System.out.println();
//		}
		
	}
	
	private static ArrayList<Subset> subsets(ArrayList<Double> sList) {
		ArrayList<TreeSet<Integer>> subs = new ArrayList<TreeSet<Integer>>();
		for (int i = 1; i <= sList.size(); i++) {
			TreeSet<Integer> set = new TreeSet<Integer>();
			for (int j = 0; j < sList.size(); j++)
				set.add(j);
			subs.addAll(subsetsOfSize(set, i));
		}
		ArrayList<Subset> results = new ArrayList<Subset>();
		for (TreeSet<Integer> s : subs) {
//			for (int i : s)
//				System.out.print(i + " ");
//			System.out.println();
			Subset newSubset = new Subset();
			newSubset.setTerms(s);
			newSubset.setN(s.size());
			//calculate logic branch cost and no branch cost
			double logicBranCost = logicBran(sList, s);
			double noBranCost = noBran(sList, s);
			if (noBranCost < logicBranCost) {
				newSubset.setC(noBranCost);
				newSubset.setB(1);
			}
			else
				newSubset.setC(logicBranCost);
			results.add(newSubset);
		}
		return results;
	}
	
	//find all the subsets of set with certain size
	private static ArrayList<TreeSet<Integer>> subsetsOfSize(TreeSet<Integer> set, int size) {
		if (size > set.size())
			return new ArrayList<TreeSet<Integer>>();
		if (size == 0) {
			ArrayList<TreeSet<Integer>> s = new ArrayList<TreeSet<Integer>>();
			s.add(new TreeSet<Integer>());
			return s;
		}
		int first = set.first();
//		System.out.println(first);
		set.remove(first);
		
		ArrayList<TreeSet<Integer>> subs1 = subsetsOfSize(new TreeSet<Integer>(set), size - 1);
		for (TreeSet<Integer> s : subs1)
			s.add(first);
		ArrayList<TreeSet<Integer>> subs2 = subsetsOfSize(set, size);
		subs1.addAll(subs2);
		return subs1;
	}
	
	//read the config file
	private static void readConfig(String configFile) {
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
	}
	
	//read the query file
	private static void readQuery(String queryFile) {
		sLists = new ArrayList<ArrayList<Double>>();
		Scanner in = null;
		String queryline = null;
		try {
			in = new Scanner(new File(queryFile));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (in.hasNextLine()) {
			ArrayList<Double> selectivities = new ArrayList<Double>();
			queryline = in.nextLine();
			String[] ss =queryline.split(" ");
			for (String s: ss)
				selectivities.add(Double.parseDouble(s));			
			sLists.add(selectivities);
		}
		in.close();
		
		//output test
//		for (ArrayList<Double> l : sLists) {
//			for (double d : l) {
//				System.out.print(d + " ");
//			}
//			System.out.println();
//		}
	}
	
	private static double calFixCost(int n){
		return n*r+(n-1)*l+n*f+t;
	}
	
	private static double[] calMetricC(int n, double p){
		double fcost = calFixCost(n);
		double[] rs = new double[2];
		rs[0] = ((p-1)/fcost);
		rs[1] = p;
		return rs;
	}
	
	private static double[] calMetricD(int n, double p){
		double fcost = calFixCost(n);
		double[] rs = new double[2];
		rs[0] = fcost;
		rs[1] = p;
		return rs;
	}
	
	//For example 4.4
	private static double noBran(ArrayList<Double> sList, TreeSet<Integer> subset){
		int n = subset.size();
	}
	
	//For example 4.5
	private static double logicBran(ArrayList<Double> sList, TreeSet<Integer> subset){
		int n = subset.size();
	}
	
}
