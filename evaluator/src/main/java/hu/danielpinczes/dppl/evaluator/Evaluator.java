package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Node;
import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.ast.statement.ExpressionStatement;
import hu.danielpinczes.dppl.ast.statement.LetStatement;
import hu.danielpinczes.dppl.ast.statement.ReturnStatement;
import hu.danielpinczes.dppl.ast.statement.expression.*;
import hu.danielpinczes.dppl.object.*;
import hu.danielpinczes.dppl.object.Object;

import java.util.*;

public class Evaluator {

    private static final BooleanObject TRUE = new BooleanObject(true);
    private static final BooleanObject  FALSE = new BooleanObject(false);
    public static final NullObject NULL = new NullObject();

    public Object eval(java.lang.Object node, Environment env) {
        if (node instanceof Program) {
            return evalProgram((Program) node, env);
        } else if (node instanceof BlockStatement) {
            return evalBlockStatement((BlockStatement) node, env);
        } else if (node instanceof ExpressionStatement) {
            return eval(((ExpressionStatement) node).getExpression(), env);
        } else if (node instanceof ReturnStatement) {
            return evalReturnStatement((ReturnStatement) node, env);
        } else if (node instanceof LetStatement) {
            var val = evalLetStatement((LetStatement) node, env);
            return val;
        } else if (node instanceof IntegerLiteral) {
            return new IntegerObject(((IntegerLiteral) node).getValue());
        }
        else if (node instanceof StringLiteral) {
            return new StringObject(((StringLiteral) node).getValue());
        }
        else if (node instanceof BooleanExpression) {
            return nativeBoolToBooleanObject(((BooleanExpression) node).isValue());
        } else if (node instanceof PrefixExpression) {
            Object right = eval(((PrefixExpression) node).getRight(), env);
            if (isError(right)) {
                return right;
            }
            return evalPrefixExpression(((PrefixExpression) node).getOperator(), right);
        } else if (node instanceof InfixExpression) {
            Object left = eval(((InfixExpression) node).getLeft(), env);
            if (isError(left)) {
                return left;
            }
            Object right = eval(((InfixExpression) node).getRight(), env);
            if (isError(right)) {
                return right;
            }
            return evalInfixExpression(((InfixExpression) node).getOperator(), left, right);
        } else if (node instanceof IfExpression) {
            return evalIfExpression((IfExpression) node, env);
        } else if (node instanceof Identifier) {
            return evalIdentifier((Identifier) node, env);
        } else if (node instanceof FunctionLiteral) {
            return new FunctionObject(((FunctionLiteral) node).getParameters(), env, ((FunctionLiteral) node).getBody());
        } else if (node instanceof CallExpression) {
            Object function = eval(((CallExpression) node).getFunction(), env);
            if (isError(function)) {
                return function;
            }
            var args = evalExpressions(((CallExpression) node).getArguments(), env);
            if (args.size() == 1 && isError(args.get(0))) {
                return args.get(0);
            }
            return applyFunction(function, args);
        } else if (node instanceof ArrayLiteral) {
            var elements = evalExpressions(((ArrayLiteral) node).getElements(), env);
            if (elements.size() == 1 && isError(elements.get(0))) {
                return elements.get(0);
            }
            return new ArrayObject(elements);
        } else if (node instanceof IndexExpression) {
            Object left = eval(((IndexExpression) node).getLeft(), env);
            if (isError(left)) {
                return left;
            }
            Object index = eval(((IndexExpression) node).getIndex(), env);
            if (isError(index)) {
                return index;
            }
            return evalIndexExpression(left, index);
        } else if (node instanceof HashLiteral) {
            return evalHashLiteral((HashLiteral) node, env);
        }
        return null;
    }

    private Object evalLetStatement(LetStatement node, Environment env) {
        Object val = eval(node.getValue(), env);
        if (isError(val)) {
            return val;
        }
        env.set(node.getName().getValue(), val);
        return null;
    }

    private Object evalReturnStatement(ReturnStatement node, Environment env) {
        Object val = eval(node.getReturnValue(), env);
        if (isError(val)) {
            return val;
        }
        return new ReturnValue(val);
    }

