package com.shakirov.dragonsofmugloar.entity;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vadim.shakirov
 * Class Dragon
 * Constructor with (Knight) parameter creates a dragon that can eat a knight
 * weather considered
 */
public class Dragon {
    
    protected int scaleThickness;
    protected int clawSharpness;
    protected int wingStrength;
    protected int fireBreath;
    
    private List<Field> sortedFields;
    
    private class FieldComparator implements Comparator<Field>{
        
        private final Dragon dragon;
        
        public FieldComparator(Dragon dragon) {
            this.dragon = dragon;
        }
    
        @Override
        public int compare(Field o1, Field o2) {
            try {
                if (o1.getInt(dragon) == o2.getInt(dragon)) return 0;
                if (o1.getInt(dragon) < o2.getInt(dragon)) return -1;
                else return 1;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                throw new ClassCastException();
            }
        }
    }

    /*
    * @param Knight knight - рыцарь, который должен быть повержен
    * @param String weather - погода, при которой идет битва
    * Функция распределяет характеристики дракона для битвы в нормальных погодных условиях
    */
    private void createNormal(Knight knight) throws IllegalArgumentException, IllegalAccessException {
        if (getFieldValue(3) == 0) {
            addFieldValue(1, -1);
            addFieldValue(2, -1);
            addFieldValue(3, 2);
        } else {
            int points = 0;
            // points needed to increase max stat to 10
            int maxPoints = 10 - getFieldValue(3);
            // if secondary stats above 1, decrease stat to take free point
            for (int i = 2; i >= 0; i--) {
                if ((getFieldValue(i)> 1)&&(points < maxPoints)) {
                    addFieldValue(i, -1);
                    points++;
                }
            }
            // put fried points to max stat
            addFieldValue(3, points);
        }
    }
    
    /*
    * @Javadoc
    * @param Knight knight - рыцарь, который должен быть повержен
    * @param String weather - погода, при которой идет битва
    * Функция распределяет характеристики дракона для битвы в особых погодных условиях
    */
    private void createSpecial(Knight knight, String weather) throws IllegalArgumentException, IllegalAccessException {
        // only balanced dragon can win during THE LONG DRY
        if (Weather.isLongDry(weather)) {
            scaleThickness = 5;
            clawSharpness = 5;
            wingStrength = 5;
            fireBreath = 5;
            return;
        } 
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

    private ArrayList<Field> getSortedFields() throws IllegalArgumentException, IllegalAccessException {
        Class c = this.getClass();
        Field[] fields = c.getDeclaredFields();
        FieldComparator fc = new FieldComparator(this);
        ArrayList<Field> res = new ArrayList<>();
        for (Field field : fields) {
            if (field.get(this).getClass() != sortedFields.getClass())
                res.add(field);
        }
        java.util.Collections.sort(res, fc);
        return res;
    }
        
    private void addFieldValue(int index, int inc) throws IllegalArgumentException, IllegalAccessException {
        int i = sortedFields.get(index).getInt(this);
        i += inc;
        sortedFields.get(index).setInt(this, i);
    }

    private int getFieldValue(int index) throws IllegalArgumentException, IllegalAccessException {
        return sortedFields.get(index).getInt(this);
    }
    
    private void init(Knight knight) throws IllegalArgumentException, IllegalAccessException {
        scaleThickness = knight.getAttack();
        clawSharpness = knight.getArmor();
        wingStrength = knight.getAgility();
        fireBreath = knight.getEndurance();
        sortedFields.addAll(getSortedFields());
    }
    
    public Dragon() { }
    
    public Dragon(Knight knight, String weather) throws IllegalArgumentException, IllegalAccessException {
        this.sortedFields = new ArrayList<>();
        // dragon stats = knight stats
        init(knight);
        if (Weather.isNormal(weather)) {
            createNormal(knight);
        } else {
            createSpecial(knight, weather);
        }
    }
    
    public int getScaleThickness() { return scaleThickness; }
    public int getClawSharpness() { return clawSharpness; }
    public int getWingStrength() { return wingStrength; }
    public int getFireBreath() { return fireBreath; }

}
