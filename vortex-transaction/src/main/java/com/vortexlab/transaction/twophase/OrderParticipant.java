package com.vortexlab.transaction.twophase;

@Component
public class OrderParticipant
        implements Participant{


    @Override
    public boolean prepare(){


        System.out.println(
            "订单prepare"
        );


        return true;

    }



    @Override
    public void commit(){


        System.out.println(
            "订单commit"
        );


    }



    @Override
    public void rollback(){


        System.out.println(
            "订单rollback"
        );

    }

}