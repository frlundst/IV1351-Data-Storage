package controller;
import model.*;
import integration.*;

public class Controller {
    private SchoolDAO dao;

    public Controller(){
        this.dao = new SchoolDAO();
    }

    public void createStudent(){

    }

    public void findStudentByID(String ID) throws SchoolDBException{
            Student student = this.dao.findStudentByID(ID);
            System.out.println(student.toString());
    }
}
