package org.team3082.chicken_planner.FileManagment.CommandLoading;

public class ParameterInfo {
    private final String name;
    private final String type;

    public ParameterInfo(String name, String type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        return "{Name:"+name+", Type:"+type+"}";
    }
}