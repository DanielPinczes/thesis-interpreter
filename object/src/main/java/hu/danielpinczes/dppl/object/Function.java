package hu.danielpinczes.dppl.object;

import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.ast.statement.expression.Identifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
class Function implements Object {

    private final List<Identifier> parameters;
    private final BlockStatement body;
    private final Environment env;

    @Override
    public ObjectType getType() {
        return ObjectType.FUNCTION_OBJ;
    }

    @Override
    public String inspect() {
        var params = parameters.stream()
                .map(Identifier::toString)
                .collect(Collectors.joining(", "));
        return "fn(" + params + ") {\n" + body.toString() + "\n}";
    }
}