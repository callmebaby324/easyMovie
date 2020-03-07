import org.junit.Test;
import org.mockito.cglib.core.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

public class CommonTest {

    @Test
    public void test1(){
        Integer[] nums = {1,2,3,3,4,4,4,5};
        List<Integer> ints = new ArrayList<>(Arrays.asList(nums));
        Iterator<Integer> iterator = ints.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if(next==1){
                iterator.remove();
            }
        }
        System.out.println(ints);
    }

    @Test
    public void test2(){
        Integer[] nums = {1,2,3,13,24,34,44,55};
        List<Integer> ints = new ArrayList<>(Arrays.asList(nums));
        System.out.println(ints);
        Collections.shuffle(ints);
        System.out.println(ints);
    }

    @Test
    public void test3(){
        String s = "asdkjf={id}";
        String replace = s.replaceAll("\\{id}", "5");
        System.out.println(s);
        System.out.println(replace);
    }

}
