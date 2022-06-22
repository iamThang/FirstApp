//package thangho.development2008.fristapp;

public class Test{
//    public Test(){
//
//    }
    public static void main(String[] args){
        try {
            int[] list = {1, 2, 3, 4, 5, 6};
            System.out.println(list[6]);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops!" + e);
        }
    }
}