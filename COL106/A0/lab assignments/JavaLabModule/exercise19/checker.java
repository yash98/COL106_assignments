public class checker
{
	public static void main ( String args [])
	{
		int inputsA[][][] = { {{1,2,3}, {0,3,0}},  {{1,2}, {6,7}}, {{1}}   } ;
		int inputsB[][][] = { {{2,3,4}, {1,2,4}},  {{1,1}, {3,4}}, {{0}}   } ;
		int outputs[][][] = { {{3,5,7}, {1,5,4}},  {{2,3}, {9,11}},{{1}}   } ;

		for(int i=0; i<inputsA.length; i++) {
			int inputA[][]=inputsA[i];
			int inputB[][]=inputsB[i];
			int oracle_output[][]=outputs[i];

			program p = new program();

			int output[][] = p.test(inputA, inputB);
			if(areEqual(output,oracle_output)) {
				System.out.println("test passed for A:" + toString(inputA) + "  B:" + toString(inputB));
			} else {
				System.out.println("test failed for A:" + toString(inputA) + "  B:" + toString(inputB));
			}
		}
	}
	
	private static String toString(int array[][])
	{
		String str = "{";
		for(int i=0; i<array.length; i++) {
			int element[] = array[i];
			if(i==(array.length-1)) {
				str+= toString(element);
			} else {
				str+=toString(element) + ",";
			}
		}
		str+="}";
		return str;
	}
	
	private static String toString(int array[])
	{
		String str = "{";
		for(int i=0; i<array.length; i++) {
			int element = array[i];
			if(i==array.length-1) {
				str+= element;
			} else {
				str+=element + ",";
			}
		}
		str+="}";
		return str;
	}

	private static boolean areEqual(int a[][], int b[][])
	{
		if(a!=null && b!=null && a.length==b.length) {
			for(int i=0; i<a.length; i++) {
				if(areEqual(a[i],b[i])==false) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static boolean areEqual(int a[], int b[])
	{
		if(a!=null && b!=null && a.length==b.length) {
			for(int i=0; i<a.length; i++) {
				int elementA = a[i];
				int elementB = b[i];

				if(elementA!=elementB) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
