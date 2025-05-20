package de.project.service;

public enum LogType {
    ERROR("Severe issue appeared (text color is red)."),
    WARN("A potential problem or something unexpected (text color is yellow)."),
    INFO("General runtime information (text color is standard)."),
    SUCCESS("Operation completed successfully (text color is green).");

    private final String description;

    LogType(String description){
        this.description = description;
    }

    public String getDescription(){ return description; }
}
