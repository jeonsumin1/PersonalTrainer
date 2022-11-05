package com.example.PT_Backend;

public class InbodyCategory {
    public static void main() {

        String sex = Signup_Php_Mysql.userGender;

        // 키 height
        String strheight = Signup_Php_Mysql.userHeight;
        float height=Float.parseFloat(strheight);
        //몸무게 weight
        String strweight = CameraCheckActivity.getWeight;
        float weight = Float.parseFloat(strweight);

        // BMI
        float BMI1;
        float BMI;
        BMI1 = (float) ((float) weight / (height * 0.01 * height * 0.01));
        BMI = Math.round(BMI1 * 10 / 10.0);

        // 골격근량 skeletal_muscle_mass1
        String strmuscle=CameraCheckActivity.getMuscle;
        float skeletal_muscle_mass1=Float.parseFloat(strmuscle);

        // 골격근률 muscle
        float muscle;
        float skelatal_muscle_mass=(float) (skeletal_muscle_mass1 / (float) weight)*100;
        muscle = Math.round(skelatal_muscle_mass * 10 / 10.0);

        //체지방량 fat
        String strfat=CameraCheckActivity.getFat;
        float fat=Float.parseFloat(strfat);

        // 체지방률 bodyfat
        float bodyfat1;
        float bodyfat;
        bodyfat1 = (float) (fat / (float) weight)*100;
        bodyfat = Math.round(bodyfat1 * 10 / 10.0);



        if ((sex.equals("여성"))) {
            if ((18.5 <= BMI) && (BMI <= 23.0)) {

                if ((26.5 <= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 34.9)) {
                    System.out.print("표준체중 강인형");

                } else if ((0.0 <= muscle) && (bodyfat >= 35.0)) {
                    System.out.println("표준체중 비만형");

                } else if ((26.4>= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 34.9)) {
                    System.out.print("표준체중 허약형");

                } else
                    System.out.println("입력 오류.");

            } else if ((18.5 > BMI)) {
                if ((26.4 >= muscle) && (0.0 <= bodyfat)&& (bodyfat <= 34.9)) {
                    System.out.print("저체중 허약형");

                } else if ((26.5 <=muscle) && (0.0 <= bodyfat) && (bodyfat <= 34.9)) {
                    System.out.println("저체중 강인형");

                } else
                    System.out.println("입력 오류.");
            } else if ((23.0 < BMI)) {
                if ((26.4 >= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 34.9)) {
                    System.out.print("과체중 허약형");

                } else if ((26.5 <= muscle) && (0.0 <= bodyfat) && (bodyfat <= 34.9)) {
                    System.out.println("과체중 강인형");

                } else if ((0.0 <= muscle) && (bodyfat >= 35.0)) {
                    System.out.print("과체중 비만형");

                } else
                    System.out.println("입력 오류.");
            } else {
                System.out.println("입력 오류.");
            }
        }else if((sex.equals("남성"))) {
            if ((18.5 <= BMI) && (BMI <= 23.0)) {

                if ((32.0 <= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 24.9)) {
                    System.out.print("표준체중 강인형");

                } else if ((0.0 <= muscle) && (bodyfat >= 25.0)) {
                    System.out.println("표준체중 비만형");

                } else if ((31.9>= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 24.9)) {
                    System.out.print("표준체중 허약형");

                } else
                    System.out.println("입력 오류.");

            } else if ((18.5 > BMI)) {
                if ((31.9 >= muscle) && (0.0 <= bodyfat)&& (bodyfat <= 24.9)) {
                    System.out.print("저체중 허약형");

                } else if ((32.0 <=muscle) && (0.0 <= bodyfat) && (bodyfat <= 24.9)) {
                    System.out.println("저체중 강인형");

                } else
                    System.out.println("입력 오류.");
            } else if ((23.0 < BMI)) {
                if ((31.9>= muscle) && (0.0 <= bodyfat) &&(bodyfat <= 24.9)) {
                    System.out.print("과체중 허약형");

                } else if ((32.0 <= muscle) && (0.0 <= bodyfat) && (bodyfat <= 24.9)) {
                    System.out.println("과체중 강인형");

                } else if ((0.0 <= muscle) && (bodyfat >= 25.0)) {
                    System.out.print("과체중 비만형");

                } else
                    System.out.println("입력 오류.");
            } else {
                System.out.println("입력 오류.");
            }


        }
    }

} // class 끝