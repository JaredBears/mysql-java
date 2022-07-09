package projects.service;

import java.util.*;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
    private ProjectDao projectDao = new ProjectDao();

    /*
     * calls DAO class to to insert a project row
     */
    public Project addProject(Project project) {
        return projectDao.insertProject(project);
    }
    
    /*
     * calls the DAO object to retrieve the details of a specified project
     */
    public Project fetchProjectByID(Integer projectID) {
        return projectDao.fetchProjectByID(projectID).orElseThrow(() -> new NoSuchElementException("Project with project ID=" + projectID + " does not exist."));
      }

    /*
     * calls the DAO object to retrieve all projects without details
     */
    public List<Project> fetchAllProjects() {
        return projectDao.fetchAllProjects();
    }

}
