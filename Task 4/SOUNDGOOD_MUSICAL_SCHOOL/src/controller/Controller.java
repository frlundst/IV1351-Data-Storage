package controller;
import model.*;
import java.util.*;
import integration.*;

public class Controller {
    private SchoolDAO dao;

    public Controller() throws SchoolDBException{
        this.dao = new SchoolDAO();
    }

    public List<Instrument> findAvailableInstruments(String kind) throws SchoolDBException{
        return this.dao.findAvailableInstruments(kind, false);
    }

    public String createLeaseContract(String student_id, String rental_instrument_id){
        return this.dao.createLeaseContract(student_id, rental_instrument_id);
    }

    public String deleteLeaseContract(String student_ID){
        return this.dao.deleteLeaseContract(student_ID);
    }
}