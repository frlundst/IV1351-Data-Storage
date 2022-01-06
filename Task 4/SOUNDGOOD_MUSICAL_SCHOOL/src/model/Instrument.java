package model;

public class Instrument {
    private String rental_instrument_id;
    private String instrument;
    private String brand;
    private String available;
    private String quantity;

    public Instrument(String rental_instrument_id, String instrument, String brand, String available, String quantity){
        this.rental_instrument_id = rental_instrument_id;
        this.instrument = instrument;
        this.brand = brand;
        this.available = available;
        this.quantity = quantity;
    }

    public String toString(){
        return "[" + rental_instrument_id + ", " + instrument + ", " + brand + ", " + available + ", " + quantity + "]";
    }
}
