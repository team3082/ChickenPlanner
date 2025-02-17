package org.team3082.chicken_planner.FileManagment.CommandLoading;

import java.util.List;

public class CommandConstructorInfo {
    private final String name;
    private final List<ParameterInfo> parameters;

    public CommandConstructorInfo(String name, List<ParameterInfo> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public List<ParameterInfo> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Constructor: " + name + ", Parameters: [");
        for (ParameterInfo param : parameters) {
            builder.append(param).append(", ");
        }
        if (!parameters.isEmpty()) {
            builder.setLength(builder.length() - 2); 
        }
        builder.append("]");
        return builder.toString();
    }
}