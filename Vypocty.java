class Vypocty{
  private float a, b, c;  
  //vypocet diskriminantu 
  public static float diskriminant(float a, float b, float c){
    return (float)Math.pow(b, 2) -  4 * a * c;
  }
  
  //prvy koren rovnice
  public static float kvadratickaRovnicaX1(float a, float b, float c){
    if(diskriminant(a,b,c) > 0){
      return (-b + (float)Math.sqrt(diskriminant(a,b,c))) / (2*a);
    }else{
    //rovnake korene rovnice ===> -b +- 0 = -b
      if(diskriminant(a,b,c) == 0){
        return -b / (2 * a);
      }else{
        return 0;    
      }
    } 
  }
  
  //druhy koren rovnice
  public static float kvadratickaRovnicaX2(float a, float b, float c){
    if(diskriminant(a,b,c) > 0){
      return (-b - (float)Math.sqrt(diskriminant(a,b,c))) / (2*a);
    }else{
    //rovnake korene rovnice ===> -b +- 0 = -b
      if(diskriminant(a,b,c) == 0){
        return -b / (2 * a);
      }else{
        return 0;     
      }
    } 
  }
  
  public static float vypocetX(float a, float b){
    return ((-b) / (2 * a));
  }
  
  public static float vypocetY(float a, float b, float c){
    return (c - (b * b) / (4 * a));
  }
}
  


