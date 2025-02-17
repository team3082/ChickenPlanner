package org.team3082.chicken_planner.FileManagment.CommandLoading;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {
    public static List<CommandConstructorInfo> findAnnotatedConstructors(String directoryPath) {
        List<CommandConstructorInfo> annotatedConstructors = new ArrayList<>();
    
        try {
            List<Path> javaFiles = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
    
            for (Path javaFile : javaFiles) {
                CompilationUnit compilationUnit = StaticJavaParser.parse(javaFile);
    
                for (TypeDeclaration<?> type : compilationUnit.getTypes()) {
                    // Check if the class extends Command
                    if (type instanceof ClassOrInterfaceDeclaration && isSubclassOfCommand((ClassOrInterfaceDeclaration) type)) {
                        // Check each constructor
                        type.getConstructors().forEach(constructor -> {
                            boolean isAnnotated = isAnnotatedWithChickenPlannable(constructor);
                            if (isAnnotated && areParametersValid(constructor)) {
                                annotatedConstructors.add(new CommandConstructorInfo(
                                        constructor.getNameAsString(),
                                        extractParameterInfo(constructor)
                                ));
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    
        return annotatedConstructors;
    }

    private static boolean isAnnotatedWithChickenPlannable(ConstructorDeclaration constructor) {
        return constructor.getAnnotations().stream()
                .anyMatch(annotation -> annotation.getNameAsString().equals("ChickenPlannable"));
    }

    private static boolean isSubclassOfCommand(ClassOrInterfaceDeclaration type) {
        return type.getExtendedTypes().stream()
                .anyMatch(t -> t.getNameAsString().equals("Command"));
    }

    private static boolean areParametersValid(ConstructorDeclaration constructor) {
        for (Parameter param : constructor.getParameters()) {
            String paramType = param.getType().asString();
            if (!isPrimitiveOrString(paramType)) {
                return false;  // If any parameter is not primitive or String, return false
            }
        }
        return true;
    }

    private static boolean isPrimitiveOrString(String type) {
        return type.equals("int") || type.equals("long") || type.equals("float") ||
               type.equals("double") || type.equals("boolean") || type.equals("char") ||
               type.equals("byte") || type.equals("short") || type.equals("String");
    }

    private static List<ParameterInfo> extractParameterInfo(ConstructorDeclaration constructor) {
        List<ParameterInfo> parameterInfos = new ArrayList<>();
        for (Parameter param : constructor.getParameters()) {
            String name = param.getNameAsString();
            String type = param.getType().asString();
            parameterInfos.add(new ParameterInfo(name, type)); 
        }
        return parameterInfos;
    }
}
