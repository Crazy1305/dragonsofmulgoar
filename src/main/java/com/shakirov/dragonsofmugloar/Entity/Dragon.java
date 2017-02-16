package com.shakirov.dragonsofmugloar.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author vadim.shakirov
 * Class Dragon
 * Constructor with (Knight) parameter creates a dragon that can eat a knight
 * weather considered
 */
public class Dragon {
    
    private class FieldComparator implements Comparator<Field> {

        private final Object main;

        public FieldComparator(Object o) {
            main = o;
        }

        @Override
        public int compare(Field o1, Field o2) {
            try {
                if (o1.getInt(main) == o2.getInt(main)) return 0;
                if (o1.getInt(main) < o2.getInt(main)) return -1;
                else return 1;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                throw new ClassCastException();
            }
        }
    }
    
    private class InnerDragon {    
        public int scaleThickness;
        public int clawSharpness;
        public int wingStrength;
        public int fireBreath;
        
        public InnerDragon() {
            scaleThickness = 0;
            clawSharpness = 0;
            wingStrength = 0;
            fireBreath = 0;
        }
        
        private ArrayList<Field> getSortedFields() {
            Class c = InnerDragon.class;
            Field[] fields = c.getFields();
            FieldComparator fc = new FieldComparator(this);
            ArrayList<Field> res = new ArrayList<>(Arrays.asList(fields));
            java.util.Collections.sort(res, fc);
            return res;
        }
        
        private void addFieldValue(Field f, int inc) throws IllegalArgumentException, IllegalAccessException {
            int i = f.getInt(this);
            i += inc;
            f.setInt(this, i);
        }
        
        private int getFieldValue(Field f) throws IllegalArgumentException, IllegalAccessException {
            return f.getInt(this);
        }
        
        /*
        * If the knight has 1 stat at 0, then we need to beat the best stat by 2, 
        * -1 on 2nd best, -1 on 3rd best, and 0 on last
        * Otherwise we should have 2+ against Knights best still, 
        * no more than -1 to all others.
        * Also no stat above 10, no stat below 1
        */
        public void create(Knight knight) throws IllegalArgumentException, IllegalAccessException {
            scaleThickness = knight.getAttack();
            clawSharpness = knight.getArmor();
            wingStrength = knight.getAgility();
            fireBreath = knight.getEndurance();
            ArrayList<Field> fields = getSortedFields();
            if (getFieldValue(fields.get(0)) == 0) {
                addFieldValue(fields.get(1), -1);
                addFieldValue(fields.get(2), -1);
                addFieldValue(fields.get(3), 2);
            } else {
                int points = 0;
                // points needed to increase max stat to 10
                int maxPoints = 10 - getFieldValue(fields.get(3));
                // if secondary stats above 1, decrease stat to take free point
                for (int i = 2; i >= 0; i++) {
                    if ((getFieldValue(fields.get(i))> 1)&&(points < maxPoints)) {
                        addFieldValue(fields.get(i), -1);
                        points++;
                    }
                }
                // put fried points to max stat
                addFieldValue(fields.get(3), points);                
            }
        }
        
        public void create(Knight knight, String weather) throws IllegalArgumentException, IllegalAccessException {
            // only balanced dragon can win during THE LONG DRY
            if (Weather.isLongDry(weather)) {
                scaleThickness = 5;
                clawSharpness = 5;
                wingStrength = 5;
                fireBreath = 5;
                return;
            } 
            // dragon stats = knight stats
            scaleThickness = knight.getAttack();
            clawSharpness = knight.getArmor();
            wingStrength = knight.getAgility();
            fireBreath = knight.getEndurance();
            // if weather is fog - dragon always win
            if (Weather.isFog(weather)) { return; }
            /* 
            * if it is rain fire is useless. 
            * Dragon need max clawSharpness to destroy umbrellaboots
            */
            if (Weather.isRain(weather)) {
                int points = 10 - clawSharpness;
                if (fireBreath > points) {
                    clawSharpness = 10;
                    scaleThickness += fireBreath - points;
                } else {
                    clawSharpness += fireBreath;
                }
                fireBreath = 0;
            }
        }
    }

    /* 
    * Inner class is being used to match request format 
    * {"dragon":{"scaleThickness":1,"clawSharpness":6,"wingStrength":4,"fireBreath":9}}
    */
    private InnerDragon dragon;
    
    public Dragon() {}
    
    public Dragon(Knight knight, String weather) throws IllegalArgumentException, IllegalAccessException {
        dragon = new InnerDragon();
        if (Weather.isNormal(weather)) {
            dragon.create(knight);
        } else {
            dragon.create(knight, weather);
        }
    }
    
    public int getScaleThickness() { return dragon.scaleThickness; }
    public int getClawSharpness() { return dragon.clawSharpness; }
    public int getWingStrength() { return dragon.wingStrength; }
    public int getFireBreath() { return dragon.fireBreath; }

}
