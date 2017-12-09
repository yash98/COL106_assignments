package col106.a3.ta;

public class StringFormatChecker {
    String argument;
    int n;
    int index;
    String token;
    String message;

    public StringFormatChecker(String s, int n) {
        this.n = n;
        argument = s;
        index = 0;
        nextToken();
    }

    public boolean verify() {
        return getDepth(false) >= 0;
    }

    public String getMessage() {
        return message;
    }

    public String getArgument() {
        return argument;
    }

    int getDepth(boolean checkdegree) {
        if (!token.equals("[")) {
            message = "Missing [ at " + index;
            return -1;
        }
        nextToken();
        int depth = 1;
        if (token.equals("[")) {
            depth = 1 + getDepth(true);
            //System.out.println("updated " + depth);
            if (depth < 0)
                return depth;
            //skip ,
            if (!token.equals(",")) {
                message = "Missing , at " + index;
                return -1;
            }
            nextToken();
        }
        int count = 1;
        for (; ; count++) {
            String key = token;
            do nextToken(); while (!"[],=".contains(token));
            if (!token.equals("=")) {
                message = "missing = at " + index;
                return -1;
            }
            //skip =
            nextToken();
            String value = token;
            do nextToken(); while (!"[],=".contains(token));
            if (token.equals("]")) {
                if (depth > 1) {
                    message = "No tail " + index;
                    return -1;
                }
                nextToken();
                if (checkdegree&&(count == n || count < n / 2 - 1)) {
                    message = "illegal node size " + index;
                    return -1;
                }
                return depth;
            }
            if (!",".equals(token)) {
                message = "unexpected token " + token + " while , expected";
                return -1;
            }
            nextToken();
            int d = 0;
            if (token.equals("[")) {
                d = getDepth(true);
                if (1 + d != depth) {
                    message = "unequal depths " + depth + " " + (1 + d) + "at " + index;
                    return -1;
                }
                if (token.equals("]")) {
                    nextToken();
                    if (count == n || count < n / 2 - 1) {
                        message = "illegal node size " + index;
                        return -1;
                    }
                    return depth;
                }
                if (!token.equals(",")) {
                    message = "missing , at " + index;
                    return -1;
                } else nextToken();
            }
        }
    }

    void nextToken() {
        if (index == argument.length()) {
            token = "]";
            return;
        }
        while (" \n\t".indexOf(argument.charAt(index)) >= 0)
            index++;
        if ("[],=".indexOf(argument.charAt(index)) >= 0)
            token = "" + argument.charAt(index++);
        else {
            int s = index;
            while (index < argument.length() && " \n\t[],=".indexOf(argument.charAt(index++)) < 0) ;
            index--;
            token = argument.substring(s, index);
        }
        //System.out.println(token);
    }
}