package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.ast.statement.ExpressionStatement;
import hu.danielpinczes.dppl.ast.statement.LetStatement;
import hu.danielpinczes.dppl.ast.statement.ReturnStatement;
import hu.danielpinczes.dppl.ast.statement.expression.*;
import hu.danielpinczes.dppl.object.*;
import hu.danielpinczes.dppl.object.DpplObject;

import java.util.*;

public class Evaluator {

    private static final BooleanObject TRUE = new BooleanObject(true);
    private static final BooleanObject  FALSE = new BooleanObject(false);
    public static final NullObject NULL = new NullObject();

    public DpplObject eval(java.lang.Object node, Environment env) {
        if (node instanceof Program) {
            return evalProgram((Program) node, env);
        } else if (node instanceof BlockStatement) {
            return evalBlockStatement((BlockStatement) node, env);
        } else if (node instanceof ExpressionStatement) {
            return eval(((ExpressionStatement) node).getExpression(), env);
        } else if (node instanceof ReturnStatement) {
            return evalReturnStatement((ReturnStatement) node, env);
        } else if (node instanceof LetStatement) {
            DpplObject val = eval(((LetStatement)node).getValue(), env);
            if (isError(val)) {
                return val;
            }
            env.set(((LetStatement)node).getName().getValue(), val);
        } else if (node instanceof IntegerLiteral) {
            return new IntegerObject(((IntegerLiteral) node).getValue());
        }
        else if (node instanceof StringLiteral) {
            return new StringObject(((StringLiteral) node).getValue());
        }
        else if (node instanceof BooleanExpression) {
            return nativeBoolToBooleanObject(((BooleanExpression) node).isValue());
        } else if (node instanceof PrefixExpression) {
            DpplObject right = eval(((PrefixExpression) node).getRight(), env);
            if (isError(right)) {
                return right;
            }
            return evalPrefixExpression(((PrefixExpression) node).getOperator(), right);
        } else if (node instanceof InfixExpression) {
            DpplObject left = eval(((InfixExpression) node).getLeft(), env);
            if (isError(left)) {
                return left;
            }
            DpplObject right = eval(((InfixExpression) node).getRight(), env);
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
            DpplObject function = eval(((CallExpression) node).getFunction(), env);
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
            DpplObject left = eval(((IndexExpression) node).getLeft(), env);
            if (isError(left)) {
                return left;
            }
            DpplObject index = eval(((IndexExpression) node).getIndex(), env);
            if (isError(index)) {
                return index;
            }
            return evalIndexExpression(left, index);
        } else if (node instanceof HashLiteral) {
            return evalHashLiteral((HashLiteral) node, env);
        }
        return null;
    }

    private DpplObject evalLetStatement(LetStatement node, Environment env) {

        return null;
    }

    private DpplObject evalReturnStatement(ReturnStatement node, Environment env) {
        DpplObject val = eval(node.getReturnValue(), env);
        if (isError(val)) {
            return val;
        }
        return new ReturnValue(val);
    }

