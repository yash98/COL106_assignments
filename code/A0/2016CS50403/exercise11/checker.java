public class checker
{
	public static void main ( String args [])
	{
		int inputs[][] = {{5,6}, {2,-8}, {4,0}};
		float outputs[][] = {{-3, -2}, {-4, 2}, {-4, 0}};

		for(int i=0; i<inputs.length; i++) {
			int input_a=inputs[i][0];
			int input_b=inputs[i][1];
			float oracle_output[]=outputs[i];

			program p = new program();

			float output[] = p.test(input_a, input_b);
			if(output!=null && output.length==oracle_output.length && (output[0]==oracle_output[0]) && (output[1]==oracle_output[1]) ) {
				System.out.println("test passed for " + input_a + "," + input_b);
			} else {
				System.out.println("test failed for " + input_a + "," + input_b);
			}
		}
	}
}
