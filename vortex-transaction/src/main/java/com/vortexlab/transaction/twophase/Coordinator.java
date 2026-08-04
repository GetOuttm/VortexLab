package com.vortexlab.transaction.twophase;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Coordinator {

    private final List<Participant>
            participants;

    public void execute(){
        boolean success=true;
        /**
         * 第一阶段
         */
        for(
            Participant p:
            participants
        ){


            if(!p.prepare()){

                success=false;

                break;

            }

        }



        /**
         * 第二阶段
         */

        if(success){


            for(
                Participant p:
                participants
            ){

                p.commit();

            }


        }else{


            for(
                Participant p:
                participants
            ){

                p.rollback();

            }


        }


    }


}