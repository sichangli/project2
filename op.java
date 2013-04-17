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
		
		String queryFile = args[0];
		String configFile = args[1];
//		String queryFile = "query.txt";
//		String configFile = "config.txt";
		
		readQuery(queryFile);
		readConfig(configFile);
		
		for (ArrayList<Double> sList : sLists) {
			//generate 2(k) - 1 subsets
			ArrayList<Subset> subs = subsets(sList);
			//s
			for (Subset sub1 : subs)  {
				//s'
				for (Subset sub2 : subs) {
					if (intersect(sub1.getTerms(), sub2.getTerms()))
						continue;
					if (dominCMetric(sub1, sub2, sList))
						continue;
					if (sub2.getP() <= 0.5 && dominDMetric(sub1, sub2, sList))
						continue;
					
					double cost = eqone(sList, sub2, sub1);
					Subset union = findUnion(sub1, sub2, subs);
					//System.out.println("new cost = "+cost+" || old cost = "+union.getC());
					if (cost < union.getC()) {
						union.setC(cost);
						union.setL(sub2);
						union.setR(sub1);
					}
				}
			}
			output(sList, subs);
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
	
	private static void output(ArrayList<Double> sList, ArrayList<Subset> subs) {
		System.out.println("======================================");
		for (int i = 0; i < sList.size(); i++) {
			if (i == sList.size() - 1)
				System.out.print(sList.get(i));
			else
				System.out.print(sList.get(i) + " ");
		}
		System.out.println("\n--------------------------------------");

		Subset curr = subs.get(subs.size()-1);
		
		//System.out.println(curr.getTerms().size());
		
/* 		if(curr.getL() == null){
			System.out.println("Right is null");
		} else {
		System.out.print("Left");
			for(Integer t : curr.getL().getTerms()){
				System.out.print(t+" ");
			}
		}
		if(curr.getR() == null){
			System.out.println("Left is null");
		} else {
		System.out.print("Right");
			for(Integer t : curr.getR().getTerms()){
				System.out.print(t+" ");
			}
		} */
		TreeSet<Integer> tmp = getNoBran(curr);
		if(curr.getTerms() == null){
			System.out.println("answer[j] = i;");
			System.out.print("j += (");
			int con = 0;
			for(Integer t : tmp){
				if(con > 0)
					System.out.print(" & ");
				System.out.print("t"+(t.intValue()+1)+"["+"o"+(t.intValue()+1)+"[i]"+"]");
				con++;
			}
			System.out.print(");");
		} else {
		System.out.print("if");
		output_in(curr, true);
		System.out.println(" {");
		System.out.println("\tanswer[j] = i;");
		System.out.print("\tj += (");
		int con = 0;
		for(Integer t : tmp){
				if(con > 0)
				System.out.print(" & ");
				System.out.print("t"+(t.intValue()+1)+"["+"o"+(t.intValue()+1)+"[i]"+"]");
				con++;
		}
		System.out.println(");\n}");
		}
		System.out.println("\n--------------------------------------");
		System.out.println("cost = "+curr.getC());
	}
	
	private static void output_in(Subset curr, boolean check){
			if(curr.getN() > 1 && check) 
				System.out.print("(");
			if(curr.getR() == null && curr.getL() == null){
					int con = 0;
					for(Integer ii : curr.getTerms()){
						if(con > 0)
							System.out.print(" & ");
						int intii = ii.intValue()+1;
						System.out.print("t"+intii+"["+"o"+intii+"[i]"+"]");
						con++;
					}
			} else {
				if(curr.getL()!= null){
					boolean checkhere = false;
					if(curr.getR().getTerms()!=null)
						checkhere = true;
					output_in(curr.getL(), checkhere);
				}
				if(curr.getR().getTerms()!= null){
					System.out.print(" && ");
					output_in(curr.getR(), true);
				}
			}
			if(curr.getN() > 1 && check)
				System.out.print(")");
		
	}
	
	private static TreeSet<Integer> getNoBran(Subset curr){
		if(curr.getR() == null && curr.getB() == 1){
			TreeSet<Integer> rs = curr.getTerms();
			curr.setTerms(null);
			return rs;
		}
		if(curr.getR() == null)
			return null;
		TreeSet<Integer> tmp = getNoBran(curr.getR());
		if(tmp != null) {
			curr.setN(curr.getN() - tmp.size());
		}
		return tmp;
	}
	
	private static Subset findUnion(Subset sub1, Subset sub2, ArrayList<Subset> subs) {
		TreeSet<Integer> s1 = sub1.getTerms();
		TreeSet<Integer> s2 = sub2.getTerms();
		TreeSet<Integer> temp = new TreeSet<Integer>();
		temp.addAll(s1);
		temp.addAll(s2);
		for (Subset s : subs) {
			TreeSet<Integer> ss = s.getTerms();
			if (temp.size() == ss.size() && temp.containsAll(ss))	
				return s;
		}
		return null;
	}
	
	
	private static boolean dominDMetric(Subset sub1, Subset sub2, ArrayList<Double> sList) {
		double[] sub2DMetric = calMetricD(sub2.getN(), sub2.getP());
		TreeSet<Integer> s1 = sub1.getTerms();
		for (int i : s1) {
			double[] sub1SomeDMetric = calMetricD(1, sList.get(i));
			if (sub2DMetric[0] > sub1SomeDMetric[0] && sub2DMetric[1] > sub1SomeDMetric[1])
				return true;
		}
		return false;
	}
	
	private static boolean dominCMetric(Subset sub1, Subset sub2, ArrayList<Double> sList) {
		double[] sub2CMetric = calMetricC(sub2.getN(), sub2.getP());
		double[] sub1LeftMostCMetric = calMetricC(1, sList.get(sub1.getTerms().first()));
		return sub2CMetric[0] > sub1LeftMostCMetric[0] && sub2CMetric[1] > sub1LeftMostCMetric[1];
	}
	
	//check whether 2 sets intersect with each other
	private static boolean intersect(TreeSet<Integer> s1,TreeSet<Integer> s2) {
		for (int i1 : s1) {
			if (s2.contains(i1))
				return true;
		}
		return false;
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
			double p = 1;
			for (int i : s)
				p *= sList.get(i);
			newSubset.setP(p);
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
		
		//System.out.println(r + " " + t + " " + l + " " + m + " " + a + " " + f);
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
		return n*r+(n-1)*l+n*f+a;
	}
	
	//For example 4.5
	private static double logicBran(ArrayList<Double> sList, TreeSet<Integer> subset){
		int n = subset.size();
		double totalP = 1;
		double totalQ;
		for(Integer curr : subset){
			double currP = sList.get(curr.intValue());
			totalP *= currP;
		}
		if(totalP <= 0.5)
			totalQ = totalP;
		else 
			totalQ = 1 - totalP;
		double result = n*r + (n-1)*l + n*f + t + m*totalQ + totalP*a; 
		return result;
	}
	
	//For Eq.(1)
	private static double eqone(ArrayList<Double> sList, Subset subsetleft, Subset subsetright){
		int nleft = subsetleft.getN();
		int nright = subsetright.getN();
		//Get cost of plan 2 for the right one
		double costright = subsetright.getC();
		
		double fcost = calFixCost(nleft);
		
		double p = subsetleft.getP();
		double q;
		if(p <= 0.5)
			q = p;
		else 
			q = 1-p;
			
		return fcost+m*q+p*costright;
	}
	
}
