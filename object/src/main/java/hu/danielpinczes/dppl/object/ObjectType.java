package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum ObjectType {
    NULL_OBJ("NULL"),
    ERROR_OBJ("ERROR"),
    INTEGER_OBJ("INTEGER"),
    BOOLEAN_OBJ("BOOLEAN"),
    STRING_OBJ("STRING"),
    RETURN_VALUE_OBJ("RETURN_VALUE"),
    FUNCTION_OBJ("FUNCTION"),
    BUILTIN_OBJ("BUILTIN"),
    ARRAY_OBJ("ARRAY"),
    HASH_OBJ("HASH"),
    ;

    private final String value;
}