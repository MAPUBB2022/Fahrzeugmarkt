package view;

import controller.Controller;
import model.Advert;
import model.Car;
import model.Motorcycle;

public class View {
    private Controller controller;

    private int userMode;

    public View() {
        this.controller = new Controller();
        userMode = -1;
    }

    public void login()
    {

    }

    public void mainMenu()
    {

    }

//    public Advert createAd(){
//        boolean is_Car=true;
//        Advert a;
//        if(is_Car){
//
////            a = new Car(make, model, year,..)
//        }
//        else
//        {
////            a = new Motorcycle();
//        }
////        return a;
//    }

}
