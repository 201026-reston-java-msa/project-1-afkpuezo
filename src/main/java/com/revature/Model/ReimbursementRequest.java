/**
 * This class represents Reimbursement Requests within the application.
 * It does not do any verification of the data it is given; that should be
 * done before creating an object of this class. 
 * 
 * @Author Andrew Curry
 */
package com.revature.Model;

public class ReimbursementRequest {
    
    // enums ---------------------

    /**
     * Describes what kind of expense
     */
    public enum Type{

        NONE(0, "NONE"), // never used?
        LODGING(1, "LODGING"),
        TRAVEL(2, "TRAVEL"),
        FOOD(3, "FOOD"),
        OTHER(4, "OTHER");

        private int ID;
        private String name;

        private Type(int ID, String name){
            this.ID = ID;
            this.name = name;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }
    } // end enum TYpe

    /**
     * Describes whether the request has been approved or not
     */
    public enum Status{

        NONE(0, "NONE"), // never used?
        PENDING(1, "PENDING"),
        APPROVED(2, "APPROVED"),
        DENIED(3, "DENIED");

        private int ID;
        private String name;

        private Status(int ID, String name){
            this.ID = ID;
            this.name = name;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }
    } // end enum Status
    
    // class/static variables ---------------------

    // instance variables ---------------------
    private int ID;
    private int authorID; // user who submitted this req
    private long moneyAmount;
    private Type type; // what kind of expense
    private Status status; // has it been approved or not
    private String description;
    private String timeSubmitted;
    private int resolverID; // manager
    private String timeResolved;

    // optionally, some way of representing an image
    
    // constructor(s) ---------------------

    /**
     * Defaults to PENDING status
     * @param ID
     * @param authorID
     * @param moneyAmount
     * @param type
     */
    public ReimbursementRequest(int ID, int authorID, long moneyAmount, Type type){

        this.ID = ID;
        this.authorID = authorID;
        this.moneyAmount = moneyAmount;
        this.type = type;
        this.status = Status.PENDING;
        this.description = "";
        this.timeSubmitted = "PLACEHOLDER";
        this.resolverID = -1;
        this.timeResolved = "PLACEHOLDER";
    }

    /**
     * 
     * @param ID
     * @param authorID
     * @param moneyAmount
     * @param type
     * @param status
     * @param description
     * @param timeSubmitted,
     * @param resolvedID
     * @param timeResolved
     */
    public ReimbursementRequest(
            int ID, 
            int authorID, 
            long moneyAmount, 
            Type type,
            Status status,
            String description,
            String timeSubmitted,
            int resolverID,
            String timeResolved) {

        this.ID = ID;
        this.authorID = authorID;
        this.moneyAmount = moneyAmount;
        this.type = type;
        this.status = status;
        this.description = description;
        this.timeSubmitted = timeSubmitted;
        this.resolverID = resolverID;
        this.timeResolved = timeResolved;
    }

    // getters and setters ---------------------
    // currently no way to set ID, authorID, money, or type

    public int getID() {
        return this.ID;
    }

    public int getAuthorID() {
        return this.authorID;
    }

    public long getMoneyAmount() {
        return this.moneyAmount;
    }

    public Type getType() {
        return this.type;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeSubmitted() {
        return this.timeSubmitted;
    }

    public void setTimeSubmitted(String timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public int getResolverID() {
        return this.resolverID;
    }

    public void setResolverID(int resolverID) {
        this.resolverID = resolverID;
    }

    public String getTimeResolved() {
        return this.timeResolved;
    }

    public void setTimeResolved(String timeResolved) {
        this.timeResolved = timeResolved;
    }

}
