package de.project.graphProblems;

public enum LogType {
    SEVERE("Serious failure. Indicates a serious problem."),
    WARNING("A potential problem or something unexpected."),
    INFO("General runtime information."),
    CONFIG("Configuration information."),
    FINE("Detailed tracing information (low-level)."),
    FINER("More detailed tracing than FINE."),
    FINEST("Most detailed tracing."),
    ALL("Enables logging of all messages."),
    OFF("Disables logging entirely.");

    private final String description;

    LogType(String description){
        this.description = description;
    }

    public String getDescription(){ return description; }
}