    private Object evalProgram(Program program, Environment env) {
        Object result = null;
        for (Statement statement : program.getStatements()) {
            result = eval(statement, env);
            if (result instanceof ReturnValue) {
                return ((ReturnValue) result).getValue();
            }
            if (result instanceof ErrorObject) {
                return result;
            }
        }
        return result;
    }

    private Object evalBlockStatement(BlockStatement block, Environment env) {
        Object result = null;
        for (Statement statement : block.getStatements()) {
            result = eval(statement, env);
            if (result instanceof ReturnValue || result instanceof ErrorObject) {
                return result;
            }
        }
        return result;
    }

    private boolean isError(Object obj) {
        return obj instanceof ErrorObject;
    }

    private BooleanObject nativeBoolToBooleanObject(boolean value) {
        return value
                ? new BooleanObject(true)
                : new BooleanObject(false);
    }

    private Object evalPrefixExpression(String operator, Object right) {
        switch (operator) {
            case "!":
                return evalBangOperatorExpression(right);
            case "-":
                return evalMinusPrefixOperatorExpression(right);
            default:
                return newError("unknown operator: " + operator + right.getType());
        }
    }

    private Object evalBangOperatorExpression(Object right) {

        if (right.equals(TRUE)) {
            return FALSE;
        } else if (right == FALSE) {
            return TRUE;
        } else if (right == NULL) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    private Object evalMinusPrefixOperatorExpression(Object right) {
        if (!(right instanceof IntegerObject)) {
            return newError("unknown operator: -" + right.getType());
        }

        IntegerObject rightInt = (IntegerObject) right;
        return new IntegerObject(-rightInt.getValue());
    }

    private ErrorObject newError(String message) {
        return new ErrorObject(message);
    }

    private Object evalInfixExpression(String operator, Object left, Object right) {
        if (left instanceof IntegerObject && right instanceof IntegerObject) {
            return evalIntegerInfixExpression(operator, (IntegerObject) left, (IntegerObject) right);
        } else if (left instanceof StringObject && right instanceof StringObject) {
            return evalStringInfixExpression(operator, (StringObject) left, (StringObject) right);
        } else if (operator.equals("==")) {
            return nativeBoolToBooleanObject(left.equals(right));
        } else if (operator.equals("!=")) {
            return nativeBoolToBooleanObject(!left.equals(right));
        } else if (!left.getClass().equals(right.getClass())) {
            return newError("type mismatch: " + left.getType() + " " + operator + " " + right.getType());
        } else {
            return newError("unknown operator: " + left.getType() + " " + operator + " " + right.getType());
        }
    }

    private Object evalIntegerInfixExpression(String operator, IntegerObject leftVal, IntegerObject rightVal) {
        return switch (operator) {
            case "+" -> new IntegerObject(leftVal.getValue() + rightVal.getValue());
            case "-" -> new IntegerObject(leftVal.getValue() - rightVal.getValue());
            case "*" -> new IntegerObject(leftVal.getValue() * rightVal.getValue());
            case "/" -> {
                if (rightVal.getValue() == 0) {
                    yield newError("division by zero");
                }
                yield new IntegerObject(leftVal.getValue() / rightVal.getValue());
            }
            case "<" -> nativeBoolToBooleanObject(leftVal.getValue() < rightVal.getValue());
            case ">" -> nativeBoolToBooleanObject(leftVal.getValue() > rightVal.getValue());
            case "==" -> nativeBoolToBooleanObject(leftVal.getValue() == rightVal.getValue());
            case "!=" -> nativeBoolToBooleanObject(leftVal.getValue() != rightVal.getValue());
            default -> newError("unknown integer operator: " + operator);
        };
    }

    private Object evalStringInfixExpression(String operator, StringObject left, StringObject right) {
        if (operator.equals("+")) {
            return new StringObject(left.getValue() + right.getValue());
        } else {
            return newError("unsupported operator for strings: " + operator);
        }
    }

    private Object evalIfExpression(IfExpression ie, Environment env) {
        Object condition = eval(ie.getCondition(), env);
        if (isError(condition)) {
            return condition;  // Return error immediately if condition evaluation fails.
        }

        if (isTruthy(condition)) {
            return eval(ie.getConsequence(), env);  // Evaluate the consequence block if condition is true.
        } else if (ie.getAlternative() != null) {
            return eval(ie.getAlternative(), env);  // Evaluate the alternative block if it exists and condition is false.
        } else {
            return NULL;  // Return NULL if there is no alternative block and condition is false.
        }
    }

    private boolean isTruthy(Object obj) {
        if (obj instanceof BooleanObject) {
            return ((BooleanObject) obj).isValue();
        } else if (obj instanceof NullObject) {
            return false;
        } else {
            return true;  // Consider non-null objects as true unless they are specifically false.
        }
    }

    private Object evalIdentifier(Identifier node, Environment env) {
        Object val = env.get(node.getValue());
        if (val == null) {
            return newError("identifier not found: " + node.getValue());
        }
        return val;
    }

    private List<Object> evalExpressions(List<Expression> exps, Environment env) {
        var result = new ArrayList<Object>();
        for (Expression e : exps) {
            Object evaluated = eval(e, env);
            if (isError(evaluated)) {
                return List.of(evaluated);
            }
            result.add(evaluated);
        }
        return result;
    }

    private Object applyFunction(Object fn, List<Object> args) {
        if (fn instanceof FunctionObject) {
            FunctionObject function = (FunctionObject) fn;
            Environment extendedEnv = extendFunctionEnv(function, args);
            return unwrapReturnValue(eval(function.getBody(), extendedEnv));
        } else if (fn instanceof BuiltinFunction) {
            BuiltinFunction builtin = (BuiltinFunction) fn;
            return builtin.apply(args);
        } else {
            return newError("not a function: " + fn.getClass().getName());
        }
    }

    private Environment extendFunctionEnv(FunctionObject fn, List<Object> args) {
        Environment env = new Environment(fn.getEnv());
        for (int i = 0; i < fn.getParameters().size(); i++) {
            Identifier param = fn.getParameters().get(i);
            var value = args.size() > i
                    ? args.get(i)
                    : NULL;
            env.set(param.getValue(), value);
        }
        return env;
    }

    private Object unwrapReturnValue(Object obj) {
        if (obj instanceof ReturnValue) {
            return ((ReturnValue) obj).getValue();
        }
        return obj;
    }

    public Object evalIndexExpression(Object left, Object index) {
        if (left instanceof ArrayObject && index instanceof IntegerObject) {
            return evalArrayIndexExpression(left, index);
        } else if (left instanceof HashObject) {
            return evalHashIndexExpression(left, index);  // Assuming evalHashIndexExpression is implemented similarly
        } else {
            return newError("index operator not supported: " + left.getType());
        }
    }

    public Object evalArrayIndexExpression(Object array, Object index) {
        var arrayObject = (ArrayObject) array;
        var integerObject = (IntegerObject) index;
        int idx = (int) integerObject.getValue();
        int max = arrayObject.getElements().size() - 1;

        if (idx < 0 || idx > max) {
            return NULL;  // Assuming NULL is a defined singleton for null-like behavior
        }

        return arrayObject.getElements().get(idx);
    }

    public Object evalHashIndexExpression(Object hash, Object index) {
        if (!(hash instanceof HashObject)) {
            return newError("Expected a hash object but found: " + hash.getType());
        }
        if (!(index instanceof Hashable)) {
            return newError("Unusable as hash key: " + index.getType());
        }

        var hashObject = (HashObject) hash;
        var hashKey = (Hashable) index;

        var value = hashObject.getPairs().get(hashKey.hashKey());
        if (value == null) {
            return NULL;  // Assuming NULL is a predefined singleton representing a null value
        }

        return (Object) value;
    }

    public Object evalHashLiteral(HashLiteral node, Environment env) {
        var pairs = new HashMap<HashKey, HashPair>();
        for (Map.Entry<Expression, Expression> entry : node.getPairs().entrySet()) {
            Expression keyNode = entry.getKey();
            Expression valueNode = entry.getValue();
            Object key = eval(keyNode, env);
            if (isError(key)) {
                return key;
            }
            if (!(key instanceof Hashable)) {
                return newError("unusable as hash key: " + key.getType());
            }
            Object value = eval(valueNode, env);
            if (isError(value)) {
                return value;
            }
            HashKey hashKey = ((Hashable) key).hashKey();
            pairs.put(hashKey, new HashPair(key, value));
        }
        return new HashObject(pairs);
    }
}
