package controller;
import model.*;
import java.util.*;
import integration.*;

public class Controller {
    private SchoolDAO dao;

    public Controller(){
        this.dao = new SchoolDAO();
    }

    public Student findStudentByID(String ID) throws SchoolDBException{
        return this.dao.findStudentByID(ID);
    }

    public List<Instrument> readAllAvailableInstruments(String kind) throws SchoolDBException{
        return this.dao.readAllAvailableInstruments(kind);
    }

    public String createLeaseContract(String student_id, String rental_instrument_id){
        return this.dao.createLeaseContract(student_id, rental_instrument_id);
    }

    public String deleteLeaseContract(String student_ID){
        return this.dao.deleteLeaseContract(student_ID);
    }
}