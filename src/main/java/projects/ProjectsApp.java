package projects;

import java.math.BigDecimal;
import java.util.*;

import projects.exception.DbException;
import projects.entity.Project;
import projects.service.ProjectService;

/*
 * This is a menu-driven application to accept user inputs and perform CRUD operations on an MySQL database
 */
public class ProjectsApp {
    private Scanner sc = new Scanner(System.in);
    private ProjectService projectService = new ProjectService();
    private Project curProject;
    
    // @formatter: off
    private List<String> operations = List.of(
        "1) Add a project",
        "2) List projects",
        "3) Select a project",
        "4) Update project details"
    );
    // @formatter: on
    
    public static void main(String[] args) {
        new ProjectsApp().processUserSelection();

    }
    
    /*
     * this method repeatedly calls for a user selection from a menu and 
     * terminates when desired
     */
    private void processUserSelection() {
        boolean done = false;
        
        while(!done) {
            try {
                int selection = getUserSelection();
                
                switch(selection) {
                    case -1:
                        done = exitMenu();
                        break;
                    case 1:
                        createProject();
                        break;
                    case 2:
                        listProjects();
                        break;
                    case 3:
                        selectProject();
                        break;
                    case 4:
                        updateProject();
                        break;
                    default:
                        System.out.println("\n" + selection + " is not a valid selection. Try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e + " Try Again.");
            }
        }
        
    }
    /*
     * Checks to see if a project is selected, and if so allows the user to update the values one at a time.
     * If an entry is null, no change is made
     */
    private void updateProject() {
        if(Objects.isNull(curProject)){
            System.out.println("\nPlease select a project.\n");
            return;
        }
        Project project = new Project();
        String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
        BigDecimal projectEH = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
        BigDecimal projectAH = getDecimalInput("Enter the actual hours [" + curProject.getActualHours() + "]");
        Integer projectDifficulty = getIntInput("Enter the difficulty [" + curProject.getDifficulty() + "]");
        String projectNotes = getStringInput("Add new notes [\n" + curProject.getNotes()+ "\n]");
        
        project.setProjectId(curProject.getProjectId());
        project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
        project.setEstimatedHours(Objects.isNull(projectEH) ? curProject.getEstimatedHours() : projectEH);
        project.setActualHours(Objects.isNull(projectAH) ? curProject.getActualHours() : projectAH);
        project.setDifficulty(Objects.isNull(projectDifficulty) ? curProject.getDifficulty() : projectDifficulty);
        project.setNotes(Objects.isNull(projectNotes) ? curProject.getNotes() : projectNotes);
        
        projectService.modifyProjectDetails(project);
        
    }

    /*
     * Assigns curProject, allowing the user to work with a particular project
     */
    private void selectProject() {
        listProjects();
        Integer projectID = getIntInput("Enter a project ID to select a project");
        
        curProject = null;
        curProject = projectService.fetchProjectByID(projectID);
        if(Objects.isNull(curProject)){
            System.out.println("\nInvalid project ID selected.\n");
        }
        
    }

    /*
     * Lists all projects without details
     */
    private void listProjects() {
        List<Project> projects = projectService.fetchAllProjects();
        
        for(Project project : projects) {
            System.out.println("   " + project.getProjectId() + ": " + project.getProjectName());
        }
        
    }

    /*
     * this method prints the menu operations and accepts the user's 
     * selected input
     */
    private int getUserSelection() {
        printOperations();
        
        Integer input = getIntInput("Enter a menu selection");
        
        return Objects.isNull(input) ? -1 : input;
    }
    
    //gathers user input
    private String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = sc.nextLine();
        
        return input.isBlank() ? null : input.trim();
    }
    
    //gathers user input and converts to Integer
    private Integer getIntInput(String prompt) {
        String input = getStringInput(prompt);
        
        if (Objects.isNull(input)) {
            return null;
        }
        try {
            return Integer.valueOf(input);
        }
        catch(NumberFormatException e) {
            throw new DbException(input + " is not a valid number.");
        }
    }
    
    //prints the menu options
    private void printOperations() {
        System.out.println("\nThese are the available selections. Press the Enter key to quit.");
        operations.forEach(line -> System.out.println(" " + line));
        
        if(Objects.isNull(curProject)) {
            System.out.println("\nYou are not working with a project.");
        } else {
            System.out.println("\nYou are working with project: " + curProject);
        }
    }
    
    //gathers user input and converts to BigDecimal
    private BigDecimal getDecimalInput(String prompt) {
        String input = getStringInput(prompt);
        
        if (Objects.isNull(input)) {
            return null;
        }
        try {
            return new BigDecimal(input).setScale(2);
        }
        catch(NumberFormatException e) {
            throw new DbException(input + " is not a valid decimal number.");
        }
    }
    
    //Gathers user input and then creates a project row
    private void createProject() {
        String projectName = getStringInput("Enter the project name");
        BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
        BigDecimal actualHours = getDecimalInput("Enter the actual hours");
        Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
        String notes = getStringInput("Enter the project notes");
        
        Project project = new Project();
        
        project.setProjectName(projectName);
        project.setEstimatedHours(estimatedHours);
        project.setActualHours(actualHours);
        project.setDifficulty(difficulty);
        project.setNotes(notes);
        
        Project dbProject = projectService.addProject(project);
        System.out.println("You have successfully created project: " + dbProject);
        
    }

    //exits the menu when called
    private boolean exitMenu() {
        System.out.println("\nClosing the menu.");
        return true;
    }
}
