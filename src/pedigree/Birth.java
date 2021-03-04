/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedigree;

/**
 *
 * @author emuli
 */
public class Birth extends Event {

    public Birth(Sim sim, double time) {
        super(sim, time);
    }

    @Override
    public void simulate() { // TODO
        System.out.println("Getting out of the womb");
    }

}
