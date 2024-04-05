package ch.cern.todo.constants;

public class Constants {
    // Controllers
    public static final String CATEGORIES_REQUEST_MAPPING = "/categories";
    public static final String ID_PATH_PARAMETER = "/{id}";

    // Tables
    // TASK_CATEGORY table
    public static final String TASK_CATEGORY_TABLE_NAME = "task_categories";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION = "category_description";

    // Errors
    public static final String CATEGORY_NOT_FOUND_ERROR_MESSAGE = "The category %s was not found in the database.";
    public static final String IDS_DONT_MATCH_ERROR_MESSAGE = "The ID in path %s and the id in request body %s must match.";
}
