package playground.lunar;

import com.nlf.calendar.Lunar;
 
/**
 * 阴历示例
 *
 * https://github.com/6tail/lunar-java
 */
public class LunarSample{
  public static void main(String[] args){
    //今天
    //Lunar date = new Lunar();
     
    //指定阴历的某一天
    Lunar date = new Lunar(1986,4,21);
    System.out.println(date.toFullString());
    System.out.println(date.getSolar().toFullString());
  }
}
