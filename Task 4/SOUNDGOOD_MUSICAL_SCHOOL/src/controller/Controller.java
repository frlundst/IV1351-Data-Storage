package controller;
import model.*;
import java.util.*;
import integration.*;

public class Controller {
    private SchoolDAO dao;

    public Controller() throws SchoolDBException{
        this.dao = new SchoolDAO();
    }

    /**
     * 
     * @param kind
     * @return
     */
    public List<Instrument> findAvailableInstruments(String kind){
        try {
            return this.dao.findAvailableInstruments(kind);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param student_id
     * @param rental_instrument_id
     */
    public void createLeaseContract(int student_id, int rental_instrument_id){
        try {
            this.dao.createLeaseContract(student_id, rental_instrument_id);
        } catch (SchoolDBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param student_ID
     */
    public void deleteLeaseContract(int student_ID){
        try {
            this.dao.terminateLeaseContract(student_ID);
        } catch (SchoolDBException e) {
            e.printStackTrace();
        }
    }
}