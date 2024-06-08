package ma.ecole.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private StringBuilder currentExpression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnDot, R.id.btnEqual, R.id.btnSqrt, R.id.btnSquare,
                R.id.btnLog, R.id.btnClear, R.id.btnOpenParenthesis, R.id.btnCloseParenthesis
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick((Button) v);
                }
            });
        }
    }

    private void onButtonClick(Button button) {
        String buttonText = button.getText().toString();
        switch (buttonText) {
            case "=":
                calculateResult();
                break;
            case "C":
                clearInput();
                break;
            case "√":
                appendInput("sqrt(");
                break;
            case "x²":
                appendInput("^2");
                break;
            case "log":
                appendInput("log10(");
                break;
            case "(":
            case ")":
                appendInput(buttonText);
                break;
            default:
                appendInput(buttonText);
                break;
        }
    }

    private void appendInput(String str) {
        currentExpression.append(str);
        input.setText(currentExpression.toString());
    }

    private void clearInput() {
        currentExpression.setLength(0);
        input.setText("");
    }

    private void calculateResult() {
        String expression = currentExpression.toString();


        if (!parenthesesAreBalanced(expression)) {
            Toast.makeText(this, "Erreur: Parenthèses non équilibrées", Toast.LENGTH_SHORT).show();
            return;
        }


        if (expression.isEmpty()) {
            Toast.makeText(this, "Erreur: Expression vide", Toast.LENGTH_SHORT).show();
            return;
        }

        Expression exp = new Expression(expression);
        double result = exp.calculate();
        if (Double.isNaN(result)) {
            Toast.makeText(this, "Erreur dans l'expression", Toast.LENGTH_SHORT).show();
        } else {
            input.setText(String.valueOf(result));
            currentExpression.setLength(0);
            currentExpression.append(result);
        }
    }


    private boolean parenthesesAreBalanced(String expression) {
        int count = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }

}