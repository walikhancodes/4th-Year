public class PatternMatchingAlgorithms{

    //** Returns the lowest index at which substring pattern begins in text or else -1 */
    public static int findBrute(char[] text, char[] pattern){
        int n = text.length;
        int m = pattern.length;
        for(int i = 0; i <= n - m; i++){    //try every starting index within text
            int k = 0;                      //k is index in to pattern
            while(k < m && text[i + k] == pattern[k]){
                k++;
            }
            if(k == m){
                return i;
            }
        }
        return -1;
    }

    //** Returns the lowest index at which substring pattern begins in text or else -1 */
    public static int findBoyerMoore(char[] text, char[] pattern){
        int n = text.length;
        int m = pattern.length;
        if(m == 0) return 0;
        Map<Character, Integer> last = new ProbeHashMap<>();
        for(int i = 0; i < n; i++){
            last.put(text[i], -1);
        }
        for(int k = 0; k < m; k++){
            last.put(pattern[k], k);
        }
        //start with the end of the pattern alligned at index m-1 of the text
        int i = m-1;    //an index into the text 
        int k = m-1;    //an index into the patten 
        while(i < n){
            if(text[i] == pattern[k]){      //a matching character 
                if(k == 0)return i;         //entire pattern has been found 
                i--;
                k--;
            } else {
                i += m - Math.min(k, 1 + last.get(text[i]));
                k = m - 1;
            }
        }
        return -1;  // return -1 if pattern is not found 
    }

    //** Returns the lowest index at which substrin pattern begins in text or else -1 */
    public static int findKMP(char[] text, char[] pattern){
        int n = text.length;    
        int m = pattern.length;
        if(m == 0) return 0;        //trivial search for empty string
        int[] fail = computeFailKMP(pattern);       // computed by private utility 
        int j = 0;      // index into text
        int k = 0;      // index into pattern
        while(j < n){
            if(text[j] == pattern[k]){  //pattern [0,,k] matched thus far
                if(k == m-1)return j - m + 1;   // match is complete
                j++;
                k++;
            } else if (k > 0){
                k = fail[k-1];
            } else {
                j++;
            }
        }
        return -1;
    }

    private static int[] computeFailKMP(char[] pattern){
        int m = pattern.length;
        int[] fail = new int[m];
        int j = 1;
        int k = 0;
        while(j < m){
            if(pattern[j] == pattern[k]){
                fail[j] = k+1;
                j++;
                k++;
            } else if(k > 0){
                k = fail[k-1];
            } else {
                j++;
            }
        }
        return fail;
    }
}