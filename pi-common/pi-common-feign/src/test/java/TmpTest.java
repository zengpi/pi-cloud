/**
 * @author ZnPi
 * @date 2023-01-13 14:48
 */
public class TmpTest {
    public static void main(String[] args) {
        try{
            int i = 1/0;
        }finally {
            System.out.println("介绍");
        }
    }
}
