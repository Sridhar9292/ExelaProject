package com.exelatech.ecx.backend.model;

import java.util.ArrayList;
import java.util.List;

public class FeedSummary{
    private List<BillerDetail> billerDetails = new ArrayList<BillerDetail>();
    private BillerTotal billerTotal = new BillerTotal();

    public List<BillerDetail> getBillerDetails() {
        return billerDetails;
    }

    public void setBillerDetails(List<BillerDetail> billerDetails) {
        this.billerDetails = billerDetails;
    }

    public BillerTotal getBillerTotal() {
        return billerTotal;
    }

   

	public void setBillerTotal(BillerTotal billerTotal) {
        this.billerTotal = billerTotal;
    }

    public void addBillerDetail(BillerDetail billerDetail){
        billerDetails.add(billerDetail);
        billerTotal.addBillerDetail(billerDetail);
    }
    
    
    @Override
   	public String toString() {
   		return "FeedSummary [billerDetails=" + billerDetails + ", billerTotal=" + billerTotal + "]";
   	}
    
}
