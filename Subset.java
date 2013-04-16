import java.util.TreeSet;


public class Subset {
	
	private int n;
	private double p;
	private int b;
	private double c;
	private Subset L;
	private Subset R;
	private TreeSet<Integer> terms;
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public TreeSet<Integer> getTerms() {
		return terms;
	}

	public void setTerms(TreeSet<Integer> terms) {
		this.terms = terms;
	}

	public Subset() {
	}

}
