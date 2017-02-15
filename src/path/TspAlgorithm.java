package path;

import java.util.Stack;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class TspAlgorithm {

    public Stack<Integer> execute(int adjacencyMatrix[][]) {
        Stack<Integer> stack = new Stack<>();
        int numberOfNodes = adjacencyMatrix[0].length;
        int[] visited = new int[numberOfNodes];
        visited[0] = 1;
        stack.push(0);
        int element, dst = 0, i;
        int min;
        boolean minFlag = false;
        System.out.print(0 + "\t");

        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 0;
            min = Integer.MAX_VALUE;
            while (i < numberOfNodes) {
                if (adjacencyMatrix[element][i] > 0 && adjacencyMatrix[element][i] < Integer.MAX_VALUE && visited[i] == 0) {
                    if (min > adjacencyMatrix[element][i]) {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag) {
                visited[dst] = 1;
                stack.push(dst);
                System.out.print(IdSubstitution.getInstance().getIdSubstitute(dst, IdSubstitution.REVERT_TYPE) + "\t");
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        return stack;
    }
}
