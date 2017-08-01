import java.util.HashMap;
public class program
{
	public String test(String hex)
	{
		/*
		Exercise 16: Hex to binary- Given a string representing a number in hexadecimal
		format, convert it into its equivalent binary string. For e.g. if the input if "1F1"
		then its binary equivalent is "111110001". If the input is "13AFFFF", the output
		should be "1001110101111111111111111".
		*/
		String binary = "";
		HashMap <String, String> hm = new HashMap <String, String>();
		hm.put("0","0000");
		hm.put("1","0001");
		hm.put("2","0010");
		hm.put("3","0011");
		hm.put("4","0100");
		hm.put("5","0101");
		hm.put("6","0110");
		hm.put("7","0111");
		hm.put("8","1000");
		hm.put("9","1001");
		hm.put("A","1010");
		hm.put("B","1011");
		hm.put("C","1100");
		hm.put("D","1101");
		hm.put("E","1110");
		hm.put("F","1111");
		for (int i = 0; i < hex.length(); i++){
			binary += hm.get(hex.substring(i,i+1));
		}
		for (int i = 0; i < binary.length(); i++){
			System.out.println(binary.substring(i,i+1));
			if (binary.substring(i,i+1).equals("1")){
				binary = binary.substring(i);
				break;
			}
		}
		return binary;
	}
}
