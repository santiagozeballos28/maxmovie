
package com.trueffect.logic;


import com.trueffect.model.Bond;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class Short {
    public static void bundle(ArrayList<ModelObject> list) {
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                swap(list, j, j + 1);
            }

        }

    }

    private static void swap(ArrayList<ModelObject> list, int x, int y) {
        Bond bondCurrent = (Bond)list.get(x);
        Bond bondNext = (Bond)list.get(y);
        if (bondCurrent.getSeniority() > bondNext.getSeniority()) {
            list.remove(x);
            list.remove(x);
            list.add(x, bondNext);
            list.add(y, bondCurrent);
        }
    } 


}