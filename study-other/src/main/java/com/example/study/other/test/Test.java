package com.example.study.other.test;

/**
 * @author F.W
 * @date 2019/3/7 16:29
 */
public class Test {

    public static void getPeople(People people, Boolean b) {
//        if (people instanceof PeopleSon){
//            people = ((PeopleSon)people).mPeople;
//        }

        System.out.println("1:  " + people.getClass());
        for (b = false; people instanceof PeopleSon; people = ((PeopleSon) people).mPeople) {
            System.out.println("2:  " + people.getClass());
        }
    }


    public static void main(String[] args) {
        PeopleSon peopleSon = new PeopleSon();
        peopleSon.mPeople = new PeopleSon();
        getPeople(peopleSon, false);
    }
}
