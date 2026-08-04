package com.vortexlab.transaction.twophase;

import org.springframework.stereotype.Component;

@Component
public class StockParticipant
        implements Participant{


    @Override
    public boolean prepare(){

        System.out.println(
            "库存prepare"
        );

        return true;

    }



    @Override
    public void commit(){

        System.out.println(
            "库存commit"
        );

    }



    @Override
    public void rollback(){

        System.out.println(
            "库存rollback"
        );

    }

}