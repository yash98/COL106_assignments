public class program
{
	public int test(char key[], char answer[])
	{
		int ret = 0;
		for (int i = 0; i < key.length; i++){
			if (key[i] == answer[i]){
				ret += 4;
			}
			else if (answer[i] == '?'){
				ret += 0;
			}
			else{
				ret -= 1;
			}
		}
		return ret;
	}
}
