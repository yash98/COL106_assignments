import java.util.*;
public class program
{
	public String[] test(String fileNames[])
	{
		Vector <String> v = new Vector <String>();
		String eachfile;
		for (int i = 0; i < fileNames.length; i++){
			eachfile = fileNames[i].substring(fileNames[i].length()-5);
			if (eachfile.equals(".java")){
				v.add(fileNames[i]);
			}
		}
		String javaFiles[] = new String[v.size()];
		Iterator<String> it = v.iterator();
		int j = 0;
		while (it.hasNext()){
			javaFiles[j] = it.next();
			j++;
		}
		return javaFiles;
	}
}
