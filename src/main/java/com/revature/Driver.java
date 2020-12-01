package com.revature;

public class Driver {

	public static void main(String[] args) {
		
		int reimbID = 2;
		String text = String.format(
                            "Successfully submitted new reimbursement request with ID %d", 
							reimbID);
		System.out.println(text);
	}
}
