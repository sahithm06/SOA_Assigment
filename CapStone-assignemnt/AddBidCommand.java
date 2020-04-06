package com.example.sharingapp;

import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Command to add a bid
 */
public class AddBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;
    private Context context;

    public AddBidCommand(BidList bid_list, Bid bid, Context context) {
        this.bid_list = bid_list;
        this.bid = bid;
        this.context = context;
    }

    // Save bid locally
    public void execute(){
        ElasticSearchManager.AddBidTask add_bid_task = new ElasticSearchManager.AddBidTask();
        add_bid_task.execute(bid);

        // use get() to access the return of AddBidTask. i.e. AddBidTask returns a Boolean to
        // indicate if the item was successfully saved to the remote server
        try {
            if(add_bid_task.get()) {
                super.setIsExecuted(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
    }
}
