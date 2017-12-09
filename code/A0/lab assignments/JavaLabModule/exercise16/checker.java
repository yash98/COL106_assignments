public class checker
{
	public static void main ( String args [])
	{
		String inputs[] =  {"1F1", "13AFFFF", "3"};
		String outputs[] = {"111110001", "1001110101111111111111111", "11"};
		
		for(int i=0; i<inputs.length; i++) {
			String input=inputs[i];
			String oracle_output=outputs[i];

			program p = new program();

			String output = p.test(input);
			if(output.equals(oracle_output)) {
				System.out.println("test passed for " + (input));
			} else {
				System.out.println("test failed for " + (input));
			}
		}
	}
}
