/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actionlisteners;

import gui.Field;
import gui.FieldCreator;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import referencechampion.ReferenceBase;
import referencechampion.ReferenceEntity;

/**
 *
 * @author alrial
 */
public class CreateReference implements ActionListener {

    private ReferenceBase base;
    private HashMap<String, String> referenceValues;
    protected Map<String, Field> fields;
    private JLabel result;
    private JComboBox typeList;

    public CreateReference(Map<String, Field> fields, ReferenceBase base, JLabel result, JComboBox typeList) {
        this.fields = fields;
        this.base = base;
        this.result = result;
        this.referenceValues = new HashMap<String, String>();
        this.typeList = typeList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ReferenceEntity reference = new ReferenceEntity(typeList.getSelectedItem().toString());
        if (fields != null) {
            for (String s : fields.keySet()) {
                reference.addValue(s, fields.get(s).getText());
            }
        }

        if (base.addReference(reference)) {
            result.setForeground(Color.green);
            result.setText("New reference added");
            // result.setFont(new Font(result.getName(), Font.PLAIN, 22));

            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    result.setText("");
                }
            };
            Timer timer = new Timer(5000, taskPerformer);
            timer.setRepeats(false);
            timer.start();
            FieldCreator.emptyFields(this.fields);
        } else {
            result.setText("One or more required fields are empty");
        }
    }

    public void setReferenceValues(HashMap<String, String> values) {
        this.referenceValues = values;
    }
}
