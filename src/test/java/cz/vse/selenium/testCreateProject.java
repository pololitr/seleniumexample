package cz.vse.selenium;

public class testCreateProject {
    /*

    TC#1: (Precondition - there exists project yourname already in the system.)
    New Task will be created with type Task, name, status New, prio Medium and some description.
    Verify task attributes (Type Task, description, name, priority, status) on task info page (icon i). Delete that task.

    TC#2: (Precondition - there exists project yourname already in the system.)
    Create new 7 tasks with different statuses New, Open, Waiting, Done, Closed, Paid, Canceled.
    Verify that using default filter (New, Open, Waiting) only 3 tasks will be shown.
    Change applied filter in Filter info dialog to only contain (New, Waiting) ...there are more ways how to do it
    (you can click small x on Open "label" to delete it, or you can deal with writing into "suggestion box").
    Verify only New and Waiting tasks are displayed. Now remove all filters and verify all created tasks are displayed.
    Delete all tasks using Select all and batch delete.


     */
}