    private DpplObject evalProgram(Program program, Environment env) {
        DpplObject result = null;
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

    private DpplObject evalBlockStatement(BlockStatement block, Environment env) {
        DpplObject result = null;
        for (Statement statement : block.getStatements()) {
            result = eval(statement, env);
            if (result instanceof ReturnValue || result instanceof ErrorObject) {
                return result;
            }
        }
        return result;
    }

    private boolean isError(DpplObject obj) {
        return obj instanceof ErrorObject;
    }

    private BooleanObject nativeBoolToBooleanObject(boolean value) {
        return value
                ? new BooleanObject(true)
                : new BooleanObject(false);
    }

    private DpplObject evalPrefixExpression(String operator, DpplObject right) {
        return switch (operator) {
            case "!" -> evalBangOperatorExpression(right);
            case "-" -> evalMinusPrefixOperatorExpression(right);
            default -> newError("unknown operator: " + operator + right.getType());
        };
    }

    private DpplObject evalBangOperatorExpression(DpplObject right) {

        if (right.inspect().equals(TRUE.inspect())) {
            return FALSE;
        } else if (right.inspect().equals(FALSE.inspect()) ) {
            return TRUE;
        } else if (right.inspect().equals(NULL.inspect()) ) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    private DpplObject evalMinusPrefixOperatorExpression(DpplObject right) {
        if (!(right instanceof IntegerObject)) {
            return newError("unknown operator: -" + right.getType());
        }

        IntegerObject rightInt = (IntegerObject) right;
        return new IntegerObject(-rightInt.getValue());
    }

    private ErrorObject newError(String message) {
        return new ErrorObject(message);
    }

    private DpplObject evalInfixExpression(String operator, DpplObject left, DpplObject right) {
        if (left instanceof IntegerObject && right instanceof IntegerObject) {
            return evalIntegerInfixExpression(operator, (IntegerObject) left, (IntegerObject) right);
        } else if (left instanceof StringObject && right instanceof StringObject) {
            return evalStringInfixExpression(operator, (StringObject) left, (StringObject) right);
        } else if (operator.equals("==")) {
            return nativeBoolToBooleanObject(left.inspect().equals(right.inspect()));
        } else if (operator.equals("!=")) {
            return nativeBoolToBooleanObject(!left.inspect().equals(right.inspect()));
        } else if (!left.getClass().equals(right.getClass())) {
            return newError("type mismatch: " + left.getType() + " " + operator + " " + right.getType());
        } else {
            return newError("unknown operator: " + left.getType() + " " + operator + " " + right.getType());
        }
    }

    private DpplObject evalIntegerInfixExpression(String operator, IntegerObject leftVal, IntegerObject rightVal) {
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

    private DpplObject evalStringInfixExpression(String operator, StringObject left, StringObject right) {
        if (operator.equals("+")) {
            return new StringObject(left.getValue() + right.getValue());
        } else {
            return newError("unsupported operator for strings: " + operator);
        }
    }

    private DpplObject evalIfExpression(IfExpression ie, Environment env) {
        DpplObject condition = eval(ie.getCondition(), env);
        if (isError(condition)) {
            return condition;
        }

        if (isTruthy(condition)) {
            return eval(ie.getConsequence(), env);
        } else if (ie.getAlternative() != null) {
            return eval(ie.getAlternative(), env);
        } else {
            return NULL;
        }
    }

    private boolean isTruthy(DpplObject obj) {
        if (obj instanceof BooleanObject) {
            return ((BooleanObject) obj).isValue();
        } else if (obj instanceof NullObject) {
            return false;
        } else {
            return true;
        }
    }

    private DpplObject evalIdentifier(Identifier node, Environment env) {
        DpplObject val = env.get(node.getValue());
        if (val == null) {
            return newError("identifier not found: " + node.getValue());
        }
        return val;
    }

    private List<DpplObject> evalExpressions(List<Expression> exps, Environment env) {
        var result = new ArrayList<DpplObject>();
        for (Expression e : exps) {
            DpplObject evaluated = eval(e, env);
            if (isError(evaluated)) {
                return List.of(evaluated);
            }
            result.add(evaluated);
        }
        return result;
    }

    private DpplObject applyFunction(DpplObject fn, List<DpplObject> args) {
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

    private Environment extendFunctionEnv(FunctionObject fn, List<DpplObject> args) {
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

    private DpplObject unwrapReturnValue(DpplObject obj) {
        if (obj instanceof ReturnValue) {
            return ((ReturnValue) obj).getValue();
        }
        return obj;
    }

    public DpplObject evalIndexExpression(DpplObject left, DpplObject index) {
        if (left instanceof ArrayObject && index instanceof IntegerObject) {
            return evalArrayIndexExpression(left, index);
        } else if (left instanceof HashObject) {
            return evalHashIndexExpression(left, index);
        } else {
            return newError("index operator not supported: " + left.getType());
        }
    }

    public DpplObject evalArrayIndexExpression(DpplObject array, DpplObject index) {
        var arrayObject = (ArrayObject) array;
        var integerObject = (IntegerObject) index;
        int idx = (int) integerObject.getValue();
        int max = arrayObject.getElements().size() - 1;

        if (idx < 0 || idx > max) {
            return NULL;
        }

        return arrayObject.getElements().get(idx);
    }

    public DpplObject evalHashIndexExpression(DpplObject hash, DpplObject index) {
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
            return NULL;
        }

        return (DpplObject) value;
    }

    public DpplObject evalHashLiteral(HashLiteral node, Environment env) {
        var pairs = new HashMap<HashKey, HashPair>();
        for (Map.Entry<Expression, Expression> entry : node.getPairs().entrySet()) {
            Expression keyNode = entry.getKey();
            Expression valueNode = entry.getValue();
            DpplObject key = eval(keyNode, env);
            if (isError(key)) {
                return key;
            }
            if (!(key instanceof Hashable)) {
                return newError("unusable as hash key: " + key.getType());
            }
            DpplObject value = eval(valueNode, env);
            if (isError(value)) {
                return value;
            }
            HashKey hashKey = ((Hashable) key).hashKey();
            pairs.put(hashKey, new HashPair(key, value));
        }
        return new HashObject(pairs);
    }
}
