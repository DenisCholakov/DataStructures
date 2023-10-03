package implementations;

import interfaces.Solvable;
import java.util.ArrayList;
import java.util.Arrays;

public class BalancedParentheses implements Solvable {
    private final String parentheses;
    private final ArrayList<Character> openBrackets = new ArrayList<>(Arrays.asList('(', '{', '['));

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < this.parentheses.length(); i++) {
            if (this.openBrackets.contains(this.parentheses.charAt(i))) {
                stack.push(this.parentheses.charAt(i));
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                Character openBracket = stack.pop();
                Character closedBracket = this.parentheses.charAt(i);

                if (!checkBrackets(openBracket, closedBracket)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private boolean checkBrackets(Character openBracket, Character closedBracket) {
        switch (openBracket) {
            case '{':
                return closedBracket == '}';
            case '(':
                return closedBracket == ')';
            case '[':
                return closedBracket == ']';
        }

        return false;
    }
}
